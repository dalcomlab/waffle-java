/*
 * Copyright WAFFLE. 2018
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.dalcomlab.waffle.session;

import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public interface Session {

    String id();

    /**
     * Put some data in a session
     *
     * @param key  the key for the data
     * @param obj  the data
     * @return a reference to this, so the API can be used fluently
     */
    Session put(String key, Object obj);

    /**
     * Get some data from the session
     *
     * @param key  the key of the data
     * @return  the data
     */
    <T> T get(String key);

    /**
     * Remove some data from the session
     *
     * @param key  the key of the data
     * @return  the data that was there or null if none there
     */
    <T> T remove(String key);

    /**
     * @return the session data as a map
     */
    Map<String, Object> data();

    /**
     * @return true if the session has data
     */
    boolean isEmpty();

    /**
     * @return the time the session was last accessed
     */
    long lastAccessed();

    /**
     * Destroy the session
     */
    void destroy();

    /**
     * @return has the session been destroyed?
     */
    boolean isDestroyed();

    /**
     * @return the amount of time in ms, after which the session will expire, if not accessed.
     */
    long timeout();

    /**
     * Mark the session as being accessed.
     */
    void setAccessed();

    /**
     * The short representation of the session to be added to the session cookie. By default is the session id.
     *
     * @return short representation string.
     */
    default String value() {
        return id();
    }
}
