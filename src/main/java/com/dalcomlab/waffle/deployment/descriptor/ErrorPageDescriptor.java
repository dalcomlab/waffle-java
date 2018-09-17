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
public class ErrorPageDescriptor implements Descriptor {

    private int errorCode = 0;
    private String exceptionType = null;
    private String location = null;

    /**
     *
     */
    public ErrorPageDescriptor () {
        initialize();
    }

    // Descriptor implements
    /**
     * Initialize the descriptor for making this descriptor a default.
     */
    @Override
    public void initialize() {
        errorCode = 0;
        exceptionType = null;
        location = null;
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
    public int getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     *
     * @return
     */
    public String getExceptionType() {
        return exceptionType;
    }

    /**
     *
     * @param exceptionType
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }


    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\n");
        sb.append("  error-code : " + this.errorCode).append("\n");
        sb.append("  exception-type : " + this.exceptionType).append("\n");
        sb.append("  location : " + this.location).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
