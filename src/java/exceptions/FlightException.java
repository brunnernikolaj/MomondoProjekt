package exceptions;

import javax.ws.rs.core.Response.Status;

/**
 * Flight exception.
 * 
 * This is the standard exception used in the system. 
 * 
 * @Author: Casper Schultz
 * @Date: 4/12 2015
 */
public class FlightException extends Exception {
    
    private Status statusCode;
    private int errorCode;
    
    /**
     * Constructor.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param string        A custom message
     * @param statusCode    The response status code.
     * @param errorCode     The custom error code defined in the assignment
     */
    public FlightException(String string, Status statusCode, int errorCode) {
        super(string);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
    
    /**
     * Response status code. 
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @return  The status code that has been set.
     */
    public Status getStatusCode() {
        return statusCode;
    }
    
    /**
     * Response status code.
     *
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param statusCode    Response status code
     */
    public void setStatusCode(Status statusCode) {
        this.statusCode = statusCode;
    } 
    
    /**
     * Error codes specified in the api.
     * 
     * 1: No Flights
     * 2: None or not enough avilable tickets
     * 3: Illegal Input
     * 4: Unknown error
     * 10 -> You can define your own codes here
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param code  The error code between 1 - 4
     */
    public void setErrorCode(int code) {
        this.errorCode = code;
    }
    
    /**
     * Error code.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     */
    public int getErrorCode() {
        return this.errorCode;
    }
}
