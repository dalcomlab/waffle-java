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
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ServletDelegate extends RegistrationImpl
        implements ServletConfig, ServletRegistration.Dynamic, Delegate<Servlet> {

    protected Servlet servlet = null;
    protected int loadOnStartup = -1;
    protected String roleName = null;
    protected boolean enabled = false;
    protected ServletManager manager = null;
    protected boolean initialized = false;
    protected ServiceUnavailable unavailable = null;

    /**
     *
     */
    protected ServletDelegate(ServletManager manager) {
        this.manager = manager;
    }

    /**
     * Creates a servlet delegate with a given information.
     *
     * @param manager
     * @param servletName
     * @param className
     * @param loadOnStartup
     * @param roleName
     * @param asyncSupported
     * @param enabled
     * @param initParameters
     * @return
     */
    public static ServletDelegate createServlet(ServletManager manager,
                                                String servletName,
                                                String className,
                                                int loadOnStartup,
                                                String roleName,
                                                boolean asyncSupported,
                                                boolean enabled,
                                                Map<String, String> initParameters) {

        if (manager == null) {
            return null;
        }

        if (servletName == null || servletName.length() == 0) {
            return null;
        }

        if (className == null || className.length() == 0) {
            return null;
        }

        ServletDelegate delegate = new ServletDelegate(manager);
        delegate.servlet = null;
        delegate.name = servletName;
        delegate.className = className;
        delegate.loadOnStartup = loadOnStartup;
        delegate.roleName = roleName;
        delegate.isAsyncSupported = asyncSupported;
        delegate.enabled = enabled;
        if (initParameters != null) {
            delegate.initParameters = new HashMap<>(initParameters);
        }
        return delegate;
    }

    /**
     * Creates a servlet delegate with a given name of a servlet and name of a class.
     *
     * @param manager
     * @param servletName
     * @param className
     * @return
     */
    public static ServletDelegate createServlet(ServletManager manager, String servletName, String className) {
        if (manager == null) {
            return null;
        }

        if (manager.getContext().getClassLoader() == null) {
            return null;
        }

        Class<?> servletClass = null;
        try {
            servletClass = manager.getContext().getClassLoader().loadClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServletDelegate.createServlet(manager, servletName, (Class<? extends Servlet>) servletClass);
    }

    /**
     * Creates a servlet delegate with a given name of a servlet and a servlet instance.
     *
     * @param manager
     * @param servletName
     * @param servlet
     * @return
     */
    public static ServletDelegate createServlet(ServletManager manager, String servletName, Servlet servlet) {
        if (manager == null) {
            return null;
        }

        if (servletName == null || servletName.length() == 0) {
            return null;
        }

        if (servlet == null) {
            return null;
        }

        ServletDelegate delegate = new ServletDelegate(manager);
        delegate.name = servletName;
        delegate.servlet = servlet;
        return delegate;
    }

    /**
     * Creates a servlet delegate with a given name of a servlet and a class instance.
     *
     * @param manager
     * @param servletName
     * @param servletClass
     * @return
     */
    public static ServletDelegate createServlet(ServletManager manager, String servletName, Class<? extends Servlet> servletClass) {
        if (manager == null) {
            return null;
        }

        if (servletName == null || servletName.length() == 0) {
            return null;
        }

        if (servletClass == null) {
            return null;
        }

        ServletDelegate delegate = new ServletDelegate(manager);
        delegate.name = servletName;
        delegate.className = servletClass.getName();
        delegate.servlet = delegate.createServlet(delegate.className);
        return delegate;
    }


    // Delegate<T> implementation

    /**
     * @return
     */
    @Override
    public Servlet delegate() {
        return servlet;
    }

    // ServletRegistration.Dynamic implementation

    /**
     * Sets the <code>loadOnStartup</code> priority on the Servlet
     * represented by this dynamic ServletRegistration.
     *
     * <p>A <tt>loadOnStartup</tt> value of greater than or equal to
     * zero indicates to the container the initialization priority of
     * the Servlet. In this case, the container must instantiate and
     * initialize the Servlet during the initialization phase of the
     * ServletContext, that is, after it has invoked all of the
     * ServletContextListener objects configured for the ServletContext
     * at their {@link ServletContextListener#contextInitialized}
     * method.
     *
     * <p>If <tt>loadOnStartup</tt> is a negative integer, the container
     * is free to instantiate and initialize the Servlet lazily.
     *
     * <p>The default value for <tt>loadOnStartup</tt> is <code>-1</code>.
     *
     * <p>A call to this method overrides any previous setting.
     *
     * @param loadOnStartup the initialization priority of the Servlet
     * @throws IllegalStateException if the ServletContext from which
     *                               this ServletRegistration was obtained has already been initialized
     */
    @Override
    public void setLoadOnStartup(int loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * Sets the {@link ServletSecurityElement} to be applied to the
     * mappings defined for this <code>ServletRegistration</code>.
     *
     * <p>This method applies to all mappings added to this
     * <code>ServletRegistration</code> up until the point that the
     * <code>ServletContext</code> from which it was obtained has been
     * initialized.
     *
     * <p>If a URL pattern of this ServletRegistration is an exact target
     * of a <code>security-constraint</code> that was established via
     * the portable deployment descriptor, then this method does not
     * change the <code>security-constraint</code> for that pattern,
     * and the pattern will be included in the return value.
     *
     * <p>If a URL pattern of this ServletRegistration is an exact
     * target of a security constraint that was established via the
     * {@link javax.servlet.annotation.ServletSecurity} annotation
     * or a previous call to this method, then this method replaces
     * the security constraint for that pattern.
     *
     * <p>If a URL pattern of this ServletRegistration is neither the
     * exact target of a security constraint that was established via
     * the {@link javax.servlet.annotation.ServletSecurity} annotation
     * or a previous call to this method, nor the exact target of a
     * <code>security-constraint</code> in the portable deployment
     * descriptor, then this method establishes the security constraint
     * for that pattern from the argument
     * <code>ServletSecurityElement</code>.
     *
     * <p>The returned set is not backed by the {@code Dynamic} object,
     * so changes in the returned set are not reflected in the
     * {@code Dynamic} object, and vice-versa.</p>
     *
     * @param constraint the {@link ServletSecurityElement} to be applied
     *                   to the patterns mapped to this ServletRegistration
     * @return the (possibly empty) Set of URL patterns that were already
     * the exact target of a <code>security-constraint</code> that was
     * established via the portable deployment descriptor. This method
     * has no effect on the patterns included in the returned set
     * @throws IllegalArgumentException if <tt>constraint</tt> is null
     * @throws IllegalStateException    if the {@link ServletContext} from
     *                                  which this <code>ServletRegistration</code> was obtained has
     *                                  already been initialized
     */
    @Override
    public Set<String> setServletSecurity(ServletSecurityElement constraint) {
        return null;
    }

    /**
     * Sets the {@link MultipartConfigElement} to be applied to the
     * mappings defined for this <code>ServletRegistration</code>. If this
     * method is called multiple times, each successive call overrides the
     * effects of the former.
     *
     * @param multipartConfig the {@link MultipartConfigElement} to be
     *                        applied to the patterns mapped to the registration
     * @throws IllegalArgumentException if <tt>multipartConfig</tt> is
     *                                  null
     * @throws IllegalStateException    if the {@link ServletContext} from
     *                                  which this ServletRegistration was obtained has already been
     *                                  initialized
     */
    @Override
    public void setMultipartConfig(MultipartConfigElement multipartConfig) {

    }

    /**
     * Sets the name of the <code>runAs</code> role for this
     * <code>ServletRegistration</code>.
     *
     * @param roleName the name of the <code>runAs</code> role
     * @throws IllegalArgumentException if <tt>roleName</tt> is null
     * @throws IllegalStateException    if the {@link ServletContext} from
     *                                  which this ServletRegistration was obtained has already been
     *                                  initialized
     */

    @Override
    public void setRunAsRole(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Adds a servlet mapping with the given URL patterns for the Servlet
     * represented by this ServletRegistration.
     *
     * <p>If any of the specified URL patterns are already mapped to a
     * different Servlet, no updates will be performed.
     *
     * <p>If this method is called multiple times, each successive call
     * adds to the effects of the former.
     *
     * <p>The returned set is not backed by the {@code ServletRegistration}
     * object, so changes in the returned set are not reflected in the
     * {@code ServletRegistration} object, and vice-versa.</p>
     *
     * @param urlPatterns the URL patterns of the servlet mapping
     * @return the (possibly empty) Set of URL patterns that are already
     * mapped to a different Servlet
     * @throws IllegalArgumentException if <tt>urlPatterns</tt> is null
     *                                  or empty
     * @throws IllegalStateException    if the ServletContext from which this
     *                                  ServletRegistration was obtained has already been initialized
     */
    @Override
    public Set<String> addMapping(String... urlPatterns) {
        return manager.addMapping(getServletName(), urlPatterns);
    }

    /**
     * Gets the currently available mappings of the
     * Servlet represented by this <code>ServletRegistration</code>.
     *
     * <p>If permitted, any changes to the returned <code>Collection</code> must not
     * affect this <code>ServletRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently
     * available mappings of the Servlet represented by this
     * <code>ServletRegistration</code>
     */
    @Override
    public Collection<String> getMappings() {
        return manager.getMappings(getServletName());
    }

    /**
     * Gets the name of the runAs role of the Servlet represented by this
     * <code>ServletRegistration</code>.
     *
     * @return the name of the runAs role, or null if the Servlet is
     * configured to run as its caller
     */
    @Override
    public String getRunAsRole() {
        return this.roleName;
    }


    // ServletConfig implementation

    /**
     * Returns the name of this servlet instance.
     * The name may be provided via server administration, assigned in the
     * web application deployment descriptor, or for an unregistered (and thus
     * unnamed) servlet instance it will be the servlet's class name.
     *
     * @return the name of the servlet instance
     */
    public String getServletName() {
        return this.getName();
    }


    /**
     * Returns a reference to the {@link ServletContext} in which the caller
     * is executing.
     *
     * @return a {@link ServletContext} object, used
     * by the caller to interact with its servlet container
     * @see ServletContext
     */
    public ServletContext getServletContext() {
        return manager.getContext();
    }

    /**
     * Returns the names of the servlet's initialization parameters
     * as an <code>Enumeration</code> of <code>String</code> objects,
     * or an empty <code>Enumeration</code> if the servlet has
     * no initialization parameters.
     *
     * @return an <code>Enumeration</code> of <code>String</code>
     * objects containing the names of the servlet's
     * initialization parameters
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        if (this.initParameters == null) {
            return Collections.emptyEnumeration();
        }
        return Collections.enumeration(this.initParameters.keySet());
    }

    // Servlet implementation

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being placed into service.
     *
     * <p>The servlet container calls the <code>init</code>
     * method exactly once after instantiating the servlet.
     * The <code>init</code> method must complete successfully
     * before the servlet can receive any requests.
     *
     * <p>The servlet container cannot place the servlet into service
     * if the <code>init</code> method
     * <ol>
     * <li>Throws a <code>ServletException</code>
     * <li>Does not return within a time period defined by the Web server
     * </ol>
     *
     * @throws ServletException if an exception has occurred that
     *                          interferes with the servlet's normal
     *                          operation
     * @see UnavailableException
     * @see #getServletConfig
     */
    public void init() throws ServletException {
        if (initialized) {
            return;
        }


        if (servlet == null) {
            if (className == null || className.length() == 0) {
                return;
            }
            servlet = createServlet(className);
        }

        if (servlet == null) {
            throw new ServletException("fail to initialize the servlet.");
        }

        try {
            servlet.init(this);
        } catch (UnavailableException e) {
            // makeUnavailable(e);
            servlet = null;
            unavailable = new ServiceUnavailable();
            throw e;
        } catch (ServletException e) {
            //makeUnavailable(e.getCause()==null?e:e.getCause());
            servlet = null;
            unavailable = new ServiceUnavailable();
            throw e;
        } catch (Exception e) {
            //makeUnavailable(e);
            servlet = null;
            throw new ServletException(this.toString(), e);
        } finally {

        }

        initialized = true;
    }

    /**
     * Returns a {@link ServletConfig} object, which contains
     * initialization and startup parameters for this servlet.
     * The <code>ServletConfig</code> object returned is the one
     * passed to the <code>init</code> method.
     *
     * <p>Implementations of this interface are responsible for storing the
     * <code>ServletConfig</code> object so that this
     * method can return it. The {@link GenericServlet}
     * class, which implements this interface, already does this.
     *
     * @return the <code>ServletConfig</code> object
     * that initializes this servlet
     * @see #init
     */
    public ServletConfig getServletConfig() {
        return this;
    }


    /**
     * Called by the servlet container to allow the servlet to respond to
     * a channel.
     *
     * <p>This method is only called after the servlet's <code>init()</code>
     * method has completed successfully.
     *
     * <p>  The status code of the response always should be set for a servlet
     * that throws or sends an error.
     *
     *
     * <p>Servlets typically run inside multithreaded servlet containers
     * that can handle multiple requests concurrently. Developers must
     * be aware to synchronize access to any shared resources such as files,
     * network connections, and as well as the servlet's class and instance
     * variables.
     * More information on multithreaded programming in Java is available in
     * <a href="http://java.sun.com/Series/Tutorial/java/threads/multithreaded.html">
     * the Java tutorial on multi-threaded programming</a>.
     *
     * @param request  the <code>ServletRequest</code> object that contains
     *                 the client's channel
     * @param response the <code>ServletResponse</code> object that contains
     *                 the servlet's response
     * @throws ServletException if an exception occurs that interferes
     *                          with the servlet's normal operation
     * @throws IOException      if an input or output exception occurs
     */
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        if (!initialized) {
            init();
        }
        if (servlet == null) {
            throw new ServletException("fail to initialize the servlet.");
        }


        if (unavailable != null) {
            if (unavailable.isPermanent()) {
                throw new ServletException("");
            } else {
                if (unavailable.isStillUnavailable()) {
                    throw new ServletException("");
                }
            }
            unavailable = null;
        }


        try {
            servlet.service(request, response);
        } catch (UnavailableException e) {
            // makeUnavailable(e);
            //servlet = null;
            throw e;
        } catch (ServletException e) {
            //makeUnavailable(e.getCause()==null?e:e.getCause());
            //servlet = null;
            throw e;
        } catch (Exception e) {
            //makeUnavailable(e);
            //servlet = null;
            throw new ServletException(this.toString(), e);
        } finally {

        }
    }


    /**
     * Returns information about the servlet, such as author, version, and copyright.
     *
     * <p>The string that this method returns should
     * be plain text and not markup of any kind (such as HTML, XML,
     * etc.).
     *
     * @return a <code>String</code> containing servlet information
     */
    public String getServletInfo() {
        return "";
    }

    /**
     * Called by the servlet container to indicate to a servlet that the
     * servlet is being taken out of service.  This method is
     * only called once all threads within the servlet's
     * <code>service</code> method have exited or after a timeout
     * period has passed. After the servlet container calls this
     * method, it will not call the <code>service</code> method again
     * on this servlet.
     *
     * <p>This method gives the servlet an opportunity
     * to clean up any resources that are being held (for example, memory,
     * file handles, threads) and make sure that any persistent state is
     * synchronized with the servlet's current state in memory.
     */
    public void destroy() {
        if (servlet == null) {
            return;
        }
        servlet.destroy();
    }


    /**
     * Creates the servlet instance with the given class name. This method will return null
     * if it fails to load class with the given name.
     *
     * @param className the class name to create servlet instance
     * @return
     */
    protected Servlet createServlet(String className) {
        Servlet servlet = null;
        try {
            Class<?> clazz = getServletContext().getClassLoader().loadClass(className);
            if (clazz != null) {
                servlet = createServlet((Class<Servlet>) clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return servlet;
    }

    /**
     * Creates the servlet instance with the given class.This method will return null
     * if it fails to create a servlet instance with the given class.
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws ServletException
     */
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        //
        // The Class<T>.isAssignableFrom method could return false even if the class
        // is derived from the javax.servlet.Servlet. Make sure that Servlet and the
        // the given class instance must be loaded from the same class loader.
        //
        javax.servlet.Servlet servlet = null;
        if (javax.servlet.Servlet.class.isAssignableFrom(clazz)) {
            try {
                servlet = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return (T) servlet;
    }


    /**
     * @return
     */
    public int getLoadOnStartup() {
        return this.loadOnStartup;
    }


    /**
     * @return
     */
    public boolean getAsyncSupported() {
        return this.isAsyncSupported;
    }


    /**
     * @return
     */
    public boolean getEnabled() {
        return this.enabled;
    }

}

// https://github.com/eclipse/jetty.project/blob/f36eba457776124c207a4a12a23d48f2d5caabf8/jetty-servlet/src/main/java/org/eclipse/jetty/servlet/ServletHolder.java
/*

    private void makeUnavailable(UnavailableException e)
    {
        if (_unavailableEx==e && _unavailable!=0)
            return;

        _servletHandler.getServletContext().log("unavailable",e);

        _unavailableEx=e;
        _unavailable=-1;
        if (e.isPermanent())
            _unavailable=-1;
        else
        {
            if (_unavailableEx.getUnavailableSeconds()>0)
                _unavailable=System.currentTimeMillis()+1000*_unavailableEx.getUnavailableSeconds();
            else
                _unavailable=System.currentTimeMillis()+5000; // TODO configure
        }
    }

    private void makeUnavailable(final Throwable e)
    {
        if (e instanceof UnavailableException)
            makeUnavailable((UnavailableException)e);
        else
        {
            ServletContext ctx = _servletHandler.getServletContext();
            if (ctx==null)
                LOG.info("unavailable",e);
            else
                ctx.log("unavailable",e);
            _unavailableEx=new UnavailableException(String.valueOf(e),-1)
            {
                {
                    initCause(e);
                }
            };
            _unavailable=-1;
        }
    }

 */