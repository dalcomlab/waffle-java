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
package com.dalcomlab.waffle.http;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Http {

    /**
     *
     * @param name
     * @return
     */
    public String[] getHeader(final String name) {
        requireNonNull(name);
        return null;
    }

    /**
     *
     * @return
     */
    public Map<String, String[]> getHeaderMap() {
        return null;
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addHeader(final String name, final String value) {
        requireNonNull(name);
        requireNonNull(value);
    }

    /**
     *
     * @param name
     * @param values
     */
    public void addHeader(final String name, final String[] values) {
        requireNonNull(name);
        requireNonNull(values);
    }

    /**
     *
     * @param name
     * @return
     */
    public String[] getParameter(final String name) {
        requireNonNull(name);
        return null;
    }

    /**
     *
     * @return
     */
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addParameter(final String name, final String value) {
        requireNonNull(name);
        requireNonNull(value);
    }

    /**
     *
     * @param name
     * @param values
     */
    public void addParameter(final String name, final String[] values) {
        requireNonNull(name);
        requireNonNull(values);
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addCookie(final String name, final String value) {
        requireNonNull(name);
        requireNonNull(value);

    }

    /**
     *
     * @param name
     * @return
     */
    public String getCookie(final String name) {
        requireNonNull(name);
        return null;
    }

    /**
     *
     * @return
     */
    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    /**
     *
     * @param method
     */
    public void setMethod(final HttpMethod method) {

    }

    /**
     *
     * @return
     */
    public URI getRequestURI() {
        return null;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return null;
    }

    /**
     *
     * @param path
     */
    public void setPath(String path) {
        requireNonNull(path);
    }

    /**
     *
     * @param uri
     */
    public void setRequestURI(URI uri) {
        requireNonNull(uri);
    }

    /**
     *
     * @return
     */
    public InputStream getInputStream() {
        return null;
    }

    /**
     *
     * @param input
     */
    public void setInputStream(InputStream input) {
        requireNonNull(input);
    }

    /**
     *
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        return null;
    }

    /**
     *
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        requireNonNull(name);
        requireNonNull(value);
    }
}
