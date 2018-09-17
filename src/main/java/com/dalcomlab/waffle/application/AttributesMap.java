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


package com.dalcomlab.waffle.application;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class AttributesMap {

    private final AtomicReference<ConcurrentMap<String, Object>> map = new AtomicReference<>();

    /**
     *
     */
    public AttributesMap() {

    }

    /**
     * @return
     */
    private ConcurrentMap<String, Object> map() {
        return map.get();
    }

    /**
     * @return
     */
    private ConcurrentMap<String, Object> ensureMap() {
        while (true) {
            ConcurrentMap<String, Object> map = map();
            if (map != null)
                return map;
            map = new ConcurrentHashMap<>();
            if (this.map.compareAndSet(null, map))
                return map;
        }
    }

    /**
     * @param name
     */
    public Object removeAttribute(String name) {
        Map<String, Object> map = map();
        if (map != null) {
            return map.remove(name);
        }
        return null;
    }

    /**
     * @param name
     * @param attribute
     */
    public Object setAttribute(String name, Object attribute) {
        if (attribute == null) {
            return removeAttribute(name);
        }

        return ensureMap().put(name, attribute);
    }

    /**
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        Map<String, Object> map = map();
        if (map != null) {
            return map.get(name);
        }
        return null;
    }

    /**
     * @param name
     * @return
     */
    public boolean containsAttribute(String name) {
        Map<String, Object> map = map();
        if (map != null) {
            return map.containsKey(name);
        }
        return false;
    }

    /**
     * @return
     */
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(getAttributeNameSet());
    }

    /**
     * @return
     */
    public Set<String> getAttributeNameSet() {
        return keySet();
    }

    /**
     *
     */
    public void removeAll() {
        Map<String, Object> map = map();
        if (map != null) {
            map.clear();
        }
    }

    /**
     * @return
     */
    public int size() {
        Map<String, Object> map = map();
        if (map != null) {
            return map.size();
        }
        return 0;
    }

    /**
     * @return
     */
    public Set<String> keySet() {
        Map<String, Object> map = map();
        if (map != null) {
            return map.keySet();
        }
        return Collections.<String>emptySet();
    }

}
