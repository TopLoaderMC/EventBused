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
package com.github.toploadermc.eventbus.core.listener;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class FilteringEventListener<T> implements EventListener {

    private final Predicate<? super T> filter;
    private final Consumer<T> listener;

    public FilteringEventListener(Predicate<? super T> filter, Consumer<T> listener) {
        this.filter = filter;
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    @Override public void invoke(Object event) {
        T cast = (T) event;

        if (!filter.test(cast)) return;

        listener.accept(cast);
    }

    @Override public Object getKey() {
        return listener;
    }

    @Override public String listenerName() {
        return listener.toString();
    }

}
