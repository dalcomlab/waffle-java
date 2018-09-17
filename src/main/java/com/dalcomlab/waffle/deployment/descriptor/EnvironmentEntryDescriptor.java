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

package com.dalcomlab.waffle.deployment.descriptor;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class EnvironmentEntryDescriptor implements Descriptor {

    private String name;
    private String type;
    private String value;
    private String mappedName;
    private String lookupName;

    //private Set<InjectionTarget> injectionTargets;
    private static Class[] allowedTypes = {
            int.class,
            boolean.class,
            double.class,
            float.class,
            long.class,
            short.class,
            byte.class,
            char.class,
            java.lang.String.class,
            java.lang.Boolean.class,
            java.lang.Integer.class,
            java.lang.Double.class,
            java.lang.Byte.class,
            java.lang.Short.class,
            java.lang.Long.class,
            java.lang.Float.class,
            java.lang.Character.class,
            java.lang.Class.class
    };


    /**
     *
     */
    public EnvironmentEntryDescriptor() {
        initialize();
    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {

    }

    /**
     *
     * @param source
     * @return
     */
    @Override
    public boolean merge(Descriptor source) {
        return true;
    }


    public boolean checkType(String name) {
         if (name == null) {
            return false;
        }
        Class typeClass = null;
        try {
            typeClass = Class.forName(name, true, Thread.currentThread().getContextClassLoader());
        } catch(Throwable e) {
            e.printStackTrace();
        }

        if (typeClass == null) {
            return false;
        }

        boolean allowed = false;
        if (typeClass.isEnum()) {
            allowed = true;
        } else {
            for (Class type : allowedTypes) {
                if (type.equals(typeClass)) {
                    allowed = true;
                    break;
                }
            }
        }
        return allowed;
    }


    private String convertPrimitiveTypes(String type) {
        if (type == null) {
            return type;
        }

        if (type.equals("int")) {
            return "java.lang.Integer";
        } else if (type.equals("boolean")) {
            return "java.lang.Boolean";
        } else if (type.equals("double")) {
            return "java.lang.Double";
        } else if (type.equals("float")) {
            return "java.lang.Float";
        } else if (type.equals("long")) {
            return "java.lang.Long";
        } else if (type.equals("short")) {
            return "java.lang.Short";
        } else if (type.equals("byte")) {
            return "java.lang.Byte";
        } else if (type.equals("char")) {
            return "java.lang.Character";
        }
        return type;
    }

}
