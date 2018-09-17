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

import com.dalcomlab.waffle.application.FilterChainDelegate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Dispatch {

    private ServletRequest request;
    private ServletResponse response;
    private String requestURI;
    private String contextPath;
    private String servletPath;
    private String pathInfo;
    private String queryString;
    private FilterChainDelegate filterChain;

    /**
     *
     */
    public Dispatch(FilterChainDelegate filterChain, ServletRequest request, ServletResponse response, String requestURI, String contextPath, String servletPath, String pathInfo, String queryString) {

        assert filterChain != null;
        assert request != null;
        assert response != null;

        this.filterChain = filterChain;
        this.request = request;
        this.response = response;
        this.requestURI = requestURI;
        this.contextPath = contextPath;
        this.servletPath = servletPath;
        this.pathInfo = pathInfo;
        this.queryString = queryString;
    }

    /**
     *
     * @return
     */
    public ServletRequest getRequest() {
        return this.request;
    }

    /**
     *
     * @return
     */
    public ServletResponse getResponse() {
        return this.response;
    }

    /**
     *
     * @return
     */
    public String getRequestURI() {
        return this.requestURI;
    }

    /**
     *
     * @return
     */
    public String getContextPath() {
        if (this.contextPath == null || this.contextPath.length() == 0) {
            return null;
        }
        return this.contextPath;
    }

    /**
     *
     * @return
     */
    public String getServletPath() {
        if (this.servletPath == null || this.servletPath.length() == 0) {
            return null;
        }
        return this.servletPath;
    }

    /**
     *
     * @return
     */
    public String getPathInfo() {
        // The empty path info must be null.
        if (this.pathInfo == null || this.pathInfo.length() == 0) {
            return null;
        }
        return this.pathInfo;
    }

    /**
     *
     * @return
     */
    public String getQueryString() {
        if (this.queryString == null || this.queryString.length() == 0) {
            return null;
        }
        return this.queryString;
    }

    public void dispatch() {
        if (filterChain != null) {
            try {
                filterChain.doFilter(request, response);
                filterChain.reset(); // cache
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
