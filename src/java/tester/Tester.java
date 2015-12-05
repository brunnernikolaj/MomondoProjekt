/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import entity.Flight;
import facades.FlightFacade;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import requests.FlightRequest;
import searchengine.SearchEngine;
import us.monoid.json.JSONArray;

/**
 *
 * @author Nikolaj
 */
public class Tester {
    public static void main(String[] args) throws InterruptedException {
        FlightFacade facade = new  FlightFacade();
        
        facade.create(new Flight());
        
        System.out.println("lol");
    }
}
