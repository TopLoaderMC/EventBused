/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.event;

public interface HasPriority {

    /**
     * Sets the priority for this event.
     *
     * The functionality of setting the priority is defined on a per-event bases.
     *
     * This is called internally during post phase to notify the event of it's current Priority
     *
     * @param value The new result
     */
    void setPriority(EventPriority value);

}
