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

import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class SessionManager extends Manager {

    private String sessionTokenName = "JSESSIONID";
    private Map<String, HttpSessionDelegate> sessions = new HashMap<>();
    private SessionCookieConfig sessionCookieConfig;

    /**
     * Session tracking modes
     */
    private Set<SessionTrackingMode> sessionTrackingModes = null;
    private Set<SessionTrackingMode> defaultSessionTrackingModes = null;
    private Set<SessionTrackingMode> supportedSessionTrackingModes = null;


    /**
     * @param context
     */
    public SessionManager(ApplicationContext context) {
        super(context);
        sessionCookieConfig = new SessionCookieConfigImpl(context);
    }

    /**
     * @return
     */
    public String getSessionTokenName() {
        return sessionTokenName;
    }

    /**
     * @param sessionTokenName
     */
    public void setSessionTokenName(String sessionTokenName) {
        this.sessionTokenName = sessionTokenName;
    }

    /**
     * @return
     */
    public HttpSessionDelegate createSession() {
        String sessionId = createSessionId();
        HttpSessionDelegate session = new HttpSessionDelegate(sessionId, this);
        sessions.put(sessionId, session);
        System.out.println(" * new session id : " + sessionId);
        return session;
    }

    /**
     * @param sessionId
     * @return
     */
    public Cookie createCookie(String sessionId) {

        Cookie cookie = new Cookie(this.sessionTokenName, sessionId);

        SessionCookieConfig config = this.sessionCookieConfig;
        if (config != null) {
            String domain = config.getDomain();
            String comment = config.getComment();
            String path = config.getPath();

            if (domain != null) {
                cookie.setDomain(domain);
            }

            if (comment != null) {
                cookie.setComment(comment);
            }

            if (path != null) {
                cookie.setPath(path);
            } else {
                cookie.setPath(context.getContextPath());
            }

            cookie.setMaxAge(config.getMaxAge());
            cookie.setSecure(config.isSecure());
            cookie.setHttpOnly(config.isHttpOnly());
        } else {
            cookie.setPath(context.getContextPath());
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
        }
        return cookie;
    }

    /**
     * @param sessionId
     * @return
     */
    public HttpSessionDelegate findSession(String sessionId) {
        HttpSessionDelegate session = sessions.get(sessionId);
        if (session != null) {
            session.access();
        }
        return session;
    }


    /**
     * @return
     */
    public String createSessionId() {
        UUID id = UUID.randomUUID();
        return id.toString();
    }

    // ServletContext


    /**
     * @return
     */
    public SessionCookieConfig getSessionCookieConfig() {
        return sessionCookieConfig;
    }


    /**
     * @param sessionTrackingModes
     */
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {

        if (context.isContextInitialized()) {
            String message = MessageFormat.format("The setSessionTrackingModes must be set during initialization.", null);
            throw new IllegalStateException(message);
        }

        // Check that only supported tracking modes have been requested
        for (SessionTrackingMode sessionTrackingMode : sessionTrackingModes) {
            if (!supportedSessionTrackingModes.contains(sessionTrackingMode)) {
                throw new IllegalArgumentException("applicationContext.setSessionTracking.iae.invalid");
            }
        }

        // Check SSL has not be configured with anything else
        if (sessionTrackingModes.contains(SessionTrackingMode.SSL)) {
            if (sessionTrackingModes.size() > 1) {
                throw new IllegalArgumentException("applicationContext.setSessionTracking.iae.ssl");
            }
        }

        this.sessionTrackingModes = sessionTrackingModes;
    }


    /**
     * @return
     */
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return defaultSessionTrackingModes;
    }


    /**
     * @return
     */
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        if (sessionTrackingModes != null) {
            return sessionTrackingModes;
        }
        return defaultSessionTrackingModes;
    }

    // HttpSessionListener

    /**
     * Fires notification that a session has been created.
     *
     * @param session
     */
    public void fireSessionCreated(HttpSession session) {
        context.getListenerManager().fireSessionCreated(session);
    }

    /**
     * Fires notification that a session is about to be invalidated.
     *
     * @param session
     */
    public void fireSessionDestroyed(HttpSession session) {
        context.getListenerManager().fireSessionDestroyed(session);
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
        context.getListenerManager().fireHttpSessionAttributeAdded(session, name, value);
    }

    /**
     * Fires notification that an attribute has been removed from a session.
     *
     * @param session
     * @param name
     * @param value
     */
    public void fireHttpSessionAttributeRemoved(HttpSession session, String name, Object value) {
        context.getListenerManager().fireHttpSessionAttributeRemoved(session, name, value);
    }

    /**
     * Fires notification that an attribute has been replaced in a session.
     *
     * @param session
     * @param name
     */
    public void fireHttpSessionAttributeReplaced(HttpSession session, String name, Object value) {
        context.getListenerManager().fireHttpSessionAttributeRemoved(session, name, value);
    }

    // HttpSessionIdListener

    /**
     * Fires notification that session id has been changed in a session.
     *
     * @param session
     * @param oldSessionId
     */
    public void fireHttpSessionIdChanged(HttpSession session, String oldSessionId) {
        context.getListenerManager().fireHttpSessionIdChanged(session, oldSessionId);
    }

    // HttpSessionActivationListener

    /**
     * Fires notification that the session is about to be passivated.
     *
     * @param session
     */
    public void fireHttpSessionWillPassivate(HttpSession session) {
        context.getListenerManager().fireHttpSessionWillPassivate(session);
    }

    /**
     * Fires notification that the session has just been activated.
     *
     * @param session
     */
    public void fireHttpSessionDidActivate(HttpSession session) {
        context.getListenerManager().fireHttpSessionDidActivate(session);
    }

}
