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
package com.github.toploadermc.eventbus.core.bus;

import com.github.toploadermc.eventbus.core.StandardEventBus;
import com.github.toploadermc.eventbus.core.event.Event;
import com.github.toploadermc.eventbus.core.handlers.EventExceptionHandler;
import com.github.toploadermc.eventbus.core.handlers.LoggingExceptionHandler;
import com.github.toploadermc.eventbus.core.listener.EventListenerProvider;
import com.github.toploadermc.eventbus.core.listener.InvokingEventListener;
import com.github.toploadermc.eventbus.core.provider.DefaultProvider;
import com.github.toploadermc.eventbus.core.provider.ListenerListProvider;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BusBuilder {

    public static BusBuilder builder() {
        return new BusBuilder();
    }

    //======================================================================================================================================

    private boolean startShutdown = false;

    public boolean startsShutdown() {
        return this.startShutdown;
    }

    public BusBuilder setStartsShutdown(boolean value) {
        this.startShutdown = value;
        return this;
    }

    //======================================================================================================================================

    private EventExceptionHandler exceptionHandler = LoggingExceptionHandler.INSTANCE;

    public EventExceptionHandler getExceptionHandler() {
        return this.exceptionHandler;
    }

    public BusBuilder setExceptionHandler(EventExceptionHandler handler) {
        this.exceptionHandler = handler;
        return this;
    }

    //======================================================================================================================================

    private EventListenerProvider listenerProvider = InvokingEventListener::new;

    public EventListenerProvider getEventListenerProvider() {
        return this.listenerProvider;
    }

    public BusBuilder setEventListenerProvider(EventListenerProvider provider) {
        this.listenerProvider = provider;
        return this;
    }

    //======================================================================================================================================

    private ListenerListProvider listProvider = DefaultProvider.INSTANCE;

    public ListenerListProvider getListenerListProvider() {
        return this.listProvider;
    }

    public BusBuilder setListenerListProvider(ListenerListProvider provider) {
        this.listProvider = provider;
        return this;
    }

    //======================================================================================================================================

    private @Nullable Class<?> markerType = Event.class;

    public @Nullable Class<?> getMarkerType() {
        return this.markerType;
    }

    public BusBuilder setMarkerType(@Nullable Class<?> markerType) {
        if (markerType != null && !markerType.isInterface())
            throw new IllegalArgumentException("Cannot specify a non interface marker type");

        this.markerType = markerType;
        return this;
    }

    //======================================================================================================================================

    public EventBus build() {
        return new StandardEventBus(this);
    }
}
