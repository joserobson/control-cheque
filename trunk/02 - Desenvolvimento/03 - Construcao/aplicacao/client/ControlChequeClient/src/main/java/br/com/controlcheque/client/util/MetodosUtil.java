/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.client.util;

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
}
