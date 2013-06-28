/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util.formata;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Robson
 */
public class FormataTelefone extends PlainDocument {

    public FormataTelefone() {
    }

    @Override
    public void insertString(int off, String string, AttributeSet attr)
            throws BadLocationException {
        // (83) 3498-1091
        if (off < 13) {
            if (!Character.isDigit(string.charAt(string.length() - 1))) {
                return;
            } else {
                if (off == 0) {
                    string = "(".concat(string);
                }
                if (off == 2) {
                    string = string.concat(")");
                }
                if (off == 7) {
                    string = string.concat("-");
                }
                super.insertString(off, string, attr);
            }
        }
    }
}
