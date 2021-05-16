<%@page import="java.util.Objects"%>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<% Boolean insertado = !Objects.isNull(request.getAttribute("vid_insertado")) && ((Boolean) request.getAttribute("vid_insertado"));%>

<% 
    if (Objects.isNull(request.getAttribute("listVideos"))) {
            request.getRequestDispatcher("servletListadoVid").forward(request, response);
    }
%>
<html>
    <head>
        <title>Listado Videos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/cliente_glassfish/css/style.css" >
    </head>
    <body>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <div class="user">Usuario: <%= usuario %>. <form action="/cliente_glassfish/servletUsuarios" method="post"><button type="submit" name="button" value="logout">Cerrar sesi&oacute;n</button></form></div>
        <h1> Listado Videos </h1>
        <center>
            <a href="/cliente_glassfish/jsp/registroVid.jsp"><button type="button">Registrar Video</button></a>
            <a href="/cliente_glassfish/jsp/cifradoContenido.jsp"><button type="button">Cifrar o descifrar v&iacute;deo</button></a>
        </center>    
        
        <% if (insertado) {%>
            <div class="notice goodnotice">�V�deo insertado con �xito!</div>
            <%request.setAttribute("vid_insertado", false);%>
        <%}%>
        <div class="container">
            <table class="datatable">
                <tr>
                    <th>Id</th>
                    <th>T&iacute;tulo</th>
                    <th>Autor</th>
                    <th>Descripci&oacute;n</th>
                    <th>Fecha Creaci&oacute;n</th>
                    <th>Duraci&oacute;n</th>
                    <th>Reproducciones</th>
                </tr>
                <c:forEach var="video" items="${listVideos}">
                    <tr>
                    <td>${video.id}</td>
                    <td>${video.titulo}</td>
                    <td>${video.autor}</td>
                    <td>${video.descripcion}</td>
                    <td>${video.date}</td>
                    <td>${video.duracionString}</td>
                    <td>${video.reproducciones}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>   
        <!--<center> <button type="button" action="/cliente_glassfish/servletListadoVid" method="get">Mis videos</button></a> </center>    -->
    </body>
</html>
