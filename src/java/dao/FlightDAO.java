/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Flight;
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import static org.eclipse.persistence.expressions.ExpressionOperator.nextDay;

/**
 *
 * @author casper
 */
public class FlightDAO extends DataManager<Flight, Integer> {
    
    public Flight getByFlightNumber(String flightNumber) {
        return (Flight) this.getManager().createNamedQuery("Flight.findFlightByFlightNumber")
           .setParameter("flightNumber", flightNumber)
           .getSingleResult();
    }
    
    public List<Flight> findFlights(String from, String to, Date date, Date nextDay) {
        // Now we want to check if we have any results in the database, by looking up
        return this.getManager().createNamedQuery("Flight.findFlights")
                .setParameter("origin", from)
                .setParameter("destination", to)
                .setParameter("theDay", date, TemporalType.DATE)
                .setParameter("theNextDay", nextDay, TemporalType.DATE)
                .getResultList();
    }
    
    public List<Flight> findFlights(String from, Date date, Date nextDay) {
        // Now we want to check if we have any results in the database, by looking up
        return this.getManager().createNamedQuery("Flight.findFlights")
                .setParameter("origin", from)
                .setParameter("theDay", date, TemporalType.DATE)
                .setParameter("theNextDay", nextDay, TemporalType.DATE)
                .getResultList();
    }
    
}
