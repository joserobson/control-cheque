/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.util.HibernateUtility;
import java.io.Serializable;
import org.hibernate.HibernateException;

/**
 *
 * @author Robson
 */
public class DaoGenericoImpl<T, ID extends Serializable> implements DaoGenerico<T, ID> {

    //private final Class<T> oClass;
    public DaoGenericoImpl() {
        // this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Class<T> getObjectClass() {
        return null;
    }

    @Override
    public T save(T objeto) {
        try {
            Object obj = null;
            HibernateUtility.beginTransaction();
            obj = HibernateUtility.getSession().merge(objeto);
            HibernateUtility.commitTransaction();
            HibernateUtility.closeSession();
            return (T) obj;
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public void delete(T objeto) {
        try {
            HibernateUtility.beginTransaction();
            HibernateUtility.getSession().flush();
            HibernateUtility.getSession().clear();
            HibernateUtility.getSession().delete(objeto);
            HibernateUtility.commitTransaction();
            HibernateUtility.closeSession();
        } catch (HibernateException hibernateException) {
            cancel();
            throw hibernateException;
        }
    }

    @Override
    public void deleteItem(T objeto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T getById(Serializable id) {

        try {
            HibernateUtility.beginTransaction();
            HibernateUtility.getSession().refresh(id);
            HibernateUtility.commitTransaction();
            HibernateUtility.closeSession();
            return (T) id;
        } catch (HibernateException ex) {
            cancel();
            throw ex;
        }
    }

    @Override
    public void cancel() {
        HibernateUtility.rollbackTransaction();
        HibernateUtility.closeSession();
    }
}
