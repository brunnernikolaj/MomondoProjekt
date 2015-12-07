/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.UserEntity;
import exceptions.FlightException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.PasswordHash;


/**
 *
 * @author casper
 */
public class UserDAO extends DataManager<UserEntity, String> {
    
    @Override
    public UserEntity create(UserEntity user) {
         
        try {
            user.setPassword(PasswordHash.createHash(user.getPassword()));
        
            transaction.begin();
            manager.persist(user);
            transaction.commit();

            return user;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }
    
}
