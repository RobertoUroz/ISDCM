package isdcm.webapp.resources;

import isdcm.webapp.modelo.Video;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("reproductor")
public class ReproductorREST {
    
    @GET
    public Response getURL(@QueryParam("id") String id){
        String result = "BAD REQUEST";
        int status = 400;
        if (id != null){
            int id_int = Integer.parseInt(id);
            Video v = new Video(id_int);
            String url = v.getUrl();
            if (url == null)
                url = v.searchURL();
            System.out.println("ReproductorREST.getURL()::Id is : " + id);
            System.out.println("ReproductorREST.getURL()::URL is : " + v.getUrl());
            status = 200;
            result = url;
        }
        return Response
                .ok(result)
                .status(status)
                .build();
    }
}
