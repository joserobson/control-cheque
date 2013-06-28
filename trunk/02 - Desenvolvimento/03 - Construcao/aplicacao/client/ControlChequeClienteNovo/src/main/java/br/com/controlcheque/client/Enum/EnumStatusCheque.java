/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.Enum;

/**
 *
 * @author Robson
 */
public enum EnumStatusCheque {

    Ativo(1),
    Removido(2);
    private final int codigo;

    private EnumStatusCheque(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
