/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Airport;

/**
 *
 * @author casper
 */
public class AirportDAO extends DataManager<Airport, Integer> {
    
    
    public Airport getAirportByIATA(String IATA) {
        Airport airport = (Airport) this.getManager().createNamedQuery("Airport.findAirportByIATA")
            .setParameter("IATAcode", IATA)
            .getSingleResult();
        
        return airport;
    }
    
}
