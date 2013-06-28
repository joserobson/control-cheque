/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.Enum;

/**
 *
 * @author Robson
 */
public enum EnumOrdenarPorCheque {

    CODIGO(1),
    RECEBIDE(2),
    BOMPARA(3),
    TITULAR(4),
    VALOR(5),
    PASSEIPARA(6);
    private final int codigo;

    private EnumOrdenarPorCheque(int valor) {
        this.codigo = valor;
    }

    public int getCodigo() {
        return codigo;
    }
}
