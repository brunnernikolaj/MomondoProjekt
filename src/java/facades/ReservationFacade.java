/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import dao.DataManager;
import dtos.ReservationDto;
import entity.Passenger;
import entity.Reservation;
import exceptions.FlightException;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.Response;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.Content;
import us.monoid.web.FormData;
import us.monoid.web.Resty;
import static us.monoid.web.Resty.*;
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
    
    public JSONObject reserveExternal(ReservationDto reservation) throws IOException, JSONException{
        Resty resty = new Resty();
        
        String lol = new Gson().toJson(reservation);
        
        Content content = new Content("application/json",lol.getBytes());
        
        return resty.json("http://angularairline-plaul.rhcloud.com/api/flightreservation", content).toObject();
    }
    
    
}
