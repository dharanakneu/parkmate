package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IParkingLocationDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.neu.csye6220.parkmate.model.ParkingLocation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParkingLocationDAO extends DAO implements IParkingLocationDAO {

    public ParkingLocation findById(int id) {
        try {
            return getSession().find(ParkingLocation.class, id);
        } catch (HibernateException e) {
            throw new DataAccessException("Error retrieving parking location with id: " + id, e);
        }
    }

    @Override
    public void delete(ParkingLocation location) {
        try {
            begin();
            getSession().remove(location);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error deleting parking location", e);
        }
    }

    @Override
    public ParkingLocation saveParkingLocation(ParkingLocation parkingLocation) {
        try {
            begin();
            getSession().persist(parkingLocation);
            commit();
            return parkingLocation;
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while trying to save parking location", e);
        }
    }

    public List<ParkingLocation> findNearby(double latitude, double longitude, double radius) {
        //here Query with hql is more efficient and readable than Criteria
        try {
            String hql = "FROM ParkingLocation p WHERE " +
                         "(6371 * acos(cos(radians(:latitude)) * cos(radians(p.latitude)) * " +
                         "cos(radians(p.longitude) - radians(:longitude)) + " +
                         "sin(radians(:latitude)) * sin(radians(p.latitude)))) <= :radius";

            Query<ParkingLocation> query = getSession().createQuery(hql, ParkingLocation.class);
            query.setParameter("latitude", latitude);
            query.setParameter("longitude", longitude);
            query.setParameter("radius", radius);

            return query.getResultList();
        } catch (HibernateException e) {
            throw new DataAccessException("Error finding nearby parking locations", e);
        }
    }

    public List<ParkingLocation> findByRenterId(Integer renterId) {
        try {
            Session session = getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ParkingLocation> criteriaQuery = criteriaBuilder.createQuery(ParkingLocation.class);
            Root<ParkingLocation> root = criteriaQuery.from(ParkingLocation.class);
            Predicate condition = criteriaBuilder.equal(root.get("renter").get("id"), renterId);
            criteriaQuery.select(root).where(condition);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            throw new DataAccessException("Error finding parking locations by renter id", e);
        }
    }
}
