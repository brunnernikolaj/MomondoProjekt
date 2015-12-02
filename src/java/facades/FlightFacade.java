/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Flight;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;
import org.joda.time.Period;
import static utility.WebScraper.getListOfFlights;

/**
 *
 * @author casper
 */
public class FlightFacade extends DataManager<Flight, Integer> {
    
    public static FlightFacade instance = null;
    
    private FlightFacade() {
    }
    
    public static FlightFacade getInstance() {
        if (instance == null) {
            instance = new FlightFacade();
        }
        return instance;
    }
    
    /**
     * Returns internal flights.
     * 
     * Fetches the internal flights, for a specific from/to destination
     * as IATA code on a given date. If there are no flights in the database,
     * we try to fetch them externally
     * 
     * @author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param from              Origin as IATA code
     * @param to                Destination as IATA code
     * @param date              The date as object to search for
     * @param seats             The number of seats required
     * @return                  List of flight objects that match the criteria
     * @throws ParseException 
     */
    public List<Flight> getJFFlights(String from, String to, String date, int seats) throws ParseException {
        
        List<Flight> flights;
        DateTime dt = new DateTime(date);
        Date nextDay = dt.plus(Period.days(1)).toDate();
        
        // Now we want to check if we have any results in the database, by looking up
        flights = manager.createNamedQuery("FlightEntity.findFlights")
        .setParameter("origin", from)
        .setParameter("destination", to)
        .setParameter("theDay", dt.toDate(), TemporalType.DATE)
        .setParameter("theNextDay", nextDay, TemporalType.DATE)
        .getResultList();
        
        System.out.println("Today: " + dt.toDate() + " - Tommorow: " + nextDay);
        
        // If no flights where found, we try to lookup the flights at Norweigian
        if (flights == null || flights.size() < 1) {
            
            System.out.println("Fetching from Norweigian");
            
            try { 
                flights = getFlightsFromNorweigian(from, to, dt);
                this.createFromList(flights);
            } catch (ParseException | IOException e) {
                System.out.println(e.getStackTrace());
            } 
        } 
        
        // Return results
        return flights;
    }
    
    
    /**
     * Fetches flights from Norwegian. 
     * 
     * Uses the web-scraper to fetch flights externally from a given
     * origin to a destination on a given date.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param from              The origin as IATA code
     * @param to                The destination as IATA code
     * @param date              The date as a date object
     * @return                  ArrayList containing flight objects
     * @throws ParseException
     * @throws IOException 
     */
    private List<Flight> getFlightsFromNorweigian(String from, String to, DateTime date) throws ParseException, IOException {
        System.out.println("Fetching from norweigian");
        
        // We need to get the date and format it a bit
        DecimalFormat df = new DecimalFormat("00");
        String day = "" + df.format(date.dayOfMonth().get());
        String month = "" + df.format(date.monthOfYear().get());
        String year = "" + date.year().getAsText();
        
        String url = "http://www.norwegian.com/uk/booking/flight-tickets/select-flight/?D_City=" + from + "&A_City=" + to + "&TripType=1&D_Day=" + day + "&D_Month=" + year + month + "&D_SelectedDay=" + day + "&R_Day=" + day + "&R_Month=" + year + month + "&R_SelectedDay=" + day + "&CurrencyCode=EUR";
        return getListOfFlights(url);
    }
}