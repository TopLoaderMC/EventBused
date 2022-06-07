/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import java.util.function.Consumer;

import com.github.toploadermc.eventbus.core.bus.BusBuilder;
import com.github.toploadermc.eventbus.core.bus.EventBus;

public class AbstractTest {

    public EventBus bus() {
        return BusBuilder.builder().build();
    }

    public EventBus bus(Consumer<BusBuilder> configure) {
        BusBuilder builder = BusBuilder.builder();
        configure.accept(builder);
        return builder.build();
    }

}
