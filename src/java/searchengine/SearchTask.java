/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.google.gson.Gson;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import requests.FlightRequest;
import us.monoid.web.JSONResource;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.Resty;

/**
 *
 * @author Nikolaj
 */

class SearchTask implements Callable<JSONObject> {

    private FlightRequest request;
    private String url;

    public SearchTask(String websiteUrl, FlightRequest request) {
        this.request = request;
        this.url = websiteUrl;
    }

    @Override

    public JSONObject call() throws Exception {

        String apiUrl = url + request.getApiString();

        Resty r = new Resty();

        return  r.json(apiUrl).object();
    }

}
