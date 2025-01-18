package com.neu.csye6220.parkmate.service;

import com.neu.csye6220.parkmate.dao.interfaces.IBookingDAO;
import com.neu.csye6220.parkmate.model.Booking;
import com.neu.csye6220.parkmate.service.interfaces.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingDAO bookingDAO;

    @Override
    public void save(Booking booking) {
        bookingDAO.save(booking);
    }

    @Override
    public Booking findById(Integer bookingId) {
        return bookingDAO.findById(bookingId);
    }

    @Override
    public List<Booking> getBookingsByRenteeId(Integer renteeId) {
        return bookingDAO.getBookingsByRenteeId(renteeId);
    }

    @Override
    public Booking getBookingById(int bookingId) {
        return bookingDAO.findById(bookingId);
    }

    @Override
    public void updateBooking(Booking booking) {
        bookingDAO.update(booking);
    }
}
