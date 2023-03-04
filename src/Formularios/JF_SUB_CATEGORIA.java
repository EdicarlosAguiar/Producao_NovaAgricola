/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Controller.ControlProduto;
import FormNotificacao.Confirmacao;
import FormNotificacao.Exeception;
import FormNotificacao.CONFUPDATEPROD;
import FormNotificacao.exclusaoFinalizada;
import Model.ModelProduto;
import Model.ModelUsuario;
import java.awt.Color;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.Conexao;
import util.Cores;
import util.utilitario;

public class JF_SUB_CATEGORIA extends javax.swing.JDialog {

    Cores cor = new Cores();

    String operacao;

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    Conexao conn = new Conexao();

    /**
     * Creates new form JD_PRODUTO
     */
    public JF_SUB_CATEGORIA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtCodigo.requestFocus();
        configIniciais();
        txtCodigo.setEnabled(false);
        txtNome.requestFocus();

    }

    public void buscaCategoria() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select * from categoria where codigo =?");
            pst.setString(1, txtCodCat.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtCategoria.setText(rs.getString(3));
            }
            rs.close();
            pst.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void buscaSubCategoria() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select * from subcategoria where codCat =?");
            pst.setString(1, txtCodCat.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                String codCat = rs.getString(2).substring(2, 4);
                int categoria = Integer.parseInt(codCat) + 1;

                txtCodigo.setText(String.valueOf(categoria));
                if (categoria < 10) {
                    codCat = "0" + categoria;
                    txtCodigo.setText(txtCodCat.getText() + codCat);
                } else {
                    txtCodigo.setText(txtCodCat.getText() + categoria);

                }
            }
            if (txtCodigo.getText().equals("")) {
                txtCodigo.setText(txtCodCat.getText() + "01");
            } else {

            }
            rs.close();
            pst.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void tituloIncluir() {
        lblTitulo.setText("Cadastro de Sub Categoria - Incluir");
        lblTitulo.setForeground(Color.WHITE);
    }

    public void parametrosAlterar() {
        lblTitulo.setText("Cadastro de Sub Categoria - Alterar");
        lblTitulo.setForeground(Color.WHITE);

    }

    public void pegaCategoria(BASE_SUB_CATEGORIA cat) {
        txtCodigo.setText(cat.getCategoriaSelecionada());
    }

    public void parametrosExcluir() {
        lblTitulo.setText("Cadastro de Sub Categoria - Excluir");
        txtNome.setEnabled(false);
        lblTitulo.setForeground(Color.WHITE);
    }

    public void configIniciais() {

        painelCorpo.setBackground(cor.getCorPainelCorpoForm());
        painelTitulo.setBackground(cor.getCorPreenchimentoTituloFormInputs());
        painelRodape.setBackground(cor.getPreenchimentoRodapeFormulario());
        txtCodigo.setBackground(cor.getCorCampoDesabilitado());
        txtCategoria.setEnabled(false);
        txtCategoria.setBackground(cor.getCorCampoDesabilitado());
        txtCodigo.setOpaque(true);

    }

    public void pegaDados() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from subcategoria where codigo=?");
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtCodigo.setText(rs.getString(2));
                txtNome.setText(rs.getString(3));
                txtCodCat.setText(rs.getString(4));
                txtCategoria.setText(rs.getString(5));

            }
            pst.close();
            rs.close();
        } catch (Exception e) {
        }

    }

    public void pegaCat() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select categoria from categoria where codigo=?");
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtNome.setText(rs.getString(1));
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
        }

    }

    public void salvar() {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into subcategoria(codigo,subCategoria,codCat,categoria)"
                    + "VALUES(?,?,?,?)");

            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtNome.getText());
            pst.setString(3, txtCodCat.getText());
            pst.setString(4, txtCategoria.getText());
            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);
            this.dispose();

            pst.close();
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, "Não foi possivel finalziar a operação " + e);
        }
    }

    public void alterar() {

        try {

            int resp = JOptionPane.showConfirmDialog(null, "Confirma a alteração da Sub Categoria " + txtCodigo.getText() + "?",
                    "Alteração", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {

                Conexao conn = new Conexao();

                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE subcategoria SET subCategoria=? where codigo =?");

                pst.setString(1, txtNome.getText());
                pst.setInt(2, Integer.parseInt(txtCodigo.getText()));

                pst.executeUpdate();

                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                conf.setVisible(true);
                this.dispose();

                pst.close();
            } else {
                dispose();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o produto! " + e, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void excluir() {

        try {

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão da Sub Categoria: " + txtCodigo.getText() + "?",
                    "Exclusão", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                Conexao conn = new Conexao();
                PreparedStatement pst;
                ResultSet rs;
                int linha = 1;
                pst = conn.getConexao().prepareStatement("delete from categoria where codigo=?");
                pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
                pst.executeUpdate();

                exclusaoFinalizada confirmacao = new exclusaoFinalizada(new javax.swing.JFrame(), true);
                confirmacao.setVisible(true);
                pst.close();
                dispose();
            } else {
                dispose();
            }
        } catch (Exception e) {
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCorpo = new javax.swing.JPanel();
        painelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        painelRodape = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        brnConfirmar = new javax.swing.JButton();
        brnCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Cadastrais = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtNome = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtCodCat = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        painelTitulo.setBackground(new java.awt.Color(102, 204, 255));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastro de Categoria de Produto");

        javax.swing.GroupLayout painelTituloLayout = new javax.swing.GroupLayout(painelTitulo);
        painelTitulo.setLayout(painelTituloLayout);
        painelTituloLayout.setHorizontalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelTituloLayout.setVerticalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelRodape.setBackground(new java.awt.Color(153, 255, 255));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        brnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        brnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        brnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        brnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        brnConfirmar.setText("Confirmar");
        brnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        brnConfirmar.setContentAreaFilled(false);
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

        brnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        brnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
        brnCancelar.setText("Cancelr");
        brnCancelar.setAlignmentY(0.8F);
        brnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        brnCancelar.setContentAreaFilled(false);
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

        Cadastrais.setBackground(new java.awt.Color(255, 255, 255));
        Cadastrais.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel17.setText("Cod Sub");

        jLabel18.setText("Sub Categoria:");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtNome.setBorder(null);
        txtNome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNome.setMargin(new java.awt.Insets(4, 2, 2, 10));
        txtNome.setOpaque(false);
        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel19.setText("Cod Cat:");

        jLabel20.setText("Categoria de Produto:");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtCodCat.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCat.setBorder(null);
        txtCodCat.setOpaque(false);
        txtCodCat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCatFocusLost(evt);
            }
        });
        txtCodCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(txtCodCat, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtCodCat, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
        );

        txtCategoria.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtCategoria.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCategoria.setMargin(new java.awt.Insets(4, 2, 2, 10));
        txtCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCategoriaActionPerformed(evt);
            }
        });

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.setEnabled(false);
        txtCodigo.setOpaque(false);
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CadastraisLayout = new javax.swing.GroupLayout(Cadastrais);
        Cadastrais.setLayout(CadastraisLayout);
        CadastraisLayout.setHorizontalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(48, 48, 48))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                .addComponent(txtCodigo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(txtCategoria))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        CadastraisLayout.setVerticalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCategoria))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CADASTRAIS", Cadastrais);

        javax.swing.GroupLayout painelCorpoLayout = new javax.swing.GroupLayout(painelCorpo);
        painelCorpo.setLayout(painelCorpoLayout);
        painelCorpoLayout.setHorizontalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(painelRodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        painelCorpoLayout.setVerticalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addComponent(painelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addGap(18, 18, 18)
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
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Campo obrigatório não preenchido! ", "Atenção!", 2);

        } else {

            switch (lblTitulo.getText()) {
                case "Cadastro de Sub Categoria - Incluir":
                    salvar();
                    break;
                case "Cadastro de Sub Categoria - Alterar":
                    alterar();
                    break;
                case "Cadastro de Sub Categoria - Excluir":
                    excluir();
                    break;
            }
        }
    }//GEN-LAST:event_brnConfirmarActionPerformed

    private void brnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_brnCancelarActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        txtCodCat.requestFocus();
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCategoriaActionPerformed

    private void txtCodCatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCatFocusLost
        buscaCategoria();
        buscaSubCategoria();
// TODO add your handling code here:
    }//GEN-LAST:event_txtCodCatFocusLost

    private void txtCodCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCatActionPerformed
        brnConfirmar.requestFocus();
        buscaCategoria();
        buscaSubCategoria();
    }//GEN-LAST:event_txtCodCatActionPerformed

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
            java.util.logging.Logger.getLogger(JF_SUB_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_SUB_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_SUB_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_SUB_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JF_SUB_CATEGORIA dialog = new JF_SUB_CATEGORIA(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelRodape;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodCat;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
