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

package com.dalcomlab.waffle.deployment.descriptor;

import java.util.HashSet;
import java.util.Set;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ServletMappingDescriptor implements Descriptor {

    private String servletName = null;

    /**
     *  A.9.3  Multiple Occurrences of Servlet Mappings
     *
     *  Previous versions of the servlet schema allows only a single url-pattern or servlet name per servlet mapping.
     *  For waffle.servlets mapped to multiple URLs this results in needless repetition of whole mapping clauses.
     *
     */
    private Set<String> urlPatterns = null;

    /**
     *
     */
    public ServletMappingDescriptor() {
        initialize();
    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        servletName = null;
        urlPatterns = null;
    }

    /**
     *
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        if (!(source instanceof ServletMappingDescriptor)) {
            return false;
        }

        ServletMappingDescriptor mapping = (ServletMappingDescriptor)source;

        if (mapping.getUrlPatterns() != null) {
            for (String urlPattern : mapping.getUrlPatterns()) {
                addUrlPattern(urlPattern);
            }
        }

        return true;
    }

    /**
     *
     * @return
     */
    public String getServletName() {
        return servletName;
    }

    /**
     *
     * @param servletName
     */
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    /**
     *
     * @return
     */
    public Set<String> getUrlPatterns() {
        return urlPatterns;
    }

    /**
     *
     * @param urlPattern
     */
    public void addUrlPattern(String urlPattern) {
        if (urlPatterns == null) {
            urlPatterns = new HashSet<>();
        }
        urlPatterns.add(urlPattern);
    }

    /**
     *
     * @param urlPatterns
     */
    public void addUrlPattern(String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            addUrlPattern(urlPattern);
        }
    }


    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------\n");
        sb.append("            SERVLET MAPPING                    \n");
        sb.append("-----------------------------------------------\n");
        sb.append("{ ").append("\n");
        sb.append("  servlet-name : " + this.servletName).append("\n");
        sb.append("  url-patterns : [").append("\n");
        for (String urlPattern : urlPatterns) {
            sb.append("         " + urlPattern).append(", \n");
        }
        sb.append("] }");
        return sb.toString();
    }
}
