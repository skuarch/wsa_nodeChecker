package model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Data Access Object generic.
 *
 * @author skuarch
 */
public final class DAO {

    private static Session session = null;
    private Transaction transaction = null;

    //==========================================================================
    /**
     * this class doesn't require a constructor.
     */
    public DAO() {
        openSession();
    } // end DAO;

    //==========================================================================
    /**
     * open a session with hibernate.
     */
    private void openSession() {

        session = HibernateUtil.getSessionFactory().openSession();

    } // end openSession

    //==========================================================================
    /**
     * save the object in the database.
     *
     * @param object Object
     * @return long (id)
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public long create(final Object object) throws HibernateException {

        if (object == null) {
            throw new IllegalArgumentException("the parameter object is null");
        }

        long id = 0;

        try {

            transaction = session.beginTransaction();
            id = (long) session.save(object);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return id;
    } // end create

    //==========================================================================
    /**
     * remove the object from the database.
     *
     * @param object Object
     * @throws HibernateException
     */
    public void delete(final Object object) throws HibernateException {

        if (object == null) {
            throw new IllegalArgumentException("the parameter object is null");
        }

        try {

            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

    } // end delete 

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> T get(final long id, final T type) {

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        if (id < 1) {
            throw new IllegalArgumentException("the parameter id is less than 1");
        }

        T t = null;

        try {

            transaction = session.beginTransaction();
            t = (T) session.get(type.getClass(), id);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return t;

    } // end get

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(final T type) throws HibernateException {

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        List<T> list = null;

        try {

            transaction = session.beginTransaction();
            list = session.createQuery("from " + type.getClass().getCanonicalName()).list();
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end getList

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> List<T> hql(final String hql, final T type) {

        if (hql == null || hql.length() < 1) {
            throw new IllegalArgumentException("the parameter hql is null");
        }

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        List<T> list = null;
        Query query = null;

        try {

            query = session.createQuery(hql);
            query.setProperties(type);
            list = query.list();

        } catch (HibernateException e) {
            throw e;
        } finally {
            closeSession();
        }

        return list;

    } // end hql

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> List<T> hql(final String hql, final T type, int maxResults) {

        if (hql == null || hql.length() < 1) {
            throw new IllegalArgumentException("the parameter hql is null");
        }

        if (type == null) {
            throw new IllegalArgumentException("the parameter type is null");
        }

        if (maxResults < 1) {
            throw new IllegalArgumentException("the parameter maxResults is incorrect");
        }

        List<T> list = null;
        Query query = null;

        try {

            query = session.createQuery(hql);
            query.setMaxResults(maxResults);
            query.setProperties(type);
            list = query.list();

        } catch (HibernateException e) {
            throw e;
        } finally {
            closeSession();
        }

        return list;

    } // end hql

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> List<T> query(final String queryName, final T type) throws HibernateException {

        if (queryName == null || queryName.length() < 1) {
            throw new NullPointerException("queryName is null or empty");
        }

        if (type == null) {
            throw new NullPointerException("type is null");
        }

        Query query = null;
        List<T> list = null;

        try {

            query = session.getNamedQuery(queryName);
            query.setProperties(type);
            list = query.list();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end query

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> List<T> query(final String queryName, final HashMap<String, String> parameters, final T type) throws HibernateException {

        if (queryName == null || queryName.length() < 1) {
            throw new NullPointerException("queryName is null or empty");
        }

        if (parameters == null || parameters.size() < 1) {
            throw new NullPointerException("parameters are null or empty");
        }

        if (type == null) {
            throw new NullPointerException("type is null");
        }

        String key = null;
        String value = null;
        Query query = null;
        List<T> list = null;

        try {

            query = session.getNamedQuery(queryName);
            query.setProperties(type);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                query.setString(key, value);
            }

            list = query.list();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end query

    //==========================================================================
    @SuppressWarnings("unchecked")
    public <T> List<T> query(final String queryName, final HashMap<String, String> parameters, final T type, final int maxResults) throws HibernateException {

        if (queryName == null || queryName.length() < 1) {
            throw new NullPointerException("queryName is null or empty");
        }

        if (parameters == null || parameters.size() < 1) {
            throw new NullPointerException("parameters are null or empty");
        }

        if (type == null) {
            throw new NullPointerException("type is null");
        }

        if (maxResults < 1) {
            throw new IllegalArgumentException("the parameter maxResults is incorrect");
        }

        String key = null;
        String value = null;
        Query query = null;
        List<T> list = null;

        try {

            query = session.getNamedQuery(queryName);
            query.setProperties(type);
            query.setMaxResults(maxResults);

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                query.setString(key, value);
            }

            list = query.list();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

        return list;

    } // end query

    //==========================================================================
    /**
     * update a object in database.
     *
     * @param object Object
     * @throws HibernateException
     */
    public void update(final Object object) throws HibernateException {

        if (object == null) {
            throw new IllegalArgumentException("the parameter object is null");
        }

        try {

            transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();

        } catch (HibernateException he) {
            throw he;
        } finally {
            closeSession();
        }

    } // end update

    //==========================================================================
    /**
     * close hibernate session.
     */
    private static void closeSession() {

        if (session != null) {

            if (session.isOpen()) {
                session.close();
                //session.disconnect();
            }

        }

    } // end closeSession

} // end class
