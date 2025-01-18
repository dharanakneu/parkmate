package com.neu.csye6220.parkmate.service;

import com.neu.csye6220.parkmate.dao.interfaces.IParkingLocationDAO;
import com.neu.csye6220.parkmate.dao.interfaces.IParkingSpotDAO;
import com.neu.csye6220.parkmate.dto.ParkingLocationDTO;
import com.neu.csye6220.parkmate.dto.ParkingSpotDTO;
import com.neu.csye6220.parkmate.dto.ParkingSpotUpdateRequest;
import com.neu.csye6220.parkmate.model.ParkingLocation;
import com.neu.csye6220.parkmate.model.ParkingSpot;
import com.neu.csye6220.parkmate.model.Renter;
import com.neu.csye6220.parkmate.service.interfaces.IParkingService;
import com.neu.csye6220.parkmate.service.external.GeocodingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingService implements IParkingService{

    @Autowired
    private IParkingLocationDAO parkingLocationDAO;

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private IParkingSpotDAO parkingSpotDAO;

    private static final String UPLOAD_DIR = "uploads/";

    public void saveParkingLocationAndSpots(ParkingLocationDTO parkingLocationDTO, Renter renter) {
        
        ParkingLocation parkingLocation = new ParkingLocation();
        parkingLocation.setStreet(parkingLocationDTO.getStreet());
        parkingLocation.setCity(parkingLocationDTO.getCity());
        parkingLocation.setState(parkingLocationDTO.getState());
        parkingLocation.setPostalCode(parkingLocationDTO.getPostalCode());
        parkingLocation.setCountry(parkingLocationDTO.getCountry());

        parkingLocation.setRenter(renter);

        // Get latitude and longitude using Geocoding Service
        geocodingService.getCoordinates(parkingLocation);

        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (ParkingSpotDTO spotDTO : parkingLocationDTO.getParkingSpots()) {
            ParkingSpot parkingSpot = new ParkingSpot();
            parkingSpot.setSpotNumber(spotDTO.getSpotNumber());
            parkingSpot.setSpotType(spotDTO.getSpotType());
            parkingSpot.setIsAvailable(spotDTO.getIsAvailable());
            parkingSpot.setPricePerHour(spotDTO.getPricePerHour());
            parkingSpot.setParkingLocation(parkingLocation);

            parkingSpots.add(parkingSpot);
        }
        
        ParkingLocation savedParkingLocation = parkingLocationDAO.saveParkingLocation(parkingLocation);

        MultipartFile image = parkingLocationDTO.getImage();

        if (image != null && !image.isEmpty()) {
            saveImage(image, String.valueOf(savedParkingLocation.getId()));
        }

        for (ParkingSpot spot : parkingSpots) {
            spot.setParkingLocation(parkingLocation);
            parkingSpotDAO.saveParkingSpot(spot);
        }
    }

    @Override
    public List<ParkingLocationDTO> getParkingLocationByRenterId(Integer renterId) {

        List<ParkingLocation> parkingLocations = parkingLocationDAO.findByRenterId(renterId);
        List<ParkingLocationDTO> parkingLocationDTOS = new ArrayList<>();
        for (ParkingLocation parkingLocation : parkingLocations) {
            ParkingLocationDTO parkingLocationDTO = new ParkingLocationDTO();
            parkingLocationDTO.setId(parkingLocation.getId());
            parkingLocationDTO.setStreet(parkingLocation.getStreet());
            parkingLocationDTO.setCity(parkingLocation.getCity());
            parkingLocationDTO.setState(parkingLocation.getState());
            parkingLocationDTO.setPostalCode(parkingLocation.getPostalCode());
            parkingLocationDTO.setCountry(parkingLocation.getCountry());

            List<ParkingSpot> parkingSpots = parkingSpotDAO.findAllSpotsByParkingLocationId(parkingLocation.getId());
            List<ParkingSpotDTO> parkingSpotDTOS = new ArrayList<>();
            for (ParkingSpot parkingSpot : parkingSpots) {
                ParkingSpotDTO parkingSpotDTO = new ParkingSpotDTO();
                parkingSpotDTO.setSpotNumber(parkingSpot.getSpotNumber());
                parkingSpotDTO.setSpotType(parkingSpot.getSpotType());
                parkingSpotDTO.setIsAvailable(parkingSpot.getIsAvailable());
                parkingSpotDTO.setPricePerHour(parkingSpot.getPricePerHour());

                parkingSpotDTOS.add(parkingSpotDTO);
            }
            parkingLocationDTO.setParkingSpots(parkingSpotDTOS);
            parkingLocationDTOS.add(parkingLocationDTO);
        }
        return parkingLocationDTOS;
    }

    @Override
    public void deleteParkingLocation(int locationId) {
        ParkingLocation location = parkingLocationDAO.findById(locationId);
        parkingLocationDAO.delete(location);
    }

    @Override
    public List<ParkingSpot> getAllSpotsByParkingLocationId(int locationId) {
        return parkingSpotDAO.findAllSpotsByParkingLocationId(locationId);
    }

    @Override
    public void updateParkingSpots(List<ParkingSpotUpdateRequest> updates) {
        for (ParkingSpotUpdateRequest update : updates) {
            ParkingSpot parkingSpot = parkingSpotDAO.findById(update.getId());
            parkingSpot.setIsAvailable(update.getIsAvailable());
            parkingSpotDAO.saveParkingSpot(parkingSpot);
        }
    }

    public List<ParkingLocation> findNearbyParkingLocations(double lat, double lon, double radius) {
        return parkingLocationDAO.findNearby(lat, lon, radius);
    }

    private void saveImage(MultipartFile image, String id) {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String newFilename = id + ".jpg";
        Path filePath = Paths.get(UPLOAD_DIR + newFilename);
        try {
            Files.copy(image.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParkingLocation getParkingLocationById(int id) {
        return parkingLocationDAO.findById(id);
    }

    public List<ParkingSpot> getAvailableSpotsByParkingLocationId(int parkingLocationId) {
        return parkingSpotDAO.findAvailableSpotsByParkingLocationId(parkingLocationId);
    }

    public ParkingSpot getParkingSpotById(int id) {
        return parkingSpotDAO.findById(id);
    }
}
