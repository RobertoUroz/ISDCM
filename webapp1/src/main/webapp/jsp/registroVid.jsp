<!DOCTYPE html>
<html>
    <head>
        <title>Registro Video</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/webapp1/css/style.css" >
    </head>
    <body>
        <center> <h1> Registro Video </h1> </center>   
        <form action="/webapp1/servletRegistroVid" method="post">  
            <div class="container">   
                <label>Titulo</label>   
                <input type="text" placeholder="Introduzca Titulo" name="titulo" required title="Por favor, introduzca un valor">  
                <label>Autor</label>   
                <input type="text" placeholder="Introduzca Autor" name="autor" required title="Por favor, introduzca un valor">  
                <label>Duracion</label>   
                <input type="text" placeholder="Introduzca Duracion" name="duracion" required title="Por favor, introduzca un valor">  
                <label>Descripcion</label>   
                <input type="text" placeholder="Introduzca Descripcion" name="descripcion" required title="Por favor, introduzca un valor">  
                <label>Formato</label>   
                <input type="text" placeholder="Introduzca Formato" name="formato" required title="Por favor, introduzca un valor">  
                <label>URL</label>   
                <input type="text" placeholder="Introduzca URL" name="url" required title="Por favor, introduzca un valor">    
                <button type="submit" name="registroVideo" value="registroV">Registrar</button>
            </div>   
        </form>
        <center><p id="errorMessage"></p></center>
    </body>
</html>
