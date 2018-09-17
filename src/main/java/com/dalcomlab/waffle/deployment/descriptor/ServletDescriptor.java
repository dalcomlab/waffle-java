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
public class ServletDescriptor implements Descriptor {

    private String servletName = null;
    private String description = null;
    private String displayName = null;
    private String smallIcon = null;
    private String largeIcon = null;
    private String servletClass = null;
    private String jspFile = null;
    private int loadOnStartup = -1;
    private String runAs = null;
    private boolean asyncSupported = false;
    private boolean enabled = false;
    private Map<String, String> parameters = null;
    private SecurityRoleRefDescriptor securityRoleRef = null;
    private MultipartConfigDescriptor multipartConfig = null;

    /**
     *
     */
    public ServletDescriptor() {
        initialize();
    }


    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        servletName = null;
        description = null;
        displayName = null;
        smallIcon = null;
        largeIcon = null;
        servletClass = null;
        jspFile = null;
        loadOnStartup = -1;
        runAs = null;
        asyncSupported = false;
        enabled = false;
        parameters = null;
        securityRoleRef = null;
        multipartConfig = null;
    }

    /**
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        if (!(source instanceof ServletDescriptor)) {
            return false;
        }

        ServletDescriptor servlet = (ServletDescriptor) source;

        if (this.servletName == null) {
            this.servletName = servlet.servletName;
        }

        if (this.description == null) {
            this.description = servlet.description;
        }

        if (this.displayName == null) {
            this.displayName = servlet.displayName;
        }

        if (this.smallIcon == null) {
            this.smallIcon = servlet.smallIcon;
        }

        if (this.largeIcon == null) {
            this.largeIcon = servlet.largeIcon;
        }

        if (this.servletClass == null) {
            this.servletClass = servlet.servletClass;
        }

        if (this.jspFile == null) {
            this.jspFile = servlet.jspFile;
        }

        if (this.loadOnStartup == -1) {
            this.loadOnStartup = servlet.loadOnStartup;
        }

        if (this.runAs == null) {
            this.runAs = servlet.runAs;
        }

        if (this.asyncSupported == false) {
            this.asyncSupported = servlet.asyncSupported;
        }

        if (this.enabled == false) {
            this.enabled = servlet.enabled;
        }

        if (this.enabled == false) {
            this.enabled = servlet.enabled;
        }

        if (servlet.parameters != null) {
            servlet.parameters.forEach((name, value) -> {
                addInitParameter(name, value);
            });
        }

        return true;
    }


    /**
     * @return
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * @param servletName
     */
    public void setServletName(String servletName) {
        this.servletName = servletName;
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
    public String getServletClass() {
        if (this.servletClass == null) {
            return "";
        }

        return this.servletClass;
    }

    /**
     * @param servletClass
     */
    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    /**
     * @return
     */
    public String getJspFile() {
        if (this.jspFile == null) {
            return "";
        }

        return this.jspFile;
    }

    /**
     * @param jspFile
     */
    public void setJspFile(String jspFile) {
        this.jspFile = jspFile;
    }

    /**
     * @return
     */
    public int getLoadOnStartup() {
        return this.loadOnStartup;
    }

    /**
     * @param loadOnStartup
     */
    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * @return
     */
    public String getRunAs() {
        if (this.runAs == null) {
            return "";
        }

        return this.runAs;
    }

    /**
     * @param runAs
     */
    public void setRunAs(String runAs) {
        this.runAs = runAs;
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
    public boolean getEnabled() {
        return this.enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return
     */
    public Map<String, String> getInitParameterMap() {
        return this.parameters;
    }

    /**
     * Adds init parameter into the servlet.
     *
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
        sb.append("            SERVLET                            \n");
        sb.append("-----------------------------------------------\n");
        sb.append("{").append("\n");
        sb.append("  servlet-name : " + this.servletName).append("\n");
        sb.append("  description : " + this.description).append("\n");
        sb.append("  display-name : " + this.displayName).append("\n");
        sb.append("  small-icon : " + this.smallIcon).append("\n");
        sb.append("  large-icon : " + this.largeIcon).append("\n");
        sb.append("  servlet-class : " + this.servletClass).append("\n");
        sb.append("  run-as : " + this.runAs).append("\n");
        sb.append("  load-on-startup : " + this.loadOnStartup).append("\n");
        sb.append("  async-supported : " + this.asyncSupported).append("\n");
        sb.append("}");
        return sb.toString();
    }

}
