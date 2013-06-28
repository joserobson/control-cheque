/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dominio;

/**
 *
 * @author Robson
 */
public enum EStatusCheque {

    ativo(1),
    removido(2);
    private String descricao;

    private EStatusCheque(int codigo) {
        if (codigo == 1) {
            descricao = "Ativo";
        } else {
            descricao = "Removido";
        }
    }

    public int getCodigoStatusCheque() {
        if (this.equals(ativo)) {
            return 1;
        } else {
            return 2;
        }
    }

    public static EStatusCheque getStatusCheque(int codigo) {
        if (codigo == 1) {
            return EStatusCheque.ativo;
        }
        return EStatusCheque.removido;

    }
}
