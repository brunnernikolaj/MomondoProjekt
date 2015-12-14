/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.concurrent.Callable;
import requests.FlightRequest;
import us.monoid.json.JSONObject;

/**
 * This class acts as a base class for all SearchTasks.
 * 
 * @author Nikolaj
 */
public abstract class AbstractSearchTask implements Callable<JSONObject>{
    
    protected FlightRequest request;
    protected String url;

    /**
     * 
     * @param url
     * @param request 
     */
    public AbstractSearchTask(String url,FlightRequest request) {
        this.request = request;
        this.url = url;
    }  
}
