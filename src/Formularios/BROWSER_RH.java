/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import util.Cores;

/**
 *
 * @author edica
 */
public class BROWSER_RH extends javax.swing.JFrame {

    /**
     * Creates new form BROWSER_RH
     */
    public BROWSER_RH() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        configInicias();
    }

    public void configInicias() {

        Cores cor = new Cores();
        //  tabela.setSelectionBackground(cor.getCorLinhaSelecionada());
        //  tabela.setSelectionForeground(cor.getCorFonteLinhaSelecionada());

        int maxHeaderHeight = 40;
        Dimension d = new Dimension(TabelaBrowser.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        TabelaBrowser.getTableHeader().setPreferredSize(d);

        btnAlterar.setBackground(Color.WHITE);
        btnExcluir.setBackground(Color.WHITE);
        btnVisualizar.setBackground(Color.WHITE);
        btnVoltar.setBackground(Color.WHITE);

        //Configuração de cabecalho da tabela
        TabelaBrowser.getTableHeader().setOpaque(false);
        TabelaBrowser.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        TabelaBrowser.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        TabelaBrowser.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnEntrar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelaBrowser = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        lblTitulo.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("RH - Listagem de funcionários");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));
        btnEntrar.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnEntrar.setText("Incluir");
        btnEntrar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar.setMaximumSize(new java.awt.Dimension(85, 41));
        btnEntrar.setMinimumSize(new java.awt.Dimension(85, 41));
        btnEntrar.setOpaque(true);
        btnEntrar.setPreferredSize(new java.awt.Dimension(85, 41));
        btnEntrar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrarFocusGained(evt);
            }
        });
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrarMouseReleased(evt);
            }
        });
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Altar2.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setAlignmentY(0.8F);
        btnAlterar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAlterar.setContentAreaFilled(false);
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnAlterar.setMaximumSize(new java.awt.Dimension(85, 41));
        btnAlterar.setMinimumSize(new java.awt.Dimension(85, 41));
        btnAlterar.setOpaque(true);
        btnAlterar.setPreferredSize(new java.awt.Dimension(85, 41));
        btnAlterar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAlterarFocusGained(evt);
            }
        });
        btnAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAlterarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAlterarMouseExited(evt);
            }
        });
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setAlignmentY(0.8F);
        btnExcluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnExcluir.setMaximumSize(new java.awt.Dimension(85, 41));
        btnExcluir.setMinimumSize(new java.awt.Dimension(85, 41));
        btnExcluir.setOpaque(true);
        btnExcluir.setPreferredSize(new java.awt.Dimension(85, 41));
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
        });
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });

        btnVisualizar.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnVisualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Visualizar.png"))); // NOI18N
        btnVisualizar.setText("Visualizar");
        btnVisualizar.setAlignmentY(0.8F);
        btnVisualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVisualizar.setContentAreaFilled(false);
        btnVisualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisualizar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVisualizar.setMaximumSize(new java.awt.Dimension(85, 41));
        btnVisualizar.setMinimumSize(new java.awt.Dimension(85, 41));
        btnVisualizar.setOpaque(true);
        btnVisualizar.setPreferredSize(new java.awt.Dimension(85, 41));
        btnVisualizar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnVisualizarFocusGained(evt);
            }
        });
        btnVisualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVisualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVisualizarMouseExited(evt);
            }
        });
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });

        btnVoltar.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/back_arrow_icon_134660.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setAlignmentY(0.0F);
        btnVoltar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVoltar.setMaximumSize(new java.awt.Dimension(85, 41));
        btnVoltar.setMinimumSize(new java.awt.Dimension(85, 41));
        btnVoltar.setOpaque(true);
        btnVoltar.setPreferredSize(new java.awt.Dimension(85, 41));
        btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVoltarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVoltarMouseExited(evt);
            }
        });
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "CPF", "Matricula" }));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jButton1.setText("Buscar");
        jButton1.setOpaque(true);

        TabelaBrowser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Matricula", "Nome do Funcionario", "Função", "Cargo"
            }
        ));
        jScrollPane1.setViewportView(TabelaBrowser);

        jPanel1.setBackground(new java.awt.Color(222, 220, 220));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblTitulo)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrarFocusGained

    }//GEN-LAST:event_btnEntrarFocusGained

    private void btnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseEntered
        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnEntrarMouseEntered

    private void btnEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseExited
        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrarMouseExited

    private void btnEntrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMousePressed

        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnEntrarMousePressed

    private void btnEntrarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrarMouseReleased

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed


    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnAlterarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAlterarFocusGained

    }//GEN-LAST:event_btnAlterarFocusGained

    private void btnAlterarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseEntered
        btnAlterar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnAlterarMouseEntered

    private void btnAlterarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseExited
        btnAlterar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnAlterarMouseExited

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained

    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(Color.white);
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnVisualizarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnVisualizarFocusGained
     ;
    }//GEN-LAST:event_btnVisualizarFocusGained

    private void btnVisualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseEntered
        btnVisualizar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVisualizarMouseEntered

    private void btnVisualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseExited
        btnVisualizar.setBackground(Color.white);
    }//GEN-LAST:event_btnVisualizarMouseExited

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        JD_PG_BLOCO pg = new JD_PG_BLOCO(this, true);
        pg.setVisible(true);
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnVoltarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseEntered
        btnVoltar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVoltarMouseEntered

    private void btnVoltarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseExited
        btnVoltar.setBackground(Color.white);
    }//GEN-LAST:event_btnVoltarMouseExited

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

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
            java.util.logging.Logger.getLogger(BROWSER_RH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BROWSER_RH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BROWSER_RH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BROWSER_RH.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BROWSER_RH().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelaBrowser;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
