/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Dialog.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import java.awt.Color;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import util.Conexao;
import util.Cores;

/**
 *
 * @author edica
 */
public class FECHAR_PERIODO extends java.awt.Dialog {

    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:SS");
    String horaAtual = hora.format(LocalDateTime.now());

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String dataAtual = dtf.format(LocalDateTime.now());

    static ArrayList<String> produtos = new ArrayList();
    Conexao conn = new Conexao();

    public FECHAR_PERIODO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }

    public void iniciaContadores() {
        lblProcesados.setText(String.valueOf(0));
        lblPendentes.setText(String.valueOf(produtos.size() / 3));
    }

    public void carregaArray() {
        produtos.clear();
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select codigo,atual,valor from tb_produtos");
            rs = pst.executeQuery();
            while (rs.next()) {

                produtos.add(rs.getString("codigo"));
                produtos.add(rs.getString("atual"));
                produtos.add(rs.getString("valor"));

            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

    }

    public void fecharEstoque() {

        new Thread() {

            public void run() {
                barraProgresso.setStringPainted(true);

                barraProgresso.setMaximum(produtos.size() / 3);
                barraProgresso.setStringPainted(true);

                try {
                    int total_linha = produtos.size();
                    int indice = 0;
                    int totalProcessados = 0;
                    while (total_linha - 2 >= indice) {
                        PreparedStatement pst = conn.getConexao().prepareStatement("insert into fechamento_estoque(data_base,"
                                + "CodProduto,qFim,vFim,data2) VALUES(?,?,?,?,?)");

                        pst.setString(1, txtDataBase.getText()); //Data do fechamento
                        pst.setString(2, produtos.get(indice)); //Codigo 
                        pst.setString(3, produtos.get(indice + 1)); //Codigo 
                        pst.setString(4, produtos.get(indice + 2)); //Codigo 
                        pst.setString(5, dataReferencia); //Codigo 

                        totalProcessados = totalProcessados + 1;

                        //  sleep(50);
                        //     barraProgresso.setIndeterminate(true);
                        barraProgresso.setStringPainted(true);
                        lblProcesados.setText(String.valueOf(totalProcessados));
                        lblPendentes.setText(String.valueOf(total_linha / 3 - totalProcessados));
                        barraProgresso.setValue(totalProcessados);
                        indice = indice + 3;
                        float pecentual = Float.parseFloat(lblProcesados.getText()) / (Float.parseFloat(lblPendentes.getText()));

                        pst.execute();
                        pst.close();
                    }
                    barraProgresso.setIndeterminate(false);
                    barraProgresso.setValue(totalProcessados);
                    Confirmacao conf = new Confirmacao(null, true);
                    conf.textoFechamentoEstoque();
                    conf.setVisible(true);
                     dispose();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);

                }
            }
        }
                .start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDataBase = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        btnProcessar = new javax.swing.JButton();
        barraProgresso = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        lblProcesados = new javax.swing.JLabel();
        lblPendentes = new javax.swing.JLabel();

        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        jPanel1.setMaximumSize(new java.awt.Dimension(364, 364));
        jPanel1.setMinimumSize(new java.awt.Dimension(364, 364));
        jPanel1.setPreferredSize(new java.awt.Dimension(364, 364));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Fechamento de Estoque");

        try {
            txtDataBase.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataBase.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Data base:");

        btnProcessar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Go.png"))); // NOI18N
        btnProcessar.setText("Processar");
        btnProcessar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProcessar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcessarActionPerformed(evt);
            }
        });

        barraProgresso.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N

        jLabel3.setText("Itens processados:");

        jLabel4.setText("Restantes");

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Back.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        lblProcesados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblProcesados.setForeground(new java.awt.Color(0, 51, 204));
        lblProcesados.setText("jLabel6");
        lblProcesados.setOpaque(true);

        lblPendentes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPendentes.setForeground(new java.awt.Color(255, 0, 51));
        lblPendentes.setText("jLabel7");
        lblPendentes.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDataBase, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProcessar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addGap(150, 150, 150))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barraProgresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblProcesados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(lblPendentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcessar)
                    .addComponent(btnSair))
                .addGap(31, 31, 31)
                .addComponent(barraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProcesados)
                    .addComponent(lblPendentes))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnProcessarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcessarActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "Confirma o fechamento do estoque?"+"\n"+
                "Essa rotina bloqueará as movimentações de estoque com data inferior a data de fechamento.", "AGUITECH", JOptionPane.YES_NO_OPTION);
        if (resp == 0) {

            fecharEstoque();

        
        } else {
            this.dispose();
        }


    }//GEN-LAST:event_btnProcessarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException {

        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                } catch (InstantiationException ex) {
                    Logger.getLogger(FECHAR_PERIODO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(FECHAR_PERIODO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(FECHAR_PERIODO.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FECHAR_PERIODO dialog = new FECHAR_PERIODO(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnProcessar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPendentes;
    private javax.swing.JLabel lblProcesados;
    private javax.swing.JFormattedTextField txtDataBase;
    // End of variables declaration//GEN-END:variables
}
