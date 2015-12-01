/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Nikolaj
 */
public class FlightDto {

    private String origin;
    private String destination;
    private Double totalPrice;
    private String flightID;
    private int numberOfSeats;
    private Date date;
    private int traveltime;

    public FlightDto(String iataFrom, String iataTo, Double price, String flightNumber, int noOfSeats, Date travelDate, int travelTime) {
        this.origin = iataFrom;
        this.destination = iataTo;
        this.totalPrice = price;
        this.flightID = flightNumber;
        this.numberOfSeats = noOfSeats;
        this.date = travelDate;
        this.traveltime = travelTime;
       
    }

    public FlightDto() {
    }

    public int getTravelTime() {
        return traveltime;
    }

    public void setTravelTime(int travelTime) {
        this.traveltime = travelTime;
    }

    public Date getTravelDate() {
        return date;
    }

    public void setTravelDate(Date travelDate) {
        this.date = travelDate;
    }

    public String getIataFrom() {
        return origin;
    }

    public void setIataFrom(String iataFrom) {
        this.origin = iataFrom;
    }

    public String getIataTo() {
        return destination;
    }

    public void setIataTo(String iataTo) {
        this.destination = iataTo;
    }

    public Double getPrice() {
        return totalPrice;
    }

    public void setPrice(Double price) {
        this.totalPrice = price;
    }

    public String getFlightNumber() {
        return flightID;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightID = flightNumber;
    }

    public int getNoOfSeats() {
        return numberOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.numberOfSeats = noOfSeats;
    }
}
