/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Airport;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author casper
 */
public class AirportDAO extends DataManager<Airport, Integer> {
    
    
    /**
     * Fetches an airport by IATA code.
     * 
     * @Author: Casper Schultz
     * @Date: 6/12 2015
     * 
     * @param IATA      IATA code to lookup with
     * @return          Airport object
     */
    public Airport getAirportByIATA(String IATA) {
        Airport airport = (Airport) this.getManager().createNamedQuery("Airport.findAirportByIATA")
            .setParameter("IATAcode", IATA)
            .getSingleResult();
        
        return airport;
    }
    
    
    /**
     * Fetches an airport by city.
     * 
     * @Author: Casper Schultz
     * @Date: 6/12 2015
     * 
     * @param city      City to lookup.
     * @return          Airport as object
     */
    public List<Airport> getAirportsByCity(String city) {
        
        List<Airport> airports = this.getManager().createNamedQuery("Airport.findAirportsByCity")
            .setParameter("city", city  + "%")
            .getResultList();
        
        return airports;
    }
}
