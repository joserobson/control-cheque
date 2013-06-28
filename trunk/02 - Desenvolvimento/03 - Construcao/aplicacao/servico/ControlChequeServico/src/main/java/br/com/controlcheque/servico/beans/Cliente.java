/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.beans;

import java.io.Serializable;

/**
 *
 * @author Robson
 */
public class Cliente implements Serializable {

    private String id;
    private String codigo;
    private String nome;
    private String telefone;
    private String rua_Endereco;
    private String bairro_Endereco;
    private String cidade_Endereco;
    private String complemento_Endereco;
    private String numero_Endereco;

    public Cliente() {
    }

    public String getBairro_Endereco() {
        return bairro_Endereco;
    }

    public void setBairro_Endereco(String bairro_Endereco) {
        this.bairro_Endereco = bairro_Endereco;
    }

    public String getCidade_Endereco() {
        return cidade_Endereco;
    }

    public void setCidade_Endereco(String cidade_Endereco) {
        this.cidade_Endereco = cidade_Endereco;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getComplemento_Endereco() {
        return complemento_Endereco;
    }

    public void setComplemento_Endereco(String complemento_Endereco) {
        this.complemento_Endereco = complemento_Endereco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero_Endereco() {
        return numero_Endereco;
    }

    public void setNumero_Endereco(String numero_Endereco) {
        this.numero_Endereco = numero_Endereco;
    }

    public String getRua_Endereco() {
        return rua_Endereco;
    }

    public void setRua_Endereco(String rua_Endereco) {
        this.rua_Endereco = rua_Endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
