/*
 * Minecraft Forge
 * Copyright (c) 2016-2021.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.github.toploadermc.eventbus.generic.util;

import com.github.toploadermc.eventbus.generic.event.Generic;
import java.util.function.Predicate;

public class Filters extends com.github.toploadermc.eventbus.core.util.Filters {

    public static <T extends Generic<? extends F>, F> Predicate<T> passGenericFilter(Class<F> type) {
        return e -> e.getGenericType() == type;
    }

}
