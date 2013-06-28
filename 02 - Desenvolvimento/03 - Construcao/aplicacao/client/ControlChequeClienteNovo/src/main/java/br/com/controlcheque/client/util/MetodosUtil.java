/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

import java.text.DecimalFormat;
import java.text.Normalizer;

/**
 *
 * @author Robson
 */
public class MetodosUtil {

    /**
     * Metodo para retirar acentos de uma string
     * @param nome
     * @return
     */
    public static String retirarAcentos(String nome) {
        nome = nome.replaceAll(" ", "_");
        nome = Normalizer.normalize(nome, Normalizer.Form.NFD);
        nome = nome.replaceAll("[^\\p{ASCII}]", "");
        return nome;
    }

    public static Double converterStringToDouble(String valor) {
        Double retorno = 0.00;

        try {
            //retirar simbolo R$
            valor = valor.replace("R$", "");

            //tira o ponto
            valor.replace(".", "");

            //troca virgula por ponto
            valor = valor.replace(",", ".");

            retorno = Double.parseDouble(valor);
        } catch (NumberFormatException ex) {
            LogUtil.logDescricaoErro(MetodosUtil.class, ex);
        }

        return retorno;
    }

    public static float converterStringToFloat(String valor) {
        float retorno = 0;

        try {
            //retirar simbolo R$
            valor = valor.replace("R$", "");

            //tira o ponto
            valor = valor.replace(".", "");

            //troca virgula por ponto
            valor = valor.replace(",", ".");



            retorno = Float.parseFloat(valor);
        } catch (NumberFormatException ex) {
            LogUtil.logDescricaoErro(MetodosUtil.class, ex);
        }

        return retorno;
    }

    public static String convertFloatToString(Float valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(valor);
    }
}
