/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.Enum;

/**
 *
 * @author Robson
 */
public enum EnumSituacaoCheque {

    EmMaos(1),
    Devolvido(2),
    Repassado(3),
    Todos(4);
    private final int Codigo;

    private EnumSituacaoCheque(int Codigo) {
        this.Codigo = Codigo;
    }

    public int getCodigo() {
        return Codigo;
    }

    public static EnumSituacaoCheque getEnumSituacaoCheque(int codigo) {
        switch (codigo) {
            case 1:
                return EmMaos;
            case 2:
                return Devolvido;
            case 3:
                return Repassado;

        }

        return Todos;
    }
}
