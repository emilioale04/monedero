package ec.edu.epn.monederovirtual.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ec.edu.epn.monederovirtual.util.HibernateUtil;
import java.util.List;

/**
 * Generic BaseDAO for common CRUD operations using Hibernate.
 *
 * @param <T> The entity type for this DAO.
 */
public abstract class BaseDAO<T> {

    private final Class<T> entityClass;

    /**
     * Constructor to initialize the DAO with the entity class type.
     *
     * @param entityClass The class of the entity.
     */
    public BaseDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Saves a new entity to the database.
     *
     * @param entity The entity to save.
     */
    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id The ID of the entity.
     * @return The entity if found, null otherwise.
     */
    public T findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(entityClass, id);
        }
    }

    /**
     * Retrieves all entities of the given type.
     *
     * @return A list of all entities.
     */
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from " + entityClass.getName(), entityClass).list();
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity to update.
     */
    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity The entity to delete.
     */
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}