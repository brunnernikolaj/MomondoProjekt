/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.FlightEntity;
import facades.FlightFacade;
import java.io.IOException;
import java.text.ParseException;
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
@Path("flight")
public class JFFlights {

    @Context
    private UriInfo context;
    private Gson gson;
    FlightFacade facade = FlightFacade.getInstance();
    
    /**
     * Creates a new instance of JFFlights
     */
    public JFFlights() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlights() {
        return gson.toJson("BlaBla");
    }
    
    /**
     * Returns flights from Just Fly (our) company.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param from              Travel Origin as IATA code
     * @param to                Travel destination as IATA code 
     * @param day               Day as date string formatted as: 2016-02-25
     * @param seats             Number of seats required
     * @return                  Json object with flights that match the criteria.
     * @throws ParseException
     * @throws IOException 
     */
    @GET
    @Path("/{from}/{to}/{day}/{seats}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlights(@PathParam("from") String from, @PathParam("to") String to, @PathParam("day") String day, @PathParam("seats") int seats) throws ParseException, IOException {
        
        // We validate the input data, to make sure its a valid IATA code
        // and that the date has been formatted correctly
        
        
        
        // Fetch the flights
        List<FlightEntity> flights = facade.getJFFlights(from, to, day, seats);
        
        // We need to conert the list to a json list in order to store it in the json object
        JsonElement element = gson.toJsonTree(flights, new TypeToken<List<FlightEntity>>() {}.getType());
        JsonArray jsonArray = element.getAsJsonArray();
        
        // We build the object
        JsonObject json = new JsonObject();
        json.addProperty("airline", "Just Fly");
        json.add("flights", jsonArray);
        
        return gson.toJson(json);
    }
}
