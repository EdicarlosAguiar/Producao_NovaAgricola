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

/**
 *
 * @author Edicarlos
 */
public class JF_CATEGORIA extends javax.swing.JDialog {

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
    public JF_CATEGORIA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtCodigo.requestFocus();
        configIniciais();
        txtCodigo.setEnabled(false);
        txtNome.requestFocus();
        buscaCategoria();
         btnCancelar.setBackground(Color.WHITE);

        if (txtCodigo.getText().equals("")) {
            txtCodigo.setText(("01"));
        }
        else if (Integer.parseInt(txtCodigo.getText()) < 10) {

            txtCodigo.setText("0" + txtCodigo.getText());
        } else {

        }
    }

    public void buscaCategoria() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select * from categoria");

            rs = pst.executeQuery();

            while (rs.next()) {
                txtCodigo.setText(String.valueOf(rs.getInt(2) + 1));

            }
            rs.close();
            pst.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void tituloIncluir() {
        lblTitulo.setText("Cadastro de Categoria - Incluir");
        lblTitulo.setForeground(Color.WHITE);
    }

    public void parametrosAlterar() {
        lblTitulo.setText("Cadastro de Categoria - Alterar");
        lblTitulo.setForeground(Color.WHITE);

    }
    
      public void parametrosVisualizar() {
        lblTitulo.setText("Cadastro de Categoria - Visaulizar");
        lblTitulo.setForeground(Color.WHITE);
        txtCodigo.setEnabled(false);
        txtNome.setEnabled(false);
        brnConfirmar.setEnabled(false);

    }


    public void pegaCategoria(BASE_CATEGORIA cat) {
        txtCodigo.setText(cat.getCategoriaSelecionada());
    }

    public void parametrosExcluir() {
        lblTitulo.setText("Cadastro de Categoria - Excluir");
        txtNome.setEnabled(false);
        lblTitulo.setForeground(Color.WHITE);
       
    }

    public void configIniciais() {

        Cores cor = new Cores();

     //   painelCorpo.setBackground(cor.getCorPainelCorpoForm());
      //  painelTitulo.setBackground(cor.getCorPreenchimentoTituloFormInputs());
       // painelRodape.setBackground(cor.getPreenchimentoRodapeFormulario());

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

    public void salvarCat() {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into categoria(codigo,categoria)VALUES(?,?)");

            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtNome.getText());
            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);
            this.dispose();

            pst.close();
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, "Erro ao salvar produto" + e);
        }
    }

    public void alterarCat() {

        try {

            int resp = JOptionPane.showConfirmDialog(null, "Confirma a alteração da Categoria " + txtCodigo.getText() + "?", "Alteração de Categoria", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {

                Conexao conn = new Conexao();

                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE categoria SET categoria=? where codigo =?");

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

    public void excluirCat() {

        try {

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do Categoria: " + txtCodigo.getText() + "?", "Exclusão de Categoria", JOptionPane.YES_NO_OPTION);
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
        brnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Cadastrais = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtNome = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        painelTitulo.setBackground(new java.awt.Color(94, 110, 110));
        painelTitulo.setForeground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Cadastro de Categoria de Produto");

        javax.swing.GroupLayout painelTituloLayout = new javax.swing.GroupLayout(painelTitulo);
        painelTitulo.setLayout(painelTituloLayout);
        painelTituloLayout.setHorizontalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelTituloLayout.setVerticalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        brnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        brnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        brnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        brnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Yes.png"))); // NOI18N
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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Close.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setAlignmentY(0.8F);
        btnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnCancelar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnCancelar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnCancelar.setOpaque(true);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelRodapeLayout = new javax.swing.GroupLayout(painelRodape);
        painelRodape.setLayout(painelRodapeLayout);
        painelRodapeLayout.setHorizontalGroup(
            painelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelRodapeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(brnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelRodapeLayout.setVerticalGroup(
            painelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelRodapeLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(painelRodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brnConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        Cadastrais.setBackground(new java.awt.Color(255, 255, 255));
        Cadastrais.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel17.setText("Codigo:");

        jLabel18.setText("Categoria de Produto:");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtCodigo.setBorder(null);
        txtCodigo.setEnabled(false);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtNome.setBorder(null);
        txtNome.setMargin(new java.awt.Insets(4, 2, 2, 10));
        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(txtNome))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout CadastraisLayout = new javax.swing.GroupLayout(Cadastrais);
        Cadastrais.setLayout(CadastraisLayout);
        CadastraisLayout.setHorizontalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 348, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(painelRodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            case "Cadastro de Categoria - Incluir":
                salvarCat();
                break;
            case "Cadastro de Categoria - Alterar":
                alterarCat();
                break;
            case "Cadastro de Categoria - Excluir":
                excluirCat();
                break;
        }

    }//GEN-LAST:event_brnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        txtNome.requestFocus();
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        txtCodigo.requestFocus();
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        brnConfirmar.requestFocus();
    }//GEN-LAST:event_txtNomeActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
      btnCancelar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
     btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCancelarMouseExited

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
            java.util.logging.Logger.getLogger(JF_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_CATEGORIA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JF_CATEGORIA dialog = new JF_CATEGORIA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton brnConfirmar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelRodape;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
