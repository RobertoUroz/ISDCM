package isdcm.webapp.resources;

import isdcm.webapp.identity.TokenSigner;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("oauth2/v1")
public class OAuth {
    
    @POST
    @Path("/token")
    public Response token(@HeaderParam("username") String username, @HeaderParam("password") String password){
        TokenSigner s = new TokenSigner();
        if (s.sign(username, password))
            return Response.status(200)
                    .entity(s.getToken())
                    .build();
        else
            return Response.status(400)
                    .entity("BAD REQUEST")
                    .build();
    }
}
