package io.dao;

import io.exception.DaoException;
import io.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Function;

@Slf4j
public abstract class AbstractHibernateDAO<T> {

    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    protected final Class<T> clazz;

    protected AbstractHibernateDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected <R> R execute(Function<Session, R> command) {
        Transaction tx = null;
        try {
            Session session = getSession();
            tx = session.beginTransaction();
            R result = command.apply(session);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new DaoException(clazz.getSimpleName(), e.getMessage(), e);
        }
    }

    public T findById(int id) {
        return execute(session -> session.get(clazz, id));
    }

    public List<T> findAll() {
        return findAll(0, Integer.MAX_VALUE);
    }

    public List<T> findAll(int page, int pageSize) {
        return execute(session ->
                session.createQuery("FROM " + clazz.getSimpleName(), clazz)
                        .setFirstResult(page * pageSize)
                        .setMaxResults(pageSize)
                        .getResultList());
    }

    public long count() {
        return execute(session ->
                session.createQuery("SELECT COUNT(*) FROM " + clazz.getSimpleName(), Long.class)
                        .getSingleResult());
    }

    public int save(T entity) {
        return execute(session -> {
            session.persist(entity);
            session.flush();
            return (int) session.getIdentifier(entity);
        });
    }

    public void update(T entity) {
        execute(session -> {
            session.merge(entity);
            return null;
        });
    }

    public void delete(T entity) {
        execute(session -> {
            session.remove(entity);
            return null;
        });
    }

    public void saveAll(List<T> entities) {
        execute(session -> {
            for (T entity : entities) {
                session.persist(entity);
            }
            return null;
        });
    }

    public void deleteAll(List<T> entities) {
        execute(session -> {
            for (T entity : entities) {
                session.remove(entity);
            }
            return null;
        });
    }
}
