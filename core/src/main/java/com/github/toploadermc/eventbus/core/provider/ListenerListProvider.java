/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.provider;

import com.github.toploadermc.eventbus.core.ListenerList;

public interface ListenerListProvider {

    ListenerList provide(Object event);

}
