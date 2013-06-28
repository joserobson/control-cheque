package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.Enum.EnumOrdenarPorCheque;
import br.com.controlcheque.client.Enum.EnumSituacaoCheque;
import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.client.classes.ChequeServerImpl;
import br.com.controlcheque.client.classes.TesteLista;
import br.com.controlcheque.client.util.UpperCaseEditor;
import br.com.controlcheque.server.model.Cheque;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcelo Nepomuceno da Silva
 */
public class ConsultarChequesForm extends ConsultaBaseForm {

    /**************************************************************************
     * INICIO ATRIBUTOS
     *************************************************************************/
    final String precisao = "0.0";
    TesteLista teste = new TesteLista();
    //ArrayList<Cheque> listaCheques = teste.gerarTeste();
    ArrayList<Cheque> listaCheques = null;
    KeyListener keyListener = new KeyListener() {

        public void keyPressed(KeyEvent keyEvent) {
            buscarTabela(txt_busca.getText().trim());
        }

        public void keyReleased(KeyEvent keyEvent) {
            buscarTabela(txt_busca.getText().trim());
        }

        public void keyTyped(KeyEvent keyEvent) {
            buscarTabela(txt_busca.getText().trim());
        }
    };

    /**************************************************************************
     * FIM ATRIBUTOS
     *************************************************************************/
    /**************************************************************************
     * INICIO CONSTRUTORES
     *************************************************************************/
    /**
     * 
     */
    public ConsultarChequesForm(int situacaoCheque) {
        try {
            initComponents();
            setLocationRelativeTo(null);
            popularListaCheques(situacaoCheque);
            preencherTabela(EnumOrdenarPorCheque.CODIGO.getCodigo());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atenção!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param largura
     * @param altura
     * @param opcao
     */
    public ConsultarChequesForm(int largura, int altura, boolean opcao) {
        initComponents();
        setBounds(0, 0, largura, altura);
        setLocationRelativeTo(null);
        setResizable(opcao);
        preencherTabela(EnumOrdenarPorCheque.CODIGO.getCodigo());
        setExtendedState(MAXIMIZED_BOTH);
        txt_busca.addKeyListener(keyListener);
    }

    /**************************************************************************
     * FIM CONSTRUTORES
     *************************************************************************/
    /**************************************************************************
     * INICIO METODOS
     *************************************************************************/
    /**
     * Função para preencher a tabela
     * @param p_ordenacao
     */
    private void preencherTabela(int p_ordenacao) {
        javax.swing.table.DefaultTableModel dtm = (javax.swing.table.DefaultTableModel) tbl_consulta.getModel();
        dtm.setRowCount(0);
        ordenarLista(listaCheques, p_ordenacao);
        double total = 0;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);

        final DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        if (listaCheques != null) {
            for (int i = 0; i < listaCheques.size(); i++) {
                dtm.addRow(new Object[]{
                            listaCheques.get(i).getCodigoCheque(),
                            listaCheques.get(i).getRecebiDe(),
                            dateFormatter.format(listaCheques.get(i).getDataBomPara()),
                            listaCheques.get(i).getTitular(),
                            formatter.format(listaCheques.get(i).getValor()),
                            listaCheques.get(i).getPasseiPara()
                        });
                total = total + listaCheques.get(i).getValor();
            }


            txt_totalCheques.setText(formatter.format(total));
            txt_numCheques.setText(listaCheques.size() + "");
        } else {
            JOptionPane.showMessageDialog(null, "A lista está vazia!", "Atenção!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * 
     * @param listaCheques
     * @param opcao
     * @return
     */
    private ArrayList<Cheque> ordenarLista(ArrayList<Cheque> listaCheques, int opcao) {
        final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        switch (opcao) {
            case 1:
                Collections.sort(listaCheques, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Cheque c1 = (Cheque) o1;
                        Cheque c2 = (Cheque) o2;
                        return Integer.parseInt(c1.getCodigoCheque()) < Integer.parseInt(c2.getCodigoCheque()) ? -1 : (Integer.parseInt(c1.getCodigoCheque()) > Integer.parseInt(c2.getCodigoCheque()) ? +1 : 0);
                    }
                });
                break;
            case 2:
                Collections.sort(listaCheques, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Cheque c1 = (Cheque) o1;
                        Cheque c2 = (Cheque) o2;
                        return retirarAcentos(c1.getRecebiDe()).compareToIgnoreCase(retirarAcentos(c2.getRecebiDe()));
                    }
                });
                break;
            case 3:
                Collections.sort(listaCheques, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Cheque c1 = (Cheque) o1;
                        Cheque c2 = (Cheque) o2;
                        try {
                            return c1.getDataBomPara().before(c2.getDataBomPara()) ? -1
                                    : c1.getDataBomPara().after(c2.getDataBomPara()) ? +1 : 0;
                        } catch (Exception ex) {
                            Logger.getLogger(ConsultarChequesForm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return 0;
                    }
                });
                break;
            case 4:
                Collections.sort(listaCheques, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Cheque c1 = (Cheque) o1;
                        Cheque c2 = (Cheque) o2;
                        return retirarAcentos(c1.getTitular()).compareToIgnoreCase(retirarAcentos(c2.getTitular()));
                    }
                });
                break;
            case 5:
                Collections.sort(listaCheques, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Cheque c1 = (Cheque) o1;
                        Cheque c2 = (Cheque) o2;
                        return (c1.getValor()) < (c2.getValor()) ? -1 : ((c1.getValor()) > (c2.getValor()) ? +1 : 0);
                    }
                });
                break;
            case 6:
                Collections.sort(listaCheques, new Comparator() {

                    public int compare(Object o1, Object o2) {
                        Cheque c1 = (Cheque) o1;
                        Cheque c2 = (Cheque) o2;
                        return retirarAcentos(c1.getPasseiPara()).compareToIgnoreCase(retirarAcentos(c2.getPasseiPara()));
                    }
                });
                break;

        }
        return listaCheques;
    }

    /**
     * 
     * @param nome
     * @return
     */
    public String retirarAcentos(String nome) {
        nome = nome.replaceAll(" ", "_");
        nome = Normalizer.normalize(nome, Normalizer.Form.NFD);
        nome = nome.replaceAll("[^\\p{ASCII}]", "");
        return nome;
    }

    /**
     * 
     * @param valor
     * @return
     */
    private double parceFloat(String valor) {
        double _valor = 0;
        try {
            _valor = Float.parseFloat(valor);

        } catch (NumberFormatException nfe) {
        } catch (Exception e) {
        }
        return _valor;
    }

    /**
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
     * //Função para buscar na tabela
     * @param palavra
     */
    private void localizar(String palavra) {
        boolean achou = false;
        try {

            for (int i = 0; i < tbl_consulta.getRowCount(); i++) {
                if (cbx_pesquisarPor.getSelectedIndex() == 0) {
                    if (tbl_consulta.getValueAt(i, 0).toString().matches(".*" + palavra.toUpperCase())) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tbl_consulta.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                } else if (cbx_pesquisarPor.getSelectedIndex() == 1) {
                    if (tbl_consulta.getValueAt(i, 1).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tbl_consulta.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                } else if (cbx_pesquisarPor.getSelectedIndex() == 2) {
                    if (tbl_consulta.getValueAt(i, 2).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tbl_consulta.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                } else if (cbx_pesquisarPor.getSelectedIndex() == 3) {
                    if (tbl_consulta.getValueAt(i, 3).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tbl_consulta.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                } else if (cbx_pesquisarPor.getSelectedIndex() == 4) {
                    if (!palavra.trim().equals("")) {
                        if (arredondar(Double.parseDouble(tbl_consulta.getValueAt(i, 4).toString())) == arredondar(Double.parseDouble(palavra.trim()))) {
                            scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                            tbl_consulta.setRowSelectionInterval(i, i);
                            achou = true;
                            break;
                        } else {
                            scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                        }
                    }
                } else if (cbx_pesquisarPor.getSelectedIndex() == 5) {
                    if (tbl_consulta.getValueAt(i, 5).toString().matches(palavra.toUpperCase() + ".*")) {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                        tbl_consulta.setRowSelectionInterval(i, i);
                        break;
                    } else {
                        scp_rolagemConsulta.getVerticalScrollBar().setValue(0);
                    }
                }
            }
            if (achou == false && cbx_pesquisarPor.getSelectedIndex() == 4) {
                boolean verifica = false;
                double auxValor = 0;

                if (!palavra.trim().equals("")) {
                    for (int n = 0; n < 50 && verifica == false; n++) {
                        auxValor += 0.10;
                        auxValor = arredondar(auxValor);
                        for (int i = 0; i < tbl_consulta.getRowCount(); i++) {
                            if (arredondar(Double.parseDouble(tbl_consulta.getValueAt(i, 4).toString())) == arredondar((Double.parseDouble(palavra.trim()) + auxValor))) {
                                scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                                tbl_consulta.setRowSelectionInterval(i, i);
                                verifica = true;
                                break;
                            }
                        }
                        for (int i = tbl_consulta.getRowCount() - 1; i >= 0; i--) {
                            if (arredondar(Double.parseDouble(tbl_consulta.getValueAt(i, 4).toString())) == arredondar((Double.parseDouble(palavra.trim()) - auxValor))) {
                                scp_rolagemConsulta.getVerticalScrollBar().setValue(i * 16);
                                tbl_consulta.setRowSelectionInterval(i, i);
                                verifica = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException nfe) {
        } catch (Exception e) {
        }

    }

    /**
     *
     * @param precoDouble
     * @return
     */
    public double arredondar(double precoDouble) {
        DecimalFormat fmt = new DecimalFormat(precisao);
        String string = fmt.format(precoDouble);
        String[] part = string.split("[,]");
        String string2 = part[0] + "." + part[1];
        double preco = Double.parseDouble(string2);
        return preco;
    }

    /**************************************************************************
     * FIM METODOS
     *************************************************************************/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_dadosPesquisa = new javax.swing.JPanel();
        lbl_pesquisarPor = new javax.swing.JLabel();
        cbx_pesquisarPor = new javax.swing.JComboBox();
        lbl_descricao = new javax.swing.JLabel();
        pnl_totalCheques = new javax.swing.JPanel();
        lbl_totalCheques = new javax.swing.JLabel();
        txt_totalCheques = new javax.swing.JTextField();
        pnl_numCheques = new javax.swing.JPanel();
        txt_numCheques = new javax.swing.JTextField();
        lbl_numCheques = new javax.swing.JLabel();
        txt_busca = new javax.swing.JTextField();
        scp_rolagemConsulta = new javax.swing.JScrollPane();
        tbl_consulta = new javax.swing.JTable();
        pnl_botoes = new javax.swing.JPanel();
        btn_deletar = new javax.swing.JButton();
        btn_gravar = new javax.swing.JButton();
        pnl_botoesDivisor1 = new javax.swing.JPanel();
        pnl_botoesDivisor2 = new javax.swing.JPanel();
        pnl_botoesDivisor3 = new javax.swing.JPanel();
        btn_sair = new javax.swing.JButton();
        pnl_teclasDeAtalhos = new javax.swing.JPanel();
        lbl_buscaClientes = new javax.swing.JLabel();
        lbl_entrarNaFicha = new javax.swing.JLabel();
        lbl_gravar = new javax.swing.JLabel();
        lbl_deletar = new javax.swing.JLabel();
        lbl_sair = new javax.swing.JLabel();
        pnl_atalhosDivisor1 = new javax.swing.JPanel();
        pnl_atalhosDivisor2 = new javax.swing.JPanel();
        pnl_atalhosDivisor3 = new javax.swing.JPanel();
        pnl_atalhosDivisor4 = new javax.swing.JPanel();
        pnl_atalhosDivisor5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Todos os Cheques");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(900, 500));

        pnl_dadosPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()), "Dados da Pesquisa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(43, 116, 185))); // NOI18N

        lbl_pesquisarPor.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_pesquisarPor.setText("Pesquisar Por:");

        cbx_pesquisarPor.setFont(new java.awt.Font("Arial", 0, 10));
        cbx_pesquisarPor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CÓDIGO CHEQUE", "RECEBI DE", "BOM PARA", "TITULAR", "VALOR", "PASSEI PARA" }));
        cbx_pesquisarPor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_pesquisarPorActionPerformed(evt);
            }
        });

        lbl_descricao.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_descricao.setText("Descrição:");

        pnl_totalCheques.setAlignmentX(0.0F);

        lbl_totalCheques.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_totalCheques.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_totalCheques.setText("Total em Cheques");

        txt_totalCheques.setBackground(java.awt.Color.black);
        txt_totalCheques.setEditable(false);
        txt_totalCheques.setFont(new java.awt.Font("Arial", 0, 12));
        txt_totalCheques.setForeground(new java.awt.Color(255, 255, 255));
        txt_totalCheques.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_totalCheques.setMinimumSize(new java.awt.Dimension(5, 19));
        txt_totalCheques.setPreferredSize(new java.awt.Dimension(5, 19));

        javax.swing.GroupLayout pnl_totalChequesLayout = new javax.swing.GroupLayout(pnl_totalCheques);
        pnl_totalCheques.setLayout(pnl_totalChequesLayout);
        pnl_totalChequesLayout.setHorizontalGroup(
            pnl_totalChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_totalChequesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_totalChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_totalCheques, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(lbl_totalCheques, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );
        pnl_totalChequesLayout.setVerticalGroup(
            pnl_totalChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_totalChequesLayout.createSequentialGroup()
                .addComponent(lbl_totalCheques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_totalCheques, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_numCheques.setAlignmentX(0.0F);
        pnl_numCheques.setAlignmentY(0.0F);

        txt_numCheques.setBackground(java.awt.Color.black);
        txt_numCheques.setEditable(false);
        txt_numCheques.setFont(new java.awt.Font("Arial", 0, 12));
        txt_numCheques.setForeground(new java.awt.Color(255, 255, 255));
        txt_numCheques.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_numCheques.setPreferredSize(new java.awt.Dimension(5, 19));

        lbl_numCheques.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_numCheques.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_numCheques.setText("Num. de Cheques");

        javax.swing.GroupLayout pnl_numChequesLayout = new javax.swing.GroupLayout(pnl_numCheques);
        pnl_numCheques.setLayout(pnl_numChequesLayout);
        pnl_numChequesLayout.setHorizontalGroup(
            pnl_numChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_numChequesLayout.createSequentialGroup()
                .addGroup(pnl_numChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_numChequesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_numCheques, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_numChequesLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(txt_numCheques, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_numChequesLayout.setVerticalGroup(
            pnl_numChequesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_numChequesLayout.createSequentialGroup()
                .addComponent(lbl_numCheques)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_numCheques, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        txt_busca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_buscaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnl_dadosPesquisaLayout = new javax.swing.GroupLayout(pnl_dadosPesquisa);
        pnl_dadosPesquisa.setLayout(pnl_dadosPesquisaLayout);
        pnl_dadosPesquisaLayout.setHorizontalGroup(
            pnl_dadosPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_dadosPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_pesquisarPor)
                    .addComponent(lbl_descricao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_dadosPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_busca, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                    .addComponent(cbx_pesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_totalCheques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_numCheques, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnl_dadosPesquisaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {pnl_numCheques, pnl_totalCheques});

        pnl_dadosPesquisaLayout.setVerticalGroup(
            pnl_dadosPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_numCheques, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_totalCheques, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_dadosPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_dadosPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbx_pesquisarPor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_pesquisarPor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_dadosPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_busca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_descricao))
                .addGap(4, 4, 4))
        );

        pnl_dadosPesquisaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbx_pesquisarPor, lbl_descricao, lbl_pesquisarPor});

        tbl_consulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "RECEBI DE", "BOM PARA", "TITULAR", "VALOR", "PASSEI PARA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_consulta.setSelectionBackground(new java.awt.Color(153, 204, 255));
        scp_rolagemConsulta.setViewportView(tbl_consulta);
        tbl_consulta.setDragEnabled(true);
        tbl_consulta.setFillsViewportHeight(true);
        tbl_consulta.setFocusCycleRoot(true);
        tbl_consulta.setFocusTraversalPolicyProvider(true);
        tbl_consulta.setOpaque(false);
        //tbl_consulta.setSelectionBackground(new java.awt.Color(102, 255, 102));
        tbl_consulta.getTableHeader().setReorderingAllowed(false);
        tbl_consulta.setUpdateSelectionOnSort(false);
        tbl_consulta.setVerifyInputWhenFocusTarget(false);

        tbl_consulta.getColumnModel().getColumn(0).setMinWidth(70);
        tbl_consulta.getColumnModel().getColumn(0).setMaxWidth(70);

        tbl_consulta.getColumnModel().getColumn(2).setMinWidth(70);
        tbl_consulta.getColumnModel().getColumn(2).setMaxWidth(70);

        tbl_consulta.getColumnModel().getColumn(4).setMinWidth(70);
        tbl_consulta.getColumnModel().getColumn(4).setMaxWidth(70);

        tbl_consulta.setDefaultEditor(String.class, new UpperCaseEditor());

        btn_deletar.setFont(new java.awt.Font("Arial", 0, 12));
        btn_deletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/deletar32x32.png"))); // NOI18N
        btn_deletar.setText("Deletar");
        btn_deletar.setFocusPainted(false);
        btn_deletar.setFocusable(false);

        btn_gravar.setFont(new java.awt.Font("Arial", 0, 12));
        btn_gravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/salvar32x32.png"))); // NOI18N
        btn_gravar.setText("Gravar");
        btn_gravar.setFocusPainted(false);
        btn_gravar.setFocusable(false);

        javax.swing.GroupLayout pnl_botoesDivisor1Layout = new javax.swing.GroupLayout(pnl_botoesDivisor1);
        pnl_botoesDivisor1.setLayout(pnl_botoesDivisor1Layout);
        pnl_botoesDivisor1Layout.setHorizontalGroup(
            pnl_botoesDivisor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnl_botoesDivisor1Layout.setVerticalGroup(
            pnl_botoesDivisor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_botoesDivisor2Layout = new javax.swing.GroupLayout(pnl_botoesDivisor2);
        pnl_botoesDivisor2.setLayout(pnl_botoesDivisor2Layout);
        pnl_botoesDivisor2Layout.setHorizontalGroup(
            pnl_botoesDivisor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnl_botoesDivisor2Layout.setVerticalGroup(
            pnl_botoesDivisor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_botoesDivisor3Layout = new javax.swing.GroupLayout(pnl_botoesDivisor3);
        pnl_botoesDivisor3.setLayout(pnl_botoesDivisor3Layout);
        pnl_botoesDivisor3Layout.setHorizontalGroup(
            pnl_botoesDivisor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        pnl_botoesDivisor3Layout.setVerticalGroup(
            pnl_botoesDivisor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        btn_sair.setFont(new java.awt.Font("Arial", 0, 12));
        btn_sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/32x32/sair32x32.png"))); // NOI18N
        btn_sair.setText("Sair");
        btn_sair.setFocusPainted(false);
        btn_sair.setFocusable(false);
        btn_sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_botoesLayout = new javax.swing.GroupLayout(pnl_botoes);
        pnl_botoes.setLayout(pnl_botoesLayout);
        pnl_botoesLayout.setHorizontalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_botoesLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_botoesDivisor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(187, 187, 187)
                .addComponent(btn_deletar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(pnl_botoesDivisor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(120, 120, 120)
                .addComponent(pnl_botoesDivisor3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        pnl_botoesLayout.setVerticalGroup(
            pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_botoesLayout.createSequentialGroup()
                .addGroup(pnl_botoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_botoesLayout.createSequentialGroup()
                        .addComponent(btn_gravar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addComponent(pnl_botoesDivisor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_botoesDivisor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_botoesDivisor3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_deletar)
                    .addComponent(btn_sair))
                .addContainerGap())
        );

        pnl_teclasDeAtalhos.setBackground(new java.awt.Color(178, 179, 180));
        pnl_teclasDeAtalhos.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()));

        lbl_buscaClientes.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_buscaClientes.setForeground(new java.awt.Color(255, 255, 255));
        lbl_buscaClientes.setText("F11: Buscar Clientes");

        lbl_entrarNaFicha.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_entrarNaFicha.setForeground(new java.awt.Color(255, 255, 255));
        lbl_entrarNaFicha.setText("F3: Entrar na Ficha");

        lbl_gravar.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_gravar.setForeground(new java.awt.Color(255, 255, 255));
        lbl_gravar.setText("Enter: Gravar");

        lbl_deletar.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_deletar.setForeground(new java.awt.Color(255, 255, 255));
        lbl_deletar.setText("Delete: Deletar");

        lbl_sair.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_sair.setForeground(new java.awt.Color(255, 255, 255));
        lbl_sair.setText("Esc: Sair");

        pnl_atalhosDivisor1.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout pnl_atalhosDivisor1Layout = new javax.swing.GroupLayout(pnl_atalhosDivisor1);
        pnl_atalhosDivisor1.setLayout(pnl_atalhosDivisor1Layout);
        pnl_atalhosDivisor1Layout.setHorizontalGroup(
            pnl_atalhosDivisor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        pnl_atalhosDivisor1Layout.setVerticalGroup(
            pnl_atalhosDivisor1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        pnl_atalhosDivisor2.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout pnl_atalhosDivisor2Layout = new javax.swing.GroupLayout(pnl_atalhosDivisor2);
        pnl_atalhosDivisor2.setLayout(pnl_atalhosDivisor2Layout);
        pnl_atalhosDivisor2Layout.setHorizontalGroup(
            pnl_atalhosDivisor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        pnl_atalhosDivisor2Layout.setVerticalGroup(
            pnl_atalhosDivisor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        pnl_atalhosDivisor3.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout pnl_atalhosDivisor3Layout = new javax.swing.GroupLayout(pnl_atalhosDivisor3);
        pnl_atalhosDivisor3.setLayout(pnl_atalhosDivisor3Layout);
        pnl_atalhosDivisor3Layout.setHorizontalGroup(
            pnl_atalhosDivisor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 54, Short.MAX_VALUE)
        );
        pnl_atalhosDivisor3Layout.setVerticalGroup(
            pnl_atalhosDivisor3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        pnl_atalhosDivisor4.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout pnl_atalhosDivisor4Layout = new javax.swing.GroupLayout(pnl_atalhosDivisor4);
        pnl_atalhosDivisor4.setLayout(pnl_atalhosDivisor4Layout);
        pnl_atalhosDivisor4Layout.setHorizontalGroup(
            pnl_atalhosDivisor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );
        pnl_atalhosDivisor4Layout.setVerticalGroup(
            pnl_atalhosDivisor4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        pnl_atalhosDivisor5.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout pnl_atalhosDivisor5Layout = new javax.swing.GroupLayout(pnl_atalhosDivisor5);
        pnl_atalhosDivisor5.setLayout(pnl_atalhosDivisor5Layout);
        pnl_atalhosDivisor5Layout.setHorizontalGroup(
            pnl_atalhosDivisor5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );
        pnl_atalhosDivisor5Layout.setVerticalGroup(
            pnl_atalhosDivisor5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnl_teclasDeAtalhosLayout = new javax.swing.GroupLayout(pnl_teclasDeAtalhos);
        pnl_teclasDeAtalhos.setLayout(pnl_teclasDeAtalhosLayout);
        pnl_teclasDeAtalhosLayout.setHorizontalGroup(
            pnl_teclasDeAtalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_teclasDeAtalhosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_buscaClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_atalhosDivisor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(127, 127, 127)
                .addComponent(pnl_atalhosDivisor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_entrarNaFicha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_atalhosDivisor3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_gravar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_atalhosDivisor4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_deletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_atalhosDivisor5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_sair)
                .addContainerGap())
        );
        pnl_teclasDeAtalhosLayout.setVerticalGroup(
            pnl_teclasDeAtalhosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_sair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_atalhosDivisor4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_atalhosDivisor3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_atalhosDivisor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_atalhosDivisor1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_buscaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_entrarNaFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_gravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbl_deletar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_atalhosDivisor5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnl_botoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scp_rolagemConsulta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                    .addComponent(pnl_dadosPesquisa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_teclasDeAtalhos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_dadosPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scp_rolagemConsulta, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pnl_botoes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_teclasDeAtalhos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbx_pesquisarPorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_pesquisarPorActionPerformed
        txt_busca.setText("");
        txt_busca.grabFocus();
        try {
            //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter()));
            scp_rolagemConsulta.getVerticalScrollBar().setValue(0);

            if (cbx_pesquisarPor.getSelectedItem().toString().equals("CÓDIGO CHEQUE")) {
                //Atribui máscara para a busca pelo critério código do cheque
                //TODO Verificar o tamanho máximo para o código
                //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
                //Ordena a lista por codigo
                preencherTabela(EnumOrdenarPorCheque.CODIGO.getCodigo());
            } else if (cbx_pesquisarPor.getSelectedItem().toString().equals("RECEBI DE")) {
                //Atribui máscara para a busca pelo critério recebi de
                //TODO Verificar o tamanho máximo para o nome
                //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("********************")));
                //Ordena a lista pelo critério Recebi De
                preencherTabela(EnumOrdenarPorCheque.RECEBIDE.getCodigo());
            } else if (cbx_pesquisarPor.getSelectedItem().toString().equals("BOM PARA")) {
                //Atribui máscara para a busca pelo critério bom para
                //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
                //Ordena a lista pelo critério Bom Para
                preencherTabela(EnumOrdenarPorCheque.BOMPARA.getCodigo());
            } else if (cbx_pesquisarPor.getSelectedItem().toString().equals("TITULAR")) {
                //Atribui máscara para a busca pelo critério titular
                //TODO Verificar o tamanho máximo para o nome
                //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("********************")));
                //Ordena a lista pelo critério Titular
                preencherTabela(EnumOrdenarPorCheque.TITULAR.getCodigo());
            } else if (cbx_pesquisarPor.getSelectedItem().toString().equals("VALOR")) {
                //Atribui máscara para a busca pelo critério valor
                //TODO Verificar o tamanho máximo para o valor
                //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("**********")));
                //Ordena a lista pelo critério Valor
                preencherTabela(EnumOrdenarPorCheque.VALOR.getCodigo());
            } else if (cbx_pesquisarPor.getSelectedItem().toString().equals("PASSEI PARA")) {
                //Atribui máscara para a busca pelo critério passei para
                //TODO Verificar o tamanho máximo para o nome
                //txt_busca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("********************")));
                //Ordena a lista pelo critério Passei Para
                preencherTabela(EnumOrdenarPorCheque.PASSEIPARA.getCodigo());
            }
            txt_busca.setText("");
            repaint();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_cbx_pesquisarPorActionPerformed

    private void btn_sairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sairActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_sairActionPerformed

    private void txt_buscaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);

    }//GEN-LAST:event_txt_buscaKeyPressed

    private void txt_buscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscaKeyReleased
        // TODO add your handling code here:
        buscarTabela(txt_busca.getText().trim());
    }//GEN-LAST:event_txt_buscaKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_deletar;
    private javax.swing.JButton btn_gravar;
    private javax.swing.JButton btn_sair;
    private javax.swing.JComboBox cbx_pesquisarPor;
    private javax.swing.JLabel lbl_buscaClientes;
    private javax.swing.JLabel lbl_deletar;
    private javax.swing.JLabel lbl_descricao;
    private javax.swing.JLabel lbl_entrarNaFicha;
    private javax.swing.JLabel lbl_gravar;
    private javax.swing.JLabel lbl_numCheques;
    private javax.swing.JLabel lbl_pesquisarPor;
    private javax.swing.JLabel lbl_sair;
    private javax.swing.JLabel lbl_totalCheques;
    private javax.swing.JPanel pnl_atalhosDivisor1;
    private javax.swing.JPanel pnl_atalhosDivisor2;
    private javax.swing.JPanel pnl_atalhosDivisor3;
    private javax.swing.JPanel pnl_atalhosDivisor4;
    private javax.swing.JPanel pnl_atalhosDivisor5;
    private javax.swing.JPanel pnl_botoes;
    private javax.swing.JPanel pnl_botoesDivisor1;
    private javax.swing.JPanel pnl_botoesDivisor2;
    private javax.swing.JPanel pnl_botoesDivisor3;
    private javax.swing.JPanel pnl_dadosPesquisa;
    private javax.swing.JPanel pnl_numCheques;
    private javax.swing.JPanel pnl_teclasDeAtalhos;
    private javax.swing.JPanel pnl_totalCheques;
    private javax.swing.JScrollPane scp_rolagemConsulta;
    private javax.swing.JTable tbl_consulta;
    private javax.swing.JTextField txt_busca;
    private javax.swing.JTextField txt_numCheques;
    private javax.swing.JTextField txt_totalCheques;
    // End of variables declaration//GEN-END:variables

    /**
     * METODO PARA POPULAR LISTA DE CHEQUES
     * @throws Exception 
     */
    private void popularListaCheques(int situacaoCheque) throws Exception {
        try {
            ChequeServerImpl serverImpl = new ChequeServerImpl();

            if (situacaoCheque == EnumSituacaoCheque.Todos.getCodigo()) {
                this.listaCheques = (ArrayList<Cheque>) serverImpl.recCheques();
            }else
            {
                this.listaCheques = (ArrayList<Cheque>) serverImpl.recChequesPorSituacao(situacaoCheque);
            }



        } catch (ClientChequeException ex) {
            throw ex;
        }
    }
}
