/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.validators;

import com.github.toploadermc.eventbus.core.util.ClassLoaders;
import java.util.ServiceLoader;

public class RegistrationValidators {

    static final ServiceLoader<RegistrationValidator> SERVICE_LOADER = ServiceLoader.load(RegistrationValidator.class, ClassLoaders.get());

    public static Iterable<RegistrationValidator> get() {
        return SERVICE_LOADER;
    }

}
