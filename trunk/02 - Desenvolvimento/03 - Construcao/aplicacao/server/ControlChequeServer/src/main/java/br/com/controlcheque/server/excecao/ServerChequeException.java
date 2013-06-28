/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.excecao;

/**
 *
 * @author Robson
 */
public class ServerChequeException extends Exception {

    public ServerChequeException(Throwable cause) {
        super(cause);
    }

    public ServerChequeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerChequeException(String message) {
        super(message);
    }

    public ServerChequeException() {
    }


}
