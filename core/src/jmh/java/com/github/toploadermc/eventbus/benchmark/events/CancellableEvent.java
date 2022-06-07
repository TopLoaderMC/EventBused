/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.benchmark.events;

import com.github.toploadermc.eventbus.core.event.Cancellable;
import com.github.toploadermc.eventbus.core.event.Event;

public class CancellableEvent implements Cancellable, Event {

    private boolean isCancelled;

    @Override public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override public boolean isCancelled() {
        return this.isCancelled;
    }
}
