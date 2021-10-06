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
