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

import javax.servlet.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class FilterChainDelegate implements FilterChain {

    private List<FilterDelegate> filters = new LinkedList<>();
    private ServletDelegate servlet = null;
    private int position = 0;

    /**
     *
     */
    public FilterChainDelegate() {

    }

    /**
     * Causes the next filter in the chain to be invoked, or if the calling filter is the last filter
     * in the chain, causes the resource at the end of the chain to be invoked.
     *
     * @param request  the channel to pass along the chain.
     * @param response the response to pass along the chain.
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception has occurred that interferes with the
     *                          filterChain's normal operation
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        if (filters.size() > position) {
            FilterDelegate filter = filters.get(position++);

            assert filter != null;

            try {
                filter.doFilter(request, response, this);
            } catch (IOException e) {
                throw e;
            }
        } else {
            if (servlet != null) {
                servlet.service(request, response);
            }
        }
    }


    /**
     * @param filter
     */
    public void addFilter(FilterDelegate filter) {
        if (filter != null) {
            filters.add(filter);
        }
    }

    /**
     * @param filters
     */
    public void addFilter(FilterDelegate... filters) {
        for (FilterDelegate filter : filters) {
            addFilter(filter);
        }
    }

    /**
     *
     */
    public void removeAllFilter() {
        filters.clear();
    }

    /**
     * @param servlet
     */
    public void setServlet(ServletDelegate servlet) {
        this.servlet = servlet;
    }

    /**
     *
     */
    public void reset() {
        position = 0;
    }


}
