/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PassengerDto;
import dtos.ReservationDto;
import entity.Passenger;
import entity.Reservation;
import exceptions.FlightException;
import facades.FlightFacade;
import facades.ReservationFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.apache.commons.mail.EmailException;
import utility.MailService;

/**
 * REST Web Service
 *
 * @author Nikolaj
 */
@Path("flightreservation")
public class ResevationService {

    @Context
    private UriInfo context;
    FlightFacade flightFacade = new FlightFacade();
    ReservationFacade reservationFacade = new ReservationFacade();
    private Gson gson;

    /**
     * Creates a new instance of ResevationService
     */
    public ResevationService() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Retrieves representation of an instance of rest.ResevationService
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getXml() throws EmailException {
        MailService.sendMail();
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String flightReservation(String json) throws FlightException {

        ReservationDto reservationDto = gson.fromJson(json, ReservationDto.class);
        
        Reservation reservation = toEntity(reservationDto);
        reservation.setFlight(flightFacade.getByFlightNumber(reservationDto.getFlightID()));

        return gson.toJson(toDto(reservationFacade.saveReservation(reservation)));
    }

    private ReservationDto toDto(Reservation reservation) {
        List<PassengerDto> passengers = new ArrayList<>();

        for (Passenger passenger : reservation.getPassengers()) {
            passengers.add(new PassengerDto(
                    passenger.getFirstname(),
                    passenger.getLastname()
            ));
        }

        return new ReservationDto(
                reservation.getFlight().getFlightNumber(),
                null,
                null,
                null,
                2,
                passengers
        );
    }
    
    private Reservation toEntity(ReservationDto reservation) {
        List<Passenger> passengers = new ArrayList<>();

        for (PassengerDto passenger : reservation.getPassengers()) {
            passengers.add(new Passenger(
                    passenger.getFirstName(),
                    passenger.getLastName()
            ));
        }

        return new Reservation(
                passengers
        );
    }

    /**
     * PUT method for updating or creating an instance of ResevationService
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putXml(String content) {
    }
}
