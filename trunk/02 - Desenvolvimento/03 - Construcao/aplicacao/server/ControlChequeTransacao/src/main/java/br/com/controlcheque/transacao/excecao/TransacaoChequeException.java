/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.transacao.excecao;

/**
 *
 * @author Administrador
 */
public class TransacaoChequeException extends Exception{

    public TransacaoChequeException(Throwable cause) {
        super(cause);
    }

    public TransacaoChequeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransacaoChequeException(String message) {
        super(message);
    }

    public TransacaoChequeException() {
    }





}
