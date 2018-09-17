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

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.util.Enumeration;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class HttpSessionDelegate implements HttpSession {

    private SessionManager manager = null;
    private boolean invalid = false;
    private long creationTime = -1;
    private String sessionId = "";
    private long lastAccessedTime = -1;
    private int maxInactiveInterval = -1;
    private long accessedTime = -1;
    private AttributesMap attributes = new AttributesMap();
    private Cookie cookie;

    /**
     *
     * @param sessionId
     * @param manager
     */
    public HttpSessionDelegate(String sessionId, SessionManager manager) {
        this.sessionId = sessionId;
        this.manager = manager;
        this.cookie = manager.createCookie(sessionId);
    }

    /**
     * @return
     */
    public boolean isExpired() {
//        if (invalid) {
//            return true;
//        }
//        return System.currentTimeMillis() - creationTime > maxInactiveInterval * 1000;
        return false;
    }


    public void access() {
        lastAccessedTime = System.currentTimeMillis();
    }

    public Cookie getCookie() {
        return this.cookie;
    }

    // HttpSession implementation

    /**
     * Returns the time when this session was created, measured
     * in milliseconds since midnight January 1, 1970 GMT.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return a <code>long</code> specifying
     * when this session was created,
     * expressed in
     * milliseconds since 1/1/1970 GMT
     */
    @Override
    public long getCreationTime() {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        return creationTime;
    }


    /**
     * Returns a string containing the unique identifier assigned
     * to this session. The identifier is assigned
     * by the servlet container and is implementation dependent.
     *
     * @return a string specifying the identifier
     * assigned to this session
     */
    @Override
    public String getId() {
        return sessionId;
    }


    /**
     * Returns the last time the client sent a channel associated with
     * this session, as the number of milliseconds since midnight
     * January 1, 1970 GMT, and marked by the time the container received the
     * channel.
     *
     * <p>Actions that your application takes, such as getting or setting
     * a value associated with the session, do not affect the access
     * time.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return a <code>long</code>
     * representing the last time
     * the client sent a channel associated
     * with this session, expressed in
     * milliseconds since 1/1/1970 GMT
     */
    @Override
    public long getLastAccessedTime() {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        return lastAccessedTime;
    }


    /**
     * Returns the ServletContext to which this session belongs.
     *
     * @return The ServletContext object for the web application
     * @since Servlet 2.3
     */
    @Override
    public ServletContext getServletContext() {
        return manager.getContext();
    }


    /**
     * Specifies the time, in seconds, between client requests before the
     * servlet container will invalidate this session.
     *
     * <p>An <tt>interval</tt> value of zero or less indicates that the
     * session should never timeout.
     *
     * @param interval An integer specifying the number
     *                 of seconds
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval = interval;
    }


    /**
     * Returns the maximum time interval, in seconds, that
     * the servlet container will keep this session open between
     * client accesses. After this interval, the servlet container
     * will invalidate the session.  The maximum time interval can be set
     * with the <code>setMaxInactiveInterval</code> method.
     *
     * <p>A return value of zero or less indicates that the
     * session will never timeout.
     *
     * @return an integer specifying the number of
     * seconds this session remains open
     * between client requests
     * @see        #setMaxInactiveInterval
     */
    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }


    /**
     * @return the {@link HttpSessionContext} for this session.
     * @deprecated As of Version 2.1, this method is
     * deprecated and has no replacement.
     * It will be removed in a future
     * version of the Java Servlet API.
     */
    @Deprecated
    public HttpSessionContext getSessionContext() {
        return null;

    }

    /**
     * Returns the object bound with the specified name in this session, or
     * <code>null</code> if no object is bound under the name.
     *
     * @param name a string specifying the name of the object
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return the object with the specified name
     */
    public Object getAttribute(String name) {
        if (invalid) {
            //throw new IllegalStateException("Invalid session");
        }

        return attributes.getAttribute(name);
    }


    /**
     * @param name a string specifying the name of the object
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return the object with the specified name
     * @deprecated As of Version 2.2, this method is
     * replaced by {@link #getAttribute}.
     */
    @Deprecated
    public Object getValue(String name) {
        return getAttribute(name);
    }


    /**
     * Returns an <code>Enumeration</code> of <code>String</code> objects
     * containing the names of all the objects bound to this session.
     *
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return an <code>Enumeration</code> of
     * <code>String</code> objects specifying the
     * names of all the objects bound to
     * this session
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        return attributes.getAttributeNames();
    }


    /**
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @return an array of <code>String</code>
     * objects specifying the
     * names of all the objects bound to
     * this session
     * @deprecated As of Version 2.2, this method is
     * replaced by {@link #getAttributeNames}
     */
    @Deprecated
    public String[] getValueNames() {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        return attributes.getAttributeNameSet().stream().toArray(String[]::new);
    }


    /**
     * Binds an object to this session, using the name specified.
     * If an object of the same name is already bound to the session,
     * the object is replaced.
     *
     * <p>After this method executes, and if the new object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueBound</code>. The container then
     * notifies any <code>HttpSessionAttributeListener</code>s in the web
     * application.
     *
     * <p>If an object was already bound to this session of this name
     * that implements <code>HttpSessionBindingListener</code>, its
     * <code>HttpSessionBindingListener.valueUnbound</code> method is called.
     *
     * <p>If the value passed in is null, this has the same effect as calling
     * <code>removeAttribute()</code>.
     *
     * @param name  the name to which the object is bound;
     *              cannot be null
     * @param value the object to be bound
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public void setAttribute(String name, Object value) {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        if (name == null) {
            throw new IllegalArgumentException("The name mustn't be a null.");
        }

        if (value == null) {
            removeAttribute(name);
            return;
        }


        Object unbound = attributes.setAttribute(name, value);

        if (value instanceof HttpSessionBindingListener) {
            if (unbound != value) {
                final HttpSessionBindingListener listener = (HttpSessionBindingListener) value;
                try {
                    listener.valueBound(new HttpSessionBindingEvent(this, name));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (unbound instanceof HttpSessionBindingListener) {
            final HttpSessionBindingListener listener = (HttpSessionBindingListener) unbound;
            try {
                listener.valueUnbound(new HttpSessionBindingEvent(this, name));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            if (unbound == null) {
                manager.fireHttpSessionAttributeAdded(this, name, value);
            } else {
                manager.fireHttpSessionAttributeReplaced(this, name, unbound);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param name  the name to which the object is bound;
     *              cannot be null
     * @param value the object to be bound; cannot be null
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @deprecated As of Version 2.2, this method is
     * replaced by {@link #setAttribute}
     */
    @Deprecated
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }


    /**
     * Removes the object bound with the specified name from
     * this session. If the session does not have an object
     * bound with the specified name, this method does nothing.
     *
     * <p>After this method executes, and if the object
     * implements <code>HttpSessionBindingListener</code>,
     * the container calls
     * <code>HttpSessionBindingListener.valueUnbound</code>. The container
     * then notifies any <code>HttpSessionAttributeListener</code>s in the web
     * application.
     *
     * @param name the name of the object to
     *             remove from this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     */
    @Override
    public void removeAttribute(String name) {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        Object unbound = attributes.removeAttribute(name);
        if (unbound != null) {

            if (unbound instanceof HttpSessionBindingListener) {
                try {
                    final HttpSessionBindingListener listener = (HttpSessionBindingListener) unbound;
                    listener.valueUnbound(new HttpSessionBindingEvent(this, name));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                manager.fireHttpSessionAttributeRemoved(this, name, unbound);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param name the name of the object to
     *             remove from this session
     * @throws IllegalStateException if this method is called on an
     *                               invalidated session
     * @deprecated As of Version 2.2, this method is
     * replaced by {@link #removeAttribute}
     */
    @Deprecated
    public void removeValue(String name) {
        removeAttribute(name);
    }


    /**
     * Invalidates this session then unbinds any objects bound
     * to it.
     *
     * @throws IllegalStateException if this method is called on an
     *                               already invalidated session
     */
    @Override
    public void invalidate() {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        final Enumeration<String> names = getAttributeNames();
        while (names.hasMoreElements()) {
            removeAttribute(names.nextElement());
        }
        

        try {
            manager.fireSessionDestroyed(this);
        } catch(Exception e) {
            e.printStackTrace();
        }

        invalid = true;

    }

    /**
     * Returns <code>true</code> if the client does not yet know about the
     * session or if the client chooses not to join the session.  For
     * example, if the server used only cookie-based sessions, and
     * the client had disabled the use of cookies, then a session would
     * be new on each channel.
     *
     * @return <code>true</code> if the
     * server has created a session,
     * but the client has not yet joined
     * @throws IllegalStateException if this method is called on an
     *                               already invalidated session
     */
    @Override
    public boolean isNew() {
        if (invalid) {
            throw new IllegalStateException("Invalid session");
        }

        return (lastAccessedTime == -1);
    }

}
