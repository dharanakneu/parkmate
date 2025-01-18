package com.neu.csye6220.parkmate.controller;

import com.neu.csye6220.parkmate.model.ParkingSpot;
import com.neu.csye6220.parkmate.service.interfaces.IBookingService;
import com.neu.csye6220.parkmate.service.interfaces.IParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.neu.csye6220.parkmate.model.Booking;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IParkingService parkingService;

    @GetMapping("/rentee/my-bookings")
    public String showMyBookings(HttpSession session, Model model) {
        Integer renteeId = (Integer) session.getAttribute("renteeId");
        List<Booking> bookings = bookingService.getBookingsByRenteeId(renteeId);
        model.addAttribute("bookings", bookings);
        return "my-bookings";
    }

    @PostMapping("/rentee/confirm-booking")
    public String confirmBooking(
            @RequestParam("selectedSpot") int selectedSpot,
            @RequestParam("bookingDate") String bookingDate,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            Model model,
            HttpSession session) {

        ParkingSpot parkingSpot = parkingService.getParkingSpotById(selectedSpot);
        double pricePerHour = parkingSpot.getPricePerHour();

        java.time.LocalTime start = java.time.LocalTime.parse(startTime);
        java.time.LocalTime end = java.time.LocalTime.parse(endTime);
        java.time.Duration duration = java.time.Duration.between(start, end);
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        double totalCharges = (hours + (double) minutes / 60) * pricePerHour;
        totalCharges = Math.round(totalCharges * 100.0) / 100.0;

        model.addAttribute("parkingSpot", parkingSpot);
        model.addAttribute("bookingDate", bookingDate);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("totalCharges", totalCharges);

        return "booking-confirmation";
    }

    @PostMapping("rentee/cancel-booking")
    public String cancelBooking(@RequestParam("bookingId") int bookingId, HttpSession session) {
        Integer renteeId = (Integer) session.getAttribute("renteeId");
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking != null && booking.getRentee().getId() == renteeId) {
            booking.setStatus("Cancelled");
            bookingService.updateBooking(booking);
        }
        return "redirect:/rentee/my-bookings";
    }
}

