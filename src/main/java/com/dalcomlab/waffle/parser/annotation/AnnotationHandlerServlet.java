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

package com.dalcomlab.waffle.parser.annotation;

import com.dalcomlab.waffle.deployment.descriptor.ServletDescriptor;
import com.dalcomlab.waffle.deployment.descriptor.ServletMappingDescriptor;
import com.dalcomlab.waffle.deployment.Deployment;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class AnnotationHandlerServlet extends AnnotationHandler {

    /**
     * @param appDescriptor
     * @throws Exception
     */
    public AnnotationHandlerServlet(Deployment appDescriptor) throws Exception {
        super(appDescriptor);
    }

    /**
     * This method will be called when a start tag(e.g.,WebServlet) appears in the class.
     *
     * @param parser
     * @param clazz
     */
    public void handle(AnnotationScanner parser, Class<?> clazz) {
        Deployment appDescriptor = this.getDeployment();
        if (appDescriptor == null) {
            return;
        }

        if (clazz == null) {
            return;
        }

        if (!Servlet.class.isAssignableFrom(clazz)) {
            return;
        }


        //
        // @WebServlet annotation is used to register a Servlet with a container. Below is the complete list of
        // attributes that annotation encapsulates.
        //
        // - name
        // - description
        // - value
        // - urlPatterns
        // - initParams
        // - loadOnStartup
        // - asyncSupported
        // - smallIcon
        // - largeIcon
        //
        // http://blog.caucho.com/2009/10/06/servlet-30-tutorial-weblistener-webservlet-webfilter-and-webinitparam/
        //

        //
        // @WebServlet(value="/hello",
        //        initParams = {
        //               @WebInitParam(name="foo", value="Hello "),
        //               @WebInitParam(name="bar", value=" World!")
        //        })
        // public class HelloServlet extends GenericServlet {
        //
        //    public void service(ServletRequest req, ServletResponse res)
        //            throws IOException, ServletException
        //    {
        //        PrintWriter out = res.getWriter();
        //        out.println(getInitParameter("foo"));
        //        out.println(getInitParameter("bar");
        //    }
        // }
        //


        WebServlet annotation = clazz.getAnnotation(WebServlet.class);

        if (annotation == null) {
            return;
        }

        System.out.println("find....servlet......");

        ServletDescriptor servlet = appDescriptor.createServlet();

        if (servlet == null) {
            return;
        }

        String servletName = annotation.name();
        String servletClass = clazz.getName();
        String smallIcon = annotation.smallIcon();
        String largeIcon = annotation.largeIcon();
        String description = annotation.description();
        String displayName = annotation.displayName();
        int loadOnStartup = annotation.loadOnStartup();
        boolean asyncSupported = annotation.asyncSupported();
        String[] values = annotation.value();
        String[] urlPatterns = annotation.urlPatterns();
        WebInitParam[] initParams = annotation.initParams();

        //
        // 8.1.1 @WebServlet
        //
        // This annotation is used to define a Servlet component in a web application. This annotation is
        // specified on a class and contains metadata about the Servlet being declared. The urlPatterns or
        // the value attribute on the annotation MUST be present. All other attributes are optional with
        // default settings. It is recommended to use value when the only attribute on the annotation is
        // the url pattern and to use the urlPatterns attribute when the other attributes are also used.
        //
        // It is illegal to have both value and urlPatterns attribute used together on the same annotation.
        //
        if (values.length > 1 && urlPatterns.length > 1) {
            return;
        }


        servlet.setServletName(servletName);
        servlet.setServletClass(servletClass);
        servlet.setSmallIcon(smallIcon);
        servlet.setLargeIcon(largeIcon);
        servlet.setDescription(description);
        servlet.setDisplayName(displayName);
        servlet.setLoadOnStartup(loadOnStartup);
        servlet.setAsyncSupported(asyncSupported);

        for (WebInitParam initParam : initParams) {
            String name = initParam.name();
            String value = initParam.value();
            servlet.addInitParameter(name, value);
        }

        appDescriptor.addServlet(servlet);

        ServletMappingDescriptor mapping = appDescriptor.createServletMapping();
        if (mapping == null) {
            return;
        }

        mapping.setServletName(servletName);
        if (values.length > 0) {
            mapping.addUrlPattern(values);
        } else if (urlPatterns.length > 0) {
            mapping.addUrlPattern(urlPatterns);
        }

        appDescriptor.addServletMapping(mapping);
    }
}
