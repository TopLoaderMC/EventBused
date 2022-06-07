/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.validators;

public interface RegistrationValidator {

    void validate(Class<?> eventClass) throws IllegalArgumentException;

}
