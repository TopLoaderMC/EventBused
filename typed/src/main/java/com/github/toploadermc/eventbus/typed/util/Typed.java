/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
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
