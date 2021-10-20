/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.github.toploadermc.eventbus.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import com.github.toploadermc.eventbus.core.bus.BusBuilder;
import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.event.Cancellable;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import com.github.toploadermc.eventbus.core.handlers.EventExceptionHandler;
import com.github.toploadermc.eventbus.core.listener.EventListener;
import com.github.toploadermc.eventbus.core.listener.EventListenerProvider;
import com.github.toploadermc.eventbus.core.logging.Loggers;
import com.github.toploadermc.eventbus.core.logging.Markers;
import com.github.toploadermc.eventbus.core.provider.ListenerListProvider;
import com.github.toploadermc.eventbus.core.register.MethodPair;
import com.github.toploadermc.eventbus.core.register.Registration;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StandardEventBus implements EventBus {

    private final Map<Object, List<EventListener>> listeners = new ConcurrentHashMap<>();

    private static final AtomicInteger maxID = new AtomicInteger(0);
    private final int busID = maxID.getAndIncrement();

    private final EventExceptionHandler exceptionHandler;
    private final EventListenerProvider listenerProvider;
    private final ListenerListProvider listProvider;
    private final @Nullable Class<?> markerType;
    private boolean isActive;

    protected StandardEventBus(EventExceptionHandler exceptionHandler, EventListenerProvider listenerProvider, ListenerListProvider listProvider, @Nullable Class<?> markerType, boolean startsShutdown) {
        this.exceptionHandler = exceptionHandler;
        this.listenerProvider = listenerProvider;
        this.listProvider = listProvider;
        this.markerType = markerType;
        this.isActive = !startsShutdown;

        ListenerList.resize(busID + 1);
    }

    public StandardEventBus(BusBuilder builder) {
        this(builder.getExceptionHandler(), builder.getEventListenerProvider(), builder.getListenerListProvider(), builder.getMarkerType(), builder.startsShutdown());
    }

    //======================================================================================================================================
    // Lifecycle
    //======================================================================================================================================

    @Override
    public void start() {
        if (isActive) return;

        Loggers.EVENT_BUS.error(Markers.EVENTBUS, "EventBus {} starting up - future events will be posted.", busID);
        this.isActive = true;
    }

    @Override
    public void shutdown() {
        if (!isActive) return;

        Loggers.EVENT_BUS.error(Markers.EVENTBUS, "EventBus {} shutting down - future events will not be posted.", busID, new Exception("stacktrace"));
        this.isActive = false;
    }

    @Override public boolean isActive() {
        return isActive;
    }

    //======================================================================================================================================
    // API
    //======================================================================================================================================

    @Override public void register(Object target) {
        if (listeners.containsKey(target)) return; // Already Registered

        Stream<MethodPair> methods = target instanceof Class<?>
            ? Registration.findStaticSubscriptions((Class<?>) target)
            : Registration.findSubscriptions(target);

        methods.forEach(it -> registerListener(target, it.annotated(), it.actual()));
    }

    private void registerListener(final Object target, final Method annotated, final Method actual) {
        Class<?>[] parameterTypes = annotated.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new IllegalArgumentException(
                "Method " + annotated + " has @SubscribeEvent annotation. " +
                "It has " + parameterTypes.length + " arguments, " +
                "but event handler methods require a single argument only."
            );
        }

        Class<?> eventType = parameterTypes[0];

        if (Config.CHECK_TYPE_ON_REGISTER && markerType != null && !markerType.isAssignableFrom(eventType)) {
            throw new IllegalArgumentException(
                "Method " + annotated + " has @SubscribeEvent annotation, " +
                "but takes an argument that is not a subtype of the marker type " +
                markerType + ": " + eventType);
        }

        register(eventType, target, actual);
    }

    private void register(Class<?> eventType, Object target, Method method) {
        try {
            SubscribeEvent annotation = method.getAnnotation(SubscribeEvent.class);
            final EventListener asm = listenerProvider.create(eventType, target, method, annotation);

            addListener(annotation.priority(), eventType, asm);
        } catch (Exception e) {
            Loggers.EVENT_BUS.error(Markers.EVENTBUS, "Error registering event handler: {} {}", eventType, method, e);
        }
    }

    @Override public void unregister(Object target) {
        if (target instanceof EventListener) target = ((EventListener) target).getKey();

        List<EventListener> list = listeners.remove(target);
        if (list == null) return;

        for (EventListener listener : list) {
            ListenerList.unregisterAll(busID, listener);
        }
    }

    @Override public boolean post(Object event) {
        if (!isActive) return false;

        if (Config.CHECK_TYPE_ON_DISPATCH && markerType != null && !markerType.isInstance(event))
            throw new IllegalArgumentException("Cannot post event of type " + event.getClass().getSimpleName() + " to this event. Must match type: " + markerType.getSimpleName());

        EventListener[] listeners = listProvider.provide(event).getListeners(busID);

        int index = 0;
        try {
            for (; index < listeners.length; index++) {
                listeners[index].invoke(event);
            }
        } catch (Throwable throwable) {
            exceptionHandler.handleException(this, event, listeners, index, throwable);
            throw throwable;
        }

        // Return true if the event is Cancelable and is Canceled, false otherwise
        return event instanceof Cancellable && ((Cancellable) event).isCancelled();
    }

    @Override public <T> void addListener(EventPriority priority, Class<T> eventClass, EventListener listener) {
        if (markerType != null && !markerType.isAssignableFrom(eventClass)) {
            throw new IllegalArgumentException("Listener for event " + eventClass + " takes an argument that is not a subtype of the marker type " + markerType);
        }

        ListenerList listenerList = EventBusHelper.getListenerList(eventClass);
        listenerList.register(busID, priority, listener);

        listeners
            .computeIfAbsent(listener.getKey(), k -> Collections.synchronizedList(new ArrayList<>()))
            .add(listener);
    }

}
