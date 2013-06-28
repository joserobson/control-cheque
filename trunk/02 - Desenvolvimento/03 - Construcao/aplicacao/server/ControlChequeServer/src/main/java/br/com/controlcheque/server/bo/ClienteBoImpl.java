/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.bo;

import br.com.controlcheque.server.dao.ClienteDao;
import br.com.controlcheque.server.model.Cliente;
import br.com.controlcheque.server.util.ChequeServerUtil;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class ClienteBoImpl<T extends Cliente> implements ClienteBo<T> {

    private ClienteDao clienteDao;

    @Override
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public void mantemCliente(Cliente c) {
        clienteDao.mantemCliente(c);
    }

    @Override
    public List<T> consultarCliente() {
        return clienteDao.consultarCliente();
    }

    @Override
    public String recuperarMaxCodigoCliente() {

        int lCodigo = clienteDao.recuperarMaxCodigoCliente();
        return ChequeServerUtil.gerarCodigoFormatoPadrao(lCodigo);

    }

    @Override
    public T deletaCliente(T cliente) {
        return (T)clienteDao.deletarCliente(cliente);
    }
}
