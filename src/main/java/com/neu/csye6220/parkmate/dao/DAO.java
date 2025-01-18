package com.neu.csye6220.parkmate.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public abstract class DAO {

    @Autowired
    private SessionFactory sessionFactory;

    private  static final ThreadLocal sessionThread = new ThreadLocal();

    private static final Logger logger = Logger.getAnonymousLogger();

    protected DAO() {}

    protected Session getSession() {
        Session session = (Session) DAO.sessionThread.get();
        if (session == null) {
            session = sessionFactory.openSession();
            sessionThread.set(session);
        }
        return session;
    }

    protected void begin() {
        getSession().beginTransaction();
    }

    protected void commit() {
        Transaction transaction = getSession().getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
    }

    protected void rollback() {
        try {
            Transaction transaction = getSession().getTransaction();
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (HibernateException e) {
            logger.log(Level.WARNING, "Rollback failed", e);
        }
    }

    public void close() {
        Session session = getSession();
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
