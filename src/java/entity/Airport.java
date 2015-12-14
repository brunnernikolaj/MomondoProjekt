/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Airport entity.
 * 
 * @Author: Casper Schultz
 */
@Entity

@Table(name = "AIRPORTS")
@NamedQueries({
    @NamedQuery(name = "Airport.findAirportByIATA", query = "SELECT p FROM Airport p WHERE p.IATAcode = :IATAcode"),
    @NamedQuery(name = "Airport.findAirportByCity", query = "SELECT p FROM Airport p WHERE p.city = :city"),
    @NamedQuery(name = "Airport.findAirportsByName", query = "SELECT p FROM Airport p WHERE p.name LIKE :name"),
    @NamedQuery(name = "Airport.findAirportByName", query = "SELECT p FROM Airport p WHERE p.name = :name"),
    @NamedQuery(name = "Airport.findAirportsByCity", query = "SELECT p FROM Airport p WHERE p.city LIKE :city")})
public class Airport {
    
    @Id
    @Column(name = "AIRPORT_ID")
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "IATA_CODE")
    private String IATAcode;
    @Column(name = "ICAO_CODE")
    private String ICAOcode;
    @Column(name = "LATITUDE")
    private String latitude;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "ALTITUDE")
    private String altitude;
    @Column(name = "TIMEZONE")
    private String timezone;

    public Airport() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATAcode() {
        return IATAcode;
    }

    public void setIATAcode(String IATAcode) {
        this.IATAcode = IATAcode;
    }

    public String getICAOcode() {
        return ICAOcode;
    }

    public void setICAOcode(String ICAOcode) {
        this.ICAOcode = ICAOcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
