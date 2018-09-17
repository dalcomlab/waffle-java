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
import javax.servlet.http.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ListenerManager extends Manager {

    private static final Class[] ACCEPTABLE_LISTENER_CLASSES = {ServletContextListener.class,
            ServletContextAttributeListener.class,
            HttpSessionListener.class,
            HttpSessionAttributeListener.class,
            HttpSessionIdListener.class,
            HttpSessionActivationListener.class,
            HttpSessionBindingListener.class,
            ServletRequestListener.class,
            ServletRequestAttributeListener.class,
            AsyncListener.class};

    private static final String[] IGNORE_ATTRIBUTE_NAMES = {
            RequestDispatcher.FORWARD_REQUEST_URI,
            RequestDispatcher.FORWARD_CONTEXT_PATH,
            RequestDispatcher.FORWARD_MAPPING,
            RequestDispatcher.FORWARD_PATH_INFO,
            RequestDispatcher.FORWARD_SERVLET_PATH,
            RequestDispatcher.FORWARD_QUERY_STRING,
            RequestDispatcher.INCLUDE_REQUEST_URI,
            RequestDispatcher.INCLUDE_CONTEXT_PATH,
            RequestDispatcher.INCLUDE_PATH_INFO,
            RequestDispatcher.INCLUDE_MAPPING,
            RequestDispatcher.INCLUDE_SERVLET_PATH,
            RequestDispatcher.INCLUDE_QUERY_STRING,
            RequestDispatcher.ERROR_EXCEPTION,
            RequestDispatcher.ERROR_EXCEPTION_TYPE,
            RequestDispatcher.ERROR_MESSAGE,
            RequestDispatcher.ERROR_REQUEST_URI,
            RequestDispatcher.ERROR_SERVLET_NAME,
            RequestDispatcher.ERROR_STATUS_CODE
    };

    private Map<Class<? extends EventListener>, List<ListenerDelegate>> listenerMap = new HashMap<>();


    /**
     *
     */
    public ListenerManager(ApplicationContext context) {
        super(context);
        for (Class listenerClass : ACCEPTABLE_LISTENER_CLASSES) {
            this.listenerMap.put(listenerClass, new LinkedList<ListenerDelegate>());
        }
    }

    /**
     * Adds the listener with the given class name.
     *
     * <p>The class with the given name will be loaded using the
     * classloader associated with the application represented by this
     * ServletContext, and must implement one or more of the following
     * interfaces:
     * <ul>
     * <li>{@link ServletContextAttributeListener}
     * <li>{@link ServletRequestListener}
     * <li>{@link ServletRequestAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionIdListener}
     * <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>If this ServletContext was passed to
     * {@link ServletContainerInitializer#onStartup}, then the class with
     * the given name may also implement {@link ServletContextListener},
     * in addition to the interfaces listed above.
     *
     * <p>As part of this method call, the container must load the class
     * with the specified class name to ensure that it implements one of
     * the required interfaces.
     *
     * <p>If the class with the given name implements a listener interface
     * whose invocation order corresponds to the declaration order (i.e.,
     * if it implements {@link ServletRequestListener},
     * {@link ServletContextListener}, or
     * {@link javax.servlet.http.HttpSessionListener}),
     * then the new listener will be added to the end of the ordered list of
     * listeners of that interface.
     *
     * <p>This method supports resource injection if the class with the
     * given <tt>className</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param className the fully qualified class name of the listener
     * @throws IllegalArgumentException      if the class with the given name
     *                                       does not implement any of the above interfaces, or if it implements
     *                                       {@link ServletContextListener} and this ServletContext was not
     *                                       passed to {@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public void addListener(String className) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addListener method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (className == null || className.length() == 0) {
            return;
        }

        ClassLoader loader = context.getClassLoader();

        if (loader == null) {
            return;
        }

        try {
            Class<? extends EventListener> clazz = (Class<? extends EventListener>) loader.loadClass(className);
            addListener(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds the given listener.
     *
     * <p>The given listener must be an instance of one or more of the
     * following interfaces:
     * <ul>
     * <li>{@link ServletContextAttributeListener}
     * <li>{@link ServletRequestListener}
     * <li>{@link ServletRequestAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionIdListener}
     * <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>If this ServletContext was passed to
     * {@link ServletContainerInitializer#onStartup}, then the given
     * listener may also be an instance of {@link ServletContextListener},
     * in addition to the interfaces listed above.
     *
     * <p>If the given listener is an instance of a listener interface whose
     * invocation order corresponds to the declaration order (i.e., if it
     * is an instance of {@link ServletRequestListener},
     * {@link ServletContextListener}, or
     * {@link javax.servlet.http.HttpSessionListener}),
     * then the listener will be added to the end of the ordered list of
     * listeners of that interface.
     *
     * @param <T>      the class of the EventListener to add
     * @param listener the listener to be added
     * @throws IllegalArgumentException      if the given listener is not
     *                                       an instance of any of the above interfaces, or if it is an instance
     *                                       of {@link ServletContextListener} and this ServletContext was not
     *                                       passed to {@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public <T extends EventListener> void addListener(T listener) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addListener method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (listener == null) {
            return;
        }

        Class<?> acceptableListenerClass = getAcceptableListenerClass(listener.getClass());
        if (acceptableListenerClass == null) {
            return;
        }

        List<ListenerDelegate> listeners = getListeners((Class<? extends EventListener>)acceptableListenerClass);
        if (listeners != null) {
            ListenerDelegate delegate = ListenerDelegate.createListener(this, listener);
            if (delegate != null) {
                listeners.add(delegate);
            }
        }
    }

    /**
     * Adds a listener of the given class type.
     *
     * <p>The given <tt>listenerClass</tt> must implement one or more of the
     * following interfaces:
     * <ul>
     * <li>{@link ServletContextAttributeListener}
     * <li>{@link ServletRequestListener}
     * <li>{@link ServletRequestAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}
     * <li>{@link javax.servlet.http.HttpSessionIdListener}
     * <li>{@link javax.servlet.http.HttpSessionListener}
     * </ul>
     *
     * <p>If this ServletContext was passed to
     * {@link ServletContainerInitializer#onStartup}, then the given
     * <tt>listenerClass</tt> may also implement
     * {@link ServletContextListener}, in addition to the interfaces listed
     * above.
     *
     * <p>If the given <tt>listenerClass</tt> implements a listener
     * interface whose invocation order corresponds to the declaration order
     * (i.e., if it implements {@link ServletRequestListener},
     * {@link ServletContextListener}, or
     * {@link javax.servlet.http.HttpSessionListener}),
     * then the new listener will be added to the end of the ordered list
     * of listeners of that interface.
     *
     * <p>This method supports resource injection if the given
     * <tt>listenerClass</tt> represents a Managed Bean.
     * See the Java EE platform and JSR 299 specifications for additional
     * details about Managed Beans and resource injection.
     *
     * @param listenerClass the listener class to be instantiated
     * @throws IllegalArgumentException      if the given <tt>listenerClass</tt>
     *                                       does not implement any of the above interfaces, or if it implements
     *                                       {@link ServletContextListener} and this ServletContext was not passed
     *                                       to {@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         if this ServletContext has already
     *                                       been initialized
     * @throws UnsupportedOperationException if this ServletContext was
     *                                       passed to the {@link ServletContextListener#contextInitialized} method
     *                                       of a {@link ServletContextListener} that was neither declared in
     *                                       <code>web.xml</code> or <code>web-fragment.xml</code>, nor annotated
     *                                       with {@link javax.servlet.annotation.WebListener}
     * @since Servlet 3.0
     */
    public void addListener(Class<? extends EventListener> listenerClass) {
        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The addListener method must be called during initialization.", null);
            throw new IllegalStateException(message);
        }

        if (!ListenerManager.isAcceptableListenerClass(listenerClass)) {
            return;
        }

        try {
            addListener(createListener(listenerClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz
     * @param <T>
     * @return
     * @throws ServletException
     */
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        if (!ListenerManager.isAcceptableListenerClass(clazz)) {
            return null;
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz
     * @return
     */
    protected List<ListenerDelegate> getListeners(Class<? extends EventListener> clazz) {
        return listenerMap.get(clazz);
    }

    /**
     * Returns a reference to the {@link ServletContext} in which the caller
     * is executing.
     *
     * @return a {@link ServletContext} object
     */
    public ServletContext getServletContext() {
        return context;
    }


    // ServletContextListener

    /**
     * Fires notification that the web application initialization handle is starting.
     */
    public void fireContextInitialized() {
        List<ListenerDelegate> listeners = getListeners(ServletContextListener.class);
        if (listeners != null) {
            ServletContextEvent event = new ServletContextEvent(context);
            listeners.forEach((listener) -> {
                listener.fireContextInitialized(event);
            });
        }
    }

    /**
     * Fires notification that the ServletContext is about to be shut down.
     */
    public void fireContextDestroyed() {
        List<ListenerDelegate> listeners = getListeners(ServletContextListener.class);
        if (listeners != null) {
            ServletContextEvent event = new ServletContextEvent(context);
            listeners.forEach((listener) -> {
                listener.fireContextDestroyed(event);
            });
        }
    }

    // ServletContextAttributeListener

    /**
     * Fires notification that an attribute has been added to the ServletContext.
     *
     * @param name
     * @param value
     */
    public void fireServletContextAttributeAdded(String name, Object value) {
        List<ListenerDelegate> listeners = getListeners(ServletContextAttributeListener.class);
        if (listeners != null) {
            ServletContextAttributeEvent event = new ServletContextAttributeEvent(context, name, value);
            listeners.forEach((listener) -> {
                listener.fireServletContextAttributeAdded(event);
            });
        }
    }


    /**
     * Fires notification that an attribute has been removed from the ServletContext.
     *
     * @param name
     * @param value
     */
    public void fireServletContextAttributeRemoved(String name, Object value) {
        List<ListenerDelegate> listeners = getListeners(ServletContextAttributeListener.class);
        if (listeners != null) {
            ServletContextAttributeEvent event = new ServletContextAttributeEvent(context, name, value);
            listeners.forEach((listener) -> {
                listener.fireServletContextAttributeRemoved(event);
            });
        }
    }

    /**
     * Fires notification that an attribute has been replaced in the ServletContext.
     *
     * @param name
     * @param value
     */
    public void fireServletContextAttributeReplaced(String name, Object value) {
        List<ListenerDelegate> listeners = getListeners(ServletContextAttributeListener.class);
        if (listeners != null) {
            ServletContextAttributeEvent event = new ServletContextAttributeEvent(context, name, value);
            listeners.forEach((listener) -> {
                listener.fireServletContextAttributeReplaced(event);
            });
        }
    }

    // HttpSessionListener

    /**
     * Fires notification that a session has been created.
     *
     * @param session
     */
    public void fireSessionCreated(HttpSession session) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionListener.class);
        if (listeners != null) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            listeners.forEach((listener) -> {
                listener.fireSessionCreated(event);
            });
        }
    }

    /**
     * Fires notification that a session is about to be invalidated.
     *
     * @param session
     */
    public void fireSessionDestroyed(HttpSession session) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionListener.class);
        if (listeners != null) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            listeners.forEach((listener) -> {
                listener.fireSessionDestroyed(event);
            });
        }
    }

    // HttpSessionAttributeListener

    /**
     * Fires notification that an attribute has been added to a session.
     *
     * @param session
     * @param name
     * @param value
     */
    public void fireHttpSessionAttributeAdded(HttpSession session, String name, Object value) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionAttributeListener.class);
        if (listeners != null) {
            HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, name, value);
            listeners.forEach((listener) -> {
                listener.fireHttpSessionAttributeAdded(event);
            });
        }
    }

    /**
     * Fires notification that an attribute has been removed from a session.
     *
     * @param session
     * @param name
     * @param value
     */
    public void fireHttpSessionAttributeRemoved(HttpSession session, String name, Object value) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionAttributeListener.class);
        if (listeners != null) {
            HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, name, value);
            listeners.forEach((listener) -> {
                listener.fireHttpSessionAttributeRemoved(event);
            });
        }
    }

    /**
     * Fires notification that an attribute has been replaced in a session.
     *
     * @param session
     * @param name
     */
    public void fireHttpSessionAttributeReplaced(HttpSession session, String name, Object value) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionAttributeListener.class);
        if (listeners != null) {
            HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, name, value);
            listeners.forEach((listener) -> {
                listener.fireHttpSessionAttributeReplaced(event);
            });
        }
    }

    // HttpSessionIdListener

    /**
     * Fires notification that session id has been changed in a session.
     *
     * @param session
     * @param oldSessionId
     */
    public void fireHttpSessionIdChanged(HttpSession session, String oldSessionId) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionIdListener.class);
        if (listeners != null) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            listeners.forEach((listener) -> {
                listener.fireHttpSessionIdChanged(event, oldSessionId);
            });
        }
    }

    // HttpSessionActivationListener

    /**
     * Fires notification that the session is about to be passivated.
     *
     * @param session
     */
    public void fireHttpSessionWillPassivate(HttpSession session) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionActivationListener.class);
        if (listeners != null) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            listeners.forEach((listener) -> {
                listener.fireHttpSessionWillPassivate(event);
            });
        }
    }

    /**
     * Fires notification that the session has just been activated.
     *
     * @param session
     */
    public void fireHttpSessionDidActivate(HttpSession session) {
        List<ListenerDelegate> listeners = getListeners(HttpSessionActivationListener.class);
        if (listeners != null) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            listeners.forEach((listener) -> {
                listener.fireHttpSessionDidActivate(event);
            });
        }
    }


    // ServletRequestListener

    /**
     * Fires notification that a ServletRequest is about to go out of scope of the web application.
     *
     * @param request
     */
    public void fireServletRequestDestroyed(ServletRequest request) {

        List<ListenerDelegate> listeners = getListeners(ServletRequestListener.class);
        if (listeners != null) {
            ServletRequestEvent event = new ServletRequestEvent(context, request);
            listeners.forEach((listener) -> {
                listener.fireServletRequestDestroyed(event);
            });
        }
    }

    /**
     * Fires notification that a ServletRequest is about to come into scope of the web application.
     *
     * @param request
     */
    public void fireServletRequestInitialized(ServletRequest request) {
        List<ListenerDelegate> listeners = getListeners(ServletRequestListener.class);
        if (listeners != null) {
            ServletRequestEvent event = new ServletRequestEvent(context, request);
            listeners.forEach((listener) -> {
                listener.fireServletRequestInitialized(event);
            });
        }
    }

    // ServletRequestAttributeListener

    /**
     * Fires notification that an attribute has been added to the ServletRequest.
     *
     * @param request
     * @param name
     * @param value
     */
    public void fireServletRequestAttributeAdded(ServletRequest request, String name, Object value) {

        // ignore following attribute names
        // - javax.servlet.forward.request_uri
        // - javax.servlet.forward.context_path
        // - javax.servlet.forward.mapping
        // - javax.servlet.forward.path_info
        // - javax.servlet.forward.servlet_path
        // - javax.servlet.forward.query_string
        // - javax.servlet.include.request_uri
        // - javax.servlet.include.context_path
        // - javax.servlet.include.path_info
        // - javax.servlet.include.mapping
        // - javax.servlet.include.servlet_path
        // - javax.servlet.include.query_string
        // - javax.servlet.error.exception
        // - javax.servlet.error.exception_type
        // - javax.servlet.error.message
        // - javax.servlet.error.request_uri
        // - javax.servlet.error.servlet_name
        // - javax.servlet.error.status_code
        if (this.isIgnoreAttribute(name)) {
            return;
        }

        List<ListenerDelegate> listeners = getListeners(ServletRequestAttributeListener.class);
        if (listeners != null) {
            ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(context, request, name, value);
            listeners.forEach((listener) -> {
                listener.fireServletRequestAttributeAdded(event);
            });
        }
    }

    /**
     * Fires notification that an attribute has been removed from the ServletRequest.
     *
     * @param request
     * @param name
     * @param value
     */
    public void fireServletRequestAttributeRemoved(ServletRequest request, String name, Object value) {

        // ignore following attribute names
        // - javax.servlet.forward.request_uri
        // - javax.servlet.forward.context_path
        // - javax.servlet.forward.mapping
        // - javax.servlet.forward.path_info
        // - javax.servlet.forward.servlet_path
        // - javax.servlet.forward.query_string
        // - javax.servlet.include.request_uri
        // - javax.servlet.include.context_path
        // - javax.servlet.include.path_info
        // - javax.servlet.include.mapping
        // - javax.servlet.include.servlet_path
        // - javax.servlet.include.query_string
        // - javax.servlet.error.exception
        // - javax.servlet.error.exception_type
        // - javax.servlet.error.message
        // - javax.servlet.error.request_uri
        // - javax.servlet.error.servlet_name
        // - javax.servlet.error.status_code
        if (this.isIgnoreAttribute(name)) {
            return;
        }

        List<ListenerDelegate> listeners = getListeners(ServletRequestAttributeListener.class);
        if (listeners != null) {
            ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(context, request, name, value);
            listeners.forEach((listener) -> {
                listener.fireServletRequestAttributeRemoved(event);
            });
        }
    }

    /**
     * Fires notification that an attribute has been replaced on the ServletRequest.
     *
     * @param request
     * @param name
     * @param value
     */
    public void fireServletRequestAttributeReplaced(ServletRequest request, String name, Object value) {
        // ignore following attribute names
        // - javax.servlet.forward.request_uri
        // - javax.servlet.forward.context_path
        // - javax.servlet.forward.mapping
        // - javax.servlet.forward.path_info
        // - javax.servlet.forward.servlet_path
        // - javax.servlet.forward.query_string
        // - javax.servlet.include.request_uri
        // - javax.servlet.include.context_path
        // - javax.servlet.include.path_info
        // - javax.servlet.include.mapping
        // - javax.servlet.include.servlet_path
        // - javax.servlet.include.query_string
        // - javax.servlet.error.exception
        // - javax.servlet.error.exception_type
        // - javax.servlet.error.message
        // - javax.servlet.error.request_uri
        // - javax.servlet.error.servlet_name
        // - javax.servlet.error.status_code
        if (this.isIgnoreAttribute(name)) {
            return;
        }

        List<ListenerDelegate> listeners = getListeners(ServletRequestAttributeListener.class);
        if (listeners != null) {
            ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(context, request, name, value);
            listeners.forEach((listener) -> {
                listener.fireServletRequestAttributeReplaced(event);
            });
        }
    }

    // AsyncListener

    /**
     * Fires notification that this AsyncListener that an asynchronous operation has been completed.
     *
     * @param context
     * @param request
     * @param response
     * @throws IOException
     */
    public void fireAsyncOnComplete(AsyncContext context, ServletRequest request, ServletResponse response) throws IOException {
        List<ListenerDelegate> listeners = getListeners(AsyncListener.class);
        if (listeners != null) {
            AsyncEvent event = new AsyncEvent(context, request, response);
            listeners.forEach((listener) -> {
                try {
                    listener.fireAsyncOnComplete(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Fires notification that this AsyncListener that an asynchronous operation has timed out.
     *
     * @param context
     * @param request
     * @param response
     * @throws IOException
     */
    public void fireAsyncOnTimeout(AsyncContext context, ServletRequest request, ServletResponse response) throws IOException {
        List<ListenerDelegate> listeners = getListeners(AsyncListener.class);
        if (listeners != null) {
            AsyncEvent event = new AsyncEvent(context, request, response);
            listeners.forEach((listener) -> {
                try {
                    listener.fireAsyncOnTimeout(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Fires notification that this AsyncListener that an asynchronous operation has failed to complete.
     *
     * @param context
     * @param request
     * @param response
     * @throws IOException
     */
    public void fireAsyncOnError(AsyncContext context, ServletRequest request, ServletResponse response) throws IOException {
        List<ListenerDelegate> listeners = getListeners(AsyncListener.class);
        if (listeners != null) {
            AsyncEvent event = new AsyncEvent(context, request, response);
            listeners.forEach((listener) -> {
                try {
                    listener.fireAsyncOnError(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Fires notification that this AsyncListener that a new asynchronous cycle is being
     * initiated via a call to one of the {@link ServletRequest#startAsync}
     * methods.
     *
     * @param context
     * @param request
     * @param response
     * @throws IOException
     */
    public void fireAsyncOnStartAsync(AsyncContext context, ServletRequest request, ServletResponse response) throws IOException {
        List<ListenerDelegate> listeners = getListeners(AsyncListener.class);
        if (listeners != null) {
            AsyncEvent event = new AsyncEvent(context, request, response);
            listeners.forEach((listener) -> {
                try {
                    listener.fireAsyncOnStartAsync(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    /**
     * @param clazz The potential listener class
     * @return true if the provided class is a valid listener class
     */
    public static boolean isAcceptableListenerClass(final Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        for (Class c : ACCEPTABLE_LISTENER_CLASSES) {
            if (c.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param clazz The potential listener class
     * @return a valid listener class
     */
    public static Class<?> getAcceptableListenerClass(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        for (Class c : ACCEPTABLE_LISTENER_CLASSES) {
            if (c.isAssignableFrom(clazz)) {
                return c;
            }
        }
        return null;
    }


    /**
     *
     * @param name
     * @return
     */
    public boolean isIgnoreAttribute(String name) {
        for (String ignoreName : IGNORE_ATTRIBUTE_NAMES) {
            if (ignoreName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
