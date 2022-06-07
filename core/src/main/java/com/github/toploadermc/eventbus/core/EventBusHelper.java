/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package com.github.toploadermc.eventbus.core;

import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.github.toploadermc.eventbus.core.util.Types;

public class EventBusHelper {

    private final static Map<Class<?>, ListenerList> listeners = new IdentityHashMap<>();
    private static ReadWriteLock lock = new ReentrantReadWriteLock(true);

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
        Lock readLock = lock.readLock();

        // to read the listener list, let's take the read lock
        readLock.lock();
        ListenerList listenerList = listeners.get(eventClass);
        readLock.unlock();

        // if there's no entry, we'll end up here
        if (listenerList == null) {
            // Let's pre-compute our new listener list value. This will possibly call parents' listener list
            // evaluations. as such, we need to make sure we don't hold a lock when we do this, otherwise
            // we could conflict with the class init global lock that is implicitly present
            listenerList = computeListenerList(eventClass, fromInstanceCall);
            // having computed a listener list, we'll grab the write lock.
            // We'll also take the read lock, so we're very clear we have _both_ locks here.
            Lock writeLock = lock.writeLock();
            writeLock.lock();
            readLock.lock();
            // insert our computed value if no existing value is present
            listeners.putIfAbsent(eventClass, listenerList);
            // get whatever value got stored in the list
            listenerList = listeners.get(eventClass);
            // and unlock, and we're done
            readLock.unlock();
            writeLock.unlock();
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
        lock = new ReentrantReadWriteLock(true);
    }

}
