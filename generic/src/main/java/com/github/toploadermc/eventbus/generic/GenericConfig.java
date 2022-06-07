/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic;

import com.github.toploadermc.eventbus.core.util.Property;

public class GenericConfig {

    public static boolean CHECK_GENERIC_REGISTERS     = Property.of("eventbus.checkIsGenericOnRegister", "true");
    public static boolean CHECK_NON_GENERIC_REGISTERS = Property.of("eventbus.checkIsNonGenericOnRegister", "true");

}
