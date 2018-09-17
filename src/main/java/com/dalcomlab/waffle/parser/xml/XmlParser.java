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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class XmlParser extends DefaultHandler {

    private Stack<XmlElement> stacks = new Stack<>();
    private Map<String, ElementHandler> handlers = new HashMap<>();
    private StringBuilder text = new StringBuilder();

    /**
     *
     */
    public XmlParser() {

    }

    /**
     * @param handler
     * @return
     */
    public boolean addHandler(ElementHandler handler) {
        if (handler == null) {
            return false;
        }
        handlers.put(handler.getNamespace(), handler);
        return true;
    }

    /**
     * @param namespace
     * @return
     */
    public ElementHandler geHandler(String namespace) {
        return handlers.get(namespace);
    }


    /**
     * @param path
     */
    public void parse(String path) {
        SAXParser parser = createSAXParser();
        if (parser == null) {
            return;
        }
        try {
            parser.parse(new File(path), this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param input
     */
    public void parse(InputStream input) {
        SAXParser parser = createSAXParser();
        if (parser == null) {
            return;
        }

        try {
            parser.parse(input, this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private SAXParser createSAXParser() {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser parser = null;
        spf.setNamespaceAware(true);
        spf.setValidating(true);
        try {
            parser = spf.newSAXParser();
        } catch (SAXException e) {
            e.printStackTrace(System.err);
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.err);
        }
        return parser;
    }

    /**
     *
     */
    public void startDocument() {

    }

    /**
     *
     */
    public void endDocument() {

    }

    /**
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        XmlElement element = new XmlElement(qName);
        XmlElement parent = null;
        if (!stacks.empty()) {
            parent = stacks.peek();
        }

        if (parent != null) {
            parent.add(element);
        }

        stacks.push(element);
    }

    /**
     * @param uri
     * @param localName
     * @param qName
     */
    public void endElement(String uri, String localName, String qName) {
        XmlElement element = stacks.peek();
        if (element != null) {
            ElementHandler handler = geHandler(element.getPath());
            if (handler != null) {
                handler.handle(this, element);
            }
            element.setText(text.toString().trim());
        }
        text.setLength(0);
        stacks.pop();
    }

    /**
     * @param ch
     * @param start
     * @param length
     */
    public void characters(char[] ch, int start, int length) {
        text.append(new String(ch, start, length));
    }

    /**
     * @param ch
     * @param start
     * @param length
     */
    public void ignorableWhitespace(char[] ch, int start, int length) {

    }
}