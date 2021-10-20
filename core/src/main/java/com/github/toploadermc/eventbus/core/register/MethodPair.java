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
