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

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class RequestAdaptorDebug extends HttpServletRequestWrapper {

    private HttpServletRequest request = null;

    public  RequestAdaptorDebug(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getAuthType() {
        System.out.println("HttpServletRequest API : getAuthType()");
        if (request != null) {
            return request.getAuthType();
        }
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        System.out.println("HttpServletRequest API : getCookies() : ");
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    System.out.println("   name : " + cookie.getName() + ", value : " + cookie.getValue());
                }
            } else {
                System.out.println("---- return : null");
            }
            return cookies;
        }
        return null;
    }


    @Override
    public long getDateHeader(String name) {
        System.out.println("HttpServletRequest API : getDateHeader(String name) : " + name);
        if (request != null) {
            return request.getDateHeader(name);
        }
        return 0;
    }


    @Override
    public String getHeader(String name) {
        System.out.println("HttpServletRequest API : getHeader(String name) : " + name);
        if (request != null) {
            System.out.println("---- return : " + request.getHeader(name));
            return request.getHeader(name);
        }
        return null;
    }


    @Override
    public Enumeration<String> getHeaders(String name) {
        System.out.println("HttpServletRequest API : getHeaders(String name) : " + name);
        if (request != null) {
            return request.getHeaders(name);
        }
        return null;
    }


    @Override
    public Enumeration<String> getHeaderNames() {
        System.out.println("HttpServletRequest API : getHeaderNames()");
        if (request != null) {
            return request.getHeaderNames();
        }
        return null;
    }

    @Override
    public int getIntHeader(String name) {
        System.out.println("HttpServletRequest API : getIntHeader(String name) : " + name);
        if (request != null) {
            return request.getIntHeader(name);
        }
        return 0;
    }


    @Override
    public String getMethod() {
        System.out.println("HttpServletRequest API : getMethod()");
        if (request != null) {
            System.out.println("---- return : " + request.getMethod());
            return request.getMethod();
        }
        return null;
    }


    @Override
    public String getPathInfo() {
        System.out.println("HttpServletRequest API : getPathInfo()");
        if (request != null) {
            System.out.println("---- return : " + request.getPathInfo());
            return request.getPathInfo();
        }
        return null;
    }


    @Override
    public String getPathTranslated() {
        System.out.println("HttpServletRequest API : getPathTranslated()");
        if (request != null) {
            return request.getPathTranslated();
        }
        return null;
    }


    @Override
    public String getContextPath() {
        System.out.println("HttpServletRequest API : getContextPath()");
        if (request != null) {
            System.out.println("---- return : " + request.getContextPath());
            return request.getContextPath();
        }
        return null;
    }


    @Override
    public String getQueryString() {
        System.out.println("HttpServletRequest API : getQueryString()");
        if (request != null) {
            System.out.println("---- return : " + request.getQueryString());
            return request.getQueryString();
        }
        return null;
    }


    @Override
    public String getRemoteUser() {
        System.out.println("HttpServletRequest API : getRemoteUser()");
        if (request != null) {
            return request.getRemoteUser();
        }
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        System.out.println("HttpServletRequest API : isUserInRole(String role) : " + role);
        if (request != null) {
            return request.isUserInRole(role);
        }
        return false;
    }


    @Override
    public Principal getUserPrincipal() {
        System.out.println("HttpServletRequest API : getUserPrincipal()");
        if (request != null) {
            return request.getUserPrincipal();
        }
        return null;
    }


    @Override
    public String getRequestedSessionId() {
        System.out.println("HttpServletRequest API : getRequestedSessionId()");
        if (request != null) {
            return request.getRequestedSessionId();
        }
        return null;
    }


    @Override
    public String getRequestURI() {
        System.out.println("HttpServletRequest API : getRequestURI()");
        if (request != null) {
            System.out.println("---- return : " + request.getRequestURI());
            return request.getRequestURI();
        }
        return null;
    }


    @Override
    public StringBuffer getRequestURL() {
        System.out.println("HttpServletRequest API : getRequestURL()");
        if (request != null) {
            return request.getRequestURL();
        }
        return null;
    }


    @Override
    public String getServletPath() {
        System.out.println("HttpServletRequest API : getServletPath()");
        if (request != null) {
            System.out.println(" ---- return : " + request.getServletPath());
            return request.getServletPath();
        }
        return null;
    }

    @Override
    public HttpSession getSession(boolean create) {
        System.out.println("HttpServletRequest API : getSession(boolean create) : " + create);
        if (request != null) {
            HttpSession session = request.getSession(create);
            if (session == null) {
                System.out.println(" ---- return : null");
            } else {
                System.out.println(" ---- return : id -> " + session.getId());
            }
            return session;
        }
        return null;
    }


    @Override
    public HttpSession getSession() {
        System.out.println("HttpServletRequest API : getSession()");
        if (request != null) {
            return request.getSession();
        }
        return null;
    }


    @Override
    public String changeSessionId() {
        System.out.println("HttpServletRequest API : changeSessionId()");
        if (request != null) {
            return request.changeSessionId();
        }
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        System.out.println("HttpServletRequest API : isRequestedSessionIdValid()");
        if (request != null) {
            return request.isRequestedSessionIdValid();
        }
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        System.out.println("HttpServletRequest API : isRequestedSessionIdFromCookie()");
        if (request != null) {
            return request.isRequestedSessionIdFromCookie();
        }
        return false;
    }


    @Override
    public boolean isRequestedSessionIdFromURL() {
        System.out.println("HttpServletRequest API : isRequestedSessionIdFromURL()");
        if (request != null) {
            return request.isRequestedSessionIdFromURL();
        }
        return false;
    }


    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        System.out.println("HttpServletRequest API : isRequestedSessionIdFromUrl()");
        if (request != null) {
            return request.isRequestedSessionIdFromUrl();
        }
        return false;
    }


    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        System.out.println("HttpServletRequest API : authenticate(HttpServletResponse response)");
        if (request != null) {
            return request.authenticate(response);
        }
        return false;
    }


    @Override
    public void login(String username, String password) throws ServletException {
        System.out.println("HttpServletRequest API : login(String username, String password)");
        if (request != null) {
            request.login(username, password);
        }
    }


    @Override
    public void logout() throws ServletException {
        System.out.println("HttpServletRequest API : logout()");
        if (request != null) {
            request.logout();
        }
    }


    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        System.out.println("HttpServletRequest API : getParts()");
        if (request != null) {
            return request.getParts();
        }
        return null;
    }


    @Override
    public Part getPart(String name) throws IOException, ServletException {
        System.out.println("HttpServletRequest API : getPart(String name) : " + name);
        if (request != null) {
            return request.getPart(name);
        }
        return null;
    }


    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        System.out.println("HttpServletRequest API : upgrade(Class<T> handlerClass)");
        if (request != null) {
            return request.upgrade(handlerClass);
        }
        return null;
    }


    @Override
    public Object getAttribute(String name) {
        System.out.println("HttpServletRequest API : getAttribute(String name) : " + name);
        if (request != null) {
            Object value = request.getAttribute(name);
            if (value == null) {
                System.out.println("---- return : null");
            } else {
                System.out.println("---- return : " + value.toString());
            }
            return value;
        }
        return null;
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        System.out.println("HttpServletRequest API : getAttributeNames()");
        if (request != null) {
            return request.getAttributeNames();
        }
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        System.out.println("HttpServletRequest API : getCharacterEncoding()");
        if (request != null) {
            System.out.println("---- return : " + request.getCharacterEncoding());
            return request.getCharacterEncoding();
        }
        return null;
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        System.out.println("HttpServletRequest API : setCharacterEncoding(String env) : " + env);
        if (request != null) {
            request.setCharacterEncoding(env);
        }
    }

    @Override
    public int getContentLength() {
        System.out.println("HttpServletRequest API : getContentLength()");
        if (request != null) {
            return request.getContentLength();
        }
        return 0;
    }


    @Override
    public long getContentLengthLong() {
        System.out.println("HttpServletRequest API : getContentLengthLong()");
        if (request != null) {
            return request.getContentLengthLong();
        }
        return 0;
    }

    @Override
    public String getContentType() {
        System.out.println("HttpServletRequest API : getContentType()");
        if (request != null) {
            System.out.println("---- return : " + request.getContentType());
            return request.getContentType();
        }
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        System.out.println("HttpServletRequest API : getInputStream()");
        if (request != null) {
            ServletInputStream input = request.getInputStream();
            if (input != null) {
                System.out.println("---- return : " + input.available() + " bytes.");
            } else {
                System.out.println("---- return : null");
            }
            return input;
        }
        return null;
    }

    @Override
    public String getParameter(String name) {
        System.out.println("HttpServletRequest API : getParameter(String name) : " + name);
        if (request != null) {
            System.out.println("---- return : " + request.getParameter(name));
            return request.getParameter(name);
        }
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        System.out.println("HttpServletRequest API : getParameterNames()");
        if (request != null) {
            return request.getParameterNames();
        }
        return null;
    }

    @Override
    public String[] getParameterValues(String name) {
        System.out.println("HttpServletRequest API : getParameterValues(String name) : " + name);
        if (request != null) {
            return request.getParameterValues(name);
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        System.out.println("HttpServletRequest API : getInitParameterMap()");
        if (request != null) {
            return request.getParameterMap();
        }
        return null;
    }


    @Override
    public String getProtocol() {
        System.out.println("HttpServletRequest API : getProtocol()");
        if (request != null) {
            System.out.println("---- return : " + request.getProtocol());
            return request.getProtocol();
        }
        return null;
    }

    @Override
    public String getScheme() {
        System.out.println("HttpServletRequest API : getScheme()");
        if (request != null) {
            System.out.println("---- return : " + request.getScheme());
            return request.getScheme();
        }
        return null;
    }


    @Override
    public String getServerName() {
        System.out.println("HttpServletRequest API : getServerName()");
        if (request != null) {
            System.out.println("---- return : " + request.getServerName());
            return request.getServerName();
        }
        return null;
    }


    @Override
    public int getServerPort() {
        System.out.println("HttpServletRequest API : getServerPort()");
        if (request != null) {
            System.out.println("---- return : " + request.getServerPort());
            return request.getServerPort();
        }
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        System.out.println("HttpServletRequest API : getReader()");
        if (request != null) {
            return request.getReader();
        }
        return null;
    }

    @Override
    public String getRemoteAddr() {
        System.out.println("HttpServletRequest API : getRemoteAddr()");
        if (request != null) {
            System.out.println("---- return : " + request.getRemoteAddr());
            return request.getRemoteAddr();
        }
        return null;
    }

    @Override
    public String getRemoteHost() {
        System.out.println("HttpServletRequest API : getRemoteHost()");
        if (request != null) {
            System.out.println("---- return : " + request.getRemoteHost());
            return request.getRemoteHost();
        }
        return null;
    }


    @Override
    public void setAttribute(String name, Object o) {
        System.out.println("HttpServletRequest API : setAttribute(String name, Object o) : " + name);
        if (request != null) {
            request.setAttribute(name, o);
        }
    }

    @Override
    public void removeAttribute(String name) {
        System.out.println("HttpServletRequest API : removeAttribute(String name) : " + name);
        if (request != null) {
            request.removeAttribute(name);
        }
    }

    @Override
    public Locale getLocale() {
        System.out.println("HttpServletRequest API : getLocale()");
        if (request != null) {
            return request.getLocale();
        }
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        System.out.println("HttpServletRequest API : getLocales()");
        if (request != null) {
            return request.getLocales();
        }
        return null;
    }

    @Override
    public boolean isSecure() {
        System.out.println("HttpServletRequest API : isSecure() : " );
        if (request != null) {
            System.out.println("---- return : " + request.isSecure());
            return request.isSecure();
        }
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        System.out.println("HttpServletRequest API : getRequestDispatcher(String path) : " + path);
        if (request != null) {
            return request.getRequestDispatcher(path);
        }
        return null;
    }


    public String getRealPath(String path) {
        System.out.println("HttpServletRequest API : getRealPath(String path) : " + path);
        if (request != null) {
            System.out.println("---- return : " + request.getRealPath(path));
            return request.getRealPath(path);
        }
        return null;
    }

    @Override
    public int getRemotePort() {
        System.out.println("HttpServletRequest API : getRemotePort()");
        if (request != null) {
            System.out.println("---- return : " + request.getRemotePort());
            return request.getRemotePort();
        }
        return 0;
    }

    @Override
    public String getLocalName() {
        System.out.println("HttpServletRequest API : getLocalName()");
        if (request != null) {
            System.out.println("---- return : " + request.getLocalName());
            return request.getLocalName();
        }
        return null;
    }

    @Override
    public String getLocalAddr() {
        System.out.println("HttpServletRequest API : getLocalAddr()");
        if (request != null) {
            System.out.println("---- return : " + request.getLocalAddr());
            return request.getLocalAddr();
        }
        return null;
    }

    @Override
    public int getLocalPort() {
        System.out.println("HttpServletRequest API : getLocalPort()");
        if (request != null) {
            System.out.println("---- return : " + request.getLocalPort());
            return request.getLocalPort();
        }
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        System.out.println("HttpServletRequest API : getServletContext()");
        if (request != null) {
            return request.getServletContext();
        }
        return null;
    }


    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        System.out.println("HttpServletRequest API : startAsync()");
        if (request != null) {
            return request.startAsync();
        }
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        System.out.println("HttpServletRequest API : startAsync(ServletRequest servletRequest, ServletResponse servletResponse)");
        if (request != null) {
            return request.startAsync(servletRequest, servletResponse);
        }
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        System.out.println("HttpServletRequest API : isAsyncStarted()");
        if (request != null) {
            return request.isAsyncStarted();
        }
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        System.out.println("HttpServletRequest API : isAsyncSupported()");
        if (request != null) {
            return request.isAsyncSupported();
        }
        return false;
    }


    @Override
    public AsyncContext getAsyncContext() {
        System.out.println("HttpServletRequest API : getAsyncContext()");
        if (request != null) {
            return request.getAsyncContext();
        }
        return null;
    }


    @Override
    public DispatcherType getDispatcherType() {
        System.out.println("HttpServletRequest API : getDispatcherType()");
        if (request != null) {
            return request.getDispatcherType();
        }
        return null;
    }
}
