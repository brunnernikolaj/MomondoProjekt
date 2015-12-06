package entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity for storing a passenger.
 * 
 * @Author: Casper Schultz
 * @Date: 4/12 2015
 */
@Entity
@Table(name = "PASSENGERS")
public class Passenger {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PASSENGER_ID")
    private int id;
    
    @Column(name = "FIRSTNAME")
    private String firstname;
    
    @Column(name = "LASTNAME")
    private String lastname;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "EMAIL")
    private String email;
    
    @ManyToMany(mappedBy = "passengers", cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVATION_ID")
    private List<Reservation> reservations;
    
    public Passenger() {
        reservations = new ArrayList<>();
    }

    public Passenger(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    
    
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    
    public void removeReservation(Reservation reservation) {
        if (reservations.contains(reservation)) 
            reservations.remove(reservation);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
