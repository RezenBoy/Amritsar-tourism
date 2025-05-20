package com.mgcfgs.amritsartourism.amritsar_tourism.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mgcfgs.amritsartourism.amritsar_tourism.model.Booking;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.BookingHistory;
import com.mgcfgs.amritsartourism.amritsar_tourism.model.Room;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.BookingHistoryRepository;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.BookingRepository;
import com.mgcfgs.amritsartourism.amritsar_tourism.repository.RoomRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

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

    public void saveBookingHistory(Booking booking) {
        BookingHistory bookingHistory = new BookingHistory();
        bookingHistory.setUserId(booking.getUser().getId());
        bookingHistory.setUserName(booking.getUser().getName());
        bookingHistory.setUserEmail(booking.getUser().getEmail());
        bookingHistory.setAdultCount(booking.getAdultsCount());
        bookingHistory.setChildCount(booking.getChildrenCount());
        bookingHistory.setBookingId(booking.getId());
        // bookingHistory.setHotelId(booking.getHotelId());
        bookingHistory.setHotelName(booking.getHotelName());
        bookingHistory.setRoomId(booking.getRoom().getId());
        bookingHistory.setRoomNumber(booking.getRoom().getRoomNumber());
        bookingHistory.setBookingDate(LocalDate.now().toString());
        bookingHistory.setCheckInDate(booking.getCheckIn().toString());
        bookingHistory.setCheckOutDate(booking.getCheckOut().toString());

        bookingHistoryRepository.save(bookingHistory);
    }
}
