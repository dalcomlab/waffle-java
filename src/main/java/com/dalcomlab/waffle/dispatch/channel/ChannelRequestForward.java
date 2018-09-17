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

package com.dalcomlab.waffle.dispatch.channel;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ChannelRequestForward extends ChannelRequest {

    /**
     * @param request
     */
    public ChannelRequestForward(ServletRequest request) {
        super(request);
        initialize();
    }

    /**
     * @param channel
     */
    public ChannelRequestForward(ChannelRequest channel) {
        super(channel);
        initialize();
    }

    /**
     *
     */
    @Override
    public void initialize() {
        attributes.put(RequestDispatcher.FORWARD_REQUEST_URI, null);
        attributes.put(RequestDispatcher.FORWARD_CONTEXT_PATH, null);
        attributes.put(RequestDispatcher.FORWARD_SERVLET_PATH, null);
        attributes.put(RequestDispatcher.FORWARD_PATH_INFO, null);
        attributes.put(RequestDispatcher.FORWARD_QUERY_STRING, null);
        attributes.put(RequestDispatcher.FORWARD_MAPPING, null);
    }


    /**
     *
     */
    @Override
    public void destroy() {

    }

    /**
     * @return
     */
    @Override
    public DispatcherType getDispatcherType() {
        return DispatcherType.FORWARD;
    }
}
