/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.App;
import br.com.controlcheque.client.interfaces.InterfaceManutencaoSimples;
import br.com.controlcheque.client.util.ClientConstantes;
import java.awt.event.KeyEvent;

/**
 *
 * @author Robson
 */
public class ConsultaBaseForm extends BaseForm implements InterfaceManutencaoSimples {

    private boolean visibleBotaoDeletar = true;
    
    @Override
    public Object deletar() throws Exception{
        return null;
    }

    @Override
    public Object salvar() throws Exception{
        return null;
    }

    @Override
    public void close() {
    }

    @Override
    public void initialize() throws Exception {
    }

    @Override
    public void clear() {
    }

    protected void eventosTeclado(KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER: {
                preSalvar();
                break;
            }
            case KeyEvent.VK_DELETE: {
                preDeletar();
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
    }

    @Override
    public void preDeletar() {
    }

    @Override
    public void preSalvar() {
    }

    public boolean isVisibleBotaoDeletar() {
        if (App.usuarioLogado.getNivelAcesso().equals(ClientConstantes.NIVEL_ACESSO_FUNCIONARIO))
            return false;
        return true;
    }
    
    
}
