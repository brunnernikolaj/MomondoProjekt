/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import dto.FlightDto;
import entity.FlightEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import requests.FlightRequest;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;

/**
 *
 * @author Nikolaj
 */
public class SearchEngine {

    ExecutorService threadPool;

    public SearchEngine() {
        threadPool = Executors.newFixedThreadPool(8);
    }

    private List<String> urls = Arrays.asList("http://angularairline-plaul.rhcloud.com");

    public JSONArray search(FlightRequest request) throws InterruptedException {
        try {
            Stream<SearchTask> tasks = urls.stream().map(url -> new SearchTask(url, request));

            List<Future<JSONObject>> temp = tasks.map(task -> threadPool.submit(task)).collect(Collectors.toList());

            threadPool.shutdown();
            threadPool.awaitTermination(20, TimeUnit.SECONDS);

            JSONArray temp1 = new JSONArray();
            
            for (Future<JSONObject> list : temp){
                temp1.put(list.get());
            }
            
            return temp1;

        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
