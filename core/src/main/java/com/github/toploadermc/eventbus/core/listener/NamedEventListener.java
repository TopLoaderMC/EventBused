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

import java.util.function.Supplier;

import com.github.toploadermc.eventbus.core.Config;

public class NamedEventListener implements EventListener {

    private final EventListener wrap;
    private final String name;

    public NamedEventListener(EventListener wrap, final String name) {
        this.wrap = wrap;
        this.name = name;
    }

    public static EventListener of(EventListener listener, Supplier<String> name) {
        if (!Config.NAMED_LISTENERS) return listener;

        return of(listener, name.get());
    }

    public static NamedEventListener of(EventListener listener, String name) {
        return new NamedEventListener(listener, name);
    }

    @Override public void invoke(final Object event) {
        this.wrap.invoke(event);
    }

    @Override public Object getKey() {
        return this.wrap.getKey();
    }

    @Override
    public String listenerName() {
        return this.name;
    }

}
