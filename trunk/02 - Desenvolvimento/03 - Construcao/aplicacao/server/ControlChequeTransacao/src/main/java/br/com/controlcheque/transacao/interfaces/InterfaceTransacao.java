/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.interfaces;

import br.com.controlcheque.transacao.excecao.TransacaoChequeException;

/**
 *
 * @author Jose Robson
 */
public interface InterfaceTransacao {

    public Object salvar(String evento) throws TransacaoChequeException;

    public boolean validarDados(String evento) throws TransacaoChequeException;

    //public List<Object> recuperarTodos(String evento);
    public Object deletar(String evento) throws TransacaoChequeException;

    public Object recObjectPorId(String evento) throws TransacaoChequeException;
}
