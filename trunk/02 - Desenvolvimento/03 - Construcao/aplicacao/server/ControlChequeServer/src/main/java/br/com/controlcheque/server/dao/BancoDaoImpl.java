    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.server.util.HibernateUtility;
import br.com.controlcheque.server.util.LogChequeUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Jose Robson
 */
public class BancoDaoImpl<T extends Banco> implements BancoDao {

    /**
     * Metodo para inserir ou atualizar um banco
     * @param b
     */
    @Override
    public Banco mantemBanco(Banco b) {
        LogChequeUtil.logInicioProcessoMetodo(Banco.class, "mantemBanco");
        try {
            DaoGenericoImpl dao = new DaoGenericoImpl();
            return (T) dao.save(b);

        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(Banco.class, "ERRO AO SALVAR BANCO.", ex);
            throw ex;
        } finally {
            LogChequeUtil.logFimProcessoMetodo(Banco.class, "mantemBanco");
        }
    }

    /**
     * BUSCA TODOS OS BANCOS
     * @return
     */
    @Override
    public List<Banco> consultarBanco() {

        LogChequeUtil.logInicioProcessoMetodo(Banco.class, "consultarBanco");

        List<Banco> lstRetorno = null;
        Session sessao = null;
        try {
            HibernateUtility.beginTransaction();
            sessao = HibernateUtility.getSession();
            StringBuilder hqlConsultaBanco = new StringBuilder();
            hqlConsultaBanco.append("from Banco b ");
            lstRetorno = sessao.createQuery(hqlConsultaBanco.toString()).list();
            HibernateUtility.commitTransaction();
        } catch (HibernateException ex) {
            //Gerar Log pelo Hibernate
            HibernateUtility.rollbackTransaction();
            LogChequeUtil.logDescricaoErro(Banco.class, "ERRO AO CONSULTAR BANCO", ex);
            throw ex;
        } finally {
            HibernateUtility.closeSession();
        }

        LogChequeUtil.logFimProcessoMetodo(Banco.class, "consultarBanco");
        return lstRetorno;

    }

    /**
     * REMOVE BANCO
     * @param b
     * @return
     */
    @Override
    public Banco deleteBanco(Banco b) {
        LogChequeUtil.logInicioProcessoMetodo(Banco.class, "deleteBanco");
        try {
            DaoGenericoImpl dao = new DaoGenericoImpl();
            dao.delete(b);
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(Banco.class, "ERRO AO DELETAR BANCO: " + b.getId(), ex);
            throw ex;
        }
        LogChequeUtil.logFimProcessoMetodo(Banco.class, "deleteBanco");
        return b;
    }
}
