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
import com.sun.net.httpserver.HttpExchange;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ResponseHttpExchange extends HttpResponse implements ResponseOutputStreamListener {

    private HttpExchange httpExchange;
    private PrintWriter printWriter = null;
    private int statusCode = 200;
    private boolean isCommitted = false;
    private ResponseOutputStream output;

    /**
     *
     */
    public ResponseHttpExchange(ApplicationContext context, HttpExchange httpExchange) {
        super(context);
        this.httpExchange = httpExchange;
        this.output = new ResponseOutputStream(this);
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
        if (printWriter != null) {
            printWriter.flush();
        }
        output.flush();
        try {
            httpExchange.getResponseBody().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getContentType() {
        if (httpExchange != null) {
            return httpExchange.getResponseHeaders().get("Content-type").toString();
        }
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return output;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null) {
            //printWriter = new PrintWriter(output);
            printWriter = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8), true);
        }

        return printWriter;
    }


    @Override
    public void flushBuffer() throws IOException {
        output.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        output.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return isCommitted;
    }

    @Override
    public void reset() {
        output.reset();
    }


    @Override
    public void setStatus(int sc) {
        this.statusCode = sc;
    }

    @Deprecated
    public void setStatus(int sc, String sm) {
        this.statusCode = sc;
    }

    @Override
    public void flush(byte[] buffer, int length) {

        if (http != null) {
            http.fireFlush();
        }

        try {
            if (!isCommitted()) {
                for (String name : headers.keySet()) {
                    List<String> values = headers.get(name);
                    if (values != null) {
                        for (String value : values) {
                            httpExchange.getResponseHeaders().add(name, value);
                        }
                    }
                }
                httpExchange.sendResponseHeaders(statusCode, 0);
            }

            httpExchange.getResponseBody().write(buffer, 0, length);
            httpExchange.getResponseBody().flush();
        } catch (IOException e) {

        }
        isCommitted = true;
    }
}
