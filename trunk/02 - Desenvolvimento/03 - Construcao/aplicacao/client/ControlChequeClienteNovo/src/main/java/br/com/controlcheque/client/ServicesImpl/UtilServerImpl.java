/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.ServicesImpl;

import br.com.controlcheque.client.Excecao.ClientChequeException;

/**
 *
 * @author Jose Robson
 */
public class UtilServerImpl extends BaseServerImpl {

    public UtilServerImpl() {
        super();
    }

    public void gerarBackup() throws ClientChequeException {

        try {
            this.port.backupControlCheque();
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

    }
}
