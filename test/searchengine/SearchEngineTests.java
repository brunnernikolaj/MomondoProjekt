/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.GregorianCalendar;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import requests.FlightRequest;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;

/**
 *
 * @author Nikolaj
 */
public class SearchEngineTests {
    
    SearchEngine searchEngine = new SearchEngine();
    
    public SearchEngineTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void normalSearchTest() throws InterruptedException, JSONException {
         GregorianCalendar calender = new GregorianCalendar(2016, 01, 01);
         
         FlightRequest request = new FlightRequest("CPH", "SXF", calender.getTime(), 1);
         
         JSONArray list = searchEngine.search(website -> new SearchTask(website,request));
         
         String airline = list.getJSONObject(0).getString("airline");
         
         assertNotNull(airline);
     }
     
     @Test
     public void destinationSearchTest() throws InterruptedException, JSONException {
         GregorianCalendar calender = new GregorianCalendar(2016, 01, 01);
         
         FlightRequest request = new FlightRequest("CPH", "SXF", calender.getTime(), 1);
         
         JSONArray list = searchEngine.search(website -> new SearchTaskWithDestination(website,request));
         
         String airline = list.getJSONObject(0).getString("airline");
         
         assertNotNull(airline);
     }
}
