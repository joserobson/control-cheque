/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.server.dao;

import br.com.controlcheque.server.model.Usuario;
import br.com.controlcheque.server.util.HibernateUtility;
import br.com.controlcheque.server.util.LogChequeUtil;
import br.com.controlcheque.server.util.ServerConstantes;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jose Robson
 */
public class UsuarioDaoImpl<T extends Usuario> implements UsuarioDao {

    @Override
    public Usuario mantemUsuario(Usuario usuario) {
        //Gerar Log
        LogChequeUtil.logInicioProcessoMetodo(UsuarioDaoImpl.class, "mantemUsuario");
        try {
            DaoGenericoImpl dao = new DaoGenericoImpl();
            return (T) dao.save(usuario);
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(UsuarioDaoImpl.class, "ERRO AO SALVAR USUARIO", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_SALVAR_USUARIO);
        } finally {
            //Gera log do hibernate
            LogChequeUtil.logFimProcessoMetodo(UsuarioDaoImpl.class, "mantemUsuario");
        }
    }

    @Override
    public Usuario loginUsuario(Usuario usuario) {

        //gerar log
        LogChequeUtil.logInicioProcessoMetodo(UsuarioDaoImpl.class, "loginUsuario");

        Session sessao = null;
        Usuario usuarioRetorno = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hql = new StringBuilder();
            hql.append("FROM Usuario u WHERE LOWER(u.login) = :login AND LOWER(u.senha) = :senha");
            Query query = sessao.createQuery(hql.toString());
            query.setParameter("login", usuario.getLogin().toLowerCase());
            query.setParameter("senha", usuario.getSenha().toLowerCase());
            usuarioRetorno = (Usuario) query.uniqueResult();
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(UsuarioDaoImpl.class, "ERRO AO FAZER LOGIN USUARIO", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_LOGIN_USUARIO);
        } finally {
            //fecha a sessao
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
            LogChequeUtil.logFimProcessoMetodo(UsuarioDaoImpl.class, "loginUsuario");
        }
        return usuarioRetorno;
    }

    @Override
    public Usuario recUsuarioPorLogin(String login) {
        //gerar log
        LogChequeUtil.logInicioProcessoMetodo(UsuarioDaoImpl.class, "recUsuarioPorLogin");

        Session sessao = null;
        Usuario usuarioRetorno = null;
        try {
            sessao = HibernateUtility.getSession();
            StringBuilder hql = new StringBuilder();
            hql.append("FROM Usuario u WHERE LOWER(u.login) = :login ");
            Query query = sessao.createQuery(hql.toString());
            query.setParameter("login", login.toLowerCase());
            usuarioRetorno = (Usuario) query.uniqueResult();
        } catch (HibernateException ex) {
            LogChequeUtil.logDescricaoErro(UsuarioDaoImpl.class, "ERRO AO CONSULTAR USUARIO POR LOGIN", ex);
            throw new HibernateException(ServerConstantes.CODIGO_ERRO_REC_LOGIN_USUARIO);
        } finally {
            //fecha a sessao
            if (sessao != null && sessao.isOpen()) {
                sessao.close();
            }
            LogChequeUtil.logFimProcessoMetodo(UsuarioDaoImpl.class, "loginUsuario");
        }
        return usuarioRetorno;
    }
}
