/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dominio;

/**
 *
 * @author Administrador
 */
public enum ESituacaoCheque {

    EmMaos(1),
    Todos(4),
    Devolvido(2),
    Repassado(3),;
    private String descricao;

    private ESituacaoCheque(int codigo) {
        switch (codigo) {
            case 1:
                descricao = "EmMaos";
            case 2:
                descricao = "Devolvido";
            case 3:
                descricao = "Repassado";
            default:
                descricao = "Todos";
        }

    }

    public int getCodigoSituacaoCheque() {
        switch (this) {
            case EmMaos:
                return 1;
            case Devolvido:
                return 2;
            case Repassado:
                return 3;
            default:
                return 4;
        }
    }
    
    public static ESituacaoCheque getSituacaoCheque(int codigo)
    {
        switch(codigo)
        {
            case 1:
                return ESituacaoCheque.EmMaos;
            case 2:
                return ESituacaoCheque.Devolvido;
            case 3:
                return ESituacaoCheque.Repassado;
            default:
                return ESituacaoCheque.Todos;
        }
    }
}
