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

import com.dalcomlab.waffle.application.*;
import com.dalcomlab.waffle.dispatch.action.HttpActionSetCookie;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.security.Principal;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class HttpRequest implements HttpServletRequest {

    protected Http http;
    protected ApplicationContext context;
    protected String authType;
    protected AttributesMap attributes = new AttributesMap();
    protected HttpSessionDelegate session = null;
    protected DispatcherType type = DispatcherType.REQUEST;
    protected ArrayList<Locale> locales = new ArrayList<>();
    protected String requestURI;
    protected String contextPath;
    protected String servletPath;
    protected String pathInfo;
    protected String queryString;


    public HttpRequest(ApplicationContext context) {
        this.context = context;
    }


    public static HttpRequest getRequest(ServletRequest request) {
        HttpRequest base = null;
        while (request instanceof HttpServletRequestWrapper) {
            request = ((HttpServletRequestWrapper) request).getRequest();
        }
        if (request instanceof HttpRequest) {
            base = (HttpRequest) request;
        }
        return base;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setSession(HttpSessionDelegate session) {
        this.session = session;
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

    public void setHttp(Http http) {
        this.http = http;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }


    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }


    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }


    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }


    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }


    @Override
    public String getAuthType() {
        return authType;
    }


    @Override
    public Cookie[] getCookies() {
        return null;
    }

    @Override
    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value != null) {
            return Long.parseLong(value);
        }
        return 0;
    }

    @Override
    public String getHeader(String name) {
        Enumeration<String> headers = getHeaders(name);
        if (headers == null) {
            return null;
        }
        return headers.nextElement();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return null;
    }


    @Override
    public Enumeration<String> getHeaderNames() {
        return null;
    }


    @Override
    public int getIntHeader(String name) {
        String value = getHeader(name);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    @Override
    public HttpServletMapping getHttpServletMapping() {
        return null;
    }


    @Override
    public String getMethod() {
        return null;
    }

    @Override
    public String getPathInfo() {
        return pathInfo;
    }


    @Override
    public String getPathTranslated() {
        String pathInfo = getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        return getRealPath(pathInfo);
    }


    @Override
    public String getContextPath() {
        return contextPath;
    }


    @Override
    public String getQueryString() {
        return queryString;
    }


    @Override
    public String getRemoteUser() {
        return null;
    }


    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }


    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }


    @Override
    public StringBuffer getRequestURL() {
        StringBuffer sb = new StringBuffer();
        sb.append(getScheme());
        sb.append("://");
        sb.append(getServerName());
        int port = getServerPort();
        if (port > 0) {
            sb.append(":");
            sb.append(port);
        }
        sb.append(getRequestURI());
        return sb;
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (session != null) {
            if (!session.isExpired()) {
                return session;
            }
        }
        SessionManager sessionManager = context.getSessionManager();
        assert sessionManager != null;

        // the token name of a session is usually 'JSESSIONID'.
        String sessionTokenName = sessionManager.getSessionTokenName();
        Cookie[] cookies = getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                if (name.equals(sessionTokenName)) {
                    session = sessionManager.findSession(value);
                    break;
                }
            }
        }

        if (session == null && create == true) {
            session = context.getSessionManager().createSession();
            String name = sessionTokenName;
            String value = session.getId();
            http.onFlush().add(new HttpActionSetCookie(session.getCookie()));
        }
        return session;
    }


    @Override
    public HttpSession getSession() {
        return getSession(true);
    }


    @Override
    public String changeSessionId() {
        return null;
    }


    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }


    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }


    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    @Override
    public void login(String username, String password) throws ServletException {
    }


    @Override
    public void logout() throws ServletException {
    }


    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        return attributes.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return attributes.getAttributeNames();
    }


    @Override
    public String getCharacterEncoding() {
        return "UTF-8";
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public long getContentLengthLong() {
        return 0;
    }

    @Override
    public String getContentType() {
        String contentType = getHeader("Content-Type");
        //application/x-git-upload-pack-request
        return contentType;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public String getParameter(String name) {

        Map<String, String[]> parameters = getParameterMap();
        if (parameters == null) {
            return "";
        }
        String[] values = parameters.get(name);

        if (values == null) {
            return "";
        }
        return values[0];

    }

    @Override
    public Enumeration<String> getParameterNames() {
        Map<String, String[]> parameters = getParameterMap();
        if (parameters != null) {
            return Collections.enumeration(parameters.keySet());
        }
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        Map<String, String[]> parameters = getParameterMap();
        if (parameters != null) {
            return parameters.get(name);
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }


    @Override
    public String getProtocol() {
        return null;
    }


    @Override
    public String getScheme() {
        return "http";
    }


    @Override
    public String getServerName() {
        return null;
    }


    @Override
    public int getServerPort() {
        return 0;
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }


    @Override
    public String getRemoteAddr() {
        return "127.0.0.0";
    }


    @Override
    public String getRemoteHost() {
        return "127.0.0.0";
    }


    @Override
    public void setAttribute(String name, Object value) {
        if (name == null) {
            return;
        }

        if (value == null) {
            removeAttribute(name);
            return;
        }


        if (context != null) {
            Object unbound = attributes.setAttribute(name, value);
            if (unbound == null) {
                context.getListenerManager().fireServletRequestAttributeAdded(this, name, value);
            } else {
                context.getListenerManager().fireServletRequestAttributeReplaced(this, name, unbound);
            }
        }

    }

    @Override
    public void removeAttribute(String name) {
        if (name == null || name.length() == 0) {
            return;
        }

        // we don't need to notify the remove event if the attribute have no given name.
        if (!attributes.containsAttribute(name)) {
            return;
        }

        if (context != null) {
            Object value = attributes.removeAttribute(name);
            context.getListenerManager().fireServletRequestAttributeRemoved(this, name, value);
        }

    }


    @Override
    public Locale getLocale() {
        getLocales();
        return this.locales.get(0);
    }

    @Override
    public Enumeration<Locale> getLocales() {
        if (this.locales.size() > 0) {
            Collections.enumeration(this.locales);
        }

        this.locales.add(Locale.getDefault());

        Enumeration<String> headers = getHeaders("Accept-Language");
        if (headers == null) {
            return Collections.enumeration(this.locales);
        }


        // https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language
        //
        // Accept-Language: <language>
        // Accept-Language: <locale>
        // Accept-Language: *
        //
        // Multiple types, weighted with the quality value syntax:
        // Accept-Language: fr-CH, fr;q=0.9, en;q=0.8, de;q=0.7, *;q=0.5
        //
        while (headers.hasMoreElements()) {
            String acceptLanguage = headers.nextElement();
            if (acceptLanguage == null || acceptLanguage.length() == 0) {
                continue;
            }

            String[] accepts = acceptLanguage.split(",");
            for (String accept : accepts) {
                String language = accept.trim();
                String country = "";
                int dash = language.indexOf('-');
                if (dash > -1) {
                    country = language.substring(dash + 1).trim();
                    language = language.substring(0, dash).trim();
                }

                int quality = language.indexOf(';');
                if (quality > -1) {
                    language = language.substring(0, quality).trim();
                }

                if (language.equals("*")) {
                    continue;
                }

                this.locales.add(new Locale(language, country));
            }
        }
        return Collections.enumeration(this.locales);
    }


    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        String requestDispatchPath = path;
        if (requestDispatchPath.startsWith("/")) {
            return context.getRequestDispatcher(requestDispatchPath);
        }

        RequestDispatcherImpl dispatcher = (RequestDispatcherImpl) context.getRequestDispatcher(requestDispatchPath);
        if (dispatcher != null) {
        }
        return dispatcher;
    }

    @Override
    public String getRealPath(String path) {
        if (context == null) {
            return null;
        }
        return context.getRealPath(path);
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        String hostName;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            hostName = "localhost";
        }
        return hostName;
    }


    @Override
    public String getLocalAddr() {
        String localAddress;
        try {
            localAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            localAddress = "127.0.0.1";
        }

        return localAddress;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }


    @Override
    public ServletContext getServletContext() {
        return context;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }


    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }


    @Override
    public DispatcherType getDispatcherType() {
        return type;
    }

}