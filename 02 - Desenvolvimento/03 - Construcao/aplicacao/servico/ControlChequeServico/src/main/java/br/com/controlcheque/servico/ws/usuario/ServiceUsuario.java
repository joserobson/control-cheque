/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.servico.ws.usuario;

import br.com.controlcheque.servico.beans.Usuario;
import br.com.controlcheque.servico.ws.ServiceBase;
import br.com.controlcheque.transacao.classes.TnUsuario;
import br.com.controlcheque.transacao.constantes.ConstanteUsuario;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;

/**
 *
 * @author Jose Robson
 */
public class ServiceUsuario extends ServiceBase {

    private TnUsuario tnUsuario = null;

    public ServiceUsuario() {
        this.tnUsuario = new TnUsuario();
    }

    @Override
    public Object salvar(Object param) throws Exception {
        Usuario retorno = null;
        try {
            tnUsuario.setUsuario(mapper.map(param, br.com.controlcheque.server.model.Usuario.class));
            retorno = mapper.map(tnUsuario.salvar(ConstanteUsuario.NOME_EVENTO_SALVAR_USUARIO), Usuario.class);
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }

    public Object loginUsuario(Object param) throws Exception {
        Usuario retorno = null;
        try {
            tnUsuario.setUsuario(mapper.map(param, br.com.controlcheque.server.model.Usuario.class));
            Object usu = tnUsuario.loginUsuario(ConstanteUsuario.NOME_EVENTO_LOGIN_USUARIO);
            if (usu != null) {
                retorno = mapper.map((br.com.controlcheque.server.model.Usuario) usu, Usuario.class);
            }
        } catch (TransacaoChequeException ex) {
            throw new Exception(ex);
        }
        return retorno;
    }
}
