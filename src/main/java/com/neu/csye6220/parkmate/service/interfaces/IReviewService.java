package com.neu.csye6220.parkmate.service.interfaces;

import com.neu.csye6220.parkmate.model.Review;

import java.util.List;

public interface IReviewService{
    void saveReview(int parkingLocationId, int rating, String comment, int renteeId);
    List<Review> getReviewsByParkingLocationId(int id);
}
