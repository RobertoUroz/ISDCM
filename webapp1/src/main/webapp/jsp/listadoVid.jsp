<<<<<<< HEAD
=======
<%@page import="java.util.Objects"%>
<!DOCTYPE html>
>>>>>>> 89cbe41f2e86c14d23a8ee26797909ec2db3a1b9
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<<<<<<< HEAD
<!DOCTYPE html>
=======
<% Boolean insertado = !Objects.isNull(request.getAttribute("vid_insertado")) && ((Boolean) request.getAttribute("vid_insertado"));%>


>>>>>>> 89cbe41f2e86c14d23a8ee26797909ec2db3a1b9
<html>
    <head>
        <title>Listado Videos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/webapp1/css/style.css" >
    </head>
    <body>
<<<<<<< HEAD
    <h1> Listado Videos </h1>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <p>Usuario: <%= usuario %></p>
=======
        <center> <h1> Listado Videos </h1> </center>  
        <% if (insertado) {%>
            <center><p>Video insertado con éxito!</p></center>
            <%request.setAttribute("vid_insertado", false);%>
        <%}%>
>>>>>>> 89cbe41f2e86c14d23a8ee26797909ec2db3a1b9
        <div class="container">
            <table>
                <c:forEach var="habit" items="${habits}">
                    <tr>
                    <td>Habit : ${habit}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>   
        <center> <a href="/webapp1/jsp/registroVid.jsp"><button type="button">Registrar Video</button></a> </center>    
        <center> <button type="button" action="/webapp1/servletListadoVid" method="get">Mis videos</button></a> </center>    
    </body>
</html>
