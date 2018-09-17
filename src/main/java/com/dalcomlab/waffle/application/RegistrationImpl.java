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

import javax.servlet.Registration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class RegistrationImpl implements Registration.Dynamic {

    protected String name = null;
    protected String className = null;
    protected boolean isAsyncSupported = false;
    protected Map<String, String> initParameters = null;

    /**
     * Gets the name of the Servlet or Filter that is represented by this
     * Registration.
     *
     * @return the name of the Servlet or Filter that is represented by this
     * Registration
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the fully qualified class name of the Servlet or Filter that
     * is represented by this Registration.
     *
     * @return the fully qualified class name of the Servlet or Filter
     * that is represented by this Registration, or null if this
     * Registration is preliminary
     */
    @Override
    public String getClassName() {
        return this.className;
    }

    /**
     * Sets the initialization parameter with the given name and value
     * on the Servlet or Filter that is represented by this Registration.
     *
     * @param name  the initialization parameter name
     * @param value the initialization parameter value
     * @return true if the update was successful, i.e., an initialization
     * parameter with the given name did not already exist for the Servlet
     * or Filter represented by this Registration, and false otherwise
     * @throws IllegalStateException    if the ServletContext from which this
     *                                  Registration was obtained has already been initialized
     * @throws IllegalArgumentException if the given name or value is
     *                                  <tt>null</tt>
     */
    @Override
    public boolean setInitParameter(String name, String value) {
        if (name == null || value == null) {
            throw new IllegalArgumentException();
        }

        if (this.initParameters == null) {
            this.initParameters = new HashMap<>();
        }
        this.initParameters.put(name, value);
        return true;
    }

    /**
     * Gets the value of the initialization parameter with the given name
     * that will be used to initialize the Servlet or Filter represented
     * by this Registration object.
     *
     * @param name the name of the initialization parameter whose value is
     *             requested
     * @return the value of the initialization parameter with the given
     * name, or <tt>null</tt> if no initialization parameter with the given
     * name exists
     */
    @Override
    public String getInitParameter(String name) {
        if (initParameters != null) {
            return initParameters.get(name);
        }
        return null;
    }

    /**
     * Sets the given initialization parameters on the Servlet or Filter
     * that is represented by this Registration.
     *
     * <p>The given map of initialization parameters is processed
     * <i>by-value</i>, i.e., for each initialization parameter contained
     * in the map, this method calls {@link #setInitParameter(String, String)}.
     * If that method would return false for any of the
     * initialization parameters in the given map, no updates will be
     * performed, and false will be returned. Likewise, if the map contains
     * an initialization parameter with a <tt>null</tt> name or value, no
     * updates will be performed, and an IllegalArgumentException will be
     * thrown.
     *
     * <p>The returned set is not backed by the {@code Registration} object,
     * so changes in the returned set are not reflected in the
     * {@code Registration} object, and vice-versa.</p>
     *
     * @param initParameters the initialization parameters
     * @return the (possibly empty) Set of initialization parameter names
     * that are in conflict
     * @throws IllegalStateException    if the ServletContext from which this
     *                                  Registration was obtained has already been initialized
     * @throws IllegalArgumentException if the given map contains an
     *                                  initialization parameter with a <tt>null</tt> name or value
     */
    @Override
    public Set<String> setInitParameters(Map<String, String> initParameters) {
        if (this.initParameters != null) {
            throw new IllegalStateException();
        }

        if (initParameters == null) {
            throw new IllegalArgumentException();
        }

        this.initParameters = new HashMap<>();
        for (String name : initParameters.keySet()) {
            if (name == null) {
                throw new IllegalArgumentException();
            }

            String value = initParameters.get(name);

            if (value == null) {
                throw new IllegalArgumentException();
            }
            this.initParameters.put(name, value);
        }

        return this.initParameters.keySet();
    }

    /**
     * Gets an immutable (and possibly empty) Map containing the
     * currently available initialization parameters that will be used to
     * initialize the Servlet or Filter represented by this Registration
     * object.
     *
     * @return Map containing the currently available initialization
     * parameters that will be used to initialize the Servlet or Filter
     * represented by this Registration object
     */
    @Override
    public Map<String, String> getInitParameters() {
        return Collections.unmodifiableMap(this.initParameters);
    }


    /**
     * Configures the Servlet or Filter represented by this dynamic
     * Registration as supporting asynchronous operations or not.
     *
     * <p>By default, servlet and filters do not support asynchronous
     * operations.
     *
     * <p>A call to this method overrides any previous setting.
     *
     * @param isAsyncSupported true if the Servlet or Filter represented
     *                         by this dynamic Registration supports asynchronous operations,
     *                         false otherwise
     * @throws IllegalStateException if the ServletContext from which
     *                               this dynamic Registration was obtained has already been
     *                               initialized
     */
    @Override
    public void setAsyncSupported(boolean isAsyncSupported) {
        this.isAsyncSupported = isAsyncSupported;
    }

}
