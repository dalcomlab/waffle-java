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

package com.dalcomlab.waffle.dispatch;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author ByungChang Yoo (dalcomlab@gmail.com)
 */
public class RequestAdaptor extends HttpRequest {

    private HttpServletRequest request = null;

    /**
     *
     * @param request
     */
    public RequestAdaptor(HttpServletRequest request) {
        super(null);
        this.request = request;
    }

    /**
     * Returns the name of the authentication scheme used to protect
     * the servlet. All servlet containers support basic, form and client
     * certificate authentication, and may additionally support digest
     * authentication.
     * If the servlet is not authenticated <code>null</code> is returned.
     *
     * <p>Same as the value of the CGI variable AUTH_TYPE.
     *
     * @return		one of the static members BASIC_AUTH,
     *			FORM_AUTH, CLIENT_CERT_AUTH, DIGEST_AUTH
     *			(suitable for == comparison) or
     *			the container-specific string indicating
     *			the authentication scheme, or
     *			<code>null</code> if the channel was
     *			not authenticated.
     */
    @Override
    public String getAuthType() {
        return request.getAuthType();
    }

    /**
     * Returns an array containing all of the <code>Cookie</code>
     * objects the client sent with this channel.
     * This method returns <code>null</code> if no cookies were sent.
     *
     * @return		an array of all the <code>Cookies</code>
     *			included with this channel, or <code>null</code>
     *			if the channel has no cookies
     */
    @Override
    public Cookie[] getCookies() {
        return request.getCookies();
    }

    /**
     * Returns the value of the specified channel header
     * as a <code>long</code> value that represents a
     * <code>Date</code> object. Use this method with
     * headers that contain dates, such as
     * <code>If-Modified-Since</code>.
     *
     * <p>The date is returned as
     * the number of milliseconds since January 1, 1970 GMT.
     * The header name is case insensitive.
     *
     * <p>If the channel did not have a header of the
     * specified name, this method returns -1. If the header
     * can't be converted to a date, the method throws
     * an <code>IllegalArgumentException</code>.
     *
     * @param name		a <code>String</code> specifying the
     *				name of the header
     *
     * @return			a <code>long</code> value
     *				representing the date specified
     *				in the header expressed as
     *				the number of milliseconds
     *				since January 1, 1970 GMT,
     *				or -1 if the named header
     *				was not included with the
     *				channel
     *
     * @exception	IllegalArgumentException	If the header value
     *							can't be converted
     *							to a date
     */
    @Override
    public long getDateHeader(String name) {
        return request.getDateHeader(name);
    }


    /**
     * Returns the value of the specified channel header
     * as a <code>String</code>. If the channel did not include a header
     * of the specified name, this method returns <code>null</code>.
     * If there are multiple headers with the same name, this method
     * returns the first head in the channel.
     * The header name is case insensitive. You can use
     * this method with any channel header.
     *
     * @param name		a <code>String</code> specifying the
     *				header name
     *
     * @return			a <code>String</code> containing the
     *				value of the requested
     *				header, or <code>null</code>
     *				if the channel does not
     *				have a header of that name
     */
    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    /**
     * Returns all the values of the specified channel header
     * as an <code>Enumeration</code> of <code>String</code> objects.
     *
     * <p>Some headers, such as <code>Accept-Language</code> can be sent
     * by clients as several headers each with a different value rather than
     * sending the header as a comma separated list.
     *
     * <p>If the channel did not include any headers
     * of the specified name, this method returns an empty
     * <code>Enumeration</code>.
     * The header name is case insensitive. You can use
     * this method with any channel header.
     *
     * @param name		a <code>String</code> specifying the
     *				header name
     *
     * @return			an <code>Enumeration</code> containing
     *                  	the values of the requested header. If
     *                  	the channel does not have any headers of
     *                  	that name return an empty
     *                  	jar. If
     *                  	the container does not allow access to
     *                  	header information, return null
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return request.getHeaders(name);
    }

    /**
     * Returns an jar of all the header names
     * this channel contains. If the channel has no
     * headers, this method returns an empty jar.
     *
     * <p>Some servlet containers do not allow
     * servlets to access headers using this method, in
     * which case this method returns <code>null</code>
     *
     * @return			an jar of all the
     *				header names sent with this
     *				channel; if the channel has
     *				no headers, an empty jar;
     *				if the servlet container does not
     *				allow servlets to use this method,
     *				<code>null</code>
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return request.getHeaderNames();
    }


    /**
     * Returns the value of the specified channel header
     * as an <code>int</code>. If the channel does not have a header
     * of the specified name, this method returns -1. If the
     * header cannot be converted to an integer, this method
     * throws a <code>NumberFormatException</code>.
     *
     * <p>The header name is case insensitive.
     *
     * @param name		a <code>String</code> specifying the name
     *				of a channel header
     *
     * @return			an integer expressing the value
     * 				of the channel header or -1
     *				if the channel doesn't have a
     *				header of this name
     *
     * @exception	NumberFormatException		If the header value
     *							can't be converted
     *							to an <code>int</code>
     */
    @Override
    public int getIntHeader(String name) {
        return request.getIntHeader(name);
    }


    /**
     * Returns the name of the HTTP method with which this
     * channel was made, for example, GET, POST, or PUT.
     * Same as the value of the CGI variable REQUEST_METHOD.
     *
     * @return			a <code>String</code>
     *				specifying the name
     *				of the method with which
     *				this channel was made
     */
    @Override
    public String getMethod() {
        return request.getMethod();
    }


