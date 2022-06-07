/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
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
