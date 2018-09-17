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

import com.dalcomlab.waffle.server.Server;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConnectorHttpGrizzly implements Connector {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public Server getServer() {
        return null;
    }

    @Override
    public void setServer(Server server) {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {
        final int port = 8080;
        final int sslPort = 8089;
        final HttpServer httpServer = new HttpServer();
        System.out.println("Preparing to listen for HTTP on port " + port + ".");
        httpServer.addListener(new NetworkListener("HTTP", "0.0.0.0", port));
        SSLEngineConfigurator sslEngineConfigurator = createSslConfiguration();
        if (sslEngineConfigurator != null) {
            System.out.println("Preparing to listen for HTTPS on port " + sslPort + ".");
            System.out.println("The server will use a self-signed certificate not known to browsers.");
            System.out.println("When using HTTPS with curl for example, try --insecure.");
            httpServer.addListener(new NetworkListener("HTTPS", "0.0.0.0", sslPort));
            httpServer.getListener("HTTPS").setSSLEngineConfig(sslEngineConfigurator);
            httpServer.getListener("HTTPS").setSecure(true);
        }
        httpServer.getServerConfiguration().addHttpHandler(new SampleHandler());

        if (false) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    httpServer.shutdownNow();
                }
            }, "shutDownHook"));
        }

        try {
            System.out.println("Starting server...");
            httpServer.start();
            if (true) {
                System.out.println("Press Ctrl+C to stop the server.");
                Thread.currentThread().join();
            }
        } catch (Exception e) {

        }

    }

    private static SSLEngineConfigurator createSslConfiguration() {
        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
        try {
            // Cannot read the key store as a file inside a jar,
            // so get the key store as a byte array.
            sslContextConfigurator.setKeyStoreBytes(getKeyStore());
            sslContextConfigurator.setKeyStorePass("changeit");
            sslContextConfigurator.setKeyPass("changeit");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        if (sslContextConfigurator.validateConfiguration(true)) {
            SSLEngineConfigurator sslEngineConfigurator =
                    new SSLEngineConfigurator(sslContextConfigurator.createSSLContext());
            sslEngineConfigurator.setClientMode(false);
            sslEngineConfigurator.setNeedClientAuth(false);
            sslEngineConfigurator.setWantClientAuth(false);
            return sslEngineConfigurator;
        } else {
            return null;
        }
    }

    /**
     * Returns {@code keystore.jks} as a byte array.
     * @return {@code keystore.jks} as a byte array.
     * @throws IOException  Failed to read {@code keystore.jks}.
     */
    private static byte[] getKeyStore() throws IOException {
        InputStream inputStream = ConnectorHttpGrizzly.class.getResourceAsStream("/keystore.jks");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int read;
        byte[] data = new byte[4096];
        while ((read = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, read);
        }
        return buffer.toByteArray();
    }

    @Override
    public void stop() {

    }

    /**
     * Handler for HTTP GET and HTTP PUT requests.
     */
    static class SampleHandler extends HttpHandler {

        @Override
        public void service(Request request, Response response) throws Exception {

        }
    }
}
