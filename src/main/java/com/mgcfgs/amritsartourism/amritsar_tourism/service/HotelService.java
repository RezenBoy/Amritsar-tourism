package com.mgcfgs.amritsartourism.amritsar_tourism.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Hotel;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.HotelRepository;

@Service
public class HotelService {

    private final HotelRepository hotelRepository; // Repository for Hotel entity

    HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }
    // This service will handle hotel-related operations
    // For example, fetching all hotels, adding a new hotel, etc.

    // Example method to fetch all hotels (to be implemented)
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // Example method to add a new hotel (to be implemented)
    public void addHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }
    // Example method to delete a hotel (to be implemented)

    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId).orElse(null);
    }
}
