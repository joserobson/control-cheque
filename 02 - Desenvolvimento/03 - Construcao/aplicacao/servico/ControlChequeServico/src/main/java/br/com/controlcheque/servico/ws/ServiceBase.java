/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.ws;

import br.com.controlcheque.servico.util.InterfaceBase;
import java.util.List;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

/**
 *
 * @author Robson
 */
public class ServiceBase implements InterfaceBase {

    protected Mapper mapper = null;

    public ServiceBase() {
        this.mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    @Override
    public Object salvar(Object param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List recTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object recObjeto(Object param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object deletar(Object param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean salvarListObjetos(List lstObjetos) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
