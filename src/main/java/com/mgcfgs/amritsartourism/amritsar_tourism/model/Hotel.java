package com.mgcfgs.amritsartourism.amritsar_tourism.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "starting_price")
    private Double startingPrice;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    // Constructors
    public Hotel() {
    }

    public Hotel(String name, String location, String description, Double rating, String imagePath,
            Double startingPrice) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.imagePath = imagePath;
        this.rating = rating;
        this.startingPrice = startingPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public Double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(Double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    // Helper methods for managing the bidirectional relationship
    public void addRoom(Room room) {
        rooms.add(room);
        room.setHotel(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setHotel(null);
    }
}