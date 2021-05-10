<%
    Boolean error = !Objects.isNull(request.getAttribute("error_opcifrado"))
            && ((Boolean) request.getAttribute("error_opcifrado"));
    Boolean acierto = !Objects.isNull(request.getAttribute("acierto_opcifrado"))
            && ((Boolean) request.getAttribute("acierto_opcifrado"));
    if (acierto) {
        if (!Objects.isNull(request.getAttribute("dlfile"))) {
            byte[] dlfile = ((byte[]) request.getAttribute("dlfile"));
            %><%= dlfile %><%
            return;
        }
    }
%>
<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Cifrado Contenido</title>
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
        <h1>Cifrado Contenido</h1>
        <form action="/cliente_glassfish/servletCifradoContenido" enctype="multipart/form-data" method="post">  
            <div class="container">
                <label>Fichero</label>
                <input type="file" name="fichero" />
                <input type="hidden" name="username" value="<%= usuario %>" />
                <button type="submit" name="accioncifrado" value="cifrar">Cifrar</button>
            </div>
        </form>
        <% if (error) {%>
            <center><p id="errorMessage">Video no ha podido ser insertado, por favor, inténtelo de nuevo</p></center>
            <%request.setAttribute("error_registro_vid", false);%>
        <%}%>
        <script src="/cliente_glassfish/js/entradatiempo.js"></script>
    </body>
</html>