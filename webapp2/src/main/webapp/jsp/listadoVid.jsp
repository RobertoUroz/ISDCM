<%@page import="java.util.Objects"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% Boolean insertado = !Objects.isNull(request.getAttribute("vid_insertado")) && ((Boolean) request.getAttribute("vid_insertado"));%>


<html>
    <head>
        <title>Listado Videos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
    </head>
    <body>
        <center> <h1> Listado Videos </h1> </center>  
        <% if (insertado) {%>
            <center><p>Video insertado con éxito!</p></center>
            <%request.setAttribute("vid_insertado", false);%>
        <%}%>
        <div class="container">
            <table>
                <c:forEach var="habit" items="${habits}">
                    <tr>
                    <td>Habit : ${habit}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>   
        <center> <a href="jsp/registroVid.jsp"><button type="button">Registrar Video</button></a> </center>    
        <center> <a href="jsp/busqueda.jsp"><button type="button">&#128270;</button></a> </center>    
        <center> <button type="button" action="servletListadoVid" method="get">Mis videos</button></a> </center>    
    </body>
</html>
