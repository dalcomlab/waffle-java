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
package com.dalcomlab.waffle.common;


/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Strings {

    /**
     *
     * @param string
     * @return
     */
    public static boolean isNullOrEmpty(String string) {
        return (string == null || string.length() == 0);
    }

    /**
     *
     * @param str
     * @param remove
     * @return
     */
    public static String removeEnd(final String str, final String remove) {
        if (str == null || remove == null) {
            return str;
        }
        if (str.endsWith(remove)) {
            return str.substring(0, str.length() - remove.length());
        }
        return str;
    }

    /**
     *
     * @param str
     * @param suffix
     * @return
     */
    public static boolean endsWithIgnoreCase(final String str, final String suffix) {
        if (str == null || suffix == null) {
            return false;
        }
        return str.toLowerCase().endsWith(suffix.toLowerCase());
    }
}
