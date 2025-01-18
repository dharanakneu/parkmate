package com.neu.csye6220.parkmate.service;

import com.neu.csye6220.parkmate.dao.interfaces.IReviewDAO;
import com.neu.csye6220.parkmate.model.ParkingLocation;
import com.neu.csye6220.parkmate.model.Rentee;
import com.neu.csye6220.parkmate.model.Review;
import com.neu.csye6220.parkmate.service.interfaces.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewDAO reviewDAO;

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private RenteeService renteeService;

    @Override
    public void saveReview(int parkingLocationId, int rating, String comment, int renteeId) {
        Review review = new Review();
        ParkingLocation parkingLocation = parkingService.getParkingLocationById(parkingLocationId);
        Rentee rentee = renteeService.findById(renteeId);
        review.setParkingLocation(parkingLocation);
        review.setRating(rating);
        review.setComment(comment);
        review.setRentee(rentee);
        reviewDAO.save(review);
    }

    @Override
    public List<Review> getReviewsByParkingLocationId(int id) {
        return reviewDAO.getReviewsByParkingLocationId(id);
    }
}
