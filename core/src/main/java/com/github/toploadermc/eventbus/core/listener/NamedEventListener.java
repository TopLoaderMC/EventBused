/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
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
