/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.ServicesImpl;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import java.util.List;

/**
 * Interface para metodos ServiceImpl
 * @author Robson
 */
interface InterfaceServiceImpl {

    public Object mantemObject(Object pObject) throws ClientChequeException;

    public List recTodos() throws ClientChequeException;

    public Object deletar(Object pObject) throws ClientChequeException;

    public boolean salvarListObject(List pListObjects) throws ClientChequeException;
    
    public Object recObjeto(Object pObject) throws ClientChequeException;
}
