package com.mgcfgs.amritsartourism.amritsar_tourism.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "user")
public class RegisterUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotEmpty(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Transient // Do not persist confirm_password in DB
    // @NotEmpty(message = "Please confirm your password")
    private String confirm_password;

    private String role = "USER"; // Default role

    public RegisterUser() {
    }

    public RegisterUser(String name, String email, String password, String confirm_password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {

        return "RegisterUser [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
                + ", confirm_password=" + confirm_password + ", role=" + role + "]";
    }

}

// Query to Add Admin User to Database:

// INSERT INTO user (name, email, password, role)
// VALUES ('admin', 'admin@mail.com', 'admin123', 'ADMIN');