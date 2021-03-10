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
        <link rel="stylesheet" href="/webapp1/css/style.css" >
    </head>
    <body>
    <h1> Listado Videos </h1>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <p>Usuario: <%= usuario %></p>
        <% if (insertado) {%>
            <center><p>¡Vídeo insertado con éxito!</p></center>
            <%request.setAttribute("vid_insertado", false);%>
        <%}%>
        <div class="container">
            <table>
                <c:forEach var="video" items="${listVideos}">
                    <tr>
                    <td>Id : ${video.id}</td>
                    <td>Titulo : ${video.titulo}</td>
                    <td>Autor : ${video.autor}</td>
                    <td>Descripcion : ${video.descripcion}</td>
                    <td>Fecha Creacion : ${video.date}</td>
                    <td>Duracion : ${video.duracionString}</td>
                    <td>Reproducciones : ${video.reproducciones}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>   
        <center> <a href="/webapp1/jsp/registroVid.jsp"><button type="button">Registrar Video</button></a> </center>    
        <center> <button type="button" action="/webapp1/servletListadoVid" method="get">Mis videos</button></a> </center>    
    </body>
</html>
