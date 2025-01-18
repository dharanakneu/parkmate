package com.neu.csye6220.parkmate.dao.interfaces;

import com.neu.csye6220.parkmate.model.Renter;

public interface IRenterDAO {
    void registerRenter(Renter renter);
    boolean emailExistsInAnyTable(String email);
    Renter findById(int id);
    Renter save(Renter renter);
    Renter findByEmail(String email);
}