    /**
     * Returns any extra path information associated with
     * the URL the client sent when it made this channel.
     * The extra path information follows the servlet path
     * but precedes the query string and will start with
     * a "/" character.
     *
     * <p>This method returns <code>null</code> if there
     * was no extra path information.
     *
     * <p>Same as the value of the CGI variable PATH_INFO.
     *
     * @return		a <code>String</code>, decoded by the
     *			web container, specifying
     *			extra path information that comes
     *			after the servlet path but before
     *			the query string in the channel URL;
     *			or <code>null</code> if the URL does not have
     *			any extra path information
     */
    @Override
    public String getPathInfo() {
        return request.getPathInfo();
    }

    /**
     * Returns any extra path information after the servlet name
     * but before the query string, and translates it to a real
     * path. Same as the value of the CGI variable PATH_TRANSLATED.
     *
     * <p>If the URL does not have any extra path information,
     * this method returns <code>null</code> or the servlet container
     * cannot translate the virtual path to a real path for any reason
     * (such as when the web application is executed from an archive).
     *
     * The web container does not decode this string.
     *
     * @return		a <code>String</code> specifying the
     *			real path, or <code>null</code> if
     *			the URL does not have any extra path
     *			information
     */
    @Override
    public String getPathTranslated() {
        return request.getPathTranslated();
    }

    /**
     * Returns the portion of the channel URI that indicates the context
     * of the channel. The context path always comes first in a channel
     * URI. The path starts with a "/" character but does not end with a "/"
     * character. For servlets in the default (root) context, this method
     * returns "". The container does not decode this string.
     *
     * <p>It is possible that a servlet container may match a context by
     * more than one context path. In such cases this method will return the
     * actual context path used by the channel and it may differ from the
     * path returned by the
     * {@link ServletContext#getContextPath()} method.
     * The context path returned by
     * {@link ServletContext#getContextPath()}
     * should be considered as the prime or preferred context path of the
     * application.
     *
     * @return		a <code>String</code> specifying the
     *			portion of the channel URI that indicates the context
     *			of the channel
     *
     * @see ServletContext#getContextPath()
     */
    @Override
    public String getContextPath() {
        return request.getContextPath();
    }

    /**
     * Returns the query string that is contained in the channel
     * URL after the path. This method returns <code>null</code>
     * if the URL does not have a query string. Same as the value
     * of the CGI variable QUERY_STRING.
     *
     * @return		a <code>String</code> containing the query
     *			string or <code>null</code> if the URL
     *			contains no query string. The value is not
     *			decoded by the container.
     */
    @Override
    public String getQueryString() {
        return request.getQueryString();
    }

    /**
     * Returns the login of the user making this channel, if the
     * user has been authenticated, or <code>null</code> if the user
     * has not been authenticated.
     * Whether the user name is sent with each subsequent channel
     * depends on the browser and type of authentication. Same as the
     * value of the CGI variable REMOTE_USER.
     *
     * @return		a <code>String</code> specifying the login
     *			of the user making this channel, or <code>null</code>
     *			if the user login is not known
     */
    @Override
    public String getRemoteUser() {
        return request.getRemoteUser();
    }

    /**
     * Returns a boolean indicating whether the authenticated user is included
     * in the specified logical "role".  Roles and role membership can be
     * defined using deployment descriptors.  If the user has not been
     * authenticated, the method returns <code>false</code>.
     *
     * <p>The role name "*" should never be used as an argument in calling
     * <code>isUserInRole</code>. Any call to <code>isUserInRole</code> with
     * "*" must return false.
     * If the role-name of the security-role to be tested is "**", and
     * the application has NOT declared an application security-role with
     * role-name "**", <code>isUserInRole</code> must only return true if
     * the user has been authenticated; that is, only when
     * {@link #getRemoteUser} and {@link #getUserPrincipal} would both return
     * a non-null value. Otherwise, the container must check
     * the user for membership in the application role.
     *
     * @param role		a <code>String</code> specifying the name
     *				of the role
     *
     * @return		a <code>boolean</code> indicating whether
     *			the user making this channel belongs to a given role;
     *			<code>false</code> if the user has not been
     *			authenticated
     */
    @Override
    public boolean isUserInRole(String role) {
        return request.isUserInRole(role);
    }

    /**
     * Returns a <code>java.security.Principal</code> object containing
     * the name of the current authenticated user. If the user has not been
     * authenticated, the method returns <code>null</code>.
     *
     * @return		a <code>java.security.Principal</code> containing
     *			the name of the user making this channel;
     *			<code>null</code> if the user has not been
     *			authenticated
     */
    @Override
    public Principal getUserPrincipal() {
        return request.getUserPrincipal();
    }

    /**
     * Returns the session ID specified by the client. This may
     * not be the same as the ID of the current valid session
     * for this channel.
     * If the client did not specify a session ID, this method returns
     * <code>null</code>.
     *
     * @return		a <code>String</code> specifying the session
     *			ID, or <code>null</code> if the channel did
     *			not specify a session ID
     *
     * @see     #isRequestedSessionIdValid
     */
    @Override
    public String getRequestedSessionId() {
        return request.getRequestedSessionId();
    }

