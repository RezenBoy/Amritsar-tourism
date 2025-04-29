package com.mgcfgs.amritsartourism.amritsar_tourism.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        return bookingRepository.findAvailableRooms(checkIn, checkOut);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
