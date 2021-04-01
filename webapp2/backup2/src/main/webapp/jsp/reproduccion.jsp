<%-- 
    Document   : reproduccion
    Created on : Mar 6, 2021, 6:35:04 PM
    Author     : ruroz
--%>
<%@page import="java.util.Objects"%>
<% if (Objects.isNull(session.getAttribute("user")))
    response.sendRedirect("/cliente/");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
