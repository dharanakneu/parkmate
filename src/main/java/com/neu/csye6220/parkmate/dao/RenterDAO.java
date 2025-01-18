package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IRenterDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import com.neu.csye6220.parkmate.model.Renter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class RenterDAO extends DAO implements IRenterDAO {

    @Override
    public void registerRenter(Renter renter) {
        try {
            begin();
            getSession().persist(renter);
            commit();
        } catch (HibernateException e) {
            rollback();
        }
    }

    @Override
    public boolean emailExistsInAnyTable(String email) {
        try {
            Long count = getSession().createQuery(
                            "SELECT COUNT(*) FROM Renter WHERE email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();

            count += getSession().createQuery(
                            "SELECT COUNT(*) FROM Rentee WHERE email = :email", Long.class)
                    .setParameter("email", email)
                    .uniqueResult();

            return count > 0;
        }catch (HibernateException e){
            return false;
        }
    }

    @Override
    public Renter findById(int renterId) {
        try {
            return getSession().find(Renter.class, renterId);
        } catch (HibernateException e) {
            throw new DataAccessException("Error finding renter by id: " + renterId, e);
        }
    }

    @Override
    public Renter save(Renter renter) {
        try {
            begin();
            getSession().persist(renter);
            commit();
            return renter;
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while saving renter", e);
        }
    }

    @Override
    public Renter findByEmail(String email) {
        try {
            Session session = getSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Renter> query = builder.createQuery(Renter.class);
            Root<Renter> root = query.from(Renter.class);
            query.select(root).where(builder.equal(root.get("email"), email));
            return session.createQuery(query).uniqueResult();
        } catch (HibernateException e) {
            throw new DataAccessException("Error fetching renter with email: " + email, e);
        }
    }
}
