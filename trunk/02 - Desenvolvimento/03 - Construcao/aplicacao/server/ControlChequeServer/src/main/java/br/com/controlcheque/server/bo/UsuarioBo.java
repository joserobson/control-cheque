/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.bo;

import br.com.controlcheque.server.dao.UsuarioDao;
import br.com.controlcheque.server.model.Usuario;

/**
 *
 * @author Jose Robson
 */
public interface UsuarioBo <T extends Usuario>{
    
    public void setUsuarioDao(UsuarioDao usuarioDao);
    
    public T mantemUsuario(T pUsuario);
    
    public T loginUsuario(T pUsuario);
    
    public T recUsuarioPorLogin(String login);
}
