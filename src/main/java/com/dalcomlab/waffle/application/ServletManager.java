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
import com.dalcomlab.waffle.servlets.DefaultServlet;

import javax.servlet.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ServletManager extends Manager {

    private Map<String, ServletDelegate> servlets = new HashMap<>();
    private List<ServletMapping> mappings = new LinkedList<>();
    private Map<String, ServletMapping> existing = new HashMap<>();
    private ServletDelegate defaultServlet;

    /**
     *
     */
    public ServletManager(ApplicationContext context) {
        super(context);
    }


    /**
     * Initialize the manager
     */
    public void initialize() {
        SortedMap<Integer, ServletDelegate> servlets = new TreeMap<Integer, ServletDelegate>();
        this.servlets.forEach((name, servlet) -> {
            if (servlet.getLoadOnStartup() != -1 && servlet.getEnabled()) {
                servlets.put(servlet.getLoadOnStartup(), servlet);
            }
        });

        servlets.forEach((i, servlet) -> {
            try {
                servlet.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        defaultServlet =  ServletDelegate.createServlet(this, "default", new DefaultServlet());
        if (defaultServlet != null) {
            try {
                defaultServlet.init();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Destroy the manager
     */
    public void destroy() {
        this.servlets.forEach((name, servlet) -> {
            servlet.destroy();
        });
    }

    /**
     * Adds the servlet with the given name and class name to this servlet
     * context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>The specified <tt>className</tt> will be loaded using the
     * classloader associated with the application represented by this
     * ServletContext.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the given <tt>className</tt> to it)
     * and returned.
     *
     * <p>This method introspects the class with the given <tt>className</tt>
     * for the {@link javax.servlet.annotation.ServletSecurity},
     * {@link javax.servlet.annotation.MultipartConfig},
     * <tt>javax.annotation.security.RunAs</tt>, and
     * <tt>javax.annotation.security.DeclareRoles</tt> annotations.
     * In addition, this method supports resource injection if the
     * class with the given <tt>className</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param servletName the name of the servlet
     * @param className   the fully qualified class name of the servlet
     * @return a ServletRegistration object that may be used to further
     * configure the registered servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for
     * a servlet with the given <tt>servletName</tt>
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws IllegalArgumentException      if <code>servletName</code> is null
     *                                       or an empty String
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addServlet method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (servletName == null || servletName.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (className == null || className.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (getServletRegistration(servletName) != null) {
            return null;
        }

        ServletDelegate delegate = ServletDelegate.createServlet(this, servletName, className);
        if (delegate != null) {
            servlets.put(servletName, delegate);
            return delegate;
        }

        return null;
    }


    /**
     * Registers the given servlet instance with this ServletContext
     * under the given <tt>servletName</tt>.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the class name of the given servlet
     * instance to it) and returned.
     *
     * @param servletName the name of the servlet
     * @param servlet     the servlet instance to register
     * @return a ServletRegistration object that may be used to further
     * configure the given servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for a
     * servlet with the given <tt>servletName</tt> or if the same servlet
     * instance has already been registered with this or another
     * ServletContext in the same container
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @throws IllegalArgumentException      if the given servlet instance
     *                                       implements {@link SingleThreadModel}, or <code>servletName</code> is null
     *                                       or an empty String
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addServlet method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (servletName == null || servletName.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (servlet == null) {
            throw new IllegalArgumentException();
        }

        if (getServletRegistration(servletName) != null) {
            return null;
        }

        ServletDelegate delegate = ServletDelegate.createServlet(this, servletName, servlet);

        if (delegate != null) {
            servlets.put(servletName, delegate);
            return delegate;
        }

        return null;
    }


    /**
     * Adds the servlet with the given name and class type to this servlet
     * context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the name of the given
     * <tt>servletClass</tt> to it) and returned.
     *
     * <p>This method introspects the given <tt>servletClass</tt> for
     * the {@link javax.servlet.annotation.ServletSecurity},
     * {@link javax.servlet.annotation.MultipartConfig},
     * <tt>javax.annotation.security.RunAs</tt>, and
     * <tt>javax.annotation.security.DeclareRoles</tt> annotations.
     * In addition, this method supports resource injection if the
     * given <tt>servletClass</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param servletName  the name of the servlet
     * @param servletClass the class object from which the servlet will be
     *                     instantiated
     * @return a ServletRegistration object that may be used to further
     * configure the registered servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for
     * the given <tt>servletName</tt>
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws IllegalArgumentException      if <code>servletName</code> is null
     *                                       or an empty String
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public ServletRegistration.Dynamic addServlet(String servletName,
                                                  Class<? extends Servlet> servletClass) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addServlet method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (servletName == null || servletName.length() == 0) {
            throw new IllegalArgumentException();
        }

        if (getServletRegistration(servletName) != null) {
            return null;
        }

        ServletDelegate delegate = ServletDelegate.createServlet(this, servletName, servletClass);

        if (delegate != null) {
            servlets.put(servletName, delegate);
            return delegate;
        }

        return null;
    }


    /**
     * Adds the servlet with the given jsp file to this servlet context.
     *
     * <p>The registered servlet may be further configured via the returned
     * {@link ServletRegistration} object.
     *
     * <p>If this ServletContext already contains a preliminary
     * ServletRegistration for a servlet with the given <tt>servletName</tt>,
     * it will be completed (by assigning the given <tt>jspFile</tt> to it)
     * and returned.
     *
     * @param servletName the name of the servlet
     * @param jspFile     the full path to a JSP file within the web application
     *                    beginning with a `/'.
     * @return a ServletRegistration object that may be used to further
     * configure the registered servlet, or <tt>null</tt> if this
     * ServletContext already contains a complete ServletRegistration for
     * a servlet with the given <tt>servletName</tt>
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws IllegalArgumentException      if <code>servletName</code> is null
     *                                       or an empty String
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 4.0
     */
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addJspFile method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        return null;
    }


    /**
     * Instantiates the given Servlet class.
     *
     * <p>The returned Servlet instance may be further customized before it
     * is registered with this ServletContext via a call to
     * {@link #addServlet(String, Servlet)}.
     *
     * <p>The given Servlet class must define a zero argument constructor,
     * which is used to instantiate it.
     *
     * <p>This method introspects the given <tt>clazz</tt> for
     * the following annotations:
     * {@link javax.servlet.annotation.ServletSecurity},
     * {@link javax.servlet.annotation.MultipartConfig},
     * <tt>javax.annotation.security.RunAs</tt>, and
     * <tt>javax.annotation.security.DeclareRoles</tt>.
     * In addition, this method supports resource injection if the
     * given <tt>clazz</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param <T>   the class of the Servlet to create
     * @param clazz the Servlet class to instantiate
     * @return the new Servlet instance
     * @throws ServletException              if the given <tt>clazz</tt> fails to be
     *                                       instantiated
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        return null;
    }


    /**
     * Gets the ServletRegistration corresponding to the servlet with the
     * given <tt>servletName</tt>.
     *
     * @param servletName the name of a servlet
     * @return the (complete or preliminary) ServletRegistration for the
     * servlet with the given <tt>servletName</tt>, or null if no
     * ServletRegistration exists under that name
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public ServletRegistration getServletRegistration(String servletName) {
        return servlets.get(servletName);
    }


    /**
     * Gets a (possibly empty) Map of the ServletRegistration
     * objects (keyed by servlet name) corresponding to all servlets
     * registered with this ServletContext.
     *
     * <p>The returned Map includes the ServletRegistration objects
     * corresponding to all declared and annotated servlets, as well as the
     * ServletRegistration objects corresponding to all servlets that have
     * been added via one of the <tt>addServlet</tt> and <tt>addJspFile</tt>
     * methods.
     *
     * <p>If permitted, any changes to the returned Map must not affect this
     * ServletContext.
     *
     * @return Map of the (complete and preliminary) ServletRegistration
     * objects corresponding to all servlets currently registered with this
     * ServletContext
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return (Map<String, ? extends ServletRegistration>) new HashMap<String, ServletDelegate>(servlets);
    }


    /**
     * @param name the servlet name
     * @return the {@code javax.servlet.Servlet Servlet} with the given name
     * @throws ServletException if an exception has occurred that interfaces
     *                          with servlet's normal operation
     * @deprecated As of Java Servlet API 2.1, with no direct replacement.
     *
     * <p>This method was originally defined to retrieve a servlet
     * from a <code>ServletContext</code>. In this version, this method
     * always returns <code>null</code> and remains only to preserve
     * binary compatibility. This method will be permanently removed
     * in a future version of the Java Servlet API.
     *
     * <p>In lieu of this method, servlets can share information using the
     * <code>ServletContext</code> class and can perform shared business logic
     * by invoking methods on common non-servlet classes.
     */
    public Servlet getServlet(String name) throws ServletException {
        return null;
    }


    /**
     * @return an <code>Enumeration</code> of {@code javax.servlet.Servlet Servlet}
     * @deprecated As of Java Servlet API 2.0, with no replacement.
     *
     * <p>This method was originally defined to return an
     * <code>Enumeration</code> of all the servlets known to this servlet
     * context.
     * In this version, this method always returns an empty jar and
     * remains only to preserve binary compatibility. This method
     * will be permanently removed in a future version of the Java
     * Servlet API.
     */
    public Enumeration<Servlet> getServlets() {
        return null;
    }


    /**
     * @return an <code>Enumeration</code> of {@code javax.servlet.Servlet Servlet} names
     * @deprecated As of Java Servlet API 2.1, with no replacement.
     *
     * <p>This method was originally defined to return an
     * <code>Enumeration</code>
     * of all the servlet names known to this context. In this version,
     * this method always returns an empty <code>Enumeration</code> and
     * remains only to preserve binary compatibility. This method will
     * be permanently removed in a future version of the Java Servlet API.
     */
    public Enumeration<String> getServletNames() {

        return null;
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
     * @param servletName the name of the servlet.
     * @param urlPatterns the URL patterns of the servlet mapping
     * @return the (possibly empty) Set of URL patterns that are already
     * mapped to a different Servlet
     * @throws IllegalArgumentException if <tt>urlPatterns</tt> is null
     *                                  or empty
     * @throws IllegalStateException    if the ServletContext from which this
     *                                  ServletRegistration was obtained has already been initialized
     */
    public Set<String> addMapping(String servletName, String... urlPatterns) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addMapping method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (urlPatterns == null || urlPatterns.length == 0) {
            throw new IllegalArgumentException();
        }

        ServletMapping mapping = existing.get(servletName);
        if (mapping == null) {
            mapping = this.addServletMapping(new ServletMapping(servletName));
        }

        mapping.addUrlPattern(urlPatterns);
        return new HashSet<String>(Arrays.asList(mapping.getUrlPatterns()));
    }

    /**
     * Gets the currently available mappings of the Servlet corresponding to the given
     * servlet name.
     *
     * <p>If permitted, any changes to the returned <code>Collection</code> must not
     * affect this <code>ServletRegistration</code>.
     *
     * @param servletName the name of the servlet.
     * @return a (possibly empty) <code>Collection</code> of the currently
     * available mappings of the Servlet represented by this
     * <code>ServletRegistration</code>
     */
    public Collection<String> getMappings(String servletName) {
        ServletMapping mapping = existing.get(servletName);
        if (mapping != null) {
            return Arrays.asList(mapping.getUrlPatterns());
        }

        return Collections.emptyList();
    }


    /**
     * Adds a servlet mapping with the given <code>ServletMapping</code> object.
     *
     * @param mapping
     */
    public ServletMapping addServletMapping(ServletMapping mapping) {
        if (mapping == null) {
            return null;
        }
        existing.put(mapping.getServletName(), mapping);
        mappings.add(mapping);
        return mapping;
    }


    /**
     * @return
     */
    public ServletMapping[] getServletMappings() {
        return mappings.stream().toArray(ServletMapping[]::new);
    }


    /**
     * @param name
     * @return
     */
    public ServletDelegate getServletByName(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        return servlets.get(name);
    }

    /**
     * TODO: implement
     *
     * @param path
     * @return
     */
    public ServletDelegate getServlet(Path path) {
        if (path == null) {
            return null;
        }

        // 1. If the channel path exactly matches on of the exact mappings, that mapping is used.
        // 2. If the channel path starts with one or more prefix mappings (not counting the mapping's trailing "/*"),
        //    then the longest matching prefix mapping is used. NOTE: Only complete path elements are compared.
        //    For example, "/foo/*" will match (among infinitely many others) "/foo", "/foo/", and "/foo/bar",
        //    but will not match "/foobar".
        //
        // 3. If the channel path ends with an extension mapping (not counting the mapping's leading "*"),
        //    that mapping is used.
        // 4. If none of the previous rules produce a match, the default mapping is used.

        String url = path.getPath();
        String name = null;

        // 1) exact match
        if (name == null) {
            for (ServletMapping mapping : mappings) {
                if (mapping.matchesExact(url) != null) {
                    name = mapping.getServletName();
                    break;
                }
            }
        }

        // 2) prefix match
        if (name == null) {
            String longestPattern = "";
            for (ServletMapping mapping : mappings) {
                String pattern = mapping.matchesPrefix(url);
                if (pattern != null) {
                    if (pattern.length() > longestPattern.length()) {
                        longestPattern = pattern;
                        name = mapping.getServletName();
                    }
                }
            }
        }

        // 3) extension match
        if (name == null) {
            for (ServletMapping mapping : mappings) {
                if (mapping.matchesExtension(url) != null) {
                    name = mapping.getServletName();
                    break;
                }
            }
        }

        // 4) default match
        if (name == null) {
            for (ServletMapping mapping : mappings) {
                if (mapping.matchesDefault(url) != null) {
                    name = mapping.getServletName();
                    break;
                }
            }
        }

        if (name == null) {
            return null;
        }

        return this.getServletByName(name);
    }


    /**
     * TODO: implement
     *
     * @param path
     * @return
     */
    public Mapping<ServletDelegate> matches(Path path) {
        if (path == null) {
            return null;
        }

        // 1. If the channel path exactly matches on of the exact mappings, that mapping is used.
        // 2. If the channel path starts with one or more prefix mappings (not counting the mapping's trailing "/*"),
        //    then the longest matching prefix mapping is used. NOTE: Only complete path elements are compared.
        //    For example, "/foo/*" will match (among infinitely many others) "/foo", "/foo/", and "/foo/bar",
        //    but will not match "/foobar".
        //
        // 3. If the channel path ends with an extension mapping (not counting the mapping's leading "*"),
        //    that mapping is used.
        // 4. If none of the previous rules produce a match, the default mapping is used.

        Mapping<ServletDelegate> mapping = null;
        String url = path.getPath();

        // 1) exact match
        if (mapping == null) {
            mapping = matchesExact(url);
        }

        // 2) prefix match
        if (mapping == null) {
            mapping = matchesPrefix(url);
        }

        // 3) extension match
        if (mapping == null) {
            mapping = matchesExtension(url);
        }

        // 4) default match
        if (mapping == null) {
            mapping = matchesDefault(url);
        }

        return mapping;
    }


    /**
     * @param url
     * @return
     */
    public Mapping<ServletDelegate> matchesExact(String url) {
        if (url == null) {
            return null;
        }

        String name = null;
        String pattern = null;


        for (ServletMapping mapping : mappings) {
            pattern = mapping.matchesExact(url);
            if (pattern != null) {
                name = mapping.getServletName();
                break;
            }
        }

        if (name == null) {
            return null;
        }

        return new Mapping<ServletDelegate>(pattern, this.getServletByName(name));
    }


    /**
     * @param url
     * @return
     */
    public Mapping<ServletDelegate> matchesPrefix(String url) {
        if (url == null) {
            return null;
        }

        String name = null;
        String pattern = null;


        for (ServletMapping mapping : mappings) {
            pattern = mapping.matchesPrefix(url);
            if (pattern != null) {
                name = mapping.getServletName();
                break;
            }
        }

        if (name == null) {
            return null;
        }

        return new Mapping<ServletDelegate>(pattern, this.getServletByName(name));
    }

    /**
     * @param url
     * @return
     */
    public Mapping<ServletDelegate> matchesExtension(String url) {
        if (url == null) {
            return null;
        }

        String name = null;
        String pattern = null;


        for (ServletMapping mapping : mappings) {
            pattern = mapping.matchesExtension(url);
            if (pattern != null) {
                name = mapping.getServletName();
                break;
            }
        }

        if (name == null) {
            return null;
        }

        return new Mapping<ServletDelegate>(pattern, this.getServletByName(name));
    }


    /**
     * @param url
     * @return
     */
    public Mapping<ServletDelegate> matchesDefault(String url) {
        if (url == null) {
            return null;
        }

        String name = null;
        String pattern = null;


        for (ServletMapping mapping : mappings) {
            pattern = mapping.matchesDefault(url);
            if (pattern != null) {
                name = mapping.getServletName();
                break;
            }
        }

        if (name == null) {
            return null;
        }

        return new Mapping<ServletDelegate>(pattern, this.getServletByName(name));
    }

    /**
     *
     * @return
     */
    public ServletDelegate getDefaultServlet() {
        return this.defaultServlet;
    }

}
