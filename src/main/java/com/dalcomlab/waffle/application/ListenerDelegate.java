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
import java.util.EventListener;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ListenerDelegate implements Delegate<EventListener> {

    private EventListener listener = null;
    private ListenerManager manager;

    /**
     * @param listener
     */
    private ListenerDelegate(ListenerManager manager, EventListener listener) {
        this.manager = manager;
        this.listener = listener;
    }

    /**
     * Creates a filter delegate with a given name of a filter and name of a class.
     *
     * @param manager
     * @param className
     * @return
     */
    public static ListenerDelegate createListener(ListenerManager manager, String className) {
        if (manager == null) {
            return null;
        }

        if (className == null || className.length() == 0) {
            return null;
        }

        Class<?> listenerClass;
        try {
            listenerClass = manager.getContext().getClassLoader().loadClass(className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return ListenerDelegate.createListener(manager, (Class<? extends EventListener>) listenerClass);
    }


    /**
     * Creates a listener delegate with a given listener class.
     *
     * @param manager
     * @param listenerClass
     * @return
     */
    public static ListenerDelegate createListener(ListenerManager manager, Class<? extends EventListener> listenerClass) {
        if (manager == null) {
            return null;
        }

        if (listenerClass == null) {
            return null;
        }

        ListenerDelegate delegate = new ListenerDelegate(manager, null);
        return delegate;
    }


    /**
     * Creates a listener delegate with a given listener instance.
     *
     * @param listener
     * @return
     */
    public static ListenerDelegate createListener(ListenerManager manager, EventListener listener) {
        if (manager == null) {
            return null;
        }

        if (listener == null) {
            return null;
        }


        ListenerDelegate delegate = new ListenerDelegate(manager, listener);
        return delegate;
    }


    // Delegate<T> implementation

    /**
     * @return
     */
    @Override
    public EventListener delegate() {
        return listener;
    }


    // ServletContextListener

    /**
     * Receives notification that the web application initialization
     * handle is starting.
     *
     * @param event the ServletContextEvent containing the ServletContext
     *              that is being initialized
     */
    public void fireContextInitialized(ServletContextEvent event) {
        if (!(listener instanceof ServletContextListener)) {
            return;
        }
        ((ServletContextListener) (listener)).contextInitialized(event);
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     *
     * @param event the ServletContextEvent containing the ServletContext
     *              that is being destroyed
     */
    public void fireContextDestroyed(ServletContextEvent event) {
        if (!(listener instanceof ServletContextListener)) {
            return;
        }
        ((ServletContextListener) (listener)).contextDestroyed(event);
    }


    /// ServletContextAttributeListener

    /**
     * Receives notification that an attribute has been added to the
     * ServletContext.
     *
     * @param event the ServletContextAttributeEvent containing the
     *              ServletContext to which the attribute was added, along with the
     *              attribute name and value
     */
    public void fireServletContextAttributeAdded(ServletContextAttributeEvent event) {
        if (!(listener instanceof ServletContextAttributeListener)) {
            return;
        }
        ((ServletContextAttributeListener) (listener)).attributeAdded(event);
    }

    /**
     * Receives notification that an attribute has been removed
     * from the ServletContext.
     *
     * @param event the ServletContextAttributeEvent containing the
     *              ServletContext from which the attribute was removed, along with
     *              the attribute name and value
     */
    public void fireServletContextAttributeRemoved(ServletContextAttributeEvent event) {
        if (!(listener instanceof ServletContextAttributeListener)) {
            return;
        }
        ((ServletContextAttributeListener) (listener)).attributeRemoved(event);
    }

    /**
     * Receives notification that an attribute has been replaced
     * in the ServletContext.
     *
     * @param event the ServletContextAttributeEvent containing the
     *              ServletContext in which the attribute was replaced, along with
     *              the attribute name and its old value
     */
    public void fireServletContextAttributeReplaced(ServletContextAttributeEvent event) {
        if (!(listener instanceof ServletContextAttributeListener)) {
            return;
        }
        ((ServletContextAttributeListener) (listener)).attributeReplaced(event);
    }


    // HttpSessionListener

    /**
     * Receives notification that a session has been created.
     *
     * @param event the HttpSessionEvent containing the session
     */
    public void fireSessionCreated(HttpSessionEvent event) {
        if (!(listener instanceof HttpSessionListener)) {
            return;
        }
        ((HttpSessionListener) (listener)).sessionCreated(event);
    }

    /**
     * Receives notification that a session is about to be invalidated.
     *
     * @param event the HttpSessionEvent containing the session
     */
    public void fireSessionDestroyed(HttpSessionEvent event) {
        if (!(listener instanceof HttpSessionListener)) {
            return;
        }
        ((HttpSessionListener) (listener)).sessionDestroyed(event);
    }

    // HttpSessionAttributeListener

    /**
     * Receives notification that an attribute has been added to a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     *              and the name and value of the attribute that was added
     */
    public void fireHttpSessionAttributeAdded(HttpSessionBindingEvent event) {
        if (!(listener instanceof HttpSessionAttributeListener)) {
            return;
        }
        ((HttpSessionAttributeListener) (listener)).attributeAdded(event);
    }

    /**
     * Receives notification that an attribute has been removed from a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     *              and the name and value of the attribute that was removed
     */
    public void fireHttpSessionAttributeRemoved(HttpSessionBindingEvent event) {
        if (!(listener instanceof HttpSessionAttributeListener)) {
            return;
        }
        ((HttpSessionAttributeListener) (listener)).attributeRemoved(event);
    }

    /**
     * Receives notification that an attribute has been replaced in a
     * session.
     *
     * @param event the HttpSessionBindingEvent containing the session
     *              and the name and (old) value of the attribute that was replaced
     */
    public void fireHttpSessionAttributeReplaced(HttpSessionBindingEvent event) {
        if (!(listener instanceof HttpSessionAttributeListener)) {
            return;
        }
        ((HttpSessionAttributeListener) (listener)).attributeReplaced(event);
    }


    // HttpSessionIdListener

    /**
     * Receives notification that session id has been changed in a
     * session.
     *
     * @param event        the HttpSessionBindingEvent containing the session
     *                     and the name and (old) value of the attribute that was replaced
     * @param oldSessionId the old session id
     */
    public void fireHttpSessionIdChanged(HttpSessionEvent event, String oldSessionId) {
        if (!(listener instanceof HttpSessionIdListener)) {
            return;
        }
        ((HttpSessionIdListener) (listener)).sessionIdChanged(event, oldSessionId);
    }

    // HttpSessionActivationListener

    /**
     * Notification that the session is about to be passivated.
     *
     * @param event the {@link HttpSessionEvent} indicating the passivation
     *              of the session
     */
    public void fireHttpSessionWillPassivate(HttpSessionEvent event) {
        if (!(listener instanceof HttpSessionActivationListener)) {
            return;
        }
        ((HttpSessionActivationListener) (listener)).sessionWillPassivate(event);
    }

    /**
     * Notification that the session has just been activated.
     *
     * @param event the {@link HttpSessionEvent} indicating the activation
     *              of the session
     */
    public void fireHttpSessionDidActivate(HttpSessionEvent event) {
        if (!(listener instanceof HttpSessionActivationListener)) {
            return;
        }
        ((HttpSessionActivationListener) (listener)).sessionDidActivate(event);
    }


    // ServletRequestListener

    /**
     * Receives notification that a ServletRequest is about to go out
     * of scope of the web application.
     *
     * @param event the ServletRequestEvent containing the ServletRequest
     *              and the ServletContext representing the web application
     */
    public void fireServletRequestDestroyed(ServletRequestEvent event) {
        if (!(listener instanceof ServletRequestListener)) {
            return;
        }
        ((ServletRequestListener) (listener)).requestDestroyed(event);
    }

    /**
     * Receives notification that a ServletRequest is about to come
     * into scope of the web application.
     *
     * @param event the ServletRequestEvent containing the ServletRequest
     *              and the ServletContext representing the web application
     */
    public void fireServletRequestInitialized(ServletRequestEvent event) {
        if (!(listener instanceof ServletRequestListener)) {
            return;
        }
        ((ServletRequestListener) (listener)).requestInitialized(event);
    }

    // ServletRequestAttributeListener

    /**
     * Receives notification that an attribute has been added to the
     * ServletRequest.
     *
     * @param event the ServletRequestAttributeEvent containing the
     *              ServletRequest and the name and value of the attribute that was
     *              added
     */
    public void fireServletRequestAttributeAdded(ServletRequestAttributeEvent event) {
        if (!(listener instanceof ServletRequestAttributeListener)) {
            return;
        }
        ((ServletRequestAttributeListener) (listener)).attributeAdded(event);
    }

    /**
     * Receives notification that an attribute has been removed from the
     * ServletRequest.
     *
     * @param event the ServletRequestAttributeEvent containing the
     *              ServletRequest and the name and value of the attribute that was
     *              removed
     */
    public void fireServletRequestAttributeRemoved(ServletRequestAttributeEvent event) {
        if (!(listener instanceof ServletRequestAttributeListener)) {
            return;
        }
        ((ServletRequestAttributeListener) (listener)).attributeRemoved(event);
    }

    /**
     * Receives notification that an attribute has been replaced on the
     * ServletRequest.
     *
     * @param event the ServletRequestAttributeEvent containing the
     *              ServletRequest and the name and (old) value of the attribute
     *              that was replaced
     */
    public void fireServletRequestAttributeReplaced(ServletRequestAttributeEvent event) {
        if (!(listener instanceof ServletRequestAttributeListener)) {
            return;
        }
        ((ServletRequestAttributeListener) (listener)).attributeReplaced(event);
    }

    // AsyncListener

    /**
     * Notifies this AsyncListener that an asynchronous operation has been completed.
     *
     * @param event the AsyncEvent indicating that an asynchronous
     *              operation has been completed
     */
    public void fireAsyncOnComplete(AsyncEvent event) throws IOException {
        if (!(listener instanceof AsyncListener)) {
            return;
        }
        ((AsyncListener) (listener)).onComplete(event);
    }


    /**
     * Notifies this AsyncListener that an asynchronous operation has timed out.
     *
     * @param event the AsyncEvent indicating that an asynchronous
     *              operation has timed out
     */
    public void fireAsyncOnTimeout(AsyncEvent event) throws IOException {
        if (!(listener instanceof AsyncListener)) {
            return;
        }
        ((AsyncListener) (listener)).onTimeout(event);
    }


    /**
     * Notifies this AsyncListener that an asynchronous operation has failed to complete.
     *
     * @param event the AsyncEvent indicating that an asynchronous
     *              operation has failed to complete
     */
    public void fireAsyncOnError(AsyncEvent event) throws IOException {
        if (!(listener instanceof AsyncListener)) {
            return;
        }
        ((AsyncListener) (listener)).onError(event);
    }


    /**
     * Notifies this AsyncListener that a new asynchronous cycle is being initiated via
     * a call to one of the {@link ServletRequest#startAsync} methods.
     *
     * @param event the AsyncEvent indicating that a new asynchronous
     *              cycle is being initiated
     */
    public void fireAsyncOnStartAsync(AsyncEvent event) throws IOException {
        if (!(listener instanceof AsyncListener)) {
            return;
        }
        ((AsyncListener) (listener)).onStartAsync(event);
    }
}
