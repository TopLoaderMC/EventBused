/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.event;

import com.github.toploadermc.eventbus.core.EventBusHelper;
import com.github.toploadermc.eventbus.core.ListenerList;

/**
 * Standard Base Type for Events
 *
 * Note: whilst it's possible to remove this requirement, it will prevent the ModLauncher Transformers from targeting your event
 */
public interface Event {

    /**
     * Overridden by Transformers to return a static field
     */
    default ListenerList getListenerList() {
        return EventBusHelper.getListenerList(this.getClass());
    }

}
