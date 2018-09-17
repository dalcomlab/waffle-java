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

import com.dalcomlab.waffle.resource.Path;

import javax.servlet.DispatcherType;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class FilterMapping {

    private String filterName;
    private PathMapping urlPatternsBefore;
    private PathMapping urlPatternsAfter;
    private List<String> servletNamesBefore;
    private List<String> servletNamesAfter;
    private Set<DispatcherType> dispatcherTypes;
    private boolean matchAllUrlPatterns = false;
    private boolean matchAllServletNames = false;

    /**
     *
     */
    public FilterMapping(String filterName) {
        this.filterName = filterName;
        this.urlPatternsBefore = new PathMapping();
        this.urlPatternsAfter = new PathMapping();
        this.servletNamesBefore = new LinkedList<>();
        this.servletNamesAfter = new LinkedList<>();
        this.dispatcherTypes = new HashSet<>();
    }


    /**
     * Gets the name of a filter corresponding to this <code><FilterMapping/code>
     * object.
     *
     * @return the name of a filter
     */
    public String getFilterName() {
        return filterName;
    }


    /**
     * Adds the given url pattern into a filter mapping in after order.
     *
     * @param urlPattern the url pattern of the filter mapping
     */
    public void addURLPatternAfter(String urlPattern) {
        if (urlPattern == null || urlPattern.length() == 0) {
            return;
        }

        if (!urlPattern.startsWith("*.") && !urlPattern.startsWith("/")) {
            urlPattern = "/" + urlPattern;
        }

        if ("/*".equals(urlPattern)) {
            matchAllUrlPatterns = true;
        }

        urlPatternsAfter.addPath(urlPattern);
    }


    /**
     * Adds the given url pattern in a filter mapping in before order.
     *
     * @param urlPattern the url pattern of the filter mapping
     */
    public void addURLPatternBefore(String urlPattern) {
        if (urlPattern == null || urlPattern.length() == 0) {
            return;
        }

        if (!urlPattern.startsWith("*.") && !urlPattern.startsWith("/")) {
            urlPattern = "/" + urlPattern;
        }

        if ("/*".equals(urlPattern)) {
            matchAllUrlPatterns = true;
        }

        urlPatternsBefore.addPath(urlPattern);
    }

    /**
     * Adds the given url patterns in a filter mapping in after order.
     *
     * @param urlPatterns the url patterns of the filter mapping
     */
    public void addURLPatternAfter(String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            addURLPatternAfter(urlPattern);
        }
    }

    /**
     * Adds the given url patterns into a filter mapping in before order.
     *
     * @param urlPatterns the url patterns of the filter mapping
     */
    public void addURLPatternBefore(String[] urlPatterns) {
        for (String urlPattern : urlPatterns) {
            addURLPatternBefore(urlPattern);
        }
    }

    /**
     * Adds the given servlet name into a filter mapping in after order.
     *
     * @param servletName the servlet name of the filter mapping
     */
    public void addServletNameAfter(String servletName) {
        if (servletName == null || servletName.length() == 0) {
            return;
        }
        if ("*".equals(servletName)) {
            matchAllServletNames = true;
        }
        servletNamesAfter.add(servletName);
    }

    /**
     * Adds the given servlet name into a filter mapping in before order.
     *
     * @param servletName the servlet name of the filter mapping
     */
    public void addServletNameBefore(String servletName) {
        if (servletName == null || servletName.length() == 0) {
            return;
        }
        if ("*".equals(servletName)) {
            matchAllServletNames = true;
        }
        servletNamesBefore.add(servletName);
    }


    /**
     * Adds the given servlet names into a filter mapping in after order.
     *
     * @param servletNames the servlet names of the filter mapping
     */
    public void addServletNameAfter(String[] servletNames) {
        for (String servletName : servletNames) {
            addServletNameAfter(servletName);
        }
    }

    /**
     * Adds the given servlet names into a filter mapping in before order.
     *
     * @param servletNames the servlet names of the filter mapping
     */
    public void addServletNameBefore(String[] servletNames) {
        for (String servletName : servletNames) {
            addServletNameBefore(servletName);
        }
    }

    /**
     * Adds the given dispatch type into a filter mapping.
     *
     * @param dispatcherType
     */
    public void addDispatcherType(DispatcherType dispatcherType) {
        dispatcherTypes.add(dispatcherType);
    }

    /**
     * Gets the currently available dispatch types of the Filter.
     *
     * @return
     */
    public DispatcherType[] getDispatchTypes() {
        return dispatcherTypes.stream().toArray(DispatcherType[]::new);
    }

    /**
     * Gets the currently available url-pattern mappings of the Filter.
     *
     * @return a (possibly empty) the array of String of the currently
     * available mappings of this <code>FilterMapping</code>
     */
    public String[] getUrlPatterns() {
        return concat(urlPatternsBefore.getPaths(), urlPatternsAfter.getPaths());
    }

    /**
     * Gets the currently available servlet name mappings of the Filter.
     *
     * @return a (possibly empty) the array of String of the currently
     * available mappings of this <code>FilterMapping</code>
     */
    public String[] getServletNames() {
        String[] before = servletNamesBefore.stream().toArray(String[]::new);
        String[] after = servletNamesAfter.stream().toArray(String[]::new);
        return concat(before, after);
    }


    /**
     * @param first
     * @param second
     * @return
     */
    private String[] concat(String[] first, String[] second) {
        String[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }


    /**
     * @param path
     * @param type
     * @return
     */
    public boolean match(Path path, DispatcherType type) {
        if (!matchDispatchType(type)) {
            return false;
        }

        if (matchAllUrlPattern(path)) {
            return true;
        }

        return matchUrlPattern(path);
    }

    /**
     * @param servletName
     * @return
     */
    public boolean match(String servletName) {
        if (matchAllServletName(servletName)) {
            return true;
        }

        return matchServletName(servletName);
    }

    /**
     * @param type
     * @return
     */
    public boolean matchDispatchType(DispatcherType type) {
        if (this.dispatcherTypes.size() == 0) {
            return (type == DispatcherType.REQUEST);
        }

        DispatcherType[] types = this.getDispatchTypes();
        boolean support = false;
        if (types != null) {
            for (int i = 0; i < types.length; i++) {
                if (types[i] == type) {
                    support = true;
                    break;
                }
            }
        }

        return support;
    }

    /**
     * @param path
     * @return
     */
    public boolean matchUrlPattern(Path path) {
        String[] urlPatterns = this.getUrlPatterns();
        if (urlPatterns == null || urlPatterns.length == 0) {
            return false;
        }

        String url = path.getPath();
        for (String urlPattern : urlPatterns) {
            if (urlPattern.equals(url)) {
                return true;
            }
        }

        return false;
    }


    /**
     * @param servletName
     * @return
     */
    public boolean matchServletName(String servletName) {
        String[] names = this.getServletNames();
        if (names == null || names.length == 0) {
            return false;
        }

        for (String name : names) {
            if (name.equals(servletName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * @param path
     * @return
     */
    public boolean matchAllUrlPattern(Path path) {
        return matchAllUrlPatterns;
    }

    /**
     * @param servletName
     * @return
     */
    public boolean matchAllServletName(String servletName) {
        return matchAllServletNames;
    }


}
