<%-- 
    Document   : reproduccion
    Created on : Mar 6, 2021, 6:35:04 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://vjs.zencdn.net/7.11.4/video-js.css" rel="stylesheet" />
        <title>JSP Page</title>
    </head>
    <body>
        <video
            id="my-video"
            class="video-js"
            controls
            preload="auto"
            width="640"
            height="264"
            poster="https://kinsta.com/es/wp-content/uploads/sites/8/2019/09/jpg-vs-jpeg.jpg"
            data-setup="{}"
        >
        <source id="sourceVideo" src="" type="video/mp4" />
        <p class="vjs-no-js">
          To view this video please enable JavaScript, and consider upgrading to a
          web browser that
          <a href="https://videojs.com/html5-video-support/" target="_blank">supports HTML5 video</a>
        </p>
        </video>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        var BASE_URL = "http://localhost:8080/server_rest/resources";
        var url_string = window.location.href;
        var url = new URL(url_string);
        var id = url.searchParams.get("id");
        var finalUrl = BASE_URL += "/reproductor?id=" + id; 
        var video = document.getElementById('my-video');
        var source = document.getElementById('sourceVideo');
        $.ajax({
            url: finalUrl,
            cache: false,
            success: function (html) {
                console.log(html);
                source.setAttribute('src', html);
                video.load();
            }
        });
    </script>
    <script src="https://vjs.zencdn.net/7.11.4/video.min.js"></script>
    </body>
</html>