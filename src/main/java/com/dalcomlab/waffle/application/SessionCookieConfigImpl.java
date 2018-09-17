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

import javax.servlet.ServletRequest;
import javax.servlet.SessionCookieConfig;
import java.text.MessageFormat;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class SessionCookieConfigImpl implements SessionCookieConfig {

    private ApplicationContext context;
    private String name;
    private String domain;
    private String path;
    private String comment;
    private boolean httpOnly = true;
    private boolean secure;
    private int maxAge = -1;

    /**
     *
     */
    public SessionCookieConfigImpl(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Sets the name that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * <p>NOTE: Changing the name of session tracking cookies may break
     * other tiers (for example, a load balancing frontend) that assume
     * the cookie name to be equal to the default <tt>JSESSIONID</tt>,
     * and therefore should only be done cautiously.
     *
     * @param name the cookie name to use
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     */
    @Override
    public void setName(String name) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The name must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.name = name;
    }


    /**
     * Gets the name that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * <p>By default, <tt>JSESSIONID</tt> will be used as the cookie name.
     *
     * @return the cookie name set via {@link #setName}, or
     * <tt>null</tt> if {@link #setName} was never called
     *
     * @see javax.servlet.http.Cookie#getName()
     */
    @Override
    public String getName() {
        return this.name;
    }


    /**
     * Sets the domain name that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * @param domain the cookie domain to use
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     *
     * @see javax.servlet.http.Cookie#setDomain(String)
     */
    @Override
    public void setDomain(String domain) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The domain must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.domain = domain;
    }


    /**
     * Gets the domain name that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * @return the cookie domain set via {@link #setDomain}, or
     * <tt>null</tt> if {@link #setDomain} was never called
     *
     * @see javax.servlet.http.Cookie#getDomain()
     */
    @Override
    public String getDomain() {
        return this.domain;
    }


    /**
     * Sets the path that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * @param path the cookie path to use
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     *
     * @see javax.servlet.http.Cookie#setPath(String)
     */
    @Override
    public void setPath(String path) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The path must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.path = path;
    }


    /**
     * Gets the path that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * <p>By default, the context path of the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired will
     * be used.
     *
     * @return the cookie path set via {@link #setPath}, or <tt>null</tt>
     * if {@link #setPath} was never called
     *
     * @see javax.servlet.http.Cookie#getPath()
     */
    @Override
    public String getPath() {
        return this.path;
    }


    /**
     * Sets the comment that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * <p>As a side effect of this call, the session tracking cookies
     * will be marked with a <code>Version</code> attribute equal to
     * <code>1</code>.
     *
     * @param comment the cookie comment to use
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     *
     * @see javax.servlet.http.Cookie#setComment(String)
     * @see javax.servlet.http.Cookie#getVersion
     */
    @Override
    public void setComment(String comment) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The comment must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.comment = comment;
    }


    /**
     * Gets the comment that will be assigned to any session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * @return the cookie comment set via {@link #setComment}, or
     * <tt>null</tt> if {@link #setComment} was never called
     *
     * @see javax.servlet.http.Cookie#getComment()
     */
    @Override
    public String getComment() {
        return this.comment;
    }


    /**
     * Marks or unmarks the session tracking cookies created on behalf
     * of the application represented by the <tt>ServletContext</tt> from
     * which this <tt>SessionCookieConfig</tt> was acquired as
     * <i>HttpOnly</i>.
     *
     * <p>A cookie is marked as <tt>HttpOnly</tt> by adding the
     * <tt>HttpOnly</tt> attribute to it. <i>HttpOnly</i> cookies are
     * not supposed to be exposed to client-side scripting code, and may
     * therefore help mitigate certain kinds of cross-site scripting
     * attacks.
     *
     * @param httpOnly true if the session tracking cookies created
     * on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired shall be marked as <i>HttpOnly</i>, false otherwise
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     *
     * @see javax.servlet.http.Cookie#setHttpOnly(boolean)
     */
    @Override
    public void setHttpOnly(boolean httpOnly) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The httpOnly must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.httpOnly = httpOnly;
    }


    /**
     * Checks if the session tracking cookies created on behalf of the
     * application represented by the <tt>ServletContext</tt> from which
     * this <tt>SessionCookieConfig</tt> was acquired will be marked as
     * <i>HttpOnly</i>.
     *
     * @return true if the session tracking cookies created on behalf of
     * the application represented by the <tt>ServletContext</tt> from
     * which this <tt>SessionCookieConfig</tt> was acquired will be marked
     * as <i>HttpOnly</i>, false otherwise
     *
     * @see javax.servlet.http.Cookie#isHttpOnly()
     */
    @Override
    public boolean isHttpOnly() {
        return this.httpOnly;
    }


    /**
     * Marks or unmarks the session tracking cookies created on behalf of
     * the application represented by the <tt>ServletContext</tt> from which
     * this <tt>SessionCookieConfig</tt> was acquired as <i>secure</i>.
     *
     * <p>One use case for marking a session tracking cookie as
     * <tt>secure</tt>, even though the channel that initiated the session
     * came over HTTP, is to support a topology where the web container is
     * front-ended by an SSL offloading load balancer.
     * In this case, the traffic between the client and the load balancer
     * will be over HTTPS, whereas the traffic between the load balancer
     * and the web container will be over HTTP.
     *
     * @param secure true if the session tracking cookies created on
     * behalf of the application represented by the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired shall be
     * marked as <i>secure</i> even if the channel that initiated the
     * corresponding session is using plain HTTP instead of HTTPS, and false
     * if they shall be marked as <i>secure</i> only if the channel that
     * initiated the corresponding session was also secure
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     *
     * @see javax.servlet.http.Cookie#setSecure(boolean)
     * @see ServletRequest#isSecure()
     */
    @Override
    public void setSecure(boolean secure) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The secure must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.secure = secure;
    }


    /**
     * Checks if the session tracking cookies created on behalf of the
     * application represented by the <tt>ServletContext</tt> from which
     * this <tt>SessionCookieConfig</tt> was acquired will be marked as
     * <i>secure</i> even if the channel that initiated the corresponding
     * session is using plain HTTP instead of HTTPS.
     *
     * @return true if the session tracking cookies created on behalf of the
     * application represented by the <tt>ServletContext</tt> from which
     * this <tt>SessionCookieConfig</tt> was acquired will be marked as
     * <i>secure</i> even if the channel that initiated the corresponding
     * session is using plain HTTP instead of HTTPS, and false if they will
     * be marked as <i>secure</i> only if the channel that initiated the
     * corresponding session was also secure
     *
     * @see javax.servlet.http.Cookie#getSecure()
     * @see ServletRequest#isSecure()
     */
    @Override
    public boolean isSecure() {
        return this.secure;
    }


    /**
     * Sets the lifetime (in seconds) for the session tracking cookies
     * created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * @param maxAge the lifetime (in seconds) of the session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * @throws IllegalStateException if the <tt>ServletContext</tt>
     * from which this <tt>SessionCookieConfig</tt> was acquired has
     * already been initialized
     *
     * @see javax.servlet.http.Cookie#setMaxAge
     */
    @Override
    public void setMaxAge(int maxAge) {
        if(context.isContextInitialized()) {
            String message = MessageFormat.format("The maxAge must be set during initialization.", null);
            throw new IllegalStateException(message);
        }
        this.maxAge = maxAge;
    }


    /**
     * Gets the lifetime (in seconds) of the session tracking cookies
     * created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired.
     *
     * <p>By default, <tt>-1</tt> is returned.
     *
     * @return the lifetime (in seconds) of the session tracking
     * cookies created on behalf of the application represented by the
     * <tt>ServletContext</tt> from which this <tt>SessionCookieConfig</tt>
     * was acquired, or <tt>-1</tt> (the default)
     *
     * @see javax.servlet.http.Cookie#getMaxAge
     */
    @Override
    public int getMaxAge() {
        return this.maxAge;
    }
}
