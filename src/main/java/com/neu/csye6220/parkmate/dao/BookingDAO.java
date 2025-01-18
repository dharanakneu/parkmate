package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IBookingDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import com.neu.csye6220.parkmate.model.Booking;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Repository
public class BookingDAO extends DAO implements IBookingDAO {

    @Override
    public void save(Booking booking) {
        try {
            begin();
            getSession().persist(booking);
            commit();
            //return booking;
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while trying to save booking: ", e);
        }
    }

    @Override
    public Booking findById(Integer bookingId) {
        try {
            return getSession().find(Booking.class, bookingId);
        } catch (HibernateException e) {
            throw new DataAccessException("Error retrieving booking with id: " + bookingId, e);
        }
    }

    @Override
    public List<Booking> getBookingsByRenteeId(Integer renteeId) {
        try {
            Session session = getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
            Root<Booking> root = criteriaQuery.from(Booking.class);
            Predicate condition = criteriaBuilder.equal(root.get("rentee").get("id"), renteeId);
            criteriaQuery.select(root).where(condition);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            throw new DataAccessException("Error while trying to fetch bookings for rentee id: " + renteeId, e);
        }
    }

    @Override
    public void update(Booking booking) {
        try {
            begin();
            getSession().merge(booking);
            commit();
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while trying to update booking: ", e);
        }
    }
}
