<%-- 
    Document   : login
    Created on : Feb 27, 2021, 3:48:09 PM
    Author     : ruroz
--%>

<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Boolean error = !Objects.isNull(request.getAttribute("error_login")) && ((Boolean) request.getAttribute("error_login"));%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <title>Login Page</title>
        <link rel="stylesheet" href="css/style.css" >
    </head>
    <body>
        <h1>Login</h1>
        <form action="servletUsuarios" method="post">  
            <div class="container">   
                <label>Nombre usuario:</label>   
                <input type="text" placeholder="Introduzca Usuario" name="username" required>  
                <label>Password:</label>   
                <input type="password" placeholder="Introduzca Password" name="password" required>  
                <button type="submit" name="button" value="login">Iniciar sesi&oacute;n</button>
                <a href="/webapp1/jsp/registroUsu.jsp"><button type="button">Registro</button></a>
            </div>   
        </form>
        <%  if (error) {%>
                <center> <p id="errorMessage">Usuario no registrado en el sistema</p> </center>
                <%request.setAttribute("error_login", false);%>
        <%}%>
</body>     
</html>
