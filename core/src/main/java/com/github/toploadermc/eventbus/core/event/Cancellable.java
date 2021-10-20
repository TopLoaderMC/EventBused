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
package com.github.toploadermc.eventbus.core.event;

public interface Cancellable {

    /**
     * Sets the cancel state of this event.
     *
     * The functionality of setting the canceled state is defined on a per-event bases.
     *
     * @param cancel The new canceled value
     */
    void setCancelled(boolean cancel);

    /**
     * Determine if this event is canceled and should stop executing.
     * @return The current canceled state
     */
    boolean isCancelled();

}
