/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.bus.EventToken;
import com.github.toploadermc.eventbus.core.events.BasicEvent;
import com.github.toploadermc.eventbus.core.events.OtherEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTokenTest extends AbstractTest {

    @Test
    public void postReceive() {
        EventBus bus = bus();
        AtomicInteger called = new AtomicInteger(0);
        EventToken<BasicEvent> basic = EventToken.of(BasicEvent.class);
        bus.addListener(BasicEvent.class, (event) -> called.getAndIncrement());

        // Should start at 0
        assertEquals(0, called.get());

        // Should be at 1 after a post without the token
        bus.post(new BasicEvent());
        assertEquals(1, called.get());

        // Should be at 2 after a post using the token
        bus.post(basic, new BasicEvent());
        assertEquals(2, called.get());

        // Should be at 3 after a post using the token
        basic.post(bus, new BasicEvent());
        assertEquals(3, called.get());

        // Should be at 3 after a post of unrelated event
        bus.post(new OtherEvent());
        assertEquals(3, called.get());
    }

    public static class ExtendedEvent extends BasicEvent {}

    @Test
    public void throwsOnClassMismatch() {
        EventBus bus = bus();
        EventToken<BasicEvent> basic = EventToken.of(BasicEvent.class);

        // Posting use the same class should not throw
        Assertions.assertDoesNotThrow(() -> basic.post(bus, new BasicEvent()));

        // Posting use a different class should throw
        Assertions.assertThrows(IllegalArgumentException.class, () -> basic.post(bus, new ExtendedEvent()));
    }

}
