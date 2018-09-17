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

package com.dalcomlab.waffle;

import com.dalcomlab.waffle.connector.ConnectorHttp;

import java.util.Properties;
import java.util.Set;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class Waffle {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void printStartupLog() {
        System.out.println("");
        System.out.println("");
        System.out.println(ANSI_GREEN);
        System.out.println("                   __  __ _             ");
        System.out.println("                  / _|/ _| |            ");
        System.out.println("   __      ____ _| |_| |_| | ___        ");
        System.out.println("   \\ \\ /\\ / / _` |  _|  _| |/ _ \\       ");
        System.out.println("    \\ V  V / (_| | | | | | |  __/       ");
        System.out.println("     \\_/\\_/ \\__,_|_| |_| |_|\\___|       ");
        System.out.println("                                        ");
        System.out.println("*** Welcome to the waffle WAS server 1.0.0.0 ***");
        System.out.println(ANSI_RESET);

        System.out.println(ANSI_YELLOW);
        Properties prop = System.getProperties();
        Set<Object> keySet = prop.keySet();
        for (Object obj : keySet) {
            String name = obj.toString();
            String value = System.getProperty(name);
            System.out.println("* " + name + " : " + value);
        }


        System.out.println(ANSI_RESET);
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }

    public static void main(String[] args) {
        printStartupLog();
        WasServer server = new WasServer();
        server.addConnector(new ConnectorHttp(8080));
        server.init();
        server.start();
    }
}
