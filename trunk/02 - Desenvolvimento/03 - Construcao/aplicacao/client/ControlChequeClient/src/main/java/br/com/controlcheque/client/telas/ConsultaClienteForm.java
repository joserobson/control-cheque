/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConsultaClienteForm.java
 *
 * Created on 29/11/2011, 23:32:12
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.App;
import br.com.controlcheque.client.Enum.EnumOrdenarPor;
import br.com.controlcheque.client.classes.ClienteServerImpl;
import br.com.controlcheque.client.interfaces.InterfaceManutencaoSimples;
import br.com.controlcheque.client.mensagem.MensagemClientCliente;
import br.com.controlcheque.client.util.formata.FormataTexto;
import br.com.controlcheque.client.util.LogUtil;
import br.com.controlcheque.client.util.MetodosUtil;
import br.com.controlcheque.client.util.UpperCaseEditor;
import br.com.controlcheque.server.model.Cliente;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose Robson
 */
public class ConsultaClienteForm extends ConsultaBaseForm implements InterfaceManutencaoSimples {

    /**************************************************************************
     * INICIO ATRIBUTOS
     *************************************************************************/
    private List<Cliente> lstClientes = null;
    private ClienteServerImpl clienteServer = null;

    /*************************************************************************
     * FIM ATRIBUTOS
     *************************************************************************/
    /*************************************************************************
     * INICIO CONSTRUTORES
     *************************************************************************/
    public ConsultaClienteForm() {
        initComponents();
        initialize();
    }

