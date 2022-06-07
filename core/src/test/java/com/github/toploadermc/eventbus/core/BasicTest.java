/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.event.EventPriority;
import com.github.toploadermc.eventbus.core.events.BasicEvent;
import com.github.toploadermc.eventbus.core.events.CancellableEvent;
import com.github.toploadermc.eventbus.core.events.OtherEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class BasicTest extends AbstractTest {

    @Test
    public void postReceive() {
        EventBus bus = bus();
        AtomicInteger called = new AtomicInteger(0);

        // Should start at 0
        assertEquals(0, called.get());

        // Should still be at 0 after a post with no listener
        bus.post(new BasicEvent());
        assertEquals(0, called.get());

        // Register Listener
        bus.addListener(BasicEvent.class, (event) -> called.getAndIncrement());

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());

        // Should be at 2 after a post of unrelated event
        bus.post(new OtherEvent());
        assertEquals(2, called.get());
    }

    @Test
    public void postInvalid() {
        EventBus bus = bus();

        // Register for BasicEvent
        bus.addListener(BasicEvent.class, (event) -> fail("Called Listener for BasicEvent"));

        // Post other event
        bus.post(new OtherEvent());
    }

    @Test
    public void postCancelable() {
        EventBus bus = bus();
        AtomicInteger skipCancelable    = new AtomicInteger(0);
        AtomicInteger processCancelable = new AtomicInteger(0);

        // Register for CancelableEvent
        bus.addListener(EventPriority.NORMAL, false, CancellableEvent.class, (event) -> skipCancelable.getAndIncrement());
        bus.addListener(EventPriority.NORMAL, true, CancellableEvent.class,  (event) -> processCancelable.getAndIncrement());

        // Post canceled event
        CancellableEvent event = new CancellableEvent(true);
        boolean result = bus.post(event);

        // Result should be true, Should be 0 and 1 after the initial post
        Assertions.assertAll(
            () -> assertTrue(result),
            () -> assertEquals(0, skipCancelable.get()),
            () -> assertEquals(1, processCancelable.get())
        );

        event = new CancellableEvent(false);
        boolean result2 = bus.post(event);

        // Result should be false, Should be 1 and 2 after the second post
        Assertions.assertAll(
            () -> assertFalse(result2),
            () -> assertEquals(1, skipCancelable.get()),
            () -> assertEquals(2, processCancelable.get())
        );
    }

    @Test
    public void postAndRemove() {
        EventBus bus = bus();
        AtomicInteger called = new AtomicInteger(0);

        // Should start at 0
        assertEquals(0, called.get());

        Consumer<BasicEvent> listener = (event) -> called.getAndIncrement();

        // Register Listener
        bus.addListener(BasicEvent.class, listener);

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());

        // UnRegister Listener
        bus.unregister(listener);

        // Should be at 2 after unregister of listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());
    }

    @Test
    public void postShutdown() {
        EventBus bus = bus(b -> b.setStartsShutdown(true));

        // Register for BasicEvent
        bus.addListener(BasicEvent.class, (event) -> fail("Called Listener for Shutdown Bus"));

        // Post other event
        bus.post(new BasicEvent());
    }

    @Test
    public void postAndShutdown() {
        EventBus bus = bus();
        AtomicInteger called = new AtomicInteger(0);

        // Should start at 0
        assertEquals(0, called.get());

        // Register Listener
        bus.addListener(BasicEvent.class, (event) -> called.getAndIncrement());

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());

        // Shutdown bus
        bus.shutdown();

        // Should be at 2 after shutdown
        bus.post(new BasicEvent());
        assertEquals(2, called.get());

        // Restart bus
        bus.start();

        // Should be at 3 after restart
        bus.post(new BasicEvent());
        assertEquals(3, called.get());
    }

    //@Test
    //public void test() {
    //    var a = MethodReferences.getMethod(this::listener);
    //    var b = MethodReferences.getMethod(this::listener);
    //
    //    var logger = Logger.of("Example", Markers.EVENTBUS);
    //
    //    logger.warn("{}", a);
    //    logger.error("{}", b);
    //
    //    System.out.println(Objects.equals(a, b));
    //}
    //
    //public void listener(BasicEvent event) {
    //
    //}
}
