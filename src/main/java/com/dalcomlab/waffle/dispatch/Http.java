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
package com.dalcomlab.waffle.dispatch;


import com.dalcomlab.waffle.application.ApplicationContext;
import com.dalcomlab.waffle.dispatch.action.HttpActions;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Http {
    private ApplicationContext context;
    private HttpRequest request;
    private HttpResponse response;
    private HttpActions actions = new HttpActions();
    /**
     *
     * @param context
     */
    public Http(ApplicationContext context, HttpRequest request, HttpResponse response) {
        assert context != null;
        assert request != null;
        assert response != null;

        this.context = context;
        this.request = request;
        this.response = response;
        this.request.setContext(context);
        this.response.setContext(context);
    }

    /**
     *
     * @return
     */
    public HttpActions onFlush() {
        return actions;
    }

    public void fireFlush() {
        actions.doAction(request, response);
    }
    /**
     *
     */
    public void initialize() {
        request.setHttp(this);
        response.setHttp(this);
        request.initialize();
        response.initialize();
        context.getListenerManager().fireServletRequestInitialized(request);
    }

    /**
     *
     */
    public void destroy() {
        request.destroy();
        response.destroy();
        context.getListenerManager().fireServletRequestDestroyed(request);
    }

}
