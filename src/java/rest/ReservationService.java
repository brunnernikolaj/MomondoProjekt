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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
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
import us.monoid.json.JSONException;
import utility.MailService;

/**
 * REST Web Service
 *
 * @author Nikolaj
 */
@Path("/reservation")
@RolesAllowed("User")
public class ReservationService {

    @Context
    private UriInfo context;
    FlightFacade flightFacade = FlightFacade.getInstance();
    ReservationFacade reservationFacade = new ReservationFacade();
    private Gson gson;

    /**
     * Creates a new instance of ResevationService
     */
    public ReservationService() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Get all reservations for the user with the provided username.
     *
     * @Author: Nikolaj
     * @Date: 9/12 2016
     *
     * @param username
     * @return
     */
    @GET
    @Path("/user/{username}")
    @Produces("application/json")
    public String getReservationsByUser(@PathParam("username") String username) {
        List<ReservationDto> returnList = flightFacade
                .getAllReservationsByUser(username)
                .stream()
                .map(x -> toDto(x))
                .collect(Collectors.toList());

        return gson.toJson(returnList);
    }

    /**
     * Get all reservations for every user.
     *
     * @Author: Nikolaj
     * @Date: 9/12 2016
     *
     * @param userName
     * @return
     * @throws EmailException
     */
    @GET
    @RolesAllowed("Admin")
    @Produces("application/json")
    public String getXml(@PathParam("userName") String userName) throws EmailException {
        List<ReservationDto> returnList = flightFacade
                .getAllReservations()
                .stream()
                .map(x -> toDto(x))
                .collect(Collectors.toList());

        return gson.toJson(returnList);
    }

    /**
     * Reserves a ticket for a specific flight and user.
     *
     * @Author: Nikolaj
     * @Date: 6/12 2015
     *
     * @param json Reservation as json string
     * @return The reserved flight as a Json String
     * @throws FlightException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String flightReservation(String json) throws FlightException {

        ReservationDto reservationDto = gson.fromJson(json, ReservationDto.class);

        Reservation reservation = toEntity(reservationDto);

        Reservation returnData = flightFacade.saveReservation(reservation, reservationDto.getFlightID(), reservationDto.getUserName());

        return gson.toJson(toDto(returnData));
    }

    /**
     * Converts a reservation to DTO.
     *
     * @Author: Nikolaj
     * @date: 6/12 2015
     *
     * @param reservation Reservation object to convert
     * @return Reservation as DTO
     */
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
                reservation.getOwner().getUserName(),
                reservation.getReserveeName(),
                reservation.getReserveeEmail(),
                reservation.getReservePhone(),
                reservation.getNumberOfSeats(),
                passengers
        );
    }

    /**
     * Converts the badly formatted json into dto.
     *
     * @Author: Nikolaj
     * @Date: 6/12 2015
     *
     * @param reservation
     * @return
     */
    private Reservation toEntity(ReservationDto reservation) {
        List<Passenger> passengers = new ArrayList<>();

        for (PassengerDto passenger : reservation.getPassengers()) {
            passengers.add(new Passenger(
                    passenger.getFirstName(),
                    passenger.getLastName()
            ));
        }

        return new Reservation(
                0,
                passengers,
                reservation.getReserveeName(),
                reservation.getReserveeEmail(),
                reservation.getReservePhone()
        );
    }
}
