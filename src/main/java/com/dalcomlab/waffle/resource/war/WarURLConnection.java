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
package com.dalcomlab.waffle.resource.war;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.List;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class WarURLConnection extends URLConnection {

    private URLConnection connection;

    protected WarURLConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {
        this.connection.connect();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.getInputStream();
    }

    @Override
    public void addRequestProperty(String key, String value) {
        this.connection.addRequestProperty(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        return this.connection.equals(obj);
    }

    @Override
    public boolean getAllowUserInteraction() {
        return this.connection.getAllowUserInteraction();
    }

    @Override
    public int getConnectTimeout() {
        return this.connection.getConnectTimeout();
    }

    @Override
    public Object getContent() throws IOException {
        return this.connection.getContent();
    }

    @Override
    public Object getContent(Class[] classes) throws IOException {
        return this.connection.getContent(classes);
    }

    @Override
    public String getContentEncoding() {
        return this.connection.getContentEncoding();
    }

    @Override
    public int getContentLength() {
        return this.connection.getContentLength();
    }

    @Override
    public String getContentType() {
        return this.connection.getContentType();
    }

    @Override
    public long getDate() {
        return this.connection.getDate();
    }

    @Override
    public boolean getDefaultUseCaches() {
        return this.connection.getDefaultUseCaches();
    }

    @Override
    public boolean getDoInput() {
        return this.connection.getDoInput();
    }

    @Override
    public boolean getDoOutput() {
        return this.connection.getDoOutput();
    }

    @Override
    public long getExpiration() {
        return this.connection.getExpiration();
    }

    @Override
    public String getHeaderField(int n) {
        return this.connection.getHeaderField(n);
    }

    @Override
    public String getHeaderField(String name) {
        return this.connection.getHeaderField(name);
    }

    @Override
    public long getHeaderFieldDate(String name, long Default) {
        return this.connection.getHeaderFieldDate(name, Default);
    }

    @Override
    public int getHeaderFieldInt(String name, int Default) {
        return this.connection.getHeaderFieldInt(name, Default);
    }

    @Override
    public String getHeaderFieldKey(int n) {
        return this.connection.getHeaderFieldKey(n);
    }

    @Override
    public Map<String, List<String>> getHeaderFields() {
        return this.connection.getHeaderFields();
    }

    @Override
    public long getIfModifiedSince() {
        return this.connection.getIfModifiedSince();
    }

    @Override
    public long getLastModified() {
        return this.connection.getLastModified();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.connection.getOutputStream();
    }

    @Override
    public Permission getPermission() throws IOException {
        return this.connection.getPermission();
    }

    @Override
    public int getReadTimeout() {
        return this.connection.getReadTimeout();
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        return this.connection.getRequestProperties();
    }

    @Override
    public String getRequestProperty(String key) {
        return this.connection.getRequestProperty(key);
    }

    @Override
    public URL getURL() {
        return this.connection.getURL();
    }

    @Override
    public boolean getUseCaches() {
        return this.connection.getUseCaches();
    }

    @Override
    public void setAllowUserInteraction(boolean allowuserinteraction) {
        this.connection.setAllowUserInteraction(allowuserinteraction);
    }

    @Override
    public void setConnectTimeout(int timeout) {
        this.connection.setConnectTimeout(timeout);
    }

    @Override
    public void setDefaultUseCaches(boolean defaultusecaches) {
        this.connection.setDefaultUseCaches(defaultusecaches);
    }

    @Override
    public void setDoInput(boolean doinput) {
        this.connection.setDoInput(doinput);
    }

    @Override
    public void setDoOutput(boolean dooutput) {
        this.connection.setDoOutput(dooutput);
    }

    @Override
    public void setIfModifiedSince(long ifmodifiedsince) {
        this.connection.setIfModifiedSince(ifmodifiedsince);
    }

    @Override
    public void setReadTimeout(int timeout) {
        this.connection.setReadTimeout(timeout);
    }

    @Override
    public void setRequestProperty(String key, String value) {
        this.connection.setRequestProperty(key, value);
    }

    @Override
    public void setUseCaches(boolean usecaches) {
        this.connection.setUseCaches(usecaches);
    }
}
