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
import com.dalcomlab.waffle.resource.ResourceJar;
import com.dalcomlab.waffle.resource.ResourceRoot;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConfigureAnnotationInJars extends ConfigureAnnotation {

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

        if (config.isMetaDataComplete()) {
            return true;
        }

        Resource[] jars = getJars(root, context);
        for (Resource jar : jars) {
            Resource resource = ResourceJar.create(jar.getCanonicalPath(), "/");
            parseAnnotation(resource, config);
        }
        return true;
    }

    public Resource[] getJars(ResourceRoot root, ConfigContext context) {
        return null;
    }
}
