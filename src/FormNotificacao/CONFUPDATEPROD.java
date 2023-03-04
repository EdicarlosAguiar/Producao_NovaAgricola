/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FormNotificacao;


import Formularios.CADASTRO_PRODUTO;


import Model.ModelProduto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author Edicarlos
 */
public class CONFUPDATEPROD extends javax.swing.JDialog {

    private String resposta;

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resp) {
        this.resposta = resposta;
    }

    public CONFUPDATEPROD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }

    public void pegaDados(CADASTRO_PRODUTO prod) {
        lblCOdigo.setText(" " + prod.getCodigo());
        lblNome.setText(" " + prod.getNome());
        lblUnidade.setText(" " + prod.getUnidade());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblCOdigo = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblUnidade = new javax.swing.JLabel();
        btnSim = new javax.swing.JButton();
        btnNao = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel3.setBackground(new java.awt.Color(94, 110, 110));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("AGUITECH");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens2/IconBrowser.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens2/Interr 80x81.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Confirma a alteração do produto abaixo?");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Codigo:");
        jLabel8.setMaximumSize(new java.awt.Dimension(2, 25));
        jLabel8.setMinimumSize(new java.awt.Dimension(2, 25));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Descrição:");
        jLabel9.setMaximumSize(new java.awt.Dimension(2, 25));
        jLabel9.setMinimumSize(new java.awt.Dimension(2, 25));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Unidade:");
        jLabel10.setMaximumSize(new java.awt.Dimension(2, 25));
        jLabel10.setMinimumSize(new java.awt.Dimension(2, 25));

        lblCOdigo.setBackground(new java.awt.Color(232, 232, 232));
        lblCOdigo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCOdigo.setForeground(new java.awt.Color(51, 51, 51));
        lblCOdigo.setMaximumSize(new java.awt.Dimension(2, 25));
        lblCOdigo.setMinimumSize(new java.awt.Dimension(2, 25));
        lblCOdigo.setOpaque(true);

        lblNome.setBackground(new java.awt.Color(232, 232, 232));
        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNome.setForeground(new java.awt.Color(51, 51, 51));
        lblNome.setMaximumSize(new java.awt.Dimension(2, 25));
        lblNome.setMinimumSize(new java.awt.Dimension(2, 25));
        lblNome.setOpaque(true);

        lblUnidade.setBackground(new java.awt.Color(232, 232, 232));
        lblUnidade.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUnidade.setForeground(new java.awt.Color(51, 51, 51));
        lblUnidade.setMaximumSize(new java.awt.Dimension(2, 25));
        lblUnidade.setMinimumSize(new java.awt.Dimension(2, 25));
        lblUnidade.setOpaque(true);

        btnSim.setBackground(new java.awt.Color(0, 102, 102));
        btnSim.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        btnSim.setForeground(new java.awt.Color(255, 255, 255));
        btnSim.setText("Sim");
        btnSim.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnSim.setContentAreaFilled(false);
        btnSim.setOpaque(true);
        btnSim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSimMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSimMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSimMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnSimMouseReleased(evt);
            }
        });
        btnSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimActionPerformed(evt);
            }
        });

        btnNao.setBackground(new java.awt.Color(204, 204, 204));
        btnNao.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnNao.setText("Não");
        btnNao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnNao.setContentAreaFilled(false);
        btnNao.setOpaque(true);
        btnNao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNaoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNaoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnNaoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnNaoMouseReleased(evt);
            }
        });
        btnNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNaoActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(217, 212, 212));
        jPanel6.setPreferredSize(new java.awt.Dimension(0, 3));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblNome, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32)
                                        .addComponent(lblCOdigo, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(81, 81, 81))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSim, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNao, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCOdigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUnidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnNao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSim, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNaoActionPerformed
        dispose();
    }//GEN-LAST:event_btnNaoActionPerformed

    private void btnNaoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNaoMouseReleased

    private void btnNaoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNaoMousePressed

    private void btnNaoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNaoMouseExited

    private void btnNaoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNaoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNaoMouseEntered

    private void btnSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimActionPerformed
        CADASTRO_PRODUTO prod = new CADASTRO_PRODUTO();
    prod.setResp(this.getResposta());
        resposta = "SIM";
        
        prod.setResp(this.getResposta());
        
        dispose();


    }//GEN-LAST:event_btnSimActionPerformed

    private void btnSimMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMouseReleased
        btnSim.setBackground(new java.awt.Color(0, 204, 204));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimMouseReleased

    private void btnSimMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMousePressed

        btnSim.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnSimMousePressed

    private void btnSimMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMouseExited
        btnSim.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimMouseExited

    private void btnSimMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSimMouseEntered
        btnSim.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnSimMouseEntered

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CONFUPDATEPROD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CONFUPDATEPROD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CONFUPDATEPROD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CONFUPDATEPROD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CONFUPDATEPROD dialog = new CONFUPDATEPROD(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnNao;
    private javax.swing.JButton btnSim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCOdigo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblUnidade;
    // End of variables declaration//GEN-END:variables
}
