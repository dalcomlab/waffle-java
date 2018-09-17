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

package com.dalcomlab.waffle.deployment.descriptor;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class SessionConfigDescriptor implements Descriptor {

    private int sessionTimeout = 0;
    private String cookieName = null;
    private String cookieDomain = null;
    private String cookiePath = null;
    private String cookieComment = null;
    private boolean cookieHttpOnly = false;
    private boolean cookieSecure = false;
    private int cookieMaxAge = 0;
    private String trackingMode = null;


    /**
     *
     */
    public SessionConfigDescriptor() {
        initialize();
    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        sessionTimeout = 0;
        cookieName = null;
        cookieDomain = null;
        cookiePath = null;
        cookieComment = null;
        cookieHttpOnly = false;
        cookieSecure = false;
        cookieMaxAge = 0;
        trackingMode = null;
    }

    /**
     *
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        return true;
    }

    /**
     *
     * @return
     */
    public int getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     *
     * @param sessionTimeout
     */
    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    /**
     *
     * @return
     */
    public String getCookieName() {
        if (this.cookieName == null) {
            return "";
        }

        return this.cookieName;
    }

    /**
     *
     * @param cookieName
     */
    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    /**
     *
     * @return
     */
    public String getCookieDomain() {
        if (this.cookieName == null) {
            return "";
        }

        return this.cookieDomain;
    }

    /**
     *
     * @param cookieDomain
     */
    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    /**
     *
     * @return
     */
    public String getCookiePath() {
        if (this.cookiePath == null) {
            return "";
        }

        return this.cookiePath;
    }

    /**
     *
     * @param cookiePath
     */
    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    /**
     *
     * @return
     */
    public String getCookieComment() {
        if (this.cookieComment == null) {
            return "";
        }

        return this.cookieComment;
    }

    /**
     *
     * @param cookieComment
     */
    public void setCookieComment(String cookieComment) {
        this.cookieComment = cookieComment;
    }

    /**
     *
     * @return
     */
    public boolean getCookieHttpOnly() {
        return this.cookieHttpOnly;
    }

    /**
     *
     * @param cookieHttpOnly
     */
    public void setCookieHttpOnly(boolean cookieHttpOnly) {
        this.cookieHttpOnly = cookieHttpOnly;
    }

    /**
     *
     * @return
     */
    public boolean getCookieSecure() {
        return this.cookieSecure;
    }

    /**
     *
     * @param cookieSecure
     */
    public void setCookieSecure(boolean cookieSecure) {
        this.cookieSecure = cookieSecure;
    }

    /**
     *
     * @return
     */
    public int getCookieMaxAge() {
        return this.cookieMaxAge;
    }

    /**
     *
     * @param cookieMaxAge
     */
    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    /**
     *
     * @return
     */
    public boolean isMergeable() {
        return true;
    }
}
