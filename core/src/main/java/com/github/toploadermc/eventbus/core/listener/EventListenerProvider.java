/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.listener;

import java.lang.reflect.Method;

import com.github.toploadermc.eventbus.core.event.SubscribeEvent;

public interface EventListenerProvider {

    EventListener create(Class<?> eventType, Object target, Method method, SubscribeEvent annotation) throws Exception;

}
