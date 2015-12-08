package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User entity.
 * 
 * @Author: Casper Schultz
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    
    @Column(name = "PASSWORD")
    private String password; 
    
    @Id
    @Column(name = "USERNAME")
    private String userName;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @ElementCollection
    @CollectionTable(name = "USER_ROLES")
    List<String> roles = new ArrayList();

    public User() {
    }

    
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password, List<String> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public User(String password, String userName, String email, String phone, String firstName, String lastName) {
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void AddRole(String role) {
        roles.add(role);
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
