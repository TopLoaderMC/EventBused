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

public class StaticRegistrationTest extends AbstractTest {

    private static final AtomicInteger called            = new AtomicInteger(0);
    private static final AtomicInteger skipCancelable    = new AtomicInteger(0);
    private static final AtomicInteger processCancelable = new AtomicInteger(0);

    @Test
    public void postReceive() {
        EventBus bus = bus();
        called.set(0);

        // Should start at 0
        assertEquals(0, called.get());

        // Should still be at 0 after a post with no listener
        bus.post(new BasicEvent());
        assertEquals(0, called.get());

        // Register
        bus.register(StaticRegistrationTest.class);

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
        called.set(0);

        // Should start at 0
        assertEquals(0, called.get());

        // Register
        bus.register(StaticRegistrationTest.class);

        // Should be still at 0 after a post of unrelated event
        bus.post(new OtherEvent());
        assertEquals(0, called.get());
    }

    @Test
    public void postCancelable() {
        EventBus bus = bus();
        skipCancelable.set(0);
        processCancelable.set(0);

        // Register for CancelableEvent
        bus.register(StaticRegistrationTest.class);

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
        called.set(0);

        // Should start at 0
        assertEquals(0, called.get());

        // Register Listener
        bus.register(StaticRegistrationTest.class);

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());

        // UnRegister Listener
        bus.unregister(StaticRegistrationTest.class);

        // Should be at 2 after unregister of listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());
    }

    @SubscribeEvent
    public static void onBasicEvent(BasicEvent event) {
        called.getAndIncrement();
    }

    @SubscribeEvent
    public static void onCancellableEvent(CancellableEvent event) {
        skipCancelable.getAndIncrement();
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void onCancellableEventAndCancelled(CancellableEvent event) {
        processCancelable.getAndIncrement();
    }

}
