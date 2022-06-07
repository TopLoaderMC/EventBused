/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
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
