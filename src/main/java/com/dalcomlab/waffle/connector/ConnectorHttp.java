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
package com.dalcomlab.waffle.connector;

import com.dalcomlab.waffle.dispatch.RequestHttpExchange;
import com.dalcomlab.waffle.dispatch.ResponseHttpExchange;
import com.dalcomlab.waffle.server.Server;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConnectorHttp implements Connector, HttpHandler {

    public static String SCHEME = "dispatch";
    private Server server = null;
    private HttpServer http = null;
    private int port = 8080;

    private boolean XPoweredBy = true; // false
    private int maxPostSizeBytes;// 2097152
    private int maxConnections; // 250
    private int requestBodyBufferSizeBytes; // 4096
    private int sendBufferSizeBytes; // 9192
    private boolean traceEnable; // true;
    private boolean cometSupportEnabled; // false;
    private boolean uploadTimeoutEnabled; // false;
    private int uploadTimeoutMillis; // 5
    private boolean chunkingEnabled; // true;
    private String version; // HTTP/1.1
    private String defaultResponseType; // text/html;charset=iso-8859-1
    private int timeoutSeconds; // 30;

    public ConnectorHttp(int port) {
        this.port = port;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getScheme() {
        return SCHEME;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public void setServer(Server server) {
        // if (isStarted()) {
        //     throw new IllegalStateException("");
        // }
        this.server = server;
    }

    @Override
    public boolean isStarted() {
        return (http != null);
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {
        if (isStarted()) {
            throw new IllegalStateException("");
        }

        try {
            http = HttpServer.create();
            http.createContext("/", this);
            http.bind(new InetSocketAddress(this.port), 0);
            http.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (http != null) {
            http.stop(0);
        }
        http = null;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!isStarted()) {
            throw new IllegalStateException("");
        }

        if (this.server == null) {
            return;
        }


        RequestHttpExchange request = new RequestHttpExchange(null, httpExchange);
        ResponseHttpExchange response = new ResponseHttpExchange(null, httpExchange);

        this.server.dispatch(this, httpExchange.getRequestURI(), request, response);
    }
}
