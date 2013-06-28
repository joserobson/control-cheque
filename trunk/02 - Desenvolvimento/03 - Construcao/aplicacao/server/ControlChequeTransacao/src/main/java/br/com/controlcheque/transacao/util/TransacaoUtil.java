/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.transacao.util;

import br.com.controlcheque.server.bo.BancoBoImpl;
import br.com.controlcheque.server.bo.ChequeBoImpl;
import br.com.controlcheque.server.bo.ClienteBoImpl;
import br.com.controlcheque.server.bo.UsuarioBoImpl;
import br.com.controlcheque.server.dao.BancoDaoImpl;
import br.com.controlcheque.server.dao.ChequeDaoImpl;
import br.com.controlcheque.server.dao.ClienteDaoImpl;
import br.com.controlcheque.server.dao.UsuarioDaoImpl;
import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.server.model.Cheque;
import br.com.controlcheque.server.model.Cliente;
import br.com.controlcheque.server.model.Usuario;

/**
 *
 * @author Administrador
 */
public class TransacaoUtil {

    public static Object getInstanceBo(Class cls) {
        //CLIENTEBO
        if (cls.equals(Cliente.class)) {
            ClienteBoImpl clienteBo = new ClienteBoImpl();
            ClienteDaoImpl clienteDao = new ClienteDaoImpl();
            clienteBo.setClienteDao(clienteDao);
            return clienteBo;
        }

        //BANCOBO
        if (cls.equals(Banco.class)) {
            BancoBoImpl bancoBo = new BancoBoImpl();
            BancoDaoImpl bancoDao = new BancoDaoImpl();
            bancoBo.setBancoDao(bancoDao);
            return bancoBo;
        }

        //CHEQUEBO
        if (cls.equals(Cheque.class)) {
            ChequeBoImpl chequeBo = new ChequeBoImpl();
            ChequeDaoImpl chequeDao = new ChequeDaoImpl();
            chequeBo.setChequeDao(chequeDao);
            return chequeBo;
        }

        //USUARIOBO
        if (cls.equals(Usuario.class)) {
            UsuarioBoImpl usuarioBo = new UsuarioBoImpl();
            UsuarioDaoImpl usuarioDao = new UsuarioDaoImpl();
            usuarioBo.setUsuarioDao(usuarioDao);
            return usuarioBo;
        }

        return null;
    }
}
