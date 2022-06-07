/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic;

import java.util.List;

import com.github.toploadermc.eventbus.core.AbstractTest;
import com.github.toploadermc.eventbus.generic.bus.GenericEventBus;
import com.github.toploadermc.eventbus.generic.events.GenericEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenericEventBusTest extends AbstractTest {

    boolean genericEventHandled = false;

    @Test
    public void testGenericListener() { //TODO: FIX THIS MESS
        GenericEventBus bus = GenericEventBus.of(bus());
        bus.addGenericListener(GenericEvent.class, List.class, this::handleGenericEvent);

        bus.post(new GenericEvent<>((Class<List<String>>) (Class<?>) List.class));

        Assertions.assertTrue(genericEventHandled);
    }

    @Test
    public void testGenericListenerRegisteredIncorrectly() {
        GenericEventBus bus = GenericEventBus.of(bus());
        Assertions.assertThrows(IllegalArgumentException.class, () -> bus.addListener(GenericEvent.class, this::handleGenericEvent));
    }

    private void handleGenericEvent(GenericEvent<List<Boolean>> evt) {
        genericEventHandled = true;
    }

}
