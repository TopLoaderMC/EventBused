/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.event;

import com.github.toploadermc.eventbus.core.listener.EventListener;

/**
 * Different priorities for Event listeners.
 *
 * {@link #NORMAL} is the default level for a listener registered without a priority.
 *
 * @see SubscribeEvent#priority()
 */
public enum EventPriority implements EventListener {

    /**
     * Priority of event listeners, listeners will be sorted with respect to this priority level.
     *
     * Note:
     *   Due to using a ArrayList in the ListenerList,
     *   these need to stay in a contiguous index starting at 0. {Default ordinal}
     */
    HIGHEST, //First to execute
    HIGH,
    NORMAL,
    LOW,
    LOWEST; //Last to execute

    @Override public void invoke(Object event) {
        if (event instanceof HasPriority) ((HasPriority) event).setPriority(this);
    }
}
