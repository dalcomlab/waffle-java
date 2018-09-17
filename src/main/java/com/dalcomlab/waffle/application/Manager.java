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

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public abstract class Manager {

    protected ApplicationContext context;

    /**
     *
     * @param context
     */
    Manager(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Initialize the manager
     */
    public void initialize() {

    }

    /**
     * Destroy the manager
     */
    public void destroy() {

    }

    /**
     * Returns the context that this servlet manager belongs to
     *
     * @return
     */
    public ApplicationContext getContext() {
        return context;
    }

}
