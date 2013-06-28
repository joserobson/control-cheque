package br.com.controlcheque.server;

import br.com.controlcheque.server.bo.ChequeBo;
import br.com.controlcheque.server.bo.ChequeBoImpl;
import br.com.controlcheque.server.bo.UsuarioBo;
import br.com.controlcheque.server.bo.UsuarioBoImpl;
import br.com.controlcheque.server.dao.ChequeDao;
import br.com.controlcheque.server.dao.ChequeDaoImpl;
import br.com.controlcheque.server.dao.UsuarioDao;
import br.com.controlcheque.server.dao.UsuarioDaoImpl;
import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.server.model.Cheque;
import br.com.controlcheque.server.model.Usuario;
import java.sql.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //TesteRecListCheque();
        //TesteRecSomatorioChequesPorData();
        //TesteRecQuantidadeChequesPorData()
        TesteLoginUsuario();
    }
    
    public static void TesteRecListCheque()
    {
        ChequeBoImpl chequeBo = new ChequeBoImpl();
        ChequeDaoImpl daoImpl = new ChequeDaoImpl();
        chequeBo.setChequeDao(daoImpl);

        List<Cheque>  lst = chequeBo.consultaChequesPorSituacao(ESituacaoCheque.Todos, EStatusCheque.ativo);

        if (lst != null)
        {

        }
    }
    
    public static void TesteRecSomatorioChequesPorData()
    {
        ChequeBo chequeBo = new ChequeBoImpl();
        ChequeDao chequeDao = new ChequeDaoImpl();
        chequeBo.setChequeDao(chequeDao);
        
        float valor = chequeBo.recSomatarioChequesPorIntervaloDatas(Date.valueOf("2011-01-01"), Date.valueOf("2014-12-05"));
    }
    
    public static void TesteRecQuantidadeChequesPorData()
    {
        ChequeBo chequeBo = new ChequeBoImpl();
        ChequeDao chequeDao = new ChequeDaoImpl();
        chequeBo.setChequeDao(chequeDao);
        
        int quantidade = chequeBo.recQuantidadeChequesPorIntervaloDatas(Date.valueOf("2011-01-01"), Date.valueOf("2014-12-05"));
    }

    private static void TesteLoginUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("Administrador");
        usuario.setSenha("123");
        
        UsuarioBo usuarioBo = new UsuarioBoImpl();
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        usuarioBo.setUsuarioDao(usuarioDao);
        
        Usuario us = usuarioBo.loginUsuario(usuario);               
        
    }
}
