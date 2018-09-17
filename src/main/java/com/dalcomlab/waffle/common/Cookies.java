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
package com.dalcomlab.waffle.common;

import javax.servlet.http.Cookie;
import java.util.LinkedList;
import java.util.List;

import static com.dalcomlab.waffle.common.Strings.isNullOrEmpty;

/**
 *
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Cookies {

    /**
     *
     * @param cookie
     * @return
     */
    public static String getCookieString(Cookie cookie) {
        if (cookie == null) {
            return "";
        }

        String name = cookie.getName();
        String value = cookie.getValue();
        String domain = cookie.getDomain();
        String path = cookie.getPath();
        int maxage = cookie.getMaxAge();
        String comment = cookie.getComment();
        boolean secure = cookie.getSecure();
        boolean httponly = cookie.isHttpOnly();
        int version = cookie.getVersion();

        if (Strings.isNullOrEmpty(name)) {
            return "";
        }

        StringBuilder setCookieHeader = new StringBuilder();
        setCookieHeader.append(name).append("=");

        if (!isNullOrEmpty(value)) {
            setCookieHeader.append(value);
        }

        if (!isNullOrEmpty(domain)) {
            setCookieHeader.append(";Domain=").append(domain);
        }

        if (!isNullOrEmpty(path)) {
            setCookieHeader.append(";Path=").append(path);
        }

        if (maxage >= 0) {
            setCookieHeader.append(";Max-Age=").append(maxage);
        }

        if (!isNullOrEmpty(comment)) {
            setCookieHeader.append(";Comment=").append(comment);
        }

        if (secure) {
            setCookieHeader.append(";Secure");
        }

        if (httponly) {
            setCookieHeader.append(";HttpOnly");
        }

        if (version >= 1) {
            setCookieHeader.append(";Version=").append(version);
        }

        return setCookieHeader.toString();
    }

    /**
     * Creates cookies from set-cookie or set-cookie2 header string.
     *
     * @param header
     * @return
     */
    public static Cookie[] parse(String header) {
        String[] cookieHeaders = header.split(";");
        if (cookieHeaders == null || cookieHeaders.length == 0) {
            return null;
        }

        List<Cookie> cookies = new LinkedList<>();
        for (String cookieHeader : cookieHeaders) {
            Cookie cookie = parseCookie(cookieHeader);
            if (cookie != null) {
                cookies.add(cookie);
            }
        }

        return cookies.stream().toArray(Cookie[]::new);
    }

    public static Cookie parseCookie(String header) {
        if (header == null || header.length() == 0) {
            return null;
        }

        String[] pair = header.split("=");

        String name = pair[0].trim();
        String value = "";

        if (pair.length > 1) {
            value = pair[1].trim();
        }

        Cookie cookie = null;
        try {
            cookie = new Cookie(name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;
    }
}
