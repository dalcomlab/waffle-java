<%@ page contentType="text/html;charset=utf-8"%>
<html>
<body>
<% out.println("hello jsp");%>
<%String a = "hello222222";%>
<%=a%>
<table border="1">
<%
    for(int i = 0; i <= 10; i++){
        out.println("<tr><td>");
        out.println("Hello World" + i + "<br/>");
        out.println("</td></tr>");
    }
%>
</table>
<% for ( int j = 0; j <= 10; j++){%>
<%=j%><br/>
<%}%>
</body>
</html>