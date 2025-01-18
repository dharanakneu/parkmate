package com.neu.csye6220.parkmate.dao.interfaces;


import com.neu.csye6220.parkmate.model.Rentee;

public interface IRenteeDAO {
    void registerRentee(Rentee rentee);
    Rentee findById(Integer renteeId);
    Rentee save(Rentee rentee);
    Rentee findByEmail(String email);
}
