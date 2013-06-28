/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Robson
 */
@Entity
@Table(name="tb_cheque")
public class Cheque implements Serializable{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "idCheque", unique = true)
    private String id;;

    @Column(name="contaBanco")
    private String conta;
    @Column(name="agenciaBanco")
    private String agencia;
    @Column(name="numeroCheque")
    private String numero;
    @Column(name="titularCheque")
    private String titular;
    @Column(name="cpfTitular")
    private String cpfTitular;
    @Column(name="telefoneTitular")
    private String telefoneTitular;
    @Column(name="dataBomPara")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataBomPara;
    @Column(name="dataEmissao")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataEmissao;
    @Column(name="obsCheque")
    private String observacao;
    @Column(name="situacaoCheque")
    private int situacaoCheque;
    @Column(name="valorCheque")
    private float valor;
    @Column(name="recebiDe")
    private String recebiDe;
    @Column(name="passeiPara")
    private String passeiPara;
    @Column(name="statusCheque")
    private int statusCheque;
    @Column(name="codigoCheque")
    private String codigoCheque;

    @OneToOne
    @JoinColumn(name="idbanco")
    private Banco banco;
    

    public Cheque() {
    }

    

    public String getCodigoCheque() {
        return codigoCheque;
    }

    public void setCodigoCheque(String codigoCheque) {
        this.codigoCheque = codigoCheque;
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
