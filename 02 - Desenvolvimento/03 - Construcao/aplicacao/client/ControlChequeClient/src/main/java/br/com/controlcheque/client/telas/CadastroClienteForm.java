/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CadastroClienteForm.java
 *
 * Created on 29/11/2011, 23:31:35
 */
package br.com.controlcheque.client.telas;

import br.com.controlcheque.client.App;
import br.com.controlcheque.client.classes.ClienteServerImpl;
import br.com.controlcheque.client.interfaces.InterfaceCadastroCompleto;
import br.com.controlcheque.client.mensagem.MensagemClientCliente;
import br.com.controlcheque.client.mensagem.MensagemClientGenerica;
import br.com.controlcheque.client.util.formata.FormataTelefone;
import br.com.controlcheque.client.util.formata.FormataTexto;
import br.com.controlcheque.client.util.LogUtil;
import br.com.controlcheque.server.model.Cliente;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class CadastroClienteForm extends CadastroBaseForm implements InterfaceCadastroCompleto {

    /**************************************************************************
     * INICIO ATRIBUTOS
     *************************************************************************/
    Cliente cliente;
    ClienteServerImpl clienteServer;

    /*************************************************************************
     * FIM ATRIBUTOS
     *************************************************************************/
    /************************************************************************
     * INICIO METODOS CONSTRUTORES
     ************************************************************************/
    /** Creates new form CadastroClienteForm */
    public CadastroClienteForm() {
        initComponents();
        setLocationRelativeTo(null);
        initialize();
    }

    /************************************************************************
     * FIM CONSTRUTORES
     ************************************************************************/
    /***********************************************************************    
     * INICIO METODOS DA CLASSE
     ************************************************************************/
    @Override
    public void initialize() {
        try {
            cliente = new Cliente();
            clienteServer = new ClienteServerImpl();
            btn_novo.setEnabled(false);
            setCodigo();  
            txt_nome.requestFocus();
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(CadastroClienteForm.class, ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "Atenção", 2);
        }
    }

    /**
     * limpar form
     */
    @Override
    public void clear() {
        txt_nome.setText("");
        txt_telefone.setText("");
        txt_rua.setText("");
        txt_bairro.setText("");
        txt_numero.setText("");
        txt_cidade.setText("");
        txt_complemento.setText("");
        habilitaBotaoNovo(false);
        this.txt_nome.requestFocus();
    }

    /**
     * novo objeto cliente
     */
    @Override
    public void createNew() {
        try {
            clear();
            cliente = new Cliente();
            setCodigo();
            this.txt_nome.requestFocus();
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(CadastroClienteForm.class, ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "Aenção", 2);
        }
    }

    /**
     * fechar janela
     */
    @Override
    public void close() {
        App.telaPrincipal.setFormCadastroCliente(null);
        this.dispose();
    }   

    /**
     * metodo de validação dos campos obrigatoios
     */
    @Override
    public boolean validarTela() {

        String msgErro = "";

        if (txt_nome.getText().isEmpty()) {
            msgErro = MensagemClientGenerica.MENSAGEM_CAMPO_NOME_OBRIGATORIO;
        }

        if (txt_telefone.getText().length() < 13) {
            if (!msgErro.isEmpty()) {
                msgErro += "\n";
            }

            msgErro += MensagemClientGenerica.MENSAGEM_CAMPO_TELEFONE_OBRIGATORIO;
        }

        if (!msgErro.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, msgErro, "Atenção", 2);
            return false;
        }
        return true;
    }

    /**
     * metodo para recuperar os objetos da tela
     */
    @Override
    public void pushToModel() {
        cliente.setCodigo(txt_codCliente.getText());
        cliente.setNome(txt_nome.getText());
        cliente.setTelefone(txt_telefone.getText());
        cliente.setRua_Endereco(txt_rua.getText());
        cliente.setBairro_Endereco(txt_bairro.getText());
        cliente.setCidade_Endereco(txt_cidade.getText());
        cliente.setComplemento_Endereco(txt_complemento.getText());
        cliente.setNumero_Endereco(txt_numero.getText());

    }

    /**
     * 
     */
    private void setCodigo() throws Exception {
        txt_codCliente.setText(clienteServer.gerarCodigoCliente());
    }

    /**
     * Salvar Cliente
     * valida os dados, intancia o novo objeto
     */
    @Override
    public void salvar() {
        try {
            if (validarTela()) {
                pushToModel();
                //chama metodo gravar passando parametro "cliente"
                String id = cliente.getId();
                if (clienteServer.mantemCliente(cliente)) {
                    habilitaBotaoNovo(true);
                    this.txt_nome.requestFocus();
                    if (id == null) {
                        JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.MENSAGEM_CADASTRO_SUCESSO_CLIENTE, "Sucesso", 1);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.MENSAGEM_ALTERAR_SUCESSO_CLIENTE, "Sucesso", 1);
                    }
                }
            }
        } catch (Exception ex) {
            LogUtil.logDescricaoErro(CadastroClienteForm.class, ex);
            JOptionPane.showMessageDialog(rootPane, MensagemClientCliente.getMensagemErro(ex.getMessage()), "Erro", 2);
        }
    }

    /**
     * metodo para manter o padrão dos botões salvar e novo
     * @param value
     */
    private void habilitaBotaoNovo(boolean value) {
        btn_novo.setEnabled(value);        
    }
    
    

    /************************************************************************
     * FIM METODOS CLASSE
     ************************************************************************/
    /************************************************************************
     * INICIO EVENTOS
     ************************************************************************/
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbl_codCliente = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_telefone = new javax.swing.JLabel();
        lbl_rua = new javax.swing.JLabel();
        lbl_bairro = new javax.swing.JLabel();
        lbl_cidade = new javax.swing.JLabel();
        txt_codCliente = new javax.swing.JTextField();
        txt_nome = new javax.swing.JTextField();
        txt_rua = new javax.swing.JTextField();
        txt_bairro = new javax.swing.JTextField();
        txt_cidade = new javax.swing.JTextField();
        lbl_numero = new javax.swing.JLabel();
        txt_numero = new javax.swing.JTextField();
        lbl_complemento = new javax.swing.JLabel();
        txt_complemento = new javax.swing.JTextField();
        txt_telefone = new javax.swing.JTextField();
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
        setTitle("Cadastro de Clientes");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                closed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createEtchedBorder()), "Dados do Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(43, 116, 185))); // NOI18N

        lbl_codCliente.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_codCliente.setText("CodCliente");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12));
        jLabel2.setText("Nome*");

        lbl_telefone.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_telefone.setText("Telefone*");

        lbl_rua.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_rua.setText("Rua");

        lbl_bairro.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_bairro.setText("Bairro");

        lbl_cidade.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_cidade.setText("Cidade");

        txt_codCliente.setBackground(new java.awt.Color(0, 0, 0));
        txt_codCliente.setEditable(false);
        txt_codCliente.setFont(new java.awt.Font("Arial", 1, 12));
        txt_codCliente.setForeground(new java.awt.Color(255, 255, 255));

        txt_nome.setDocument(new FormataTexto(100, 'E'));
        txt_nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nomeKeyPressed(evt);
            }
        });

        txt_rua.setDocument(new FormataTexto(100,'B'));
        txt_rua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ruaActionPerformed(evt);
            }
        });
        txt_rua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ruaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_ruaKeyReleased(evt);
            }
        });

        txt_bairro.setDocument(new FormataTexto(45, 'B'));
        txt_bairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bairroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bairroKeyReleased(evt);
            }
        });

        txt_cidade.setDocument(new FormataTexto(45, 'B'));
        txt_cidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cidadeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cidadeKeyReleased(evt);
            }
        });

        lbl_numero.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_numero.setText("Número");

        txt_numero.setDocument(new FormataTexto(10, 'D'));
        txt_numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_numeroKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_numeroKeyReleased(evt);
            }
        });

        lbl_complemento.setFont(new java.awt.Font("Arial", 1, 12));
        lbl_complemento.setText("Complemento");

        txt_complemento.setDocument(new FormataTexto(10, 'B'));
        txt_complemento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_complementoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_complementoKeyReleased(evt);
            }
        });

        txt_telefone.setDocument(new FormataTelefone());
        txt_telefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_telefoneKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_codCliente)
                    .addComponent(txt_codCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_telefone)
                    .addComponent(lbl_rua)
                    .addComponent(txt_rua, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lbl_bairro, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_bairro, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_cidade, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_cidade))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_complemento)
                            .addComponent(lbl_complemento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_numero)
                            .addComponent(txt_numero)))
                    .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lbl_codCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_codCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_telefone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_rua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_rua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_bairro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_cidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_numero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_complemento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_complemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, 0, 640, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(btn_novo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_sair, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
    private void txt_ruaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruaKeyReleased
        // TODO add your handling code here:        
    }//GEN-LAST:event_txt_ruaKeyReleased
    private void txt_bairroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bairroKeyReleased
        // TODO add your handling code here:       
    }//GEN-LAST:event_txt_bairroKeyReleased
    private void txt_cidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cidadeKeyReleased
        // TODO add your handling code here:        
    }//GEN-LAST:event_txt_cidadeKeyReleased
    private void txt_numeroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numeroKeyReleased
        // TODO add your handling code here:       
    }//GEN-LAST:event_txt_numeroKeyReleased
    private void txt_complementoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_complementoKeyReleased
        // TODO add your handling code here:       
    }//GEN-LAST:event_txt_complementoKeyReleased
    private void btn_gravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gravarActionPerformed
        // TODO add your handling code here:
        salvar();
    }//GEN-LAST:event_btn_gravarActionPerformed
    private void btn_novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_novoActionPerformed
        // TODO add your handling code here:
        createNew();
    }//GEN-LAST:event_btn_novoActionPerformed

    private void txt_ruaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ruaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ruaActionPerformed

    private void txt_nomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomeKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_nomeKeyPressed

    private void txt_telefoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefoneKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_telefoneKeyPressed

    private void txt_ruaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruaKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_ruaKeyPressed

    private void txt_bairroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bairroKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_bairroKeyPressed

    private void txt_numeroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numeroKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_numeroKeyPressed

    private void txt_cidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cidadeKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_cidadeKeyPressed

    private void txt_complementoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_complementoKeyPressed
        // TODO add your handling code here:
        eventosTeclado(evt);
    }//GEN-LAST:event_txt_complementoKeyPressed

    private void closed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closed
        // TODO add your handling code here:
        close();
    }//GEN-LAST:event_closed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_gravar;
    private javax.swing.JButton btn_limpar;
    private javax.swing.JButton btn_novo;
    private javax.swing.JButton btn_sair;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbl_bairro;
    private javax.swing.JLabel lbl_buscaClientes1;
    private javax.swing.JLabel lbl_buscaClientes2;
    private javax.swing.JLabel lbl_buscaClientes3;
    private javax.swing.JLabel lbl_buscaClientes4;
    private javax.swing.JLabel lbl_cidade;
    private javax.swing.JLabel lbl_codCliente;
    private javax.swing.JLabel lbl_complemento;
    private javax.swing.JLabel lbl_numero;
    private javax.swing.JLabel lbl_rua;
    private javax.swing.JLabel lbl_telefone;
    private javax.swing.JTextField txt_bairro;
    private javax.swing.JTextField txt_cidade;
    private javax.swing.JTextField txt_codCliente;
    private javax.swing.JTextField txt_complemento;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_rua;
    private javax.swing.JTextField txt_telefone;
    // End of variables declaration//GEN-END:variables
    
    /**************************************************************************
     * FIM EVENTOS
     *************************************************************************/
}
