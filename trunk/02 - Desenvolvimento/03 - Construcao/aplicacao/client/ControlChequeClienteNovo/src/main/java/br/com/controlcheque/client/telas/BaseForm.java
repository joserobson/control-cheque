/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.mensagem.MensagemClientBanco;
import br.com.controlcheque.client.util.LogUtil;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Robson
 */
class BaseForm extends JFrame {

    protected void tratarErro(Class cls, Exception ex) {
        LogUtil.logDescricaoErro(ConsultaBancoForm.class, ex);
        JOptionPane.showMessageDialog(rootPane, MensagemClientBanco.getMensagemErro(ex.getMessage()), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    protected void tratarErro(Class cls, String msg, Exception ex) {
        LogUtil.logDescricaoErro(ConsultaBancoForm.class, ex);
        JOptionPane.showMessageDialog(rootPane, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
