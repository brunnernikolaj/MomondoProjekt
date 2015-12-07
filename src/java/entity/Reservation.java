/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Reservation entity.
 * 
 * @Author: Casper Schultz
 * @Date: 4/12 2015
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Reservation.findAllReservation", query = "SELECT p FROM Reservation p")
})
@Table(name = "RESERVATIONS")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private int id;
    
    @Column(name = "PRICE")
    private double price;
    
    @ManyToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "PASSENGER_ID")
    private List<Passenger> passengers;
    
    @JoinColumn(name = "FLIGHT")
    private Flight flight;
    
    private String ReserveeName;
    private String ReserveeEmail;
    private String ReservePhone;
    private int numberOfSeats;
    
    public Reservation() {
        passengers = new ArrayList();
    }

    public Reservation(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Reservation(double price, List<Passenger> passengers, String ReserveeName, String ReserveeEmail, String ReservePhone) {
        this.price = price;
        this.passengers = passengers;
        this.ReserveeName = ReserveeName;
        this.ReserveeEmail = ReserveeEmail;
        this.ReservePhone = ReservePhone;
    }
    
    
    
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }
    
    public void removeReservation(Passenger passenger) {
        if (passengers.contains(passenger)) 
            passengers.remove(passenger);
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

    public void setReserveeEmail(String ReserveeEmail) {
        this.ReserveeEmail = ReserveeEmail;
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

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
