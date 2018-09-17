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
public class ServletMapping {

    private PathMapping urlPatterns;
    private String servletName;

    /**
     *
     */
    public ServletMapping(String servletName) {
        this.servletName = servletName;
        this.urlPatterns = new PathMapping();
    }

    /**
     * Gets the name of a servlet corresponding to this <code><ServletMapping/code>
     * object.
     *
     * @return the name of a servlet
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * Adds the given url pattern in a servlet mapping.
     *
     * @param urlPattern the url pattern of the servlet mapping
     */
    public void addUrlPattern(String urlPattern) {
        urlPatterns.addPath(urlPattern);
    }

    /**
     * Adds the given url patterns in a servlet mapping.
     *
     * @param urlPatterns the url patterns of the servlet mapping
     */
    public void addUrlPattern(String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            addUrlPattern(urlPattern);
        }
    }

    /**
     * Determines whether the given path matches one of prefix patterns.
     *
     * @param path
     * @return
     */
    public String matchesPrefix(String path) {
        return urlPatterns.matchesPrefix(path);
    }

    /**
     * Determines whether the given path matches one of extension patterns.
     *
     * @param path
     * @return
     */
    public String matchesExtension(String path) {
        return urlPatterns.matchesExtension(path);
    }


    /**
     * Determines whether the given path matches one of the default pattern.
     *
     * @param path
     * @return
     */
    public String matchesDefault(String path) {
        return urlPatterns.matchesDefault(path);
    }

    /**
     * Determines whether the given path matches one of exact patterns.
     *
     * @param path
     * @return
     */
    public String matchesExact(String path) {
        return urlPatterns.matchesExact(path);
    }

    /**
     * Gets the all url patterns in this mapping.
     *
     * @return
     */
    public String[] getUrlPatterns() {
        return urlPatterns.getPaths();
    }
}
