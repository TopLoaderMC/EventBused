/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.util;

import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

public class Methods {

    public static Optional<Method> findDeclared(Class<?> clazz, Method input) {
        try {
            return Optional.of(clazz.getDeclaredMethod(input.getName(), input.getParameterTypes()));
        } catch (NoSuchMethodException nse) {
            return Optional.empty();
        }
    }

    public static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    public static boolean isNotStatic(Method method) {
        return !isStatic(method);
    }

    public static MethodType asType(Method method) {
        return MethodType.methodType(method.getReturnType(), method.getParameterTypes());
    }

}
