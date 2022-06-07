/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.handlers;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.listener.EventListener;

public interface EventExceptionHandler {

    /**
     * Fired when a EventListener throws an exception for the specified event on the event bus.
     * After this function returns, the original Throwable will be propagated upwards.
     *
     * @param bus The bus the event is being fired on
     * @param event The event that is being fired
     * @param listeners All listeners that are listening for this event, in order
     * @param index Index for the current listener being fired.
     * @param throwable The throwable being thrown
     */
    void handleException(EventBus bus, Object event, EventListener[] listeners, int index, Throwable throwable);

}
