/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Reservation;
import java.util.List;

/**
 *
 * @author casper
 */
public class ReservationDAO extends DataManager<Reservation, Integer> {
    public List<Reservation> getAll(){
        return manager.createNamedQuery("Reservation.findAll")
                .getResultList();
    }
    
    public List<Reservation> getAllByUser(String userName){
        return manager.createNamedQuery("Reservation.findAllByUser")
                .setParameter("userName", userName)
                .getResultList();
    }
}
