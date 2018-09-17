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
import com.dalcomlab.waffle.common.LRUCache;

import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class FilterChainManager extends Manager {

    /**
     *
     */
    private static class FilterChainCache extends LRUCache<String, FilterChainDelegate> {
        public FilterChainCache() {
            super(64);
        }
    }

    private Map<DispatcherType, FilterChainCache> caches = new HashMap<>();

    /**
     * Creates new instance.
     *
     * @param context
     */
    public FilterChainManager(ApplicationContext context) {
        super(context);
        this.caches.put(DispatcherType.REQUEST, new FilterChainCache());
        this.caches.put(DispatcherType.FORWARD, new FilterChainCache());
        this.caches.put(DispatcherType.INCLUDE, new FilterChainCache());
        this.caches.put(DispatcherType.ERROR, new FilterChainCache());
        this.caches.put(DispatcherType.ASYNC, new FilterChainCache());
    }


    /**
     * Creates the filter chain instance.
     *
     * @param request
     * @param response
     * @return
     */
    public FilterChainDelegate createFilterChain(ServletRequest request, ServletResponse response) {
        if (request == null || response == null) {
            return null;
        }

        if (request instanceof HttpServletRequest) {
            String path = ((HttpServletRequest) request).getRequestURL().toString();
            return createFilterChain(path, request.getDispatcherType());
        }

        return null;
    }

    /**
     * Creates the filter chain instance.
     *
     * @param path
     * @param type
     * @return
     */
    public FilterChainDelegate createFilterChain(String path, DispatcherType type) {
        if (path == null || path.length() == 0) {
            return null;
        }

        ServletDelegate servlet = context.getServlet(new Path(path));

        return this.createFilterChain(servlet, path, type);
    }

    /**
     * Creates the filter chain instance.
     *
     * @param servlet
     * @param path
     * @param type
     * @return
     */
    public FilterChainDelegate createFilterChain(ServletDelegate servlet, String path, DispatcherType type) {

        FilterChainDelegate filterChain = getFilterChainFromCache(path, type);
        if (filterChain != null) {
            System.out.println("* hits the filter chain cache.");
            return filterChain;
        } else {
            System.out.println("* missing the filter chain cache.");
        }


        filterChain = new FilterChainDelegate();

        // 1) add filter matching url patterns to the filter chain.
        FilterDelegate[] filters = context.getFilters(new Path(path), type);

        if (filters != null) {
            filterChain.addFilter(filters);
        }

        if (servlet != null) {
            // 2) add filter matching a servlet name to the filter chain.
            filters = context.getFilters(servlet.getServletName());
            if (filters != null) {
                filterChain.addFilter(filters);
            }

            // 3) add filter matching "*" to the filter chain.
            filters = context.getFilters("*");
            if (filters != null) {
                filterChain.addFilter(filters);
            }

            // 4) set a servlet
            filterChain.setServlet(servlet);
        } else {

           // if (filters == null && path.endsWith("/")) {
            if (path.endsWith("/")) {
                // 5) default servlet.....
                    servlet = context.getServletManager().getDefaultServlet();
                    filterChain.removeAllFilter();
                    filterChain.setServlet(servlet);
            }
        }

        if (filterChain != null) {
            putFilterChainToCache(filterChain, path, type);
        }

        return filterChain;
    }

    /**
     * Gets the filter chain instance from a cache.
     *
     * @param path
     * @param type
     * @return
     */
    private FilterChainDelegate getFilterChainFromCache(String path, DispatcherType type) {
        FilterChainCache cache = getCache(type);
        if (cache == null) {
            return null;
        }
        return cache.get(path);
    }

    /**
     * Puts the filter chain to a cache.
     *
     * @param filterChain
     * @param path
     * @param type
     */
    private void putFilterChainToCache(FilterChainDelegate filterChain, String path, DispatcherType type) {
        FilterChainCache cache = getCache(type);
        if (cache != null) {
            cache.put(path, filterChain);
        }
    }

    /**
     * @param type
     * @return
     */
    private FilterChainCache getCache(DispatcherType type) {
        return caches.get(type);
    }
}
