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

import com.dalcomlab.waffle.dispatch.Dispatch;
import com.dalcomlab.waffle.resource.Path;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ApplicationContextDebug extends ApplicationContext {

    ApplicationContext context;

    /**
     * Creates new instance.
     *
     */
    public ApplicationContextDebug(ApplicationContext context) {
        super(null, null);
        this.context = context;
    }

    /**
     * @throws Exception
     */
    public void initialize() throws Exception {
        context.initialize();
    }

    /**
     * @throws Exception
     */
    public void destroy() throws Exception {
        context.destroy();
    }


    public ListenerManager getListenerManager() {
        return  context.getListenerManager();
    }

    public ServletManager getServletManager() {
        return  context.getServletManager();
    }

    public FilterManager getFilterManager() {
        return  context.getFilterManager();
    }

    public SessionManager getSessionManager() {
        return context.getSessionManager();
    }

    public ResourceManager getResourceManager() {
        return context.getResourceManager();
    }

    public void setClassLoader(ClassLoader classloader) {
        context.setClassLoader(classloader);
    }

    @Override
    public String getContextPath() {
        System.out.println("ServletContext.getContextPath()");
        return context.getContextPath();
    }

    @Override
    public ServletContext getContext(String uripath) {
        System.out.println("ServletContext.getContext() : " + uripath);
        return context.getContext(uripath);
    }


    @Override
    public int getMajorVersion() {
        System.out.println("ServletContext.getMajorVersion()");
        return context.getMajorVersion();
    }


    @Override
    public int getMinorVersion() {
        System.out.println("ServletContext.getMinorVersion()");
        return context.getMinorVersion();
    }

    @Override
    public int getEffectiveMajorVersion() {
        System.out.println("ServletContext.getEffectiveMajorVersion()");
        return context.getEffectiveMajorVersion();
    }

    @Override
    public int getEffectiveMinorVersion() {
        System.out.println("ServletContext.getEffectiveMinorVersion()");
        return context.getEffectiveMinorVersion();
    }

    @Override
    public String getMimeType(String file) {
        System.out.println("ServletContext.getMimeType() : " + file);
        return context.getMimeType(file);
    }

    @Override
    public Set<String> getResourcePaths(String path) {
        System.out.println("ServletContext.getResourcePaths() : " + path);
        return context.getResourcePaths(path);
    }

    @Override
    public URL getResource(String path) throws MalformedURLException {
        System.out.println("ServletContext.getResource() : " + path);
        return context.getResource(path);
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        System.out.println("ServletContext.getResourceAsStream() : " + path);
        return context.getResourceAsStream(path);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        System.out.println("ServletContext.RequestDispatcher() : " + path);
        return context.getRequestDispatcher(path);
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String name) {
        System.out.println("ServletContext.getNamedDispatcher() : " + name);
        return context.getNamedDispatcher(name);
    }


    @Deprecated
    public Servlet getServlet(String name) throws ServletException {
        System.out.println("ServletContext.getServlet() : " + name);
        return context.getServlet(name);
    }


    @Deprecated
    public Enumeration<Servlet> getServlets() {
        System.out.println("ServletContext.getServlets()");
        return context.getServlets();
    }

    @Deprecated
    public Enumeration<String> getServletNames() {
        System.out.println("ServletContext.getServletNames()");
        return context.getServletNames();
    }

    @Override
    public void log(String msg) {
        System.out.println("ServletContext.log() : " + msg);
        context.log(msg);
    }

    @Deprecated
    public void log(Exception exception, String msg) {
        System.out.println("ServletContext.log(exception, msg) : " + msg);
        context.log(exception, msg);
    }


    @Override
    public void log(String message, Throwable throwable) {
        System.out.println("ServletContext.log(message, throwable) : " + message);
        context.log(message, throwable);
    }


    @Override
    public String getRealPath(String path) {
        System.out.println("ServletContext.getRealPath(path) : " + path);
        return context.getRealPath(path);
    }


    @Override
    public String getServerInfo() {
        System.out.println("ServletContext.getServerInfo()");
        return context.getServerInfo();
    }


    @Override
    public String getInitParameter(String name) {
        System.out.println("ServletContext.getInitParameter(name) : "  + name);
        return context.getInitParameter(name);
    }


    public Enumeration<String> getInitParameterNames() {
        System.out.println("ServletContext.getInitParameterNames()");
        return context.getInitParameterNames();
    }


    @Override
    public boolean setInitParameter(String name, String value) {
        System.out.println("ServletContext.setInitParameter(name, value) : " + name + "," + value);
        return context.setInitParameter(name, value);
    }


    @Override
    public Object getAttribute(String name) {
        System.out.println("ServletContext.getAttribute(name) : "  + name);
        return context.getAttribute(name);
    }


    @Override
    public Enumeration<String> getAttributeNames() {
        System.out.println("ServletContext.getAttributeNames()");
        return context.getAttributeNames();
    }


    @Override
    public void setAttribute(String name, Object object) {
        System.out.println("ServletContext.setAttribute(name, object) : " + name);
        context.setAttribute(name, object);
    }

    @Override
    public void removeAttribute(String name) {
        System.out.println("ServletContext.removeAttribute(name) : " + name);
        context.removeAttribute(name);
    }

    @Override
    public String getServletContextName() {
        System.out.println("ServletContext.getServletContextName()");
        return context.getServletContextName();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        System.out.println("ServletContext.addServlet(servletName, className) : " + servletName + "," + className);
        return context.addServlet(servletName, className);
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        System.out.println("ServletContext.addServlet(servletName, servlet) : " + servletName);
        return context.addServlet(servletName, servlet);
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName,
                                                  Class<? extends Servlet> servletClass) {
        System.out.println("ServletContext.addServlet(servletName, servletClass) : " + servletName);
        return context.addServlet(servletName, servletClass);
    }

    @Override
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
        System.out.println("ServletContext.addJspFile(servletName, jspFile) : " + servletName + "," + jspFile);
        return context.addJspFile(servletName, jspFile);
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        System.out.println("ServletContext.createServlet(clazz)");
        return context.createServlet(clazz);
    }


    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        System.out.println("ServletContext.getServletRegistration(servletName) : " + servletName);
        return context.getServletRegistration(servletName);
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        System.out.println("ServletContext.getServletRegistrations()");
        return context.getServletRegistrations();
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        System.out.println("ServletContext.addFilter(filterName, className)" + filterName + "," + className);
        return context.addFilter(filterName, className);
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        System.out.println("ServletContext.addFilter(filterName, filter)" + filterName );
        return context.addFilter(filterName, filter);
    }


    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
                                                Class<? extends Filter> filterClass) {
        System.out.println("ServletContext.addFilter(filterName, filterClass)" + filterName );
        return context.addFilter(filterName, filterClass);
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        System.out.println("ServletContext.createFilter(clazz)");
        return context.createFilter(clazz);
    }


    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        System.out.println("ServletContext.getFilterRegistration(filterName) : " + filterName);
        return context.getFilterRegistration(filterName);
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        System.out.println("ServletContext.getFilterRegistrations()");
        return context.getFilterRegistrations();
    }


    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        System.out.println("ServletContext.getSessionCookieConfig()");
        return context.getSessionCookieConfig();
    }


    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        System.out.println("ServletContext.setSessionTrackingModes()");
        context.setSessionTrackingModes(sessionTrackingModes);
    }


    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        System.out.println("ServletContext.getDefaultSessionTrackingModes()");
        return context.getDefaultSessionTrackingModes();
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        System.out.println("ServletContext.getEffectiveSessionTrackingModes()");
        return context.getEffectiveSessionTrackingModes();
    }


    @Override
    public void addListener(String className) {
        System.out.println("ServletContext.addListener(className) : " + className);
        context.addListener(className);
    }

    @Override
    public <T extends EventListener> void addListener(T t) {
        System.out.println("ServletContext.addListener(t)");
        context.addListener(t);
    }


    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        System.out.println("ServletContext.addListener(listenerClass)");
        context.addListener(listenerClass);
    }

    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        System.out.println("ServletContext.createListener(clazz)");
        return context.createListener(clazz);
    }


    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        System.out.println("ServletContext.getJspConfigDescriptor()");
        return context.getJspConfigDescriptor();
    }


    @Override
    public ClassLoader getClassLoader() {
        System.out.println("ServletContext.getClassLoader()");
        return context.getClassLoader();
    }

    @Override
    public void declareRoles(String... roleNames) {
        System.out.println("ServletContext.declareRoles()");
        context.declareRoles(roleNames);
    }

    @Override
    public String getVirtualServerName() {
        System.out.println("ServletContext.getVirtualServerName()");
        return context.getVirtualServerName();
    }


    @Override
    public int getSessionTimeout() {
        System.out.println("ServletContext.getSessionTimeout()");
        return context.getSessionTimeout();
    }


    @Override
    public void setSessionTimeout(int sessionTimeout) {
        System.out.println("ServletContext.setSessionTimeout()");
        context.setSessionTimeout(sessionTimeout);
    }


    @Override
    public String getRequestCharacterEncoding() {
        System.out.println("ServletContext.getRequestCharacterEncoding()");
        return context.getRequestCharacterEncoding();
    }


    @Override
    public void setRequestCharacterEncoding(String encoding) {
        System.out.println("ServletContext.setRequestCharacterEncoding(encoding) : " + encoding);
        context.setRequestCharacterEncoding(encoding);
    }


    @Override
    public String getResponseCharacterEncoding() {
        System.out.println("ServletContext.getResponseCharacterEncoding()");
        return context.getResponseCharacterEncoding();
    }


    @Override
    public void setResponseCharacterEncoding(String encoding) {
        System.out.println("ServletContext.setResponseCharacterEncoding(encoding) : " + encoding);
        context.setResponseCharacterEncoding(encoding);
    }


    /**
     *
     * @return
     */
    boolean isContextInitialized() {
        System.out.println("ServletContext.isContextInitialized()");
        return context.isContextInitialized();
    }


    /**
     *
     * @param path
     * @return
     */
    public ServletDelegate getServlet(Path path) {
        System.out.println("ServletContext.getServlet(path)");
        return context.getServlet(path);
    }

    /**
     *
     * @param path
     * @param type
     * @return
     */
    public FilterDelegate[] getFilters(Path path, DispatcherType type) {
        return context.getFilters(path, type);
    }

    /**
     *
     * @param servletName
     * @return
     */
    public FilterDelegate[] getFilters(String servletName) {
        return context.getFilters(servletName);
    }


    //

    public Dispatch dispatch(String path, ServletRequest request, ServletResponse response)  {
        return context.dispatch(path, request, response);
    }

}
