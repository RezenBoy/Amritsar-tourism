package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email); // Method to find a user by email
    boolean existsByEmail(String email); // Method to check if a user exists by email
}
