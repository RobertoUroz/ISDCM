<%-- 
    Document   : registroUsu
    Created on : Feb 27, 2021, 3:51:11 PM
    Author     : ruroz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name='viewport' content='width=device-width, initial-scale=1.0'>
        <title>Registro usuarios</title>
        <link rel="stylesheet" href="/webapp1/css/login.css" >
    </head>
    <body>
        <center> <h1> Registro usuarios </h1> </center>   
        <form>  
            <div class="container">   
                <label>Nombre: </label>   
                <input type="text" placeholder="Introduzca Nombre" name="name" required>  
                <label>Apellidos: </label>   
                <input type="text" placeholder="Introduzca Apellidos" name="fName" required>  
                <label>Correo electrónico: </label>   
                <input type="text" placeholder="Introduzca Correo Electrónico" name="email" required>  
                <label>Nombre usuario: </label>   
                <input type="text" placeholder="Introduzca Nombre Usuario" name="username" required>  
                <label>Contraseña: </label>   
                <input type="text" placeholder="Introduzca Contraseña" name="password" required>  
                <label>Repetir contraseña: </label>   
                <input type="text" placeholder="Introduzca Repetir Contraseña" name="rPassword" required>  
                <button type="submit">Register</button>
            </div>   
        </form>
    </body>
</html>
