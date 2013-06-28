package br.com.controlcheque.client.interfaces;

/**
 * @author Administrador
 * @version 1.0
 * @created 01-set-2011 21:12:13
 */
public interface InterfaceManutencaoSimples extends InterfaceCadastroSimples {

        public void preDeletar();
        
	public Object deletar() throws Exception;

}