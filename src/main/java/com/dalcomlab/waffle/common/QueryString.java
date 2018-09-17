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


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class QueryString {

    /**
     * @param inputStream
     * @return
     */
    public static Hashtable<String, String[]> parseQueryString(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int totalBytes = 0;
        int read = 0;
        try {
            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, read);
                totalBytes += read;
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The size of parameter : " + totalBytes);
        return parseQueryString(new String(out.toByteArray()));
    }

    /**
     * @param queryString
     * @return
     */
    public static Hashtable<String, String[]> parseQueryString(String queryString) {
        if (queryString == null) {
            return null;
        }
        String values[] = null;
        Hashtable<String, String[]> parameters = new Hashtable<String, String[]>();
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(queryString, "&");
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = parseName(pair.substring(0, pos), sb);
            String val = parseName(pair.substring(pos + 1, pair.length()), sb);
            if (parameters.containsKey(key)) {
                String olds[] = parameters.get(key);
                values = new String[olds.length + 1];
                for (int i = 0; i < olds.length; i++) {
                    values[i] = olds[i];
                }
                values[olds.length] = val;
            } else {
                values = new String[1];
                values[0] = val;
            }
            parameters.put(key, values);
        }

        return parameters;
    }

    /*
     * Parse a name in the query string.
     */
    private static String parseName(String name, StringBuilder sb) {
        sb.setLength(0);
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char) Integer.parseInt(name.substring(i + 1, i + 3), 16));
                        i += 2;
                    } catch (NumberFormatException e) {
                        continue;
                    } catch (StringIndexOutOfBoundsException e) {
                        String rest = name.substring(i);
                        sb.append(rest);
                        if (rest.length() == 2)
                            i++;
                    }

                    break;
                default:
                    sb.append(c);
                    break;
            }
        }

        return sb.toString();
    }

}
