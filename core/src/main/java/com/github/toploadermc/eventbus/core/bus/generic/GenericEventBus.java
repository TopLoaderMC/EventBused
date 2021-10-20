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
package com.github.toploadermc.eventbus.core.bus.generic;

import java.util.function.Consumer;

import com.github.toploadermc.eventbus.core.Config;
import com.github.toploadermc.eventbus.core.bus.DelegatingEventBus;
import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.core.event.Generic;
import com.github.toploadermc.eventbus.core.util.Filters;
import com.github.toploadermc.eventbus.core.util.Types;

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
        if (Config.CHECK_GENERIC_REGISTERS && !Types.isGeneric(eventClass))
            throw new IllegalArgumentException("Cannot register a non generic event listener with addGenericListener, use addListener");

        addListener(priority, Filters.passGenericFilter(genericFilter).and(Filters.passCancelled(receiveCancelled)), eventClass, listener);
    }
}
