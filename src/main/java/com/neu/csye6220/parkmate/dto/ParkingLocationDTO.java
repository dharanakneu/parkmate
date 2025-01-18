package com.neu.csye6220.parkmate.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;


public class ParkingLocationDTO {

    private int id;

    @NotBlank(message = "Street cannot be blank")
    @Size(min = 1, max = 255, message = "Street must be between 1 and 255 characters")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @Size(min = 1, max = 255, message = "City must be between 1 and 255 characters")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Size(min = 1, max = 255, message = "State must be between 1 and 255 characters")
    private String state;

    @NotBlank(message = "Postal Code cannot be blank")
    @Size(min = 5, max = 10, message = "Postal Code must be between 5 and 10 characters")
    @Pattern(regexp = "^[0-9]+$", message = "Postal Code must be numeric")
    private String postalCode;

    @NotBlank(message = "Country cannot be blank")
    @Size(min = 1, max = 255, message = "Country must be between 1 and 255 characters")
    private String country;

    @NotNull(message = "Parking spots cannot be null.")
    @Size(min = 1, message = "At least one parking spot must be provided.")
    @Valid
    private List<ParkingSpotDTO> parkingSpots;

    @NotNull(message = "Image cannot be blank")
    private MultipartFile image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<ParkingSpotDTO> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpotDTO> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
