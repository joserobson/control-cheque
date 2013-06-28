package br.com.controlcheque.transacao;

import br.com.controlcheque.server.bo.BancoBoImpl;
import br.com.controlcheque.server.dao.BancoDaoImpl;
import br.com.controlcheque.server.dominio.ESituacaoCheque;
import br.com.controlcheque.server.dominio.EStatusCheque;
import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.server.model.Cliente;
import br.com.controlcheque.transacao.classes.TnCheque;
import br.com.controlcheque.transacao.constantes.ConstanteCheque;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import br.com.controlcheque.transacao.util.TransacaoUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
         System.out.println( "Hello World!" );

         //testeBanco();
        // TransacaoUtil.getInstanceBo(Cliente.class);
         TnCheque tnCheque = new TnCheque();
         tnCheque.setEnumSituacaoCheque(ESituacaoCheque.Todos);
         tnCheque.setEnumStatusCheque(EStatusCheque.ativo);
        try {
            List<br.com.controlcheque.server.model.Cheque> listaRetorno = tnCheque.recuperarChequePorSituacao(ConstanteCheque.NOME_EVENTO_BUSCAR_CHEQUES_POR_SITUACAO);
        } catch (TransacaoChequeException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Banco testeBanco()
    {
        BancoBoImpl bancoBo = new BancoBoImpl();
        BancoDaoImpl bancoDao = new BancoDaoImpl();
        bancoBo.setBancoDao(bancoDao);

       Banco b = new Banco();
        b.setId("-1");
        b.setCodigo("15");
        b.setDescricao("Mercantil");

        bancoBo.mantemBanco(b);

        List<Banco> lst = bancoBo.consultarBanco();

        if (lst != null && lst.size() > 0)
        {
           return lst.get(0);

        }

        return null;
    }
}
