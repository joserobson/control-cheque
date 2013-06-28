/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.ServicesImpl;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.service.Banco;
import java.util.List;

/**
 *
 * @author Robson
 */
public class BancoServerImpl extends BaseServerImpl {
    
    /**
     * Construtor
     * @throws Exception 
     */
    public BancoServerImpl() {
        super();
    }

    /**************************************************************************
     * INICIO CHAMADA METODOS DO SERVER
     *************************************************************************/
    /**
     * Mantem Object
     * @param pObject
     * @return
     */
    @Override
    public Object mantemObject(Object pObject) throws ClientChequeException {

        try {
            Banco result = (Banco) this.port.salvarBanco((Banco)pObject);
            return result;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            throw new ClientChequeException(ex);
        }

    }

    /***
     * Retorna todos os bancos cadastrados
     * @return
     * @throws ClientChequeException 
     */
    @Override
    public List recTodos() throws ClientChequeException {
        try {
            List result = port.recTodosBancos();
            return result;
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

    }

    @Override
    public Object deletar(Object pObject) throws ClientChequeException {
        Object result = null;
        try {
            result = port.deletarBanco((Banco)pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return result;
    }

    /**
     * Metodo que salva todos os Bancos da lista
     * @return
     */
    @Override
    public boolean salvarListObject(List plistObjects) throws ClientChequeException {
        boolean result = false;
        try {
            result = port.salvarListBancos(plistObjects);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }
        return result;

    }
    /**************************************************************************
     * FIM CHAMADA METODOS DO SERVER
     *************************************************************************/
}
