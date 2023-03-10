/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.chart.PieChart;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import util.Conexao;
import util.utilitario;

/**
 *
 * @author Desktop
 */
public class DJ_SERVIDOR extends javax.swing.JDialog {

    utilitario util = new utilitario();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    public DJ_SERVIDOR(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));

        initComponents();
        btnContinuar.requestFocus();
        txtDataBase.setText(dataAtual);
    }

    public void ultimaFechamento() {

        try {
            Conexao conn = new Conexao();

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select data_base from fechamento_estoque order by data2 desc");
            rs = pst.executeQuery();

            while (rs.next()) {
                util.setUltimoFechamento(rs.getString("data_base"));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar data do ultima fechamento de "
                    + "estoque.!" + ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbServidor = new javax.swing.JComboBox<>();
        btnContinuar = new javax.swing.JButton();
        lblBemVindo = new javax.swing.JLabel();
        lblFechar = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cntDataBase = new javax.swing.JPanel();
        txtDataBase = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(38, 85, 100));
        jLabel1.setText("Configura????es iniciais");

        cbServidor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbServidor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LocaWeb", "Teste", "Local", " ", " " }));
        cbServidor.setBorder(null);

        btnContinuar.setBackground(new java.awt.Color(0, 102, 102));
        btnContinuar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnContinuar.setForeground(new java.awt.Color(255, 255, 255));
        btnContinuar.setText("Continuar ...");
        btnContinuar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnContinuar.setContentAreaFilled(false);
        btnContinuar.setOpaque(true);
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });

        lblBemVindo.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblBemVindo.setForeground(new java.awt.Color(38, 85, 100));
        lblBemVindo.setText("AGUITECH");

        lblFechar.setBackground(new java.awt.Color(255, 255, 255));
        lblFechar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblFechar.setForeground(new java.awt.Color(51, 51, 51));
        lblFechar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechar.setText("x");
        lblFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFechar.setOpaque(true);
        lblFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFecharMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblFecharMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lblFecharMouseReleased(evt);
            }
        });
        lblFechar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblFecharKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lblFecharKeyReleased(evt);
            }
        });

        jPanel2.setPreferredSize(new java.awt.Dimension(0, 4));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens2/IconBrowser.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Servidor");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Data base:");

        cntDataBase.setBackground(new java.awt.Color(255, 255, 255));
        cntDataBase.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 2, 1, new java.awt.Color(204, 204, 204)));

        txtDataBase.setBorder(null);
        try {
            txtDataBase.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataBase.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDataBase.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataBaseFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataBaseFocusLost(evt);
            }
        });

        javax.swing.GroupLayout cntDataBaseLayout = new javax.swing.GroupLayout(cntDataBase);
        cntDataBase.setLayout(cntDataBaseLayout);
        cntDataBaseLayout.setHorizontalGroup(
            cntDataBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cntDataBaseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDataBase))
        );
        cntDataBaseLayout.setVerticalGroup(
            cntDataBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDataBase, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                            .addComponent(lblBemVindo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(lblFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(btnContinuar, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(cbServidor, 0, 280, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(cntDataBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblBemVindo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(7, 7, 7)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cntDataBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(btnContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        Conexao conn = new Conexao();

        String dataBase = txtDataBase.getText();
        util.setDataBase(dataBase);

        if (cbServidor.getSelectedItem().equals("Local")) {
            conn.setUsuario("root");
            conn.setSenha("");
            conn.setDriver("jdbc:mysql://");
            conn.setUrl("localhost/");
            conn.setDataBase("novaagricola?characterEncoding=utf8");
            conn.setServidor("Servidor Local - LocalHost");
            this.dispose();
            NOVO_LOGIN login = new NOVO_LOGIN();

            login.setVisible(true);
        } else if (cbServidor.getSelectedItem().equals("Teste")) {
            conn.setUsuario("testenovaagr");
            conn.setSenha("C@rlim05");
            conn.setDriver("jdbc:mysql://");
            conn.setUrl("testenovaagr.mysql.dbaas.com.br/");
            conn.setDataBase("testenovaagr?serverTimezone=UTC");
            conn.setServidor("Servidor em Nuvem - Ambiente de Teste");
            this.dispose();
            NOVO_LOGIN login = new NOVO_LOGIN();

            login.setVisible(true);
        } else {
            conn.setUsuario("novaagricola");
            conn.setSenha("C@rlim05");
            conn.setDriver("jdbc:mysql://");
            conn.setUrl("novaagricola.mysql.dbaas.com.br/");
            conn.setDataBase("novaagricola?serverTimezone=UTC");
            conn.setServidor("Servidor em Nuvem - Locaweb");
            this.dispose();
            NOVO_LOGIN login = new NOVO_LOGIN();

            login.setVisible(true);

        }
        ultimaFechamento();

    }//GEN-LAST:event_btnContinuarActionPerformed

    private void lblFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFecharMouseClicked
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharMouseClicked

    private void lblFecharMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFecharMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharMousePressed

    private void lblFecharMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFecharMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharMouseReleased

    private void lblFecharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblFecharKeyPressed

        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharKeyPressed

    private void lblFecharKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblFecharKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharKeyReleased

    private void txtDataBaseFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataBaseFocusGained
        txtDataBase.setBackground(new Color(242, 242, 242));
        cntDataBase.setBackground(new Color(242, 242, 242));
    }//GEN-LAST:event_txtDataBaseFocusGained

    private void txtDataBaseFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataBaseFocusLost
        txtDataBase.setBackground(new Color(255, 255, 255));
        cntDataBase.setBackground(new Color(255, 255, 255));
    }//GEN-LAST:event_txtDataBaseFocusLost

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
            java.util.logging.Logger.getLogger(DJ_SERVIDOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DJ_SERVIDOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DJ_SERVIDOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DJ_SERVIDOR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DJ_SERVIDOR dialog = new DJ_SERVIDOR(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnContinuar;
    private javax.swing.JComboBox<String> cbServidor;
    private javax.swing.JPanel cntDataBase;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblBemVindo;
    private javax.swing.JLabel lblFechar;
    private javax.swing.JFormattedTextField txtDataBase;
    // End of variables declaration//GEN-END:variables
}
