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

package com.dalcomlab.waffle.servlets;

import com.dalcomlab.waffle.application.ApplicationContext;
import com.dalcomlab.waffle.resource.Path;
import com.dalcomlab.waffle.resource.Resource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class DefaultServlet implements Servlet {

    private ServletConfig servletConfig = null;
    private ApplicationContext context = null;
    private StringBuilder builder = new StringBuilder();

    /**
     *
     */
    public DefaultServlet() {
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        this.context = (ApplicationContext) servletConfig.getServletContext();
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

//    @Override
//    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//        builder.setLength(0);
//        builder.append("<html>\r\n");
//        builder.append("<head>\r\n");
//        builder.append("<title>Waffle 1.0</title>\r\n");
//        builder.append("<style>\r\n");
//        builder.append("<!--\r\n");
//        builder.append("H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} \r\n");
//        builder.append("H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;}\r\n");
//        builder.append("H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} \r\n");
//        builder.append("BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} \r\n");
//        builder.append("B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} \r\n");
//        builder.append("P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}\r\n");
//        builder.append("A {color : black;}A.name {color : black;}HR {color : #525D76;}\r\n");
//        builder.append("-->\r\n");
//        builder.append("</style>\r\n");
//        builder.append("</head>\r\n");
//        builder.append("<body>\r\n");
//
//        builder.append("<h1>Directory Listing </h1>\r\n");
//        builder.append("<hr size='1' noshade='noshade'>\r\n");
//        builder.append("<table width='100%' cellspacing='0' cellpadding='5' align='center'>\r\n");
//        builder.append("<tr>\r\n");
//        builder.append("<td align='left'><font size='+1'><strong>Filename</strong></font></td>\r\n");
//        builder.append("<td align='center'><font size='+1'><strong>Size</strong></font></td>\r\n");
//        builder.append("<td align='right'><font size='+1'><strong>Last Modified</strong></font></td>\r\n");
//        builder.append("</tr>\r\n");
//
//
//        Set<String> paths = this.context.getResourcePaths(httpRequest.getServletPath() + "/" + httpRequest.getPathInfo());
//        if (paths != null) {
//            for (String path : paths) {
//                builder.append("<tr bgcolor='#eeeeee'>\r\n");
//                builder.append("<td align='left'>&nbsp;&nbsp;\r\n");
//                builder.append("<a href='" + path.substring(1) + "'><tt>" + path + "</tt></a></td>\r\n");
//                builder.append("<td align='right'><tt>0.8 kb</tt></td>\r\n");
//                builder.append("<td align='right'><tt>Sat, 16 Jul 2016 19:39:55 GMT</tt></td>\r\n");
//                builder.append("</tr>\r\n");
//            }
//        }
//
//        builder.append("</table>\r\n");
//        builder.append("<hr size='1' noshade='noshade'>\r\n");
//        builder.append("<h1>Waffle 1.0</h1>\r\n");
//        builder.append("<body>\r\n");
//        builder.append("<html>\r\n");
//
//        out.print(builder.toString());
//        builder.setLength(0);
//        return;
//    }
//

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        builder.setLength(0);
        builder.append("<html>\r\n");
        builder.append("<head>\r\n");
        builder.append("<title>Waffle 1.0</title>\r\n");
        builder.append("<style>\r\n");


        builder.append("table {\r\n");
        builder.append("    display: table;\r\n");
        builder.append("    border-collapse: separate;\r\n");
        builder.append("    border-spacing: 2px;\r\n");
        builder.append("    border-color: grey;\r\n");
        builder.append("}\r\n");

        builder.append("table.files td.icon {\r\n");
        builder.append("    width: 17px;\r\n");
        builder.append("    padding-right: 2px;\r\n");
        builder.append("    padding-left: 10px;\r\n");
        builder.append("    color: rgba(3,47,98,0.55);\r\n");
        builder.append("}\r\n");
        builder.append("table.files td {\r\n");
        builder.append("    padding: 6px 3px;\r\n");
        builder.append("    line-height: 20px;\r\n");
        builder.append("    border-top: 1px solid #eaecef;\r\n");
        builder.append("}\r\n");
        builder.append("td, th {\r\n");
        builder.append("    padding: 0;\r\n");
        builder.append("}\r\n");
        builder.append("\r\n");
        builder.append("td, th {\r\n");
        builder.append("    display: table-cell;\r\n");
        builder.append("    vertical-align: inherit;\r\n");
        builder.append("}\r\n");


        builder.append("svg.waffle {\r\n");
        builder.append("    object-fit: fill;\r\n");
        builder.append("    width: 100%;\r\n");
        builder.append("    height: 100%;\r\n");
        builder.append("    max-width: 100%;\r\n");
        builder.append("    max-height: 100%;\r\n");
        builder.append("}\r\n");

        builder.append("div.waffle {\r\n");
        builder.append("    height: 50px;\r\n");
        builder.append("    width:  50px;\r\n");
        builder.append("}\r\n");


        // a href style
        builder.append("a {\r\n");
        builder.append("  outline: none;\r\n");
        builder.append("  text-decoration: none;\r\n");
        builder.append("  padding: 2px 1px 0;\r\n");
        builder.append("}\r\n");
        builder.append("\r\n");
        builder.append("a:link {\r\n");
        builder.append("  color: #265301;\r\n");
        builder.append("}\r\n");
        builder.append("\r\n");
        builder.append("a:visited {\r\n");
        builder.append("  color: #437A16;\r\n");
        builder.append("}\r\n");
        builder.append("\r\n");
        builder.append("a:focus {\r\n");
        builder.append("  border-bottom: 1px solid;\r\n");
        builder.append("  background: #BAE498;\r\n");
        builder.append("}\r\n");
        builder.append("\r\n");
        builder.append("a:hover {\r\n");
        builder.append("  border-bottom: 1px solid;     \r\n");
        builder.append("  background: #CDFEAA;\r\n");
        builder.append("}\r\n");
        builder.append("\r\n");
        builder.append("a:active {\r\n");
        builder.append("  background: #265301;\r\n");
        builder.append("  color: #CDFEAA;\r\n");
        builder.append("}\r\n");

        builder.append("</style>\r\n");
        builder.append("</head>\r\n");
        builder.append("<body>\r\n");


        builder.append("<h2>Directory Listing </h2>\r\n");
        builder.append("<hr size='1' noshade='noshade'>\r\n");
        builder.append("<table width='100%' class=''>\r\n");
        builder.append("<tr class = ''>\r\n");
        builder.append("<td class = ''>Filename</td>\r\n");
        builder.append("<td class = ''>Size</td>\r\n");
        builder.append("<td class = ''>Last Modified</td>\r\n");
        builder.append("</tr>\r\n");

        String servletPath = httpRequest.getServletPath();
        String pathInfo = httpRequest.getPathInfo();
        String dirPath = servletPath;

        System.out.println("servlet path : " + servletPath);
        System.out.println("path info : "  + pathInfo);

        if (!servletPath.endsWith("/") && !pathInfo.startsWith("/")) {
            pathInfo = "/" + pathInfo;
        }
        if (pathInfo != null) {
            dirPath += pathInfo;
        }

        Set<String> paths = this.context.getResourcePaths(dirPath);
        SortedSet<String> directories = new TreeSet<>();
        SortedSet<String> files = new TreeSet<>();

        if (paths != null) {
            for (String path : paths) {
                String realPath = this.context.getRealPath(path);
                File file = new File(realPath);
                if (file.isDirectory()) {
                    directories.add(path);
                } else {
                    files.add(path);
                }
            }
        }


        for (String path : directories) {
            builder.append("<tr class =''>\r\n");
            builder.append("<td class = ''>");
            builder.append("<svg class='' viewBox='0 0 14 16' version='1.1' width='14' height='16' aria-hidden='true'><path fill-rule='evenodd' d='M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z'></path></svg>");
            builder.append("&nbsp;&nbsp;<a href='" + context.getContextPath() + path + "'>" + path.substring(1) + "</a>");
            builder.append("</td>\r\n");
            builder.append("<td class = ''></td>\r\n");
            String realPath = this.context.getRealPath(path);
            File file = new File(realPath);
            builder.append("<td class = ''>" + new Date(file.lastModified()) + "</td>\r\n");
            builder.append("</tr>\r\n");
        }

        for (String path : files) {
            builder.append("<tr class =''>\r\n");
            builder.append("<td class = ''>");
            builder.append("<svg class='' viewBox='0 0 12 16' version='1.1' width='12' height='16' aria-hidden='true'><path fill-rule='evenodd' d='M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z'></path></svg>");
            builder.append("&nbsp;&nbsp;<a href='" + context.getContextPath() + path + "'>" + path.substring(1) + "</a>");
            builder.append("</td>\r\n");
            String realPath = this.context.getRealPath(path);
            File file = new File(realPath);
            builder.append("<td class = ''>" + file.length() / 1024 + " kb </td>\r\n");
            builder.append("<td class = ''>" + new Date(file.lastModified()) + "</td>\r\n");
            builder.append("</tr>\r\n");
        }



        builder.append("</table>\r\n");
        builder.append("<hr size='1' noshade='noshade'>\r\n");

        // waffle icon
        builder.append("<div class='waffle'>\r\n");
        builder.append("<svg class='waffle' version='1.1' id='Capa_1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' x='0px' y='0px'\r\n");
        builder.append("	  viewBox='0 0 443.69 443.69' xml:space='preserve'>\r\n");
        builder.append("<g>\r\n");
        builder.append("	<path d='M221.845,24.893c-108.6,0-196.953,88.353-196.953,196.952s88.353,196.952,196.953,196.952\r\n");
        builder.append("		c108.6,0,196.952-88.353,196.952-196.952S330.445,24.893,221.845,24.893z M221.845,404.797\r\n");
        builder.append("		c-100.88,0-182.953-82.072-182.953-182.952S120.965,38.893,221.845,38.893c100.88,0,182.952,82.072,182.952,182.952\r\n");
        builder.append("		S322.725,404.797,221.845,404.797z'/>\r\n");
        builder.append("	<path d='M378.714,64.977C336.812,23.076,281.102,0,221.845,0S106.878,23.076,64.977,64.977C23.076,106.878,0,162.588,0,221.845\r\n");
        builder.append("		s23.076,114.968,64.977,156.869c41.901,41.9,97.611,64.977,156.868,64.977s114.967-23.076,156.868-64.977\r\n");
        builder.append("		c41.9-41.901,64.977-97.612,64.977-156.869S420.614,106.878,378.714,64.977z M221.845,429.69C107.239,429.69,14,336.451,14,221.845\r\n");
        builder.append("		S107.239,14,221.845,14S429.69,107.238,429.69,221.845S336.452,429.69,221.845,429.69z'/>\r\n");
        builder.append("	<path d='M216.896,157.502c1.367,1.366,3.158,2.05,4.95,2.05c1.791,0,3.583-0.684,4.95-2.05l37.748-37.748\r\n");
        builder.append("		c2.733-2.734,2.733-7.166,0-9.9l-37.748-37.748c-2.732-2.731-7.166-2.733-9.9,0l-37.749,37.748c-1.313,1.313-2.05,3.094-2.05,4.95\r\n");
        builder.append("		s0.737,3.637,2.05,4.95L216.896,157.502z M221.845,86.955l27.849,27.849l-27.849,27.849l-27.849-27.849L221.845,86.955z'/>\r\n");
        builder.append("	<path d='M232.668,173.274l37.748,37.748c1.367,1.366,3.159,2.05,4.95,2.05s3.583-0.684,4.95-2.05l37.748-37.748\r\n");
        builder.append("		c2.733-2.734,2.733-7.166,0-9.9l-37.748-37.748c-2.734-2.732-7.166-2.732-9.9,0l-37.748,37.748\r\n");
        builder.append("		C229.934,166.108,229.934,170.54,232.668,173.274z M275.366,140.476l27.849,27.849l-27.849,27.849l-27.849-27.849L275.366,140.476z\r\n");
        builder.append("		'/>\r\n");
        builder.append("	<path d='M333.836,179.146c-2.732-2.732-7.165-2.732-9.899,0l-37.748,37.748c-2.733,2.734-2.733,7.166,0,9.899l37.748,37.749\r\n");
        builder.append("		c1.313,1.313,3.094,2.051,4.95,2.051s3.637-0.737,4.95-2.05l37.749-37.749c1.313-1.313,2.05-3.094,2.05-4.95\r\n");
        builder.append("		s-0.737-3.637-2.051-4.95L333.836,179.146z M328.886,249.694l-27.849-27.85l27.849-27.849l27.85,27.849L328.886,249.694z'/>\r\n");
        builder.append("	<path d='M397.44,197.001c-0.369-2.629-2.193-4.823-4.71-5.666c-2.52-0.844-5.295-0.189-7.173,1.688l-3.149,3.15l-27.849-27.849\r\n");
        builder.append("		l22.251-22.25c2.187-2.188,2.679-5.551,1.211-8.273c-3.761-6.971-8.029-13.752-12.688-20.154c-1.204-1.655-3.072-2.7-5.113-2.86\r\n");
        builder.append("		c-2.042-0.163-4.049,0.582-5.497,2.028l-25.837,25.838l-27.849-27.849l25.836-25.835c1.448-1.448,2.189-3.456,2.029-5.497\r\n");
        builder.append("		s-1.205-3.909-2.86-5.114c-6.415-4.666-13.196-8.935-20.155-12.687c-2.722-1.468-6.085-0.976-8.271,1.211l-22.25,22.249\r\n");
        builder.append("		l-27.849-27.849l3.141-3.14c1.877-1.878,2.53-4.655,1.688-7.173s-3.037-4.341-5.666-4.71c-16.538-2.317-33.371-2.298-49.679-0.008\r\n");
        builder.append("		c-2.628,0.368-4.822,2.192-5.665,4.71c-0.842,2.518-0.188,5.294,1.688,7.172l3.149,3.148l-27.849,27.849l-22.246-22.245\r\n");
        builder.append("		c-2.187-2.186-5.551-2.678-8.272-1.211c-6.961,3.754-13.743,8.022-20.154,12.686c-1.655,1.204-2.701,3.073-2.861,5.113\r\n");
        builder.append("		c-0.16,2.041,0.581,4.05,2.029,5.498l25.832,25.832l-27.849,27.849L88.971,116.82c-1.448-1.448-3.463-2.193-5.497-2.029\r\n");
        builder.append("		c-2.041,0.16-3.91,1.206-5.114,2.861c-4.664,6.412-8.932,13.193-12.686,20.153c-1.468,2.723-0.976,6.086,1.211,8.273l22.246,22.245\r\n");
        builder.append("		l-27.849,27.849l-3.149-3.149c-1.877-1.876-4.654-2.532-7.171-1.688c-2.518,0.842-4.341,3.036-4.71,5.665\r\n");
        builder.append("		c-1.148,8.184-1.731,16.542-1.731,24.844c0,8.301,0.583,16.66,1.731,24.845c0.369,2.629,2.193,4.823,4.71,5.665\r\n");
        builder.append("		c2.517,0.844,5.294,0.188,7.171-1.688l3.149-3.149l27.849,27.849l-22.246,22.245c-2.187,2.188-2.68,5.551-1.211,8.273\r\n");
        builder.append("		c3.756,6.965,8.025,13.745,12.686,20.152c1.204,1.656,3.072,2.701,5.113,2.861c2.042,0.166,4.049-0.581,5.497-2.028l25.832-25.832\r\n");
        builder.append("		l27.849,27.849L116.82,354.72c-1.448,1.447-2.189,3.456-2.029,5.497c0.16,2.04,1.206,3.909,2.861,5.113\r\n");
        builder.append("		c6.406,4.659,13.187,8.928,20.154,12.686c2.724,1.469,6.086,0.976,8.273-1.211l22.246-22.246l27.849,27.849l-3.149,3.15\r\n");
        builder.append("		c-1.876,1.877-2.53,4.654-1.688,7.171c0.843,2.518,3.037,4.342,5.665,4.71c8.184,1.149,16.543,1.731,24.845,1.731\r\n");
        builder.append("		c8.243,0,16.599-0.586,24.835-1.74c2.629-0.369,4.822-2.193,5.665-4.711c0.843-2.517,0.189-5.294-1.688-7.172l-3.141-3.14\r\n");
        builder.append("		l27.849-27.849l22.249,22.25c2.188,2.187,5.554,2.681,8.272,1.211c6.954-3.749,13.734-8.018,20.154-12.687\r\n");
        builder.append("		c1.655-1.204,2.701-3.072,2.861-5.113s-0.581-4.05-2.029-5.498l-25.836-25.835l27.849-27.849l25.837,25.838\r\n");
        builder.append("		c1.447,1.446,3.453,2.194,5.497,2.028c2.041-0.16,3.909-1.205,5.114-2.86c4.66-6.407,8.929-13.188,12.687-20.154\r\n");
        builder.append("		c1.468-2.723,0.976-6.086-1.211-8.273l-22.251-22.25l27.849-27.849l3.149,3.15c1.878,1.877,4.653,2.532,7.173,1.688\r\n");
        builder.append("		c2.517-0.843,4.341-3.037,4.71-5.666c1.147-8.187,1.729-16.546,1.729-24.845S398.588,205.188,397.44,197.001z M384.911,231.079\r\n");
        builder.append("		c-2.497-0.956-5.437-0.425-7.453,1.589l-37.748,37.748c-2.733,2.734-2.733,7.166,0,9.9l23.454,23.453\r\n");
        builder.append("		c-1.429,2.457-2.925,4.883-4.481,7.266l-24.846-24.847c-2.734-2.732-7.166-2.732-9.9,0l-37.748,37.748\r\n");
        builder.append("		c-1.313,1.313-2.05,3.094-2.05,4.95s0.737,3.637,2.051,4.95l24.845,24.844c-2.385,1.559-4.811,3.054-7.266,4.481l-23.452-23.453\r\n");
        builder.append("		c-2.734-2.732-7.166-2.732-9.9,0l-37.748,37.748c-2.014,2.015-2.544,4.95-1.59,7.45c-6.18,0.351-12.323,0.347-18.467,0.004\r\n");
        builder.append("		c0.956-2.5,0.426-5.438-1.589-7.454l-37.748-37.748c-2.733-2.732-7.166-2.732-9.899,0l-23.448,23.449\r\n");
        builder.append("		c-2.458-1.429-4.883-2.925-7.266-4.48l24.841-24.841c2.734-2.734,2.734-7.166,0-9.9l-37.748-37.748\r\n");
        builder.append("		c-2.733-2.732-7.166-2.732-9.899,0l-24.841,24.841c-1.557-2.383-3.052-4.809-4.48-7.265l23.448-23.448\r\n");
        builder.append("		c2.734-2.734,2.734-7.166,0-9.9l-37.748-37.748c-2.015-2.014-4.954-2.546-7.453-1.589c-0.172-3.077-0.259-6.16-0.259-9.234\r\n");
        builder.append("		s0.086-6.156,0.259-9.233c2.5,0.956,5.438,0.426,7.453-1.589l37.748-37.748c2.734-2.734,2.734-7.166,0-9.9l-23.448-23.448\r\n");
        builder.append("		c1.428-2.456,2.924-4.882,4.48-7.266l24.841,24.842c1.313,1.313,3.093,2.05,4.95,2.05s3.637-0.737,4.95-2.05l37.748-37.748\r\n");
        builder.append("		c2.734-2.734,2.734-7.166,0-9.899L132.66,85.013c2.384-1.557,4.809-3.053,7.266-4.48l23.449,23.448\r\n");
        builder.append("		c2.733,2.732,7.166,2.732,9.899,0l37.748-37.748c2.015-2.016,2.545-4.953,1.589-7.453c6.144-0.344,12.286-0.346,18.465,0.004\r\n");
        builder.append("		c-0.953,2.499-0.423,5.435,1.591,7.449l37.748,37.748c2.734,2.732,7.167,2.733,9.899,0l23.453-23.452\r\n");
        builder.append("		c2.456,1.428,4.882,2.924,7.266,4.48l-24.845,24.845c-1.313,1.313-2.051,3.094-2.051,4.95s0.737,3.637,2.05,4.95l37.748,37.748\r\n");
        builder.append("		c2.734,2.732,7.166,2.732,9.9,0l24.846-24.847c1.557,2.382,3.053,4.809,4.481,7.266l-23.454,23.453c-2.733,2.734-2.733,7.166,0,9.9\r\n");
        builder.append("		l37.748,37.748c2.017,2.015,4.956,2.544,7.453,1.589c0.172,3.077,0.259,6.159,0.259,9.233S385.083,228.001,384.911,231.079z'/>\r\n");
        builder.append("	<path d='M163.375,211.022c1.367,1.366,3.158,2.05,4.95,2.05s3.583-0.684,4.95-2.05l37.748-37.748c2.734-2.734,2.734-7.166,0-9.9\r\n");
        builder.append("		l-37.748-37.748c-2.733-2.732-7.166-2.732-9.899,0l-37.748,37.748c-2.734,2.734-2.734,7.166,0,9.9L163.375,211.022z\r\n");
        builder.append("		 M168.324,140.476l27.849,27.849l-27.849,27.849l-27.849-27.849L168.324,140.476z'/>\r\n");
        builder.append("	<path d='M216.896,179.146l-37.749,37.748c-1.313,1.313-2.05,3.094-2.05,4.95s0.737,3.637,2.05,4.95l37.749,37.749\r\n");
        builder.append("		c1.313,1.313,3.093,2.05,4.95,2.05s3.637-0.737,4.95-2.051l37.748-37.749c2.733-2.733,2.733-7.165,0-9.899l-37.748-37.748\r\n");
        builder.append("		C224.063,176.415,219.63,176.414,216.896,179.146z M221.845,249.694l-27.849-27.85l27.849-27.849l27.849,27.849L221.845,249.694z'\r\n");
        builder.append("		/>\r\n");
        builder.append("	<path d='M280.316,232.668c-2.734-2.732-7.166-2.732-9.9,0l-37.748,37.748c-2.733,2.734-2.733,7.166,0,9.9l37.748,37.748\r\n");
        builder.append("		c1.367,1.366,3.159,2.05,4.95,2.05s3.583-0.684,4.95-2.05l37.748-37.748c2.733-2.734,2.733-7.166,0-9.9L280.316,232.668z\r\n");
        builder.append("		 M275.366,303.215l-27.849-27.849l27.849-27.849l27.849,27.849L275.366,303.215z'/>\r\n");
        builder.append("	<path d='M157.501,216.895l-37.748-37.748c-2.733-2.732-7.166-2.732-9.899,0l-37.748,37.748c-2.734,2.734-2.734,7.166,0,9.899\r\n");
        builder.append("		l37.748,37.749c1.313,1.313,3.093,2.051,4.95,2.051s3.637-0.737,4.95-2.051l37.748-37.749\r\n");
        builder.append("		C160.235,224.061,160.235,219.629,157.501,216.895z M114.803,249.694l-27.849-27.85l27.849-27.849l27.849,27.849L114.803,249.694z'\r\n");
        builder.append("		/>\r\n");
        builder.append("	<path d='M211.022,270.416l-37.748-37.748c-2.733-2.732-7.166-2.732-9.899,0l-37.748,37.748c-2.734,2.734-2.734,7.166,0,9.9\r\n");
        builder.append("		l37.748,37.748c1.367,1.366,3.158,2.05,4.95,2.05s3.583-0.684,4.95-2.05l37.748-37.748\r\n");
        builder.append("		C213.756,277.582,213.756,273.15,211.022,270.416z M168.324,303.215l-27.849-27.849l27.849-27.849l27.849,27.849L168.324,303.215z'\r\n");
        builder.append("		/>\r\n");
        builder.append("	<path d='M226.796,286.188c-2.732-2.731-7.166-2.732-9.9,0l-37.749,37.748c-1.313,1.313-2.05,3.094-2.05,4.95s0.737,3.637,2.05,4.95 \r\n");
        builder.append("	        l37.749,37.748c1.367,1.366,3.158,2.05,4.95,2.05c1.791,0,3.583-0.684,4.95-2.05l37.748-37.748c2.733-2.734,2.733-7.166,0-9.9\r\n");
        builder.append("	L226.796,286.188z M221.845,356.735l-27.849-27.849l27.849-27.849l27.849,27.849L221.845,356.735z'/>\r\n");
        builder.append("</g>\r\n");
        builder.append("</svg>\r\n");
        builder.append("</div>\r\n");
        builder.append("<h3>Waffle 1.0</h3>\r\n");
        builder.append("<body>\r\n");
        builder.append("<html>\r\n");

        out.print(builder.toString());
        builder.setLength(0);
        return;
    }


    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
