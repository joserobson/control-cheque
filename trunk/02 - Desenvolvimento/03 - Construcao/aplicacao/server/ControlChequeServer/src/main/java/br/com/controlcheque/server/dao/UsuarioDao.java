/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.model.Usuario;

/**
 *
 * @author Jose Robson
 */
public interface UsuarioDao <T extends Usuario> {
 
    public T mantemUsuario(T usuario);
    
    public T loginUsuario(T usuario);
    
    public T recUsuarioPorLogin(String login);
}
