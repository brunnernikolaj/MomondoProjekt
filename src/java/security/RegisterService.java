/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RegisterDto;
import entity.User;
import exceptions.FlightException;
import facades.UserFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import searchengine.SearchEngine;

/**
 * REST Web Service
 *
 * @author Nikolaj
 */
@Path("/register")
public class RegisterService {

    @Context
    private UriInfo context;

    private UserFacade userFacade = UserFacade.getInstance();
    private Gson gson;

    /**
     * Creates a new instance of RegisterService
     */
    public RegisterService() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Retrieves representation of an instance of security.RegisterService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RegisterService
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes("application/json")
    public Response postJson(String content) throws FlightException {

        try {
            RegisterDto newUser = gson.fromJson(content, RegisterDto.class);
            userFacade.createNewUser(toEntity(newUser));
        } catch (Exception ex) {
            Logger.getLogger(RegisterService.class.getName()).log(Level.SEVERE, null, ex);
            throw new FlightException("An unknown error occured while registering", Response.Status.INTERNAL_SERVER_ERROR, 4);
        }
        
        return Response.accepted("User created successfully").build();
    }
    
    private User toEntity(RegisterDto dto){
        return new User
        (
                dto.getPassword(), 
                dto.getUserName(),
                dto.getEmail(), 
                dto.getPhone(), 
                dto.getFirstName(), 
                dto.getLastName()
        );
    }
}
