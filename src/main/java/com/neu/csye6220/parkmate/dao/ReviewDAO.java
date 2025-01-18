package com.neu.csye6220.parkmate.dao;

import com.neu.csye6220.parkmate.dao.interfaces.IReviewDAO;
import com.neu.csye6220.parkmate.exception.DataAccessException;
import com.neu.csye6220.parkmate.model.Review;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ReviewDAO extends DAO implements IReviewDAO {

    @Override
    public void save(Review review) {
        try {
            begin();
            getSession().persist(review);
            commit();
            //return review;
        } catch (HibernateException e) {
            rollback();
            throw new DataAccessException("Error while trying to save review: ", e);
        }
    }

    @Override
    public List<Review> getReviewsByParkingLocationId(int id) {
        try {
            Session session = getSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
            Root<Review> root = criteriaQuery.from(Review.class);

            Predicate condition = criteriaBuilder.equal(root.get("parkingLocation").get("id"), id);

            criteriaQuery.select(root).where(condition);

            return session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            throw new DataAccessException("Error fetching review by parking location id: " + id, e);
        }
    }
}
