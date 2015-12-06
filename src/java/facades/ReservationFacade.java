/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Passenger;
import entity.Reservation;
import exceptions.FlightException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nikolaj
 */
public class ReservationFacade extends DataManager<Reservation, Integer> {

    public Reservation saveReservation(Reservation reservation) throws FlightException {

        if (reservation.getPassengers().size() <= 0) {
            throw new FlightException("An error occured and we could not procedd with the reservation", Response.Status.INTERNAL_SERVER_ERROR, 4);
        }

        create(reservation);

        // We also store the reservation with each passenger.
        for (Passenger passenger : reservation.getPassengers()) {
            passenger.addReservation(reservation);
        }

        update(reservation);

        return reservation;
    }
}
