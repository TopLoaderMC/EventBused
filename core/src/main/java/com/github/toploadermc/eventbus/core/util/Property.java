/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.util;

public class Property {

    public static boolean of(String property, String defaultValue) {
        return Boolean.parseBoolean(System.getProperty(property, defaultValue));
    }

}
