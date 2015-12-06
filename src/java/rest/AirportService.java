package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Airport;
import exceptions.RestException;
import facades.AirportFacade;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author casper
 */
@Path("airport")
public class AirportService {

    @Context
    private UriInfo context;
    private Gson gson;
    AirportFacade facade = AirportFacade.getInstance();
    
    /**
     * Creates a new instance of JFFlights
     */
    public AirportService() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Look up airport by iata.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param iata              The aiport to look for as IATA
     * @return                  The airport that was looked up as object
     * @throws RestException    if no airports is found 
     */
    @GET
    @Path("{iata}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAirportByIATA(@PathParam("iata") String iata) throws RestException {
        
        Airport airport = facade.getAirportByIATA(iata);
        return gson.toJson(airport);
    }
    
    @GET
    @Path("city/{city}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAirportByCity(@PathParam("city") String city) throws RestException {
        
        List<Airport> airports = facade.getAirportsBycity(city);
        return gson.toJson(airports);
    }
}
