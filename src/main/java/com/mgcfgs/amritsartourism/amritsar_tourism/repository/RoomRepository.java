package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Custom query to find rooms by price range
    List<Room> findByPricePerNightBetween(Double min, Double max);

    // Custom query to find available rooms
    List<Room> findByAvailableTrue();

    // Find a room by room number
    Room findByRoomNumber(String roomNumber);

    // Find rooms by hotel ID
    List<Room> findByHotelId(Long hotelId);

    // Find available rooms for a hotel and date range
    @Query("SELECT r FROM Room r LEFT JOIN Booking b ON b.room.id = r.id " +
            "AND (b.checkIn < :checkOut AND b.checkOut > :checkIn) " +
            "WHERE r.hotel.name = :hotelName AND r.available = true AND b.id IS NULL")
    List<Room> findAvailableRooms(
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("hotelName") String hotelName);
}
