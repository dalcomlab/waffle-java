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

package com.dalcomlab.waffle.dispatch;

import com.dalcomlab.waffle.application.ApplicationContext;
import com.dalcomlab.waffle.common.Cookies;
import com.dalcomlab.waffle.common.Strings;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class HttpResponse implements HttpServletResponse {
    protected Http http;
    protected ApplicationContext context;
    protected Map<String, List<String>> headers = new HashMap<>();
    protected String contentType = "text/html";
    protected String characterEncoding = "ISO-8859-1";

    /**
     *
     */
    public HttpResponse(ApplicationContext context) {
        this.context = context;
    }


    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     *
     */
    public void initialize() {

    }

    /**
     *
     */
    public void destroy() {

    }

    public void setHttp(Http http) {
        this.http = http;
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (isCommitted()) {
            return;
        }
        if (cookie != null) {
            addHeader("Set-Cookie", Cookies.getCookieString(cookie));
        }
    }

    @Override
    public boolean containsHeader(String name) {
        return headers.containsKey(name);
    }

    @Override
    public String encodeURL(String url) {
        return url;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return url;
    }

    @Deprecated
    public String encodeUrl(String url) {
        return url;
    }

    @Deprecated
    public String encodeRedirectUrl(String url) {
        return url;
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        if (isCommitted()) {
            throw new IllegalStateException("sendError");
        }
        setStatus(sc);
    }

    @Override
    public void sendError(int sc) throws IOException {
        if (isCommitted()) {
            throw new IllegalStateException("sendError");
        }

        setStatus(sc);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        if (isCommitted()) {
            throw new IllegalStateException("sendRedirect");
        }

        setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
        setHeader("Location", location);
    }

    @Override
    public void setDateHeader(String name, long date) {
        setHeader(name, Long.toString(date));
    }

    @Override
    public void addDateHeader(String name, long date) {
        addHeader(name, Long.toString(date));
    }

    @Override
    public void setHeader(String name, String value) {
        if (isCommitted()) {
            return;
        }

        if (Strings.isNullOrEmpty(name)) {
            return;
        }

        if (name.equalsIgnoreCase("content-type")) {

        } else if (name.equalsIgnoreCase("content-length")) {

        }

        List<String> values = headers.get(name);
        if (values == null) {
            values = new LinkedList<>();
            headers.put(name, values);
        } else {
            values.clear();
        }

        values.add(value);

    }

    @Override
    public void addHeader(String name, String value) {
        if (isCommitted()) {
            return;
        }

        if (Strings.isNullOrEmpty(name)) {
            return;
        }

        if (name.equalsIgnoreCase("content-type") || name.equalsIgnoreCase("content-length")) {
            setHeader(name, value);
            return;
        }

        List<String> values = headers.get(name);
        if (values == null) {
            values = new LinkedList<>();
            headers.put(name, values);
        }
        values.add(value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        setHeader(name, Integer.toString(value));
    }

    @Override
    public void addIntHeader(String name, int value) {
        addHeader(name, Integer.toString(value));
    }

    @Override
    public void setStatus(int sc) {
        if (isCommitted()) {
            return;
        }
    }

    @Deprecated
    public void setStatus(int sc, String sm) {
        if (isCommitted()) {
            return;
        }
    }

    @Override
    public int getStatus() {
        return 200;
    }

    @Override
    public String getHeader(String name) {
        if (headers.containsKey(name)) {
            List<String> values = headers.get(name);
            if (values != null) {
                return values.get(0);
            }
        }
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        if (headers.containsKey(name)) {
            List<String> values = headers.get(name);
            if (values != null) {
                return Collections.unmodifiableList(values);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }


    @Override
    public String getContentType() {
        return this.contentType;
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setCharacterEncoding(String charset) {
        if (isCommitted()) {
            return;
        }

        this.characterEncoding = charset;
    }

    @Override
    public void setContentLength(int len) {
        if (isCommitted()) {
            return;
        }
        setHeader("Content-Length", Integer.toString(len));
    }

    @Override
    public void setContentLengthLong(long len) {
        if (isCommitted()) {
            return;
        }
        setHeader("Content-Length", Long.toString(len));
    }

    @Override
    public void setContentType(String type) {
        if (isCommitted()) {
            return;
        }

        setHeader("Content-Type", type);
    }

    @Override
    public void setBufferSize(int size) {
        if (isCommitted()) {
            return;
        }
    }

    @Override
    public int getBufferSize() {
        return 1024;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {
        if (isCommitted()) {
            return;
        }
    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {
        if (isCommitted()) {
            return;
        }
    }

    @Override
    public void setLocale(Locale loc) {
        if (isCommitted()) {
            return;
        }
    }

    @Override
    public Locale getLocale() {
        return null;
    }

}
