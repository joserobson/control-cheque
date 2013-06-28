/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Robson
 */
public class Cheque implements Serializable {

    private String id;
    private String conta;
    private String agencia;
    private String numero;
    private String titular;
    private String cpfTitular;
    private String telefoneTitular;
    private Date dataBomPara;
    private Date dataEmissao;
    private String observacao;
    private int situacaoCheque;
    private float valor;
    private String recebiDe;
    private String passeiPara;
    private int statusCheque;
    private Banco banco;
    private String codigoCheque;

    public Cheque() {
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getCodigoCheque() {
        return codigoCheque;
    }

    public void setCodigoCheque(String codigoCheque) {
        this.codigoCheque = codigoCheque;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public Date getDataBomPara() {
        return dataBomPara;
    }

    public void setDataBomPara(Date dataBomPara) {
        this.dataBomPara = dataBomPara;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getPasseiPara() {
        return passeiPara;
    }

    public void setPasseiPara(String passeiPara) {
        this.passeiPara = passeiPara;
    }

    public String getRecebiDe() {
        return recebiDe;
    }

    public void setRecebiDe(String recebiDe) {
        this.recebiDe = recebiDe;
    }

    public int getSituacaoCheque() {
        return situacaoCheque;
    }

    public void setSituacaoCheque(int situacaoCheque) {
        this.situacaoCheque = situacaoCheque;
    }

    public int getStatusCheque() {
        return statusCheque;
    }

    public void setStatusCheque(int statusCheque) {
        this.statusCheque = statusCheque;
    }

    public String getTelefoneTitular() {
        return telefoneTitular;
    }

    public void setTelefoneTitular(String telefoneTitular) {
        this.telefoneTitular = telefoneTitular;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
