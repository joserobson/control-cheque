/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroChequesForm.java
 *
 * Created on 29/11/2011, 23:31:11
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.Enum.EnumSituacaoCheque;
import br.com.controlcheque.client.Enum.EnumStatusCheque;
import br.com.controlcheque.client.Excecao.ClientChequeException;
import br.com.controlcheque.client.ServicesImpl.ChequeServerImpl;
import br.com.controlcheque.client.interfaces.InterfaceCadastroCompleto;
import br.com.controlcheque.client.mensagem.MensagemClientCheque;
import br.com.controlcheque.client.mensagem.MensagemClientGenerica;
import br.com.controlcheque.client.util.DateUtil;
import br.com.controlcheque.client.util.LogUtil;
import br.com.controlcheque.client.util.MetodosUtil;
import br.com.controlcheque.client.util.formata.FormataData;
import br.com.controlcheque.client.util.formata.FormataTelefone;
import br.com.controlcheque.client.util.formata.FormataTexto;
import br.com.controlcheque.client.util.formata.FormataValorMonetario;
import br.com.controlcheque.service.Banco;
import br.com.controlcheque.service.Cheque;
import br.com.controlcheque.service.Cliente;
import java.awt.Component;
import java.awt.Container;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.codehaus.stax2.io.EscapingWriterFactory;

/**
 *
 * @author Robson
 */
public class CadastroChequesForm extends CadastroBaseForm implements InterfaceCadastroCompleto {

    /**************************************************************************
     * INICIO ATRIBUTOS
     *************************************************************************/
    private Cheque cheque;
    private List<Banco> listBancos;
    private List<Cliente> listClientes;
    private ChequeServerImpl chequeServerImpl;
    private Banco bancoSelecionado;
    private String codigo = null;

    /**************************************************************************
     * FIM ATRIBUTOS
     *************************************************************************/
    /**************************************************************************
     * INICIO CONSTRUTORES
     *************************************************************************/
    /***
     * CONSTRUTOR
     * @param codCheque
     * @param lstBancos
     * @param lstClientes 
     */
    public CadastroChequesForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<Banco> getListBancos() {
        return listBancos;
    }

    public void setListBancos(List<Banco> listBancos) {
        this.listBancos = listBancos;
    }

    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    /**************************************************************************
     * FIM CONSTRUTORES
     *************************************************************************/
    /**************************************************************************
     * INICIO METODOS DA CLASSE
     *************************************************************************/
    /**
     * limpar form
     */
    @Override
    public void clear() {
        txt_banco.setText("");
        txt_conta.setText("");
        txt_agencia.setText("");
        txt_numCheque.setText("");
        txt_titular.setText("");
        txt_valor.setText("");
        txt_cpfCnpj.setText("");
        txt_telCelular.setText("");
        txt_bomPara.setText("");
        txt_dataEmissao.setText("");
        cb_situacao.setSelectedIndex(0);
        txt_recebi.setText("");
        lblRecebiDe.setText("");
        txt_passei.setText("");
        lblPasseiPara.setText("");
        txta_observacao.setText("");
        lblNomeBanco.setText("");
        habilitaBotaoNovo(false);
        txt_banco.requestFocus();
    }

