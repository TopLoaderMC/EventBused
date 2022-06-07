/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.provider;

import com.github.toploadermc.eventbus.core.EventBusHelper;
import com.github.toploadermc.eventbus.core.ListenerList;

public class EBHProvider implements ListenerListProvider {

    public static final ListenerListProvider INSTANCE = new EBHProvider();

    @Override public ListenerList provide(Object event) {
        return EventBusHelper.getListenerList(event.getClass());
    }
}
