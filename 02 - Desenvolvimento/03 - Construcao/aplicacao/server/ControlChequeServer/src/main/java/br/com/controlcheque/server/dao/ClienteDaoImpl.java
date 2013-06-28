/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.util.HibernateUtility;
import br.com.controlcheque.server.model.Cliente;
import br.com.controlcheque.server.util.LogChequeUtil;
import br.com.controlcheque.server.util.ServerConstantes;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Jose Robson
 */
public class ClienteDaoImpl<T extends Cliente> implements ClienteDao<T> {

    /**
     * Metodo para inserir ou atualizar um cliente
     *
     * @param c
     */
    @Override
    public Cliente mantemCliente(Cliente c) {
        //Gerar Log
        LogChequeUtil.logInicioProcessoMetodo(Cliente.class, "mantemCliente");
        try {
            DaoGenericoImpl dao = new DaoGenericoImpl();
            return (Cliente) dao.save(c);
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(Cliente.class, "ERRO AO SALVAR CLIENTE", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_SALVAR_CLIENTE);
        } finally {
            //Gera log do hibernate
            LogChequeUtil.logFimProcessoMetodo(Cliente.class, "mantemCliente");
        }
    }

    /**
     * Metodo para Buscar todos os clientes
     *
     * @return
     */
    @Override
    public List<T> consultarCliente() {

        //gerar log do hibernate
        LogChequeUtil.logInicioProcessoMetodo(Cliente.class, "consultarCliente");

        //inicializa variaveis locais
        List<T> lstRetorno = null;
        Session sessao = null;

        try {
            //implementa consulta
            sessao = HibernateUtility.getSession();
            StringBuilder hqlConsultaBanco = new StringBuilder();
            hqlConsultaBanco.append("from Cliente c order by c.nome");
            lstRetorno = sessao.createQuery(hqlConsultaBanco.toString()).list();

        } catch (HibernateException ex) {

            //gera log do hibernate
            LogChequeUtil.logDescricaoErro(Cliente.class, "ERRO AO CONSULTAR CLIENTE", ex);

            //lanca Excecao
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_BUSCAR_TODOS_CLIENTE);

        } finally {
            //fecha a sessao
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        //gera log do hibernate
        LogChequeUtil.logFimProcessoMetodo(Cliente.class, "consultarCliente");

        //retorno do metodo
        return lstRetorno;
    }

    /**
     * Metodo para recuperar o maior codigo cliente.
     *
     * @return
     */
    @Override
    public int recuperarMaxCodigoCliente() {

        //Gera Log
        LogChequeUtil.logInicioProcessoMetodo(Cliente.class, "recuperarMaxCodigoCliente");

        Session sessao = null;
        int retorno = -1;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hql = new StringBuilder();
            hql.append("Select max(c.codigo) From Cliente c");
            Object result = sessao.createQuery(hql.toString()).uniqueResult();
            if (result != null) {
                retorno = Integer.parseInt(result.toString()) + 1;
            }

        } catch (HibernateException ex) {
            //gera Log
            LogChequeUtil.logDescricaoErro(Cliente.class, ex.getMessage(), ex);
            //lança Excecao
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_CLIENTE_GERAR_CODIGO_SEQUENCIAL);

        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        LogChequeUtil.logFimProcessoMetodo(Cliente.class, "recuperarMaxCodigoCliente");
        return retorno;
    }

    @Override
    public T deletarCliente(T cliente) {
        LogChequeUtil.logInicioProcessoMetodo(Cliente.class, "deletaCliente");
        try {
            DaoGenericoImpl dao = new DaoGenericoImpl();
            dao.delete(cliente);
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(Cliente.class, "ERRO AO DELETAR CLIENTE: " + cliente.getId(), ex);
            throw ex;
        }
        LogChequeUtil.logFimProcessoMetodo(Cliente.class, "deletaCliente");

        return cliente;
    }
}
