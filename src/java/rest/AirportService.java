/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Airport;
import facades.AirportFacade;
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

  
    @GET
    @Path("{iata}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAirportByIATA(@PathParam("iata") String iata) {
        
        Airport airport = facade.getAirportByIATA(iata);
        return gson.toJson(airport);
    }
}
