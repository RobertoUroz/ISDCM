<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Boolean error = !Objects.isNull(request.getAttribute("error_registro")) && ((Boolean) request.getAttribute("error_registro"));%>

<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <title>Registro usuarios</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
    </head>
    <body>
        <h1>Registro usuarios</h1>
        <form action="../servletUsuarios" method="post">  
            <div class="container">   
                <label>Nombre: </label>   
                <input type="text" placeholder="Introduzca Nombre" name="nombre" required>  
                <label>Apellidos: </label>   
                <input type="text" placeholder="Introduzca Apellidos" name="apellidos" required>  
                <label>E-mail: </label>   
                <input type="email" placeholder="Introduzca E-mail" name="email" required>  
                <label>Nombre usuario: </label>   
                <input type="text" placeholder="Introduzca Nombre Usuario" name="username" required>  
                <label>Password: </label>   
                <input type="password" placeholder="Introduzca Password" name="password" required>  
                <label>Repetir Password: </label>   
                <input type="password" placeholder="Introduzca Repetir Password" name="rPassword" required>  
                <button type="submit" name="button" value="registro">Registrarse</button>
            </div>   
        </form>
        <% if (error) {%>
            <center> <p id="errorMessage">Usuario ya existe en el sistema</p> </center>
        <%}%>
    </body>
</html>
