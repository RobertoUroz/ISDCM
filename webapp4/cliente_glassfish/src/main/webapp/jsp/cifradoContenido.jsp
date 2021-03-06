<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Cifrado de contenido</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/cliente_glassfish/css/style.css" >

    </head>
    <body>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <div class="user">Usuario: <%= usuario %>. <form action="/cliente_glassfish/servletUsuarios" method="post"><button type="submit" name="button" value="logout">Cerrar sesi&oacute;n</button></form></div>

        <a href="/cliente_glassfish/jsp/listadoVid.jsp">&laquo; Listado de vídeos</a>
        <h1>Cifrado de contenido</h1>
        <form action="/cliente_glassfish/servletCifradoContenido" enctype="multipart/form-data" method="post">
            <div class="container" id="container">
                <label>Fichero</label>
                <input type="file" name="fichero" />
                <input type="hidden" name="username" id="username" value="<%= usuario %>" />
                <button type="submit" name="accioncifrado" id="bcifrar" value="cifrar">Cifrar</button>
                <button type="submit" name="accioncifrado" id="bdescifrar" value="descifrar">Descifrar</button>
            </div>
        </form>
        <script src="/cliente_glassfish/js/draganddrop.js"></script>
    </body>
</html>