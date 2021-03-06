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

package com.dalcomlab.waffle.loader;


import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ApplicationClassLoader extends URLClassLoader {

    public ApplicationClassLoader() {
        super(new URL[]{}, null);
    }

    public ApplicationClassLoader(ClassLoader parent) {
        super(new URL[]{}, parent);
    }

}
