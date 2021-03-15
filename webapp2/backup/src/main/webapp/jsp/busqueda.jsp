<%-- 
    Document   : busqueda
    Created on : Mar 6, 2021, 6:34:55 PM
    Author     : ruroz
--%>

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
        <center> <h1> Búsqueda Videos </h1> </center>  

        <div class="container" title="Buscar Videos">
            <p>Filtrar por atributos:<br>
            <form action="../servletBusqueda" method="get">  
            <div class="container">   
                <label>Titulo</label>   
                <input type="text" placeholder="Introduzca título" name="titulo">  
                <label>Autor</label>   
                <input type="text" placeholder="Introduzca Autor" name="autor">  
                <label>Año</label>   
                <input type="number" min="1914" max="2030" placeholder="Introduzca Año" name="year">  
                <label>Mes</label>    
                <input type="number" min="1" max="12" placeholder="Introduzca Mes" name="mes">  
                <label>Dia</label>   
                <input type="number" min="1" max="31" placeholder="Introduzca Dia" name="dia">  
                <button type="submit" name="button" value="busquedaVideo">Buscar</button>
            </div>   
        </form>
        </div>

        <div class="container">
            <table>
                <c:forEach var="habit" items="${habits}">
                    <tr>
                        <td>Habit : ${habit}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
