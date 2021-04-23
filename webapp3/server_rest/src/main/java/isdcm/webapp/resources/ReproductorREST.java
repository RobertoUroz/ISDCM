package isdcm.webapp.resources;

import isdcm.webapp.modelo.Video;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
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
    
    @POST
    @Path("/viewoncemore")
    public Response viewOnceMorePost(@QueryParam("id") String id) {
        Client client = ClientBuilder.newClient();
        Integer c = 1;
        Response wt = client.target("http://localhost:8080/server_rest/resources/viewoncemore").
                queryParam("id",id).request().put(Entity.text(c));
        int st = wt.getStatus();
        String re = (st == 400) ? "BAD REQUEST" : "";
        return Response.ok(re).status(st).build();
    }
    
    @PUT
    @Path("/viewoncemore")
    public Response viewOnceMore(@QueryParam("id") String id) {
        String result = "BAD REQUEST";
        int status = 400;
        if (id != null){
            int id_int = Integer.parseInt(id);
            Video v = new Video(id_int);
            boolean vom = v.viewOnceMore();
            System.out.println("ReproductorREST.viewOnceMore()::Id is : " + id);
            System.out.println("ReproductorREST.viewOnceMore(): vom is : " + vom);
            status = 204;
            result = "";
        }
        return Response.ok(result).status(status).build();
    }
}
