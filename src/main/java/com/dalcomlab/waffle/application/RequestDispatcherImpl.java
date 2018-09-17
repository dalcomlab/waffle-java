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

import com.dalcomlab.waffle.common.Paths;
import com.dalcomlab.waffle.common.QueryString;
import com.dalcomlab.waffle.dispatch.Dispatch;
import com.dalcomlab.waffle.dispatch.ChannelledRequest;
import com.dalcomlab.waffle.dispatch.channel.ChannelRequest;

import javax.servlet.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Hashtable;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class RequestDispatcherImpl implements RequestDispatcher {

    private ApplicationContext context;
    protected String requestURI;

    /**
     * @param context
     * @param requestURI
     */
    public RequestDispatcherImpl(ApplicationContext context, String requestURI) {
        this.context = context;

        //
        // the given requestURI must be normalized. the requestURI can contain
        // single dot(.) in below cases.
        //
        // <jsp:include page="./include/header.jsp" flush="true">
        //	<jsp:param name="title" value="Forum" />
        // </jsp:include>
        //
        // requestURI : /forum/./include/header.jsp?title=Forum
        //

        this.requestURI = Paths.normalize(requestURI);
    }


    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        System.out.println("forward : " + requestURI);


        // Reset any output that has been buffered, but keep headers/cookies
        if (response.isCommitted()) {
            String message = MessageFormat.format("The servlet response already is committed.", null);
            throw new IllegalStateException(message);
        }

        try {
            response.resetBuffer();
        } catch (IllegalStateException e) {
            throw e;
        }

        ChannelRequest channel = this.createChannel(DispatcherType.FORWARD, request, response);
        try {

            Dispatch dispatch = context.dispatch(this.requestURI, request, response);
            if (dispatch != null) {
                String requestURI = dispatch.getRequestURI();
                String contextPath = dispatch.getContextPath();
                String servletPath = dispatch.getServletPath();
                String pathInfo = dispatch.getPathInfo();
                String queryString = dispatch.getQueryString();

                channel.setAttribute(RequestDispatcher.FORWARD_REQUEST_URI, requestURI);
                channel.setAttribute(RequestDispatcher.FORWARD_CONTEXT_PATH, contextPath);
                channel.setAttribute(RequestDispatcher.FORWARD_SERVLET_PATH, servletPath);
                channel.setAttribute(RequestDispatcher.FORWARD_PATH_INFO, pathInfo);
                channel.setAttribute(RequestDispatcher.FORWARD_QUERY_STRING, queryString);

                channel.setRequestURI(requestURI);
                channel.setContextPath(contextPath);
                channel.setServletPath(servletPath);
                channel.setPathInfo(pathInfo);
                channel.setQueryString(queryString);

                dispatch.dispatch();
            }
        } finally {
            this.deleteChannel(channel, request, response);
        }
    }


    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        System.out.println("include : " + requestURI);

        ChannelRequest channel = this.createChannel(DispatcherType.INCLUDE, request, response);
        try {

            Dispatch dispatch = context.dispatch(this.requestURI, request, response);
            if (dispatch != null) {
                String requestURI = dispatch.getRequestURI();
                String contextPath = dispatch.getContextPath();
                String servletPath = dispatch.getServletPath();
                String pathInfo = dispatch.getPathInfo();
                String queryString = dispatch.getQueryString();

                channel.setAttribute(RequestDispatcher.INCLUDE_REQUEST_URI, requestURI);
                channel.setAttribute(RequestDispatcher.INCLUDE_CONTEXT_PATH, contextPath);
                channel.setAttribute(RequestDispatcher.INCLUDE_SERVLET_PATH, servletPath);
                channel.setAttribute(RequestDispatcher.INCLUDE_PATH_INFO, pathInfo);
                channel.setAttribute(RequestDispatcher.INCLUDE_QUERY_STRING, queryString);

                channel.setRequestURI(requestURI);
                channel.setContextPath(contextPath);
                channel.setServletPath(servletPath);
                channel.setPathInfo(pathInfo);
                channel.setQueryString(queryString);

                dispatch.dispatch();
            }
        } finally {
            this.deleteChannel(channel, request, response);
        }
    }

    /**
     * @param dispatchType
     * @param request
     * @param response
     * @return
     */
    public ChannelRequest createChannel(DispatcherType dispatchType, ServletRequest request, ServletResponse response) {
        ChannelledRequest master = (ChannelledRequest) getMasterRequest(request);
        ChannelRequest channel = master.createChannel(dispatchType, request, response);

        if (requestURI != null) {
            int i = requestURI.indexOf("?");
            if (i != -1) {
                String query = requestURI.substring(i + 1);
                Hashtable<String, String[]> params = QueryString.parseQueryString(query);
                for (String name : params.keySet()) {
                    channel.setParameter(name, params.get(name));
                }
            }
        }

        return channel;
    }

    /**
     * @param channel
     * @param request
     * @param response
     */
    public void deleteChannel(ChannelRequest channel, ServletRequest request, ServletResponse response) {
        ChannelledRequest master = (ChannelledRequest) getMasterRequest(request);
        master.deleteChannel();
    }

    /**
     * @param request
     * @return
     */
    protected ServletRequest getMasterRequest(ServletRequest request) {
        ServletRequest original = request;
        while (original instanceof ServletRequestWrapper) {
            if (original instanceof ChannelledRequest) {
                return original;
            }
            original = ((ServletRequestWrapper) original).getRequest();
        }
        return original;
    }

}
