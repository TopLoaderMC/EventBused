/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.benchmark.events;

import com.github.toploadermc.eventbus.core.event.Event;

public class EventWithData implements Event {

    private final String data;
    private final int foo;
    private final boolean bar;

    public EventWithData(String data, int foo, boolean bar)
    {
        this.data = data;
        this.foo = foo;
        this.bar = bar;
    }

    public int getFoo()
    {
        return foo;
    }

    public String getData()
    {
        return data;
    }

    public boolean isBar()
    {
        return bar;
    }

}
