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

import com.dalcomlab.waffle.deployment.descriptor.ErrorPageDescriptor;
import com.dalcomlab.waffle.deployment.Deployment;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ElementHandlerErrorPage extends ElementHandler {
    /**
     *
     * @param deployment
     * @param namespace
     * @throws Exception
     */
    public ElementHandlerErrorPage(Deployment deployment, String namespace) throws Exception {
        super(deployment, namespace);
    }

    /**
     *
     *  This method will be called when a close tag(e.g., </servlet>) appears in XML.
     *
     *
     * @param parser
     * @param element
     */
    public void handle(XmlParser parser, XmlElement element) {
        Deployment deployment = this.getDeployment();
        if (deployment == null) {
            return;
        }

        ErrorPageDescriptor descriptor = new ErrorPageDescriptor();

        descriptor.setErrorCode(element.getInt("error-code", 0));
        descriptor.setExceptionType(element.getString("exception-type", null));
        descriptor.setLocation(element.getString("location", null));

        //deployment.addErrorPage(descriptor);
    }

}