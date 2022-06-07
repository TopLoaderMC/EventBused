/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.listener.EventListener;

/**
 * Annotation to subscribe a method to an Event
 *
 * This annotation can only be applied to single parameter methods, where the single parameter is any Object so long as
 * it implements the MarkerType for the bus.
 *
 * Use {@link EventBus#register(Object)} to submit either an {@link Object} instance or a {@link Class} to the event bus
 * for scanning to generate callback {@link EventListener} wrappers.
 *
 */
// TODO:  * The Event Bus system generates an ASM wrapper that dispatches to the marked method.
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface SubscribeEvent {

    EventPriority priority() default EventPriority.NORMAL;

    boolean receiveCanceled() default false;

}
