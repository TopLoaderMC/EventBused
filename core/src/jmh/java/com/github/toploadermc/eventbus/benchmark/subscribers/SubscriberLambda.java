/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.benchmark.subscribers;

import com.github.toploadermc.eventbus.benchmark.events.CancellableEvent;
import com.github.toploadermc.eventbus.benchmark.events.EventWithData;
import com.github.toploadermc.eventbus.benchmark.events.ResultEvent;
import com.github.toploadermc.eventbus.core.bus.EventBus;

public class SubscriberLambda {

    public static void register(EventBus bus) {
        bus.addListener(CancellableEvent.class, SubscriberLambda::onCancellableEvent);
        bus.addListener(ResultEvent.class, SubscriberLambda::onResultEvent);
        bus.addListener(EventWithData.class, SubscriberLambda::onSimpleEvent);
    }

    public static void onCancellableEvent(CancellableEvent event) { }

    public static void onResultEvent(ResultEvent event) {}

    public static void onSimpleEvent(EventWithData event) {}
}
