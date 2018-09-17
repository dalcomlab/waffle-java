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
package com.dalcomlab.waffle.application.initializer;

import com.dalcomlab.waffle.application.ServletManager;
import com.dalcomlab.waffle.application.ServletMapping;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class InitializerServlet implements Initializer {

    private ServletManager manager = null;

    /**
     * @param manager
     */
    public InitializerServlet(ServletManager manager) {
        this.manager = manager;
    }

    /**
     * Initialize
     */
    @Override
    public void initialize() {

        // 1) activate manager
        try {
            activate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2) validate manager
        try {
            validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    private void activate() throws Exception {

    }

    /**
     * @throws Exception
     */
    private void validate() throws Exception {
        ServletMapping[] mappings = manager.getServletMappings();
        if (mappings == null || mappings.length == 0) {
            return;
        }

        for (ServletMapping mapping : mappings) {
            // 1) checks if the url pattern is valid
            if (!checksUrlPattern(mapping.getUrlPatterns())) {
                throw new Exception("invalid url pattern");
            }
            // 2) checks if the servlet is valid
            if (!checksServletName(mapping.getServletName())) {
                throw new Exception("the servlet is invalid");
            }
        }

    }

    /**
     * @param urlPattern
     * @return
     */
    private boolean checksUrlPattern(String urlPattern) {
        if (urlPattern == null || urlPattern.length() == 0) {
            return false;
        }

        if ("/".equals(urlPattern)) {
            return true;
        }

        if (!urlPattern.startsWith("/")) {
            if (!urlPattern.startsWith("*")) {
                return false;
            }
            return true;
        }

        if (urlPattern.indexOf('*') != -1) {
            return false;
        }

        return true;
    }

    /**
     * @param urlPatterns
     * @return
     */
    private boolean checksUrlPattern(String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            if (!checksUrlPattern(urlPattern)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param servletName
     * @return
     */
    private boolean checksServletName(String servletName) {
        if (servletName == null || servletName.length() == 0) {
            return false;
        }
        if (manager.getServletRegistration(servletName) == null) {
            return false;
        }
        return true;
    }
}
