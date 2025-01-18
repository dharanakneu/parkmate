package com.neu.csye6220.parkmate.service;

import com.neu.csye6220.parkmate.dao.interfaces.IPaymentDAO;
import com.neu.csye6220.parkmate.model.Payment;
import com.neu.csye6220.parkmate.service.interfaces.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentDAO paymentDAO;

    @Override
    public void save(Payment payment) {
        paymentDAO.save(payment);
    }
}
