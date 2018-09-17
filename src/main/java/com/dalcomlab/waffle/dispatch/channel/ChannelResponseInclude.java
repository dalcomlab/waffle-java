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

import javax.servlet.http.Cookie;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ChannelResponseInclude extends ChannelResponse {

    /**
     * @param cookie
     */
    @Override
    public void addCookie(Cookie cookie) {
        // no operation in include
    }


    /**
     * @param name
     * @param date
     */
    @Override
    public void setDateHeader(String name, long date) {
        // no operation in include
    }

    /**
     * @param name
     * @param date
     */
    @Override
    public void addDateHeader(String name, long date) {
        // no operation in include
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setHeader(String name, String value) {
        // no operation in include
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void addHeader(String name, String value) {
        // no operation in include
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void setIntHeader(String name, int value) {
        // no operation in include
    }

    /**
     * @param name
     * @param value
     */
    @Override
    public void addIntHeader(String name, int value) {
        // no operation in include
    }


    /**
     * @param sc
     */
    @Override
    public void setStatus(int sc) {
        // no operation in include
    }

    /**
     * @param sc
     * @param sm
     */
    @Override
    public void setStatus(int sc, String sm) {
        // no operation in include
    }


    /**
     * @param charset
     */
    @Override
    public void setCharacterEncoding(String charset) {
        // no operation in include
    }

    /**
     * @param type
     */
    @Override
    public void setContentType(String type) {
        // no operation in include
    }
}
