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

package com.dalcomlab.waffle.deployment;

import com.dalcomlab.waffle.deployment.descriptor.*;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Deployment {

    private String name = null;
    private OrderingDescriptor ordering = null;
    private Map<String, ServletDescriptor> servlets = new HashMap<>();
    private Map<String, FilterDescriptor> filters = new HashMap<>();
    private Map<String, ListenerDescriptor> listeners = new HashMap<>();
    private Map<String, ServletMappingDescriptor> servletMappings = new HashMap<>();
    private Map<String, FilterMappingDescriptor> filterMappings = new HashMap<>();
    private List<String> welcomeFiles = new LinkedList<>();
    private List<ErrorPageDescriptor> errorPages = new LinkedList<>();

    private boolean metaDataComplete = false;

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     */
    public boolean isMetaDataComplete() {
        return metaDataComplete;
    }

    /**
     * @return
     */
    public OrderingDescriptor getOrdering() {
        if (ordering == null) {
            ordering = new OrderingDescriptor();
        }
        return ordering;
    }

    /**
     * @return
     */
    public ServletDescriptor createServlet() {
        return new ServletDescriptor();
    }

    /**
     * @return
     */
    public FilterDescriptor createFilter() {
        return new FilterDescriptor();
    }

    /**
     * @return
     */
    public ServletMappingDescriptor createServletMapping() {
        return new ServletMappingDescriptor();
    }

    /**
     * @return
     */
    public FilterMappingDescriptor createFilterMapping() {
        return new FilterMappingDescriptor();
    }

    /**
     * @return
     */
    public ListenerDescriptor createListener() {
        return new ListenerDescriptor();
    }


    /**
     * @param servlet
     */
    public void addServlet(ServletDescriptor servlet) {
        if (servlet == null) {
            return;
        }
        // if there is the same servlet, merge new servlet into the old servlet.
        String name = servlet.getServletName();
        if (servlets.containsKey(name)) {
            servlets.get(name).merge(servlet);
        } else {
            servlets.put(name, servlet);
        }
    }

    /**
     * @return
     */
    public ServletDescriptor[] getServlets() {
        return toArray(this.servlets, ServletDescriptor.class);
    }

    /**
     * @param filter
     */
    public void addFilter(FilterDescriptor filter) {
        if (filter == null) {
            return;
        }

        // if there is the same filter, merge new filter into the old filter.
        String name = filter.getFilterName();
        if (filters.containsKey(name)) {
            filters.get(name).merge(filter);
        } else {
            filters.put(name, filter);
        }
    }

    /**
     * @return
     */
    public FilterDescriptor[] getFilters() {
        return toArray(this.filters, FilterDescriptor.class);
    }

    /**
     * @param mapping
     */
    public void addServletMapping(ServletMappingDescriptor mapping) {
        if (mapping == null) {
            return;
        }

        // if there is the servlet mapping with the same servlet name, merge new servlet mapping
        // into the old servlet mapping.
        String name = mapping.getServletName();
        if (servletMappings.containsKey(name)) {
            servletMappings.get(name).merge(mapping);
        } else {
            servletMappings.put(name, mapping);
        }

    }

    /**
     * @return
     */
    public ServletMappingDescriptor[] getServletMappings() {
        return toArray(this.servletMappings, ServletMappingDescriptor.class);
    }


    /**
     * @param mapping
     */
    public void addFilterMapping(FilterMappingDescriptor mapping) {
        if (mapping == null) {
            return;
        }

        // if there is the filter mapping with the same filter name, merge new filter mapping
        // into the old filter mapping.
        String name = mapping.getFilterName();
        if (filterMappings.containsKey(name)) {
            filterMappings.get(name).merge(mapping);
        } else {
            filterMappings.put(name, mapping);
        }

    }


    /**
     * @return
     */
    public FilterMappingDescriptor[] getFilterMappings() {
        return toArray(this.filterMappings, FilterMappingDescriptor.class);
    }

    /**
     * @param listener
     */
    public void addListener(ListenerDescriptor listener) {
        if (listener == null) {
            return;
        }
        // override listener class with the same name in a map.
        String name = listener.getListenerClass();
        listeners.put(name, listener);
    }

    /**
     * @return
     */
    public ListenerDescriptor[] getLiseners() {
        return toArray(this.listeners, ListenerDescriptor.class);
    }

    /**
     * @param source
     */
    public void merge(Deployment source) {
        if (source.servlets != null) {
            source.servlets.forEach((name, servlet) -> {
                addServlet(servlet);
            });
        }

        if (source.filters != null) {
            source.filters.forEach((name, filter) -> {
                addFilter(filter);
            });
        }

        if (source.listeners != null) {
            source.listeners.forEach((name, listener) -> {
                addListener(listener);
            });
        }

        if (source.servletMappings != null) {
            source.servletMappings.forEach((name, mapping) -> {
                addServletMapping(mapping);
            });
        }

        if (source.filterMappings != null) {
            source.filterMappings.forEach((name, mapping) -> {
                addFilterMapping(mapping);
            });
        }
    }

    /**
     * @param context
     */
    public void deploy(ServletContext context) {
        deployServlet(context);
        deployFilter(context);
        deployListener(context);
    }

    /**
     * @param context
     */
    protected void deployServlet(ServletContext context) {

        // 1) deploy servlet
        ServletDescriptor[] servlets = this.getServlets();
        for (ServletDescriptor descriptor : servlets) {
            String servletName = descriptor.getServletName();
            String servletClass = descriptor.getServletClass();
            System.out.println("   * find servlet : " + servletName + ", " + servletClass);
            ServletRegistration.Dynamic servlet = context.addServlet(servletName, servletClass);
            if (servlet != null) {
                servlet.setLoadOnStartup(descriptor.getLoadOnStartup());
                servlet.setRunAsRole(descriptor.getRunAs());
                servlet.setAsyncSupported(descriptor.getAsyncSupported());
                Map<String, String> parameters = descriptor.getInitParameterMap();
                if (parameters != null) {
                    servlet.setInitParameters(parameters);
                }
            }

        }

        // 2) deploy servlet mapping
        ServletMappingDescriptor[] mappings = this.getServletMappings();
        for (ServletMappingDescriptor mapping : mappings) {
            ServletRegistration registration = context.getServletRegistration(mapping.getServletName());
            if (registration != null) {
                String[] urls = mapping.getUrlPatterns().toArray(new String[mapping.getUrlPatterns().size()]);
                registration.addMapping(urls);
            }
        }
    }

    /**
     * @param context
     */
    protected void deployFilter(ServletContext context) {

        // 1) deploy filter
        FilterDescriptor[] filters = this.getFilters();
        for (FilterDescriptor descriptor : filters) {
            String filterName = descriptor.getFilterName();
            String filterClass = descriptor.getFilterClass();
            System.out.println("   * find filter : " + filterName + ", " + filterClass);
            FilterRegistration.Dynamic filter = context.addFilter(filterName, filterClass);
            if (filter != null) {
                filter.setAsyncSupported(descriptor.getAsyncSupported());
                Map<String, String> parameters = descriptor.getInitParameterMap();
                if (parameters != null) {
                    filter.setInitParameters(parameters);
                }
            }
        }

        // 2) deploy filter mapping
        FilterMappingDescriptor[] mappings = this.getFilterMappings();
        for (FilterMappingDescriptor mapping : mappings) {
            FilterRegistration registration = context.getFilterRegistration(mapping.getFilterName());
            if (registration != null) {
                EnumSet<DispatcherType> dispatcherTypes = mapping.getDispatcherTypes();
                String servletNames[] = mapping.getServletNames();
                if (servletNames != null) {
                    registration.addMappingForServletNames(dispatcherTypes, false, servletNames);
                }
                String urlPatterns[] = mapping.getUrlPatterns();
                if (urlPatterns != null) {
                    registration.addMappingForUrlPatterns(dispatcherTypes, false, urlPatterns);
                }
            }
        }
    }

    /**
     * @param context
     */
    protected void deployListener(ServletContext context) {
        ListenerDescriptor[] listeners = this.getLiseners();
        for (ListenerDescriptor descriptor : listeners) {
            String listenerClass = descriptor.getListenerClass();
            System.out.println("   * find listener : " + listenerClass);
            context.addListener(listenerClass);
        }
    }

    /**
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T[] toArray(Map<String, T> map, Class<T> clazz) {
        T[] array = (T[]) Array.newInstance(clazz, map.size());
        int i = 0;
        for (String key : map.keySet()) {
            array[i++] = map.get(key);
        }
        return array;
    }
}