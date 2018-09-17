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
public class FilterDelegate extends RegistrationImpl
        implements FilterConfig, FilterRegistration.Dynamic, Delegate<Filter> {

    private Filter filter = null;
    private boolean asyncSupported = false;
    private FilterManager manager = null;
    private boolean init = false;

    /**
     * @param manager
     */
    private FilterDelegate(FilterManager manager) {
        this.manager = manager;
    }


    /**
     * Creates a filter delegate with a given name of a filter and name of a class.
     *
     * @param manager
     * @param filterName
     * @param className
     * @return
     */
    public static FilterDelegate createFilter(FilterManager manager, String filterName, String className) {
        if (manager == null) {
            return null;
        }

        if (filterName == null || filterName.length() == 0) {
            return null;
        }

        if (className == null || className.length() == 0) {
            return null;
        }

        Class<?> filterClass = null;
        try {
            filterClass = manager.getContext().getClassLoader().loadClass(className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return FilterDelegate.createFilter(manager, filterName, (Class<? extends Filter>) filterClass);
    }

    /**
     * Creates a filter delegate with a given name of a filter and a filter instance.
     *
     * @param manager
     * @param filterName
     * @param filter
     * @return
     */
    public static FilterDelegate createFilter(FilterManager manager, String filterName, Filter filter) {
        if (manager == null) {
            return null;
        }

        if (filterName == null || filterName.length() == 0) {
            return null;
        }

        if (filter == null) {
            return null;
        }

        FilterDelegate delegate = new FilterDelegate(manager);
        delegate.name = filterName;
        delegate.filter = filter;
        return delegate;
    }

    /**
     * Creates a filter delegate with a given name of a filter and a class instance.
     *
     * @param manager
     * @param filterName
     * @param filterClass
     * @return
     */
    public static FilterDelegate createFilter(FilterManager manager, String filterName, Class<? extends Filter> filterClass) {
        if (manager == null) {
            return null;
        }

        if (filterName == null || filterName.length() == 0) {
            return null;
        }

        if (filterClass == null) {
            return null;
        }

        FilterDelegate delegate = new FilterDelegate(manager);
        delegate.name = filterName;
        delegate.className = filterClass.getName();
        delegate.filter = delegate.createFilter(delegate.className);
        return delegate;
    }


    // Delegate<T> implementation

    /**
     * @return
     */
    @Override
    public Filter delegate() {
        return filter;
    }

    // FilterRegistration.Dynamic implementation

    /**
     * Adds a filter mapping with the given servlet names and dispatcher
     * types for the Filter represented by this FilterRegistration.
     *
     * <p>Filter mappings are matched in the order in which they were
     * added.
     *
     * <p>Depending on the value of the <tt>isMatchAfter</tt> parameter, the
     * given filter mapping will be considered after or before any
     * <i>declared</i> filter mappings of the ServletContext from which this
     * FilterRegistration was obtained.
     *
     * <p>If this method is called multiple times, each successive call
     * adds to the effects of the former.
     *
     * @param dispatcherTypes the dispatcher types of the filter mapping,
     *                        or null if the default <tt>DispatcherType.REQUEST</tt> is to be used
     * @param isMatchAfter    true if the given filter mapping should be matched
     *                        after any declared filter mappings, and false if it is supposed to
     *                        be matched before any declared filter mappings of the ServletContext
     *                        from which this FilterRegistration was obtained
     * @param servletNames    the servlet names of the filter mapping
     * @throws IllegalArgumentException if <tt>servletNames</tt> is null or
     *                                  empty
     * @throws IllegalStateException    if the ServletContext from which this
     *                                  FilterRegistration was obtained has already been initialized
     */
    public void addMappingForServletNames(
            EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... servletNames) {
        manager.addMappingForServletNames(getFilterName(), dispatcherTypes, isMatchAfter, servletNames);
    }

    /**
     * Gets the currently available servlet name mappings
     * of the Filter represented by this <code>FilterRegistration</code>.
     *
     * <p>If permitted, any changes to the returned <code>Collection</code> must not
     * affect this <code>FilterRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently
     * available servlet name mappings of the Filter represented by this
     * <code>FilterRegistration</code>
     */
    public Collection<String> getServletNameMappings() {
        return manager.getServletNameMappings(getFilterName());
    }

    /**
     * Adds a filter mapping with the given url patterns and dispatcher
     * types for the Filter represented by this FilterRegistration.
     *
     * <p>Filter mappings are matched in the order in which they were
     * added.
     *
     * <p>Depending on the value of the <tt>isMatchAfter</tt> parameter, the
     * given filter mapping will be considered after or before any
     * <i>declared</i> filter mappings of the ServletContext from which
     * this FilterRegistration was obtained.
     *
     * <p>If this method is called multiple times, each successive call
     * adds to the effects of the former.
     *
     * @param dispatcherTypes the dispatcher types of the filter mapping,
     *                        or null if the default <tt>DispatcherType.REQUEST</tt> is to be used
     * @param isMatchAfter    true if the given filter mapping should be matched
     *                        after any declared filter mappings, and false if it is supposed to
     *                        be matched before any declared filter mappings of the ServletContext
     *                        from which this FilterRegistration was obtained
     * @param urlPatterns     the url patterns of the filter mapping
     * @throws IllegalArgumentException if <tt>urlPatterns</tt> is null or
     *                                  empty
     * @throws IllegalStateException    if the ServletContext from which this
     *                                  FilterRegistration was obtained has already been initialized
     */
    public void addMappingForUrlPatterns(
            EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... urlPatterns) {
        manager.addMappingForUrlPatterns(getFilterName(), dispatcherTypes, isMatchAfter, urlPatterns);
    }

    /**
     * Gets the currently available URL pattern mappings of the Filter
     * represented by this <code>FilterRegistration</code>.
     *
     * <p>If permitted, any changes to the returned <code>Collection</code> must not
     * affect this <code>FilterRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently
     * available URL pattern mappings of the Filter represented by this
     * <code>FilterRegistration</code>
     */
    public Collection<String> getUrlPatternMappings() {
        return manager.getUrlPatternMappings(getFilterName());
    }

    // FilterConfig implementation

    /**
     * Returns the filter-name of this filter as defined in the deployment
     * descriptor.
     *
     * @return the filter name of this filter
     */
    public String getFilterName() {
        return this.getName();
    }


    /**
     * Returns a reference to the {@link ServletContext} in which the caller
     * is executing.
     *
     * @return a {@link ServletContext} object, used by the caller to
     * interact with its servlet container
     * @see ServletContext
     */
    public ServletContext getServletContext() {
        return this.manager.getContext();
    }


    /**
     * Returns the names of the filter's initialization parameters
     * as an <code>Enumeration</code> of <code>String</code> objects,
     * or an empty <code>Enumeration</code> if the filter has
     * no initialization parameters.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of the filter's initialization parameters
     */
    public Enumeration<String> getInitParameterNames() {
        if (this.initParameters == null) {
            return Collections.emptyEnumeration();
        }
        return Collections.enumeration(initParameters.keySet());
    }

    // Filter implementation

    /**
     * <p>Called by the web container
     * to indicate to a filter that it is being placed into service.</p>
     *
     * <p>The servlet container calls the init
     * method exactly once after instantiating the filter. The init
     * method must complete successfully before the filter is asked to do any
     * filtering work.</p>
     *
     * <p>The web container cannot place the filter into service if the init
     * method either</p>
     * <ol>
     * <li>Throws a ServletException
     * <li>Does not return within a time period defined by the web container
     * </ol>
     *
     * @throws ServletException if an exception has occurred that interferes with
     *                          the filter's normal operation
     * @implSpec The default implementation takes no action.
     */
    public void init() throws ServletException {
        if (init) {
            return;
        }
        init = true;

        if (className == null || className.length() == 0) {
            return;
        }

        if (filter == null) {
            filter = createFilter(className);
        }

        if (filter != null) {
            filter.init(this);
        }

    }


    /**
     * The <code>doFilter</code> method of the Filter is called by the
     * container each time a channel/response pair is passed through the
     * chain due to a client channel for a resource at the end of the chain.
     * The FilterChain passed in to this method allows the Filter to pass
     * on the channel and response to the next entity in the chain.
     *
     * <p>A typical implementation of this method would follow the following
     * pattern:
     * <ol>
     * <li>Examine the channel
     * <li>Optionally wrap the channel object with a custom implementation to
     * filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain
     * using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the channel/response pair to
     * the next entity in the filter chain to
     * block the channel processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the
     * next entity in the filter chain.
     * </ol>
     *
     * @param request  the <code>ServletRequest</code> object contains the client's channel
     * @param response the <code>ServletResponse</code> object contains the filter's response
     * @param chain    the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     * @see UnavailableException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!init) {
            init();
        }

        if (filter != null) {
            filter.doFilter(request, response, chain);
        }
    }


    /**
     * <p>Called by the web container
     * to indicate to a filter that it is being
     * taken out of service.</p>
     *
     * <p>This method is only called once all threads within the filter's
     * doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter.</p>
     *
     * <p>This method gives the filter an opportunity to clean up any
     * resources that are being held (for example, memory, file handles,
     * threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.</p>
     *
     * @implSpec The default implementation takes no action.
     */

    public void destroy() {
        if (filter == null) {
            return;
        }
        filter.destroy();
    }


    /**
     * Creates the filter instance with the given class name. This method will return null
     * if it fails to load class with the given name.
     *
     * @param className the class name to create filter instance
     * @return
     */
    private javax.servlet.Filter createFilter(String className) {
        if (className == null || className.length() == 0) {
            return null;
        }

        javax.servlet.Filter filter = null;
        try {
            Class<?> clazz = getServletContext().getClassLoader().loadClass(className);
            if (clazz != null) {
                filter = this.<Filter>createFilter((Class<Filter>) clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return filter;
    }

    /**
     * Creates the filter instance with the given class.This method will return null
     * if it fails to create a filter instance with the given class.
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws ServletException
     */
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        //
        // The Class<T>.isAssignableFrom method could return false even if the class
        // is derived from the javax.servlet.Filter. Make sure that Filter and the
        // the given class instance must be loaded from the same class loader.
        //
        javax.servlet.Filter filter = null;
        if (javax.servlet.Filter.class.isAssignableFrom(clazz)) {
            try {
                filter = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return (T) filter;
    }

}
