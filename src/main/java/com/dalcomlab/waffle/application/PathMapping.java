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

package com.dalcomlab.waffle.application;

//https://github.com/apache/zookeeper/blob/master/src/java/main/org/apache/zookeeper/common/PathTrie.java

import java.util.HashSet;
import java.util.Set;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class PathMapping {

    /**
     * 1. Path-prefix mappings - Any mapping starting with a "/" and ending with a "/*" is a path-prefix mapping.
     * 2. Extension mappings - Any mapping starting with "*." is an extension mapping.
     * 3. Default mapping - A mapping that contains exactly "/" is the default mapping.
     * 4. Exact mappings - Any other mapping starting with a "/" (but not ending with a "/*") is an exact mapping.
     */
    private Set<String> prefixPatterns = new HashSet<>();
    private Set<String> extensionPatterns = new HashSet<>();
    private Set<String> defaultPatterns = new HashSet<>();
    private Set<String> exactPatterns = new HashSet<>();

    /**
     *
     */
    public PathMapping() {
    }

    /**
     * Adds the given path into this mapping.
     *
     * @param path
     */
    public void addPath(String path) {
        if (path == null || path.length() == 0) {
            return;
        }
        // 1) default mapping
        if (path.equals("/")) {
            defaultPatterns.add(path);
            return;
        }

        // 2) prefix mapping
        if (path.endsWith("*")) {
            prefixPatterns.add(path);
            return;
        }

        // 3) extension mapping
        if (path.startsWith("*.")) {
            extensionPatterns.add(path);
            return;
        }

        // 4) exact mapping
        exactPatterns.add(path);
    }

    /**
     * Gets the all paths in this mapping.
     *
     * @return
     */
    public String[] getPaths() {
        int n = 0;
        n += prefixPatterns.size();
        n += extensionPatterns.size();
        n += defaultPatterns.size();
        n += exactPatterns.size();



//        final int[] i = {0}; // crazy way
//        prefixPatterns.forEach((path) -> {
//            paths[i[0]++] = path;
//        });
//
//        extensionPatterns.forEach((path) -> {
//            paths[i[0]++] = path;
//        });
//
//        defaultPatterns.forEach((path) -> {
//            paths[i[0]++] = path;
//        });
//
//        exactPatterns.forEach((path) -> {
//            paths[i[0]++] = path;
//        });

        String[] paths = new String[n];

        int i = 0;
        for (String path : prefixPatterns) {
            paths[i++] = path;
        }

        for (String path : extensionPatterns) {
            paths[i++] = path;
        }

        for (String path : defaultPatterns) {
            paths[i++] = path;
        }

        for (String path : exactPatterns) {
            paths[i++] = path;
        }


        return paths;
    }

    /**
     * Determines whether the given path matches one of all patterns.
     *
     * @param path
     * @return
     */
    public boolean matches(String path) {
        if (matchesPrefix(path) != null) {
            return true;
        }

        if (matchesExtension(path) != null) {
            return true;
        }

        if (matchesDefault(path) != null) {
            return true;
        }

        if (matchesExact(path) != null) {
            return true;
        }

        return false;
    }

    /**
     * Determines whether the given path matches one of prefix patterns.
     *
     * @param path
     * @return
     */
    public String matchesPrefix(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        path = normalizePath(path);

        for (String pattern : prefixPatterns) {

            // remove the tailing text '/*'
            String prefix = pattern.substring(0, pattern.length() - 2);

            // the path must ending a slash('/') or have same prefix.
            //
            // web.xml
            // <servlet-mapping>
            //    <servlet-name>a</servlet-name>
            //    <url-pattern>/a/*</url-pattern>
            // </servlet-mapping>

            // 1) http://localhsot:8080/a   - true
            // 2) http://localhost:8080/aa  - false
            // 3) http://localhost:8080/a/b - true
            if (path.equals(prefix) || path.startsWith(prefix + "/")) {
                return pattern;
            }
        }
        return null;
    }

    /**
     * Determines whether the given path matches one of extension patterns.
     *
     * @param path
     * @return
     */
    public String matchesExtension(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        path = normalizePath(path);

        for (String pattern : extensionPatterns) {
            String extension = pattern.substring(pattern.lastIndexOf('.'));
            if (path.endsWith(extension)) {
                return pattern;
            }
        }
        return null;
    }


    /**
     * Determines whether the given path matches one of the default pattern.
     *
     * @param path
     * @return
     */
    public String matchesDefault(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        path = normalizePath(path);

        for (String pattern : defaultPatterns) {
            if (path.equals(pattern)) {
                return pattern;
            }
        }
        return null;
    }

    /**
     * Determines whether the given path matches one of exact patterns.
     *
     * @param path
     * @return
     */
    public String matchesExact(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        path = normalizePath(path);

        for (String pattern : exactPatterns) {
            if (path.equals(pattern)) {
                return pattern;
            }
        }
        return null;
    }

    /**
     *
     * @param path
     * @return
     */
    private String normalizePath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return path;
    }
}
