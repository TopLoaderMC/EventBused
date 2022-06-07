/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.event;

public interface HasResult {

    enum Result {
        DENY,
        DEFAULT,
        ALLOW
    }

    /**
     * Sets the result value for this event.
     *
     * The functionality of setting the result is defined on a per-event bases.
     *
     * @param value The new result
     */
    void setResult(Result value);

    /**
     * Returns the value set as the result of this event
     */
    Result getResult();

}
