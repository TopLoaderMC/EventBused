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

import com.github.toploadermc.eventbus.core.util.Property;

public class Config {

    //TODO: Migrate these to BusBuilder configuration

    public static boolean NAMED_LISTENERS             = Property.of("eventbus.nameListeners", "false");

    public static boolean VALIDATE_ON_REGISTER        = Property.of("eventbus.validateOnRegister", "true");

    public static boolean CHECK_TYPE_ON_REGISTER      = Property.of("eventbus.checkTypesOnRegister", "true");
    public static boolean CHECK_TYPE_ON_DISPATCH      = Property.of("eventbus.checkTypesOnDispatch", "false");

    public static boolean CHECK_GENERIC_REGISTERS     = Property.of("eventbus.checkIsGenericOnRegister", "true");
    public static boolean CHECK_NON_GENERIC_REGISTERS = Property.of("eventbus.checkIsNonGenericOnRegister", "true");

}
