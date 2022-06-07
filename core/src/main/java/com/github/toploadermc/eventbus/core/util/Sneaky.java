/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.util;

public class Sneaky {

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void rethrow(Throwable e) throws E {
        throw (E) e;
    }

}
