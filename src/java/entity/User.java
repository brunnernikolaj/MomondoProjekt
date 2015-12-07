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

    public void AddRole(String role) {
        roles.add(role);
    }

    public List<String> getRoles() {
        return roles;
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
