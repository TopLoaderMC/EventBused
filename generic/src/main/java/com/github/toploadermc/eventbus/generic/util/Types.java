/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic.util;

import com.github.toploadermc.eventbus.generic.event.Generic;

public class Types extends com.github.toploadermc.eventbus.core.util.Types {

    public static boolean isGeneric(Class<?> clazz) {
        return Generic.class.isAssignableFrom(clazz);
    }

}
