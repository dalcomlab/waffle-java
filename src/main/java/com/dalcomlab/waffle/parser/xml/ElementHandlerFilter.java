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

import com.dalcomlab.waffle.deployment.descriptor.FilterDescriptor;
import com.dalcomlab.waffle.deployment.Deployment;

import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ElementHandlerFilter extends ElementHandler {

    /**
     * @param deployment
     * @param namespace
     * @throws Exception
     */
    public ElementHandlerFilter(Deployment deployment, String namespace) throws Exception {
        super(deployment, namespace);
    }

    /**
     * This method will be called when a close tag(e.g., </servlet>) appears in XML.
     *
     * @param builder
     * @param element
     */
    public void handle(XmlParser builder, XmlElement element) {
        Deployment deployment = this.getDeployment();
        if (deployment == null) {
            return;
        }

        FilterDescriptor filter = deployment.createFilter();

        if (filter == null) {
            return;
        }

        filter.setDescription(element.getString("description", null));
        filter.setDisplayName(element.getString("display-name", null));
        filter.setSmallIcon(element.getString("small-icon", null));
        filter.setLargeIcon(element.getString("large-icon", null));
        filter.setFilterClass(element.getString("filter-class", null));
        filter.setFilterName(element.getString("filter-name", null));
        filter.setAsyncSupported(element.getBoolean("async-supported", false));


        List<XmlElement> parameters = element.select("init-param");
        for (XmlElement parameter : parameters) {
            String name = parameter.getString("param-name", "");
            String value = parameter.getString("param-value", "");
            if (name.length() > 0) {
                filter.addInitParameter(name, value);
            }
        }

        deployment.addFilter(filter);
    }

}
