/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.ServicesImpl;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.service.Usuario;

/**
 *
 * @author Jose Robson
 */
public class UsuarioServerImpl extends BaseServerImpl {

    public UsuarioServerImpl() {
        super();
    }

    /**
     *
     * @param pObject
     * @return
     * @throws ClientChequeException
     */
    @Override
    public Object mantemObject(Object pObject) throws ClientChequeException {
        Usuario retorno = null;
        try {
            retorno = (Usuario) port.salvarUsuario((Usuario) pObject);
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     *
     * @param pUsuario
     * @return
     * @throws ClientChequeException
     */
    public Usuario loginUsuario(Usuario pUsuario) throws ClientChequeException {
        Usuario retorno = null;
        try {
            Object ret = port.loginUsuario(pUsuario);
            retorno = port.loginUsuario(pUsuario) != null ? (Usuario) port.loginUsuario(pUsuario) : null;
        } catch (Exception ex) {
            throw new ClientChequeException(ex);
        }
        return retorno;
    }
}
