/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

import br.com.controlcheque.client.ServicesImpl.BancoServerImpl;
import br.com.controlcheque.client.ServicesImpl.ChequeServerImpl;
import br.com.controlcheque.client.ServicesImpl.ClienteServerImpl;
import br.com.controlcheque.service.Banco;
import br.com.controlcheque.service.Cheque;
import br.com.controlcheque.service.Cliente;
import java.util.List;

/**
 *
 * @author Robson
 */
public class DadosCadastroCheque {

    private Cheque cheque;
    private String codigo;
    private List<Banco> lstBancos;
    private List<Cliente> lstClientes;
    private List<Cheque> lstCheques;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Banco> getLstBancos() {
        return lstBancos;
    }

    public void setLstBancos(List<Banco> lstBancos) {
        this.lstBancos = lstBancos;
    }

    public List<Cliente> getLstClientes() {
        return lstClientes;
    }

    public void setLstClientes(List<Cliente> lstClientes) {
        this.lstClientes = lstClientes;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public List<Cheque> getLstCheques() {
        return lstCheques;
    }

    public void setLstCheques(List<Cheque> lstCheques) {
        this.lstCheques = lstCheques;
    }

    /**
     * Busca dados Cheque
     * @return
     * @throws Exception 
     */
    public static DadosCadastroCheque getDadosCadastroCheque() throws Exception {
        DadosCadastroCheque dadosCheque = new DadosCadastroCheque();
        //busca codigo        
        dadosCheque.setCodigo(buscaCodigo());

        //lista bancos        
        dadosCheque.setLstBancos(buscaListaBancos());

        //lista Clientes
        ClienteServerImpl clienteServer = new ClienteServerImpl();
        dadosCheque.setLstClientes(clienteServer.recTodos());

        return dadosCheque;
    }

    //busca codigo
    public static String buscaCodigo() throws Exception {
        ChequeServerImpl chequeServer = new ChequeServerImpl();
        return chequeServer.gerarCodigo();
    }

    public static List<Banco> buscaListaBancos() throws Exception {
        BancoServerImpl bancoServer = new BancoServerImpl();
        return bancoServer.recTodos();
    }

    public static List<Cliente> buscaListaCliente() throws Exception {
        ClienteServerImpl clienteServer = new ClienteServerImpl();
        return clienteServer.recTodos();
    }
}
