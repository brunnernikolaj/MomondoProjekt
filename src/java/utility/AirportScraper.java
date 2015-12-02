/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import entity.AirportEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles airports.
 * 
 * Fetches airports from an external list and stores them in the database.
 * Makes it possible to lookup an airport with all its data, including 
 * latitude / longitude. 
 * 
 * @author casper
 */
public class AirportScraper {
    
    // We just fetch them manually, by running this class
    public static void main(String[] args) {
        List<AirportEntity> airports = new AirportScraper().fetchAiportData();
        
        for (AirportEntity airport : airports) 
            System.out.println("The aiport with IATA: " + airport.getIATAcode() + "was fetched");
    }
    
    /**
     * Scrapes airport data.
     * 
     * Scrapes a csv file that contains data about all the aiports.
     * 
     * @Author: Casper Schultz
     * @Date: 2/12 2015
     * 
     * @return List of AirportEntities 
     */
    public static List<AirportEntity> fetchAiportData() {
        
        BufferedReader in = null;
        List<AirportEntity> airports = new ArrayList();
        
        try {
            
            URL oracle = new URL("https://raw.githubusercontent.com/jpatokal/openflights/master/data/airports.dat");
            in = new BufferedReader(new InputStreamReader(oracle.openStream()));
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {   
                String[] values = inputLine.split(",");
                
                AirportEntity airport = new AirportEntity();
                airport.setId(Integer.parseInt(values[0]));
                airport.setName(values[1]);
                airport.setCity(values[2]);
                airport.setCountry(values[3]);
                airport.setIATAcode(values[4]);
                airport.setICAOcode(values[5]);
                airport.setLatitude(values[6]);
                airport.setLongitude(values[7]);
                airport.setAltitude(values[8]);
                airport.setTimezone(values[9]);
                
                airports.add(airport);
            }
            
            in.close();
            
        } catch (IOException ex) {
            Logger.getLogger(AirportScraper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(AirportScraper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return airports;
    }
}
