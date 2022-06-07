/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.filters;

import com.github.toploadermc.eventbus.core.util.ClassLoaders;
import java.util.ServiceLoader;

public class EventFilters {

    static final ServiceLoader<EventFilter> SERVICE_LOADER = ServiceLoader.load(EventFilter.class, ClassLoaders.get());

    public static Iterable<EventFilter> get() {
        return SERVICE_LOADER;
    }

}
