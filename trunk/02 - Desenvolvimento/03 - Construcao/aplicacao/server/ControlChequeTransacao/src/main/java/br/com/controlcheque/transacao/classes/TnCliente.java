/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.classes;

import br.com.controlcheque.server.bo.ClienteBoImpl;
import br.com.controlcheque.server.model.Cliente;
import br.com.controlcheque.transacao.constantes.ConstanteCliente;
import br.com.controlcheque.transacao.constantes.ConstanteGenerica;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import br.com.controlcheque.transacao.util.TransacaoUtil;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *Classe para tratar eventos de transação para Cliente
 * @author Jose Robson
 */
public class TnCliente extends TnBase {

    /**
     * ATRIBUTOS DA CLASSE
     */
    private Cliente cliente = null;
    private ClienteBoImpl clienteBo = null;

    public TnCliente() {
        this.clienteBo = (ClienteBoImpl) TransacaoUtil.getInstanceBo(Cliente.class);
    }

    /**
     * METODO ACESSORES PARA CLIENTE
     * @return
     */
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * METODOS DA TRANSACAO
     */
    /**
     * Salvar Cliente
     * @param cliente
     * @return
     */
    @Override
    public Object salvar(String event) throws TransacaoChequeException {
        boolean retorno = true;
        try {
            if (validarDados(event)) {
                clienteBo.mantemCliente(cliente);
            }
        } catch (HibernateException ex) {
            retorno = false;
            throw new TransacaoChequeException(ex);
        }

        return retorno;
    }

    /**
     * valida dados antes de qualquer operacao
     * @return
     */
    @Override
    public boolean validarDados(String event) throws TransacaoChequeException {
        boolean retorno = true;

        if (event.equals(ConstanteCliente.NOME_EVENTO_SALVAR_CLIENTE)) {

            //valida campo obrigatorio codigo
            if (cliente.getCodigo().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteGenerica.CODIGO_ERRO_CODIGO_OBRIGATORIO);
            }

            //valida campo obrigatorio nome
            if (cliente.getNome().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteGenerica.CODIGO_ERRO_NOME_OBRIGATORIO);
            }

            //valida campo obrigatorio telefone
            if (cliente.getTelefone().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteGenerica.CODIGO_ERRO_TELEFONE_OBRIGATORIO);
            }

        }


        return retorno;
    }

    /**
     * gerar código sequencial para o cliente
     * @return
     */
    public String gerarCodigoCliente(String evento) throws TransacaoChequeException {
        String codigoRetorno = "-1";
        try {
            codigoRetorno = clienteBo.recuperarMaxCodigoCliente();

        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return codigoRetorno;
    }

    /**
     * Metodo que busca todos os clientes ativos
     * @return
     */
    public List<Cliente> recuperarTodos(String evento) throws TransacaoChequeException {
        List<Cliente> retornoLista = null;
        try {
            retornoLista = clienteBo.consultarCliente();

        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retornoLista;
    }

    /**
     * 
     * @param evento
     * @return
     * @throws TransacaoChequeException
     */
    @Override
    public Object deletar(String evento) throws TransacaoChequeException {
        Cliente retorno = null;
        try {
            if (this.validarDados(evento)) {
                retorno = clienteBo.deletaCliente(cliente);
            }
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }

        return retorno;
    }
}
