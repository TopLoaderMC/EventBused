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
