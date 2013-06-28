/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.client.interfaces.InterfaceCadastroCompleto;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 *
 * @author Robson
 */
public class CadastroBaseForm extends JFrame implements InterfaceCadastroCompleto {

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
    public void salvar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected void eventosTeclado(KeyEvent evt) {

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER: {
                salvar();
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
}
