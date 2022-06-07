/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import java.util.concurrent.atomic.AtomicBoolean;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.events.OtherEvent;
import com.github.toploadermc.eventbus.core.events.hierarchy.ChildEvent;
import com.github.toploadermc.eventbus.core.events.hierarchy.GrandParentEvent;
import com.github.toploadermc.eventbus.core.events.hierarchy.ParentEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HierarchyTest extends AbstractTest {

    @Test
    public void testUnrelated() {
        assertHierarchy(new OtherEvent(), false, false, false);
    }

    @Test
    public void testGrandParent() {
        assertHierarchy(new GrandParentEvent(), true, false, false);
    }

    @Test
    public void testParent() {
        assertHierarchy(new ParentEvent(), true, true, false);
    }

    @Test
    public void testChild() {
        assertHierarchy(new ChildEvent(), true, true, true);
    }

    private <T> void assertHierarchy(T event, boolean shouldGrandParent, boolean shouldParent, boolean shouldChild) {
        EventBus bus = bus();

        AtomicBoolean didGrandParent = addListener(bus, GrandParentEvent.class);
        AtomicBoolean didParent = addListener(bus, ParentEvent.class);
        AtomicBoolean didChild = addListener(bus, ChildEvent.class);

        bus.post(event);

        Assertions.assertAll(
            () -> Assertions.assertEquals(shouldGrandParent, didGrandParent.get(), "GrandParent"),
            () -> Assertions.assertEquals(shouldParent,      didParent.get(), "Parent"),
            () -> Assertions.assertEquals(shouldChild,       didChild.get(), "Child")
        );
    }

    private <T> AtomicBoolean addListener(EventBus bus, Class<T> clazz) {
        AtomicBoolean fired = new AtomicBoolean(false);
        bus.addListener(clazz, (e) -> fired.set(true));
        return fired;
    }

}
