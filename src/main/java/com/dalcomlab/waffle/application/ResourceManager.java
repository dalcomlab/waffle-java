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

import com.dalcomlab.waffle.resource.Resource;
import com.dalcomlab.waffle.resource.ResourceRoot;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ResourceManager extends Manager {

    private ResourceRoot root;
    private String contextPath;

    /**
     * @param context
     */
    public ResourceManager(ApplicationContext context, String contextPath) {
        super(context);
        this.contextPath = contextPath;
        this.root = ResourceRoot.create(contextPath);
        if (root == null) {
            System.out.println("* fail to mount to root : " + this.contextPath);
        } else {
            System.out.println("* success to mount to root : " + this.contextPath);
        }
    }


    /**
     * @return
     */
    public ResourceRoot getResourceRoot() {
        return this.root;
    }


    /**
     * Returns a directory-like listing of all the paths to resources
     * within the web application whose longest sub-path matches the
     * supplied path argument.
     *
     * <p>Paths indicating subdirectory paths end with a <tt>/</tt>.
     *
     * <p>The returned paths are all relative to the root of the web
     * application, or relative to the <tt>/META-INF/resources</tt>
     * directory of a JAR file inside the web application's
     * <tt>/WEB-INF/lib</tt> directory, and have a leading <tt>/</tt>.
     *
     * <p>The returned set is not backed by the {@code ServletContext} object,
     * so changes in the returned set are not reflected in the
     * {@code ServletContext} object, and vice-versa.</p>
     *
     * <p>For example, for a web application containing:
     *
     * <pre>{@code
     *   /welcome.html
     *   /catalog/index.html
     *   /catalog/products.html
     *   /catalog/offers/books.html
     *   /catalog/offers/music.html
     *   /customer/login.jsp
     *   /WEB-INF/web.xml
     *   /WEB-INF/classes/com.acme.OrderServlet.class
     *   /WEB-INF/lib/catalog.jar!/META-INF/resources/catalog/moreOffers/books.html
     * }</pre>
     *
     * <tt>getResourcePaths("/")</tt> would return
     * <tt>{"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}</tt>,
     * and <tt>getResourcePaths("/catalog/")</tt> would return
     * <tt>{"/catalog/index.html", "/catalog/products.html",
     * "/catalog/offers/", "/catalog/moreOffers/"}</tt>.
     *
     * @param path the partial path used to match the resources,
     *             which must start with a <tt>/</tt>
     * @return a Set containing the directory listing, or null if there
     * are no resources in the web application whose path
     * begins with the supplied path.
     * @since Servlet 2.3
     */
//    public Set<String> getResourcePaths(String path) {
//        if (root != null) {
//            Resource[] resources = root.getResources(path);
//            if (resources != null) {
//                Set<String> paths = new HashSet<>();
//                for (Resource resource : resources) {
//                    if (resource.isFile()) {
//                        paths.add(path + resource.getName());
//                    } else {
//                        paths.add(path + resource.getName() + "/");
//                    }
//                }
//                return paths;
//            }
//        }
//        return Collections.emptySet();
//    }

    public Set<String> getResourcePaths(String path) {
        if (root == null) {
            return Collections.emptySet();
        }

        return root.getResourcePaths(path);
    }

    /**
     * Returns a URL to the resource that is mapped to the given path.
     *
     * <p>The path must begin with a <tt>/</tt> and is interpreted
     * as relative to the current context root,
     * or relative to the <tt>/META-INF/resources</tt> directory
     * of a JAR file inside the web application's <tt>/WEB-INF/lib</tt>
     * directory.
     * This method will first search the document root of the
     * web application for the requested resource, before searching
     * any of the JAR files inside <tt>/WEB-INF/lib</tt>.
     * The order in which the JAR files inside <tt>/WEB-INF/lib</tt>
     * are searched is undefined.
     *
     * <p>This method allows the servlet container to make a resource
     * available to servlets from any source. Resources
     * can be located on a local or remote
     * file system, in a database, or in a <code>.war</code> file.
     *
     * <p>The servlet container must implement the URL handlers
     * and <code>URLConnection</code> objects that are necessary
     * to access the resource.
     *
     * <p>This method returns <code>null</code>
     * if no resource is mapped to the pathname.
     *
     * <p>Some containers may allow writing to the URL returned by
     * this method using the methods of the URL class.
     *
     * <p>The resource content is returned directly, so be aware that
     * requesting a <code>.jsp</code> page returns the JSP source code.
     * Use a <code>RequestDispatcher</code> instead to include results of
     * an execution.
     *
     * <p>This method has a different purpose than
     * <code>java.lang.Class.getResource</code>,
     * which looks up resources based on a class loader. This
     * method does not use class loaders.
     *
     * @param path a <code>String</code> specifying
     *             the path to the resource
     * @return the resource located at the named path,
     * or <code>null</code> if there is no resource at that path
     * @throws MalformedURLException if the pathname is not given in
     *                               the correct form
     */
    public URL getResource(String path) throws MalformedURLException {
        Resource resource = root.getResource(path);
        if (resource != null) {
            return resource.getURL();
        }

        return null;
    }


    /**
     * Returns the resource located at the named path as
     * an <code>InputStream</code> object.
     *
     * <p>The data in the <code>InputStream</code> can be
     * of any type or length. The path must be specified according
     * to the rules given in <code>getResource</code>.
     * This method returns <code>null</code> if no resource exists at
     * the specified path.
     *
     * <p>Meta-information such as content length and content type
     * that is available via <code>getResource</code>
     * method is lost when using this method.
     *
     * <p>The servlet container must implement the URL handlers
     * and <code>URLConnection</code> objects necessary to access
     * the resource.
     *
     * <p>This method is different from
     * <code>java.lang.Class.getResourceAsStream</code>,
     * which uses a class loader. This method allows servlet containers
     * to make a resource available
     * to a servlet from any location, without using a class loader.
     *
     * @param path a <code>String</code> specifying the path
     *             to the resource
     * @return the <code>InputStream</code> returned to the
     * servlet, or <code>null</code> if no resource
     * exists at the specified path
     */
    public InputStream getResourceAsStream(String path) {
        Resource resource = root.getResource(path);
        if (resource != null) {
            return resource.getInputStream();
        }
        return null;
    }


    /**
     * Gets the <i>real</i> path corresponding to the given
     * <i>virtual</i> path.
     *
     * <p>For example, if <tt>path</tt> is equal to <tt>/index.html</tt>,
     * this method will return the absolute file path on the server's
     * filesystem to which a channel of the form
     * <tt>http://&lt;host&gt;:&lt;port&gt;/&lt;contextPath&gt;/index.html</tt>
     * would be mapped, where <tt>&lt;contextPath&gt;</tt> corresponds to the
     * context path of this ServletContext.
     *
     * <p>The real path returned will be in a form
     * appropriate to the computer and operating system on
     * which the servlet container is running, including the
     * proper path separators.
     *
     * <p>Resources inside the <tt>/META-INF/resources</tt>
     * directories of JAR files bundled in the application's
     * <tt>/WEB-INF/lib</tt> directory must be considered only if the
     * container has unpacked them from their containing JAR file, in
     * which case the path to the unpacked location must be returned.
     *
     * <p>This method returns <code>null</code> if the servlet container
     * is unable to translate the given <i>virtual</i> path to a
     * <i>real</i> path.
     *
     * @param path the <i>virtual</i> path to be translated to a
     *             <i>real</i> path
     * @return the <i>real</i> path, or <tt>null</tt> if the
     * translation cannot be performed
     */
    public String getRealPath(String path) {
        Resource resource = root.getResource(path);
        if (resource != null) {
            return resource.getCanonicalPath();
        }
        return null;
    }
}
