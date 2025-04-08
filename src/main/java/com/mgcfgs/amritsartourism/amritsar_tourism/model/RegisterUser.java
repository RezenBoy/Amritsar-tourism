package com.mgcfgs.amritsartourism.amritsar_tourism.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class RegisterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    
    private String confirm_password;
    

    public RegisterUser() {
    }

    public RegisterUser(String name, String email, String password,String confirm_password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm_password= confirm_password;
        
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getConfirm_password()
    {
        return confirm_password;
    }
    public void setConfirm_password(String confirm_password){
        this.confirm_password=confirm_password;
    }

}
