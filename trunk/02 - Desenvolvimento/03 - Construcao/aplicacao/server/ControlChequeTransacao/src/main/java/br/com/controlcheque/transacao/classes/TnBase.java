/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.classes;

import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import br.com.controlcheque.transacao.interfaces.InterfaceTransacao;

/**
 *
 * @author Robson
 */
public class TnBase implements InterfaceTransacao {

    @Override
    public Object salvar(String evento) throws TransacaoChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean validarDados(String evento) throws TransacaoChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object deletar(String evento) throws TransacaoChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object recObjectPorId(String evento) throws TransacaoChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
