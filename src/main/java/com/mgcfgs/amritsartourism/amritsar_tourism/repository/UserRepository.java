package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.BookingHistory;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.RegisterUser;

@Repository
public interface UserRepository extends JpaRepository<RegisterUser, Long> {
    // Automatically finds by email
    RegisterUser findByEmail(String email);

    boolean existsByEmail(String email);

    // Custom query for login (optional)
    @Query("SELECT u FROM RegisterUser u WHERE u.email = :email AND u.password = :password")
    RegisterUser registerUser(@Param("email") String email, @Param("password") String password);

    @Query("SELECT b FROM BookingHistory b WHERE b.userId = :id")
    List<BookingHistory> getBookingHistory(@Param("id") Long id);

}
