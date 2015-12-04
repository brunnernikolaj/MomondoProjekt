package exceptions;

import javax.ws.rs.core.Response.Status;

/**
 * General rest exception used for internal errors.
 * 
 * @author Casper Schultz
 * @Date: 4/5 2015
 */
public class RestException extends Exception{

    private Status statusCode;
    
    public RestException(String string, Status statusCode) {
        super(string);
        this.statusCode = statusCode;
    }

    public Status getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    }     
}
