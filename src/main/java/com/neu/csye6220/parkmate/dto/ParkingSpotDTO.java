package com.neu.csye6220.parkmate.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public class ParkingSpotDTO {

    @NotBlank(message = "Spot number is required")
    private String spotNumber;

    @NotBlank(message = "Spot type is required")
    private String spotType;

    private boolean isAvailable;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price per hour must be greater than 0")
    private Double pricePerHour = 0.0;

    public String getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(String spotNumber) {
        this.spotNumber = spotNumber;
    }

    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        this.spotType = spotType;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
