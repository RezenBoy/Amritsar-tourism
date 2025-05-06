package com.mgcfgs.amritsartourism.amritsar_tourism.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.BookingRepository;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.RoomRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, String hotelName) {
        return roomRepository.findAvailableRooms(checkIn, checkOut, hotelName);
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> findByHotelId(Long hotelId) {
        // Find all rooms for the hotel
        List<Room> rooms = roomRepository.findByHotelId(hotelId);
        // Find all bookings and filter by rooms
        List<Long> roomIds = rooms.stream().map(Room::getId).collect(Collectors.toList());
        return bookingRepository.findAll().stream()
                .filter(booking -> roomIds.contains(booking.getRoom().getId()))
                .collect(Collectors.toList());
    }

    public void deleteBooking(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new IllegalArgumentException("Booking with ID " + id + " not found");
        }
        bookingRepository.deleteById(id);
    }

    public Page<Booking> getBookings(Pageable pageable, String status) {
        if (status == null || status.isEmpty()) {
            return bookingRepository.findAll(pageable);
        } else {
            return bookingRepository.findByStatus(status.toUpperCase(), pageable);
        }
    }
}
