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
