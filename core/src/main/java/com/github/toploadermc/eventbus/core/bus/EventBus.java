/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.bus;

import com.github.toploadermc.eventbus.core.validators.RegistrationValidators;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.github.toploadermc.eventbus.core.Config;
import com.github.toploadermc.eventbus.core.event.Cancellable;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import com.github.toploadermc.eventbus.core.listener.EventListener;
import com.github.toploadermc.eventbus.core.listener.FilteringEventListener;
import com.github.toploadermc.eventbus.core.listener.NamedEventListener;
import com.github.toploadermc.eventbus.core.util.Filters;
import com.github.toploadermc.eventbus.core.util.Types;

public interface EventBus {

    //======================================================================================================================================
    // Lifecycle
    //======================================================================================================================================

    /**
     * Starts this event bus.
     *
     * Events can now be fired on this event bus, so any call to {@link #post(Object)} will no longer be a no op
     */
    void start();

    /**
     * Shuts down this event bus.
     *
     * No future events will be fired on this event bus, so any call to {@link #post(Object)} will be a no op after this method has been invoked
     */
    void shutdown();

    /**
     * Whether this event bus is currently posting events
     */
    boolean isActive();

    //======================================================================================================================================
    // API
    //======================================================================================================================================

    /**
     * Submit the event for dispatch to appropriate listeners
     *
     * @param event The event to dispatch to listeners
     * @return true if the event was {@link Cancellable} cancelled
     */
    boolean post(Object event);

    /**
     * Submit the event for dispatch to appropriate listeners
     *
     * @param token The event token
     * @param event The event to dispatch to listeners
     * @return true if the event was {@link Cancellable} cancelled
     */
    <T> boolean post(EventToken<T> token, T event);

    /**
     * Register an instance object or a Class, and add listeners for all {@link SubscribeEvent} annotated methods
     * found there.
     *
     * Depending on what is passed as an argument, different listener creation behaviour is performed.
     *
     * <dl>
     *     <dt>Object Instance</dt>
     *     <dd>Scanned for <em>non-static</em> methods annotated with {@link SubscribeEvent} and creates listeners for
     *     each method found.</dd>
     *     <dt>Class Instance</dt>
     *     <dd>Scanned for <em>static</em> methods annotated with {@link SubscribeEvent} and creates listeners for
     *     each method found.</dd>
     * </dl>
     *
     * @param target Either a {@link Class} instance or an arbitrary object, for scanning and event listener creation
     */
    void register(Object target);

    /**
     * Unregister the supplied listener from this EventBus.
     *
     * Removes all listeners from events.
     *
     * NOTE: Consumers can be stored in a variable if unregistration is required for the Consumer.
     *
     * @param target The {@link Object}, {@link Class} or {@link Consumer} to unsubscribe.
     */
    void unregister(Object target);

    <T> void addListener(EventPriority priority, Class<T> eventClass, EventListener listener);

    //======================================================================================================================================
    // Helpers
    //======================================================================================================================================

    default <T> void addListener(Class<T> eventClass, Consumer<T> listener) {
        addListener(EventPriority.NORMAL, false, eventClass, listener);
    }

    default <T> void addListener(EventPriority priority, Class<T> eventClass, Consumer<T> listener) {
        addListener(priority, false, eventClass, listener);
    }

    default <T> void addListener(boolean receiveCancelled, Class<T> eventClass, Consumer<T> listener) {
        addListener(EventPriority.NORMAL, receiveCancelled, eventClass, listener);
    }

    default <T> void addListener(EventPriority priority, boolean receiveCancelled, Class<T> eventClass, Consumer<T> listener) {
        if (Config.VALIDATE_ON_REGISTER)
            RegistrationValidators.get().forEach(it -> it.validate(eventClass));

        addListener(priority, Filters.passCancelled(receiveCancelled), eventClass, listener);
    }

    default <T> void addListener(EventPriority priority, Predicate<? super T> filter, Class<T> eventClass, Consumer<T> listener) {
        addListener(priority, eventClass, NamedEventListener.of(new FilteringEventListener<>(filter, listener), listener.getClass()::getName));
    }

}
