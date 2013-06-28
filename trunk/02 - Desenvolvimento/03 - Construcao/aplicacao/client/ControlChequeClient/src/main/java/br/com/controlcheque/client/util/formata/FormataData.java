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
public class FormataData extends PlainDocument {

    /*private static int TAMANHO_TEXTO = 10; //para o formato dd/MM/yyyy  
    private static String CARACTERES_DATA = "0123456789/";
    private static String FORMATO = "dd/MM/yyyy";*/
    public FormataData() {
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // dd/MM/yyyy
        if (offs < 10) {
            if (!Character.isDigit(str.charAt(str.length() - 1))) {
                return;
            } else {

                //valida Dia
                if (offs == 0) {
                    if (Integer.parseInt(str) > 3) {
                        return;
                    }
                }

                if (offs == 1) {
                    if (Integer.parseInt(this.getText(0, offs) + str) < 32
                            & Integer.parseInt(this.getText(0, offs) + str) > 0) {
                        str = str.concat("/");
                    } else {
                        return;
                    }
                }

                //valida Mes
                if (offs == 3) {
                    if (Integer.parseInt(str) > 1) {
                        return;
                    }
                }
                if (offs == 4) {
                    int somaMes = Integer.parseInt(this.getText(0, offs).substring(offs - 1) + str);

                    if (somaMes < 13
                            & somaMes > 0) {
                        str = str.concat("/");
                    } else {
                        return;
                    }

                }
                super.insertString(offs, str, a);
            }
        }
    }
    /*@Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
    //Nao mostrar se a string for null.
    if (str == null) {
    return;
    }
    
    //Verificar o tamanho maximo permitido
    if (getLength() > TAMANHO_TEXTO) {
    return;
    }
    
    //Somente para validar os caracteres digitados
    for (int i = 0; i < str.length(); i++) {
    if (CARACTERES_DATA.indexOf(String.valueOf(str.charAt(i))) == -1) {
    java.awt.Toolkit.getDefaultToolkit().beep();
    return;
    }
    }
    
    //Checar a posicao do caracter que separa a data
    if ((offset == 2) || (offset == 5)) {
    if (!str.equals("/")) {
    return;
    }
    }
    
    super.insertString(offset, str, attr);
    // inserir o caracter que separa
    if ((offset == 1) || (offset == 4)) {
    super.insertString(offset + 1, "/", attr);
    }
    
    //If the user has finished entering validate the date entered by him.
    if (offset == TAMANHO_TEXTO - 1) {
    String strr = getText(0, getLength());
    remove(0, getLength());
    super.insertString(0, getCorrectDate(strr), attr);
    }
    }
    
    private String getCorrectDate(String oldDate) {
    String newDate = "";
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(FORMATO);
    try {
    java.util.Date date1 = formatter.parse(oldDate);
    newDate = formatter.format(date1);
    } catch (java.text.ParseException ex) {
    Logger.getLogger(this.getClass()).log(Level.ERROR, null, ex);
    newDate = "";
    }
    return newDate;
    }*/
}
