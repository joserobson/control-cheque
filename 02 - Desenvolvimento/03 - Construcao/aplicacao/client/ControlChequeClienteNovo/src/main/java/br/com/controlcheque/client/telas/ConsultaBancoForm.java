/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConsultaBancoForm.java
 *
 * Created on 29/11/2011, 23:33:16
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.Enum.EnumOrdenarPor;
import br.com.controlcheque.client.ServicesImpl.BancoServerImpl;
import br.com.controlcheque.client.interfaces.InterfaceManutencaoSimples;
import br.com.controlcheque.client.mensagem.MensagemClientGenerica;
import br.com.controlcheque.client.util.formata.FormataTexto;
import br.com.controlcheque.client.util.LogUtil;
import br.com.controlcheque.client.util.MetodosUtil;
import br.com.controlcheque.client.util.UpperCaseEditor;
import br.com.controlcheque.service.Banco;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Diego
 */
public class ConsultaBancoForm extends ConsultaBaseForm implements InterfaceManutencaoSimples {

    /**
     * ************************************************************************
     * INICIO ATRIBUTOS
     ************************************************************************
     */
    private List<Banco> g_listaBancos = null;
    private BancoServerImpl bancoServerImpl = null;

    /**
     * ************************************************************************
     * FIM ATRIBUTOS
     ************************************************************************
     */
    /**
     * ************************************************************************
     * INICIO CONSTRUTORES
     ************************************************************************
     */
    /**
     * Creates new form ConsultaBancoForm
     */
    public ConsultaBancoForm() throws Exception {
        initComponents();
    }

    /**
     * Construtor passando lista de bancos
     *
     * @param pListBanco
     * @throws Exception
     */
    public ConsultaBancoForm(List<Banco> pListBanco) throws Exception {
        initComponents();
        this.g_listaBancos = pListBanco;
        initialize();
    }

    /**
     * ************************************************************************
     * FIM CONSTRUTORES
     ************************************************************************
     */
    /**
     * ************************************************************************
     * INICIO METODOS DA CLASSE
     ************************************************************************
     */
    /**
     * Metodo para preencher tabela de Bancos
     *
     * @param p_ordenacao
     */
    public List<Banco> getG_listaBancos() {
        return g_listaBancos;
    }

    public void setG_listaBancos(List<Banco> g_listaBancos) {
        this.g_listaBancos = g_listaBancos;
    }

