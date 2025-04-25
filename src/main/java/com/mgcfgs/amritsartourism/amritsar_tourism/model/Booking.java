package com.mgcfgs.amritsartourism.amritsar_tourism.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who made the booking
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private RegisterUser user;

    // Room that was booked
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "status", nullable = false)
    private String status; // e.g. Confirmed, Cancelled, Completed

    // --- Constructors ---
    public Booking() {}

    public Booking(RegisterUser user, Room room, LocalDate checkIn, LocalDate checkOut, LocalDateTime bookingDate, String status) {
        this.user = user;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public RegisterUser getUser() {
        return user;
    }

    public void setUser(RegisterUser user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

