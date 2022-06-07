/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.register;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import com.github.toploadermc.eventbus.core.util.Filters;
import com.github.toploadermc.eventbus.core.util.Methods;
import com.github.toploadermc.eventbus.core.util.Optionals;
import com.github.toploadermc.eventbus.core.util.Types;

public class Registration {

    public static Stream<MethodPair> findStaticSubscriptions(final Class<?> clazz) {
        return Arrays
            .stream(clazz.getMethods())
            .filter(Methods::isStatic)
            .filter(Filters.hasAnnotation(SubscribeEvent.class))
            .map(MethodPair::of);
    }

    public static Stream<MethodPair> findSubscriptions(final Object object) {
        final Class<?> objectClass = object.getClass();
        final Set<Class<?>> classes = Types.linked(objectClass);

        return Arrays
            .stream(objectClass.getMethods())
            .filter(Methods::isNotStatic)
            .flatMap(m -> findAnnotatedAndActual(classes.stream(), m));
    }

    private static Stream<MethodPair> findAnnotatedAndActual(Stream<Class<?>> classes, Method method) {
        return classes
            .map(c -> Methods.findDeclared(c, method))
            .flatMap(Optionals::stream)
            .filter(Filters.hasAnnotation(SubscribeEvent.class))
            .map(m -> MethodPair.of(method, m))
            .limit(1);
    }
}
