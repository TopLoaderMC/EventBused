/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic.bus;

import java.util.function.Consumer;

import com.github.toploadermc.eventbus.core.bus.DelegatingEventBus;
import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.generic.GenericConfig;
import com.github.toploadermc.eventbus.generic.event.Generic;
import com.github.toploadermc.eventbus.generic.util.Filters;
import com.github.toploadermc.eventbus.generic.util.Types;

//TODO: Make Generic a separate artifact + generalise it to remove special casing for it
public class GenericEventBus extends DelegatingEventBus {

    protected GenericEventBus(EventBus bus) {
        super(bus);
    }

    public static GenericEventBus of(EventBus bus) {
        return new GenericEventBus(bus);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(Class<T> eventClass, Class<F> genericFilter, Consumer<T> listener) {
        addGenericListener(EventPriority.NORMAL, false, eventClass, genericFilter, listener);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(EventPriority priority, Class<T> eventClass, Class<F> genericFilter, Consumer<T> listener) {
        addGenericListener(priority, false, eventClass, genericFilter, listener);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(boolean receiveCancelled, Class<T> eventClass, Class<F> genericFilter, Consumer<T> listener) {
        addGenericListener(EventPriority.NORMAL, receiveCancelled, eventClass, genericFilter, listener);
    }

    public <T extends Generic<? extends F>, F> void addGenericListener(EventPriority priority, boolean receiveCancelled, Class<T> eventClass, Class<F> genericFilter, Consumer<T> listener) {
        if (GenericConfig.CHECK_GENERIC_REGISTERS && !Types.isGeneric(eventClass))
            throw new IllegalArgumentException("Cannot register a non generic event listener with addGenericListener, use addListener");

        addListener(priority, Filters.passGenericFilter(genericFilter).and(Filters.passCancelled(receiveCancelled)), eventClass, listener);
    }
}
