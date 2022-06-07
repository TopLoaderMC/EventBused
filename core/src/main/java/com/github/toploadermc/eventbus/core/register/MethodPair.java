/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core.register;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;

public class MethodPair {

    private final Method annotated;
    private final Method actual;

    private MethodPair(Method annotated, Method actual) {
        this.annotated = annotated;
        this.actual = actual;
    }

    public Method annotated() {
        return annotated;
    }

    public Method actual() {
        return actual;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodPair)) return false;
        MethodPair that = (MethodPair) o;
        return annotated.equals(that.annotated) && actual.equals(that.actual);
    }

    @Override public int hashCode() {
        return Objects.hash(annotated, actual);
    }

    @Override public String toString() {
        return new StringJoiner(", ", MethodPair.class.getSimpleName() + "[", "]")
            .add("annotated=" + annotated)
            .add("actual=" + actual)
            .toString();
    }

    public static MethodPair of(Method method) {
        return new MethodPair(method, method);
    }

    public static MethodPair of(Method annotated, Method actual) {
        return new MethodPair(annotated, actual);
    }

}
