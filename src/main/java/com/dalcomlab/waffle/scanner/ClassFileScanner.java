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
package com.dalcomlab.waffle.scanner;

import com.dalcomlab.waffle.common.Strings;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ClassFileScanner {

    /**
     * @param classPath
     * @return
     */
    public Set<String> scan(final String classPath) {
        return scan(toURL(classPath));
    }

    /**
     * @param classPath
     * @return
     */
    public Set<String> scan(final URL classPath) {
        if (classPath == null) {
            return Collections.emptySet();
        }
        Set<String> collect = new HashSet<>();
        scan(classPath, (clazzPath, className) -> {
            collect.add(className);
            return true;
        });
        return collect;
    }

    /**
     * @param classPaths
     * @return
     */
    public Set<String> scan(final String[] classPaths) {
        if (classPaths == null || classPaths.length == 0) {
            return Collections.emptySet();
        }

        Set<String> collect = new HashSet<>();
        for (String classPath : classPaths) {
            collect.addAll(this.scan(classPath));
        }
        return collect;
    }

    /**
     * @param classPaths
     * @return
     */
    public Set<String> scan(final URL[] classPaths) {
        if (classPaths == null || classPaths.length == 0) {
            return Collections.emptySet();
        }

        Set<String> collect = new HashSet<>();
        for (URL classPath : classPaths) {
            collect.addAll(scan(classPath));
        }
        return collect;
    }

    /**
     * @param classPath
     */
    public void scan(final String classPath, ClassFileVisitor visitor) {
        scan(toURL(classPath), visitor);
    }

    /**
     * @param classPath
     */
    public void scan(final URL classPath, ClassFileVisitor visitor) {
        if (classPath == null || visitor == null) {
            return;
        }

        String path = classPath.getPath();
        if (Strings.endsWithIgnoreCase(path, ".jar")) {
            scanJar(classPath, visitor);
        } else {
            scanDir(classPath, visitor);
        }
    }

    /**
     * @param classPaths
     */
    public void scan(final URL[] classPaths, ClassFileVisitor visitor) {
        for (URL classPath : classPaths) {
            scan(classPath, visitor);
        }
    }

    /**
     * @param classPath
     */
    public void scanDir(final URL classPath, ClassFileVisitor visitor) {
        if (classPath == null || visitor == null) {
            return;
        }

        File file = new File(classPath.getPath());
        if (!file.exists()) {
            return;
        }
        scanDir(classPath, file, "", visitor);
    }

    /**
     * @param directory
     * @param packageName
     */
    public void scanDir(final URL classPath, final File directory, final String packageName, ClassFileVisitor visitor) {
        if (directory == null || !directory.exists() || visitor == null) {
            return;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (file.isDirectory()) {
                // recursive call
                if (packageName == null || packageName.length() == 0) {
                    this.scanDir(classPath, file, name, visitor);
                } else {
                    this.scanDir(classPath, file, packageName + "." + name, visitor);
                }
            } else if (Strings.endsWithIgnoreCase(name, ".class")) {
                String className = Strings.removeEnd(name, ".class");
                if (!packageName.isEmpty()) {
                    className = packageName + '.' + className;
                }
                // stop the scan if the acceptClass returns false
                if (!visitor.accept(classPath, className)) {
                    break;
                }
            }
        }
    }

    /**
     * @param classPath
     */
    public void scanJar(final URL classPath, ClassFileVisitor visitor) {
        if (classPath == null || visitor == null) {
            return;
        }

        String jarPath = classPath.getPath();

        try (JarFile jar = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (Strings.endsWithIgnoreCase(name, ".class")) {
                    name = name.replace("/", ".");
                    name = Strings.removeEnd(name, ".class"); // remove .class

                    // stops the scan if the acceptClass returns false;
                    if (visitor.accept(classPath, name)) {
                        break;
                    }
                }
            }
        } catch (IOException e) {

        }
    }

    /**
     * @param path
     * @return
     */
    private URL toURL(final String path) {
        URL url = null;
        try {
            url = new URL("file:" + path);
        } catch (Exception e) {
            // ignore
        }
        return url;
    }
}
