package facades;

import entity.Airport;
import java.util.List;
import javax.persistence.NoResultException;
import utility.AirportScraper;

/**
 * Airport facade.
 * 
 * This class handles communication between the system and the 
 * database part about airports.
 * 
 * @author casper
 */
public class AirportFacade extends DataManager<Airport, Integer> {
    
    boolean airportsFetched = false;
    
    /**
     * Airport instance.
     */
    public static AirportFacade instance = null;
    
    /**
     * Constructor.
     * 
     * Fetches airports on startup.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     */
    private AirportFacade() {
        this.saveAirports();
    }
    
    /**
     * Singleton.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @return  instance of current class. 
     */
    public static AirportFacade getInstance() {
        if (instance == null) {
            instance = new AirportFacade();
        }
        return instance;
    }
    
    /**
     * Lookup an airport by IATA code.
     * 
     * Looks up an airport by iata code and returns the
     * result as an airport object with full airport info.
     * 
     * @author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @param IATA      IATA code as string to lookup
     * @return          Airport entity if found / otherwise null
     */
    public Airport getAirportByIATA(String IATA) {
        
        try {
            
            Airport airport = (Airport) manager.createNamedQuery("Airport.findAirportByIATA")
            .setParameter("IATAcode", IATA)
            .getSingleResult();
            
            return airport;
            
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
    /**
     * Saves airports in the database.
     * 
     * Fetches the airport by using the web scraper and then saves 
     * the results in the database.
     * 
     * @author: Casper Schultz
     * @Date: 3/12 2015
     */
    private void saveAirports() {
        
        try {
            manager.createNamedQuery("Airport.findAirportByIATA")
            .setParameter("IATAcode", "CPH")
            .getSingleResult();
        } catch (NoResultException e) {
            deleteAll("AIRPORTS");
            List<Airport> airports = AirportScraper.fetchAiportData();
            for (Airport a : airports)
                System.out.println(a.getCity());
            createFromList(airports);
        }
    }
}
