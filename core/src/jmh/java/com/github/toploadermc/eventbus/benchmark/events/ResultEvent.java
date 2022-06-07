/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.benchmark.events;

import com.github.toploadermc.eventbus.core.event.Event;
import com.github.toploadermc.eventbus.core.event.HasResult;

public class ResultEvent implements HasResult, Event {

    private Result result = Result.DEFAULT;

    @Override public void setResult(Result value) {
        this.result = value;
    }

    @Override public Result getResult() {
        return result;
    }
}
