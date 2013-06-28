/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroBancosForm.java
 *
 * Created on 29/11/2011, 23:29:40
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.client.classes.BancoServerImpl;
import br.com.controlcheque.client.interfaces.InterfaceCadastroCompleto;
import br.com.controlcheque.client.mensagem.MensagemClientBanco;
import br.com.controlcheque.client.mensagem.MensagemClientGenerica;
import br.com.controlcheque.client.util.formata.FormataTexto;
import br.com.controlcheque.client.util.LogUtil;
import br.com.controlcheque.server.model.Banco;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Jose Robson
 */
public class CadastroBancosForm extends CadastroBaseForm implements InterfaceCadastroCompleto {

    /**************************************************************************
     * INICIO ATRIBUTOS
     *************************************************************************/
    private Banco banco;
    private BancoServerImpl bancoServerImpl;

    /**************************************************************************
     * FIM ATRIBUTOS
     *************************************************************************/
    /**************************************************************************
     * INICIO CONSTRUTORES
     *************************************************************************/
    /** Creates new form CadastroBancosForm */
    public CadastroBancosForm() {
        initComponents();
        setLocationRelativeTo(null);
        initialize();
    }

    /**************************************************************************
     * FIM CONSTRUTORES
     *************************************************************************/
    /**************************************************************************
     * INICIO METODOS DA CLASSE
     *************************************************************************/
    /**
     * Inicializa banco
     */
    @Override
    public void initialize() {
        banco = new Banco();
        bancoServerImpl = new BancoServerImpl();
        btn_novo.setEnabled(false);
        txt_codBanco.requestFocus();
    }

    /**
     * limpar form
     */
    @Override
    public void clear() {
        txt_codBanco.setText("");
        txt_banco.setText("");
        habilitaBotaoNovo(false);
        txt_codBanco.requestFocus();
    }

    /**
     * novo objeto Banco
     */
    @Override
    public void createNew() {
        clear();
        banco = new Banco();
        txt_codBanco.requestFocus();
    }

    /**
     * fechar janela
     */
    @Override
    public void close() {
        this.dispose();
    }

    /**
     * metodo para validar o tamanho permitido em cada textfield
     * @param size
     * @param text
     */
    private void validaTamanhoText(int size, JTextField text) {
        if (text.getText().length() >= size) {
            text.setText(text.getText().substring(0, size));
        }
    }

    /**
     * metodo para recuperar os objetos da tela
     */
    @Override
    public void pushToModel() {
        banco.setCodigo(txt_codBanco.getText());
        banco.setDescricao(txt_banco.getText());

    }

