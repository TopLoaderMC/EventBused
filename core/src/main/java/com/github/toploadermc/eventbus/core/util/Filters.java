/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Predicate;

import com.github.toploadermc.eventbus.core.event.Cancellable;

public class Filters {

    public static <T> Predicate<T> passCancelled(boolean receiveCancelled) {
        return e -> receiveCancelled || !(e instanceof Cancellable) || !((Cancellable) e).isCancelled();
    }

    public static Predicate<Method> hasAnnotation(Class<? extends Annotation> annotation) {
        return m -> m.isAnnotationPresent(annotation);
    }

}
