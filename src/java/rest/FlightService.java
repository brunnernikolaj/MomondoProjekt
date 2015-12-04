/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.Flight;
import facades.AirportFacade;
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
import org.joda.time.DateTime;

/**
 * REST Web Service
 *
 * @author casper
 */
@Path("flight")
public class FlightService {

    @Context
    private UriInfo context;
    private Gson gson;
    FlightFacade facade = FlightFacade.getInstance();
    AirportFacade airportFacade = AirportFacade.getInstance();
    
    /**
     * Creates a new instance of JFFlights
     */
    public FlightService() {
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
        if (airportFacade.getAirportByIATA(from) == null || airportFacade.getAirportByIATA(to) == null)
            return "Invalid IATA code(s)";
        
        if (!day.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
            return "Invalid date format";
        
        // Fetch the flights
        List<Flight> flights = facade.getJFFlights(from, to, day, seats);
        
        // We need to conert the list to a json list in order to store it in the json object
        //JsonElement element = gson.toJsonTree(flights, new TypeToken<List<Flight>>() {}.getType());
        JsonArray jsonArray = new JsonArray(); //element.getAsJsonArray();
        
        // We build the object
        JsonObject json = new JsonObject();
        json.addProperty("airline", "Just Fly");
        
        for (Flight flight : flights) {
            
            JsonObject obj = new JsonObject();
            obj.addProperty("origin", flight.getIataFrom());
            obj.addProperty("destination", flight.getIataTo());
            obj.addProperty("flightNumber", flight.getFlightNumber());
            obj.addProperty("noOfSeats", flight.getNoOfSeats());
            obj.addProperty("travelTime", flight.getTravelTime());
            obj.addProperty("price", flight.getPrice() * seats);
            
            DateTime date = new DateTime(flight.getTravelDate());
            
            obj.addProperty("travelDate", date.toString());
            
            jsonArray.add(obj);
        }
        
        json.add("flights", jsonArray);
        
        return gson.toJson(json);
    }
}
