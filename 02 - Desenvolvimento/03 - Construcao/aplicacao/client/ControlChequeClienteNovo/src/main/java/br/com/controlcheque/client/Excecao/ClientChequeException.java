/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.Excecao;

/**
 *
 * @author Robson
 */
public class ClientChequeException extends Exception {

    public ClientChequeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ClientChequeException(Throwable cause) {
        super(cause);
    }

    public ClientChequeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientChequeException(String message) {
        super(message);
    }

    public ClientChequeException() {
    }
}
