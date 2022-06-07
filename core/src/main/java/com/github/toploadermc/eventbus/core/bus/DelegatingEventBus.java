/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.bus;

import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.core.listener.EventListener;

public class DelegatingEventBus implements EventBus {

    private final EventBus bus;

    protected DelegatingEventBus(EventBus bus) {
        this.bus = bus;
    }

    @Override public void start() {
        bus.start();
    }

    @Override public void shutdown() {
        bus.shutdown();
    }

    @Override public boolean isActive() {
        return bus.isActive();
    }

    @Override public boolean post(Object event) {
        return bus.post(event);
    }

    @Override public void register(Object target) {
        bus.register(target);
    }

    @Override public void unregister(Object target) {
        bus.unregister(target);
    }

    @Override public <T> void addListener(EventPriority priority, Class<T> eventClass, EventListener listener) {
        bus.addListener(priority, eventClass, listener);
    }

}
