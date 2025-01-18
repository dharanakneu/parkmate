package com.neu.csye6220.parkmate.service.interfaces;

import com.neu.csye6220.parkmate.model.Rentee;

public interface IRenteeService {
    void registerRentee(Rentee rentee);
    Rentee findById(Integer renteeId);
    Rentee save(Rentee existingRentee);
    int authenticate(String email, String rawPassword);
}
