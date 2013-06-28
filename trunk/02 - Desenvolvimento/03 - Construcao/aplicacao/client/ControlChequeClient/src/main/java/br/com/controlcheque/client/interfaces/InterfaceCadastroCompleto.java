package br.com.controlcheque.client.interfaces;

import br.com.controlcheque.client.Excecao.ClientChequeException;

/**
 * @author Administrador
 * @version 1.0
 * @created 01-set-2011 21:12:14
 */
public interface InterfaceCadastroCompleto extends InterfaceCadastroSimples {

    public void createNew();

    public boolean validarTela();

    public void pushToModel() throws ClientChequeException;
}
