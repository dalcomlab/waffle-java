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

package com.dalcomlab.waffle.parser.xml;

import com.dalcomlab.waffle.deployment.Deployment;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public abstract class ElementHandler {

    private Deployment deployment;
    private String namespace;

    /**
     *
     * @param namespace
     */
    public ElementHandler(Deployment deployment, String namespace) throws Exception {
        if (namespace == null || namespace.length() == 0) {
            throw new Exception("The namespace can't be a null or empty.");
        }

        if (namespace.charAt(0) != '/') {
            throw new Exception("The namespace must start with a '/'");
        }

        this.deployment = deployment;
        this.namespace = namespace;
    }

    /**
     *
     * @return
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     *
     * @return
     */
    public Deployment getDeployment() {
        return this.deployment;
    }


    /**
     *
     *  This method will be called when a close tag(e.g., </servlet>) appears in XML.
     *
     *
     * @param parser
     * @param element
     */
    public abstract void handle(XmlParser parser, XmlElement element);
}