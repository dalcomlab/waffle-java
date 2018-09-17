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
import com.dalcomlab.waffle.parser.annotation.AnnotationScanner;
import com.dalcomlab.waffle.resource.Resource;
import com.dalcomlab.waffle.resource.ResourceRoot;
import com.dalcomlab.waffle.resource.ResourceVisitor;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.InputStream;
import java.util.Set;

import static org.objectweb.asm.Opcodes.ASM5;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ConfigureAnnotation implements Configure {

    /**
     *
     */
    protected static final class ClassInspector extends ClassVisitor {
        private String name;
        private Set<String> annotations;

        /**
         * Creates new instance of the class inspector
         *
         */
        public ClassInspector() {
            super(ASM5);
        }

        /**
         * Visits to the class.
         *
         * @param version
         * @param access
         * @param name
         * @param signature
         * @param superName
         * @param interfaces
         */
        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.name = name.replace('/', '.');
            super.visit(version, access, name, signature, superName, interfaces);
        }

        /**
         * Visits to the annotation of the class.
         *
         * @param desc
         * @param visible
         * @return
         */
        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            annotations.add(desc.replace('/', '.'));
            return super.visitAnnotation(desc, visible);
        }

        /**
         * Gets the name of the class.
         *
         * @return the name of the class
         */
        public String getClassName() {
            return this.name;
        }

        /**
         * Checks that the class has a WebFilter annotation.
         *
         * @return
         */
        public boolean hasWebFilterAnnotation() {
            return hasAnnotation("javax.servlet.annotation.WebFilter");
        }

        /**
         * Checks that the class has a WebServlet annotation.
         *
         * @return
         */
        public boolean hasWebServletAnnotation() {
            return hasAnnotation("javax.servlet.annotation.WebServlet");
        }

        /**
         * Checks that the class has a WebListener annotation.
         *
         * @return
         */
        public boolean hasWebListenerAnnotation() {
            return hasAnnotation("javax.servlet.annotation.WebListener");
        }

        /**
         * Checks that the class has a specified annotation.
         * @param name
         * @return
         */
        public boolean hasAnnotation(String name) {
            return annotations.contains(name);
        }
    }

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

        return true;
    }


    /**
     * Inspects the class
     *
     * @param input
     * @return
     */
    public ClassInspector inspectClass(InputStream input) {
        if (input == null) {
            return null;
        }

        ClassInspector inspector = new ClassInspector();
        try {
            ClassReader reader = new ClassReader(input);
            reader.accept(inspector, 0);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

        return inspector;
    }


    /**
     * Parses the annotation
     *
     * @param resource
     * @param config
     */
    public void parseAnnotation(final Resource resource, final ApplicationConfig config) {
        if (resource == null) {
            return;
        }

        if (resource.isFile()) {
            parseAnnotationFile(resource, config);
        } else {
            parseAnnotationDirectory(resource, config);
        }
    }

    /**
     * Parses the annotation
     *
     * @param resource
     * @param config
     */
    public void parseAnnotationFile(final Resource resource, final ApplicationConfig config) {
        if (resource == null || !resource.isFile()) {
            return;
        }

        InputStream input = resource.getInputStream();
        if (input == null) {
            return;
        }

        ClassInspector inspector = inspectClass(input);
        if (inspector == null) {
            return;
        }

        if (inspector.hasWebFilterAnnotation() ||
                inspector.hasWebServletAnnotation() ||
                inspector.hasWebListenerAnnotation()) {
            AnnotationScanner parser = new AnnotationScanner(config);
            //parser.parse(input);
        }
    }


    /**
     * Parses the annotation
     *
     * @param resource
     * @param config
     */
    public void parseAnnotationDirectory(final Resource resource, final ApplicationConfig config) {
        if (resource == null || !resource.isDirectory()) {
            return;
        }

        resource.accept(new ResourceVisitor() {
            @Override
            public boolean visitFile(Resource resource) {
                if (resource.getName().endsWith(".class")) {
                    parseAnnotationFile(resource, config);
                }
                return true;
            }

            @Override
            public boolean visitDirectory(Resource resource) {
                if (resource.getName().equalsIgnoreCase("META-INF")) {
                    return false;
                }
                return true;
            }
        });
    }
}
