/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import requests.FlightRequest;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

/**
 *
 * @author Nikolaj
 */
public class SearchTaskWithDestination extends AbstractSearchTask{

    public SearchTaskWithDestination(String url, FlightRequest request) {
        super(url, request);
    }

    @Override
    public JSONObject call() throws Exception {
         String apiUrl = url + request.getApiStringWithDestination();
        Resty r = new Resty();
        return  r.json(apiUrl).object();
        
    }
    
}
