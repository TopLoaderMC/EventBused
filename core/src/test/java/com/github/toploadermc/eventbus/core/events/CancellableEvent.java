/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.events;

import com.github.toploadermc.eventbus.core.event.Cancellable;
import com.github.toploadermc.eventbus.core.event.Event;

public class CancellableEvent implements Cancellable, Event {

    private boolean isCanceled;

    public CancellableEvent(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    @Override public void setCancelled(boolean cancel) {
        this.isCanceled = cancel;
    }

    @Override public boolean isCancelled() {
        return isCanceled;
    }
}
