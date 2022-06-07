/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.handlers;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.github.toploadermc.eventbus.core.bus.EventBus;
import com.github.toploadermc.eventbus.core.listener.EventListener;
import com.github.toploadermc.eventbus.core.logging.Loggers;
import com.github.toploadermc.eventbus.core.logging.Markers;

public class LoggingExceptionHandler implements EventExceptionHandler {

    public static final EventExceptionHandler INSTANCE = new LoggingExceptionHandler();

    @Override public void handleException(EventBus bus, Object event, EventListener[] listeners, int index, Throwable throwable) {
        Loggers.EVENT_BUS.error(Markers.EVENTBUS, format(index, listeners, throwable));

        bus.shutdown();
    }

    private static String format(int index, EventListener[] listeners, Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        builder.
            append("Exception caught during firing event: ").append(throwable.getMessage()).append('\n').
            append("\tIndex: ").append(index).append('\n').
            append("\tListeners:\n");

        for (int x = 0; x < listeners.length; x++) {
            builder.append("\t\t").append(x).append(": ").append(listeners[x]).append('\n');
        }

        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        builder.append(sw.getBuffer());

        return builder.toString();
    }

}
