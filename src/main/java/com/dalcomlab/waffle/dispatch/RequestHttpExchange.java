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
import com.dalcomlab.waffle.application.ServletInputStreamImpl;
import com.dalcomlab.waffle.common.Cookies;
import com.dalcomlab.waffle.common.QueryString;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class RequestHttpExchange extends HttpRequest {

    protected HttpExchange httpExchange;
    protected Hashtable<String, String[]> parameters = null;
    protected List<Cookie> cookies = null;
    protected ByteArrayOutputStream requestStream = null;

    /**
     * Creates new instance
     *
     * @param context
     * @param httpExchange
     */
    public RequestHttpExchange(ApplicationContext context, HttpExchange httpExchange) {
        super(context);
        this.httpExchange = httpExchange;
    }


    @Override
    public Cookie[] getCookies() {

        if (this.cookies != null) {
            return cookies.toArray(new Cookie[cookies.size()]);
        }

        this.cookies = new LinkedList<>();
        Headers headers = this.httpExchange.getRequestHeaders();
        for (String name : headers.keySet()) {
            if (name.equalsIgnoreCase("cookie")) {
                List<String> values = headers.get(name);
                if (values != null) {
                    for (String value : values) {
                        Cookie[] cookieList = Cookies.parse(value);
                        if (cookieList != null) {
                            this.cookies.addAll(Arrays.asList(cookieList));
                        }
                    }
                }
            }
        }

        return cookies.toArray(new Cookie[cookies.size()]);
    }

    @Override
    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value != null) {
            return Long.parseLong(value);
        }
        return 0;
    }

    @Override
    public String getHeader(String name) {
        Headers headers = httpExchange.getRequestHeaders();
        if (headers != null) {
            return headers.getFirst(name);
        }
        return null;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        Headers headers = httpExchange.getRequestHeaders();
        if (headers != null) {
            List<String> values = headers.get(name);
            if (values != null) {
                return Collections.enumeration(values);
            }
        }
        return Collections.emptyEnumeration();
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Headers headers = httpExchange.getRequestHeaders();
        if (headers != null) {
            List names = new LinkedList();
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                names.add(header.getKey());
            }

            return Collections.enumeration(names);
        }
        return Collections.emptyEnumeration();
    }


    @Override
    public String getMethod() {
        return httpExchange.getRequestMethod();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (requestStream != null) {
            return new ServletInputStreamImpl(new ByteArrayInputStream(requestStream.toByteArray()));
        }
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (parameters != null) {
            return parameters;
        }

        if (requestStream == null) {
            requestStream = new ByteArrayOutputStream();

            InputStream inputStream = httpExchange.getRequestBody();
            byte[] buffer = new byte[1024];
            int totalBytes = 0;
            int read = 0;
            try {
                while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    requestStream.write(buffer, 0, read);
                    totalBytes += read;
                }
                requestStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        parameters = new Hashtable<>();
        if (getMethod().equalsIgnoreCase("POST")) {
            //Hashtable<String, String[]> postParameters = QueryString.parseQueryString(httpExchange.getRequestBody());
            if (requestStream != null) {
                Hashtable<String, String[]> postParameters = QueryString.parseQueryString(new ByteArrayInputStream(requestStream.toByteArray()));
                if (postParameters != null) {
                    this.parameters.putAll(postParameters);
                }
            }
        }

        if (httpExchange.getRequestURI() != null) {
            Hashtable<String, String[]> getParameters = QueryString.parseQueryString(httpExchange.getRequestURI().getQuery());
            if (getParameters != null) {
                this.parameters.putAll(getParameters);
            }
        }

        return parameters;
    }

    @Override
    public String getProtocol() {
        return httpExchange.getProtocol();
    }


    @Override
    public String getScheme() {
        //return httpExchange.getRequestURI().getScheme();
        return "http";
    }

    @Override
    public String getServerName() {
        return "localhost";
    }

    @Override
    public int getServerPort() {
        return 8080;
    }

    @Override
    public String getRemoteAddr() {
        return "127.0.0.1";
    }

    @Override
    public String getRemoteHost() {
        return "0.0.0.0";
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

}
