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
