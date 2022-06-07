/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic.util;

import com.github.toploadermc.eventbus.generic.event.Generic;
import java.util.function.Predicate;

public class Filters extends com.github.toploadermc.eventbus.core.util.Filters {

    public static <T extends Generic<? extends F>, F> Predicate<T> passGenericFilter(Class<F> type) {
        return e -> e.getGenericType() == type;
    }

}
