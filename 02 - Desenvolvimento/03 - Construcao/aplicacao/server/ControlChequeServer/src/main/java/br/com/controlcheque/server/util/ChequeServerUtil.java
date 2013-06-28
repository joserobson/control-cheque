/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.util;

/**
 *
 * @author Robson
 */
public class ChequeServerUtil {

    private static final int TAMANHO_MAX_PADRAO_CODIGO = 4;

    /**
     * METODO UTIL PARA GERAR CODIGO NO PADRAO DO SISTEMA 
     * @param pcodigo
     * @return
     */
    public static String gerarCodigoFormatoPadrao(int pcodigo) {
        String lCodigo = "0001";
        if (pcodigo != -1) {
            lCodigo = String.valueOf(pcodigo);
            int tam = lCodigo.length();
            for (int i = 0; i < TAMANHO_MAX_PADRAO_CODIGO - tam; i++) {
                lCodigo = "0" + lCodigo;
            }
        }

        return lCodigo;
    }
}
