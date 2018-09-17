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
import com.dalcomlab.waffle.deployment.descriptor.ServletDescriptor;

import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ElementHandlerServlet extends ElementHandler {

    /**
     * @param deployment
     * @param namespace
     * @throws Exception
     */
    public ElementHandlerServlet(Deployment deployment, String namespace) throws Exception {
        super(deployment, namespace);
    }

    /**
     * This method will be called when a close tag(e.g., </servlet>) appears in XML.
     *
     * @param parser
     * @param element
     */
    public void handle(XmlParser parser, XmlElement element) {
        Deployment deployment = this.getDeployment();
        if (deployment == null) {
            return;
        }


        String servletName = element.getString("servlet-name", null);
        String servletClass = element.getString("servlet-class", null);
        String smallIcon = element.getString("small-icon", null);
        String largeIcon = element.getString("large-icon", null);
        String description = element.getString("description", null);
        String displayName = element.getString("display-name", null);
        int loadOnStartup = element.getInt("load-on-startup", 0);
        boolean asyncSupported = element.getBoolean("async-supported", false);


        ServletDescriptor servlet = deployment.createServlet();
        if (servlet == null) {
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

        List<XmlElement> parameters = element.select("init-param");
        for (XmlElement parameter : parameters) {
            String name = parameter.getString("param-name", "");
            String value = parameter.getString("param-value", "");
            servlet.addInitParameter(name, value);
        }

        deployment.addServlet(servlet);
    }

}
