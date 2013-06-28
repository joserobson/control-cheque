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
public class UsuarioBoImpl<T extends Usuario> implements UsuarioBo<Usuario> {

    private UsuarioDao usuarioDao;

    @Override
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public Usuario mantemUsuario(Usuario pUsuario) {
        return usuarioDao.mantemUsuario(pUsuario);
    }

    @Override
    public Usuario loginUsuario(Usuario pUsuario) {
        return usuarioDao.loginUsuario(pUsuario);
    }

    @Override
    public Usuario recUsuarioPorLogin(String login) {
        return usuarioDao.recUsuarioPorLogin(login);
    }
}
