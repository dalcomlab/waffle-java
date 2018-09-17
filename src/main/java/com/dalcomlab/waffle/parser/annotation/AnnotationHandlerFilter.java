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

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.dalcomlab.waffle.deployment.descriptor.FilterDescriptor;
import com.dalcomlab.waffle.deployment.descriptor.FilterMappingDescriptor;
import com.dalcomlab.waffle.deployment.Deployment;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class AnnotationHandlerFilter extends AnnotationHandler {

    /**
     * @param appDescriptor
     * @throws Exception
     */
    public AnnotationHandlerFilter(Deployment appDescriptor) throws Exception {
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


        if (!Filter.class.isAssignableFrom(clazz)) {
            return;
        }


        //
        // The @WebFilter annotation has the following attributes
        // - filterName
        // - description
        // - displayName
        // - initParams
        // - servletNames
        // - value
        // - urlPatterns
        // - dispatcherTypes
        // - asyncSupported
        //
        // http://blog.caucho.com/2009/10/06/servlet-30-tutorial-weblistener-webservlet-webfilter-and-webinitparam/
        //


        WebFilter annotation = clazz.getAnnotation(WebFilter.class);

        if (annotation == null) {
            return;
        }

        FilterDescriptor filter = appDescriptor.createFilter();


        String filterName = annotation.filterName();
        String filterClass = clazz.getName();
        String smallIcon = annotation.smallIcon();
        String largeIcon = annotation.largeIcon();
        String description = annotation.description();
        String displayName = annotation.displayName();
        boolean asyncSupported = annotation.asyncSupported();
        String[] values = annotation.value();
        String[] urlPatterns = annotation.urlPatterns();
        DispatcherType[] dispatches = annotation.dispatcherTypes();
        String[] servletNames = annotation.servletNames();
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
        if (values.length > 0 && urlPatterns.length > 0) {
            return;
        }


        filter.setFilterName(filterName);
        filter.setFilterClass(filterClass);
        filter.setSmallIcon(smallIcon);
        filter.setLargeIcon(largeIcon);
        filter.setDescription(description);
        filter.setDisplayName(displayName);
        filter.setAsyncSupported(asyncSupported);

        for (WebInitParam initParam : initParams) {
            String name = initParam.name();
            String value = initParam.value();
            filter.addInitParameter(name, value);
        }

        appDescriptor.addFilter(filter);

        FilterMappingDescriptor mapping = appDescriptor.createFilterMapping();

        if (mapping == null) {
            return;
        }

        mapping.setFilterName(filterName);
        if (values.length > 0) {
            mapping.addUrlPattern(values);
        } else if (urlPatterns.length > 0) {
            mapping.addUrlPattern(urlPatterns);
        }

        for (DispatcherType dispatcher : dispatches) {
            mapping.addDispatcher(dispatcher.toString());
        }

        appDescriptor.addFilterMapping(mapping);

    }
}
