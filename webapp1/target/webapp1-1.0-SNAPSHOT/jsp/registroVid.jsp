<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Boolean error = !Objects.isNull(request.getAttribute("error_registro_vid")) && ((Boolean) request.getAttribute("error_registro_vid"));%>

<!DOCTYPE html>
<html>
    <head>
        <title>Registro Video</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/webapp1/css/style.css" >
    </head>
    <body>
        <h1> Registro Video </h1>
        <form action="/webapp1/servletRegistroVid" method="post">  
            <div class="container">   
                <label>Titulo</label>   
                <input type="text" placeholder="Introduzca Titulo" name="titulo" required>  
                <label>Autor</label>   
                <input type="text" placeholder="Introduzca Autor" name="autor" required>  
                <label>Duracion</label>   
                <!--<input type="text" placeholder="Introduzca Duracion" name="duracion" required>-->
                <div class="inputgroup duration"><input type="number" placeholder="hh" min="0" max="99" name="duracionh" />:<input type="number" placeholder="mm" min="0" max="59" name="duracionmin" />:<input type="number" placeholder="ss" min="0" max="59" name="duracions" /></div>
                <label>Descripcion</label>   
                <input type="text" placeholder="Introduzca Descripcion" name="descripcion" required>
                <label>Formato</label>   
                <input type="text" placeholder="Introduzca Formato" name="formato" required>  
                <label>URL</label>   
                <input type="text" placeholder="Introduzca URL" name="url" required>    
                <button type="submit" name="registroVideo" value="registroV">Registrar</button>
            </div>   
        </form>
        <% if (error) {%>
            <center><p id="errorMessage">Video no ha podido ser insertado, por favor, inténtelo de nuevo</p></center>
            <%request.setAttribute("error_registro_vid", false);%>
         <%}%>
    </body>
</html>
