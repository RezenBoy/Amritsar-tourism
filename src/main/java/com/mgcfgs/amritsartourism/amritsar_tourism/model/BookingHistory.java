package com.mgcfgs.amritsartourism.amritsar_tourism.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User details
    private Long userId;
    private String userName;
    private String userEmail;

    // Hotel details
    private Long hotelId;
    private String hotelName;

    // Room details
    private String roomId;
    private String roomNumber;
    private Long adultCount;
    private Long childCount;

    // Booking details
    private Long bookingId;
    private String bookingDate;
    private String checkInDate;
    private String checkOutDate;

    public BookingHistory() {
    }

    public BookingHistory(Long userId, String userName, String userEmail, Long hotelId, String hotelName, String roomId,
            String roomNumber, Long bookingId, String bookingDate, String checkInDate, String checkOutDate) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Long adultCount) {
        this.adultCount = adultCount;
    }

    public Long getChildCount() {
        return childCount;
    }

    public void setChildCount(Long childCount) {
        this.childCount = childCount;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "BookingHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", roomId='" + roomId + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", adultCount=" + adultCount +
                ", childCount=" + childCount +
                ", bookingId=" + bookingId +
                ", bookingDate='" + bookingDate + '\'' +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                '}';
    }

}