    /*************************************************************************
     * FIM CONSTRUTORES
     *************************************************************************/
    /*************************************************************************
     * INICIO METODOS DA CLASSE
     *************************************************************************/
    /**
     * Metodo para preencher tabela de clientes
     * @param p_ordenacao
     */
    private void preencherTabela(int p_ordenacao) throws Exception {

        try {
            javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) tb_clientes.getModel();
            dtm.setRowCount(0);
            lstClientes = clienteServer.getTodosClientes();
            if (lstClientes != null
                    && !lstClientes.isEmpty()) {
                ordenarLista(lstClientes, p_ordenacao);
                for (int i = 0; i < lstClientes.size(); i++) {
                    dtm.addRow(new Object[]{
                                lstClientes.get(i).getId(),
                                lstClientes.get(i).getCodigo(),
                                lstClientes.get(i).getNome(),
                                lstClientes.get(i).getTelefone(),
                                lstClientes.get(i).getCidade_Endereco(),
                                lstClientes.get(i).getRua_Endereco(),
                                lstClientes.get(i).getBairro_Endereco(),
                                lstClientes.get(i).getNumero_Endereco()
                            });
                }
                //NumberFormat formatter = NumberFormat.getCurrencyInstance();
                //formatter.setMaximumFractionDigits(2);//duas casas decimais
                //se eu n me engano aceita String ou double ve ai
            } else {
                JOptionPane.showMessageDialog(null, "A lista está vazia!", "Atenção!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para ordenar a lista de cliente de acordo com o parametro de opcao
     * @param listaClientes
     * @param opcao
     * @return
     */
    private List<Cliente> ordenarLista(List<Cliente> listaClientes, int opcao) {
        switch (opcao) {
            case 1:
                Collections.sort(listaClientes, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        Cliente c1 = (Cliente) o1;
                        Cliente c2 = (Cliente) o2;
                        return Integer.parseInt(c1.getCodigo()) < Integer.parseInt(c2.getCodigo()) ? -1
                                : (Integer.parseInt(c1.getCodigo()) > Integer.parseInt(c2.getCodigo()) ? +1 : 0);
                    }
                });
                break;
            case 2:
                Collections.sort(listaClientes, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        Cliente c1 = (Cliente) o1;
                        Cliente c2 = (Cliente) o2;
                        return MetodosUtil.retirarAcentos(c1.getNome()).compareToIgnoreCase(MetodosUtil.retirarAcentos(c2.getNome()));

                    }
                });
                break;

        }
        return listaClientes;
    }

    /**
     * Metodo para bsucar palavra na tabela de clientes
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

    //Metodo para buscar na tabela
    private void localizar(String palavra) {
        try {

            for (int i = 0; i < tb_clientes.getRowCount(); i++) {
                if (cb_pesquisa.getSelectedIndex() == 0) {
                    int tam = palavra.length();
                    for (int pos = 0; pos < 4 - tam; pos++) {
                        palavra = "0" + palavra;
                    }
                    if (tb_clientes.getValueAt(i, 1).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tb_clientes.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                } else if (cb_pesquisa.getSelectedIndex() == 1) {
                    if (tb_clientes.getValueAt(i, 2).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tb_clientes.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                }
            }
        } catch (NumberFormatException nfe) {
            LogUtil.logDescricaoErro(this.getClass(), nfe);
        } catch (Exception e) {
            LogUtil.logDescricaoErro(this.getClass(), e);
        }

    }

    /**
     * Metodo que atualiza o cliente passado como parametro na lista de clientes
     * @param auxCliente
     */
    private void atualizarClienteNaLista(Cliente auxCliente) {

        //procura cliente
        for (Cliente cliente : lstClientes) {
            if (auxCliente.getId().equals(cliente.getId())) {
                //atualizar dados do cliente
                cliente.setNome(auxCliente.getNome());
                cliente.setCidade_Endereco(auxCliente.getCidade_Endereco());
                cliente.setTelefone(auxCliente.getTelefone());
                cliente.setBairro_Endereco(auxCliente.getBairro_Endereco());
                cliente.setRua_Endereco(auxCliente.getRua_Endereco());
                cliente.setNumero_Endereco(auxCliente.getNumero_Endereco());
            }
        }
    }

    /**
     * METODOS DA INTERFACE
     * @return
     */
    @Override
    public void deletar() {
        try {
            if (tb_clientes.getSelectedRow() != -1) {
                //mostrar mensagem de confirmacao da remocao do banco selecionado
                if (JOptionPane.showConfirmDialog(rootPane, "Remover Cliente " + tb_clientes.getValueAt(tb_clientes.getSelectedRow(), 2).toString() +" ?", "Remover", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE)
                        == JOptionPane.YES_OPTION) {
                    Cliente cliente = new Cliente();
                    cliente.setId(tb_clientes.getValueAt(tb_clientes.getSelectedRow(), 0).toString());                    

                    //chamar metodo do server para remover banco
                    if (clienteServer.deletarCliente(cliente) != null) {
                        JOptionPane.showMessageDialog(rootPane, "CLIENTE REMOVIDO COM SUCESSO", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);                                                                        
                        this.tb_clientes.getDefaultEditor(String.class).cancelCellEditing();
                        this.initialize();                        
                    }
                }
            }
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void salvar() {
        try {            
            this.tb_clientes.getDefaultEditor(String.class).stopCellEditing();
            
            //ATUALIZA DADOS NA GRID DE CLIENTES
            for (int i = 0; i < tb_clientes.getRowCount(); i++) {
                Cliente auxCliente = new Cliente();
                auxCliente.setId(tb_clientes.getValueAt(i, 0).toString());
                auxCliente.setCodigo(tb_clientes.getValueAt(i, 1).toString());
                auxCliente.setNome(tb_clientes.getValueAt(i, 2).toString());
                auxCliente.setTelefone(tb_clientes.getValueAt(i, 3).toString());
                auxCliente.setCidade_Endereco(tb_clientes.getValueAt(i, 4).toString());
                auxCliente.setRua_Endereco(tb_clientes.getValueAt(i, 5).toString());
                auxCliente.setBairro_Endereco(tb_clientes.getValueAt(i, 6).toString());
                auxCliente.setNumero_Endereco(tb_clientes.getValueAt(i, 7).toString());
                atualizarClienteNaLista(auxCliente);
            }

            //ATUALIZA NO BANCO
            boolean retorno = clienteServer.salvarListaClientes(this.lstClientes);
            if (retorno) {
                JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.MENSAGEM_ALTERAR_SUCESSO_CLIENTE, "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.MENSAGEM_ERRO_SALVAR_CLIENTE, "ERRO", JOptionPane.ERROR_MESSAGE);
            }

            this.txt_valorPesquisa.requestFocus();

        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize() {
        try {            
            setLocationRelativeTo(null);
            clienteServer = new ClienteServerImpl();            
            preencherTabela(EnumOrdenarPor.CODIGO.getValor());
            txt_valorPesquisa.requestFocus();
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "Aenção", 2);
        }
    }

    /**
     * limpa tela
     */
    @Override
    public void clear() {
        cb_pesquisa.setSelectedIndex(0);
        txt_valorPesquisa.setText("");
    }

    /**
     * fechar janela
     */
    @Override
    public void close() {
        App.telaPrincipal.setFormConsultarCliente(null);
        this.dispose();
    }

    /*************************************************************************
     * FIM METODOS DA CLASSE
     *************************************************************************/
    /*************************************************************************
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
        lbl_pesquisar = new javax.swing.JLabel();
        cb_pesquisa = new javax.swing.JComboBox();
        lbl_valorPesquisa = new javax.swing.JLabel();
        txt_valorPesquisa = new javax.swing.JTextField();
        scp_rolagemConsulta = new javax.swing.JScrollPane();
        tb_clientes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_gravar = new javax.swing.JButton();
        btn_deletar = new javax.swing.JButton();
        btn_sair1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbl_buscaClientes2 = new javax.swing.JLabel();
        lbl_buscaClientes3 = new javax.swing.JLabel();
        lbl_buscaClientes4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta de Clientes");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                closed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()), "Dados da Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(43, 116, 185))); // NOI18N

        lbl_pesquisar.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_pesquisar.setText("Pesquisa Por:");

        cb_pesquisa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CÓDIGO", "NOME" }));
        cb_pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_pesquisaActionPerformed(evt);
            }
        });

        lbl_valorPesquisa.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_valorPesquisa.setText("Valor a Pesquisar:");

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
                    .addComponent(lbl_pesquisar)
                    .addComponent(cb_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(183, 183, 183)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_valorPesquisa)
                    .addComponent(txt_valorPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_pesquisar)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_valorPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_valorPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        txt_valorPesquisa.setDocument(new FormataTexto(100, 'B'));

        scp_rolagemConsulta.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        tb_clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Código", "Nome", "Telefone", "Cidade", "Rua", "Bairro", "Num."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_clientes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tb_clientes.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tb_clientes.getTableHeader().setReorderingAllowed(false);
        tb_clientes.setVerifyInputWhenFocusTarget(false);
        tb_clientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tb_clientesKeyPressed(evt);
            }
        });
        scp_rolagemConsulta.setViewportView(tb_clientes);
        tb_clientes.setDragEnabled(true);
        tb_clientes.setFillsViewportHeight(true);
        tb_clientes.setFocusCycleRoot(true);
        tb_clientes.setFocusTraversalPolicyProvider(true);
        tb_clientes.setOpaque(false);
        //tb_clientes.setSelectionBackground(new java.awt.Color(102, 255, 102));
        tb_clientes.getTableHeader().setReorderingAllowed(false);
        tb_clientes.setUpdateSelectionOnSort(false);
        tb_clientes.setVerifyInputWhenFocusTarget(false);

        tb_clientes.getColumnModel().getColumn(0).setMinWidth(0);
        tb_clientes.getColumnModel().getColumn(0).setMaxWidth(0);

        tb_clientes.getColumnModel().getColumn(1).setMinWidth(50);
        tb_clientes.getColumnModel().getColumn(1).setMaxWidth(50);

        tb_clientes.getColumnModel().getColumn(2).setMinWidth(500);
        tb_clientes.getColumnModel().getColumn(2).setMaxWidth(500);

        tb_clientes.getColumnModel().getColumn(3).setMinWidth(100);
        tb_clientes.getColumnModel().getColumn(3).setMaxWidth(100);

        tb_clientes.getColumnModel().getColumn(4).setMinWidth(300);
        tb_clientes.getColumnModel().getColumn(4).setMaxWidth(300);

        tb_clientes.getColumnModel().getColumn(5).setMinWidth(300);
        tb_clientes.getColumnModel().getColumn(5).setMaxWidth(300);

        tb_clientes.getColumnModel().getColumn(6).setMinWidth(150);
        tb_clientes.getColumnModel().getColumn(6).setMaxWidth(300);

        tb_clientes.getColumnModel().getColumn(7).setMinWidth(50);
        tb_clientes.getColumnModel().getColumn(7).setMaxWidth(50);

        tb_clientes.setDefaultEditor(String.class, new UpperCaseEditor());

        btn_gravar.setFont(new java.awt.Font("Arial", 0, 12));
        btn_gravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/salvar32x32.png"))); // NOI18N
        btn_gravar.setText("Gravar");
        btn_gravar.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_gravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gravarActionPerformed(evt);
            }
        });

        btn_deletar.setFont(new java.awt.Font("Arial", 0, 12));
        btn_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/deletar32x32.png"))); // NOI18N
        btn_deletar.setText("Deletar");
        btn_deletar.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_deletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deletarActionPerformed(evt);
            }
        });

        btn_sair1.setFont(new java.awt.Font("Arial", 0, 12));
        btn_sair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/sair32x32.png"))); // NOI18N
        btn_sair1.setText("Sair");
        btn_sair1.setPreferredSize(new java.awt.Dimension(150, 40));
        btn_sair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sair1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                .addComponent(btn_sair1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_sair1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(178, 179, 180));
        jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()));

        lbl_buscaClientes2.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes2.setText("Deletar: Delete");

        lbl_buscaClientes3.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes3.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes3.setText("Esc: Sair");

        lbl_buscaClientes4.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes4.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes4.setText("Enter: Gravar");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(lbl_buscaClientes4)
                .addGap(220, 220, 220)
                .addComponent(lbl_buscaClientes2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addComponent(lbl_buscaClientes3)
                .addGap(94, 94, 94))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_buscaClientes4)
                    .addComponent(lbl_buscaClientes2)
                    .addComponent(lbl_buscaClientes3))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                        .addComponent(scp_rolagemConsulta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scp_rolagemConsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_deletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deletarActionPerformed
        // TODO add your handling code here:        
        deletar();        
    }//GEN-LAST:event_btn_deletarActionPerformed

    private void btn_sair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sair1ActionPerformed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_btn_sair1ActionPerformed

    private void cb_pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_pesquisaActionPerformed
        // TODO add your handling code here:
        txt_valorPesquisa.setText(null);
        txt_valorPesquisa.grabFocus();
        try {
            scp_rolagemConsulta.getVerticalScrollBar().setValue(0);

            if (cb_pesquisa.getSelectedItem().toString().equals("CÓDIGO")) {
                //Atribui máscara para a busca pelo critério código do cheque
                //TODO Verificar o tamanho máximo para o código                
                //Ordena a lista por codigo
                preencherTabela(EnumOrdenarPor.CODIGO.getValor());
            } else if (cb_pesquisa.getSelectedItem().toString().equals("NOME")) {
                //Atribui máscara para a busca pelo critério recebi de
                //TODO Verificar o tamanho máximo para o nome                
                //Ordena a lista pelo critério Recebi De
                preencherTabela(EnumOrdenarPor.NOME.getValor());
            }
            txt_valorPesquisa.setText("");
            repaint();
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
        }
    }//GEN-LAST:event_cb_pesquisaActionPerformed

    private void btn_gravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gravarActionPerformed
        // TODO add your handling code here:
        salvar();
    }//GEN-LAST:event_btn_gravarActionPerformed

    private void closed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_closed

    private void txt_valorPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorPesquisaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_valorPesquisaKeyPressed

    private void txt_valorPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorPesquisaKeyReleased
        // TODO add your handling code here:
        buscarTabela(txt_valorPesquisa.getText().trim());
    }//GEN-LAST:event_txt_valorPesquisaKeyReleased

    private void tb_clientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tb_clientesKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_tb_clientesKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_deletar;
    private javax.swing.JButton btn_gravar;
    private javax.swing.JButton btn_sair1;
    private javax.swing.JComboBox cb_pesquisa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_buscaClientes2;
    private javax.swing.JLabel lbl_buscaClientes3;
    private javax.swing.JLabel lbl_buscaClientes4;
    private javax.swing.JLabel lbl_pesquisar;
    private javax.swing.JLabel lbl_valorPesquisa;
    private javax.swing.JScrollPane scp_rolagemConsulta;
    private javax.swing.JTable tb_clientes;
    private javax.swing.JTextField txt_valorPesquisa;
    // End of variables declaration//GEN-END:variables

 
    /*************************************************************************
     * FIM EVENTOS
     *************************************************************************/
}
