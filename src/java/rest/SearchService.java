/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import requests.FlightRequest;
import searchengine.SearchEngine;
import searchengine.SearchTask;
import searchengine.SearchTaskWithDestination;

/**
 * REST Web Service
 *
 * @author Nikolaj
 */
@Path("/flightinfo")
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
     * Retrieves representation of an instance of conf.FlightinfoResource
     * @param from
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{from}/{day}/{seats}")
    @Produces("application/json")
    public String search(@PathParam("from") String from, @PathParam("day") String day, @PathParam("seats") int seats) throws ParseException, InterruptedException {
        Date time = convertToDate(day);
        
        FlightRequest request = new FlightRequest(from, null, time, seats);     
        
        return searchEngine.search(x -> new SearchTask(x, request)).toString();
    }
    
    @GET
    @Path("/{from}/{to}/{day}/{seats}")
    @Produces("application/json")
    public String searchWithDestination(@PathParam("from") String from,@PathParam("to") String to, @PathParam("day") String day, @PathParam("seats") int seats) throws ParseException, InterruptedException {
        Date time = convertToDate(day);
        
        FlightRequest request = new FlightRequest(from, to, time, seats);     
        
        return searchEngine.search(x -> new SearchTaskWithDestination(x, request)).toString();
    }

    private Date convertToDate(String day) throws ParseException {
        //TODO return proper representation object
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        Date time = df.parse(day);
        return time;
    }
}
