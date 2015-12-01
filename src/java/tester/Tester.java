/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import dto.FlightDTO;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import requests.FlightRequest;
import searchengine.SearchEngine;

/**
 *
 * @author Nikolaj
 */
public class Tester {
    public static void main(String[] args) throws InterruptedException {
        SearchEngine search = new SearchEngine();
        
        Calendar calendar = new GregorianCalendar(2016, 1, 2);
        Date date =  calendar.getTime();
        
        FlightRequest request = new FlightRequest("CPH", "ABE", date, 1);
        
        List<FlightDTO> list = search.search(request);
        
        System.out.println("lol");
    }
}
