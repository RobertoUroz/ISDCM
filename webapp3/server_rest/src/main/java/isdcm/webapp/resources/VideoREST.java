/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.resources;

import isdcm.webapp.modelo.Video;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Resis
 */

@Path("video")
public class VideoREST {
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerVideo(String msg) throws URISyntaxException{
        int status = 500;
        Map<String, String> values = new HashMap<>();
        String[] msg_arr = msg.split("&");
        try {
            for (String element : msg_arr){
             values.put(element.split("=")[0], decode(element.split("=")[1]));
            }
            System.out.println(values.toString());
            Video v = new Video(
                values.get("titulo"),
                values.get("autor"),
                Integer.parseInt(values.get("duracionh")),
                Integer.parseInt(values.get("duracionmin")),
                Integer.parseInt(values.get("duracions")),
                values.get("descripcion"),
                values.get("formato"),
                values.get("url"),
                values.get("username")
            );
            if (v.registerVideo())
                status = 201;
            else
                status = 500;
            java.net.URI location = new java.net.URI("http://localhost:8080/cliente/servletListadoVid?vid_insertado=true");
            return Response.seeOther(location).build();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VideoREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response
                .ok()
                .status(500)
                .build();
    }
    
    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }
}
