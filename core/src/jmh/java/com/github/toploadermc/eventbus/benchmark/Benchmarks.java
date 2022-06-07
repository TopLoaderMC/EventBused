/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.benchmark;

import com.github.toploadermc.eventbus.benchmark.events.CancellableEvent;
import com.github.toploadermc.eventbus.benchmark.events.EventWithData;
import com.github.toploadermc.eventbus.benchmark.events.ResultEvent;
import com.github.toploadermc.eventbus.benchmark.subscribers.SubscriberDynamic;
import com.github.toploadermc.eventbus.benchmark.subscribers.SubscriberLambda;
import com.github.toploadermc.eventbus.benchmark.subscribers.SubscriberStatic;
import com.github.toploadermc.eventbus.core.bus.BusBuilder;
import com.github.toploadermc.eventbus.core.bus.EventBus;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class Benchmarks {

    private EventBus dynamicSubscriberBus;
    private EventBus lambdaSubscriberBus;
    private EventBus staticSubscriberBus;

    @Setup
    public void setup() {
        dynamicSubscriberBus = BusBuilder.builder().build();
        lambdaSubscriberBus  = BusBuilder.builder().build();
        staticSubscriberBus  = BusBuilder.builder().build();

        dynamicSubscriberBus.register(new SubscriberDynamic());
        SubscriberLambda.register(lambdaSubscriberBus);
        staticSubscriberBus.register(SubscriberStatic.class);
    }

    @Benchmark
    public int testDynamic() {
        postAll(dynamicSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testLambda() {
        postAll(lambdaSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testStatic() {
        postAll(staticSubscriberBus);
        return 0;
    }

    private static void postAll(EventBus bus) {
        bus.post(new CancellableEvent());
        bus.post(new ResultEvent());
        bus.post(new EventWithData("Foo", 5, true)); //Some example data
    }

}
