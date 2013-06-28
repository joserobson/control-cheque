/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.ServicesImpl;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.service.WsControlChequeService_Service;
import java.util.List;

/**
 *
 * @author Robson
 */
public class BaseServerImpl implements InterfaceServiceImpl{

    private br.com.controlcheque.service.WsControlChequeService_Service service;
    protected br.com.controlcheque.service.WsControlChequeService port;

    public BaseServerImpl() {
        this.service = new WsControlChequeService_Service();
        this.port = service.getWsControlChequeServicePort();
    }       
    
    @Override
    public Object mantemObject(Object pObject) throws ClientChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List recTodos() throws ClientChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object deletar(Object pObject) throws ClientChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean salvarListObject(List pListObjects) throws ClientChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object recObjeto(Object pObject) throws ClientChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
