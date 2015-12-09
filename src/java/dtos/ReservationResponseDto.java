/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Nikolaj
 */
public class ReservationResponseDto {

    private String flightID;

    private String Origin;

    private String Destination;

    private String Date;

    private int FlightTime;

    private String ReserveeName;

    private int numberOfSeats;

    private List<PassengerDto> Passengers;

    public ReservationResponseDto(String flightID, String Origin, String Destination, String Date, int FlightTime, String ReserveeName, int numberOfSeats, List<PassengerDto> Passengers) {
        this.flightID = flightID;
        this.Origin = Origin;
        this.Destination = Destination;
        this.Date = Date;
        this.FlightTime = FlightTime;
        this.ReserveeName = ReserveeName;
        this.numberOfSeats = numberOfSeats;
        this.Passengers = Passengers;
    }
    
    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getFlightTime() {
        return FlightTime;
    }

    public void setFlightTime(int FlightTime) {
        this.FlightTime = FlightTime;
    }

    public String getReserveeName() {
        return ReserveeName;
    }

    public void setReserveeName(String ReserveeName) {
        this.ReserveeName = ReserveeName;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<PassengerDto> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<PassengerDto> Passengers) {
        this.Passengers = Passengers;
    }

}
