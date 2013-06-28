/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.classes;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.server.model.Cliente;
import br.com.controlcheque.transacao.classes.TnCliente;
import br.com.controlcheque.transacao.constantes.ConstanteCliente;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import java.util.List;

/**
 *
 * @author Robson
 */
public class ClienteServerImpl {

    /************************************************************************
     * INICIO METODOS QUE FAZEM CHAMADAS AO SERVER
     ************************************************************************/
    /**
     * metodo para gerar um novo codigo para o objeto cliente
     * @return
     */
    public String gerarCodigoCliente() throws ClientChequeException {
        String codigo = "-1";

        try {
            TnCliente tn = new TnCliente();
            codigo = tn.gerarCodigoCliente(ConstanteCliente.NOME_EVENTO_GERAR_COD_CLIENTE);
        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }

        return codigo;
    }

    /**
     * MantemCliente
     * @param cliente
     * @return
     */
    public boolean mantemCliente(Cliente cliente) throws ClientChequeException {
        boolean retorno = true;
        try {
            TnCliente tn = new TnCliente();
            tn.setCliente(cliente);
            retorno = (Boolean) tn.salvar(ConstanteCliente.NOME_EVENTO_SALVAR_CLIENTE);
        } catch (TransacaoChequeException ex) {
            retorno = false;
            throw new ClientChequeException(ex);
        }
        return retorno;
    }

    /**
     * Metodo que retorna todos os clientes ativos do sistema
     * @return
     */
    public List<Cliente> getTodosClientes() throws ClientChequeException {
        List<Cliente> lListaClientes = null;
        try {
            TnCliente tnCliente = new TnCliente();
            lListaClientes = tnCliente.recuperarTodos(ConstanteCliente.NOME_EVENTO_BUSCAR_TODOS_CLIENTE);
        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }
        return lListaClientes;
    }

    /**
     * Metodo que salva todos os clientes da lista
     * @return
     */
    public boolean salvarListaClientes(List<Cliente> pLstClientes) throws ClientChequeException {
        boolean retorno = true;
        try {
            TnCliente tnCliente = new TnCliente();

            for (Cliente cliente : pLstClientes) {
                tnCliente.setCliente(cliente);
                retorno = (Boolean) tnCliente.salvar(ConstanteCliente.NOME_EVENTO_SALVAR_LISTA_CLIENTE);

            }

        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * DELETA CLIENTE
     * @param b
     * @return
     */
    public Cliente deletarCliente(Cliente c) throws ClientChequeException {
        Cliente retorno = null;
        try {
            TnCliente tnCliente = new TnCliente();
            tnCliente.setCliente(c);
            retorno = (Cliente) tnCliente.deletar(ConstanteCliente.NOME_EVENTO_DELETAR_CLIENTE);
        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }
        return retorno;
    }
    /************************************************************************
     * FIM METODOS QUE FAZEM CHAMADAS AO SERVER
     ************************************************************************/
}
