package com.neu.csye6220.parkmate.service;

import com.neu.csye6220.parkmate.dao.interfaces.IRenterDAO;
import com.neu.csye6220.parkmate.model.Rentee;
import com.neu.csye6220.parkmate.model.Renter;
import com.neu.csye6220.parkmate.service.interfaces.IRenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RenterService implements IRenterService {

    @Autowired
    private IRenterDAO renterDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerRenter(Renter renter) {
        renterDAO.registerRenter(renter);
    }

    @Override
    public boolean emailExistsInAnyTable(String email) {
        return renterDAO.emailExistsInAnyTable(email);
    }

    public Renter findById(int renterId) {
        return renterDAO.findById(renterId);
    }

    @Override
    public Renter save(Renter renter) {
        return renterDAO.save(renter);
    }

    @Override
    public int authenticate(String email, String rawPassword) {
        Renter renter = renterDAO.findByEmail(email);
        if (renter != null) {
            if(passwordEncoder.matches(rawPassword, renter.getPassword())){
                return renter.getId();
            }
        }
        return 0;
    }
}
