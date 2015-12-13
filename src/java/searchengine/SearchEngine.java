/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;

/**
 *
 * @author Nikolaj
 */
public class SearchEngine {

    ExecutorService threadPool;

    public SearchEngine() {
        threadPool = Executors.newFixedThreadPool(8);
    }


    private final Stream<String> urls = Stream.of("http://localhost:8080","http://angularairline-plaul.rhcloud.com");

    public <T extends AbstractSearchTask> JSONArray  search(Function<String,T> ctor) throws InterruptedException {
        
        //Create a new SearchTask for each url. Then submit each task to the threadpool.
        //Finally convert to a list because otherwise submit won't be called until after shutdown() 
        List<Future<JSONObject>> tasks = urls
                .map(ctor)
                .map(task -> threadPool.submit(task))
                .collect(Collectors.toList());

        threadPool.shutdown();
        threadPool.awaitTermination(20, TimeUnit.SECONDS);

        try {

            JSONArray flightsInfo = new JSONArray();

            //Create a json array containing all airlines and associated flights
            for (Future<JSONObject> flightList : tasks) {
                JSONObject obj = flightList.get();
                if (obj != null)
                    flightsInfo.put(obj);
            }

            return flightsInfo.;

        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SearchEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


}
