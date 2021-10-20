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
package com.github.toploadermc.eventbus.typed.bus;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.toploadermc.eventbus.core.AbstractTest;
import com.github.toploadermc.eventbus.core.events.BasicEvent;
import com.github.toploadermc.eventbus.core.events.GenericEvent;
import com.github.toploadermc.eventbus.core.events.OtherEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TypedEventBusTest extends AbstractTest {

    @Test
    public void basicTest() {
        TypedEventBus bus = TypedEventBus.of(bus());
        AtomicInteger called = new AtomicInteger(0);

        // Should start at 0
        assertEquals(0, called.get());

        // Should still be at 0 after a post with no listener
        bus.post(new BasicEvent());
        assertEquals(0, called.get());

        // Register Listener
        bus.addListener((BasicEvent event) -> called.getAndIncrement());

        // Should be at 1 after a post with listener
        bus.post(new BasicEvent());
        assertEquals(1, called.get());

        // Should be at 2 after a second post with listener
        bus.post(new BasicEvent());
        assertEquals(2, called.get());

        // Should be at 2 after a post of an unrelated event
        bus.post(new OtherEvent());
        assertEquals(2, called.get());

    }


    @Test
    public void testGenericListener() {
        TypedEventBus bus = TypedEventBus.of(bus());
        AtomicInteger called = new AtomicInteger(0);

        bus.addGenericListener(List.class, (GenericEvent<List> event) -> called.getAndIncrement());

        bus.post(new GenericEvent<>((Class<List<String>>) (Object) List.class));
        assertEquals(1, called.get());

        bus.post(new GenericEvent<>(String.class));
        assertEquals(1, called.get());
    }

    @Test
    public void postInvalid() {
        TypedEventBus bus = TypedEventBus.of(bus());

        // Register for BasicEvent
        bus.addListener((BasicEvent event) -> fail("Called Listener for BasicEvent"));

        // Post other event
        bus.post(new OtherEvent());
    }

}
