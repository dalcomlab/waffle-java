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
package com.dalcomlab.waffle.dispatch;

import com.dalcomlab.waffle.application.ApplicationContext;
import com.dalcomlab.waffle.common.Strings;
import com.dalcomlab.waffle.dispatch.channel.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Stack;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ChannelledRequest extends HttpServletRequestWrapper {

    protected ApplicationContext context;
    protected ChannelRequest channel;
    protected Stack<ChannelRequest> channels = new Stack<>();
    protected String requestURI;
    protected String contextPath;
    protected String servletPath;
    protected String pathInfo;
    protected String queryString;

    public ChannelledRequest(ApplicationContext context, HttpServletRequest request) {
        super(request);
        this.context = context;
        this.channel = new ChannelRequest(request);
        pushChannel(this.channel);
    }


    protected boolean pushChannel(ChannelRequest channel) {
        channels.push(channel);
        return true;
    }

    @Override
    public DispatcherType getDispatcherType() {
        if (channel != null) {
            return channel.getDispatcherType();
        }
        return super.getDispatcherType();
    }


    @Override
    public String getParameter(String name) {
        if (channel != null) {
            return channel.getParameter(name);
        }
        return super.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if (channel != null) {
            return channel.getParameterNames();
        }
        return super.getParameterNames();
    }


    @Override
    public String[] getParameterValues(String name) {
        if (channel != null) {
            return channel.getParameterValues(name);
        }
        return super.getParameterValues(name);
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        if (channel != null) {
            return channel.getParameterMap();
        }
        return super.getParameterMap();
    }

    public void setParameter(String name, String[] values) {
        if (channel != null) {
            channel.setParameter(name, values);
        }
    }

    @Override
    public void setAttribute(String name, Object value) {
        if (channel != null) {
            channel.setAttribute(name, value);
        } else {
            super.setAttribute(name, value);
        }
    }

    @Override
    public void removeAttribute(String name) {
        if (channel != null) {
            channel.removeAttribute(name);
        } else {
            super.removeAttribute(name);
        }
    }

    @Override
    public Object getAttribute(String name) {
        if (channel != null) {
            return channel.getAttribute(name);
        }
        return super.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        if (channel != null) {
            return channel.getAttributeNames();
        }
        return super.getAttributeNames();
    }

    public String getLastDispatchedServletPath() {
        String servletPath = this.channel.getServletPath();
        if (servletPath == null) {
            servletPath = getServletPath();
        }
        return servletPath;
    }

    public String getLastDispatchedPathInfo() {
        String pathInfo = this.channel.getPathInfo();
        if (pathInfo == null) {
            pathInfo = getPathInfo();
        }
        return pathInfo;
    }

    public String getLastDispatchedQueryString() {
        String queryString = this.channel.getQueryString();
        if (queryString == null) {
            queryString = getQueryString();
        }
        return queryString;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        if (Strings.isNullOrEmpty(path)) {
            return null;
        }

        if (path.startsWith("/")) {
            return context.getRequestDispatcher(path);
        }

        String requestDispatchPath = "";
        String servletPath = getLastDispatchedServletPath();
        String pathInfo = getLastDispatchedPathInfo();

        if (servletPath != null) {
            requestDispatchPath += servletPath;
        }

        if (pathInfo != null) {
            requestDispatchPath += pathInfo;
        }

        int lastSlash = requestDispatchPath.lastIndexOf('/');
        if (lastSlash >= 0) {
            requestDispatchPath = requestDispatchPath.substring(0, lastSlash + 1);
        }

        requestDispatchPath += path;

        return context.getRequestDispatcher(requestDispatchPath);
    }

    /**
     *
     * @param type
     * @param request
     * @param response
     * @return
     */
    public ChannelRequest createChannel(DispatcherType type, ServletRequest request, ServletResponse response) {
        switch (type) {
            case REQUEST:
                this.channel = new ChannelRequest(this.channel);
                break;
            case INCLUDE:
                this.channel = new ChannelRequestInclude(this.channel);
                break;
            case FORWARD:
                this.channel = new ChannelRequestForward(this.channel);
                break;
            case ERROR:
                this.channel = new ChannelRequestError(this.channel);
                break;
            case ASYNC:
                channel = new ChannelRequestAsync(this.channel);
                break;
        }
        channels.push(this.channel);
        return this.channel;
    }

    /**
     *
     */
    public void deleteChannel() {
        channels.pop();
        this.channel = channels.peek();
    }

}
