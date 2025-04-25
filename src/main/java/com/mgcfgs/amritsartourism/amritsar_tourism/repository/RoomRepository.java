package com.mgcfgs.amritsartourism.amritsar_tourism.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Custom query to find rooms by type
    List<Room> findByType(String type);

    // Custom query to find rooms by price range
    List<Room> findByPricePerNightBetween(Double min, Double max);

    // Custom query to find available rooms (assuming you have a field 'isAvailable')
    List<Room> findByAvailableTrue();

    Room findByRoomNumber(String roomNumber);

}
