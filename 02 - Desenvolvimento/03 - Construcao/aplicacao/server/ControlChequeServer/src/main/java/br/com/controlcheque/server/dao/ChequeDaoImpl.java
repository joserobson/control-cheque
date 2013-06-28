/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.bo.ChequeBo;
import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.server.model.Cheque;
import br.com.controlcheque.server.util.HibernateUtility;
import br.com.controlcheque.server.util.LogChequeUtil;
import br.com.controlcheque.server.util.ServerConstantes;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Robson
 */
public class ChequeDaoImpl<T extends Cheque> implements ChequeDao {

    /**
     * Cadastrar cheque
     *
     * @param cheque
     */
    @Override
    public Cheque mantemCheque(Cheque pCheque) {
        //Gerar Log
        LogChequeUtil.logInicioProcessoMetodo(Cheque.class, "mantemCheque");
        try {
            DaoGenericoImpl dao = new DaoGenericoImpl();
            return (T) dao.save(pCheque);
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(Cheque.class, "ERRO AO SALVAR CHEQUE", ex);
        } finally {
            //Gera log do hibernate
            LogChequeUtil.logFimProcessoMetodo(Cheque.class, "mantemCheque");
        }
        return null;
    }

    /**
     * Retorna todos os cheques cadastrados
     *
     * @return
     */
    @Override
    public List<Cheque> consultarCheques() {
        LogChequeUtil.logInicioProcessoMetodo(Cheque.class, "consultarCheques");

        List<Cheque> lstRetorno = null;
        Session sessao = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hqlConsultaCheque = new StringBuilder();
            hqlConsultaCheque.append("from Cheque c ");
            lstRetorno = sessao.createQuery(hqlConsultaCheque.toString()).list();

        } catch (HibernateException ex) {
            //Gerar Log pelo Hibernate
            LogChequeUtil.logDescricaoErro(Cheque.class, "ERRO AO CONSULTAR CHEQUE", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_BUSCAR_TODOS_CHEQUE);
        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        LogChequeUtil.logFimProcessoMetodo(Cheque.class, "consultarCheques");
        return lstRetorno;
    }

    /**
     *
     * @param eSituacao
     * @param eStatus
     * @return
     */
    @Override
    public List<Cheque> consultaChequesPorSituacao(ESituacaoCheque eSituacao, EStatusCheque eStatus) {
        LogChequeUtil.logInicioProcessoMetodo(Cheque.class, "consultaChequesPorSituacao");

        List<Cheque> lstRetorno = null;
        Session sessao = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hqlConsultaCheque = new StringBuilder();
            hqlConsultaCheque.append("from Cheque c ");

            boolean seWhere = false;
            if (!eSituacao.equals(ESituacaoCheque.Todos)) {
                hqlConsultaCheque.append("where c.situacaoCheque  = ").append(eSituacao.getCodigoSituacaoCheque());
                seWhere = true;
            }

            if (seWhere) {
                hqlConsultaCheque.append(" and c.statusCheque = ").append(eStatus.getCodigoStatusCheque());
            } else {
                hqlConsultaCheque.append(" where c.statusCheque = ").append(eStatus.getCodigoStatusCheque());
            }

            lstRetorno = sessao.createQuery(hqlConsultaCheque.toString()).list();
            sessao.close();

        } catch (HibernateException ex) {
            //Gerar Log pelo Hibernate
            LogChequeUtil.logDescricaoErro(Cheque.class, "ERRO AO CONSULTAR CHEQUE POR SITUACAO", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_BUSCAR_TODOS_CHEQUE);

        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        LogChequeUtil.logFimProcessoMetodo(Cheque.class, "consultaChequesPorSituacao");
        if (lstRetorno.isEmpty()) {
            lstRetorno = null;
        }
        return lstRetorno;
    }

