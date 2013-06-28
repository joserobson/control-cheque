/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Robson
 */
public class LogUtil {

    /**
     * 
     * @param classe
     * @param ex 
     */
    public static void logDescricaoErro(Class classe, ClientChequeException ex) {
        Logger log = Logger.getLogger(classe);
        log.error(new Date() + " - " + ex.getMessage(), ex.getCause());              
    }

    /**
     * 
     * @param classe
     * @param ex 
     */
    public static void logDescricaoErro(Class classe, Exception ex) {
        Logger log = Logger.getLogger(classe);
        log.error(new Date() + " - " + ex.getMessage(), ex.getCause());

    }

    /**
     * 
     * @param inf 
     */
    public static void logInformacao(String inf) {
        Logger log = Logger.getLogger("INFORMACAO");
        StringBuilder str = new StringBuilder();
        str.append(new Date());
        str.append(inf);
        log.info(str.toString());
    }
}
