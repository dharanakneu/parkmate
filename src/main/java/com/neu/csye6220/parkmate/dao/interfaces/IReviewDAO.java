package com.neu.csye6220.parkmate.dao.interfaces;

import com.neu.csye6220.parkmate.model.Review;

import java.util.List;

public interface IReviewDAO {
    void save(Review review);
    List<Review> getReviewsByParkingLocationId(int id);
}
