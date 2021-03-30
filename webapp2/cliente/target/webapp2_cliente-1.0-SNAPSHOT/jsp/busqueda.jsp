<%-- 
    Document   : busqueda
    Created on : Mar 6, 2021, 6:34:55 PM
    Author     : ruroz
--%>

<%@page import="java.util.Objects"%>
<% if (Objects.isNull(session.getAttribute("user")))
    response.sendRedirect("/cliente/");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Búsqueda Vídeos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
    </head>
    <body>
        
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <div class="user">Usuario: <%= usuario %>. <form action="${pageContext.request.contextPath}/servletUsuarios" method="post"><button type="submit" name="button" value="logout">Cerrar sesi&oacute;n</button></form></div>
        
        <center> <h1> Búsqueda Videos </h1> </center>  

        <div class="container" title="Buscar Videos">
            <p>Filtrar por atributos:<br>
            <form action="../servletBusqueda" method="get">  
            <div class="container">   
                <label>Titulo</label>   
                <input type="text" placeholder="Introduzca título" name="titulo">  
                <label>Autor</label>   
                <input type="text" placeholder="Introduzca Autor" name="autor">  
                <!--
                <label>Año</label>   
                <input type="text" min="1914" max="2030" placeholder="Introduzca Año" name="year">  
                <label>Mes</label>    
                <input type="text" min="1" max="12" placeholder="Introduzca Mes" name="mes">  
                <label>Dia</label>   
                <input type="text" min="1" max="31" placeholder="Introduzca Dia" name="dia">  
                -->
                <label>Fecha</label>
                <div class="inputgroup duration"><!--
                    --><input type="text" placeholder="yyyy" pattern="(19[1-9][0-9]|20[0-3][0-9])" name="fechay" id="fechay" class="year" />-<!--
                    --><input type="text" placeholder="mm" pattern="(0?[1-9]|1[012])" name="fecham" id="fecham" />-<!--
                    --><input type="text" placeholder="dd" pattern="(0?[1-9]|[12][0-9]|3[01])" name="fechad" id="fechad" /><!--
                --></div>
                <button type="submit" name="button" value="busquedaVideo">Buscar</button>
            </div>   
        </form>
        </div>

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
                            <script src="../js/entradatiempo.js"></script>
    </body>
</html>
