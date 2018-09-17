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

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ErrorPage {

    private String exceptionType;
    private String errorCode;
    private String location;

    /**
     * @param exceptionType
     * @param errorCode
     * @param location
     */
    public ErrorPage(String exceptionType, String errorCode, String location) {
        this.exceptionType = exceptionType;
        this.errorCode = errorCode;
        this.location = location;
    }

    /**
     * @return
     */
    public String getExceptionType() {
        return this.exceptionType;
    }

    /**
     * @return
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * @return
     */
    public String getLocation() {
        return this.location;
    }
}
