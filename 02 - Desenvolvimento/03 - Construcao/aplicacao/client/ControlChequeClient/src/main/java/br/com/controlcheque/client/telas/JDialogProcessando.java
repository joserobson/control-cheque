/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

/**
 *
 * @author Robson
 */
public class JDialogProcessando extends JDialog {

    private JProgressBar jProgressBar1;

    public JDialogProcessando(Frame parent, String titulo, String textoBarra) {
        super(parent, ModalityType.MODELESS);
        initComponents();
        if (!textoBarra.isEmpty()) {
            jProgressBar1.setString(textoBarra);
            jProgressBar1.setStringPainted(true);
        } else {
            jProgressBar1.setStringPainted(false);
        }
        jProgressBar1.setIndeterminate(true);
        setTitle(titulo);
        setLocationRelativeTo(null);
        setResizable(false);
        setAlwaysOnTop(true);
    }

    private void initComponents() {
        jProgressBar1 = new JProgressBar();
        this.add(BorderLayout.CENTER, jProgressBar1);
        Toolkit thekit = this.getToolkit();
        Dimension dim = thekit.getScreenSize();
        int hor = (dim.width / 2) - 150;
        int ver = (dim.height / 2) - 100;
        this.setUndecorated(true);
        this.setBounds(hor, ver, 201, 51);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
}
