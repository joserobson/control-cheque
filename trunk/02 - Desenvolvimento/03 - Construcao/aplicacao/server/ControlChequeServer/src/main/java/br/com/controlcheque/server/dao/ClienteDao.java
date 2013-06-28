/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.model.Cliente;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface ClienteDao <T extends Cliente>{

    public T mantemCliente(T c);

    public List<T> consultarCliente();

    public int recuperarMaxCodigoCliente();
    
    public T deletarCliente(T cliente);
}
