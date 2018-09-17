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

package com.dalcomlab.waffle.deployment.descriptor;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class OrderingGroup {

    private Set<String> names = new TreeSet<String>();
    private boolean others = false;


    /**
     * @param name
     */
    public void addName(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalStateException("The empty name is invalid for relative ordering element");
        }

        names.add(name);
    }

    /**
     *
     */
    public void addOthers() {
        others = true;
    }

    /**
     * @return
     */
    public boolean containsOthers() {
        return others;
    }

    /**
     * @return
     */
    public Set<String> getNames() {
        return names;
    }

    /**
     * @param name
     * @return
     */
    public boolean containsName(String name) {
        return names.contains(name);
    }
}


