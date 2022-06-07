/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.provider;

import com.github.toploadermc.eventbus.core.EventBusHelper;
import com.github.toploadermc.eventbus.core.ListenerList;
import com.github.toploadermc.eventbus.core.event.Event;

public class DefaultProvider implements ListenerListProvider {

    public static final ListenerListProvider INSTANCE = new DefaultProvider();

    @Override public ListenerList provide(Object event) {
        if (event instanceof Event)
            return ((Event) event).getListenerList();

        return EventBusHelper.getListenerList(event.getClass());
    }
}
