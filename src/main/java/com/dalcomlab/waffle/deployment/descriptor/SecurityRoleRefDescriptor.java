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
public class SecurityRoleRefDescriptor implements Descriptor {
    private String name = null;
    private String link = null;

    /**
     *
     */
    public SecurityRoleRefDescriptor() {
       initialize();
    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        name = null;
        link = null;
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
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getLink() {
        return this.link;
    }

    /**
     *
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

}
