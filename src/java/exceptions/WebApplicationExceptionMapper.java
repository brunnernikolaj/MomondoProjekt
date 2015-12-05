package exceptions;

import com.google.gson.JsonObject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Basic web application mapper.
 * 
 * @author casper
 */
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
    
    /**
     * Exception mapper for webapplication exception.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param e
     * @return 
     */
    @Override
    public Response toResponse(WebApplicationException e) {
        
        JsonObject json = new JsonObject();
        json.addProperty("statusCode", "" + Response.Status.INTERNAL_SERVER_ERROR);
        json.addProperty("message", e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
    }
}