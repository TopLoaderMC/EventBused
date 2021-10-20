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
package com.github.toploadermc.eventbus.core.listener;

import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import com.github.toploadermc.eventbus.core.filters.EventFilter;
import com.github.toploadermc.eventbus.core.filters.EventFilters;
import com.github.toploadermc.eventbus.core.util.Filters;
import com.github.toploadermc.eventbus.core.util.Methods;
import com.github.toploadermc.eventbus.core.util.Sneaky;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public class InvokingEventListener implements EventListener {

    private final Object target;
    private final String readable;

    private final Class<?> eventType;
    private final Predicate<Object> filter;

    private final MethodHandle handle;

    public InvokingEventListener(Class<?> eventType, Object target, Method method, SubscribeEvent annotation) throws IllegalAccessException {
        this.target = target;
        this.eventType = eventType;
        this.readable = "Invoking: " + target + " " + method.getName() + Methods.asType(method).toMethodDescriptorString();

        Predicate<Object> filter = Filters.passCancelled(annotation.receiveCanceled());
        for (EventFilter filters : EventFilters.get()) {
            filter = filters.apply(filter, eventType, target, method, annotation);
        }

        this.filter = filter;

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle handle = lookup.unreflect(method);
        this.handle = Methods.isNotStatic(method) ? handle.bindTo(target) : handle;
    }

    @Override public void invoke(Object event) {
        if (!filter.test(event)) return;

        try {
            handle.invoke(eventType.cast(event));
        } catch (Throwable throwable) {
            Sneaky.rethrow(throwable);
        }
    }

    @Override public Object getKey() {
        return target;
    }

    @Override public String toString() {
        return readable;
    }
}
