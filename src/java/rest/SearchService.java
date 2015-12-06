/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import exceptions.FlightException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import requests.FlightRequest;
import searchengine.SearchEngine;
import searchengine.SearchTask;
import searchengine.SearchTaskWithDestination;

/**
 * REST Web Service
 *
 * @author Nikolaj
 */
@Path("/search")
public class SearchService {

    @Context
    private UriInfo context;
    
    private SearchEngine searchEngine;

    /**
     * Creates a new instance of FlightinfoResource
     */
    public SearchService() {
        searchEngine = new SearchEngine();
    }

    /**
     * Retrieves and returns flight results.
     * 
     * @Author: Nikolaj, Casper
     * @Date: 6/12 2015
     * 
     * @param from              Origin as IATA code
     * @param day               Date as ISO 8601
     * @param seats             Number of seats to search for.
     * @return                  Json object with results
     * @throws FlightException 
     */
    @GET
    @Path("/{from}/{day}/{seats}")
    @Produces("application/json")
    public String search(@PathParam("from") String from, @PathParam("day") String day, @PathParam("seats") int seats) throws FlightException {
        
        try {
            Date time = convertToDate(day);
            FlightRequest request = new FlightRequest(from, null, time, seats);
            String result = searchEngine.search(x -> new SearchTask(x, request)).toString();
            
            return result;
            
        } catch (InterruptedException | ParseException ex) {
            throw new FlightException("An unknown error occured while searching for flights", Response.Status.INTERNAL_SERVER_ERROR, 4);
        } 
    }
    
    /**
     * Retrieves and returns flight results.
     * 
     * @Author: Nikolaj, Casper
     * @Date: 6/12 2015
     * 
     * @param from              Origin as IATA code
     * @param day               Date as ISO 8601
     * @param seats             Number of seats to search for.
     * @param to                Destination as IATA code
     * @return                  Json object with results
     * @throws FlightException 
     */
    @GET
    @Path("/{from}/{to}/{day}/{seats}")
    @Produces("application/json")
    public String searchWithDestination(@PathParam("from") String from,@PathParam("to") String to, @PathParam("day") String day, @PathParam("seats") int seats) throws FlightException {
        
        try {
            Date time = convertToDate(day);
            FlightRequest request = new FlightRequest(from, to, time, seats);
            String result = searchEngine.search(x -> new SearchTaskWithDestination(x, request)).toString();
            
            return result;
            
        } catch (InterruptedException | ParseException ex) {
            throw new FlightException("An unknown error occured while searching for flights", Response.Status.INTERNAL_SERVER_ERROR, 4);
        }
    }
    
    
    /**
     * Converts a date as String to ISO 8601.
     * 
     * @Author: Nikolaj
     * @Date: 6/12 2015
     * 
     * @param day               Date as string
     * @return                  Date object formatted after ISO 8601
     * @throws ParseException 
     */
    private Date convertToDate(String day) throws ParseException {
        //TODO return proper representation object
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        Date time = df.parse(day);
        return time;
    }
}
