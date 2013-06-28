/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.classes;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.server.model.Banco;
import br.com.controlcheque.transacao.classes.TnBanco;
import br.com.controlcheque.transacao.constantes.ConstanteBanco;
import br.com.controlcheque.transacao.excecao.TransacaoChequeException;
import java.util.List;

/**
 *
 * @author Robson
 */
public class BancoServerImpl {

    /**************************************************************************
     * INICIO CHAMADA METODOS DO SERVER
     *************************************************************************/
    /**
     * MantemBanco
     * @param banco
     * @return
     */
    public Banco mantemBanco(Banco banco) throws ClientChequeException {

        Banco retorno = null;
        try {
            TnBanco tn = new TnBanco();
            tn.setBanco(banco);
            retorno = (Banco) tn.salvar(ConstanteBanco.NOME_EVENTO_SALVAR_BANCO);
        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }
        return retorno;
    }

    /**
     * Metodo que retorna todos os Bancos ativos do sistema
     * @return
     */
    public List<Banco> getTodosBancos() throws ClientChequeException {
        List<Banco> lListBanco = null;
        try {
            TnBanco tnBanco = new TnBanco();
            lListBanco = tnBanco.recuperarTodos(ConstanteBanco.NOME_EVENTO_BUSCAR_TODOS_BANCOS);
        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }
        return lListBanco;
    }

    /**
     * Metodo que salva todos os clientes da lista
     * @return
     */
    public boolean salvarListaBancos(List<Banco> pLstBancos) throws ClientChequeException {
        boolean retorno = true;
        try {
            TnBanco l_TnBanco = new TnBanco();

            for (Banco banco : pLstBancos) {
                l_TnBanco.setBanco(banco);
                l_TnBanco.salvar(ConstanteBanco.NOME_EVENTO_SALVAR_LISTA_BANCO);
            }

        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }

        return retorno;
    }

    /**
     * DELETA BANCO
     * @param b
     * @return
     */
    public Banco deletarBanco(Banco b) throws ClientChequeException {
        Banco retorno = null;
        try {
            TnBanco tnBanco = new TnBanco();
            tnBanco.setBanco(b);
            retorno = (Banco) tnBanco.deletar(ConstanteBanco.NOME_EVENTO_DELETAR_BANCO);
        } catch (TransacaoChequeException ex) {
            throw new ClientChequeException(ex);
        }
        return retorno;
    }
    /**************************************************************************
     * FIM CHAMADA METODOS DO SERVER
     *************************************************************************/
}
