/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

import br.com.controlcheque.client.ServicesImpl.ChequeServerImpl;
import br.com.controlcheque.service.Cheque;
import java.util.List;

/**
 *
 * @author Robson
 */
public class UtilServiceCheque {

    public List<Cheque> recChequeDoInBackground(int situacao) throws Exception {
        List<Cheque> lstCheques = null;
        try {
            lstCheques = new ChequeServerImpl().recChequesPorSituacao(situacao);
        } catch (Exception ex) {
            throw ex;
        }

        return lstCheques;
    }
}