    private void preencherTabela(int p_ordenacao) throws Exception {
        try {
            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) tb_bancos.getModel();
            dtm.setRowCount(0);

            if (g_listaBancos != null
                    && !g_listaBancos.isEmpty()) {
                ordenarLista(g_listaBancos, p_ordenacao);
                for (int i = 0; i < g_listaBancos.size(); i++) {
                    dtm.addRow(new Object[]{
                                g_listaBancos.get(i).getId(),
                                g_listaBancos.get(i).getCodigo(),
                                g_listaBancos.get(i).getDescricao()
                            });
                }

            } else {
                JOptionPane.showMessageDialog(null, "A lista está vazia!", "Atenção!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Metodo para ordenar a lista de cliente de acordo com o parametro de opcao
     *
     * @param listaClientes
     * @param opcao
     * @return
     */
    private List<Banco> ordenarLista(List<Banco> listaBancos, int opcao) {
        switch (opcao) {
            case 1:
                Collections.sort(listaBancos, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        Banco c1 = (Banco) o1;
                        Banco c2 = (Banco) o2;
                        return Integer.parseInt(c1.getCodigo()) < Integer.parseInt(c2.getCodigo()) ? -1
                                : (Integer.parseInt(c1.getCodigo()) > Integer.parseInt(c2.getCodigo()) ? +1 : 0);
                    }
                });
                break;
            case 3:
                Collections.sort(listaBancos, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        Banco c1 = (Banco) o1;
                        Banco c2 = (Banco) o2;
                        return MetodosUtil.retirarAcentos(c1.getDescricao()).compareToIgnoreCase(MetodosUtil.retirarAcentos(c2.getDescricao()));

                    }
                });
                break;

        }
        return listaBancos;
    }

    /**
     * Metodo para bsucar palavra na tabela de clientes
     *
     * @param palavra
     */
    private void buscarTabela(final String palavra) {
        new Thread(new Runnable() {

            public void run() {
                iniciarThreads();
            }

            public void iniciarThreads() {
                localizar(palavra);
            }
        }).start();
    }

    /**
     * Metodo para buscar na tabela
     */
    private void localizar(String palavra) {
        try {

            for (int i = 0; i < tb_bancos.getRowCount(); i++) {
                if (cb_pesquisar.getSelectedIndex() == 0) {
                    if (tb_bancos.getValueAt(i, 1).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tb_bancos.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                } else if (cb_pesquisar.getSelectedIndex() == 1) {
                    if (tb_bancos.getValueAt(i, 2).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tb_bancos.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                }
            }
        } catch (NumberFormatException nfe) {
        } catch (Exception e) {
        }

    }

    /**
     * Metodo que atualiza o Banco passado como parametro na lista de Bancos da
     * Grid
     *
     * @param auxCliente
     */
    private void atualizarBancoNaLista(Banco p_Banco) {

        //procura cliente
        for (Banco banco : g_listaBancos) {
            if (p_Banco.getId().equals(banco.getId())) {
                //atualizar dados do Banco
                banco.setCodigo(p_Banco.getCodigo());
                banco.setDescricao(p_Banco.getDescricao());
            }
        }
    }

    /**
     * ************************************************************************
     * FIM METODOS DA CLASSE
     ************************************************************************
     */
    /**
     * ************************************************************************
     * INICIO EVENTOS
     ************************************************************************
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scp_rolagemConsulta = new javax.swing.JScrollPane();
        tb_bancos = new javax.swing.JTable();
        lbl_pesquisar = new javax.swing.JLabel();
        lbl_valorPesquisa = new javax.swing.JLabel();
        cb_pesquisar = new javax.swing.JComboBox();
        txt_valorPesquisa = new javax.swing.JTextField();
        btn_gravar1 = new javax.swing.JButton();
        btn_deletar1 = new javax.swing.JButton();
        btn_sair1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_gravar = new javax.swing.JLabel();
        lbl_deletar = new javax.swing.JLabel();
        lbl_sair = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Bancos");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()), "Bancos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(43, 116, 185))); // NOI18N

        tb_bancos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Codigo", "Descricao"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_bancos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tb_bancos.setDragEnabled(true);
        tb_bancos.setFillsViewportHeight(true);
        tb_bancos.setFocusCycleRoot(true);
        tb_bancos.setFocusTraversalPolicyProvider(true);
        tb_bancos.setOpaque(false);
        //tb_clientes.setSelectionBackground(new java.awt.Color(102, 255, 102));
        tb_bancos.getTableHeader().setReorderingAllowed(false);
        tb_bancos.setUpdateSelectionOnSort(false);
        tb_bancos.setVerifyInputWhenFocusTarget(false);
        tb_bancos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_bancosKeyPressed(evt);
            }
        });
        scp_rolagemConsulta.setViewportView(tb_bancos);
        tb_bancos.getColumnModel().getColumn(0).setMinWidth(0);
        tb_bancos.getColumnModel().getColumn(0).setMaxWidth(0);

        tb_bancos.getColumnModel().getColumn(1).setMinWidth(50);
        tb_bancos.getColumnModel().getColumn(1).setMaxWidth(50);

        tb_bancos.getColumnModel().getColumn(2).setMinWidth(200);
        tb_bancos.getColumnModel().getColumn(2).setMinWidth(500);
        tb_bancos.setDefaultEditor(String.class, new UpperCaseEditor());

        lbl_pesquisar.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_pesquisar.setText("Pesquisar Por:");

        lbl_valorPesquisa.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_valorPesquisa.setText("Valor da Pesquisa:");

        cb_pesquisar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "Banco" }));
        cb_pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_pesquisarActionPerformed(evt);
            }
        });

        txt_valorPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_valorPesquisaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_valorPesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scp_rolagemConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_pesquisar)
                            .addComponent(cb_pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_valorPesquisa)
                            .addComponent(txt_valorPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_pesquisar)
                    .addComponent(lbl_valorPesquisa))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_valorPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(scp_rolagemConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_gravar1.setFont(new java.awt.Font("Arial", 0, 12));
        btn_gravar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/salvar32x32.png"))); // NOI18N
        btn_gravar1.setText("Gravar");
        btn_gravar1.setFocusPainted(false);
        btn_gravar1.setFocusable(false);
        btn_gravar1.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_gravar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gravar1ActionPerformed(evt);
            }
        });

        btn_deletar1.setFont(new java.awt.Font("Arial", 0, 12));
        btn_deletar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/deletar32x32.png"))); // NOI18N
        btn_deletar1.setText("Deletar");
        btn_deletar1.setFocusPainted(false);
        btn_deletar1.setFocusable(false);
        btn_deletar1.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_deletar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletar1ActionPerformed(evt);
            }
        });

        btn_sair1.setFont(new java.awt.Font("Arial", 0, 12));
        btn_sair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/sair32x32.png"))); // NOI18N
        btn_sair1.setText("Sair");
        btn_sair1.setFocusPainted(false);
        btn_sair1.setFocusable(false);
        btn_sair1.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_sair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sair1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(178, 179, 180));
        jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()));

        lbl_gravar.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_gravar.setForeground(new java.awt.Color(255, 255, 255));
        lbl_gravar.setText("Enter: Gravar");

        lbl_deletar.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_deletar.setForeground(new java.awt.Color(255, 255, 255));
        lbl_deletar.setText("Delete: Deletar");

        lbl_sair.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_sair.setForeground(new java.awt.Color(255, 255, 255));
        lbl_sair.setText("Esc: Sair");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lbl_gravar)
                .addGap(141, 141, 141)
                .addComponent(lbl_deletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                .addComponent(lbl_sair)
                .addGap(48, 48, 48))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lbl_gravar)
                .addComponent(lbl_deletar)
                .addComponent(lbl_sair))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btn_gravar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(btn_deletar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68)
                        .addComponent(btn_sair1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_gravar1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_sair1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_deletar1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deletar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletar1ActionPerformed
        // TODO add your handling code here:
        preDeletar();
    }//GEN-LAST:event_btn_deletar1ActionPerformed

    private void btn_sair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sair1ActionPerformed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_btn_sair1ActionPerformed

    private void btn_gravar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gravar1ActionPerformed
        // TODO add your handling code here:
        preSalvar();
    }//GEN-LAST:event_btn_gravar1ActionPerformed

    private void cb_pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_pesquisarActionPerformed
        // TODO add your handling code here:
        txt_valorPesquisa.setText(null);
        txt_valorPesquisa.grabFocus();
        try {
            scp_rolagemConsulta.getVerticalScrollBar().setValue(0);

            if (cb_pesquisar.getSelectedItem().toString().toUpperCase().equals("CÓDIGO")) {
                //Atribui máscara para a busca pelo critério código do cheque
                //TODO Verificar o tamanho máximo para o código
                txt_valorPesquisa.setDocument(new FormataTexto(3, 'D'));
                //Ordena a lista por codigo
                preencherTabela(EnumOrdenarPor.CODIGO.getValor());
            } else if (cb_pesquisar.getSelectedItem().toString().toUpperCase().equals("BANCO")) {
                //Atribui máscara para a busca pelo critério recebi de
                //TODO Verificar o tamanho máximo para o nome
                txt_valorPesquisa.setDocument(new FormataTexto(50, 'B'));
                //Ordena a lista pelo critério Recebi De
                preencherTabela(EnumOrdenarPor.DESCRICAO.getValor());
            }
            txt_valorPesquisa.setText("");
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cb_pesquisarActionPerformed

    private void txt_valorPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorPesquisaKeyReleased
        // TODO add your handling code here:
        buscarTabela(txt_valorPesquisa.getText().trim());
    }//GEN-LAST:event_txt_valorPesquisaKeyReleased

    private void txt_valorPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorPesquisaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_valorPesquisaKeyPressed

    private void tb_bancosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_bancosKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_tb_bancosKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_deletar1;
    private javax.swing.JButton btn_gravar1;
    private javax.swing.JButton btn_sair1;
    private javax.swing.JComboBox cb_pesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_deletar;
    private javax.swing.JLabel lbl_gravar;
    private javax.swing.JLabel lbl_pesquisar;
    private javax.swing.JLabel lbl_sair;
    private javax.swing.JLabel lbl_valorPesquisa;
    private javax.swing.JScrollPane scp_rolagemConsulta;
    private javax.swing.JTable tb_bancos;
    private javax.swing.JTextField txt_valorPesquisa;
    // End of variables declaration//GEN-END:variables

    /**
     * ************************************************************************
     * FIM EVENTOS
     ************************************************************************
     */
    /**
     * ************************************************************************
     * METODOS DA INTERFACE
     *************************************************************************
     */
    @Override
    public void close() {
        this.dispose();
    }

    @Override
    public void initialize() throws Exception {
        try {
            setLocationRelativeTo(null);
            bancoServerImpl = new BancoServerImpl();
            cb_pesquisar.setSelectedIndex(0);
            txt_valorPesquisa.setText("");
            preencherTabela(EnumOrdenarPor.CODIGO.getValor());
            txt_valorPesquisa.requestFocus();
            this.btn_deletar1.setEnabled(this.isVisibleBotaoDeletar());
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void clear() {
        cb_pesquisar.setSelectedIndex(0);
        txt_valorPesquisa.setText("");
    }

    @Override
    public void preSalvar() {
        final JDialogProcessando aguarde = new JDialogProcessando(this, "Aguarde...", "Processando...");
        aguarde.setVisible(true);

        SwingWorker<Object, Void> worker = new SwingWorker<Object, Void>() {

            @Override
            protected Object doInBackground() throws Exception {
                Boolean retorno = false;
                try {
                    setEnableBotoes(false);
                    retorno = (Boolean) salvar();
                } catch (Exception ex) {
                    aguarde.dispose();
                    txt_valorPesquisa.requestFocus();
                    throw ex;
                }
                return retorno;
            }

            @Override
            protected void done() {
                try {
                    aguarde.dispose();
                    if ((Boolean) get()) {
                        JOptionPane.showMessageDialog(rootPane, MensagemClientGenerica.MENSAGEM_ALTERACAO_SUCESSO, "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (InterruptedException ex) {
                    tratarErro(this.getClass(), ex);
                } catch (ExecutionException ex) {
                    tratarErro(this.getClass(), ex);
                } finally {
                    setEnableBotoes(true);
                }
            }
        };

        worker.execute();
    }

    @Override
    public Object salvar() throws Exception {
        try {
            this.tb_bancos.getDefaultEditor(String.class).stopCellEditing();

            //atualizar dados na grid
            for (int i = 0; i < tb_bancos.getRowCount(); i++) {
                Banco l_Banco = new Banco();
                l_Banco.setId(tb_bancos.getValueAt(i, 0).toString());
                l_Banco.setCodigo(tb_bancos.getValueAt(i, 1).toString());
                l_Banco.setDescricao(tb_bancos.getValueAt(i, 2).toString());
                atualizarBancoNaLista(l_Banco);
            }

            //salvar no server
            return this.bancoServerImpl.salvarListObject(this.g_listaBancos);
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            throw ex;
        }
    }

    /**
     * Logica para habilitar e desabilitar botoes da tela
     *
     * @param enable
     */
    @Override
    public void setEnableBotoes(boolean enable) {
        this.btn_gravar1.setEnabled(enable);
        if (isVisibleBotaoDeletar()) {
            this.btn_deletar1.setEnabled(enable);
        }
        this.btn_sair1.setEnabled(enable);
    }

    @Override
    public void preDeletar() {
        if (tb_bancos.getSelectedRow() != -1) {
            //mostrar mensagem de confirmacao da remocao do banco selecionado
            if (JOptionPane.showConfirmDialog(rootPane, "Remover banco?", "Remover", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)
                    == JOptionPane.YES_OPTION) {
                final JDialogProcessando aguarde = new JDialogProcessando(this, "Aguarde...", "Processando...");
                aguarde.setVisible(true);

                SwingWorker<Banco, Void> worker = new SwingWorker<Banco, Void>() {

                    @Override
                    protected Banco doInBackground() throws Exception {
                        setEnableBotoes(false);
                        try {
                            return (Banco) deletar();
                        } catch (Exception ex) {
                            aguarde.dispose();
                            tratarErro(ConsultaBancoForm.class, ex);
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            aguarde.dispose();
                            if (get() != null) {
                                JOptionPane.showMessageDialog(rootPane, "BANCO REMOVIDO COM SUCESSO", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                            }
                            setEnableBotoes(true);
                            tb_bancos.getDefaultEditor(String.class).cancelCellEditing();
                            initialize();
                        } catch (InterruptedException ex) {
                            tratarErro(this.getClass(), ex);
                        } catch (ExecutionException ex) {
                            tratarErro(this.getClass(), ex);
                        } catch (Exception ex) {
                            tratarErro(this.getClass(), ex);
                        }
                    }
                };

                worker.execute();
            }
        }
    }

    @Override
    public Object deletar() throws Exception {
        try {
            this.tb_bancos.getDefaultEditor(String.class).stopCellEditing();
            Banco banco = new Banco();
            banco.setId(tb_bancos.getValueAt(tb_bancos.getSelectedRow(), 0).toString());
            //chamar metodo do server para remover banco
            banco = (Banco) bancoServerImpl.deletar(banco);
            removeBancoListaPorId(banco.getId());
            initialize();
            return banco;
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            throw ex;
        }
    }

    @Override
    public void showFrame() throws Exception {
        super.showFrame();
    }

    private void removeBancoListaPorId(String id) {
        Banco remover = null;
        for (Banco banco : g_listaBancos) {
            if (banco.getId().equals(id)) {
                remover = banco;
                break;
            }
        }

        this.g_listaBancos.remove(remover);
    }
    /**
     * ************************************************************************
     * FIM METODOS DA INTERFACE
     *************************************************************************
     */
}
