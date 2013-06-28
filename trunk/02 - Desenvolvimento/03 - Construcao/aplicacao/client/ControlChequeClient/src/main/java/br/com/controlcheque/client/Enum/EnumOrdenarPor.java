/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.client.Enum;

/**
 *
 * @author Jose RObson
 */
public enum EnumOrdenarPor {

    CODIGO (1),
    NOME (2),
    DESCRICAO(3);

    private final int VALOR;

    private EnumOrdenarPor(int valor) {
        this.VALOR = valor;
    }

    public int getValor()
    {
        return VALOR;
    }

}
