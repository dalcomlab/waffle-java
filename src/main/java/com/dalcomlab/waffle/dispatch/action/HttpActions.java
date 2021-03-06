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

package com.dalcomlab.waffle.dispatch.action;

import com.dalcomlab.waffle.dispatch.HttpRequest;
import com.dalcomlab.waffle.dispatch.HttpResponse;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class HttpActions {

    List<HttpAction> actions = new LinkedList<>();

    public HttpActions() {

    }

    /**
     *
     * @param action
     * @return
     */
    public HttpActions add(HttpAction action) {
        assert action != null;
        actions.add(action);
        return this;
    }

    /**
     *
     * @param request
     */
    public void doAction(HttpRequest request, HttpResponse response) {
        for (HttpAction action : actions) {
            action.doAction(request, response);
        }
    }
}