    /**
     * Returns the part of this channel's URL from the protocol
     * name up to the query string in the first line of the HTTP channel.
     * The web container does not decode this String.
     * For example:
     *
     * <table summary="Examples of Returned Values">
     * <tr align=left><th>First line of HTTP channel      </th>
     * <th>     Returned Value</th>
     * <tr><td>POST /some/path.html HTTP/1.1<td><td>/some/path.html
     * <tr><td>GET http://foo.bar/a.html HTTP/1.0
     * <td><td>/a.html
     * <tr><td>HEAD /xyz?a=b HTTP/1.1<td><td>/xyz
     * </table>
     *
     * <p>To reconstruct an URL with a scheme and host, use
     * {@link HttpUtils#getRequestURL}.
     *
     * @return		a <code>String</code> containing
     *			the part of the URL from the
     *			protocol name up to the query string
     *
     * @see     HttpUtils#getRequestURL
     */
    @Override
    public String getRequestURI() {
        return request.getRequestURI();
    }

    /**
     * Reconstructs the URL the client used to make the channel.
     * The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query
     * string parameters.
     *
     * <p>If this channel has been forwarded using
     * {@link RequestDispatcher#forward}, the server path in the
     * reconstructed URL must reflect the path used to obtain the
     * RequestDispatcher, and not the server path specified by the client.
     *
     * <p>Because this method returns a <code>StringBuffer</code>,
     * not a string, you can modify the URL easily, for example,
     * to append query parameters.
     *
     * <p>This method is useful for creating redirect messages
     * and for reporting errors.
     *
     * @return		a <code>StringBuffer</code> object containing
     *			the reconstructed URL
     */
    @Override
    public StringBuffer getRequestURL() {
        return request.getRequestURL();
    }

    /**
     * Returns the part of this channel's URL that calls
     * the servlet. This path starts with a "/" character
     * and includes either the servlet name or a path to
     * the servlet, but does not include any extra path
     * information or a query string. Same as the value of
     * the CGI variable SCRIPT_NAME.
     *
     * <p>This method will return an empty string ("") if the
     * servlet used to handle this channel was matched using
     * the "/*" pattern.
     *
     * @return		a <code>String</code> containing
     *			the name or path of the servlet being
     *			called, as specified in the channel URL,
     *			decoded, or an empty string if the servlet
     *			used to handle the channel is matched
     *			using the "/*" pattern.
     */
    @Override
    public String getServletPath() {
        return request.getServletPath();
    }

    /**
     * Returns the current <code>HttpSession</code>
     * associated with this channel or, if there is no
     * current session and <code>create</code> is true, returns
     * a new session.
     *
     * <p>If <code>create</code> is <code>false</code>
     * and the channel has no valid <code>HttpSession</code>,
     * this method returns <code>null</code>.
     *
     * <p>To make sure the session is properly maintained,
     * you must call this method before
     * the response is committed. If the container is using cookies
     * to maintain session integrity and is asked to create a new session
     * when the response is committed, an IllegalStateException is thrown.
     *
     * @param create	<code>true</code> to create
     *			a new session for this channel if necessary;
     *			<code>false</code> to return <code>null</code>
     *			if there's no current session
     *
     * @return 		the <code>HttpSession</code> associated
     *			with this channel or <code>null</code> if
     * 			<code>create</code> is <code>false</code>
     *			and the channel has no valid session
     *
     * @see #getSession()
     */
    @Override
    public HttpSession getSession(boolean create) {
        return request.getSession(create);
    }

    /**
     * Returns the current session associated with this channel,
     * or if the channel does not have a session, creates one.
     *
     * @return		the <code>HttpSession</code> associated
     *			with this channel
     *
     * @see	#getSession(boolean)
     */
    @Override
    public HttpSession getSession() {
        return request.getSession();
    }

    /**
     * Change the session id of the current session associated with this
     * channel and return the new session id.
     *
     * @return the new session id
     *
     * @throws IllegalStateException if there is no session associated
     * with the channel
     *
     * @since Servlet 3.1
     */
    @Override
    public String changeSessionId() {
        return request.changeSessionId();
    }

