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
package com.github.toploadermc.eventbus.generic.filters;

import com.github.toploadermc.eventbus.generic.event.Generic;
import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import com.github.toploadermc.eventbus.core.filters.TypedChainFilter;
import com.github.toploadermc.eventbus.core.util.Types;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.function.Predicate;

public class GenericEventFilter extends TypedChainFilter<Generic<?>> {

    /**
     * Don't you just love Generics in Java, We need a double cast here for this to work...
     */
    @SuppressWarnings("unchecked")
    private static final Class<Generic<?>> TYPE = (Class<Generic<?>>) (Class<?>) Generic.class;

    public GenericEventFilter() {
        super(TYPE);
    }

    @Override protected Predicate<Generic<?>> createFilter(Class<? extends Generic<?>> eventType, Object target, Method method, SubscribeEvent annotation) {
        Type filter = Types.generateFilter(method.getGenericParameterTypes()[0]);
        return it -> it.getGenericType() == filter;
    }

}