    /**
     * Salvar Banco
     * valida os dados, intancia o novo objeto
     */
    @Override
    public void salvar() {
        try {
            if (validarTela()) {
                pushToModel();
                //chama metodo gravar passando parametro "cliente"
                String id = banco.getId();

                if (bancoServerImpl.mantemBanco(banco) != null) {
                    habilitaBotaoNovo(true);
                    txt_codBanco.requestFocus(); 
                    if (id == null) {
                        JOptionPane.showMessageDialog(rootPane, MensagemClientBanco.MENSAGEM_CADASTRO_SUCESSO_BANCO, "Sucesso", 1);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, MensagemClientBanco.MENSAGEM_ALTERAR_SUCESSO_BANCO, "Sucesso", 1);
                    }
                }
            }
        } catch (ClientChequeException ex) {
            LogUtil.logDescricaoErro(CadastroBancosForm.class, ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientBanco.getMensagemErro(ex.getMessage()), "Erro", 2);
        }
    }

    /**
     * metodo para manter o padrão dos botões salvar e novo
     * @param value
     */
    private void habilitaBotaoNovo(boolean value) {
        btn_novo.setEnabled(value);
    }

    /**
     * metodo de validação dos campos obrigatoios
     */
    @Override
    public boolean validarTela() {
        String msgErro = "";

        if (txt_codBanco.getText().trim().isEmpty()) {
            msgErro = MensagemClientGenerica.MENSAGEM_CAMPO_CODIGO_OBRIGATORIO;
        }

        if (txt_banco.getText().trim().isEmpty()) {
            if (!msgErro.isEmpty()) {
                msgErro += "\n";
            }

            msgErro += MensagemClientGenerica.MENSAGEM_CAMPO_NOME_OBRIGATORIO;
        }

        if (!msgErro.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, msgErro, "Atenção", 2);
            return false;
        }
        return true;
    }

    /**************************************************************************
     * FIM METODOS DA CLASSE
     *************************************************************************/
    /**************************************************************************
     * INICIO EVENTOS
     *************************************************************************/
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbl_codBanco = new javax.swing.JLabel();
        lbl_banco = new javax.swing.JLabel();
        txt_banco = new javax.swing.JTextField();
        txt_codBanco = new javax.swing.JTextField();
        btn_gravar = new javax.swing.JButton();
        btn_novo = new javax.swing.JButton();
        btn_limpar = new javax.swing.JButton();
        btn_sair = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_buscaClientes1 = new javax.swing.JLabel();
        lbl_buscaClientes2 = new javax.swing.JLabel();
        lbl_buscaClientes3 = new javax.swing.JLabel();
        lbl_buscaClientes4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Bancos");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()), "Dados do Banco", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(43, 116, 185))); // NOI18N

        lbl_codBanco.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_codBanco.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_codBanco.setText(" Código do Banco");

        lbl_banco.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_banco.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl_banco.setText(" Nome do Banco");

        txt_banco.setDocument(new FormataTexto(50, 'B'));
        txt_banco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bancoKeyPressed(evt);
            }
        });

        txt_codBanco.setDocument(new FormataTexto(3, 'D'));
        txt_codBanco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codBancoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_codBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(lbl_banco, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                    .addComponent(txt_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_codBanco, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_codBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_banco, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_gravar.setFont(new java.awt.Font("Arial", 0, 12));
        btn_gravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/salvar32x32.png"))); // NOI18N
        btn_gravar.setText("Gravar");
        btn_gravar.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_gravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gravarActionPerformed(evt);
            }
        });

        btn_novo.setFont(new java.awt.Font("Arial", 0, 12));
        btn_novo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/document-new.png"))); // NOI18N
        btn_novo.setText("Novo");
        btn_novo.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_novoActionPerformed(evt);
            }
        });

        btn_limpar.setFont(new java.awt.Font("Arial", 0, 12));
        btn_limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/edit-clear.png"))); // NOI18N
        btn_limpar.setText("Limpar");
        btn_limpar.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        btn_sair.setFont(new java.awt.Font("Arial", 0, 12));
        btn_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/sair32x32.png"))); // NOI18N
        btn_sair.setText("Sair");
        btn_sair.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sairActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(178, 179, 180));
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()));

        lbl_buscaClientes1.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes1.setText("Insert: Novo");

        lbl_buscaClientes2.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes2.setText("F4: Limpar");

        lbl_buscaClientes3.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes3.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes3.setText("Esc: Sair");

        lbl_buscaClientes4.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes4.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes4.setText("Enter: Gravar");

        jPanel3.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(lbl_buscaClientes4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(lbl_buscaClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_buscaClientes2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_buscaClientes3)
                .addGap(121, 121, 121))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_buscaClientes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_buscaClientes4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_buscaClientes2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_buscaClientes3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(129, 129, 129))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sairActionPerformed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_btn_sairActionPerformed
    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_limparActionPerformed
    private void btn_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novoActionPerformed
        // TODO add your handling code here:
        createNew();
    }//GEN-LAST:event_btn_novoActionPerformed
    private void btn_gravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gravarActionPerformed
        // TODO add your handling code here:
        salvar();
    }//GEN-LAST:event_btn_gravarActionPerformed

    private void txt_bancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bancoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_bancoKeyPressed

    private void txt_codBancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codBancoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_codBancoKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_gravar;
    private javax.swing.JButton btn_limpar;
    private javax.swing.JButton btn_novo;
    private javax.swing.JButton btn_sair;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbl_banco;
    private javax.swing.JLabel lbl_buscaClientes1;
    private javax.swing.JLabel lbl_buscaClientes2;
    private javax.swing.JLabel lbl_buscaClientes3;
    private javax.swing.JLabel lbl_buscaClientes4;
    private javax.swing.JLabel lbl_codBanco;
    private javax.swing.JTextField txt_banco;
    private javax.swing.JTextField txt_codBanco;
    // End of variables declaration//GEN-END:variables

   
    /**************************************************************************
     * FIM EVENTOS
     *************************************************************************/
}
