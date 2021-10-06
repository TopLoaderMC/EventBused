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
