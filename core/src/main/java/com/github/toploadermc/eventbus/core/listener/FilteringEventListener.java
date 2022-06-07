/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
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
