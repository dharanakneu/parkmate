package com.neu.csye6220.parkmate.controller;

import com.neu.csye6220.parkmate.dto.ParkingLocationDTO;
import com.neu.csye6220.parkmate.dto.ParkingSpotDTO;
import com.neu.csye6220.parkmate.dto.ParkingSpotUpdateRequest;
import com.neu.csye6220.parkmate.model.ParkingLocation;
import com.neu.csye6220.parkmate.model.ParkingSpot;
import com.neu.csye6220.parkmate.model.Renter;

import com.neu.csye6220.parkmate.model.Review;
import com.neu.csye6220.parkmate.service.interfaces.IParkingService;
import com.neu.csye6220.parkmate.service.interfaces.IRenterService;
import com.neu.csye6220.parkmate.service.interfaces.IReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ParkingController {

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IRenterService renterService;

    @Autowired
    private IReviewService reviewService;

    @GetMapping("/renter/post-parking")
    public String showParkingForm(Model model) {
        ParkingLocationDTO parkingLocationDTO = new ParkingLocationDTO();
        List<ParkingSpotDTO> parkingSpots = new ArrayList<>();
        parkingSpots.add(new ParkingSpotDTO());
        parkingLocationDTO.setParkingSpots(parkingSpots);
        model.addAttribute("parkingLocationDTO", parkingLocationDTO);
        return "post-parking";
    }

    @PostMapping("/renter/post-parking")
    public String submitParkingForm(@Valid @ModelAttribute("parkingLocationDTO") ParkingLocationDTO parkingLocationDTO, BindingResult bindingResult, HttpSession session, Model model) {
        if (parkingLocationDTO.getImage().isEmpty()) {
            bindingResult.rejectValue("image", "error.image", "Image cannot be blank");
        }
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            model.addAttribute("errorMessages", errorMessages);
            model.addAttribute("parkingLocationDTO", parkingLocationDTO);
            return "post-parking";
        }
        Renter currentRenter = renterService.findById((Integer) session.getAttribute("renterId"));
        parkingService.saveParkingLocationAndSpots(parkingLocationDTO, currentRenter);
        return "redirect:/renter/post-parking";
    }

    @GetMapping("/rentee/parking-locations")
    public String getNearbyParkingLocations(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam double radius,
            Model model) {
        List<ParkingLocation> parkingLocations = parkingService.findNearbyParkingLocations(lat, lon, 2);
        model.addAttribute("parkingLocations", parkingLocations);
        return "search-parking";
    }

    @GetMapping("/rentee/parking-location-details")
    public String getParkingLocationDetails(@RequestParam("id") int id, Model model) {
        ParkingLocation parkingLocation = parkingService.getParkingLocationById(id);
        List<ParkingSpot> parkingSpots = parkingService.getAvailableSpotsByParkingLocationId(id);
        List<Review> reviews = reviewService.getReviewsByParkingLocationId(id);
        model.addAttribute("parkingLocation", parkingLocation);
        model.addAttribute("parkingSpots", parkingSpots);
        model.addAttribute("reviews", reviews);
        return "parking-location-details";
    }

    @GetMapping("/renter/my-postings")
    public String viewParkingLocations(Model model, HttpSession session) {
        Integer renterId = (Integer) session.getAttribute("renterId");
        List<ParkingLocationDTO> parkingLocationDTOS = parkingService.getParkingLocationByRenterId(renterId);
        model.addAttribute("parkingLocations", parkingLocationDTOS);
        return "my-postings";
    }

    @GetMapping("/renter/get-parking-spots")
    public ResponseEntity<List<ParkingSpot>> getParkingSpots(@RequestParam("locationId") int locationId) {
        try {
            List<ParkingSpot> parkingSpots = parkingService.getAllSpotsByParkingLocationId(locationId);
            return ResponseEntity.ok(parkingSpots);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/renter/update-parking-spots")
    @ResponseBody
    public ResponseEntity<String> updateParkingSpots(@RequestBody List<ParkingSpotUpdateRequest> updates) {
        try {
            parkingService.updateParkingSpots(updates);
            return ResponseEntity.ok("Parking spots updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update parking spots: " + e.getMessage());
        }
    }

    @PostMapping("/renter/delete-parking/{locationId}")
    public String deleteParkingLocation(@PathVariable int locationId, RedirectAttributes redirectAttributes) {
        try {
            parkingService.deleteParkingLocation(locationId);
            redirectAttributes.addFlashAttribute("message", "Parking location deleted successfully!");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to delete parking location: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        return "redirect:/renter/my-postings";
    }
}
