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
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ChannelResponse {

    protected HttpServletResponse response;

    /**
     * @param cookie
     */
    public void addCookie(Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * @param name
     * @return
     */
    public boolean containsHeader(String name) {
        return response.containsHeader(name);
    }


    /**
     * @param name
     * @param date
     */
    public void setDateHeader(String name, long date) {
        response.setDateHeader(name, date);
    }

    /**
     * @param name
     * @param date
     */
    public void addDateHeader(String name, long date) {
        response.addDateHeader(name, date);
    }

    /**
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        response.setHeader(name, value);
    }

    /**
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        response.addHeader(name, value);
    }

    /**
     * @param name
     * @param value
     */
    public void setIntHeader(String name, int value) {
        response.setIntHeader(name, value);
    }

    /**
     * @param name
     * @param value
     */
    public void addIntHeader(String name, int value) {
        response.addIntHeader(name, value);
    }


    /**
     * @param sc
     */
    public void setStatus(int sc) {
        response.setStatus(sc);
    }

    /**
     * @param sc
     * @param sm
     */
    public void setStatus(int sc, String sm) {
        response.setStatus(sc, sm);
    }

    /**
     * @return
     */
    public int getStatus() {
        return response.getStatus();
    }


    /**
     * @param name
     * @return
     */
    public String getHeader(String name) {
        return response.getHeader(name);
    }


    /**
     * @param name
     * @return
     */
    public Collection<String> getHeaders(String name) {
        return response.getHeaders(name);
    }

    /**
     * @return
     */
    public Collection<String> getHeaderNames() {
        return response.getHeaderNames();
    }

    /**
     * @return
     */
    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    /**
     * @return
     */
    public String getContentType() {
        return response.getContentType();
    }

    /**
     * @param charset
     */
    public void setCharacterEncoding(String charset) {
        response.setCharacterEncoding(charset);
    }

    /**
     * @param type
     */
    public void setContentType(String type) {
        response.setContentType(type);
    }
}
