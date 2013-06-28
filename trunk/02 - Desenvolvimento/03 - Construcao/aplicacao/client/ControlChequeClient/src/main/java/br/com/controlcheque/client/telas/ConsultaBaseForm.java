/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.interfaces.InterfaceManutencaoSimples;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 *
 * @author Robson
 */
public class ConsultaBaseForm extends JFrame implements InterfaceManutencaoSimples {

    @Override
    public void deletar() {
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
            case KeyEvent.VK_DELETE: {
                deletar();
                break;
            }
            case KeyEvent.VK_ESCAPE: {
                close();
                break;
            }
        }
    }
}
