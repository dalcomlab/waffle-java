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

import java.util.HashMap;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class FilterDescriptor implements Descriptor {

    private String description = null;
    private String displayName = null;
    private String smallIcon = null;
    private String largeIcon = null;
    private String filterClass = null;
    private String filterName = null;
    private boolean asyncSupported = false;
    private Map<String, String> parameters = null;

    /**
     *
     */
    public FilterDescriptor() {
        initialize();
    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        description = null;
        displayName = null;
        smallIcon = null;
        largeIcon = null;
        filterClass = null;
        filterName = null;
        asyncSupported = false;
        parameters = null;
    }

    /**
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        if (!(source instanceof FilterDescriptor)) {
            return false;
        }

        FilterDescriptor filter = (FilterDescriptor) source;

        if (this.filterName == null) {
            this.filterName = filter.filterName;
        }

        if (this.description == null) {
            this.description = filter.description;
        }

        if (this.displayName == null) {
            this.displayName = filter.displayName;
        }

        if (this.smallIcon == null) {
            this.smallIcon = filter.smallIcon;
        }

        if (this.largeIcon == null) {
            this.largeIcon = filter.largeIcon;
        }

        if (this.filterClass == null) {
            this.filterClass = filter.filterClass;
        }


        if (this.asyncSupported == false) {
            this.asyncSupported = filter.asyncSupported;
        }

        if (filter.parameters != null) {
            filter.parameters.forEach((name, value) -> {
                addInitParameter(name, value);
            });
        }

        return true;
    }

    /**
     * @return
     */
    public String getDescription() {
        if (this.description == null) {
            return "";
        }

        return this.description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return
     */
    public String getDisplayName() {
        if (this.displayName == null) {
            return "";
        }

        return this.displayName;
    }

    /**
     * @param displayName
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return
     */
    public String getSmallIcon() {
        if (this.smallIcon == null) {
            return "";
        }

        return this.smallIcon;
    }

    /**
     * @param smallIcon
     */
    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    /**
     * @return
     */
    public String getLargeIcon() {
        if (this.largeIcon == null) {
            return "";
        }

        return this.largeIcon;
    }

    /**
     * @param largeIcon
     */
    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    /**
     * @return
     */
    public String getFilterClass() {
        if (this.filterClass == null) {
            return "";
        }

        return this.filterClass;
    }

    /**
     * @param filterClass
     */
    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    /**
     * @return
     */
    public String getFilterName() {
        if (this.filterName == null) {
            return "";
        }

        return this.filterName;
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
    public boolean getAsyncSupported() {
        return this.asyncSupported;
    }

    /**
     * @param asyncSupported
     */
    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }


    /**
     * @return
     */
    public Map<String, String> getInitParameterMap() {
        return this.parameters;
    }

    /**
     * @param name
     * @param value
     */
    public void addInitParameter(String name, String value) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }

        if (parameters.containsKey(name)) {
            return;
        }

        parameters.put(name, value);
    }


    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------------\n");
        sb.append("            FILTER                             \n");
        sb.append("-----------------------------------------------\n");
        sb.append("{").append("\n");
        sb.append("  description : " + this.description).append("\n");
        sb.append("  display-name : " + this.displayName).append("\n");
        sb.append("  small-icon : " + this.smallIcon).append("\n");
        sb.append("  large-icon : " + this.largeIcon).append("\n");
        sb.append("  filter-class : " + this.filterClass).append("\n");
        sb.append("  filter-name : " + this.filterName).append("\n");
        sb.append("  async-supported : " + this.asyncSupported).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
