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

import com.dalcomlab.waffle.deployment.Deployment;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class AnnotationHandlerListener extends AnnotationHandler {

    /**
     * @param appDescriptor
     * @throws Exception
     */
    public AnnotationHandlerListener(Deployment appDescriptor) throws Exception {
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


        if (!ServletContextListener.class.isAssignableFrom(clazz) &&
                !ServletContextAttributeListener.class.isAssignableFrom(clazz) &&
                !HttpSessionListener.class.isAssignableFrom(clazz) &&
                !HttpSessionAttributeListener.class.isAssignableFrom(clazz) &&
                !HttpSessionIdListener.class.isAssignableFrom(clazz) &&
                !HttpSessionActivationListener.class.isAssignableFrom(clazz) &&
                !HttpSessionBindingListener.class.isAssignableFrom(clazz) &&
                !ServletRequestListener.class.isAssignableFrom(clazz) &&
                !ServletRequestAttributeListener.class.isAssignableFrom(clazz) &&
                !AsyncListener.class.isAssignableFrom(clazz)
                ) {
            return;
        }

    }

}
