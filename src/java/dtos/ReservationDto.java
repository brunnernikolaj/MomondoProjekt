/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author Nikolaj
 */
public class ReservationDto {
    private String flightID;
    private String userName;
    
    private String ReserveeName;
    private String ReserveeEmail;
    private String ReservePhone;
    
    private int numberOfSeats;
    
    private List<PassengerDto> Passengers;

    public ReservationDto(String flightId,String username ,String ReserveeName, String ReserveeEmail, String ReserveePhone, int numberOfSeats, List<PassengerDto> Passengers) {
        this.flightID = flightId;
        this.userName = username;
        this.ReserveeName = ReserveeName;
        this.ReserveeEmail = ReserveeEmail;
        this.ReservePhone = ReserveePhone;
        this.numberOfSeats = numberOfSeats;
        this.Passengers = Passengers;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getReserveeName() {
        return ReserveeName;
    }

    public void setReserveeName(String ReserveeName) {
        this.ReserveeName = ReserveeName;
    }

    public String getReserveeEmail() {
        return ReserveeEmail;
    }

    public void setReserveeEmail(String ReserveEmail) {
        this.ReserveeEmail = ReserveEmail;
    }

    public String getReservePhone() {
        return ReservePhone;
    }

    public void setReservePhone(String ReservePhone) {
        this.ReservePhone = ReservePhone;
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
