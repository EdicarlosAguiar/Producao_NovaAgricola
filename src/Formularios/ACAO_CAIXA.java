/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import static Formularios.BASE_CAIXA.statusMOv;
import Model.ModelUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author edica
 */
public class ACAO_CAIXA extends javax.swing.JDialog {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());
    static private int codTitulo;
    static private int codCaixa;
    static private String valor, natureza, observacao, nomeCaixa;

    public int getCodCaixa() {
        return codCaixa;
    }

    public void setCodCaixa(int codCaixa) {
        this.codCaixa = codCaixa;
    }

    public String getNomeCaixa() {
        return nomeCaixa;
    }

    public void setNomeCaixa(String nomeCaixa) {
        this.nomeCaixa = nomeCaixa;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getCodTitulo() {
        return codTitulo;
    }

    public void setCodTitulo(int codTitulo) {
        this.codTitulo = codTitulo;
    }

    public ACAO_CAIXA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void buscaStatusdoMov() {

        Conexao conn = new Conexao();
        Cores cor = new Cores();
        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select status_mov from conta_finan where codigo =? order by status_mov");
            pst.setInt(1, codCaixa);
            rs = pst.executeQuery();

            while (rs.next()) {
                statusMOv = rs.getString("status_mov");
            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }

    }

    public void notificaAprovacao() {
        ModelUsuario mod = new ModelUsuario();
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String meuEmail = "notificacao@aguitech.com.br";
        String senha = "C@rlim051215";

        HtmlEmail email = new HtmlEmail();
        email.setSSLOnConnect(true);
        email.setHostName("email-ssl.com.br");
        email.setSslSmtpPort("465");
        email.setAuthentication(meuEmail, senha);
        try {
            email.setFrom(meuEmail, "AGUITECH");
            email.setDebug(true);
            email.setSubject("Movimentação aprovada");

            StringBuilder builder = new StringBuilder();
            builder.append("<p>MOVIMENTAÇÃO APROVADA<p>");

            //Dados do titulo
            builder.append("<p>---------------------------------------------------------------</p>");
            builder.append("Caixinha: " + "<h3>" + this.getNomeCaixa() + "</h3>");
            builder.append("Codigo Mov: " + "<h3>" + this.getCodTitulo() + "</h3>");
            builder.append("Natureza da OP: " + "<h3>" + this.getNatureza() + "</h3>");
            builder.append("Historico: " + "<h3>" + this.getObservacao() + "</h3>");
            builder.append("Valor: " + "<h3>" + this.getValor() + "</h3>");
            builder.append("<p>---------------------------------------------------------------</p>");
            builder.append("<p><b>Aprovador:  </b>" + mod.getUsuarioLogado() + "</p>");

            email.setHtmlMsg(builder.toString());
            email.addTo("tiagolimasousa@outlook.com.br");
            //      email.addTo("edicarlosaguiar14@gmail.com");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

    public void notificaDesaprovação() {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        String meuEmail = "notificacao@aguitech.com.br";
        String senha = "C@rlim051215";

        HtmlEmail email = new HtmlEmail();
        email.setSSLOnConnect(true);
        email.setHostName("email-ssl.com.br");
        email.setSslSmtpPort("465");
        email.setAuthentication(meuEmail, senha);
        try {
            email.setFrom(meuEmail, "AGUITECH");
            email.setDebug(true);
            email.setSubject("Movimentação - Aprovação Excluida");

            StringBuilder builder = new StringBuilder();
            builder.append("<h2>Apovação Excluida</h2>");

            //Dados do titulo
            builder.append("Caixinha: " + "<h3>" + this.getNomeCaixa() + "</h3>");
            builder.append("Codigo Mov: " + "<h3>" + this.getCodTitulo() + "</h3>");
            builder.append("Natureza da OP: " + "<h3>" + this.getNatureza() + "</h3>");
            builder.append("Historico: " + "<h3>" + this.getObservacao() + "</h3>");
            builder.append("Valor: " + "<h3>" + this.getValor() + "</h3>");

            email.setHtmlMsg(builder.toString());
            email.addTo("tiagolimasousa@outlook.com.br");

            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
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
        btnAbrir = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        btnMivimento = new javax.swing.JButton();
        btnAprovar = new javax.swing.JButton();
        btnDesfazerApr = new javax.swing.JButton();
        btnAprovar1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        btnAbrir.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Unlock.png"))); // NOI18N
        btnAbrir.setText("Abertura");
        btnAbrir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAbrir.setIconTextGap(10);
        btnAbrir.setPreferredSize(new java.awt.Dimension(250, 30));
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnFechar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Terminate.png"))); // NOI18N
        btnFechar.setText("Fechamento");
        btnFechar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFechar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnFechar.setIconTextGap(10);
        btnFechar.setPreferredSize(new java.awt.Dimension(250, 30));
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnMivimento.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMivimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Sync.png"))); // NOI18N
        btnMivimento.setText("Movimentação");
        btnMivimento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMivimento.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnMivimento.setIconTextGap(10);
        btnMivimento.setPreferredSize(new java.awt.Dimension(250, 30));
        btnMivimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMivimentoActionPerformed(evt);
            }
        });

        btnAprovar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAprovar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Thumbs up.png"))); // NOI18N
        btnAprovar.setText("Aprovar");
        btnAprovar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAprovar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAprovar.setIconTextGap(10);
        btnAprovar.setPreferredSize(new java.awt.Dimension(250, 30));
        btnAprovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprovarActionPerformed(evt);
            }
        });

        btnDesfazerApr.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDesfazerApr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Thumbs down.png"))); // NOI18N
        btnDesfazerApr.setText("Desfazer aprovação");
        btnDesfazerApr.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDesfazerApr.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDesfazerApr.setIconTextGap(10);
        btnDesfazerApr.setPreferredSize(new java.awt.Dimension(250, 30));
        btnDesfazerApr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesfazerAprActionPerformed(evt);
            }
        });

        btnAprovar1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAprovar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Info.png"))); // NOI18N
        btnAprovar1.setText("Solicitar Justificativa");
        btnAprovar1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAprovar1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnAprovar1.setIconTextGap(10);
        btnAprovar1.setPreferredSize(new java.awt.Dimension(250, 30));
        btnAprovar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprovar1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Back.png"))); // NOI18N
        jButton2.setText("Sair");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("O que deseja fazer?");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Movimentações no Caixainha");
        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAbrir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFechar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMivimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAprovar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDesfazerApr, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAprovar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(34, 34, 34)
                .addComponent(btnAbrir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMivimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAprovar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDesfazerApr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAprovar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        MOV_CAIXINHA movCaixa = new MOV_CAIXINHA(null, true);
        ABRIR_CAIXINHA mov = new ABRIR_CAIXINHA(new javax.swing.JFrame(), true);
        mov.geraCodigoCaixa();
        mov.setVisible(true);
        movCaixa.carregaArrayCaixa();
        this.dispose();
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        buscaStatusdoMov();
        //  JOptionPane.showMessageDialog(this,statusMOv);

        //Verifica se existe moviemntação não aprovadas
        if (statusMOv.equals("PENDENTE")) {
            JOptionPane.showMessageDialog(this, "Açao não permitida!" + "\n"
                    + "Não é permitido realizar o fechamento do caixa com movimentações pendentes de aprovação!", "AGUITECH", 2);
        } else {
            Conexao conn = new Conexao();
            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma o fechamento do caixa:" + "\n"
                    + nomeCaixa + "?",
                    "AGUITECH", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {
                try {
                    PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE conta_finan SET status_caixa=?,"
                            + "data_fechamento=? where codigo =?");
                    pst.setString(1, "FECHADO");
                    pst.setString(2, dataAtual);
                    pst.setInt(3, this.getCodCaixa());
                    pst.executeUpdate();
                    //  notificaAprovacao();
                    Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                    conf.setVisible(true);
                    this.dispose();
                    pst.close();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o produto! " + ex, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
                }
            } else {

            }
        }
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnMivimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMivimentoActionPerformed

        MOV_CAIXINHA mov = new MOV_CAIXINHA(new javax.swing.JFrame(), true);
        mov.carregaComboCentroCusto();
        mov.carregaArrayCaixa();
        mov.carregaCaixa();
        //  mov.carregaComboEquipamentos();
        //  mov.carregaComboCaixas();
        //  mov.carregaArrayNatureza();
        mov.setVisible(true);
        mov.carregaComboCentroCusto();
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnMivimentoActionPerformed

    private void btnAprovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprovarActionPerformed
        ModelUsuario mod = new ModelUsuario();

        if (mod.getUsuarioLogado().equals("Leonardo") || mod.getUsuarioLogado().equals("Edicarlos")) {
            Conexao conn = new Conexao();
            try {
                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE conta_finan SET status_mov=?,"
                        + "usuario_apr=?,data_aprovacao=? where ID =?");
                pst.setString(1, "APROVADO");
                pst.setString(2, mod.getUsuarioLogado());
                pst.setString(3, dataDigiatacao);
                pst.setInt(4, this.getCodTitulo());
                pst.executeUpdate();
                notificaAprovacao();
                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                conf.setVisible(true);
                this.dispose();
                pst.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o produto! " + ex, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Açao não permitida!" + "\n"
                    + "Usuario não autorizado!", "AGUITECH", 2);
        }

    }//GEN-LAST:event_btnAprovarActionPerformed

    private void btnDesfazerAprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesfazerAprActionPerformed

        ModelUsuario mod = new ModelUsuario();

        if (mod.getUsuarioLogado().equals("Leonardo") || mod.getUsuarioLogado().equals("Edicarlos")) {

            Conexao conn = new Conexao();
            try {
                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE conta_finan SET status_mov=? where id =?");
                pst.setString(1, "PENDENTE");
                pst.setInt(2, this.getCodTitulo());
                pst.executeUpdate();
                notificaDesaprovação();
                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                conf.setVisible(true);
                this.dispose();
                pst.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o produto! " + ex, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Açao não permitida!" + "\n"
                    + "Usuario não autorizado!", "AGUITECH", 2);
        }
    }//GEN-LAST:event_btnDesfazerAprActionPerformed

    private void btnAprovar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprovar1ActionPerformed

        JD_JUST_MOV_CX just = new JD_JUST_MOV_CX(new javax.swing.JFrame(), true);
        just.setCodTitulo(codTitulo);
        just.setNatureza(natureza);
        just.setObservacao(observacao);
        just.setValor(valor);

        if (codTitulo == 0) {
            JOptionPane.showMessageDialog(this, "Selecione algum titulo! ", "AGUITECH", 2);
        } else {
            just.setVisible(true);
            dispose();
        }

    }//GEN-LAST:event_btnAprovar1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ACAO_CAIXA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ACAO_CAIXA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ACAO_CAIXA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ACAO_CAIXA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ACAO_CAIXA dialog = new ACAO_CAIXA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnAprovar;
    private javax.swing.JButton btnAprovar1;
    private javax.swing.JButton btnDesfazerApr;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnMivimento;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
