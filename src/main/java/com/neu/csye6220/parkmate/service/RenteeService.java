package com.neu.csye6220.parkmate.service;

import com.neu.csye6220.parkmate.dao.interfaces.IRenteeDAO;
import com.neu.csye6220.parkmate.model.Rentee;
import com.neu.csye6220.parkmate.service.interfaces.IRenteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RenteeService implements IRenteeService {

    @Autowired
    private IRenteeDAO renteeDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerRentee(Rentee rentee) {
        renteeDAO.registerRentee(rentee);
    }

    @Override
    public Rentee findById(Integer renteeId) {
        return renteeDAO.findById(renteeId);
    }

    @Override
    public Rentee save(Rentee rentee) {
        return renteeDAO.save(rentee);
    }

    public int authenticate(String email, String rawPassword) {
        Rentee rentee = renteeDAO.findByEmail(email);
        if (rentee != null) {
            if(passwordEncoder.matches(rawPassword, rentee.getPassword())){
                return rentee.getId();
            }
        }
        return 0;
    }
}