    /**
     * Checks whether the requested session ID is still valid.
     *
     * <p>If the client did not specify any session ID, this method returns
     * <code>false</code>.
     *
     * @return			<code>true</code> if this
     *				channel has an id for a valid session
     *				in the current session context;
     *				<code>false</code> otherwise
     *
     * @see			#getRequestedSessionId
     * @see			#getSession
     * @see			HttpSessionContext
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return request.isRequestedSessionIdValid();
    }

    /**
     * <p>Checks whether the requested session ID was conveyed to the
     * server as an HTTP cookie.</p>
     *
     * @return			<code>true</code> if the session ID
     *				was conveyed to the server an an HTTP
     *				cookie; otherwise, <code>false</code>
     *
     * @see         #getSession
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return request.isRequestedSessionIdValid();
    }

    /**
     * <p>Checks whether the requested session ID was conveyed to the
     * server as part of the channel URL.</p>
     *
     * @return <code>true</code> if the session ID was conveyed to the
     *				server as part of a URL; otherwise,
     *				<code>false</code>
     *
     * @see         #getSession
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return request.isRequestedSessionIdFromURL();
    }

    /**
     * @deprecated		As of Version 2.1 of the Java Servlet
     *				API, use {@link #isRequestedSessionIdFromURL}
     *				instead.
     *
     * @return <code>true</code> if the session ID was conveyed to the
     *				server as part of a URL; otherwise,
     *				<code>false</code>
     */
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return request.isRequestedSessionIdFromUrl();
    }

    /**
     * Use the container login mechanism configured for the
     * <code>ServletContext</code> to authenticate the user making
     * this channel.
     *
     * <p>This method may modify and commit the argument
     * <code>HttpServletResponse</code>.
     *
     * @param response The <code>HttpServletResponse</code>
     * associated with this <code>HttpServletRequest</code>
     *
     * @return <code>true</code> when non-null values were or have been
     * established as the values returned by <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code>. Return
     * <code>false</code> if authentication is incomplete and the underlying
     * login mechanism has committed, in the response, the message (e.g.,
     * challenge) and HTTP status code to be returned to the user.
     *
     * @throws IOException if an input or output error occurred while
     * reading from this channel or writing to the given response
     *
     * @throws IllegalStateException if the login mechanism attempted to
     * modify the response and it was already committed
     *
     * @throws ServletException if the authentication failed and
     * the caller is responsible for handling the error (i.e., the
     * underlying login mechanism did NOT establish the message and
     * HTTP status code to be returned to the user)
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return request.authenticate(response);
    }

    /**
     * Validate the provided username and password in the password validation
     * realm used by the web container login mechanism configured for the
     * <code>ServletContext</code>.
     *
     * <p>This method returns without throwing a <code>ServletException</code>
     * when the login mechanism configured for the <code>ServletContext</code>
     * supports username password validation, and when, at the time of the
     * call to login, the identity of the caller of the channel had
     * not been established (i.e, all of <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code> return null),
     * and when validation of the provided credentials is successful.
     * Otherwise, this method throws a <code>ServletException</code> as
     * described below.
     *
     * <p>When this method returns without throwing an exception, it must
     * have established non-null values as the values returned by
     * <code>getUserPrincipal</code>, <code>getRemoteUser</code>, and
     * <code>getAuthType</code>.
     *
     * @param username The <code>String</code> value corresponding to
     * the login identifier of the user.
     *
     * @param password The password <code>String</code> corresponding
     * to the identified user.
     *
     * @exception	ServletException    if the configured login mechanism
     *                                      does not support username
     *                                      password authentication, or if a
     *                                      non-null caller identity had
     *                                      already been established (prior
     *                                      to the call to login), or if
     *                                      validation of the provided
     *                                      username and password fails.
     *
     * @since Servlet 3.0
     */
    @Override
    public void login(String username, String password) throws ServletException {
        request.login(username, password);
    }


    /**
     * Establish <code>null</code> as the value returned when
     * <code>getUserPrincipal</code>, <code>getRemoteUser</code>,
     * and <code>getAuthType</code> is called on the channel.
     *
     * @exception ServletException if logout fails
     *
     * @since Servlet 3.0
     */
    @Override
    public void logout() throws ServletException {
        request.logout();
    }

    /**
     * Gets all the {@link Part} components of this channel, provided
     * that it is of type <code>multipart/form-data</code>.
     *
     * <p>If this channel is of type <code>multipart/form-data</code>, but
     * does not contain any <code>Part</code> components, the returned
     * <code>Collection</code> will be empty.
     *
     * <p>Any changes to the returned <code>Collection</code> must not
     * affect this <code>HttpServletRequest</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the
     * <code>Part</code> components of this channel
     *
     * @throws IOException if an I/O error occurred during the retrieval
     * of the {@link Part} components of this channel
     *
     * @throws ServletException if this channel is not of type
     * <code>multipart/form-data</code>
     *
     * @throws IllegalStateException if the channel body is larger than
     * <code>maxRequestSize</code>, or any <code>Part</code> in the
     * channel is larger than <code>maxFileSize</code>, or there is no
     * <code>@MultipartConfig</code> or <code>multipart-configure</code> in
     * deployment descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return request.getParts();
    }


    /**
     * Gets the {@link Part} with the given name.
     *
     * @param name the name of the requested <code>Part</code>
     *
     * @return The <code>Part</code> with the given name, or
     * <code>null</code> if this channel is of type
     * <code>multipart/form-data</code>, but does not
     * contain the requested <code>Part</code>
     *
     * @throws IOException if an I/O error occurred during the retrieval
     * of the requested <code>Part</code>
     * @throws ServletException if this channel is not of type
     * <code>multipart/form-data</code>
     * @throws IllegalStateException if the channel body is larger than
     * <code>maxRequestSize</code>, or any <code>Part</code> in the
     * channel is larger than <code>maxFileSize</code>, or there is no
     * <code>@MultipartConfig</code> or <code>multipart-configure</code> in
     * deployment descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return request.getPart(name);
    }

    /**
     * Creates an instance of <code>HttpUpgradeHandler</code> for a given
     * class and uses it for the dispatch protocol upgrade processing.
     *
     * @param <T> The {@code Class}, which extends {@link
     * HttpUpgradeHandler}, of the {@code handlerClass}.
     * @param handlerClass The <code>HttpUpgradeHandler</code> class used for the upgrade.
     *
     * @return an instance of the <code>HttpUpgradeHandler</code>
     *
     * @exception IOException if an I/O error occurred during the upgrade
     * @exception ServletException if the given <code>handlerClass</code> fails to
     * be instantiated
     *
     * @see HttpUpgradeHandler
     * @see WebConnection
     *
     * @since Servlet 3.1
     */
    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return request.upgrade(handlerClass);
    }


    /**
     * Returns the value of the named attribute as an <code>Object</code>,
     * or <code>null</code> if no attribute of the given name exists.
     *
     * <p> Attributes can be set two ways.  The servlet container may set
     * attributes to make available custom information about a channel.
     * For example, for requests made using HTTPS, the attribute
     * <code>javax.servlet.channel.X509Certificate</code> can be used to
     * retrieve information on the certificate of the client.  Attributes
     * can also be set programatically using
     * {@link ServletRequest#setAttribute}.  This allows information to be
     * embedded into a channel before a {@link RequestDispatcher} call.
     *
     * <p>Attribute names should follow the same conventions as package
     * names. This specification reserves names matching <code>java.*</code>,
     * <code>javax.*</code>, and <code>sun.*</code>.
     *
     * @param name a <code>String</code> specifying the name of the attribute
     *
     * @return an <code>Object</code> containing the value of the attribute,
     * or <code>null</code> if the attribute does not exist
     */
    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    /**
     * Returns an <code>Enumeration</code> containing the
     * names of the attributes available to this channel.
     * This method returns an empty <code>Enumeration</code>
     * if the channel has no attributes available to it.
     *
     * @return an <code>Enumeration</code> of strings containing the names
     * of the channel's attributes
     */
    @Override
    public Enumeration<String> getAttributeNames() {
        return request.getAttributeNames();
    }


    /**
     * Returns the name of the character encoding used in the body of this
     * channel. This method returns <code>null</code> if no channel encoding
     * character encoding has been specified. The following methods for
     * specifying the channel character encoding are consulted, in decreasing
     * order of priority: per channel, per web app (using
     * {@link ServletContext#setRequestCharacterEncoding}, deployment
     * descriptor), and per container (for all web applications deployed in
     * that container, using vendor specific configuration).
     *
     * @return a <code>String</code> containing the name of the character
     * encoding, or <code>null</code> if the channel does not specify a
     * character encoding
     */
    @Override
    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    /**
     * Overrides the name of the character encoding used in the body of this
     * channel. This method must be called prior to reading channel parameters
     * or reading input using getReader(). Otherwise, it has no effect.
     *
     * @param env <code>String</code> containing the name of
     * the character encoding.
     *
     * @throws UnsupportedEncodingException if this ServletRequest is still
     * in a state where a character encoding may be set,
     * but the specified encoding is invalid
     */
    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        request.setCharacterEncoding(env);
    }

    /**
     * Returns the length, in bytes, of the channel body and made available by
     * the input stream, or -1 if the length is not known. For HTTP servlets,
     * same as the value of the CGI variable CONTENT_LENGTH.
     *
     * @return a long containing the length of the channel body or -1L if
     * the length is not known
     *
     * @since Servlet 3.1
     */
    @Override
    public int getContentLength() {
        return request.getContentLength();
    }

    /**
     * Returns the length, in bytes, of the channel body and made available by
     * the input stream, or -1 if the length is not known. For HTTP servlets,
     * same as the value of the CGI variable CONTENT_LENGTH.
     *
     * @return a long containing the length of the channel body or -1L if
     * the length is not known
     *
     * @since Servlet 3.1
     */
    @Override
    public long getContentLengthLong() {
        return request.getContentLengthLong();
    }

    /**
     * Returns the MIME type of the body of the channel, or
     * <code>null</code> if the type is not known. For HTTP servlets,
     * same as the value of the CGI variable CONTENT_TYPE.
     *
     * @return a <code>String</code> containing the name of the MIME type
     * of the channel, or null if the type is not known
     */
    @Override
    public String getContentType() {
        return request.getContentType();
    }

    /**
     * Retrieves the body of the channel as binary data using
     * a {@link ServletInputStream}.  Either this method or
     * {@link #getReader} may be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing
     * the body of the channel
     *
     * @exception IllegalStateException if the {@link #getReader} method
     * has already been called for this channel
     *
     * @exception IOException if an input or output exception occurred
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    /**
     * Returns the value of a channel parameter as a <code>String</code>,
     * or <code>null</code> if the parameter does not exist. HttpRequest parameters
     * are extra information sent with the channel.  For HTTP servlets,
     * parameters are contained in the query string or posted form data.
     *
     * <p>You should only use this method when you are sure the
     * parameter has only one value. If the parameter might have
     * more than one value, use {@link #getParameterValues}.
     *
     * <p>If you use this method with a multivalued
     * parameter, the value returned is equal to the first value
     * in the array returned by <code>getParameterValues</code>.
     *
     * <p>If the parameter data was sent in the channel body, such as occurs
     * with an HTTP POST channel, then reading the body directly via {@link
     * #getInputStream} or {@link #getReader} can interfere
     * with the execution of this method.
     *
     * @param name a <code>String</code> specifying the name of the parameter
     *
     * @return a <code>String</code> representing the single value of
     * the parameter
     *
     * @see #getParameterValues
     */
    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    /**
     *
     * Returns an <code>Enumeration</code> of <code>String</code>
     * objects containing the names of the parameters contained
     * in this channel. If the channel has
     * no parameters, the method returns an empty <code>Enumeration</code>.
     *
     * @return an <code>Enumeration</code> of <code>String</code>
     * objects, each <code>String</code> containing the name of
     * a channel parameter; or an empty <code>Enumeration</code>
     * if the channel has no parameters
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return request.getParameterNames();
    }

    /**
     * Returns an array of <code>String</code> objects containing
     * all of the values the given channel parameter has, or
     * <code>null</code> if the parameter does not exist.
     *
     * <p>If the parameter has a single value, the array has a length
     * of 1.
     *
     * @param name a <code>String</code> containing the name of
     * the parameter whose value is requested
     *
     * @return an array of <code>String</code> objects
     * containing the parameter's values
     *
     * @see #getParameter
     */
    @Override
    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    /**
     * Returns a java.util.Map of the parameters of this channel.
     *
     * <p>HttpRequest parameters are extra information sent with the channel.
     * For HTTP servlets, parameters are contained in the query string or
     * posted form data.
     *
     * @return an immutable java.util.Map containing parameter names as
     * keys and parameter values as map values. The keys in the parameter
     * map are of type String. The values in the parameter map are of type
     * String array.
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return request.getParameterMap();
    }

    /**
     * Returns the name and version of the protocol the channel uses
     * in the form <i>protocol/majorVersion.minorVersion</i>, for
     * example, HTTP/1.1. For HTTP servlets, the value
     * returned is the same as the value of the CGI variable
     * <code>SERVER_PROTOCOL</code>.
     *
     * @return a <code>String</code> containing the protocol
     * name and version number
     */
    @Override
    public String getProtocol() {
        return request.getProtocol();
    }

    /**
     * Returns the name of the scheme used to make this channel,
     * for example,
     * <code>dispatch</code>, <code>https</code>, or <code>ftp</code>.
     * Different schemes have different rules for constructing URLs,
     * as noted in RFC 1738.
     *
     * @return a <code>String</code> containing the name
     * of the scheme used to make this channel
     */
    @Override
    public String getScheme() {
        return request.getScheme();
    }

    /**
     * Returns the host name of the server to which the channel was sent.
     * It is the value of the part before ":" in the <code>Host</code>
     * header value, if any, or the resolved server name, or the server IP
     * address.
     *
     * @return a <code>String</code> containing the name of the server
     */
    @Override
    public String getServerName() {
        return request.getServerName();
    }

    /**
     * Returns the port number to which the channel was sent.
     * It is the value of the part after ":" in the <code>Host</code>
     * header value, if any, or the server port where the client connection
     * was accepted on.
     *
     * @return an integer specifying the port number
     */
    @Override
    public int getServerPort() {
        return request.getServerPort();
    }

    /**
     * Retrieves the body of the channel as character data using
     * a <code>BufferedReader</code>.  The reader translates the character
     * data according to the character encoding used on the body.
     * Either this method or {@link #getInputStream} may be called to read the
     * body, not both.
     *
     * @return a <code>BufferedReader</code> containing the body of the channel
     *
     * @exception UnsupportedEncodingException  if the character set encoding
     * used is not supported and the text cannot be decoded
     *
     * @exception IllegalStateException if {@link #getInputStream} method
     * has been called on this channel
     *
     * @exception IOException if an input or output exception occurred
     *
     * @see #getInputStream
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }

    /**
     * Returns the Internet Protocol (IP) address of the client
     * or last proxy that sent the channel.
     * For HTTP servlets, same as the value of the
     * CGI variable <code>REMOTE_ADDR</code>.
     *
     * @return a <code>String</code> containing the
     * IP address of the client that sent the channel
     */
    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    /**
     * Returns the fully qualified name of the client
     * or the last proxy that sent the channel.
     * If the engine cannot or chooses not to resolve the hostname
     * (to improve performance), this method returns the dotted-string form of
     * the IP address. For HTTP servlets, same as the value of the CGI variable
     * <code>REMOTE_HOST</code>.
     *
     * @return a <code>String</code> containing the fully
     * qualified name of the client
     */
    @Override
    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    /**
     * Stores an attribute in this channel.
     * Attributes are reset between requests.  This method is most
     * often used in conjunction with {@link RequestDispatcher}.
     *
     * <p>Attribute names should follow the same conventions as
     * package names. Names beginning with <code>java.*</code>,
     * <code>javax.*</code>, and <code>com.sun.*</code>, are
     * reserved for use by Sun Microsystems.
     *<br> If the object passed in is null, the effect is the same as
     * calling {@link #removeAttribute}.
     * <br> It is warned that when the channel is dispatched from the
     * servlet resides in a different web application by
     * <code>RequestDispatcher</code>, the object set by this method
     * may not be correctly retrieved in the caller servlet.
     *
     * @param name a <code>String</code> specifying
     * the name of the attribute
     *
     * @param o the <code>Object</code> to be stored
     *
     */
    @Override
    public void setAttribute(String name, Object o) {
        request.setAttribute(name, o);
    }

    /**
     *
     * Removes an attribute from this channel.  This method is not
     * generally needed as attributes only persist as long as the channel
     * is being handled.
     *
     * <p>Attribute names should follow the same conventions as
     * package names. Names beginning with <code>java.*</code>,
     * <code>javax.*</code>, and <code>com.sun.*</code>, are
     * reserved for use by Sun Microsystems.
     *
     * @param name a <code>String</code> specifying
     * the name of the attribute to remove
     */
    @Override
    public void removeAttribute(String name) {
        request.removeAttribute(name);
    }

    /**
     * Returns the preferred <code>Locale</code> that the client will
     * accept content in, based on the Accept-Language header.
     * If the client channel doesn't provide an Accept-Language header,
     * this method returns the default locale for the server.
     *
     * @return the preferred <code>Locale</code> for the client
     */
    @Override
    public Locale getLocale() {
        return request.getLocale();
    }

    /**
     * Returns an <code>Enumeration</code> of <code>Locale</code> objects
     * indicating, in decreasing order starting with the preferred locale, the
     * locales that are acceptable to the client based on the Accept-Language
     * header.
     * If the client channel doesn't provide an Accept-Language header,
     * this method returns an <code>Enumeration</code> containing one
     * <code>Locale</code>, the default locale for the server.
     *
     * @return an <code>Enumeration</code> of preferred
     * <code>Locale</code> objects for the client
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return request.getLocales();
    }

    /**
     *
     * Returns a boolean indicating whether this channel was made using a
     * secure channel, such as HTTPS.
     *
     * @return a boolean indicating if the channel was made using a
     * secure channel
     */
    @Override
    public boolean isSecure() {
        return request.isSecure();
    }

    /**
     *
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for
     * the resource located at the given path.
     * A <code>RequestDispatcher</code> object can be used to forward
     * a channel to the resource or to include the resource in a response.
     * The resource can be dynamic or static.
     *
     * <p>The pathname specified may be relative, although it cannot extend
     * outside the current servlet context.  If the path begins with
     * a "/" it is interpreted as relative to the current context root.
     * This method returns <code>null</code> if the servlet container
     * cannot return a <code>RequestDispatcher</code>.
     *
     * <p>The difference between this method and {@link
     * ServletContext#getRequestDispatcher} is that this method can take a
     * relative path.
     *
     * @param path a <code>String</code> specifying the pathname
     * to the resource. If it is relative, it must be
     * relative against the current servlet.
     *
     * @return a <code>RequestDispatcher</code> object that acts as a
     * wrapper for the resource at the specified path,
     * or <code>null</code> if the servlet container cannot
     * return a <code>RequestDispatcher</code>
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
     */
    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return request.getRequestDispatcher(path);
    }

    /**
     * @param path the path for which the real path is to be returned.
     * @return the <i>real</i> path, or <tt>null</tt> if the
     * translation cannot be performed.
     * @deprecated  As of Version 2.1 of the Java Servlet API,
     *    use {@link ServletContext#getRealPath} instead.
     */
    public String getRealPath(String path) {
        return request.getRealPath(path);
    }

    /**
     * Returns the Internet Protocol (IP) source port of the client
     * or last proxy that sent the channel.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */
    @Override
    public int getRemotePort() {
        return request.getRemotePort();
    }

    /**
     * Returns the host name of the Internet Protocol (IP) interface on
     * which the channel was received.
     *
     * @return a <code>String</code> containing the host
     *         name of the IP on which the channel was received.
     *
     * @since Servlet 2.4
     */
    @Override
    public String getLocalName() {
        return request.getLocalName();
    }

    /**
     * Returns the Internet Protocol (IP) address of the interface on
     * which the channel  was received.
     *
     * @return a <code>String</code> containing the
     * IP address on which the channel was received.
     *
     * @since Servlet 2.4
     */
    @Override
    public String getLocalAddr() {
        return request.getLocalAddr();
    }

    /**
     * Returns the Internet Protocol (IP) port number of the interface
     * on which the channel was received.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */
    @Override
    public int getLocalPort() {
        return request.getLocalPort();
    }

    /**
     * Gets the servlet context to which this ServletRequest was last
     * dispatched.
     *
     * @return the servlet context to which this ServletRequest was last
     * dispatched
     *
     * @since Servlet 3.0
     */
    @Override
    public ServletContext getServletContext() {
        return request.getServletContext();
    }

    /**
     * Puts this channel into asynchronous mode, and initializes its
     * {@link AsyncContext} with the original (unwrapped) ServletRequest
     * and ServletResponse objects.
     *
     * <p>Calling this method will cause committal of the associated
     * response to be delayed until {@link AsyncContext#complete} is
     * called on the returned {@link AsyncContext}, or the asynchronous
     * operation has timed out.
     *
     * <p>Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on
     * the returned AsyncContext will return <code>true</code>. Any filters
     * invoked in the <i>outbound</i> direction after this channel was put
     * into asynchronous mode may use this as an indication that any channel
     * and/or response wrappers that they added during their <i>inbound</i>
     * invocation need not stay around for the duration of the asynchronous
     * operation, and therefore any of their associated resources may be
     * released.
     *
     * <p>This method clears the list of {@link AsyncListener} instances
     * (if any) that were registered with the AsyncContext returned by the
     * previous call to one of the startAsync methods, after calling each
     * AsyncListener at its {@link AsyncListener#onStartAsync onStartAsync}
     * method.
     *
     * <p>Subsequent invocations of this method, or its overloaded
     * variant, will return the same AsyncContext instance, reinitialized
     * as appropriate.
     *
     * @return the (re)initialized AsyncContext
     *
     * @throws IllegalStateException if this channel is within the scope of
     * a filter or servlet that does not support asynchronous operations
     * (that is, {@link #isAsyncSupported} returns false),
     * or if this method is called again without any asynchronous dispatch
     * (resulting from one of the {@link AsyncContext#dispatch} methods),
     * is called outside the scope of any such dispatch, or is called again
     * within the scope of the same dispatch, or if the response has
     * already been closed
     *
     * @see AsyncContext#dispatch()
     * @since Servlet 3.0
     */
    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return request.startAsync();
    }

    /**
     * Puts this channel into asynchronous mode, and initializes its
     * {@link AsyncContext} with the given channel and response objects.
     *
     * <p>The ServletRequest and ServletResponse arguments must be
     * the same instances, or instances of {@link ServletRequestWrapper} and
     * {@link ServletResponseWrapper} that wrap them, that were passed to the
     * {@link Servlet#service service} method of the Servlet or the
     * {@link Filter#doFilter doFilter} method of the Filter, respectively,
     * in whose scope this method is being called.
     *
     * <p>Calling this method will cause committal of the associated
     * response to be delayed until {@link AsyncContext#complete} is
     * called on the returned {@link AsyncContext}, or the asynchronous
     * operation has timed out.
     *
     * <p>Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on
     * the returned AsyncContext will return <code>false</code>,
     * unless the passed in ServletRequest and ServletResponse arguments
     * are the original ones or do not carry any application-provided wrappers.
     * Any filters invoked in the <i>outbound</i> direction after this
     * channel was put into asynchronous mode may use this as an indication
     * that some of the channel and/or response wrappers that they added
     * during their <i>inbound</i> invocation may need to stay in place for
     * the duration of the asynchronous operation, and their associated
     * resources may not be released.
     * A ServletRequestWrapper applied during the <i>inbound</i>
     * invocation of a filter may be released by the <i>outbound</i>
     * invocation of the filter only if the given <code>servletRequest</code>,
     * which is used to initialize the AsyncContext and will be returned by
     * a call to {@link AsyncContext#getRequest()}, does not contain said
     * ServletRequestWrapper. The same holds true for ServletResponseWrapper
     * instances.
     *
     * <p>This method clears the list of {@link AsyncListener} instances
     * (if any) that were registered with the AsyncContext returned by the
     * previous call to one of the startAsync methods, after calling each
     * AsyncListener at its {@link AsyncListener#onStartAsync onStartAsync}
     * method.
     *
     * <p>Subsequent invocations of this method, or its zero-argument
     * variant, will return the same AsyncContext instance, reinitialized
     * as appropriate. If a call to this method is followed by a call to its
     * zero-argument variant, the specified (and possibly wrapped) channel
     * and response objects will remain <i>locked in</i> on the returned
     * AsyncContext.
     *
     * @param servletRequest the ServletRequest used to initialize the
     * AsyncContext
     * @param servletResponse the ServletResponse used to initialize the
     * AsyncContext
     *
     * @return the (re)initialized AsyncContext
     *
     * @throws IllegalStateException if this channel is within the scope of
     * a filter or servlet that does not support asynchronous operations
     * (that is, {@link #isAsyncSupported} returns false),
     * or if this method is called again without any asynchronous dispatch
     * (resulting from one of the {@link AsyncContext#dispatch} methods),
     * is called outside the scope of any such dispatch, or is called again
     * within the scope of the same dispatch, or if the response has
     * already been closed
     *
     * @since Servlet 3.0
     */
    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return request.startAsync(servletRequest, servletResponse);
    }

    /**
     * Checks if this channel has been put into asynchronous mode.
     *
     * <p>A ServletRequest is put into asynchronous mode by calling
     * {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on it.
     *
     * <p>This method returns <tt>false</tt> if this channel was
     * put into asynchronous mode, but has since been dispatched using
     * one of the {@link AsyncContext#dispatch} methods or released
     * from asynchronous mode via a call to {@link AsyncContext#complete}.
     *
     * @return true if this channel has been put into asynchronous mode,
     * false otherwise
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean isAsyncStarted() {
        return request.isAsyncStarted();
    }

    /**
     * Checks if this channel supports asynchronous operation.
     *
     * <p>Asynchronous operation is disabled for this channel if this channel
     * is within the scope of a filter or servlet that has not been annotated
     * or flagged in the deployment descriptor as being able to support
     * asynchronous handling.
     *
     * @return true if this channel supports asynchronous operation, false
     * otherwise
     *
     * @since Servlet 3.0
     */
    @Override
    public boolean isAsyncSupported() {
        return request.isAsyncSupported();
    }


    /**
     * Gets the AsyncContext that was created or reinitialized by the
     * most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on this channel.
     *
     * @return the AsyncContext that was created or reinitialized by the
     * most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on
     * this channel
     *
     * @throws IllegalStateException if this channel has not been put
     * into asynchronous mode, i.e., if neither {@link #startAsync} nor
     * {@link #startAsync(ServletRequest,ServletResponse)} has been called
     *
     * @since Servlet 3.0
     */
    @Override
    public AsyncContext getAsyncContext() {
        return request.getAsyncContext();
    }

    /**
     * Gets the dispatcher type of this channel.
     *
     * <p>The dispatcher type of a channel is used by the container
     * to listEntries the filters that need to be applied to the channel:
     * Only filters with matching dispatcher type and url patterns will
     * be applied.
     *
     * <p>Allowing a filter that has been configured for multiple
     * dispatcher types to query a channel for its dispatcher type
     * allows the filter to handle the channel differently depending on
     * its dispatcher type.
     *
     * <p>The initial dispatcher type of a channel is defined as
     * <code>DispatcherType.REQUEST</code>. The dispatcher type of a channel
     * dispatched via {@link RequestDispatcher#forward(ServletRequest,
     * ServletResponse)} or {@link RequestDispatcher#include(ServletRequest,
     * ServletResponse)} is given as <code>DispatcherType.FORWARD</code> or
     * <code>DispatcherType.INCLUDE</code>, respectively, while the
     * dispatcher type of an asynchronous channel dispatched via
     * one of the {@link AsyncContext#dispatch} methods is given as
     * <code>DispatcherType.ASYNC</code>. Finally, the dispatcher type of a
     * channel dispatched to an error page by the container's error handling
     * mechanism is given as <code>DispatcherType.ERROR</code>.
     *
     * @return the dispatcher type of this channel
     *
     * @see DispatcherType
     *
     * @since Servlet 3.0
     */
    @Override
    public DispatcherType getDispatcherType() {
        return request.getDispatcherType();
    }
}
