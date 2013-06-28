/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.util;

import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Robson
 */
public class LogChequeUtil {
    /**
     *
     * @param classe
     * @param nomeMetodo
     */
    public static void logInicioProcessoMetodo(Class classe, String nomeMetodo)
    {
       Logger log = Logger.getLogger(classe);
       StringBuilder str = new StringBuilder();       
       str.append(new Date());
       str.append("INICIO PROCESSAMENTO METODO - ");
       str.append(nomeMetodo).append("=").append('\n');
       log.info(str.toString());
    }

    /**
     * 
     * @param classe
     * @param nomeMetodo
     */
    public static void logFimProcessoMetodo(Class classe, String nomeMetodo)
    {
       Logger log = Logger.getLogger(classe);
       StringBuilder str = new StringBuilder();
       str.append(new Date());
       str.append("FIM PROCESSAMENTO METODO - ");
       str.append(nomeMetodo).append("=").append('\n');
       log.info(str.toString());
    }

    /**
     * 
     * @param classe
     * @param descErro
     * @param t
     */
    public static void logDescricaoErro(Class classe, String descErro, Throwable t)
    {
        Logger log = Logger.getLogger(classe);
        log.error(new Date() + " - ERRO CONTROL-CHEQUE: " + descErro, t);
    }

    /**
     *
     * @param classe
     */
    public static void logSucesso(Class classe, String nomeMetodo, String id)
    {
        Logger log = Logger.getLogger(classe);
        log.error(new Date() + " - OPERAÇÃO REALIZADA COM SUCESSO - METODO: " + nomeMetodo + " ID: " + id);
    }
}
