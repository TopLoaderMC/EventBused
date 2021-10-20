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
package com.github.toploadermc.eventbus.typed.util;

import java.util.function.Consumer;

import com.github.toploadermc.eventbus.core.logging.Markers;
import com.github.toploadermc.eventbus.core.util.Types;
import com.github.toploadermc.eventbus.typed.TypedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.jodah.typetools.TypeResolver;

public class Typed {

    private static final Logger LOGGER = LoggerFactory.getLogger("TypedEventBus");

    @SuppressWarnings("unchecked")
    public static <T> Class<T> of(Consumer<T> consumer) {
        final Class<T> eventClass = (Class<T>) TypeResolver.resolveRawArgument(Consumer.class, consumer.getClass());

        if (eventClass == TypeResolver.Unknown.class) {
            LOGGER.error(Markers.EVENTBUS, "Failed to resolve handler for \"{}\"", consumer.toString());

            throw new IllegalStateException("Failed to resolve consumer event type: " + consumer);
        }

        if (TypedConfig.TYPED_WARN_BASE_TYPE && Types.isBaseType(eventClass)) {
            LOGGER.warn(Markers.EVENTBUS,
                "Attempting to add a Lambda listener with a base type of {}. " +
                    "Are you sure this is what you meant? NOTE: there are complex lambda forms where " +
                    "the generic type information is erased and cannot be recovered at runtime.",
                eventClass.getName()
            );
        }

        return eventClass;
    }

}
