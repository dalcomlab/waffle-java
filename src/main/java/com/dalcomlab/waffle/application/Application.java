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

import com.dalcomlab.waffle.Lifecycle;
import com.dalcomlab.waffle.application.configure.ConfigureWeb;
import com.dalcomlab.waffle.dispatch.Dispatch;
import com.dalcomlab.waffle.dispatch.HttpRequest;
import com.dalcomlab.waffle.dispatch.RequestAdaptorDebug;
import com.dalcomlab.waffle.dispatch.ResponseHttpExchange;
import com.dalcomlab.waffle.resource.Resource;
import com.dalcomlab.waffle.resource.ResourceRoot;
import org.apache.jasper.servlet.JasperInitializer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Application extends Lifecycle {

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private String contextName;
    private String contextPath;
    private ApplicationContext context = null;
    private URLClassLoader classloader;
    private JasperInitializer jasperInitialize = new JasperInitializer();

    /**
     * Creates new instance
     *
     * @param contextName
     * @param contextPath
     */
    public Application(String contextName, String contextPath) {
        this.contextName = contextName;
        this.contextPath = contextPath;
        //this.context = new ApplicationContextDebug(new ApplicationContext(contextName, contextPath));
        this.context = new ApplicationContext(contextName, contextPath);

    }

    /**
     * Gets the name of this application.
     *
     * @return the name of this application
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * Gets the full path of this application.
     *
     * @return the full path of this application
     */
    public String getContextPath() {
        return this.contextPath;
    }

    /**
     * Gets the context instance of this application.
     *
     * @return the context instance of this application.
     */
    public ApplicationContext getContext() {
        return context;
    }


    /**
     * @throws Exception
     */
    public void init() {
        super.init();
        initClassLoader();
        Thread.currentThread().setContextClassLoader(this.classloader);

        ApplicationConfig config = new ApplicationConfig();
        ConfigureWeb configure = new ConfigureWeb();

        configure.configure(context.getResourceManager().getResourceRoot(), config, null);
        System.out.println("* scan web.xml");

        config.deploy(this.context);

        try {
            jasperInitialize.onStartup(null, this.context);

            this.context.initialize();
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------------");
    }

    public void initClassLoader() {
        List<URL> urls = new LinkedList<>();
        try {
            urls.add(new URL("file:" + this.contextPath + "/WEB-INF/classes/"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResourceRoot root = context.getResourceManager().getResourceRoot();
        Resource[] files = root.getFiles("/WEB-INF/lib");
        if (files != null) {
            for (Resource file : files) {
                String canonicalPath = file.getCanonicalPath();
                if (canonicalPath.endsWith(".jar") || canonicalPath.endsWith(".zip")) {
                    URL url = file.getURL();
                    if (url != null) {
                        urls.add(url);
                    }

                }
            }
        }

        try {
            this.classloader = new URLClassLoader(urls.stream().toArray(URL[]::new), Thread.currentThread().getContextClassLoader());
            this.context.setClassLoader(this.classloader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    public void start() {
        super.start();
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    public void service(String path, ServletRequest request, ServletResponse response) throws Exception {

        System.out.println("*************************************");
        System.out.println("service : " + path);
        System.out.println("*************************************");

        ResourceRoot root = getContext().getResourceManager().getResourceRoot();

        String resourcePath = path;
        if (resourcePath.indexOf('?') > 0) {
            resourcePath = resourcePath.substring(0, resourcePath.indexOf('?'));
        }

        if (root != null && !resourcePath.endsWith(".php") && !resourcePath.endsWith(".jsp")) {
            resourcePath = resourcePath.replaceFirst("/" + contextName + "/", "");
            Resource resource = root.getResource(resourcePath);
            if (resource != null) {
                System.out.println("the file found");
                print(resource, response);
            } else {
                System.out.println("the file not found");
            }
        }

        //RequestHttpExchange httpRequest = (RequestHttpExchange) request;
        ResponseHttpExchange httpResponse = (ResponseHttpExchange) response;


        httpResponse.setContentType("text/html");


        // Dispatch dispatch = this.context.dispatch(path, new RequestAdaptorDebug((HttpServletRequest)request), response);
        Dispatch dispatch = this.context.dispatch(path, request, response);
        if (dispatch != null) {
            HttpRequest baseRequest = HttpRequest.getRequest(request);
            baseRequest.setRequestURI(dispatch.getRequestURI());
            baseRequest.setContextPath(dispatch.getContextPath());
            baseRequest.setServletPath(dispatch.getServletPath());
            baseRequest.setPathInfo(dispatch.getPathInfo());
            baseRequest.setQueryString(dispatch.getQueryString());

            dispatch.dispatch();
        }

    }


    void print(Resource resource, ServletResponse response) {

        InputStream input = resource.getInputStream();
        if (input == null) {
            return;
        }

        String contentType = context.getMimeType(resource.getName());
        if (contentType != null) {
            response.setContentType(contentType);
        }

        try {
            response.setContentLength((int) resource.getContentLength());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            byte[] buffer = new byte[4096 * 2];
            int bytesRead = -1;
            while ((bytesRead = input.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            input.close();
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    public void stop() {
        super.stop();
        //Thread.currentThread().setContextClassLoader(oldClassloader);
    }
}
