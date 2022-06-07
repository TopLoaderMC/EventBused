/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.listener;

public interface EventListener {

    void invoke(Object event);

    default Object getKey() {
        return this;
    }

    default String listenerName() {
        return getClass().getName();
    }

}
