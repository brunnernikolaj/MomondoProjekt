/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reservation;
import entity.WebsiteUrl;
import java.util.List;

/**
 *
 * @author Nikolaj
 */
public class WebsiteDAO extends DataManager<WebsiteUrl, Integer>{

    public WebsiteDAO() {
        create(new WebsiteUrl("http://angularairline-plaul.rhcloud.com", "AngularJS Airline"));
        create(new WebsiteUrl("http://localhost:8080", "Just Fly"));
    }
      
    
     public List<WebsiteUrl> getAll(){
        return manager.createNamedQuery("WebsiteUrl.findAll")
                .getResultList();
    }
}
