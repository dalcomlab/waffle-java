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
package com.dalcomlab.waffle.application.configure;

import com.dalcomlab.waffle.application.ApplicationConfig;
import com.dalcomlab.waffle.parser.WebXmlParser;
import com.dalcomlab.waffle.resource.Resource;
import com.dalcomlab.waffle.resource.ResourceRoot;

import java.io.InputStream;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConfigureWeb implements Configure {

    /**
     * Configures the application.
     *
     * @param root
     * @param config
     * @param context
     * @return
     */
    @Override
    public boolean configure(ResourceRoot root, ApplicationConfig config, ConfigContext context) {

        Resource resource = root.getFile("WEB-INF/web.xml");
        if (resource == null) {
            return true;
        }

        InputStream input = resource.getInputStream();
        if (input == null) {
            return true;

        }

        WebXmlParser parser = new WebXmlParser(config, "web-app");
        parser.parse(input);

        return true;
    }
}
