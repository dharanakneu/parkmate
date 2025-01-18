package com.neu.csye6220.parkmate.service.interfaces;

import com.neu.csye6220.parkmate.model.Renter;

public interface IRenterService {
    void registerRenter(Renter renter);
    boolean emailExistsInAnyTable(String email);
    Renter findById(int id);
    Renter save(Renter existingRenter);
    int authenticate(String email, String password);
}
