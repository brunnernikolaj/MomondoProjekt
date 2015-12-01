package entity;

import java.sql.Time;
import java.util.Date;

/**
 *  Flight entity.
 * 
 *  This class represents a given flight that flies from / to a destination.
 * 
 *  @author casper
 */
public class FlightEntity {
    private String iataFrom;
	
	private String iataTo;
	
	private String flightDeparture;
	
	private Double price;
	
	private String flightNumber;
	
	private int noOfSeats;
	
	private Date travelDate;
	
	private Time travelTime;
	
	private Date departureDate;
        
        public FlightEntity() {}
        
	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Time getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Time travelTime) {
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

	public String getFlightDeparture() {
		return flightDeparture;
	}

	public void setFlightDeparture(String flightDeparture) {
		this.flightDeparture = flightDeparture;
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
	
	public String toString() {
		String result = "IATA Code From = "+this.iataFrom+", IATA Code To = "+this.iataTo+", Flight Departure = "+this.flightDeparture+
				", price = "+this.price+", Flight Number = "+this.flightNumber+", Number Of Seats = "+this.noOfSeats+", travel Date = "+
				this.travelDate+", Travel Time = "+this.travelTime+", Departure Date = "+this.departureDate;
		return result;
	}
}
