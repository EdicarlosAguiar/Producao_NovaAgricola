/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Controller.ControlFornecedor;
import FormNotificacao.Confirmacao;

import Model.ModelFornecedor;
import Model.ModelProduto;
import Model.ModelUsuario;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Conexao;
import util.Cores;

/**
 *
 * @author Edicarlos
 */
public class JD_CLIENTE extends javax.swing.JDialog {

    DateTimeFormatter padrao1 = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
    DateTimeFormatter padrao2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    String dataBase = padrao1.format(LocalDateTime.now());
    String dataBase2 = padrao2.format(LocalDateTime.now());

    public JD_CLIENTE(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtCodigo.requestFocus();
        configIniciais();

        txtCodigo.setEnabled(false);
    }

    public void pegaCodCliente(BASE_CLIENTE base) {
        txtCodigo.setText(String.valueOf(base.getClienteSelecionada()));
    }

    public void buscaDadosFornecedor() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from fornecedor where codigo=?");
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();

            //while (
            rs.last();
            txtRazaoSocial.setText(rs.getString(3));
            txtNomeFantasia.setText(rs.getString(4));
            txtCNPJ.setText(rs.getString(5));
            txtCEP.setText(rs.getString(7));
            txtEndereco.setText(rs.getString(8));
            txtBairro.setText(rs.getString(9));
            txtCidade.setText(rs.getString(10));
            txtEmail.setText(rs.getString(11));
            txtTelefone.setText(rs.getString(12));
            cbBloqueado.setSelectedItem(rs.getString(13));

            cbUF.addItem((String) rs.getString(17));

