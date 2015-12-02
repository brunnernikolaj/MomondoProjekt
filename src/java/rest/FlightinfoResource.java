/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Nikolaj
 */
@Path("/flightinfo")
public class FlightinfoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FlightinfoResource
     */
    public FlightinfoResource() {
    }

    /**
     * Retrieves representation of an instance of conf.FlightinfoResource
     * @param from
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{from}/{day}/{seats}")
    @Produces("application/json")
    public String getJson(@PathParam("from") String from, @PathParam("day") String day, @PathParam("seats") int seats) {
        //TODO return proper representation object
        return "lol";
    }

    /**
     * PUT method for updating or creating an instance of FlightinfoResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
