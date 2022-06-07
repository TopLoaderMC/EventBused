/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.filters;

import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public interface EventFilter { //TODO: Additional Support for EventBus#addListener

    Predicate<Object> apply(Predicate<Object> current, Class<?> eventType, Object target, Method method, SubscribeEvent annotation);

}
