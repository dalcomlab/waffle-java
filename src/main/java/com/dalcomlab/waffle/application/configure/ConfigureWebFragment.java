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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConfigureWebFragment implements Configure {


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

        Resource[] resources = root.getFiles("META-INF/web-fragment.xml");
        if (resources == null) {
            return true;
        }

        List<ApplicationConfig> fragments = new LinkedList<>();
        Map<String, ApplicationConfig> fragmentsMap = new HashMap<>();
        for (Resource file : resources) {

            InputStream input = file.getInputStream();
            if (input == null) {
                continue;
            }

            ApplicationConfig fragment = new ApplicationConfig();
            WebXmlParser parser = new WebXmlParser(fragment, "web-fragment");
            parser.parse(input);

            String name = fragment.getName();

            if (name != null) {
                // checks that there is a fragment with the same name.
                if (fragmentsMap.containsKey(name)) {
                    return false;
                }
                fragmentsMap.put(name, fragment);
            }

            fragments.add(fragment);
        }

        if (fragments.size() == 0) {
            return true;
        }

        // sorts web fragments by the ordering descriptor.
        //List<ApplicationConfig> fragments = this.topologicalSort(fragmentsMap);

        // merges web fragments into the configure
        for (ApplicationConfig fragment : fragments) {
            config.merge(fragment);
        }

        return true;
    }

    /**
     *
     * @param fragmentsMap
     * @return
     */
    private List<ApplicationConfig> topologicalSort(Map<String, ApplicationConfig> fragmentsMap) {

        return null;
    }
}

