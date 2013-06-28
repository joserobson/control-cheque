package br.com.controlcheque.client.ServicesImpl;

///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.service.Cliente;

import java.util.List;

//
///**
// *
// * @author Robson
// */
public class ClienteServerImpl extends BaseServerImpl {

    /**
     * Constructor
     */
    public ClienteServerImpl() {
        super();
    }

    /************************************************************************
     * INICIO METODOS QUE FAZEM CHAMADAS AO SERVER
     ************************************************************************/
    /**
     * Metodo para gerar codigo para um cliente
     * @return
     * @throws ClientChequeException 
     */
    public String gerarCodigoCliente() throws ClientChequeException {
        String codigo = "-1";
        try {
            codigo = port.gerarCodigoCliente();
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return codigo;
    }

    /**
     * Metodo para salvar um cliente
     * @param cliente
     * @return
     * @throws ClientChequeException 
     */
    @Override
    public Object mantemObject(Object pObject) throws ClientChequeException {
        Cliente retorno = null;
        try {
            retorno = (Cliente) port.salvarCliente((Cliente)pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * Metodo que retorna todos os clientes ativos do sistema
     * @return
     */
    @Override
    public List recTodos() throws ClientChequeException {
        List listaClientes = null;
        try {
            listaClientes = port.recTodosClientes();
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }
        return listaClientes;
    }

    /**
     * Metodo que salva todos os clientes da lista
     * @return
     */
    @Override
    public boolean salvarListObject(List pListObjects) throws ClientChequeException {
        boolean retorno = true;
        try {
            retorno = port.salvarListClientes(pListObjects);

        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * DELETA CLIENTE
     * @param b
     * @return
     */
    @Override
    public Object deletar(Object pObject) throws ClientChequeException {
        Cliente retorno = null;

        try {
            retorno = (Cliente) port.deletarCliente((Cliente)pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }
    /************************************************************************
     * FIM METODOS QUE FAZEM CHAMADAS AO SERVER
     ************************************************************************/
}