            //}
            pst.close();
            rs.close();
        } catch (Exception e) {
        }

    }

    public void buscaDadosCliente() {

        try {
            Conexao conn = new Conexao();

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select * from cliente where ID =?");
            pst.setString(1, txtCodigo.getText());

            rs = pst.executeQuery();

            while (rs.next()) {
                txtRazaoSocial.setText(rs.getString(3));
                txtNomeFantasia.setText(rs.getString(4));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }

    }

    public void tituloIncluir() {
        lblTitulo.setText("Cadastro de Cliente - Incluir");

    }

    public void tituloAlterar() {
        lblTitulo.setText("Cadastro de Cliente - Alterar");

    }

    public void tituloExcluir() {
        lblTitulo.setText("Cadastro de Cliente - Excluir");

    }

    public void configIniciais() {

        Cores cor = new Cores();

        //  painelCorpo.setBackground(cor.getCorPainelCorpoForm());
        //   painelTitulo.setBackground(cor.getCorPreenchimentoTituloFormInputs());
        //   painelRodape.setBackground(cor.getPreenchimentoRodapeFormulario());
        txtRazaoSocial.requestFocus();

    }

    public void geraCodCliente() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select codigo from cliente");
            rs = pst.executeQuery();
            while (rs.next()) {
                txtCodigo.setText(String.valueOf(rs.getInt("codigo") + 1));
            }

            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar codigo do cleinte " + e);

        }
        if (txtCodigo.getText().equals("")) {
            txtCodigo.setText(String.valueOf(1));
        } else {

        }

    }

    public void salvar() {
        ModelUsuario user = new ModelUsuario();
        Conexao conn = new Conexao();

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into cliente(codigo,razaoSocial,nomeFantasia,"
                    + "CPF_CNPJ)VALUES(?,?,?,?)");

            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.setString(2, txtRazaoSocial.getText());
            pst.setString(3, txtNomeFantasia.getText());
            pst.setString(4, txtCNPJ.getText());

            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);
            dispose();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel finalizar o cadastro " + e, "Mensagem", 2);
        }
    }
     public void excluir() {
        ModelUsuario user = new ModelUsuario();
        Conexao conn = new Conexao();

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("delete from cliente where codigo =?");
            pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
            pst.execute();
            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);
            dispose();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel finalizar o cadastro " + e, "Mensagem", 2);
        }
    }
     
       public void alterar() {

        try {

            int resp = JOptionPane.showConfirmDialog(null, "Confirma a alteração do Cliente: "
                    + "\n" + txtCodigo.getText()
                    + " | " + txtRazaoSocial.getText() + "?", "Alteração de Cliente", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {

                Conexao conn = new Conexao();

                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE cliente SET razaoSocial=?, "
                        + "nomeFantasia=?,CPF_CNPJ=? where codigo =?");

                pst.setString(1, txtRazaoSocial.getText());
                pst.setString(2, txtNomeFantasia.getText());
                pst.setString(3, txtCNPJ.getText());
                pst.setInt(4, Integer.parseInt(txtCodigo.getText()));

                pst.executeUpdate();

                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                conf.setVisible(true);
                this.dispose();

                pst.close();
            } else {
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o fornecedor! " + e, "AGUITECH", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCorpo = new javax.swing.JPanel();
        painelRodape = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        brnConfirmar = new javax.swing.JButton();
        brnCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Cadastrais = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtRazaoSocial = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtCNPJ = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtNomeFantasia = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCEP = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        cbCetegoria = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        cbUF = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        cbBloqueado = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtBanco = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtAgencia = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtConta = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaDadosBancarios = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnAdicionar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        painelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        brnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        brnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        brnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        brnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        brnConfirmar.setText("Confirmar");
        brnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        brnConfirmar.setContentAreaFilled(false);
        brnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brnConfirmar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        brnConfirmar.setOpaque(true);
        brnConfirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                brnConfirmarFocusGained(evt);
            }
        });
        brnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                brnConfirmarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                brnConfirmarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                brnConfirmarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                brnConfirmarMouseReleased(evt);
            }
        });
        brnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnConfirmarActionPerformed(evt);
            }
        });
        jPanel1.add(brnConfirmar);

        brnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
        brnCancelar.setText("Cancelr");
        brnCancelar.setAlignmentY(0.8F);
        brnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        brnCancelar.setContentAreaFilled(false);
        brnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        brnCancelar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        brnCancelar.setMaximumSize(new java.awt.Dimension(32, 50));
        brnCancelar.setMinimumSize(new java.awt.Dimension(32, 50));
        brnCancelar.setOpaque(true);
        brnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(brnCancelar);

        javax.swing.GroupLayout painelRodapeLayout = new javax.swing.GroupLayout(painelRodape);
        painelRodape.setLayout(painelRodapeLayout);
        painelRodapeLayout.setHorizontalGroup(
            painelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelRodapeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelRodapeLayout.setVerticalGroup(
            painelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelRodapeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 102, 255));

        Cadastrais.setBackground(new java.awt.Color(255, 255, 255));
        Cadastrais.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Codigo:");

        txtCodigo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Razão Social:");

        txtRazaoSocial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtRazaoSocial.setMargin(new java.awt.Insets(4, 2, 2, 10));
        txtRazaoSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRazaoSocialActionPerformed(evt);
            }
        });

        jLabel22.setText("CNPJ-CPF:");

        txtCNPJ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCNPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCNPJActionPerformed(evt);
            }
        });

        jLabel23.setText("Nome Fantasia:");

        txtNomeFantasia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtNomeFantasia.setMargin(new java.awt.Insets(4, 2, 2, 10));
        txtNomeFantasia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeFantasiaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("CEP:");

        txtCEP.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("Endereço:");

        txtEndereco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtEndereco.setMargin(new java.awt.Insets(4, 2, 2, 10));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("E-mail:");

        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("Cidade:");

        txtCidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Categoria:");

        cbCetegoria.setForeground(new java.awt.Color(102, 102, 102));
        cbCetegoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PESSOA FISICA", "PESSOA JURIDICA" }));
        cbCetegoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("UF:");

        cbUF.setForeground(new java.awt.Color(102, 102, 102));
        cbUF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RN" }));
        cbUF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText("Bairro:");

        txtBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Telefone:");

        txtTelefone.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout CadastraisLayout = new javax.swing.GroupLayout(Cadastrais);
        Cadastrais.setLayout(CadastraisLayout);
        CadastraisLayout.setHorizontalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(txtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(80, 80, 80))
                            .addComponent(cbUF, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(txtRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNomeFantasia)))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(cbCetegoria, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 207, Short.MAX_VALUE)))
                .addContainerGap())
        );
        CadastraisLayout.setVerticalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCetegoria, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel18))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(30, 30, 30))
                                    .addComponent(txtNomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel26)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbUF, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(30, 30, 30))
                                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CADASTRAIS", Cadastrais);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel30.setText("Bloquear:");

        cbBloqueado.setForeground(new java.awt.Color(102, 102, 102));
        cbBloqueado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));
        cbBloqueado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Dados Bancários"));

        jLabel20.setText("Banco:");

        txtBanco.setForeground(new java.awt.Color(102, 102, 102));
        txtBanco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        jLabel34.setText("Agencia:");

        txtAgencia.setForeground(new java.awt.Color(102, 102, 102));
        txtAgencia.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        jLabel35.setText("Conta:");

        txtConta.setForeground(new java.awt.Color(102, 102, 102));
        txtConta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        tabelaDadosBancarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Banco", "Agencia", "Conta"
            }
        ));
        tabelaDadosBancarios.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(tabelaDadosBancarios);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        btnAdicionar.setBackground(new java.awt.Color(0, 153, 153));
        btnAdicionar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAdicionar.setForeground(new java.awt.Color(255, 255, 255));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Novo2.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAdicionar.setContentAreaFilled(false);
        btnAdicionar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnAdicionar.setOpaque(true);
        btnAdicionar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAdicionarFocusGained(evt);
            }
        });
        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAdicionarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseReleased(evt);
            }
        });
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdicionar);

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/apagar.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.setAlignmentY(0.8F);
        btnRemover.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnRemover.setContentAreaFilled(false);
        btnRemover.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnRemover.setMaximumSize(new java.awt.Dimension(32, 50));
        btnRemover.setMinimumSize(new java.awt.Dimension(32, 50));
        btnRemover.setOpaque(true);
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        jPanel3.add(btnRemover);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(txtBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34)
                                    .addComponent(txtAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35)
                                    .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 61, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(cbBloqueado, 0, 209, Short.MAX_VALUE))
                .addGap(400, 400, 400)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbBloqueado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("OUTRAS INFORMAÇÕES", jPanel5);

        painelTitulo.setPreferredSize(new java.awt.Dimension(0, 4));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(38, 85, 100));
        lblTitulo.setText("Cadastro de Cliente");

        javax.swing.GroupLayout painelTituloLayout = new javax.swing.GroupLayout(painelTitulo);
        painelTitulo.setLayout(painelTituloLayout);
        painelTituloLayout.setHorizontalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelTituloLayout.setVerticalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout painelCorpoLayout = new javax.swing.GroupLayout(painelCorpo);
        painelCorpo.setLayout(painelCorpoLayout);
        painelCorpoLayout.setHorizontalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelRodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCorpoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addComponent(painelTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
        );
        painelCorpoLayout.setVerticalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addComponent(painelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelRodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void brnConfirmarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_brnConfirmarFocusGained

    }//GEN-LAST:event_brnConfirmarFocusGained

    private void brnConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnConfirmarMouseEntered
        brnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_brnConfirmarMouseEntered

    private void brnConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnConfirmarMouseExited
        brnConfirmar.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_brnConfirmarMouseExited

    private void brnConfirmarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnConfirmarMousePressed

        brnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_brnConfirmarMousePressed

    private void brnConfirmarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnConfirmarMouseReleased
        brnConfirmar.setBackground(new java.awt.Color(0, 204, 204));

        // TODO add your handling code here:
    }//GEN-LAST:event_brnConfirmarMouseReleased

    private void brnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnConfirmarActionPerformed

        switch (lblTitulo.getText()) {
            case "Cadastro de Cliente - Incluir":
                salvar();
                break;
            case "Cadastro de Cliente - Alterar":
                alterar();
                break;
            case "Cadastro de Cliente - Excluir":
                excluir();
                break;
        }
    }//GEN-LAST:event_brnConfirmarActionPerformed

    private void brnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_brnCancelarActionPerformed

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnAdicionarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarMousePressed

    private void btnAdicionarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarMouseReleased

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void txtRazaoSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRazaoSocialActionPerformed
        txtNomeFantasia.requestFocus();
    }//GEN-LAST:event_txtRazaoSocialActionPerformed

    private void txtNomeFantasiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeFantasiaActionPerformed
        txtCNPJ.requestFocus();
    }//GEN-LAST:event_txtNomeFantasiaActionPerformed

    private void txtCNPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCNPJActionPerformed
        txtCEP.requestFocus();  // TODO add your handling code here:
    }//GEN-LAST:event_txtCNPJActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JD_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_CLIENTE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_CLIENTE dialog = new JD_CLIENTE(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cadastrais;
    private javax.swing.JButton brnCancelar;
    private javax.swing.JButton brnConfirmar;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cbBloqueado;
    private javax.swing.JComboBox<String> cbCetegoria;
    private javax.swing.JComboBox<String> cbUF;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelRodape;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JTable tabelaDadosBancarios;
    private javax.swing.JTextField txtAgencia;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JTextField txtBanco;
    private javax.swing.JTextField txtCEP;
    private javax.swing.JTextField txtCNPJ;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtConta;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNomeFantasia;
    private javax.swing.JTextField txtRazaoSocial;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
