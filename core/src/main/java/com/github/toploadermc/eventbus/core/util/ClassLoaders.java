/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.util;

public class ClassLoaders {

    public static ClassLoader get() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
    }

}
