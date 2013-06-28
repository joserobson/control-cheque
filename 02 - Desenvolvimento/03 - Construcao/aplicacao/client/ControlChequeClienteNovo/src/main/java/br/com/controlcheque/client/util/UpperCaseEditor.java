/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.controlcheque.client.util;

import br.com.controlcheque.client.util.formata.FormataTexto;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Robson
 */
//O objetivo dessa classe é retornar um editor, que será usado pelo JTable para editar uma célula.
//O editor pode ser qualquer componente do Swing.
public class UpperCaseEditor extends AbstractCellEditor implements TableCellEditor {
    // Esse é o componente que será usado para edição
    JTextField editor = new JTextField();

    public UpperCaseEditor() {
        super();

        //Associa seu document que só permite maiúsculas ao editor.
        editor.setDocument(new FormataTexto(150, 'G')); 
    }

    // Esse método é chamado quando o valor for editado pelo usuário.
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
        // 'value' é o valor contido na celula rowIndex, vColIndex

        // Configura o componente com o valor que está na célula
        editor.setText((String)value);
        
        // Retorna o seu editor
        return editor ;
    }

    // Esse método é chamado quando a edição termina
    // Ele deve retornar o novo valor a ser armazenado na célula
    public Object getCellEditorValue() {
        return editor.getText();
    }
}
