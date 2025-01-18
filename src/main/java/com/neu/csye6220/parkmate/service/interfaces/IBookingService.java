package com.neu.csye6220.parkmate.service.interfaces;

import com.neu.csye6220.parkmate.model.Booking;

import java.util.List;

public interface IBookingService {
    void save(Booking booking);
    Booking findById(Integer bookingId);
    List<Booking> getBookingsByRenteeId(Integer renteeId);
    Booking getBookingById(int bookingId);
    void updateBooking(Booking booking);
}
