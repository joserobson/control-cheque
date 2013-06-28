/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.bo;

import br.com.controlcheque.server.dao.ClienteDao;
import br.com.controlcheque.server.model.Cliente;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ClienteBo <T extends Cliente> {

    public void setClienteDao(ClienteDao clienteDao);

    public void mantemCliente(T c);

    public List<T> consultarCliente();

    public String recuperarMaxCodigoCliente();
    
    public T deletaCliente(T cliente);

}
