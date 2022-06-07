/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.benchmark.subscribers;

import com.github.toploadermc.eventbus.benchmark.events.CancellableEvent;
import com.github.toploadermc.eventbus.benchmark.events.EventWithData;
import com.github.toploadermc.eventbus.benchmark.events.ResultEvent;
import com.github.toploadermc.eventbus.core.event.SubscribeEvent;

public class SubscriberStatic {

    @SubscribeEvent
    public static void onCancellableEvent(CancellableEvent event) { }

    @SubscribeEvent
    public static void onResultEvent(ResultEvent event) {}

    @SubscribeEvent
    public static void onSimpleEvent(EventWithData event) {}
}
