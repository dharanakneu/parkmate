package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IParkingSpotDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import com.neu.csye6220.parkmate.model.ParkingSpot;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ParkingSpotDAO extends DAO implements IParkingSpotDAO {

    @Override
    public void saveParkingSpot(ParkingSpot parkingSpot) {
        try {
            begin();
            getSession().persist(parkingSpot);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error saving parking spot", e);
        }
    }

    public List<ParkingSpot> findAvailableSpotsByParkingLocationId(int parkingLocationId) {
        try {
            Session session = getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ParkingSpot> criteriaQuery = criteriaBuilder.createQuery(ParkingSpot.class);
            Root<ParkingSpot> root = criteriaQuery.from(ParkingSpot.class);

            Predicate locationCondition = criteriaBuilder.equal(root.get("parkingLocation").get("id"), parkingLocationId);
            Predicate availabilityCondition = criteriaBuilder.equal(root.get("isAvailable"), true);

            criteriaQuery.select(root).where(criteriaBuilder.and(locationCondition, availabilityCondition));
            return session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            throw new DataAccessException("Error while fetching available parking spots: ", e);
        }
    }

    public ParkingSpot findById(int id) {
        try {
            return getSession().find(ParkingSpot.class, id);
        } catch (HibernateException e) {
            throw new DataAccessException("Error finding parking spot by id: " + id, e);
        }
    }

    @Override
    public List<ParkingSpot> findAllSpotsByParkingLocationId(int id) {
        try {
            Session session = getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ParkingSpot> criteriaQuery = criteriaBuilder.createQuery(ParkingSpot.class);
            Root<ParkingSpot> root = criteriaQuery.from(ParkingSpot.class);

            Predicate condition = criteriaBuilder.equal(root.get("parkingLocation").get("id"), id);

            criteriaQuery.select(root).where(condition);

            return session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            throw new DataAccessException("Error finding all parking spots by location id: " + id, e);
        }
    }
}
