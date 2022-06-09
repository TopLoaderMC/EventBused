/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.toploadermc.eventbus.core.util.Types;

public class EventBusHelper {

    private final static Map<Class<?>, ListenerList> listeners = new ConcurrentHashMap<>();

    /**
     * Returns a {@link ListenerList} object that contains all listeners
     * that are registered to this event class.
     *
     * This supports abstract classes that cannot be instantiated.
     */
    // TODO: * Note: this is much slower than the instance method {@link Event#getListenerList()}.
    // TODO: * For performance when emitting events, always call that method instead.
    public static ListenerList getListenerList(Class<?> eventClass) {
        return getListenerListInternal(eventClass, false);
    }

    static ListenerList getListenerListInternal(Class<?> eventClass, boolean fromInstanceCall) {
        ListenerList listenerList = listeners.get(eventClass);

        // if there's no entry, we'll end up here
        if (listenerList == null) {
            // Let's pre-compute our new listener list value. This will possibly call parents' listener list
            // evaluations.
            listenerList = computeListenerList(eventClass, fromInstanceCall);

            // insert our computed value if no existing value is present
            listeners.putIfAbsent(eventClass, listenerList);
            // get whatever value got stored in the list
            listenerList = listeners.get(eventClass);
        }

        return listenerList;
    }

    private static ListenerList computeListenerList(Class<?> eventClass, boolean fromInstanceCall) {
        if (Types.isBaseType(eventClass)) return new ListenerList();

        if (fromInstanceCall || Modifier.isAbstract(eventClass.getModifiers())) {
            Class<?> superclass = eventClass.getSuperclass();
            ListenerList parentList = getListenerList(superclass);
            return new ListenerList(parentList);
        }

        //TODO: Event -> getListenerListInternal(c, true) (?)

        return getListenerListInternal(eventClass, true);
    }

    private static void clearAll() {
        listeners.clear();
    }

}
