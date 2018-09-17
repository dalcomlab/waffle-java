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

package com.dalcomlab.waffle.parser;

import com.dalcomlab.waffle.deployment.Deployment;
import com.dalcomlab.waffle.parser.xml.*;

import java.io.InputStream;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class WebXmlParser {

    private static final String SERVLET = "/servlet";
    private static final String FILTER = "/filter";
    private static final String SERVLET_MAPPING = "/servlet-mapping";
    private static final String FILTER_MAPPING = "/filter-mapping";
    private static final String ERROR_PAGE = "/error-page";
    private static final String LISTENER = "/listener";

    private XmlParser parser = new XmlParser();

    /**
     *
     */
    public WebXmlParser(Deployment deployment, String root) {
        //
        // root web-app or web web-fragment;
        //
        try {
            parser.addHandler(new ElementHandlerWebApp(deployment, "/" + root));
            parser.addHandler(new ElementHandlerServlet(deployment, "/" + root + SERVLET));
            parser.addHandler(new ElementHandlerFilter(deployment, "/" + root + FILTER));
            parser.addHandler(new ElementHandlerServletMapping(deployment, "/" + root + SERVLET_MAPPING));
            parser.addHandler(new ElementHandlerFilterMapping(deployment, "/" + root + FILTER_MAPPING));
            parser.addHandler(new ElementHandlerErrorPage(deployment, "/" + root + ERROR_PAGE));
            parser.addHandler(new ElementHandlerListener(deployment, "/" + root + LISTENER));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param path
     */
    public void parse(String path) {
        parser.parse(path);
    }

    /**
     * @param input
     */
    public void parse(InputStream input) {
        parser.parse(input);
    }
}
