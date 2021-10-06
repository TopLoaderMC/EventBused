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
