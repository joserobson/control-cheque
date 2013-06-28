/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import java.io.Serializable;

/**
 *
 * @author Robson
 */
public interface DaoGenerico<T, ID extends Serializable> {

    public Class<T> getObjectClass();

    public T save(T objeto);

    public void delete(T objeto);

    public void deleteItem(T objeto);

    public T getById(Serializable id);

    public void cancel();
}
