/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.typed.bus;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.generic.bus.GenericEventBus;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.generic.event.Generic;
import com.github.toploadermc.eventbus.core.listener.FilteringEventListener;
import com.github.toploadermc.eventbus.generic.util.Filters;
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
