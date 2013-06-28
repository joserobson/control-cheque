/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.classes;

import br.com.controlcheque.server.bo.UsuarioBo;
import br.com.controlcheque.server.bo.UsuarioBoImpl;
import br.com.controlcheque.server.model.Usuario;
import br.com.controlcheque.transacao.constantes.ConstanteUsuario;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import br.com.controlcheque.transacao.util.TransacaoUtil;
import org.hibernate.HibernateException;

/**
 *
 * @author Jose Robson
 */
public class TnUsuario extends TnBase {

    /**
     * ATRIBUTOS DA CLASSE
     */
    private Usuario usuario = null;
    private UsuarioBo usuarioBo = null;

    /**
     * Construtor
     */
    public TnUsuario() {
        this.usuarioBo = (UsuarioBoImpl) TransacaoUtil.getInstanceBo(Usuario.class);
    }

    /**
     * METODOS ACESSORES
     */
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioBo getUsuarioBo() {
        return usuarioBo;
    }

    public void setUsuarioBo(UsuarioBo usuarioBo) {
        this.usuarioBo = usuarioBo;
    }

    /**
     * Salvar usuario
     *
     * @param evento
     * @return
     * @throws TransacaoChequeException
     */
    @Override
    public Object salvar(String evento) throws TransacaoChequeException {

        Usuario retorno = null;
        try {
            if (validarDados(evento)) {
                Usuario usu = usuarioBo.recUsuarioPorLogin(usuario.getLogin());
                usu.setSenha(usuario.getSenha());
                retorno = usuarioBo.mantemUsuario(usu);
            }
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }
        return retorno;
    }

    @Override
    public boolean validarDados(String evento) throws TransacaoChequeException {
        boolean retorno = true;

        if (evento.equals(ConstanteUsuario.NOME_EVENTO_SALVAR_USUARIO)
                || evento.equals(ConstanteUsuario.NOME_EVENTO_LOGIN_USUARIO)) {

            //valida campo login
            if (this.usuario.getLogin() == null
                    || this.usuario.getLogin().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteUsuario.CODIGO_ERRO_LOGIN_OBRIGATORIO);
            }

            //valida campo senha
            if (this.usuario.getSenha() == null
                    || this.usuario.getSenha().isEmpty()) {
                retorno = false;
                throw new TransacaoChequeException(ConstanteUsuario.CODIGO_ERRO_SENHA_OBRIGATORIO);
            }
        }

        return retorno;
    }

    /**
     * Login Usuario
     *
     * @return
     */
    public Object loginUsuario(String evento) throws TransacaoChequeException {

        Usuario retorno = null;
        try {
            if (validarDados(evento)) {
                retorno = this.usuarioBo.loginUsuario(this.usuario);
            }
        } catch (HibernateException ex) {
            throw new TransacaoChequeException(ex);
        }                
        
        return retorno;                
    }
}
