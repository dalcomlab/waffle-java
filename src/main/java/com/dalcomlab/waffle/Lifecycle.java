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

package com.dalcomlab.waffle;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public abstract class Lifecycle {

    /**
     * Lifecycle State.
     */
    public enum State {
        INITIALIZED,
        STOPPED,
        STARTED
    }

    private State state = State.INITIALIZED;

    /**
     *
     * @throws Exception
     */
    public void init() {
        state = State.INITIALIZED;
    }

    /**
     *
     * @throws Exception
     */
    public void start() {
        state = State.STARTED;
    }

    /**
     *
     * @throws Exception
     */
    public void stop() {
        state = State.STOPPED;
    }

    /**
     * Returns true if the state is started.
     */
    public boolean started() {
        return state == State.STARTED;
    }

    /**
     * Returns true if the state is stopped.
     */
    public boolean stopped() {
        return state == State.STOPPED;
    }

}
