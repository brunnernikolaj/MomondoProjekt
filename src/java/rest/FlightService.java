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
import entity.Reservation;
import exceptions.FlightException;
import facades.AirportFacade;
import facades.FlightFacade;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.joda.time.DateTime;
import utility.NorweigianDestinations;

/**
 * REST Web Service
 *
 * @author casper
 */
@Path("/flightinfo")
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
    
    
    
    /**
     * Returns flights from Just Fly (our) company.
     * 
     * Returns flights from a given origin but without destination.
     * 
     * @param request
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param from              Travel Origin as IATA code 
     * @param day               Day as date string formatted as: 2016-02-25
     * @param seats             Number of seats required
     * @return                  Json object with flights that match the criteria.
     * @throws FlightException  
     */
    @GET
    @Path("/{from}/{day}/{seats}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightsFrom(@PathParam("from") String from, @PathParam("day") String day, @PathParam("seats") int seats, @Context Request request) throws FlightException {
        
        // We validate the input data, to make sure its a valid IATA code
        // and that the date has been formatted correctly
        if (! NorweigianDestinations.validDestination(from))
            throw new FlightException("We do not support flights to the given IATA destination", Response.Status.NO_CONTENT, 1);
        
        // Fetch the flights
        List<Flight> flights = facade.getJFFlightsFrom(from, day, seats);
        
        // Add some caching
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        EntityTag etag = new EntityTag(Integer.toString(flights.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(etag);
        
        // cached resource did change -> serve updated content
        if(builder == null) {
            builder = Response.ok(buildJsonObject(flights, seats));
            builder.tag(etag);
        }

        builder.cacheControl(cc);
        return builder.build();
    }
    
    /**
     * Returns flights from Just Fly (our) company.
     * 
     * @param request
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param from              Travel Origin as IATA code
     * @param to                Travel destination as IATA code 
     * @param day               Day as date string formatted as: 2016-02-25
     * @param seats             Number of seats required
     * @return                  Json object with flights that match the criteria.
     * @throws FlightException  
     */
    @GET
    @Path("/{from}/{to}/{day}/{seats}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFlightsToFrom(@PathParam("from") String from, @PathParam("to") String to, @PathParam("day") String day, @PathParam("seats") int seats, @Context Request request) throws FlightException {
        
        // We validate the input data, to make sure its a valid IATA code
        // and that the date has been formatted correctly
        if (! NorweigianDestinations.validDestination(from) || ! NorweigianDestinations.validDestination(to))
            throw new FlightException("We do not support flights to the given IATA destination", Response.Status.NO_CONTENT, 1);
        
        
        // Fetch the flights
        List<Flight> flights = facade.getJFFlights(from, to, day, seats);
        
         // Add some caching
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        EntityTag etag = new EntityTag(Integer.toString(flights.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(etag);
        
        if(builder == null) {
            builder = Response.ok(buildJsonObject(flights, seats));
            builder.tag(etag);
        }
        
        builder.cacheControl(cc);
        return builder.build();
    }
    
    
    /**
     * Builds Json object with flight results.
     * 
     * Builds a json object afther the specified format handed
     * out in the assignment.
     * 
     * @Author: Casper Schultz
     * @Date: 4/12 2015
     * 
     * @param flights       List of flight objects
     * @param seats         The number of seats used to calculate the price
     * @return              Json object as string
     */
    private String buildJsonObject(List<Flight> flights, int seats) {
        
        JsonArray jsonArray = new JsonArray(); 
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
