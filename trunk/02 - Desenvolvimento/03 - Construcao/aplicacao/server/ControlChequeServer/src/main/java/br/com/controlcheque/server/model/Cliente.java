/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Administrador
 */

@Entity
@Table(name="tb_cliente")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "idCliente", unique = true)
    private String id;

    @Column(name="codigoCliente")
    private String codigo;
    @Column(name="nomeCliente")
    private String nome;
    @Column(name="telefoneCliente")
    private String telefone;
    @Column(name="ruaCliente")
    private String rua_Endereco;
    @Column(name="bairroCliente")
    private String bairro_Endereco;
    @Column(name="cidadeCliente")
    private String cidade_Endereco;
    @Column(name="complementoCliente")
    private String complemento_Endereco;
    @Column(name="numeroCliente")
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
