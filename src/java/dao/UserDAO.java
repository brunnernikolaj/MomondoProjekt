/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.User;
import exceptions.FlightException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.PasswordHash;

/**
 *
 * @author casper
 */
public class UserDAO extends DataManager<User, String> {

    @Override
    public User create(User user) {

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

    public List<User> getAll() {
        return manager.createNamedQuery("User.findAll")
                .getResultList();
    }

}
