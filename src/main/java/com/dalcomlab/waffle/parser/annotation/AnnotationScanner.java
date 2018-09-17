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

package com.dalcomlab.waffle.parser.annotation;

import com.dalcomlab.waffle.deployment.Deployment;
import com.dalcomlab.waffle.scanner.ClassFileScanner;
import com.dalcomlab.waffle.scanner.ClassFileVisitor;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class AnnotationScanner implements ClassFileVisitor {

    private List<AnnotationHandler> handlers = new LinkedList<>();
    private ClassLoader classLoader = null;

    /**
     *
     */
    public AnnotationScanner(Deployment applicationDescriptor) {
        try {
            addHandler(new AnnotationHandlerFilter(applicationDescriptor));
            addHandler(new AnnotationHandlerServlet(applicationDescriptor));
            addHandler(new AnnotationHandlerListener(applicationDescriptor));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param handler
     * @return
     */
    public boolean addHandler(AnnotationHandler handler) {
        if (handler == null) {
            return false;
        }
        handlers.add(handler);
        return true;
    }


    /**
     * @param path
     */
    public void scan(final String path) {
        if (path == null || path.length() == 0) {
            return;
        }
        this.classLoader = Thread.currentThread().getContextClassLoader();
        ClassFileScanner scanner = new ClassFileScanner();
        URL[] paths = ((URLClassLoader)this.classLoader).getURLs();
        scanner.scan(paths, this);
    }


    // implements
    @Override
    public boolean accept(URL classPath, String className) {
        try {
            final Class<?> clazz = classLoader.loadClass(className);
            if (clazz != null) {
                handlers.forEach((handler) -> {
                    handler.handle(this, clazz);
                });
            }

        } catch (ClassNotFoundException | NoClassDefFoundError | ClassFormatError e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return true; // continue
    }

}
