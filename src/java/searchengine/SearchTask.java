/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.google.gson.Gson;
import dto.FlightDTO;
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
class SearchTask implements Callable<List<FlightDTO>> {

    private FlightRequest request;
    private String url;

    public SearchTask(String websiteUrl, FlightRequest request) {
        this.request = request;
        this.url = websiteUrl;
    }

    @Override
    public List<FlightDTO> call() throws Exception {

        String apiUrl = url + request.getApiString();

        Resty r = new Resty();

        JSONArray json = (JSONArray) r.json(apiUrl).get("flights");

        List<FlightDTO> dtos = new ArrayList<>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject object = (JSONObject) json.get(i);

            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            df.setTimeZone(tz);
            Date time = df.parse((String) object.get("date"));

            dtos.add(new FlightDTO(
                    (String) object.get("origin"),
                    (String) object.get("destination"),
                    (Double) object.get("priceTotal"),
                    (String) object.get("flightId"),
                    (int) object.get("numberOfSeats"),
                    time, 
                    (int) object.get("travelTime")));
        }
        
        return dtos;
    }

}
