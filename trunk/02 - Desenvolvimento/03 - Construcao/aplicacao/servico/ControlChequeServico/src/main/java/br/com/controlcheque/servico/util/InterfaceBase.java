/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.util;

import java.util.List;

/**
 *
 * @author Robson
 */
public interface InterfaceBase {
    
    public Object salvar(Object param) throws Exception;
    
    public List recTodos() throws Exception;
    
    public Object recObjeto(Object param) throws Exception;
    
    public Object deletar(Object param) throws Exception;
    
    public boolean salvarListObjetos(List lstObjetos) throws Exception;
}
