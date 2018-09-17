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

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ResourceJarDirectory extends ResourceJar {

    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar file.
     *
     * @param file a Jar filey
     */
    public ResourceJarDirectory(JarFile file) {
        super(file);
    }


    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar file.
     *
     * @param file  a Jar file
     * @param entry
     */
    public ResourceJarDirectory(JarFile file, JarEntry entry) {
        super(file, entry);
    }

    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar file.
     *
     * @param jar a Jar filey
     */
    public ResourceJarDirectory(JarArchive jar) {
        super(jar);
    }


    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar file.
     *
     * @param file  a Jar file
     * @param entry
     */
    public ResourceJarDirectory(JarArchive file, JarEntry entry) {
        super(file, entry);
    }


    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar input stream.
     *
     * @param input a Jar input stream
     */
    public ResourceJarDirectory(InputStream input) {
        super(input);
    }

    /**
     * Creates a new <code>ResourceJar</code> instance with a given jar input stream.
     *
     * @param input a Jar input stream
     * @param entry
     */
    public ResourceJarDirectory(InputStream input, JarEntry entry) {
        super(input, entry);
    }

    /**
     * Checks that this resource is a file.
     *
     * @return
     */
    @Override
    public boolean isFile() {
        return false;
    }

    /**
     * Checks that this resource is a directory.
     *
     * @return
     */
    @Override
    public boolean isDirectory() {
        return true;
    }


    /**
     * Lists all child resources of this resource.
     *
     * @return
     */
    @Override
    public Resource[] list() {
        if (entry == null) {
            return null;
        }
        return list(entry.getName());
    }


    /**
     * Lists all child resources of this resource.
     *
     * @return
     */
    @Override
    public Resource[] list(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        JarEntry[] entries = listEntries(path);
        if (entries == null || entries.length == 0) {
            return null;
        }

        Resource[] resources = new Resource[entries.length];

        int i = 0;
        for (JarEntry entry : entries) {
            Resource resource;
            if (entry.isDirectory()) {
                resource = new ResourceJarDirectory(jar, entry);
            } else {
                resource = new ResourceJarFile(jar, entry);
            }
            resources[i++] = resource;
        }

        return resources;
    }

    /**
     * Returns resource paths in the given paths.
     *
     * @return
     */
    @Override
    public String[] getResourcePaths() {
        String root = "";
        if (entry != null) {
            root = entry.getName();
        }

        JarEntry[] entries = listEntries(root);
        if (entries == null || entries.length == 0) {
            return null;
        }

        String[] paths = new String[entries.length];

        int i = 0;
        for (JarEntry entry : entries) {
            String path = entry.getName();
            paths[i++] = path.substring(root.length()); // remove root path
        }

        return paths;
    }


    /**
     * @param root
     * @return
     */
    public JarEntry[] listEntries(String root) {
        if (root == null) {
            return null;
        }

        //
        // the JarEntry doesn't start with a slash, so remove leading '/' char
        // from the given name.
        //
        if (root.startsWith(File.separator)) {
            root = root.substring(1);
        }

        List<JarEntry> result = new LinkedList<>();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // skip the this entry that does not belong to the given path.
            if (!name.startsWith(root)) {
                continue;
            }

            // skip the entry that have the same name with the given path.
            if (name.equals(root)) {
                continue;
            }

            // remove path from the entry
            name = name.substring(root.length());

            // skip the entry with the depth over one.
            if (name.split("/").length > 1) {
                continue;
            }
            result.add(entry);
        }
        if (result.size() == 0) {
            return null;
        }

        return result.stream().toArray(JarEntry[]::new);
    }

    /**
     * Returns the resource located at the given path.
     *
     * @param path
     * @return
     */
    public Resource getResource(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        Resource resource = null;
        JarEntry entry = jar.getEntry(path);
        if (entry != null) {
            if (entry.isDirectory()) {
                resource = new ResourceJarDirectory(jar, entry);
            } else {
                resource = new ResourceJarFile(jar, entry);
            }
        }

        return resource;
    }

    /**
     * Gets the input stream of this resource.
     *
     * @return the input stream object
     */
    @Override
    public InputStream getInputStream() {
        return null;
    }

    /**
     * Visits to all child resources in this resource.
     *
     * @param visitor
     */
    @Override
    public void accept(ResourceVisitor visitor) {
        if (this.entry != null) {
            acceptDir(visitor);
        } else {
            acceptAll(visitor);
        }
    }

    /**
     * Visits to all child resources in this resource.
     *
     * @param visitor
     */
    public void acceptDir(ResourceVisitor visitor) {
        if (visitor == null) {
            return;
        }

        boolean find = false;
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();

            // find the current directory
            if (find == false && name.equals(this.entry.getName())) {
                find = true;
                continue;
            }

            if (find) {
                if (name.startsWith(this.entry.getName())) {
                    if (entry.isDirectory()) {
                        visitor.visitDirectory(new ResourceJarDirectory(jar, entry));
                    } else {
                        visitor.visitFile(new ResourceJarFile(jar, entry));
                    }
                } else {
                    // no more files in the current directory
                    break;
                }
            }
        }
    }

    /**
     * Visits to all child resources in this resource.
     *
     * @param visitor
     */
    public void acceptAll(ResourceVisitor visitor) {
        if (visitor == null) {
            return;
        }

        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();

            if (entry.isDirectory()) {
                visitor.visitDirectory(new ResourceJarDirectory(jar, entry));
            } else {
                visitor.visitFile(new ResourceJarFile(jar, entry));
            }
        }
    }

}