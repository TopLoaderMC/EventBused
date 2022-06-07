/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.event;

public interface Cancellable {

    /**
     * Sets the cancel state of this event.
     *
     * The functionality of setting the canceled state is defined on a per-event bases.
     *
     * @param cancel The new canceled value
     */
    void setCancelled(boolean cancel);

    /**
     * Determine if this event is canceled and should stop executing.
     * @return The current canceled state
     */
    boolean isCancelled();

}
