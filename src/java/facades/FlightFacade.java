package facades;

import entity.Flight;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import org.joda.time.DateTime;
import org.joda.time.Period;
import static utility.WebScraper.getListOfFlights;

/**
 * Flight facade class.
 * 
 * This class handles communication between our rest API and the
 * data concerning the flights. 
 * 
 * Most queries are handled by the generic DataManager and the rest is
 * handled here, mostly with named queries made in the entity classes.
 * 
 * @author casper
 * @Date: 1/12 2015
 */
public class FlightFacade extends DataManager<Flight, Integer> {
    
    /**
     * Instance of self. 
     * 
     * Instantiated as null as default.
     */
    public static FlightFacade instance = null;
    
    /**
     * Private constructor.
     */
    private FlightFacade() {
    }
    
    /**
     * Singleton facade.
     * 
     * This class is used as a singleton. This could have some performance 
     * issues, but for now while we aren't sure, we will use it like this.
     * 
     * @author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @return instance as a singleton 
     */
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
        flights = manager.createNamedQuery("Flight.findFlights")
        .setParameter("origin", from)
        .setParameter("destination", to)
        .setParameter("theDay", dt.toDate(), TemporalType.DATE)
        .setParameter("theNextDay", nextDay, TemporalType.DATE)
        .getResultList();
        
        // If no flights where found, we try to lookup the flights at Norweigian
        if (flights == null || flights.size() < 1) {
            
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