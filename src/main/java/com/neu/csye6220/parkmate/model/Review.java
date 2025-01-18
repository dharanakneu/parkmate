package com.neu.csye6220.parkmate.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private double rating;

    @ManyToOne
    @JoinColumn(name = "rentee_id", nullable = false)
    private Rentee rentee;

    @ManyToOne
    @JoinColumn(name = "parking_location_id", nullable = false)
    private ParkingLocation parkingLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Rentee getRentee() {
        return rentee;
    }

    public void setRentee(Rentee rentee) {
        this.rentee = rentee;
    }

    public ParkingLocation getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(ParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;
    }
}
