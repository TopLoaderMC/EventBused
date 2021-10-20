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
package com.github.toploadermc.eventbus.core.filters;

import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public abstract class TypedChainFilter<T> implements EventFilter {

    private final Class<T> type;

    public TypedChainFilter(Class<T> type) {
        this.type = type;
    }

    @Override public Predicate<Object> apply(Predicate<Object> current, Class<?> eventType, Object target, Method method, SubscribeEvent annotation) {
        if  (!type.isAssignableFrom(eventType)) return current;

        Predicate<T> filter = createFilter(eventType.asSubclass(type), target, method, annotation);
        return current.and(it -> filter.test(type.cast(it)));
    }

    protected abstract Predicate<T> createFilter(Class<? extends T> eventType, Object target, Method method, SubscribeEvent annotation);

}
