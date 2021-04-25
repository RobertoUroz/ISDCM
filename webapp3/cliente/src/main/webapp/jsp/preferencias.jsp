<%@page import="isdcm.webapp.modelo.PreferenciasUsuario"%>
<%@page import="java.util.Objects"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<% Boolean guardadas = !Objects.isNull(request.getAttribute("pu_guardadas")) && Boolean.parseBoolean((String) request.getAttribute("pu_guardadas"));%>

<% if (Objects.isNull(session.getAttribute("user")))
    response.sendRedirect("/cliente/");
%>

<html>
    <head>
        <title>Preferencias</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
    </head>
    <body>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <div class="user">Usuario: <%= usuario %>. <form action="${pageContext.request.contextPath}/servletUsuarios" method="post"><button type="submit" name="button" value="logout">Cerrar sesi&oacute;n</button></form></div>
        <a href="${pageContext.request.contextPath}/servletListadoVid">&laquo; Listado de vídeos</a>
        <h1>Preferencias</h1>
        <% if (guardadas) {%>
            <div class="notice goodnotice">¡Preferencias guardadas con éxito!</div>
            <%request.setAttribute("pu_guardadas", false);%>
        <%}%>
        <%
            PreferenciasUsuario pu = PreferenciasUsuario.get(usuario);
        %>
        <form action="${pageContext.request.contextPath}/servletUsuarios" method="POST">
            <div class="container">
            <label>Reproductor</label>
            <div class="inputgroup">
            <input type="radio" name="pu_reproductor" value="1" id="pu_reproductor_1"<% if (pu.reproductor()==pu.REPRODUCTOR_VIDEOJS) { %> checked="checked"<% } %> />
            <label for="pu_reproductor_1">VideoJS</label>
            <input type="radio" name="pu_reproductor" value="2" id="pu_reproductor_2"<% if (pu.reproductor()==pu.REPRODUCTOR_HTML5) { %> checked="checked"<% } %> />
            <label for="pu_reproductor_2">HTML5</label>
            </div>
            
            <label>Lista de v&iacute;deos</label>
            <div class="inputgroup">
            <input type="radio" name="pu_listavideos" value="1" id="pu_listavideos_1"<% if (pu.listavideos()==pu.LISTAVIDEOS_BD) { %> checked="checked"<% } %> />
            <label for="pu_listavideos_1">Base de datos</label>
            <input type="radio" name="pu_listavideos" value="2" id="pu_listavideos_2"<% if (pu.listavideos()==pu.LISTAVIDEOS_SOAPREST) { %> checked="checked"<% } %> />
            <label for="pu_listavideos_2">Servicios SOAP y REST</label>
            </div>
            
            <label>Color de la interfaz</label>
            <div class="inputgroup">
            <input type="radio" name="pu_color" value="1" id="pu_color_1"<% if (pu.color()==pu.COLOR_CLARO) { %> checked="checked"<% } %> />
            <label for="pu_color_1">Claro</label>
            <input type="radio" name="pu_color" value="2" id="pu_color_2"<% if (pu.color()==pu.COLOR_OSCURO) { %> checked="checked"<% } %> />
            <label for="pu_color_2">Oscuro</label>
            </div>
            
            <input type="hidden" name="username" value="<%= usuario %>" />
            <button type="submit" name="button" value="savepreferencias">Guardar</button>
            </div>
        </form>
    </body>
</html>
