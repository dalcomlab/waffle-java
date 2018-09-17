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
package com.dalcomlab.waffle.resource;

import com.dalcomlab.waffle.resource.jar.JarArchive;
import com.dalcomlab.waffle.resource.jar.JarArchiveInputStream;

import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ResourceJarFile extends ResourceJar {

    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar file.
     *
     * @param file  a Jar file
     * @param entry
     */
    public ResourceJarFile(JarFile file, JarEntry entry) {
        super(file, entry);
    }

    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar file.
     *
     * @param jar   a Jar file
     * @param entry
     */
    public ResourceJarFile(JarArchive jar, JarEntry entry) {
        super(jar, entry);
    }


    /**
     * Checks that this resource is a file.
     *
     * @return
     */
    @Override
    public boolean isFile() {
        return true;
    }

    /**
     * Checks that this resource is a directory.
     *
     * @return
     */
    @Override
    public boolean isDirectory() {
        return false;
    }

    /**
     * Gets the URL to access this resource or null
     *
     * @return the URL to access this resource
     */
    public URL getURL() {
        if (jar == null) {
            return null;
        }
        URL url = null;
        try {
            url = new URL("");
        } catch (Exception e) {

        }
        return url;
    }

    /**
     * Gets the input stream of this resource.
     *
     * @return the input stream object
     */
    @Override
    public InputStream getInputStream() {
        InputStream input = null;
        try {
            input = jar.getInputStream(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }

    /**
     * Creates new resource
     *
     * @return
     */
    @Override
    public Resource create() {
        if (entry == null) {
            return null;
        }
        InputStream input = jar.getInputStream(entry);
        if (input == null) {
            return null;
        }
        return new ResourceJarDirectory(input);
    }
}
