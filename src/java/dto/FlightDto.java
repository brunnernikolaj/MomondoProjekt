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

    private String iataFrom;

    private String iataTo;

    private Double price;

    private String flightNumber;

    private int noOfSeats;

    private Date travelDate;

    private int travelTime;

    public FlightDto(String iataFrom, String iataTo, Double price, String flightNumber, int noOfSeats, Date travelDate, int travelTime) {
        this.iataFrom = iataFrom;
        this.iataTo = iataTo;
        this.price = price;
        this.flightNumber = flightNumber;
        this.noOfSeats = noOfSeats;
        this.travelDate = travelDate;
        this.travelTime = travelTime;
       
    }

    public FlightDto() {
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public String getIataFrom() {
        return iataFrom;
    }

    public void setIataFrom(String iataFrom) {
        this.iataFrom = iataFrom;
    }

    public String getIataTo() {
        return iataTo;
    }

    public void setIataTo(String iataTo) {
        this.iataTo = iataTo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }
}
