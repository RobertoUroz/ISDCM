<%-- 
    Document   : cifrado
    Created on : 04-may-2021, 16:08:33
    Author     : Resis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cifrado</title>
    </head>
    <body>
        <form action="/servletCifrado" method="post" enctype="multipart/form-data">
            <input type="text" name="description" />
            <input type="file" name="file" />
            <input type="submit" name="button" value="encryptXML" />
        </form>
        <form action="/servletCifrado" method="post" enctype="multipart/form-data">
            <input type="text" name="description" />
            <input type="file" name="file" />
            <input type="submit" name="button" value="decryptXML" />
        </form>
    </body>
</html>
