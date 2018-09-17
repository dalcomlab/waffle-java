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
public class MultipartConfigDescriptor implements Descriptor {

    private String location;
    private String maxFileSize;
    private String maxRequestSize;
    private String fileSizeThreshold;

    /**
     *
     */
    public MultipartConfigDescriptor() {

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

    /**
     *
     * @return
     */
    public String getLocation() {
        if (this.location == null) {
            return "";
        }
        return this.location;
    }

    /**
     *
     * @param location
     */
    public void setLocationo(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getMaxFileSize() {
        if (this.maxRequestSize == null) {
            return "";
        }

        return this.maxFileSize;
    }

    /**
     *
     * @param maxFileSize
     */
    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    /**
     *
     * @return
     */
    public String getMaxRequestSize() {
        if (this.maxRequestSize == null) {
            return "";
        }

        return this.maxRequestSize;
    }

    /**
     *
     * @param maxRequestSize
     */
    public void setMaxRequestSize(String maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    /**
     *
     * @return
     */
    public String getMaxFileSizeThreshold() {
        if (this.fileSizeThreshold == null) {
            return "";
        }

        return this.fileSizeThreshold;
    }

    /**
     *
     * @param fileSizeThreshold
     */
    public void setMaxFileSizeTheshold(String fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

}
