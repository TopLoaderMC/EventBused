/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import com.github.toploadermc.eventbus.core.util.Property;

public class Config {

    //TODO: Migrate these to BusBuilder configuration

    public static boolean NAMED_LISTENERS             = Property.of("eventbus.nameListeners", "false");

    public static boolean VALIDATE_ON_REGISTER        = Property.of("eventbus.validateOnRegister", "true");

    public static boolean CHECK_TYPE_ON_REGISTER      = Property.of("eventbus.checkTypesOnRegister", "true");
    public static boolean CHECK_TYPE_ON_DISPATCH      = Property.of("eventbus.checkTypesOnDispatch", "false");

}
