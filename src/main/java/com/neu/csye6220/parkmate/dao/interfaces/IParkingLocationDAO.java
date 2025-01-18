package com.neu.csye6220.parkmate.dao.interfaces;

import com.neu.csye6220.parkmate.model.ParkingLocation;

import java.util.List;

public interface IParkingLocationDAO {
    ParkingLocation saveParkingLocation(ParkingLocation parkingLocation);
    List<ParkingLocation> findByRenterId(Integer renterId);
    List<ParkingLocation> findNearby(double lat, double lon, double radius);
    ParkingLocation findById(int id);

    void delete(ParkingLocation location);
}
