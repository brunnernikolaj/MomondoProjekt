package exceptions;

import com.google.gson.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Exception mapper for the flight exeption described 
 * in the assignment.
 * 
 * @Author: Casper Schultz
 * @Date: 4/12 2015
 */
@Provider
public class FlightExceptionMapper implements ExceptionMapper<FlightException> {
    
    /**
     * Exception mapper for flight exception.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param e         Exception that is getting mapped.
     * @return          Response object with error as json in the body.
     */
    @Override
    public Response toResponse(FlightException e) {
        
        JsonObject json = new JsonObject();
        json.addProperty("httpError", e.getErrorCode());
        json.addProperty("errorCode", "" + e.getStatusCode().toString());
        json.addProperty("message", e.getMessage());
        return Response.status(e.getStatusCode()).entity(json.toString()).build();
    }
}
