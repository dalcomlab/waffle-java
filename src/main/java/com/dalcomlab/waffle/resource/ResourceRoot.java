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

import java.io.File;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ResourceRoot {
    protected Resource root;
    protected List<Resource> mounts = new LinkedList<>();
    protected CacheManager cache = new CacheManager();

    public ResourceRoot() {

    }

    /**
     * @param root
     */
    protected ResourceRoot(Resource root) {
        this.root = root;
    }

    /**
     * @param path
     * @return
     */
    public static ResourceRoot create(String path) {
        if (path == null || path.length() == 0) {
            return null;
        }

        ResourceRoot root;
        if (path.endsWith(".war")) {
            root = new ResourceRoot(ResourceJar.create(path, null));
        } else {
            root = new ResourceRoot(ResourceFile.create(path));
        }
        root.mountJars();
        return root;
    }

    /**
     * Returns resource paths in the given paths.
     *
     * @param root
     * @return
     */
    public Set<String> getResourcePaths(String root) {
        if (root == null) {
            return Collections.emptySet();
        }

        // the root should be a directory.
        if (!root.endsWith(File.separator)) {
            root += File.separator;
        }

        Resource[] resources = getResources(root);
        if (resources == null) {
            return Collections.emptySet();
        }

        Set<String> result = new HashSet<>();
        for (Resource resource : resources) {
            String[] paths = resource.getResourcePaths();
            if (paths == null) {
                continue;
            }
            for (String path : paths) {
                result.add(root + path);
            }
        }
        return result;
    }

    /**
     * Returns the resource located at the given path.
     *
     * @param path
     * @return
     */
    public Resource getResource(String path) {
        return getResource(new Path(path));
    }


    /**
     * Returns the resource located at the given path.
     *
     * @param path
     * @return
     */
    protected Resource getResource(Path path) {
        Resource resource = null;
        if (root == null) {
            return resource;
        }

        String normalizePath = path.getPath();

        // 0) don't access to WEB-INF directory
        //if (normalizePath.startsWith("WEB-INF")) {
        //    return resource;
        //}

        // 1) find a resource from a cache
        resource = getResourceFromCache(normalizePath);
        if (resource != null) {
            return resource;
        }

        // 2) find a resource from a root
        resource = getResourceFromRoot(normalizePath);
        if (resource == null) {

            // 3) find a resource from a jars
            resource = getResourceFromJars(normalizePath);
        }

        if (resource != null) {
            // 4) put a resource to a cache
            putResourceToCache(normalizePath, resource);
        }
        return resource;
    }

    /**
     * Returns the resource located at the given path.
     *
     * @param path
     * @return
     */
    public Resource[] getResources(String path) {
        return getResources(new Path(path));
    }


    /**
     * Returns the resource located at the given path.
     *
     * @param path
     * @return
     */
    protected Resource[] getResources(Path path) {
        if (root == null) {
            return null;
        }

        List<Resource> list = new LinkedList<>();

        String normalizePath = path.getPath();

        // 0) don't access to WEB-INF directory
        //if (normalizePath.startsWith("WEB-INF")) {
        //    return null;
        //}


        // 1) find a resource from a root
        Resource resource = getResourceFromRoot(normalizePath);
        if (resource != null) {
            list.add(resource);
        }

        // 2) find a resource from a jars
        Resource[] resources = getResourcesFromJars(normalizePath);

        if (resources != null) {
            list.addAll(Arrays.asList(resources));
        }

        return list.stream().toArray(Resource[]::new);
    }


    /**
     * Returns the file located at the given path.
     *
     * @param path
     * @return
     */
    public Resource getFile(String path) {
        return getFile(new Path(path));
    }

    /**
     * Returns the file located at the given path.
     *
     * @param path
     * @return
     */
    protected Resource getFile(Path path) {
        Resource resource = null;
        if (root == null) {
            return resource;
        }

        String normalizePath = path.getPath();

        // 1) find a file from a cache
        resource = getResourceFromCache(normalizePath);
        if (resource != null) {
            return resource;
        }

        // 2) find a file from a root
        resource = getResourceFromRoot(normalizePath);
        if (resource == null) {

            // 3) find a file from a jars
            resource = getFileFromJars(normalizePath);
        }

        if (resource != null) {
            // 4) put a file to a cache
            putResourceToCache(normalizePath, resource);
        }

        return resource;
    }

    /**
     * Returns the file located at the given path.
     *
     * @param path
     * @return
     */
    public Resource[] getFiles(String path) {
        if (root == null) {
            return null;
        }

        List<Resource> resources = new LinkedList<>();

        // 1) find a file from a root
        Resource resource = getResourceFromRoot(path);
        if (resource != null) {
            if (resource.isDirectory()) {
                Resource[] files = resource.list();
                if (files != null) {
                    resources.addAll(Arrays.asList(files));
                }
            }
        }

        // 2) find a file from a jars
        getFileFromJars(resources, path);

        if (resources.size() == 0) {
            return null;
        }

        return resources.stream().toArray(Resource[]::new);
    }


    /**
     * Returns the file located at the given path.
     *
     * @param path
     * @return
     */
    public Resource getClass(String path) {
        Resource resource = null;
        if (root == null) {
            return resource;
        }

        String normalizePath = path;

        // 1) find a class from a cache
        resource = getResourceFromCache(normalizePath);
        if (resource != null) {
            return resource;
        }

        // 2) find a class from a root
        resource = getClassFromRoot(normalizePath);
        if (resource == null) {
            // 3) find a class from a jars
            resource = getClassFromJars(normalizePath);
        }

        if (resource != null) {
            // 4) put a class to a cache
            putResourceToCache(normalizePath, resource);
        }

        return resource;
    }


    /**
     * Gets the resource from a cache.
     *
     * @param path
     * @return
     */
    protected Resource getResourceFromCache(String path) {
        return cache.get(path);
    }

    /**
     * Gets the resource from a root resource.
     *
     * @param path
     * @return
     */
    protected Resource getResourceFromRoot(String path) {
        return root.getResource(path);
    }


    /**
     * Gets the resource from a jar resource.
     *
     * @param path
     * @return
     */
    protected Resource getResourceFromJars(String path) {
        Resource resource;
        for (Resource mount : mounts) {
            resource = mount.getResource("META-INF/resources/" + path);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }


    /**
     * Gets the resource from a jar resource.
     *
     * @param path
     * @return
     */
    protected Resource[] getResourcesFromJars(String path) {
        List<Resource> resources = new LinkedList<>();

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        for (Resource mount : mounts) {
            Resource resource = mount.getResource("META-INF/resources/" + path);
            if (resource != null) {
                resources.add(resource);
            }
        }
        return resources.stream().toArray(Resource[]::new);
    }



    /**
     * Gets the resource from a jar resource.
     *
     * @param path
     * @return
     */
    protected Resource getFileFromJars(String path) {
        Resource resource;
        for (Resource mount : mounts) {
            resource = mount.getResource(path);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }


    /**
     * Gets the resource from a jar resource.
     *
     * @param path
     * @return
     */
    protected Resource[] getFilesFromJars(String path) {
        List<Resource> resources = new LinkedList<>();
        for (Resource mount : mounts) {
            Resource resource = mount.getResource(path);
            if (resource != null) {
                resources.add(resource);
            }
        }
        return resources.stream().toArray(Resource[]::new);
    }

    /**
     * Gets the resource from a jar resource.
     *
     * @param path
     * @return
     */
    protected void getFileFromJars(List<Resource> resources, String path) {
        if (resources == null) {
            return;
        }

        for (Resource mount : mounts) {
            Resource resource = mount.getResource(path);
            if (resource != null) {
                resources.add(resource);
            }
        }
    }

    /**
     * Gets the class from a root resource.
     *
     * @param path
     * @return
     */
    protected Resource getClassFromRoot(String path) {
        return root.getResource("WEB-INF/classes/" + path);
    }

    /**
     * Gets the class from a jar resource.
     *
     * @param path
     * @return
     */
    protected Resource getClassFromJars(String path) {

        // 1) find a class from a root of the jar file.
        Resource resource = getFileFromJars(path);
        if (resource != null) {
            return resource;
        }

        // 2) find a class from META-INF/resources/META-INF/classes in the jar file.
        for (Resource mount : mounts) {
            resource = mount.getResource("META-INF/resources/WEB-INF/classes/" + path);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }

    /**
     * @param path
     * @param resource
     */
    protected void putResourceToCache(String path, Resource resource) {
        if (resource == null) {
            return;
        }
        cache.put(path, resource);
    }

    /**
     *
     */
    private void mountJars() {
        if (root == null) {
            return;
        }

        Resource[] files = root.list("WEB-INF/lib/");
        if (files == null) {
            return;
        }

        for (Resource file : files) {
            if (file.isDirectory()) {
                continue;
            }
            String name = file.getName().toLowerCase(Locale.ENGLISH);
            if (name.endsWith(".jar") || name.endsWith(".zip")) {
                Resource resource = file.create();
                if (resource != null) {
                    mounts.add(resource);
                }
            }
        }
    }


}
