/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.client.interfaces.InterfaceCadastroCompleto;
import java.awt.event.KeyEvent;

/**
 *
 * @author Robson
 */
public class CadastroBaseForm extends BaseForm implements InterfaceCadastroCompleto {

    @Override
    public void createNew() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean validarTela() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pushToModel() throws ClientChequeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object salvar() throws Exception {
        return null;
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initialize() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void eventosTeclado(KeyEvent evt){

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER: {
                preSalvar();
                break;
            }
            case KeyEvent.VK_INSERT: {
                createNew();
                break;
            }
            case KeyEvent.VK_F4: {
                clear();
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                close();
                break;
            }
        }

    }

    @Override
    public void showFrame() throws Exception {
        this.initialize();
        this.setVisible(true);
    }

    @Override
    public void setEnableBotoes(boolean enable) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void preSalvar() {        
    }
}
