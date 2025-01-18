package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IRenteeDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import com.neu.csye6220.parkmate.model.Rentee;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class RenteeDAO extends DAO implements IRenteeDAO {

    @Override
    public void registerRentee(Rentee rentee) {
        try {
            begin();
            getSession().persist(rentee);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while saving rentee", e);
        }
    }

    @Override
    public Rentee findById(Integer renteeId) {
        try {
            return getSession().find(Rentee.class, renteeId);
        } catch (HibernateException e) {
            throw new DataAccessException("Error while fetching rentee by id: " + renteeId, e);
        }
    }

    @Override
    public Rentee save(Rentee rentee) {
        try {
            begin();
            getSession().persist(rentee);
            commit();
            return rentee;
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while saving rentee", e);
        }
    }

    @Override
    public Rentee findByEmail(String email) {
        try {
            Session session = getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Rentee> query = builder.createQuery(Rentee.class);
            Root<Rentee> root = query.from(Rentee.class);
            query.select(root).where(builder.equal(root.get("email"), email));
            return session.createQuery(query).uniqueResult();
        } catch (HibernateException e) {
            throw new DataAccessException("Error fetching rentee with email: " + email, e);
        }
    }
}