    /**
     * novo objeto cliente
     */
    @Override
    public void createNew() {
        try {
            clear();
            cheque = new Cheque();
            gerarCodigo();
            txt_banco.requestFocus();
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(this.getClass(), ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCheque.getMensagemErro(ex.getMessage()), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Gerar código para o Cheque
     */
    private void gerarCodigo() throws Exception {
        txt_codCheque.setText(chequeServerImpl.gerarCodigo());
    }

    /**
     * fechar janela
     */
    @Override
    public void close() {
        this.dispose();
        if (cheque.getId() != null) {
            ConsultarChequesForm.atualizarGrid = true;
        }
    }

    /**
     * metodo para recuperar os objetos da tela
     */
    @Override
    public void pushToModel() throws ClientChequeException {
        cheque.setCodigoCheque(txt_codCheque.getText());
        cheque.setConta(txt_conta.getText());
        cheque.setAgencia(txt_agencia.getText());
        cheque.setNumero(txt_numCheque.getText());
        cheque.setTitular(txt_titular.getText());
        if (!txt_valor.getText().isEmpty()) {
            cheque.setValor(Float.parseFloat(txt_valor.getText().replace(".", "").replace(",", ".")));
        }
        cheque.setCpfTitular(txt_cpfCnpj.getText());
        cheque.setTelefoneTitular(txt_telCelular.getText());
        cheque.setStatusCheque(EnumStatusCheque.Ativo.getCodigo());
        cheque.setDataBomPara(DateUtil.formatFromString(txt_bomPara.getText()));


        //cheque.setDataBomPara(XMLGregorianCalendarImpltxt_bomPara.getText()));
        txt_bomPara.requestFocus();
        if (!txt_dataEmissao.getText().isEmpty()) {
            cheque.setDataEmissao(DateUtil.formatFromString(txt_dataEmissao.getText()));
        }
        cheque.setSituacaoCheque(getSituacaoCheque(cb_situacao.getSelectedIndex() + 1));
        cheque.setRecebiDe(txt_recebi.getText());
        cheque.setPasseiPara(txt_passei.getText());
        cheque.setObservacao(txta_observacao.getText());

        //banco
        cheque.setBanco(bancoSelecionado);
    }

    @Override
    public void preSalvar() {
        if (validarTela()) {

            final JDialogProcessando aguarde = new JDialogProcessando(this, "Aguarde...", "Aguarde Processando...");
            aguarde.setVisible(true);
            SwingWorker<Cheque, Void> worker = new SwingWorker<Cheque, Void>() {

                @Override
                protected Cheque doInBackground() throws Exception {
                    try {
                        setEnabledBotoes(false);
                        return (Cheque) salvar();
                    } catch (Exception ex) {
                        aguarde.dispose();
                        tratarErro(CadastroBancosForm.class, ex);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        aguarde.dispose();
                        if (get() != null) {
                            JOptionPane.showMessageDialog(rootPane, MensagemClientGenerica.MENSAGEM_CADASTRO_SUCESSO, "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                            cheque = get();
                            bancoSelecionado = cheque.getBanco();
                        }
                    } catch (InterruptedException ex) {
                        tratarErro(this.getClass(), ex);
                    } catch (ExecutionException ex) {
                        tratarErro(this.getClass(), ex);
                    } finally {
                        setEnabledBotoes(true);
                        habilitaBotaoNovo(true);
                        txt_codCheque.requestFocus();
                    }
                }
            };

            worker.execute();
        }
    }

    /**
     * Salvar Cliente
     * valida os dados, intancia o novo objeto
     */
    @Override
    public Object salvar() throws ClientChequeException {
        try {
            pushToModel();
            return chequeServerImpl.mantemObject(this.cheque);


        } catch (ClientChequeException ex) {
            LogUtil.logDescricaoErro(CadastroClienteForm.class, ex);

            throw ex;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public boolean validarTela() {
        String msgErro = "";
        JTextField jText = null;

        if (lblNomeBanco.getText() == null
                || lblNomeBanco.getText().trim().isEmpty()) {
            msgErro = "Campo banco em branco.";
            jText = txt_banco;
        }

        if (txt_valor.getText().trim().isEmpty()
                || txt_valor.getText().equals("0,00")) {
            if (jText == null) {
                jText = txt_valor;
            }
            String aux = "Campo valor em branco.";
            if (msgErro.isEmpty()) {
                msgErro = aux;
            } else {
                msgErro += "\n" + aux;
            }
        }

        if (txt_titular.getText().trim().isEmpty()) {
            String aux = "Campo titular em branco.";
            if (jText == null) {
                jText = txt_titular;
            }
            if (msgErro.isEmpty()) {
                msgErro = aux;
            } else {
                msgErro += "\n" + aux;
            }
        }



        if (txt_bomPara.getText().trim().isEmpty()) {
            if (jText == null) {
                jText = txt_bomPara;
            }
            String aux = "Campo bom para em branco.";
            if (msgErro.isEmpty()) {
                msgErro = aux;
            } else {
                msgErro += "\n" + aux;
            }
        }


        if (txt_recebi.getText().trim().isEmpty()) {
            if (jText == null) {
                jText = txt_recebi;
            }
            String aux = "Campo Recebi em branco.";
            if (msgErro.isEmpty()) {
                msgErro = aux;
            } else {
                msgErro += "\n" + aux;
            }
        }


        if (cb_situacao.getSelectedIndex() > 0) {
            if (txt_passei.getText().trim().isEmpty()) {
                if (jText == null) {
                    jText = txt_passei;
                }
                String aux = "Campo Passei Para em branco.";
                if (msgErro.isEmpty()) {
                    msgErro = aux;
                } else {
                    msgErro += "\n" + aux;
                }
            }
        } else {
            if (cb_situacao.getSelectedIndex() == 0) {
                if (!txt_passei.getText().equals("")) {
                    String aux = "Para cheque em mãos campo passei não pode ser preenchido!";
                    if (msgErro.isEmpty()) {
                        msgErro = aux;
                    } else {
                        msgErro += "\n" + aux;
                    }
                }
            }
        }

        //valida regra para Situação recibeDe e Passei Para
        //msgErro = validaRegraSituacaoCheque();

        if (!msgErro.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, msgErro, "Atenção", 2);
            jText.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * Inicializa tela
     */
    @Override
    public void initialize() throws Exception {
        try {
            setLocationRelativeTo(null);
            chequeServerImpl = new ChequeServerImpl();
            btn_novo.setEnabled(false);
            txt_valor.setDocument(new FormataValorMonetario());
            //verificar se tela em edicao
            if (cheque.getId() != null) {
                popularCampos(cheque);
            } else {
                if (cheque == null) {
                    cheque = new Cheque();
                }
                lblNomeBanco.setText(null);
                lblPasseiPara.setText(null);
                lblRecebiDe.setText(null);
                txt_passei.setEnabled(true);
                txt_banco.requestFocus();
                limparTodosCampos(rootPane);
                txt_codCheque.setText(this.codigo);
            }

        } catch (Exception ex) {
            throw ex;
        }

    }

    public void limparTodosCampos(Container container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setValue(null);
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setText("");
            } else if (component instanceof Container) {
                limparTodosCampos((Container) component);
            }
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
     * METODO QUE BUSCA BANCO NA LISTA DE BANCOS
     * BUSCA POR CODIGO E DESCRICAO DE ACORDO COM QUE O USUARIO DIGITA
     */
    private void buscarBanco() {
        String lnome = this.txt_banco.getText().trim();
        lblNomeBanco.setText("");
        bancoSelecionado = null;
        if (!lnome.isEmpty()) {
            if (listBancos != null) {
                for (Banco banco : listBancos) {
                    if (banco.getDescricao().toUpperCase().matches(lnome.toUpperCase() + ".*")) {
                        lblNomeBanco.setText(banco.getDescricao());
                        bancoSelecionado = banco;
                        break;
                    } else {
                        if (banco.getCodigo().matches(lnome + ".*")) {
                            lblNomeBanco.setText(banco.getDescricao());
                            bancoSelecionado = banco;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * METODO USADO PARA BUSCAR NA LISTA DE CLIENTES PARA
     * OS CAMPOS PASSEIPARA E RECEBIDE AO DE ACORDO COM QUE O USUARIO DIGITA
     * @param pNome
     * @param pLabel
     */
    private void buscarCliente(String pNome, JLabel pLabel) {
        String lnome = pNome;
        pLabel.setText(null);
        if (!lnome.isEmpty()) {
            if (listClientes != null) {
                for (Cliente cliente : listClientes) {
                    if (cliente.getNome().toUpperCase().matches(lnome.toUpperCase() + ".*")) {
                        pLabel.setText(cliente.getNome());
                        break;
                    }
                }
            }
        }
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

        jLabel6 = new javax.swing.JLabel();
        jPanelDados = new javax.swing.JPanel();
        lbl_dataEmissao = new javax.swing.JLabel();
        lbl_agencia = new javax.swing.JLabel();
        lbl_titular = new javax.swing.JLabel();
        lbl_observacao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txta_observacao = new javax.swing.JTextArea();
        lbl_bomPara = new javax.swing.JLabel();
        lbl_telTitular = new javax.swing.JLabel();
        txt_titular = new javax.swing.JTextField();
        lbl_conta = new javax.swing.JLabel();
        txt_passei = new javax.swing.JTextField();
        txt_recebi = new javax.swing.JTextField();
        lbl_psi = new javax.swing.JLabel();
        lbl_rcb = new javax.swing.JLabel();
        cb_situacao = new javax.swing.JComboBox();
        lbl_situacao = new javax.swing.JLabel();
        lbl_cpfcnpj = new javax.swing.JLabel();
        txt_numCheque = new javax.swing.JTextField();
        lbl_numCheque = new javax.swing.JLabel();
        txt_banco = new javax.swing.JTextField();
        lbl_bnc = new javax.swing.JLabel();
        txt_codCheque = new javax.swing.JTextField();
        lblNomeBanco = new javax.swing.JLabel();
        lblRecebiDe = new javax.swing.JLabel();
        lblPasseiPara = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_valor = new javax.swing.JTextField();
        txt_conta = new javax.swing.JTextField();
        txt_agencia = new javax.swing.JTextField();
        txt_cpfCnpj = new javax.swing.JTextField();
        txt_telCelular = new javax.swing.JTextField();
        txt_bomPara = new javax.swing.JTextField();
        txt_dataEmissao = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        lbl_buscaClientes1 = new javax.swing.JLabel();
        lbl_buscaClientes2 = new javax.swing.JLabel();
        lbl_buscaClientes3 = new javax.swing.JLabel();
        lbl_buscaClientes4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btn_gravar = new javax.swing.JButton();
        btn_novo = new javax.swing.JButton();
        btn_limpar = new javax.swing.JButton();
        btn_sair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Cheques");
        setResizable(false);

        jPanelDados.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()), "Dados do Cheque", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(43, 116, 185))); // NOI18N

        lbl_dataEmissao.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_dataEmissao.setText("Data Emissão");

        lbl_agencia.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_agencia.setText("Agência");

        lbl_titular.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_titular.setText("Titular");

        lbl_observacao.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_observacao.setText("Observação");

        txta_observacao.setColumns(20);
        txta_observacao.setFont(new java.awt.Font("Arial", 0, 12));
        txta_observacao.setLineWrap(true);
        txta_observacao.setRows(5);
        txta_observacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txta_observacaoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txta_observacao);
        txta_observacao.setDocument(new FormataTexto(200, 'B'));

        lbl_bomPara.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_bomPara.setText("Bom Para");

        lbl_telTitular.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_telTitular.setText("Telefone Titular");

        txt_titular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_titularKeyPressed(evt);
            }
        });

        lbl_conta.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_conta.setText("Conta");

        txt_passei.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_passeiFocusLost(evt);
            }
        });
        txt_passei.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passeiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_passeiKeyReleased(evt);
            }
        });

        txt_recebi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_recebiFocusLost(evt);
            }
        });
        txt_recebi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_recebiKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_recebiKeyReleased(evt);
            }
        });

        lbl_psi.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_psi.setText("Passei para");

        lbl_rcb.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_rcb.setText("Recebi de");

        cb_situacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "EM MÃOS", "DEVOLVIDO", "REPASSADO" }));
        cb_situacao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cb_situacaoFocusLost(evt);
            }
        });
        cb_situacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cb_situacaoKeyPressed(evt);
            }
        });

        lbl_situacao.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_situacao.setText("Situação");

        lbl_cpfcnpj.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_cpfcnpj.setText("CPF/CNPJ");

        txt_numCheque.setPreferredSize(new java.awt.Dimension(30, 20));
        txt_numCheque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numChequeKeyPressed(evt);
            }
        });

        lbl_numCheque.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_numCheque.setText("Nᴼ Cheque");

        txt_banco.setDocument(new FormataTexto(20, 'B'));
        txt_banco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bancoFocusLost(evt);
            }
        });
        txt_banco.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bancoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bancoKeyReleased(evt);
            }
        });

        lbl_bnc.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_bnc.setText("Banco");

        txt_codCheque.setBackground(new java.awt.Color(0, 0, 0));
        txt_codCheque.setEditable(false);
        txt_codCheque.setFont(new java.awt.Font("Arial", 0, 12));
        txt_codCheque.setForeground(new java.awt.Color(255, 255, 255));
        txt_codCheque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codChequeActionPerformed(evt);
            }
        });

        lblNomeBanco.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        lblNomeBanco.setText("Nome Banco");
        lblNomeBanco.setMaximumSize(new java.awt.Dimension(50, 15));
        lblNomeBanco.setMinimumSize(new java.awt.Dimension(50, 15));
        lblNomeBanco.setPreferredSize(new java.awt.Dimension(50, 14));

        lblRecebiDe.setFont(new java.awt.Font("Arial", 1, 11));
        lblRecebiDe.setText("Recebi De");

        lblPasseiPara.setFont(new java.awt.Font("Arial", 1, 11));
        lblPasseiPara.setText("Passei Para");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel1.setText("Valor");

        txt_valor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_valorKeyPressed(evt);
            }
        });

        txt_conta.setDocument(new FormataTexto(15, 'H'));
        txt_conta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_contaKeyPressed(evt);
            }
        });

        txt_agencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_agenciaKeyPressed(evt);
            }
        });

        txt_cpfCnpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cpfCnpjKeyPressed(evt);
            }
        });

        txt_telCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_telCelularKeyPressed(evt);
            }
        });

        txt_bomPara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bomParaKeyPressed(evt);
            }
        });

        txt_dataEmissao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dataEmissaoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDadosLayout = new javax.swing.GroupLayout(jPanelDados);
        jPanelDados.setLayout(jPanelDadosLayout);
        jPanelDadosLayout.setHorizontalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_codCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelDadosLayout.createSequentialGroup()
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_bnc)
                            .addComponent(lbl_numCheque)
                            .addComponent(lbl_cpfcnpj)
                            .addComponent(lbl_rcb)
                            .addComponent(lbl_psi)
                            .addComponent(lbl_situacao)
                            .addComponent(txt_recebi, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                            .addComponent(txt_passei)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(txt_banco, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNomeBanco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(cb_situacao, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_numCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPasseiPara, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                    .addComponent(lblRecebiDe, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                                    .addComponent(lbl_conta, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_telCelular)
                                            .addComponent(lbl_telTitular, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_bomPara)
                                            .addComponent(txt_bomPara, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(7, 7, 7))
                                    .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_valor, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_conta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)))
                                .addGap(27, 27, 27))
                            .addComponent(jLabel1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_titular, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_agencia)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_observacao)
                    .addComponent(txt_titular, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(lbl_dataEmissao)
                    .addComponent(txt_agencia, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanelDadosLayout.setVerticalGroup(
            jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(lbl_agencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_agencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_titular)
                        .addGap(1, 1, 1)
                        .addComponent(txt_titular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_dataEmissao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_dataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(txt_codCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(lbl_bnc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_banco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNomeBanco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_numCheque)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_numCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_cpfcnpj)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_cpfCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelDadosLayout.createSequentialGroup()
                                .addComponent(lbl_conta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_conta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jLabel1)
                                .addGap(2, 2, 2)
                                .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                                        .addComponent(lbl_telTitular)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_telCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                                        .addComponent(lbl_bomPara)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_bomPara, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(lbl_situacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb_situacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(lbl_observacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelDadosLayout.createSequentialGroup()
                        .addComponent(lbl_rcb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_recebi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRecebiDe))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_psi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_passei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPasseiPara)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        txt_titular.setDocument(new FormataTexto(150, 'E'));
        txt_passei.setDocument(new FormataTexto(150, 'B'));
        txt_recebi.setDocument(new FormataTexto(150, 'B'));
        txt_numCheque.setDocument(new FormataTexto(15, 'D'));
        lblNomeBanco.getAccessibleContext().setAccessibleName("");
        txt_valor.setDocument(new FormataValorMonetario());
        txt_agencia.setDocument(new FormataTexto(20, 'H'));
        txt_cpfCnpj.setDocument(new FormataTexto(18, 'H'));
        txt_telCelular.setDocument(new FormataTelefone());
        txt_bomPara.setDocument(new FormataData());
        txt_dataEmissao.setDocument(new FormataData());

        jPanel6.setBackground(new java.awt.Color(178, 179, 180));
        jPanel6.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()));

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
            .addGap(0, 185, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(178, 179, 180));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(lbl_buscaClientes4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(lbl_buscaClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(lbl_buscaClientes2)
                .addGap(28, 28, 28)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(lbl_buscaClientes3)
                .addGap(57, 57, 57))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_buscaClientes3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(lbl_buscaClientes2, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(lbl_buscaClientes4, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(lbl_buscaClientes1, javax.swing.GroupLayout.Alignment.LEADING)))
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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)
                            .addComponent(jPanelDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_codChequeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codChequeActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txt_codChequeActionPerformed

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
        preSalvar();
    }//GEN-LAST:event_btn_gravarActionPerformed

    private void txt_bancoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bancoKeyReleased
        // TODO add your handling code here:
        buscarBanco();
    }//GEN-LAST:event_txt_bancoKeyReleased

    private void txt_recebiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_recebiKeyReleased
        // TODO add your handling code here:
        buscarCliente(txt_recebi.getText(), lblRecebiDe);
    }//GEN-LAST:event_txt_recebiKeyReleased

    private void txt_passeiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passeiKeyReleased
        // TODO add your handling code here:
        buscarCliente(txt_passei.getText(), lblPasseiPara);
    }//GEN-LAST:event_txt_passeiKeyReleased

    private void txt_recebiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_recebiFocusLost
        // TODO add your handling code here:
        if (!lblRecebiDe.getText().isEmpty()) {
            ((JTextField) evt.getComponent()).setText(lblRecebiDe.getText());
        }
        txt_passei.requestFocus();
    }//GEN-LAST:event_txt_recebiFocusLost

    private void txt_passeiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_passeiFocusLost
        // TODO add your handling code here:
        if (!lblPasseiPara.getText().isEmpty()) {
            ((JTextField) evt.getComponent()).setText(lblPasseiPara.getText());
        }

    }//GEN-LAST:event_txt_passeiFocusLost

    private void cb_situacaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cb_situacaoFocusLost
        // TODO add your handling code here:
        /*switch (((JComboBox) evt.getComponent()).getSelectedIndex()) {
            case 0: {
                this.txt_passei.setEnabled(false);
                break;
            }
            default:
                this.txt_passei.setEnabled(true);
        }
        this.txt_recebi.requestFocus();*/
    }//GEN-LAST:event_cb_situacaoFocusLost

    private void txt_bancoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bancoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_bancoKeyPressed

    private void txt_contaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_contaKeyPressed

    private void txt_agenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_agenciaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_agenciaKeyPressed

    private void txt_numChequeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numChequeKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_numChequeKeyPressed

    private void txt_valorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_valorKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_valorKeyPressed

    private void txt_titularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_titularKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_titularKeyPressed

    private void txt_cpfCnpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cpfCnpjKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_cpfCnpjKeyPressed

    private void txt_telCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telCelularKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_telCelularKeyPressed

    private void txt_bomParaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bomParaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_bomParaKeyPressed

    private void txt_dataEmissaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dataEmissaoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_dataEmissaoKeyPressed

    private void txt_recebiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_recebiKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_recebiKeyPressed

    private void txt_passeiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passeiKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_passeiKeyPressed

    private void txta_observacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txta_observacaoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txta_observacaoKeyPressed

    private void txt_bancoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bancoFocusLost
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (this.bancoSelecionado != null) {
            this.txt_banco.setText(this.bancoSelecionado.getCodigo());
        }
    }//GEN-LAST:event_txt_bancoFocusLost

    private void cb_situacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cb_situacaoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_cb_situacaoKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_gravar;
    private javax.swing.JButton btn_limpar;
    private javax.swing.JButton btn_novo;
    private javax.swing.JButton btn_sair;
    private javax.swing.JComboBox cb_situacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelDados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNomeBanco;
    private javax.swing.JLabel lblPasseiPara;
    private javax.swing.JLabel lblRecebiDe;
    private javax.swing.JLabel lbl_agencia;
    private javax.swing.JLabel lbl_bnc;
    private javax.swing.JLabel lbl_bomPara;
    private javax.swing.JLabel lbl_buscaClientes1;
    private javax.swing.JLabel lbl_buscaClientes2;
    private javax.swing.JLabel lbl_buscaClientes3;
    private javax.swing.JLabel lbl_buscaClientes4;
    private javax.swing.JLabel lbl_conta;
    private javax.swing.JLabel lbl_cpfcnpj;
    private javax.swing.JLabel lbl_dataEmissao;
    private javax.swing.JLabel lbl_numCheque;
    private javax.swing.JLabel lbl_observacao;
    private javax.swing.JLabel lbl_psi;
    private javax.swing.JLabel lbl_rcb;
    private javax.swing.JLabel lbl_situacao;
    private javax.swing.JLabel lbl_telTitular;
    private javax.swing.JLabel lbl_titular;
    private javax.swing.JTextField txt_agencia;
    private javax.swing.JTextField txt_banco;
    private javax.swing.JTextField txt_bomPara;
    private javax.swing.JTextField txt_codCheque;
    private javax.swing.JTextField txt_conta;
    private javax.swing.JTextField txt_cpfCnpj;
    private javax.swing.JTextField txt_dataEmissao;
    private javax.swing.JTextField txt_numCheque;
    private javax.swing.JTextField txt_passei;
    private javax.swing.JTextField txt_recebi;
    private javax.swing.JTextField txt_telCelular;
    private javax.swing.JTextField txt_titular;
    private javax.swing.JTextField txt_valor;
    private javax.swing.JTextArea txta_observacao;
    // End of variables declaration//GEN-END:variables

    /**
     * Retorna Enum Situacao Cheque
     * @param selectedIndex
     * @return 
     */
    private int getSituacaoCheque(int selectedIndex) {
        if (selectedIndex == 1) {
            return EnumSituacaoCheque.EmMaos.getCodigo();
        }
        if (selectedIndex == 2) {
            return EnumSituacaoCheque.Devolvido.getCodigo();
        }
        return EnumSituacaoCheque.Repassado.getCodigo();
    }

    /**
     * Setar Enable para os botoes da tela
     * @param b 
     */
    private void setEnabledBotoes(boolean b) {
        this.btn_gravar.setEnabled(b);
        this.btn_limpar.setEnabled(b);
        this.btn_novo.setEnabled(b);
        this.btn_sair.setEnabled(b);
    }

    private void popularCampos(Cheque cheque) {
        txt_agencia.setText(cheque.getAgencia());
        txt_banco.setText(cheque.getBanco().getCodigo());
        lblNomeBanco.setText(cheque.getBanco().getDescricao());
        txt_codCheque.setText(cheque.getCodigoCheque());
        txt_conta.setText(cheque.getConta());
        txt_cpfCnpj.setText(cheque.getCpfTitular());
        txt_numCheque.setText(cheque.getNumero());
        txt_passei.setText(cheque.getPasseiPara());
        lblPasseiPara.setText(cheque.getPasseiPara());
        txt_recebi.setText(cheque.getRecebiDe());
        lblRecebiDe.setText(cheque.getRecebiDe());
        txt_telCelular.setText(cheque.getTelefoneTitular());
        txt_titular.setText(cheque.getTitular());
        txta_observacao.setText(cheque.getObservacao());
        if (cheque.getDataEmissao() != null) {
            txt_dataEmissao.setText(DateUtil.asString(cheque.getDataEmissao()));
        }
        txt_bomPara.setText(DateUtil.asString(cheque.getDataBomPara()));
        txt_valor.setText(MetodosUtil.convertFloatToString(cheque.getValor()));
        bancoSelecionado = cheque.getBanco();        
        
        
        cb_situacao.setSelectedIndex(cheque.getSituacaoCheque()-1);

    }

    private String validaRegraSituacaoCheque() {
        //valida regra para situacao igual em Maos
        if (cb_situacao.getSelectedIndex() == EnumSituacaoCheque.EmMaos.getCodigo() - 1) {
            //  if ()
        }
        return null;
    }
    /**************************************************************************
     * FIM EVENTOS
     *************************************************************************/
}
