package entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * Flight entity.
 *
 * This class represents a given flight that flies from / to a destination.
 *
 * @author casper
 */
@Entity
@Table(name = "FLIGHTS")
@NamedQueries({
    @NamedQuery(name = "Flight.findFlights", query = "SELECT p FROM Flight p WHERE p.origin = :origin AND p.destination = :destination AND p.date >= :theDay AND p.date < :theNextDay"),
    @NamedQuery(name = "Flight.findFlightsFrom", query = "SELECT p FROM Flight p WHERE p.origin = :origin AND p.date >= :theDay AND p.date < :theNextDay"),
    @NamedQuery(name = "Flight.findFlightByFlightNumber", query = "SELECT p FROM Flight p WHERE p.flightNumber = :flightNumber")})
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLIGHT_ID")
    int id;

    @Column(name = "ORIGIN")
    private String origin;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "FLIGHT_NUMBER")
    private String flightNumber;

    @Column(name = "NUMBER_OF_SEATS")
    private int noOfSeats;

    @Column(name = "TRAVELTIME")
    private int travelTime;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "TRAVELDATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    public Flight() {
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
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
        String result = "IATA Code From = " + this.origin + ", IATA Code To = " + this.destination
                + ", price = " + this.price + ", Flight Number = " + this.flightNumber + ", Number Of Seats = " + this.noOfSeats + ", travel Date = "
                + this.date + ", Travel Time = " + this.travelTime;
        return result;
    }
}
