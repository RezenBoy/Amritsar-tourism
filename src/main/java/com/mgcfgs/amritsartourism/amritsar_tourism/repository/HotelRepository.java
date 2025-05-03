package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // This interface will handle CRUD operations for Hotel entity
    // JpaRepository provides methods like save(), findAll(), findById(),
    // deleteById(), etc.
    // You can also define custom query methods here if needed
    List<Hotel> findByLocation(String location);

    // This method will find hotels by their location
    // Spring Data JPA will automatically implement this method based on the name
    // You can add more custom query methods as per your requirements
    List<Hotel> findByRatingGreaterThan(Double rating);

    // This method will find hotels with a rating greater than the specified value
    // Spring Data JPA will automatically implement this method based on the name
    // You can also use @Query annotation to define custom queries if needed
    @Query("SELECT h FROM Hotel h WHERE h.rating > ?1")
    List<Hotel> findHotelsWithRatingGreaterThan(Double rating);
    // This method will find hotels with a rating greater than the specified value

}
