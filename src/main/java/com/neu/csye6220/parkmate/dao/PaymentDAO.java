package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IPaymentDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import com.neu.csye6220.parkmate.model.Payment;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDAO extends DAO implements IPaymentDAO {
    @Override
    public void save(Payment payment) {
        try {
            begin();
            getSession().persist(payment);
            commit();
            //return payment;
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while trying to save payment: ", e);
        }
    }
}
