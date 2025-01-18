package com.neu.csye6220.parkmate.controller;

import com.neu.csye6220.parkmate.model.Booking;
import com.neu.csye6220.parkmate.model.Payment;
import com.neu.csye6220.parkmate.model.Rentee;
import com.neu.csye6220.parkmate.model.ParkingSpot;
import com.neu.csye6220.parkmate.service.interfaces.IBookingService;
import com.neu.csye6220.parkmate.service.interfaces.IParkingService;
import com.neu.csye6220.parkmate.service.interfaces.IPaymentService;
import com.neu.csye6220.parkmate.service.interfaces.IRenteeService;
import com.neu.csye6220.parkmate.view.BookingReceiptPdfView;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.Timestamp;

@Controller
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IRenteeService renteeService;

    @Autowired
    private IParkingService parkingService;

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/rentee/process-payment")
    public String processPayment(
            @RequestParam("selectedSpot") int selectedSpot,
            @RequestParam("bookingDate") String bookingDate,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("totalCharges") double totalCharges,
            Model model,
            HttpSession session,
            HttpServletResponse response) throws IOException {

        Rentee rentee = renteeService.findById((Integer) session.getAttribute("renteeId"));
        ParkingSpot parkingSpot = parkingService.getParkingSpotById(selectedSpot);

        try {
            String startDateTimeStr = bookingDate + " " + startTime + ":00";
            String endDateTimeStr = bookingDate + " " + endTime + ":00";

            Timestamp startTimestamp = Timestamp.valueOf(startDateTimeStr);
            Timestamp endTimestamp = Timestamp.valueOf(endDateTimeStr);

            Payment payment = new Payment();
            payment.setAmount(totalCharges);
            payment.setStatus("Success");
            payment.setSender(rentee);
            payment.setReceiver(parkingSpot.getParkingLocation().getRenter());
            paymentService.save(payment);

            Booking booking = new Booking();
            booking.setStartTime(startTimestamp);
            booking.setEndTime(endTimestamp);
            booking.setStatus("Confirmed");
            booking.setRentee(rentee);
            booking.setParkingSpot(parkingSpot);
            booking.setPayment(payment);
            bookingService.save(booking);

            session.setAttribute("selectedSpot", parkingSpot);
            session.setAttribute("startTime", startTimestamp);
            session.setAttribute("endTime", endTimestamp);

            session.setAttribute("bookingId", booking.getId());

        } catch (IllegalArgumentException e) {
            response.sendRedirect("/error");
        }
        return "processing-payment";
    }


    @GetMapping("/rentee/payment-success")
    public String thankYou(Model model, HttpSession session) {
        Integer bookingId = (Integer) session.getAttribute("bookingId");
        Booking booking = bookingService.findById(bookingId);

        model.addAttribute("booking", booking);
        session.setAttribute("booking", booking);
        return "payment-success";
    }

    @GetMapping("/rentee/generate-receipt")
    public void generateReceipt(HttpSession session, HttpServletResponse response) throws Exception {
        Booking booking = (Booking) session.getAttribute("booking");
        if (booking == null) {
            throw new IllegalArgumentException("No recent booking found in session.");
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"parkmate_booking_receipt.pdf\"");
        new BookingReceiptPdfView().generateReceiptPdf(response, booking);
    }

}
