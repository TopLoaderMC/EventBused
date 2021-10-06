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
package com.github.toploadermc.eventbus.typed.bus;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.bus.generic.GenericEventBus;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.core.event.Generic;
import com.github.toploadermc.eventbus.core.listener.FilteringEventListener;
import com.github.toploadermc.eventbus.core.util.Filters;
import com.github.toploadermc.eventbus.typed.util.Typed;

public class TypedEventBus extends GenericEventBus {

    protected TypedEventBus(EventBus bus) {
        super(bus);
    }

    public static TypedEventBus of(EventBus bus) {
        return new TypedEventBus(bus);
    }

    //======================================================================================================================================
    // Basic API
    //======================================================================================================================================

    public <T> void addListener(Consumer<T> listener) {
        addListener(Typed.of(listener), listener);
    }

    public <T> void addListener(EventPriority priority, Consumer<T> listener) {
        addListener(priority, false, Typed.of(listener), listener);
    }

    public <T> void addListener(boolean receiveCancelled, Consumer<T> listener) {
        addListener(EventPriority.NORMAL, receiveCancelled, Typed.of(listener), listener);
    }

    public <T> void addListener(EventPriority priority, boolean receiveCancelled, Consumer<T> listener) {
        addListener(priority, receiveCancelled, Typed.of(listener), listener);
    }

    public <T> void addListener(EventPriority priority, Predicate<T> filter, Consumer<T> listener) {
        addListener(priority, Typed.of(listener), new FilteringEventListener<>(filter, listener));
    }

    //======================================================================================================================================
    // Generic API
    //======================================================================================================================================

    public <T extends Generic<? extends F>, F> void addGenericListener(Class<F> genericFilter, Consumer<T> listener) {
        addGenericListener(EventPriority.NORMAL, false, Typed.of(listener), genericFilter, listener);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(EventPriority priority, Class<F> genericFilter, Consumer<T> listener) {
        addGenericListener(priority, false, Typed.of(listener), genericFilter, listener);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(boolean receiveCancelled, Class<F> genericFilter, Consumer<T> listener) {
        addGenericListener(EventPriority.NORMAL, receiveCancelled, Typed.of(listener), genericFilter, listener);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(EventPriority priority, boolean receiveCancelled, Class<F> genericFilter, Consumer<T> listener) {
        addListener(priority, Filters.passGenericFilter(genericFilter).and(Filters.passCancelled(receiveCancelled)), Typed.of(listener), listener);
    }

}
