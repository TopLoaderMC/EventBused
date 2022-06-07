/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic.event;

import com.github.toploadermc.eventbus.core.event.Event;
import java.lang.reflect.Type;

/**
 * Marks a generic event - one that is able to be filtered based on the supplied Generic type
 *
 * @param <T> The filtering type
 */
public interface Generic<T> extends Event {

    Type getGenericType();

}
