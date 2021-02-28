<%-- 
    Document   : login
    Created on : Feb 27, 2021, 3:48:09 PM
    Author     : ruroz
--%>

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
        <link rel="stylesheet" href="/webapp1/css/style.css" >
    </head>
    <body>
        <center> <h1> Login </h1> </center>   
        <form action="/webapp1/servletUsuarios" method="get">  
            <div class="container">   
                <label>Nombre usuario:</label>   
                <input type="text" placeholder="Introduzca Usuario" name="username" required title="Por favor, introduzca un valor">  
                <label>Password:</label>   
                <input type="password" placeholder="Introduzca Password" name="password" required>  
                <button type="submit" name="login" value="loginV">Login</button>
                <a href="/webapp1/jsp/registroUsu.jsp"><button type="button">Register</button></a>
            </div>   
        </form>
        <center> <p id="errorMessage" class="hidden"> User is not registered </p> </center>
</body>     
</html>