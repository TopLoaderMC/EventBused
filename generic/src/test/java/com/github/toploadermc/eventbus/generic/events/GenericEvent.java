/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic.events;

import java.lang.reflect.Type;

import com.github.toploadermc.eventbus.generic.event.Generic;

public class GenericEvent<T> implements Generic<T> {

    private final Class<T> type;

    public GenericEvent(Class<T> type)
    {
        this.type = type;
    }

    @Override
    public Type getGenericType()
    {
        return type;
    }

}
