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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class ErrorPageManager extends Manager {

    private Map<String, ErrorPage> exceptionErrorPages = new HashMap<>();
    private Map<String, ErrorPage> errorCodeErrorPages = new HashMap<>();

    /**
     *
     */
    public ErrorPageManager(ApplicationContext context) {
        super(context);
    }


    /**
     * @param page
     */
    public void addErrorPage(final ErrorPage page) {
        if (page == null) {
            return;
        }

        String exception = page.getExceptionType();
        String errorCode = page.getErrorCode();
        if (exception != null) {
            this.exceptionErrorPages.put(exception, page);
        }

        if (errorCode != null) {
            this.exceptionErrorPages.put(errorCode, page);
        }
    }

    /**
     * @param request
     * @return
     */
    public ErrorPage getErrorPage(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        // 1) find a error page matching the exception type.
        Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        ErrorPage page = getErrorPage(throwable);
        if (page != null) {
            return page;
        }

        // 2) find a error page matching the error code.
        Integer errorCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        page = getErrorPage(errorCode);

        return page;
    }


    /**
     * @param exception
     * @return
     */
    public ErrorPage getErrorPage(final String exception) {
        if (exception == null || exception.length() == 0) {
            return null;
        }

        return exceptionErrorPages.get(exception);
    }

    /**
     * @param errorCode
     * @return
     */
    public ErrorPage getErrorPage(final int errorCode) {
        return errorCodeErrorPages.get(errorCode);
    }

    /**
     * @param throwable
     * @return
     */
    public ErrorPage getErrorPage(Throwable throwable) {
        ErrorPage page = null;

        while (page == null) {
            Class<?> exceptionClass = throwable.getClass();
            page = this.getErrorPage(exceptionClass.getName());

            // 1) go to the super class until find a error page.
            while (page == null) {
                exceptionClass = exceptionClass.getSuperclass();
                if (exceptionClass == null) {
                    break;
                }
                page = this.getErrorPage(exceptionClass.getName());
            }

            if (page != null) {
                break;
            }

            // 2) go to the root exception until find a error page.
            if (throwable instanceof ServletException) {
                throwable = ((ServletException) throwable).getRootCause();
            } else {
                break;
            }
        }

        return page;
    }

}
