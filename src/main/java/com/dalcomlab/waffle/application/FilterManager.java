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

import com.dalcomlab.waffle.resource.Path;

import javax.servlet.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class FilterManager extends Manager {

    private Map<String, FilterDelegate> filters = new HashMap<>();
    private List<FilterMapping> mappings = new LinkedList<>();
    private Map<String, FilterMapping> existing = new HashMap<>();

    /**
     * @param context
     */
    public FilterManager(ApplicationContext context) {
        super(context);
    }

    /**
     * Initialize the manager
     */
    public void initialize() {
        filters.forEach((name, filter) -> {
            try {
                filter.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Destroy the manager
     */
    public void destroy() {

    }


    /**
     * Adds the filter with the given name and class name to this servlet
     * context.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.
     *
     * <p>The specified <tt>className</tt> will be loaded using the
     * classloader associated with the application represented by this
     * ServletContext.
     *
     * <p>If this ServletContext already contains a preliminary
     * FilterRegistration for a filter with the given <tt>filterName</tt>,
     * it will be completed (by assigning the given <tt>className</tt> to it)
     * and returned.
     *
     * <p>This method supports resource injection if the class with the
     * given <tt>className</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param filterName the name of the filter
     * @param className  the fully qualified class name of the filter
     * @return a FilterRegistration object that may be used to further
     * configure the registered filter, or <tt>null</tt> if this
     * ServletContext already contains a complete FilterRegistration for
     * a filter with the given <tt>filterName</tt>
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws IllegalArgumentException      if <code>filterName</code> is null or
     *                                       an empty String
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addFilter method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (filterName == null || filterName.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (className == null || className.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (getFilterRegistration(filterName) != null) {
            return null;
        }

        FilterDelegate delegate = FilterDelegate.createFilter(this, filterName, className);
        if (delegate != null) {
            filters.put(filterName, delegate);
            return delegate;
        }

        return null;
    }


    /**
     * Registers the given filter instance with this ServletContext
     * under the given <tt>filterName</tt>.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * FilterRegistration for a filter with the given <tt>filterName</tt>,
     * it will be completed (by assigning the class name of the given filter
     * instance to it) and returned.
     *
     * @param filterName the name of the filter
     * @param filter     the filter instance to register
     * @return a FilterRegistration object that may be used to further
     * configure the given filter, or <tt>null</tt> if this
     * ServletContext already contains a complete FilterRegistration for a
     * filter with the given <tt>filterName</tt> or if the same filter
     * instance has already been registered with this or another
     * ServletContext in the same container
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws IllegalArgumentException      if <code>filterName</code> is null or
     *                                       an empty String
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addFilter method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (filterName == null || filterName.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (filter == null) {
            throw new IllegalArgumentException();
        }

        if (getFilterRegistration(filterName) != null) {
            return null;
        }

        FilterDelegate delegate = FilterDelegate.createFilter(this, filterName, filter);

        if (delegate != null) {
            filters.put(filterName, delegate);
            return delegate;
        }

        return null;
    }


    /**
     * Adds the filter with the given name and class type to this servlet
     * context.
     *
     * <p>The registered filter may be further configured via the returned
     * {@link FilterRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * FilterRegistration for a filter with the given <tt>filterName</tt>,
     * it will be completed (by assigning the name of the given
     * <tt>filterClass</tt> to it) and returned.
     *
     * <p>This method supports resource injection if the given
     * <tt>filterClass</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param filterName  the name of the filter
     * @param filterClass the class object from which the filter will be
     *                    instantiated
     * @return a FilterRegistration object that may be used to further
     * configure the registered filter, or <tt>null</tt> if this
     * ServletContext already contains a complete FilterRegistration for a
     * filter with the given <tt>filterName</tt>
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws IllegalArgumentException      if <code>filterName</code> is null or
     *                                       an empty String
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addFilter method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (filterName == null || filterName.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (getFilterRegistration(filterName) != null) {
            return null;
        }

        FilterDelegate delegate = FilterDelegate.createFilter(this, filterName, filterClass);

        if (delegate != null) {
            filters.put(filterName, delegate);
            return delegate;
        }

        return null;
    }


    /**
     * Instantiates the given Filter class.
     *
     * <p>The returned Filter instance may be further customized before it
     * is registered with this ServletContext via a call to
     * {@link #addFilter(String, Filter)}.
     *
     * <p>The given Filter class must define a zero argument constructor,
     * which is used to instantiate it.
     *
     * <p>This method supports resource injection if the given
     * <tt>clazz</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param <T>   the class of the Filter to create
     * @param clazz the Filter class to instantiate
     * @return the new Filter instance
     * @throws ServletException              if the given <tt>clazz</tt> fails to be
     *                                       instantiated
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        return null;
    }


    /**
     * Gets the FilterRegistration corresponding to the filter with the
     * given <tt>filterName</tt>.
     *
     * @param filterName the name of a filter
     * @return the (complete or preliminary) FilterRegistration for the
     * filter with the given <tt>filterName</tt>, or null if no
     * FilterRegistration exists under that name
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public FilterRegistration getFilterRegistration(String filterName) {
        return filters.get(filterName);
    }


    /**
     * Gets a (possibly empty) Map of the FilterRegistration
     * objects (keyed by filter name) corresponding to all filters
     * registered with this ServletContext.
     *
     * <p>The returned Map includes the FilterRegistration objects
     * corresponding to all declared and annotated filters, as well as the
     * FilterRegistration objects corresponding to all filters that have
     * been added via one of the <tt>addFilter</tt> methods.
     *
     * <p>Any changes to the returned Map must not affect this
     * ServletContext.
     *
     * @return Map of the (complete and preliminary) FilterRegistration
     * objects corresponding to all filters currently registered with this
     * ServletContext
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return (Map<String, ? extends FilterRegistration>) new HashMap<String, FilterDelegate>(filters);
    }


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
     * @param filterName      the name of the filter
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
    public void addMappingForServletNames(String filterName, EnumSet<DispatcherType> dispatcherTypes,
                                          boolean isMatchAfter, String... servletNames) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addMappingForServletNames method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        FilterMapping mapping = existing.get(filterName);
        if (mapping == null) {
            mapping = this.addFilterMapping(new FilterMapping(filterName));
        }

        if (isMatchAfter) {
            mapping.addServletNameAfter(servletNames);
        } else {
            mapping.addServletNameBefore(servletNames);
        }
        if (dispatcherTypes != null) {
            for (DispatcherType dispatcherType : dispatcherTypes) {
                mapping.addDispatcherType(dispatcherType);
            }
        }
    }

    /**
     * Gets the currently available servlet name mappings
     * of the Filter represented by this <code>FilterRegistration</code>.
     *
     * <p>If permitted, any changes to the returned <code>Collection</code> must not
     * affect this <code>FilterRegistration</code>.
     *
     * @param filterName the name of the filter
     * @return a (possibly empty) <code>Collection</code> of the currently
     * available servlet name mappings of the Filter represented by this
     * <code>FilterRegistration</code>
     */
    public Collection<String> getServletNameMappings(String filterName) {
        FilterMapping filterMapping = existing.get(filterName);
        if (filterMapping != null) {
            return Arrays.asList(filterMapping.getServletNames());
        }
        return Collections.emptyList();
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
     * @param filterName      the name of the filter
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
    public void addMappingForUrlPatterns(String filterName, EnumSet<DispatcherType> dispatcherTypes,
                                         boolean isMatchAfter, String... urlPatterns) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addMappingForUrlPatterns method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        FilterMapping mapping = existing.get(filterName);
        if (mapping == null) {
            mapping = this.addFilterMapping(new FilterMapping(filterName));
        }

        if (isMatchAfter) {
            mapping.addURLPatternAfter(urlPatterns);
        } else {
            mapping.addURLPatternBefore(urlPatterns);
        }

        if (dispatcherTypes != null) {
            for (DispatcherType dispatcherType : dispatcherTypes) {
                mapping.addDispatcherType(dispatcherType);
            }
        }
    }

    /**
     * Gets the currently available URL pattern mappings of the Filter
     * represented by this <code>FilterRegistration</code>.
     *
     * <p>If permitted, any changes to the returned <code>Collection</code> must not
     * affect this <code>FilterRegistration</code>.
     *
     * @param filterName the name of the filter
     * @return a (possibly empty) <code>Collection</code> of the currently
     * available URL pattern mappings of the Filter represented by this
     * <code>FilterRegistration</code>
     */
    public Collection<String> getUrlPatternMappings(String filterName) {
        FilterMapping mapping = existing.get(filterName);
        if (mapping != null) {
            return Arrays.asList(mapping.getUrlPatterns());
        }
        return Collections.emptyList();
    }


    /**
     * @param mapping
     */
    public FilterMapping addFilterMapping(FilterMapping mapping) {
        if (mapping == null) {
            return null;
        }
        existing.put(mapping.getFilterName(), mapping);
        mappings.add(mapping);
        return mapping;
    }

    /**
     * @return
     */
    public FilterMapping[] getFilterMappings() {
        return mappings.stream().toArray(FilterMapping[]::new);
    }

    /**
     * Gets a filter instance corresponding to the given name.
     *
     * @param name the name of the filter
     * @return
     */
    public FilterDelegate getFilterByName(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        return filters.get(name);
    }

    /**
     * Gets filter instances corresponding to the given path and dispatcher type.
     *
     * @param path the path
     * @param type the type of a dispatcher
     * @return
     */
    public FilterDelegate[] getFilters(Path path, DispatcherType type) {
        List<FilterDelegate> matchedFilters = new ArrayList<>();

        for (FilterMapping mapping : mappings) {
            if (mapping.match(path, type)) {
                FilterDelegate filter = getFilterByName(mapping.getFilterName());
                if (filter != null) {
                    matchedFilters.add(filter);
                }
            }
        }

        if (matchedFilters.size() == 0) {
            return null;
        }

        return matchedFilters.stream().toArray(FilterDelegate[]::new);
    }

    /**
     * Gets filter instances corresponding to the given servlet name.
     *
     * @param servletName the name of the servlet
     * @return
     */
    public FilterDelegate[] getFilters(String servletName) {
        List<FilterDelegate> matchedFilters = new ArrayList<>();

        for (FilterMapping mapping : mappings) {
            if (mapping.match(servletName)) {
                FilterDelegate filter = getFilterByName(mapping.getFilterName());
                if (filter != null) {
                    matchedFilters.add(filter);
                }
            }
        }

        if (matchedFilters.size() == 0) {
            return null;
        }

        return matchedFilters.stream().toArray(FilterDelegate[]::new);
    }

}