    /**
     * Monta objeto cheque só pelo id
     *
     * @param id
     * @return
     */
    @Override
    public Cheque recChequePorID(String id) {
        LogChequeUtil.logInicioProcessoMetodo(Cheque.class, "atualizaChequePorID");

        Cheque retorno = null;
        Session sessao = null;
        Transaction transacao = null;
        try {
            sessao = HibernateUtility.getSession();
            transacao = sessao.beginTransaction();
            StringBuilder hql = new StringBuilder();
            hql.append("from Cheque c where c.id = '").append(id).append("'");
            Query query = sessao.createQuery(hql.toString());
            retorno = (Cheque) query.uniqueResult();
            transacao.commit();

        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(Banco.class, "ERRO AO ATUALIZAR CHEQUE POR ID" + id, ex);
            if (transacao != null) {
                transacao.rollback();
            }
        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        LogChequeUtil.logFimProcessoMetodo(Cheque.class, "atualizaChequePorID");
        return retorno;
    }

    /**
     *
     * @param b
     * @return
     */
    @Override
    public List<Cheque> consultaChequesPorBanco(Banco b) {

        LogChequeUtil.logInicioProcessoMetodo(Cheque.class, "consultaChequesPorBanco");

        List<Cheque> lstRetorno = null;
        Session sessao = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hqlConsulta = new StringBuilder();
            hqlConsulta.append("from Cheque c where c.banco.id = '").append(b.getId()).append("'");
            lstRetorno = sessao.createQuery(hqlConsulta.toString()).list();

        } catch (HibernateException ex) {
            //Gerar Log pelo Hibernate
            LogChequeUtil.logDescricaoErro(Banco.class, "ERRO AO CONSULTAR CHEQUES POR BANCO", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_BUSCAR_CLIENTES_POR_BANCO);
        } finally {
            sessao.close();
        }

        LogChequeUtil.logFimProcessoMetodo(Cheque.class, "consultaChequesPorBanco");
        return lstRetorno;
    }

    /**
     * RECUPERAR ULTIMO CODIGO CADASTRADO PARA CLIENTE
     *
     * @return
     */
    @Override
    public int recuperaMaxcodigoCheque() {
        //Gera Log
        LogChequeUtil.logInicioProcessoMetodo(Cheque.class, "recuperaMaxcodigoCheque");

        Session sessao = null;
        int retorno = -1;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hql = new StringBuilder();
            hql.append("Select max(c.codigoCheque) From Cheque c");
            Object result = sessao.createQuery(hql.toString()).uniqueResult();
            if (result != null) {
                retorno = Integer.parseInt(result.toString()) + 1;
            }

        } catch (HibernateException ex) {
            //gera Log
            LogChequeUtil.logDescricaoErro(Cheque.class, ex.getMessage(), ex);
            //lança Excecao
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_CHEQUE_GERAR_CODIGO_SEQUENCIAL);

        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        LogChequeUtil.logFimProcessoMetodo(Cheque.class, "recuperaMaxcodigoCheque");
        return retorno;
    }

    /**
     * Alterar situação do cheque para
     *
     * @param cheque
     * @return
     */
    @Override
    public Cheque deletarCheque(Cheque cheque) {

        Cheque retorno = null;
        try {
            DaoGenericoImpl daoImpl = new DaoGenericoImpl();
            cheque = (Cheque) daoImpl.getById(cheque);
            cheque.setStatusCheque(EStatusCheque.removido.getCodigoStatusCheque());
            retorno = (Cheque) daoImpl.save(cheque);
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(ChequeBo.class, ex.getMessage(), ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_DELETAR_CHEQUE);
        }

        return retorno;
    }

    @Override
    public Cheque deletarChequeLixeira(Cheque cheque) {
        Cheque retorno = null;
        try {
            DaoGenericoImpl daoImpl = new DaoGenericoImpl();
            daoImpl.delete(cheque);
            retorno = cheque;
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(ChequeBo.class, ex.getMessage(), ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_DELETAR_CHEQUE_LIXEIRA);
        }

        return retorno;
    }

    @Override
    public float recSomatarioChequesPorIntervaloDatas(Date dataInicial, Date dataFinal) {
        Float valorRetorno = Float.valueOf(0);
        Session sessao = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hql = new StringBuilder();
            hql.append("SELECT SUM(c.valor) FROM Cheque c WHERE c.dataBomPara between :dtInicio and :dtFim");
            Query query = sessao.createQuery(hql.toString());
            query.setParameter("dtInicio", dataInicial);
            query.setParameter("dtFim", dataFinal);
            Object obj = query.uniqueResult();
            if (obj != null) {
                valorRetorno = Float.valueOf(obj.toString());
            }
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(ChequeDao.class, ex.getMessage(), ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_CHEQUE_REC_SOMATARIO_CHEQUES_PORDATA);
        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        return valorRetorno;
    }

    @Override
    public int recQuantidadeChequesPorIntervaloDatas(Date dataInicial, Date dataFinal) {
        int quantidadeRetorno = 0;
        Session sessao = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hql = new StringBuilder();
            hql.append("SELECT count(c.valor) FROM Cheque c WHERE c.dataBomPara between :dtInicio and :dtFim");
            Query query = sessao.createQuery(hql.toString());
            query.setParameter("dtInicio", dataInicial);
            query.setParameter("dtFim", dataFinal);
            Object obj = query.uniqueResult();
            if (obj != null) {
                quantidadeRetorno = Integer.valueOf(obj.toString());
            }
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(ChequeDao.class, ex.getMessage(), ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_CHEQUE_REC_QUANTIDADE_CHEQUES_PORDATA);
        } finally {
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
        }

        return quantidadeRetorno;
    }
}
