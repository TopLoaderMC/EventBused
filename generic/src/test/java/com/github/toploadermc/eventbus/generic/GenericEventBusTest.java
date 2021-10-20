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

        bus.post(new GenericEvent<>((Class<List<String>>) (Object) List.class));

        Assertions.assertTrue(genericEventHandled);
    }

    @Test
    public void testGenericListenerRegisteredIncorrectly() {
        GenericEventBus bus = GenericEventBus.of(bus());
        Assertions.assertThrows(IllegalArgumentException.class, () -> bus.addListener(GenericEvent.class, this::handleGenericEvent));
    }

    private void handleGenericEvent(GenericEvent<List<String>> evt) {
        genericEventHandled = true;
    }

}
