/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.generic.validators;

import com.github.toploadermc.eventbus.core.validators.RegistrationValidator;
import com.github.toploadermc.eventbus.generic.GenericConfig;
import com.github.toploadermc.eventbus.generic.util.Types;

public class GenericValidator implements RegistrationValidator {

    @Override public void validate(Class<?> eventClass) throws IllegalArgumentException {
        if (GenericConfig.CHECK_NON_GENERIC_REGISTERS && Types.isGeneric(eventClass))
            throw new IllegalArgumentException("Cannot register a generic event listener with addListener, use addGenericListener");
    }

}
