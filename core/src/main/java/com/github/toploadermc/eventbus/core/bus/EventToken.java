/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.bus;

import com.github.toploadermc.eventbus.core.EventBusHelper;
import com.github.toploadermc.eventbus.core.ListenerList;

public final class EventToken<T> {

    private final Class<T> clazz;
    private final ListenerList listeners;

    private EventToken(Class<T> clazz, ListenerList listeners) {
        this.clazz = clazz;
        this.listeners = listeners;
    }

    public static <T> EventToken<T> of(Class<T> clazz) {
        var listeners = EventBusHelper.getListenerList(clazz);
        return new EventToken<>(clazz, listeners);
    }

    public Class<T> getEventClass() {
        return clazz;
    }

    public ListenerList get() {
        return listeners;
    }

    public boolean post(EventBus bus, T event) {
        return bus.post(this, event);
    }

}
