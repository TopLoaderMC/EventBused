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
package com.github.toploadermc.eventbus.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Types {

    public static boolean isBaseType(Class<?> clazz) {
        return clazz == Object.class || clazz.getSuperclass() == null;
    }

    public static Set<Class<?>> linked(final Class<?> clazz) {
        Set<Class<?>> visited = new HashSet<>();
        linked(clazz, visited);
        return visited;
    }

    public static void linked(final Class<?> clazz, final Set<Class<?>> visited) {
        if (isBaseType(clazz)) return;

        // Mark Self as Visited
        if (!visited.add(clazz)) return;

        // Visit Super Class
        linked(clazz.getSuperclass(), visited);

        // Visit Interfaces
        Arrays.stream(clazz.getInterfaces())
            .forEach(iface -> linked(iface, visited));
    }

    public static Type generateFilter(Type type) {
        Type filter = null;

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterized = (ParameterizedType) type;
            filter = parameterized.getActualTypeArguments()[0];
            // Unlikely that nested generics will ever be relevant for event filtering, so discard them
            if (filter instanceof ParameterizedType) filter = parameterized.getRawType();

            // If there's a wildcard filter of Object.class, then remove the filter.
            if (filter instanceof WildcardType) {
                WildcardType wildcard = (WildcardType) filter;
                if (wildcard.getUpperBounds().length == 1 && wildcard.getUpperBounds()[0] == Object.class && wildcard.getLowerBounds().length == 0) {
                    filter = null;
                }
            }
        }

        return filter;
    }

}
