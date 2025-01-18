package com.neu.csye6220.parkmate.service.interfaces;

import java.util.List;

import com.neu.csye6220.parkmate.dto.ParkingLocationDTO;
import com.neu.csye6220.parkmate.dto.ParkingSpotUpdateRequest;
import com.neu.csye6220.parkmate.model.ParkingLocation;
import com.neu.csye6220.parkmate.model.ParkingSpot;
import com.neu.csye6220.parkmate.model.Renter;

public interface IParkingService {
    List<ParkingLocation> findNearbyParkingLocations(double lat, double lon, double radius);
    ParkingLocation getParkingLocationById(int id);
    List<ParkingSpot> getAvailableSpotsByParkingLocationId(int parkingLocationId);
    ParkingSpot getParkingSpotById(int selectedSpot);
    void saveParkingLocationAndSpots(ParkingLocationDTO parkingLocationDTO, Renter currentRenter);
    List<ParkingLocationDTO> getParkingLocationByRenterId(Integer renterId);
    void deleteParkingLocation(int locationId);
    List<ParkingSpot> getAllSpotsByParkingLocationId(int locationId);
    void updateParkingSpots(List<ParkingSpotUpdateRequest> updates);
}
