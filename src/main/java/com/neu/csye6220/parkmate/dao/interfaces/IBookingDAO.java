package com.neu.csye6220.parkmate.dao.interfaces;

import com.neu.csye6220.parkmate.model.Booking;

import java.util.List;

public interface IBookingDAO {
    void save(Booking booking);
    Booking findById(Integer bookingId);
    List<Booking> getBookingsByRenteeId(Integer renteeId);
    void update(Booking booking);
}
