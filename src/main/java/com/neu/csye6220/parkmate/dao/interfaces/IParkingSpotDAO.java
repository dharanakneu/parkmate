package com.neu.csye6220.parkmate.dao.interfaces;

import com.neu.csye6220.parkmate.model.ParkingSpot;

import java.util.List;

public interface IParkingSpotDAO {
    void saveParkingSpot(ParkingSpot parkingSpot);
    List<ParkingSpot> findAvailableSpotsByParkingLocationId(int parkingLocationId);
    ParkingSpot findById(int id);
    List<ParkingSpot> findAllSpotsByParkingLocationId(int id);
}
