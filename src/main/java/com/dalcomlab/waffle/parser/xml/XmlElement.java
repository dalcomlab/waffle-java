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

package com.dalcomlab.waffle.parser.xml;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public final class XmlElement {

    private XmlElement parent;
    private String name;
    private String text;
    private String value;
    private List<XmlElement> elements = new LinkedList<>();

    /**
     * @param name
     */
    public XmlElement(String name) {
        this.parent = null;
        this.name = name;
    }

    /**
     * @param name
     * @param text
     */
    public XmlElement(String name, String text) {
        this(name);
        this.text = text;
    }

    /**
     * @param parent
     * @param name
     */
    public XmlElement(XmlElement parent, String name) {
        this.parent = parent;
        this.name = name;
    }


    /**
     * @param parent
     * @param name
     * @param text
     */
    public XmlElement(XmlElement parent, String name, String text) {
        this(parent, name);
        this.text = text;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public XmlElement getParent() {
        return parent;
    }

    /**
     * @return
     */
    public String getPath() {
        String path = "";
        if (parent != null) {
            path += parent.getPath();
        }
        return path + "/" + name;
    }

    /**
     * @param parent
     * @return
     */
    public String getPath(XmlElement parent) {
        String path = "";
        if (parent != null) {
            path += parent.getPath();
        }
        return path + "/" + name;
    }

    /**
     * @param element
     */
    public void add(XmlElement element) {
        element.parent = this;
        elements.add(element);
    }

    /**
     * @param path
     * @return
     */
    public List<XmlElement> select(String path) {

        String[] namespaces = path.split("/");
        List<XmlElement> elements = new LinkedList<>(this.elements);
        List<XmlElement> matches = new LinkedList<>();

        for (String name : namespaces) {

            // clear matches for the next search.
            matches.clear();

            // skip a namespace with an empty such as '//' or '/servlet//servlet-name'.
            if (name.length() == 0) {
                continue;
            }

            for (XmlElement element : elements) {
                if (element.getName().equals(name)) {
                    matches.add(element);
                }
            }

            // if there are no matches, we don't need to search namespaces any more.
            if (matches.isEmpty()) {
                break;
            }

            elements.clear();

            // copy child elements to elements for the next search.
            for (XmlElement element : matches) {
                elements.addAll(element.elements);
            }

        }
        return matches;
    }

    /**
     * This method will try to convert value of element to a boolean.
     *
     * @return
     */
    public boolean getBoolean() {
        boolean returnValue = false;
        if ("1".equalsIgnoreCase(value) ||
                "yes".equalsIgnoreCase(value) ||
                "true".equalsIgnoreCase(value) ||
                "on".equalsIgnoreCase(value)) {
            returnValue = true;
        }
        return returnValue;
    }

    /**
     * This method will try to convert value of element to a boolean.
     *
     * @return
     */
    public boolean getBoolean(String path, boolean defaultValue) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return defaultValue;
        }
        return matches.get(0).getBoolean();
    }


    /**
     * This method will try to convert value of element to a double.
     *
     * @return
     */
    public double getDouble() {
        double returnValue = 0;
        try {
            returnValue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * This method will try to convert value of element to a double.
     *
     * @return
     */
    public double getDouble(String path, double defaultValue) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return defaultValue;
        }
        return matches.get(0).getDouble();
    }


    /**
     * This method will try to convert value of element to an int.
     *
     * @return
     */
    public int getInt() {
        int returnValue = 0;
        try {
            returnValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * This method will try to convert value of element to an int.
     *
     * @return
     */
    public int getInt(String path, int defaultValue) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return defaultValue;
        }
        return matches.get(0).getInt();
    }

    /**
     * This method will try to convert value of element to a long.
     *
     * @return
     */
    public long getLong() {
        long returnValue = 0;
        try {
            returnValue = Long.parseLong(value);
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * This method will try to convert value of element to a long.
     *
     * @return
     */
    public long getLong(String path, long defaultValue) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return defaultValue;
        }
        return matches.get(0).getLong();
    }

    /**
     * This method will try to convert value of element to a string.
     *
     * @return
     */
    public String getString() {
        return value;
    }


    /**
     * This method will try to convert value of element to a string.
     *
     * @return
     */
    public String getString(String path, String defaultValue) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return defaultValue;
        }
        return matches.get(0).getString();
    }

    /*****/

    /**
     * This method will try to convert value of element to a string.
     *
     * @return
     */
    public boolean[] getArrayBoolean(String path) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return new boolean[0];
        }
        int n = matches.size();
        boolean[] array = new boolean[n];
        for (int i = 0; i < n; i++) {
            array[i] = matches.get(i).getBoolean();
        }

        return array;
    }


    /**
     * This method will try to convert value of element to a double.
     *
     * @return
     */
    public double[] getArrayDouble(String path) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return new double[0];
        }
        int n = matches.size();
        double[] array = new double[n];
        for (int i = 0; i < n; i++) {
            array[i] = matches.get(i).getDouble();
        }

        return array;
    }

    /**
     * This method will try to convert value of element to an int.
     *
     * @return
     */
    public int[] getArrayInt(String path) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return new int[0];
        }
        int n = matches.size();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = matches.get(i).getInt();
        }

        return array;
    }

    /**
     * This method will try to convert value of element to a long.
     *
     * @return
     */
    public long[] getArrayLong(String path) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return new long[0];
        }
        int n = matches.size();
        long[] array = new long[n];
        for (int i = 0; i < n; i++) {
            array[i] = matches.get(i).getLong();
        }

        return array;
    }

    /**
     * This method will try to convert value of element to a string.
     *
     * @return
     */
    public String[] getArrayString(String path) {
        List<XmlElement> matches = select(path);
        if (matches.isEmpty()) {
            return new String[0];
        }
        int n = matches.size();
        String[] array = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = matches.get(i).getString();
        }

        return array;
    }


    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        this.value = text;
    }

    /**
     * @return
     */
    public String getText() {
        return text;
    }
}
