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
import com.dalcomlab.waffle.resource.Resource;
import com.dalcomlab.waffle.resource.ResourceRoot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConfigureServletContainerInitializer implements Configure {

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

        Resource resource = root.getResource("/WEB-INF/lib/services/");
        if (resource == null || resource.isFile()) {
            return true;
        }

        Resource[] files = resource.list();
        for (Resource file : files) {
            if (file.isDirectory()) {
                continue;
            }
            parseConfigFile(file);
        }

        return true;
    }

    void parseConfigFile(Resource resource) {
        InputStreamReader in = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(in);
        String line;
        try {
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }

                int i = line.indexOf('#');
                if (i >= 0) {
                    line = line.substring(0, i);
                }
                line = line.trim();
                if (line.length() == 0) {
                    continue;
                }
                //servicesFound.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
