/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.classes;

import br.com.controlcheque.server.bo.BancoBoImpl;
import br.com.controlcheque.server.bo.ChequeBoImpl;
import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.server.model.Cheque;
import br.com.controlcheque.transacao.constantes.ConstanteBanco;
import br.com.controlcheque.transacao.constantes.ConstanteGenerica;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import br.com.controlcheque.transacao.util.TransacaoUtil;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author Jose Robson
 */
public class TnBanco extends TnBase {

    /**
     * ATRIBUTOS DA CLASSE
     */
    private Banco banco = null;
    private BancoBoImpl bancoBo = null;

    public TnBanco() {
        this.bancoBo = (BancoBoImpl) TransacaoUtil.getInstanceBo(Banco.class);
    }

    /**
     * METODOS ACESSORES PARA BANCO
     * @return
     */
    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    /**
     * METODOS DA TRANSACAO
     */
    /**
     *
     * @param event
     * @return
     * @throws Exception
     */
    @Override
    public Object salvar(String event) throws TransacaoChequeException {
        Banco retorno = null;
        try {
            if (validarDados(event)) {
                retorno = bancoBo.mantemBanco(banco);
            }
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retorno;
    }

    /**
     * Valida Dados campos obrigatórios
     * @param event
     * @return
     * @throws Exception
     */
    @Override
    public boolean validarDados(String event) throws TransacaoChequeException {
        boolean retorno = true;

        if (event.equals(ConstanteBanco.NOME_EVENTO_SALVAR_BANCO)) {
            //valida campo obrigatorio codigo
            if (banco.getCodigo().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteGenerica.CODIGO_ERRO_CODIGO_OBRIGATORIO);
            }

            //valida campo obrigatorio nome
            if (banco.getDescricao().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteGenerica.CODIGO_ERRO_DESCRICAO_OBRIGATORIO);
            }
        } else {
            if (event.equals(ConstanteBanco.NOME_EVENTO_DELETAR_BANCO)) {
                ChequeBoImpl chequeBo = (ChequeBoImpl) TransacaoUtil.getInstanceBo(Cheque.class);
                List<Cheque> lst = chequeBo.consultaChequesPorBanco(banco);
                if (lst != null
                        && lst.size() > 0) {
                    retorno = false;
                    throw new TransacaoChequeException(ConstanteGenerica.CODIGO_MENSAGEM_OBJETO_NAO_PODE_SER_REMOVIDO);
                }
            }
        }

        return retorno;
    }

    /**
     * Recupera todos os banco cadastrados
     * @param evento
     * @return
     */
    public List<Banco> recuperarTodos(String evento) throws TransacaoChequeException {
        List<Banco> retornoLista = null;
        try {
            retornoLista = bancoBo.consultarBanco();

        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retornoLista;
    }

    /**
     * DELETA BANCO PASSADO COMO PARAMETRO
     * @param obj
     * @return
     */
    @Override
    public Object deletar(String event) throws TransacaoChequeException {
        Banco retorno = null;
        try {
            if (this.validarDados(event)) {                
                retorno = bancoBo.deleteBanco(banco);
            }
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retorno;
    }
}
