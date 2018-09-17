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

import javax.servlet.DispatcherType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class FilterMappingDescriptor implements Descriptor {

    /**
     *
     */
    private final static Set<String> allowedDispatcher = new HashSet<String>() {{
        add("FORWARD");
        add("REQUEST");
        add("INCLUDE");
        add("ERROR");
    }};

    private String filterName = null;

    /**
     * A.9.4 Multiple Occurrences of Servlet Mappings
     * <p>
     * Previous versions of the servlet schema allows only a single url-pattern or servlet name per servlet mapping.
     * For servlets mapped to multiple URLs this results in needless repetition of whole mapping clauses.
     * The deployment descriptor servlet-mappingType was updated to:
     * <p>
     * <filter-mapping>
     * <filter-name>Demo Filter</filter-name>
     * <url-pattern>/foo/*</url-pattern>
     * <url-pattern>/bar/*</url-pattern>
     * <servlet-name>Logger</servlet-name>
     * <dispatcher>REQUEST</dispatcher>
     * <dispatcher>ERROR</dispatcher>
     * </filter-mapping>
     */
    private Set<String> urlPatterns = null;

    private Set<String> dispatchers = null;

    private Set<String> servletNames = null;

    /**
     *
     */
    public FilterMappingDescriptor() {
        initialize();
    }

    // Descriptor implements

    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        filterName = null;
        urlPatterns = null;
        dispatchers = null;
        servletNames = null;
    }

    /**
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        if (!(source instanceof FilterMappingDescriptor)) {
            return false;
        }

        FilterMappingDescriptor mapping = (FilterMappingDescriptor) source;

        // merge url patterns
        if (mapping.getUrlPatterns() != null) {
            for (String urlPattern : mapping.getUrlPatterns()) {
                addUrlPattern(urlPattern);
            }
        }

        // merge servlet names
        if (mapping.getServletNames() != null) {
            for (String servletName : mapping.getServletNames()) {
                addServletName(servletName);
            }
        }

        return true;
    }

    /**
     * @return
     */
    public String getFilterName() {
        return filterName;
    }

    /**
     * @param filterName
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * @return
     */
    public String[] getUrlPatterns() {
        if (urlPatterns == null) {
            return new String[0];
        }

        return urlPatterns.stream().toArray(String[]::new);
    }

    /**
     * @param urlPattern
     */
    public void addUrlPattern(String urlPattern) {
        if (urlPattern == null || urlPattern.length() == 0) {
            return;
        }

        if (urlPatterns == null) {
            urlPatterns = new HashSet<>();
        }
        urlPatterns.add(urlPattern);
    }

    /**
     * @param urlPatterns
     */
    public void addUrlPattern(String[] urlPatterns) {
        if (urlPatterns == null) {
            return;
        }

        for (String urlPattern : urlPatterns) {
            addUrlPattern(urlPattern);
        }
    }

    /**
     * @param dispatcher
     */
    public void addDispatcher(String dispatcher) {
        if (dispatcher == null || dispatcher.length() == 0) {
            return;
        }

        if (!allowedDispatcher.contains(dispatcher)) {
            return;
        }

        if (dispatchers == null) {
            dispatchers = new HashSet<>();
        }
        dispatchers.add(dispatcher);
    }

    /**
     * @param dispatchers
     */
    public void addDispatcher(String[] dispatchers) {
        if (dispatchers == null) {
            return;
        }

        for (String dispatcher : dispatchers) {
            addDispatcher(dispatcher);
        }
    }

    /**
     * @return
     */
    public EnumSet<DispatcherType> getDispatcherTypes() {
        if (dispatchers == null) {
            return null;
        }
        List<?> list = dispatchers.stream().collect(Collectors.toList());
        return EnumSet.copyOf((List<DispatcherType>) list);
    }

    /**
     * @return
     */
    public String[] getServletNames() {
        if (servletNames == null) {
            return new String[0];
        }
        return servletNames.stream().toArray(String[]::new);
    }


    /**
     * @param servletName
     */
    public void addServletName(String servletName) {
        if (servletName == null || servletName.length() == 0) {
            return;
        }

        if (servletNames == null) {
            servletNames = new HashSet<>();
        }
        servletNames.add(servletName);
    }

    /**
     * @param servletNames
     */
    public void addServletNames(String[] servletNames) {
        if (servletNames == null) {
            return;
        }

        for (String servletName : servletNames) {
            addServletName(servletName);
        }
    }


    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------\n");
        sb.append("            FILTER  MAPPING                    \n");
        sb.append("-----------------------------------------------\n");
        sb.append("{ ").append("\n");
        sb.append("  filter-name : " + this.filterName).append("\n");
        sb.append("  url-patterns : [").append("\n");
        if (urlPatterns != null) {
            for (String urlPattern : urlPatterns) {
                sb.append("         " + urlPattern).append(", \n");
            }
        }
        sb.append("]").append("\n");
        sb.append("  dispatchers : [").append("\n");
        if (dispatchers != null) {
            for (String dispatcher : dispatchers) {
                sb.append("         " + dispatcher).append(", \n");
            }
        }
        sb.append("]").append("\n");
        sb.append("}").append("\n");
        return sb.toString();
    }
}
