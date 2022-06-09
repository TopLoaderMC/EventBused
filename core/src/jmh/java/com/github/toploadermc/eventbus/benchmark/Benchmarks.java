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
import com.github.toploadermc.eventbus.core.bus.EventToken;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class Benchmarks {

    private EventBus staticSubscriberBus;
    private EventBus dynamicSubscriberBus;
    private EventBus lambdaSubscriberBus;
    private EventBus combinedSubscriberBus;

    private static final EventToken<CancellableEvent> cancellable   = EventToken.of(CancellableEvent.class);
    private static final EventToken<ResultEvent>      result        = EventToken.of(ResultEvent.class);
    private static final EventToken<EventWithData>    eventWithData = EventToken.of(EventWithData.class);

    @Setup
    public void setup() {
        staticSubscriberBus   = BusBuilder.builder().build();
        dynamicSubscriberBus  = BusBuilder.builder().build();
        lambdaSubscriberBus   = BusBuilder.builder().build();
        combinedSubscriberBus = BusBuilder.builder().build();

        staticSubscriberBus.register(SubscriberStatic.class);
        dynamicSubscriberBus.register(new SubscriberDynamic());
        SubscriberLambda.register(lambdaSubscriberBus);

        combinedSubscriberBus.register(SubscriberStatic.class);
        combinedSubscriberBus.register(new SubscriberDynamic());
        SubscriberLambda.register(combinedSubscriberBus);
    }

    @Benchmark
    public int testStatic() {
        postAll(staticSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testStaticToken() {
        postAllViaTokens(staticSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testDynamic() {
        postAll(dynamicSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testDynamicToken() {
        postAllViaTokens(dynamicSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testLambda() {
        postAll(lambdaSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testLambdaToken() {
        postAllViaTokens(lambdaSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testCombined() {
        postAll(combinedSubscriberBus);
        return 0;
    }

    @Benchmark
    public int testCombinedToken() {
        postAllViaTokens(combinedSubscriberBus);
        return 0;
    }

    private static void postAll(EventBus bus) {
        bus.post(new CancellableEvent());
        bus.post(new ResultEvent());
        bus.post(new EventWithData("Foo", 5, true)); //Some example data
    }

    private static void postAllViaTokens(EventBus bus) {
        cancellable.post(bus, new CancellableEvent());
        result.post(bus, new ResultEvent());
        eventWithData.post(bus, new EventWithData("Foo", 5, true));
    }

}
