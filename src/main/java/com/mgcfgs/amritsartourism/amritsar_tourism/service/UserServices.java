package com.mgcfgs.amritsartourism.amritsar_tourism.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.UserRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    public void updateUserProfile(Long userId, String name, String email) {
        RegisterUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Validate email uniqueness if changed
        if (!user.getEmail().equals(email)) {
            if (userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email " + email + " is already in use");
            }
        }
        
        user.setName(name);
        user.setEmail(email);
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public RegisterUser getUserById(Long id) {
        RegisterUser user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return user;
        // return userRepository.findById(id)
        //         .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    public List<RegisterUser> getAllUsers() {
        // This method retrieves all users from the database.
        return userRepository.findAll();
    }

    public void deleteUser(RegisterUser user) {
        // This method deletes a user from the database.
        userRepository.delete(user);
    }
    public RegisterUser getUserByEmail(String email) {
        // This method retrieves a user by their email address.
        return userRepository.findByEmail(email);
    }

    public List<RegisterUser> findAll() {
        return userRepository.findAll();
    }

}
