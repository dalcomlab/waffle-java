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
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class JarFileScanner {

    /**
     * @param dirPath
     * @return
     */
    public URL[] scan(final String dirPath) {
        return scan(toURL(dirPath));
    }

    /**
     * @param dirPath
     * @return
     */
    public URL[] scan(final URL dirPath) {
        if (dirPath == null) {
            return new URL[0];
        }
        List<URL> collect = new LinkedList<>();
        scan(dirPath, (jarPath, jarName) -> {
            collect.add(toURL(jarName));
            return true;
        });
        return toArray(collect);
    }

    /**
     * @param dirPaths
     * @return
     */
    public URL[] scan(final String[] dirPaths) {
        List<URL> collect = new LinkedList<>();
        for (String dirPath : dirPaths) {
            collect.addAll(Arrays.asList(this.scan(dirPath)));
        }
        return toArray(collect);
    }

    /**
     * @param dirPaths
     * @return
     */
    public URL[] scan(final URL[] dirPaths) {
        List<URL> collect = new LinkedList<>();
        for (URL dirPath : dirPaths) {
            collect.addAll(Arrays.asList(scan(dirPath)));
        }
        return toArray(collect);
    }

    /**
     * @param dirPath
     */
    public void scan(final String dirPath, JarFileVisitor visitor) {
        scan(toURL(dirPath), visitor);
    }

    /**
     * @param dirPath
     */
    public void scan(final URL dirPath, JarFileVisitor visitor) {
        if (dirPath == null || visitor == null) {
            return;
        }

        scanDir(dirPath, visitor);
    }

    /**
     * @param dirPaths
     */
    public void scan(final String[] dirPaths, JarFileVisitor visitor) {
        if (dirPaths == null || dirPaths.length == 0) {
            return;
        }
        for (String dirPath : dirPaths) {
            scan(dirPath, visitor);
        }
    }

    /**
     * @param dirPaths
     */
    public void scan(final URL[] dirPaths, JarFileVisitor visitor) {
        if (dirPaths == null || dirPaths.length == 0) {
            return;
        }

        for (URL dirPath : dirPaths) {
            scan(dirPath, visitor);
        }
    }

    /**
     * @param dirPath
     */
    public void scanDir(final URL dirPath, JarFileVisitor visitor) {
        if (dirPath == null || visitor == null) {
            return;
        }

        File directory = new File(dirPath.getPath());
        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (file.isDirectory()) {
                this.scanDir(toURL(file), visitor); // recursive call
            } else if (this.isJarFile(name)) {
                // stop the scan if the acceptJar returns false
                if (!visitor.acceptJar(toURL(file), name)) {
                    break;
                }
            }
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

    /**
     * @param file
     * @return
     */
    private URL toURL(File file) {
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (Exception e) {
            // ignore
        }
        return url;
    }

    private URL[] toArray(List<URL> collect) {
        return collect.stream().toArray(URL[]::new);
    }

    /**
     * @param name
     * @return
     */
    private boolean isJarFile(String name) {
        return Strings.endsWithIgnoreCase(name, ".jar") || Strings.endsWithIgnoreCase(name, ".zip");
    }
}