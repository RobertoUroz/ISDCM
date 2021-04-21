<%@page import="java.util.Objects"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Boolean error = !Objects.isNull(request.getAttribute("error_registro_vid")) && ((Boolean) request.getAttribute("error_registro_vid"));%>
<% if (Objects.isNull(session.getAttribute("user")))
    response.sendRedirect("/cliente/");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Registro de vídeo</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" >
    </head>
    <body>
        <%
            String usuario = (String) session.getAttribute("user");
        %>
        <div class="user">Usuario: <%= usuario %>. <form action="../servletUsuarios" method="post"><button type="submit" name="button" value="logout">Cerrar sesi&oacute;n</button></form></div>

        <a href="${pageContext.request.contextPath}/servletListadoVid">&laquo; Listado de vídeos</a>
        <h1>Registro de vídeo</h1>
        
        <!-- TODO -->
        <!-- Form with Servlet -->
        <!--<form action="../servletRegistroVid" method="post">  
            <div class="container">   
                <label>Titulo</label>   
                <input type="text" placeholder="Introduzca Titulo" name="titulo" required>  
                <label>Autor</label>   
                <input type="text" placeholder="Introduzca Autor" name="autor" required>  
                <label>Duracion</label>   
                <div class="inputgroup duration">
                    <input type="text" placeholder="hh" pattern="[0-9][0-9]?" name="duracionh" id="duracionh" />:
                    <input type="text" placeholder="mm" pattern="[0-5]?[0-9]" name="duracionmin" id="duracionmin" />:
                    <input type="text" placeholder="ss" pattern="[0-5]?[0-9]" name="duracions" id="duracions" />
                </div>
                <label>Descripcion</label>   
                <input type="text" placeholder="Introduzca Descripcion" name="descripcion" required>
                <label>Formato</label>    <!--pattern="(video|application)/[a-zA-Z0-9.-]+"-->
                <!--<input type="text" placeholder="Introduzca Formato" maxlength="5" name="formato" required>  
                <label>URL</label>   
                <input type="text" placeholder="Introduzca URL" name="url" required>    
                <input type="hidden" name="username" value="<%= usuario %>" />
                <button type="submit" name="registroVideo" value="registroV">Registrar</button>
            </div>   
        </form>-->
              
        <!-- Form with REST POST -->
        <form action="http://localhost:8080/server_rest/resources/video/register" method="post">  
            <div class="container">   
                <label>Titulo</label>   
                <input type="text" placeholder="Introduzca Titulo" name="titulo" required>  
                <label>Autor</label>   
                <input type="text" placeholder="Introduzca Autor" name="autor" required>  
                <label>Duracion</label>   
                <div class="inputgroup duration">
                    <input type="text" placeholder="hh" pattern="[0-9][0-9]?" name="duracionh" id="duracionh" />:
                    <input type="text" placeholder="mm" pattern="[0-5]?[0-9]" name="duracionmin" id="duracionmin" />:
                    <input type="text" placeholder="ss" pattern="[0-5]?[0-9]" name="duracions" id="duracions" />
                </div>
                <label>Descripcion</label>   
                <input type="text" placeholder="Introduzca Descripcion" name="descripcion" required>
                <label>Formato</label>    <!--pattern="(video|application)/[a-zA-Z0-9.-]+"-->
                <input type="text" placeholder="Introduzca Formato" maxlength="5" name="formato" required>
                <label>URL</label>   
                <input type="text" placeholder="Introduzca URL" name="url" required>    
                <input type="hidden" name="username" value="<%= usuario %>" />
                <button type="submit" name="registroVideo" value="registroV">Registrar</button>
            </div>   
        </form>
                
        <% if (error) {%>
            <center><p id="errorMessage">Video no ha podido ser insertado, por favor, inténtelo de nuevo</p></center>
            <%request.setAttribute("error_registro_vid", false);%>
        <%}%>
        <script src="../js/entradatiempo.js"></script>											
    </body>
</html>
