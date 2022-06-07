/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.event.SubscribeEvent;
import com.github.toploadermc.eventbus.core.events.BasicEvent;
import com.github.toploadermc.eventbus.core.events.CancellableEvent;
import com.github.toploadermc.eventbus.core.events.OtherEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstanceRegistrationTest extends AbstractTest {

    private final AtomicInteger called            = new AtomicInteger(0);
    private final AtomicInteger skipCancelable    = new AtomicInteger(0);
    private final AtomicInteger processCancelable = new AtomicInteger(0);

    @Test
    public void postReceive() {
        EventBus bus = bus();
        InstanceRegistrationTest instance = instance();

        // Should start at 0
        assertEquals(0, instance.called.get());

        // Should still be at 0 after a post with no listener
        bus.post(new BasicEvent());
        assertEquals(0, instance.called.get());

        // Register
        bus.register(instance);

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, instance.called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, instance.called.get());

        // Should be at 2 after a post of unrelated event
        bus.post(new OtherEvent());
        assertEquals(2, instance.called.get());
    }

    @Test
    public void postInvalid() {
        EventBus bus = bus();
        InstanceRegistrationTest instance = instance();

        // Should start at 0
        assertEquals(0, instance.called.get());

        // Register
        bus.register(instance);

        // Should be still at 0 after a post of unrelated event
        bus.post(new OtherEvent());
        assertEquals(0, instance.called.get());
    }

    @Test
    public void postCancelable() {
        EventBus bus = bus();
        InstanceRegistrationTest instance = instance();

        // Register for CancelableEvent
        bus.register(instance);

        // Post canceled event
        CancellableEvent event = new CancellableEvent(true);
        boolean result = bus.post(event);

        // Result should be true, Should be 0 and 1 after the initial post
        Assertions.assertAll(
            () -> assertTrue(result),
            () -> assertEquals(0, instance.skipCancelable.get()),
            () -> assertEquals(1, instance.processCancelable.get())
        );

        event = new CancellableEvent(false);
        boolean result2 = bus.post(event);

        // Result should be false, Should be 1 and 2 after the second post
        Assertions.assertAll(
            () -> assertFalse(result2),
            () -> assertEquals(1, instance.skipCancelable.get()),
            () -> assertEquals(2, instance.processCancelable.get())
        );
    }

    @Test
    public void postAndRemove() {
        EventBus bus = bus();
        InstanceRegistrationTest instance = instance();

        // Should start at 0
        assertEquals(0, instance.called.get());

        // Register Listener
        bus.register(instance);

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, instance.called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, instance.called.get());

        // UnRegister Listener
        bus.unregister(instance);

        // Should be at 2 after unregister of listener
        bus.post(new BasicEvent());
        assertEquals(2, instance.called.get());
    }

    private InstanceRegistrationTest instance() {
        return new InstanceRegistrationTest();
    }

    @SubscribeEvent
    public void onBasicEvent(BasicEvent event) {
        called.getAndIncrement();
    }

    @SubscribeEvent
    public void onCancellableEvent(CancellableEvent event) {
        skipCancelable.getAndIncrement();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onCancellableEventAndCancelled(CancellableEvent event) {
        processCancelable.getAndIncrement();
    }

}
