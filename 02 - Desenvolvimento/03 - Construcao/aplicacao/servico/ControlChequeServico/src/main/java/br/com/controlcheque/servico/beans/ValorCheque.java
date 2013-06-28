/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.beans;

/**
 *
 * @author Jose Robson
 */
public class ValorCheque {

    private float valor;
    private int quantidade;

    public ValorCheque() {
    }

    public ValorCheque(float valor, int quantidade) {
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
