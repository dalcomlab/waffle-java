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

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class OrderingDescriptor implements Descriptor {

    private OrderingGroup before = null;
    private OrderingGroup after = null;

    /**
     *
     */
    public OrderingDescriptor() {

    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {

    }

    /**
     *
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        return true;
    }

    /**
     *
     * @return
     */
    public OrderingGroup getBefore() {
        if (before == null) {
            before = new OrderingGroup();
        }
        return before;
    }

    /**
     *
     * @return
     */
    public OrderingGroup getAfter() {
        if (after == null) {
            after = new OrderingGroup();
        }
        return after;
    }

    /**
     *
     * @return
     */
    public boolean validate() {
        if (after != null && before != null) {
            if (after.containsOthers() && before.containsOthers()) {
                return false;
            }

            for (String name : after.getNames()) {
                if (before.containsName(name)) {
                    return false;
                }
            }
        }
        return true;
    }

}
