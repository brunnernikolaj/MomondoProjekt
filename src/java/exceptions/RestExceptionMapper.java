package exceptions;

import com.google.gson.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper for rest exceptions.
 * 
 * @author casper
 * @Date: 4/12 2015
 */
@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    
    /**
     * Exception mapper for flight exception.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param e
     * @return 
     */
    @Override
    public Response toResponse(RestException e) {
        
        JsonObject json = new JsonObject();
        json.addProperty("statusCode", Integer.parseInt(e.getStatusCode().toString()));
        json.addProperty("message", e.getMessage());
        return Response.status(e.getStatusCode()).entity(json.toString()).build();
    }
}