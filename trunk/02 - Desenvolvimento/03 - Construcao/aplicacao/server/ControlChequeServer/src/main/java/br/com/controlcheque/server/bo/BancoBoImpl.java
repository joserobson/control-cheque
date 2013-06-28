/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.bo;

import br.com.controlcheque.server.dao.BancoDao;
import br.com.controlcheque.server.model.Banco;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author Jose Robson
 */
public class BancoBoImpl<T extends Banco> implements BancoBo {

    private BancoDao bancoDao;

    @Override
    public Banco mantemBanco(Banco b) {
        try {
            return bancoDao.mantemBanco(b);
        } catch (HibernateException ex) {
            throw ex;
        }
    }

    @Override
    public List<T> consultarBanco() {
        return bancoDao.consultarBanco();
    }

    @Override
    public void setBancoDao(BancoDao bancoDao) {
        this.bancoDao = bancoDao;
    }

    @Override
    public Banco deleteBanco(Banco b) {
        return bancoDao.deleteBanco(b);
    }
}
