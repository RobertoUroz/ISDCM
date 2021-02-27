<%-- 
    Document   : login
    Created on : Feb 27, 2021, 3:48:09 PM
    Author     : ruroz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <title>Login Page</title>
        <link rel="stylesheet" href="css/login.css" >
    </head>
    <body>    
        <center> <h1> Login </h1> </center>   
        <form action="${pageContext.request.contextPath}/servletUsuarios" method="get">  
            <div class="container">   
                <label>Nombre usuario:</label>   
                <input type="text" placeholder="Introduzca Usuario" name="username" required title="Por favor, introduzca un valor">  
                <label>Contraseña:</label>   
                <input type="password" placeholder="Introduzca Contraseña" name="password" required>  
                <button type="submit" name="login" value="loginV">Login</button>
                <a href="/webapp1/jsp/registroUsu.jsp"><button type="button">Register</button></a>
            </div>   
        </form>     
</body>     
</html>
