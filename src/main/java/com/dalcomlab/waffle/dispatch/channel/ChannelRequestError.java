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
public class ChannelRequestError extends ChannelRequest {

    /**
     * @param request
     */
    public ChannelRequestError(ServletRequest request) {
        super(request);
    }

    /**
     * @param channel
     */
    public ChannelRequestError(ChannelRequest channel) {
        super(channel);
    }


    /**
     *
     */
    @Override
    public void initialize() {
        attributes.put(RequestDispatcher.ERROR_EXCEPTION, null);
        attributes.put(RequestDispatcher.ERROR_EXCEPTION_TYPE, null);
        attributes.put(RequestDispatcher.ERROR_MESSAGE, null);
        attributes.put(RequestDispatcher.ERROR_REQUEST_URI, null);
        attributes.put(RequestDispatcher.ERROR_SERVLET_NAME, null);
        attributes.put(RequestDispatcher.ERROR_STATUS_CODE, null);
    }

    /**
     * @return
     */
    @Override
    public DispatcherType getDispatcherType() {
        return DispatcherType.ERROR;
    }
}
