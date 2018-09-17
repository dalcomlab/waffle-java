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


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public abstract class AnnotationHandler {

    private Deployment deployment = null;

    /**
     * @param deployment
     * @throws Exception
     */
    public AnnotationHandler(Deployment deployment) throws Exception {
        if (deployment == null) {
            throw new Exception("The deployment must not be a null.");
        }
        this.deployment = deployment;
    }


    /**
     * @return
     */
    public Deployment getDeployment() {
        return this.deployment;
    }


    /**
     * This method will be called when a start tag(e.g., WebServlet) appears in the class.
     *
     * @param parser
     * @param clazz
     */
    public abstract void handle(AnnotationScanner parser, Class<?> clazz);
}
