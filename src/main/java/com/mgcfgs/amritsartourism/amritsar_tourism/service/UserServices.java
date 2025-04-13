package com.mgcfgs.amritsartourism.amritsar_tourism.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.UserRepository;

@Service
public class UserServices {
    // This class is responsible for user-related services, such as registration and
    // login.
    @Autowired
    private UserRepository userRepository;

    public void saveUser(RegisterUser user) {
        userRepository.save(user);
    }

    public RegisterUser findByEmail(String email) {
        // This method checks if the user already exists in the database by email.
        // If the user exists, return the user object.
        // Otherwise, return null or throw an exception.
        RegisterUser existingUser = userRepository.findByEmail(email);
        return existingUser;
    }

    public RegisterUser loginUser(String email, String password) {
        // This method checks if the user exists and if the password is correct.
        // If the user exists and the password is correct, return the user object.
        // Otherwise, return null or throw an exception.
        RegisterUser registerUser = userRepository.registerUser(email, password);
        return registerUser;

    }
}
