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
package com.dalcomlab.waffle.dispatch.channel;


import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ChannelRequest {

    protected ServletRequest request = null;
    protected Hashtable<String, String[]> parameters = new Hashtable<String, String[]>();
    protected Map<String, Object> attributes = new HashMap<>();
    protected String requestURI;
    protected String contextPath;
    protected String servletPath;
    protected String pathInfo;
    protected String queryString;

    /**
     *
     */
    public ChannelRequest() {
    }


    /**
     * @param request
     */
    public ChannelRequest(ServletRequest request) {
        if (request.getParameterMap() != null) {
            parameters.putAll(request.getParameterMap());
        }
        this.request = request;
    }

    /**
     * @param channel
     */
    public ChannelRequest(ChannelRequest channel) {
        if (channel.getParameterMap() != null) {
            parameters.putAll(channel.getParameterMap());
        }
        this.request = channel.request;
    }

    /**
     *
     */
    public void initialize() {

    }


    /**
     *
     */
    public void destroy() {

    }


    /**
     * @return
     */
    public DispatcherType getDispatcherType() {
        return DispatcherType.REQUEST;
    }

    /**
     * @param name
     * @return
     */
    public String getParameter(String name) {
        String[] values = getParameterValues(name);

        if (values == null) {
            return null;
        }
        return values[0];
    }

    /**
     * @return
     */
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameters.keySet());
    }

    /**
     * @param name
     * @return
     */
    public String[] getParameterValues(String name) {
        String[] values = parameters.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values;
    }

    /**
     * @return
     */
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(parameters);
    }

    /**
     * @param name
     * @param value
     */
    public void setParameter(String name, String value) {
        this.setParameter(name, new String[]{value});
    }

    /**
     * @param name
     * @param values
     */
    public void setParameter(String name, String[] values) {
        if (name == null || name.length() == 0) {
            return;
        }

        if (values == null) {
            return;
        }

        values = mergeValues(parameters.get(name), values);
        parameters.put(name, values);
    }

    /**
     * @param name
     * @param value
     */
    public void setAttribute(String name, Object value) {
        if (request == null) {
            return;
        }

        if (attributes.containsKey(name)) {
            attributes.put(name, value);
        } else {
            request.setAttribute(name, value);
        }
    }

    /**
     * @param name
     */
    public void removeAttribute(String name) {
        if (request == null) {
            return;
        }
        request.removeAttribute(name);
    }

    /**
     * @param name
     * @return
     */
    public Object getAttribute(String name) {
        if (request == null) {
            return null;
        }

        if (attributes.containsKey(name)) {
            return attributes.get(name);
        }

        return request.getAttribute(name);
    }

    /**
     * @return
     */
    public Enumeration<String> getAttributeNames() {
        if (request == null) {
            return null;
        }

        Set<String> names;
        if (request.getAttributeNames() != null) {
            names = new HashSet<>(Collections.list(request.getAttributeNames()));
        } else {
            names = new HashSet<>();
        }

        // add the attribute except for item with null.
        for (String name : attributes.keySet()) {
            Object value = attributes.get(name);
            if (value != null) {
                names.add(name);
            }
        }
        return Collections.enumeration(names);
    }


    /**
     * Returns the request uri corresponding to this dispatch.
     *
     * @return
     */
    public String getRequestURI() {
        return this.requestURI;
    }

    /**
     * Returns the context path corresponding to this dispatch.
     *
     * @return
     */
    public String getContextPath() {
        return this.contextPath;
    }

    /**
     * Returns the servlet path corresponding to this dispatch.
     *
     * @return
     */
    public String getServletPath() {
        return this.servletPath;
    }

    /**
     * Returns the path info corresponding to this dispatch.
     *
     * @return
     */
    public String getPathInfo() {
        return this.pathInfo;
    }

    /**
     * Returns the query string corresponding to this dispatch.
     *
     * @return
     */
    public String getQueryString() {
        return this.queryString;
    }

    /**
     * Sets new request uri to this dispatch.
     *
     * @param requestURI
     */
    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }


    /**
     * Sets new context path to this dispatch.
     *
     * @param contextPath
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * Sets new servlet path to this dispatch.
     *
     * @param servletPath
     */
    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    /**
     * Sets new path info to this dispatch.
     *
     * @param pathInfo
     */
    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    /**
     * Sets new query string to this dispatch.
     *
     * @param queryString
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     *
     * @param values1
     * @param values2
     * @return
     */
    private String[] mergeValues(String[] values1, String[] values2) {
        int length = 0;
        if (values1 != null) {
            length += values1.length;
        }

        if (values2 != null) {
            length += values2.length;
        }

        int i = 0;
        String[] joinedValues = new String[length];
        if (values1 != null) {
            for(String value : values1) {
                joinedValues[i++] = value;
            }
        }

        if (values2 != null) {
            for(String value : values2) {
                joinedValues[i++] = value;
            }
        }

        return joinedValues;
    }
}
