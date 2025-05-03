package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

        @Query("SELECT b FROM Booking b WHERE " +
                        "(b.checkIn <= :checkOut AND b.checkOut >= :checkIn)")
        List<Booking> findOverlappingBookings(LocalDate checkIn, LocalDate checkOut);

        @Query("SELECT b FROM Booking b WHERE b.room.hotel.id = :hotelId")
        List<Booking> findByHotelId(@Param("hotelId") Long hotelId);

}
