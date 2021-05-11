<%-- 
    Document   : cifrado
    Created on : 04-may-2021, 16:08:33
    Author     : Resis
--%>
<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Cifrado de contenido</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/css/style.css" >

    </head>
    <body>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <div class="user">Usuario: <%= usuario %>. <form action="/servletUsuarios" method="post"><button type="submit" name="button" value="logout">Cerrar sesi&oacute;n</button></form></div>

        <a href="/jsp/listadoVid.jsp">&laquo; Listado de vídeos</a>
        <h1>Cifrado de XML</h1>
        <form action="/servletCifrado" enctype="multipart/form-data" method="post">  
            <div class="container">
                <label>Fichero</label>
                <input type="file" name="file" />
                <input type="checkbox" id="only_content" name="only_content" value="selected">
                <label for="only_content"> Solo contenido</label><br><br>
                <label for="names"> Nombre elementos que se quieren cifrar (separados por comas)</label>
                <input type="text" id="names" name="names">
                <input type="hidden" name="username" value="<%= usuario %>" />
                <button type="submit" name="button" value="encryptXML">Cifrar</button>
                <button type="submit" name="button" value="decryptXML">Descifrar</button>
            </div>
        </form>
        <script src="/js/entradatiempo.js"></script>
    </body>
</html>