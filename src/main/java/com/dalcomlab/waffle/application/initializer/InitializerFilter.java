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

import com.dalcomlab.waffle.application.FilterManager;
import com.dalcomlab.waffle.application.FilterMapping;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class InitializerFilter implements Initializer {

    private FilterManager manager = null;


    /**
     * @param manager
     */
    public InitializerFilter(FilterManager manager) {
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
        FilterMapping[] mappings = manager.getFilterMappings();
        if (mappings == null || mappings.length == 0) {
            return;
        }

        for (FilterMapping mapping : mappings) {
            // 1) checks if the url pattern is valid
            if (!checksUrlPattern(mapping.getUrlPatterns())) {
                throw new Exception("invalid url pattern");
            }
            // 2) checks if the filter is valid
            if (!checksFilterName(mapping.getFilterName())) {
                throw new Exception("the filter is invalid");
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
     *
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
     * @param filterName
     * @return
     */
    private boolean checksFilterName(String filterName) {
        if (filterName == null || filterName.length() == 0) {
            return false;
        }
        if (manager.getFilterRegistration(filterName) == null) {
            return false;
        }
        return true;
    }
}
