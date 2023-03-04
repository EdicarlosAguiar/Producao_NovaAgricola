/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import Model.ModelUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Conexao;
import util.Cores;

/**
 *
 * @author edica
 */
public class MOV_CAIXINHA extends javax.swing.JDialog {

   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    private static ArrayList<String> natureza_finan = new ArrayList();
    private static ArrayList<String> centro_custo = new ArrayList();
    private static ArrayList<String> equipamentos = new ArrayList();
    private static ArrayList<String> caixa = new ArrayList();

    int codCaixa;

    public ArrayList<String> getNatureza_finan() {
        return natureza_finan;
    }

    public static void setNatureza_finan(ArrayList<String> natureza_finan) {
        MOV_CAIXINHA.natureza_finan = natureza_finan;
    }
    public MOV_CAIXINHA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
           carregaComboCentroCusto();
        carregaComboEquipamentos();
        carregaCaixa();
        configuracoesIniciais();
        txtData.setText(dataAtual);
    }
    
        public void configuracoesIniciais() {
        txtData.requestFocus();
        cbNatureza.removeAllItems();
        int totalItemArray = natureza_finan.size();
        int increment = 0;
        if (cbTipo.getSelectedItem().equals("SAIDA")) {

            while (totalItemArray > increment) {
                if (natureza_finan.get(increment + 1).equals("DESPESA")) {
                    cbNatureza.setSelectedItem("");
                    cbNatureza.addItem(natureza_finan.get(increment));
                    increment = increment + 2;
                } else {
                    increment = increment + 2;
                }
            }
        } else {
            while (totalItemArray > increment) {
                if (natureza_finan.get(increment + 1).equals("RECEITA")) {
                    cbNatureza.setSelectedItem("");
                    cbNatureza.addItem(natureza_finan.get(increment));
                    increment = increment + 2;
                } else {
                    increment = increment + 2;
                }
            }
        }

    }

    public void carregaArrayNatureza() {

        Conexao conn = new Conexao();
        Cores cor = new Cores();

        natureza_finan.clear();

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select NATUREZA, TIPO from natureza_finan");
            rs = pst.executeQuery();

            while (rs.next()) {
                natureza_finan.add(rs.getString("NATUREZA"));
                natureza_finan.add(rs.getString("TIPO"));
            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }
    }

    public void carregaArrayCentroCusto() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        centro_custo.clear();
        try {
            PreparedStatement pst;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select codigo,DESCRICAO from centro_custo");
            rs = pst.executeQuery();
            while (rs.next()) {
                centro_custo.add(rs.getString("codigo") + " - " + rs.getString("DESCRICAO"));

            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }

    }

    public void carregaArrayEquipamentos() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();

        centro_custo.clear();

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select prefixo, DESCRICAO from equipamentos order by descricao");
            rs = pst.executeQuery();

            while (rs.next()) {
                equipamentos.add(rs.getString("prefixo")
                        + " - " + rs.getString("DESCRICAO"));
            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }
    }

    public void carregaArrayCaixa() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        caixa.clear();
        try {
            PreparedStatement pst;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select nome_caixa,codigo "
                    + "from conta_finan where natureza = 'ABERTURA DE CAIXA' and status_caixa = 'ABERTO'group by nome_caixa");
            rs = pst.executeQuery();
            while (rs.next()) {
                caixa.add(rs.getString("codigo") + " - " + rs.getString("nome_caixa"));
                caixa.add(rs.getString("codigo"));

            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }

    }

    public void carregaComboCentroCusto() {
        cbCentroCusto.removeAllItems();
        int totalItens = centro_custo.size();
        int increment = 0;
        while (totalItens > increment) {
            cbCentroCusto.addItem(centro_custo.get(increment));
            increment++;
        }
    }

    public void carregaComboEquipamentos() {
        cbEquipamento.removeAllItems();
        cbEquipamento.addItem("Não se aplica");
        int totalItens = equipamentos.size();
        int increment = 0;
        while (totalItens > increment) {
            cbEquipamento.addItem(equipamentos.get(increment));
            increment++;
        }
    }

    public void carregaCaixa() {

        int totalItens = caixa.size();
        int increment = 0;
        while (totalItens > increment) {
            cbApelidoCaixa.addItem(caixa.get(increment));
            increment = increment + 2;
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
        txtData = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObs = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDoc = new javax.swing.JTextField();
        cbTipoDoc = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbApelidoCaixa = new javax.swing.JComboBox<>();
        cbNatureza = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbCentroCusto = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbEquipamento = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtValor = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jLabel1.setText("Data:");

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        txtObs.setColumns(20);
        txtObs.setLineWrap(true);
        txtObs.setRows(5);
        txtObs.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                txtObsAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(txtObs);

        jLabel2.setText("Historico / Observação / Detalhes:");

        jLabel3.setText("Documento");

        txtDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocActionPerformed(evt);
            }
        });

        cbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOTA FISCAL", "RECIBO", "BOLETO", "PROMISSORIA", "OUTROS" }));

        jLabel4.setText("Tipo doc");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Movimentações do Caixinha");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/OK.png"))); // NOI18N
        btnSalvar.setText("Confirmar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Go back.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        jLabel6.setText("Selecione o caixinha:");

        cbApelidoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbApelidoCaixaActionPerformed(evt);
            }
        });
        cbApelidoCaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbApelidoCaixaKeyReleased(evt);
            }
        });

        jLabel7.setText("Natureza da Despesa/Gasto");

        jLabel8.setText("Centro de Custo");

        jLabel9.setText("Equipamento:");

        cbEquipamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não se aplica" }));
        cbEquipamento.setPreferredSize(new java.awt.Dimension(250, 22));

        jLabel10.setText("Valor (R$):");

        txtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        jLabel11.setText("Tipo Mov:");

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SAIDA", "ENTRADA" }));
        cbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoActionPerformed(evt);
            }
        });
        cbTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbTipoKeyReleased(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel12.setText("Texto curto - até 200 caracteres");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(328, 328, 328)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbNatureza, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(cbCentroCusto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel9)
                                    .addComponent(cbEquipamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(cbApelidoCaixa, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addGap(8, 8, 8)
                                                            .addComponent(jLabel3)))))
                                            .addComponent(jLabel8))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbApelidoCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbNatureza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnSair))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        txtDoc.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void txtObsAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_txtObsAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObsAncestorAdded

    private void txtDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocActionPerformed
        txtValor.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        ModelUsuario user = new ModelUsuario();
        Conexao conn = new Conexao();
        ModelUsuario mod = new ModelUsuario();

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into conta_finan(data,codigo,banco,conta,"
                + "agencia,observacao,documento,centro_custo,equipamento,"
                + "pagamento_pendente,nome_caixa,natureza,status_caixa,"
                + "tipo_mov,STATUS_MOV,usuario_emi,usuario_apr)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setString(1, txtData.getText()); //Codigo da Conta
            pst.setInt(2, codCaixa);

            pst.setString(3, "CAIXINHA INTERNO"); //Banco
            pst.setString(4, "CAIXINHA INTERNO"); //Conta
            pst.setString(5, "CAIXINHA INTERNO"); //Agencia
            pst.setString(6, txtObs.getText()); //observacao
            pst.setString(7, txtDoc.getText()); //Documento
            pst.setString(8, (String) cbCentroCusto.getSelectedItem()); //Centro de Custo
            pst.setString(9, (String) cbEquipamento.getSelectedItem()); //Equipamento
            pst.setFloat(10, Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."))); //Valor

            pst.setString(11, (String) cbApelidoCaixa.getSelectedItem()); //Apelido do caixa
            pst.setString(12, (String) cbNatureza.getSelectedItem()); //Natureza Finaceira
            pst.setString(13, "ABERTO"); //Status do caixa
            pst.setString(14, (String) cbTipo.getSelectedItem()); //Tipo da movimentação
            pst.setString(15, "PENDENTE"); //Status do movimento
            pst.setString(16, mod.getUsuarioLogado()); //Usuario Emissao do movimento
            pst.setString(17, ""); //Usuario aprovador

            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);
            dispose();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel finalizar o cadastro " + e, "Mensagem", 2);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void cbApelidoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbApelidoCaixaActionPerformed
        int totalItemArray = caixa.size();
        int increment = 0;
        boolean procura = false;
        while (totalItemArray > increment && procura == false) {
            if (caixa.get(increment).equals(cbApelidoCaixa.getSelectedItem())) {
                codCaixa = Integer.parseInt(caixa.get(increment + 1));
                procura = true;
            } else {
                increment = increment + 2;
            }
        }

    }//GEN-LAST:event_cbApelidoCaixaActionPerformed

    private void cbApelidoCaixaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbApelidoCaixaKeyReleased

        int totalItemArray = caixa.size();
        int increment = 0;
        boolean procura = false;
        while (totalItemArray > increment && procura == false) {
            if (caixa.get(increment + 1) + " - " + caixa.get(increment) == cbApelidoCaixa.getSelectedItem()) {
                codCaixa = Integer.parseInt(caixa.get(increment + 1));
                procura = true;
            } else {
                increment = increment + 2;
            }
        }

    }//GEN-LAST:event_cbApelidoCaixaKeyReleased

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        txtObs.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void cbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoActionPerformed
        cbNatureza.removeAllItems();
        int totalItemArray = natureza_finan.size();
        int increment = 0;
        if (cbTipo.getSelectedItem().equals("SAIDA")) {

            while (totalItemArray > increment) {
                if (natureza_finan.get(increment + 1).equals("DESPESA")) {
                    cbNatureza.setSelectedItem("");
                    cbNatureza.addItem(natureza_finan.get(increment));
                    increment = increment + 2;
                } else {
                    increment = increment + 2;
                }
            }
        } else {
            while (totalItemArray > increment) {
                if (natureza_finan.get(increment + 1).equals("RECEITA")) {
                    cbNatureza.setSelectedItem("");
                    cbNatureza.addItem(natureza_finan.get(increment));
                    increment = increment + 2;
                } else {
                    increment = increment + 2;
                }
            }
        }
    }//GEN-LAST:event_cbTipoActionPerformed

    private void cbTipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoKeyReleased
        cbNatureza.removeAllItems();
        int totalItemArray = natureza_finan.size();
        int increment = 0;
        if (cbTipo.getSelectedItem().equals("SAIDA")) {

            while (totalItemArray > increment) {
                if (natureza_finan.get(increment + 1).equals("DESPESA")) {
                    cbNatureza.setSelectedItem("");
                    cbNatureza.addItem(natureza_finan.get(increment));
                    increment = increment + 2;
                } else {
                    increment = increment + 2;
                }
            }
        } else {
            while (totalItemArray > increment) {
                if (natureza_finan.get(increment + 1).equals("RECEITA")) {
                    cbNatureza.setSelectedItem("");
                    cbNatureza.addItem(natureza_finan.get(increment));
                    increment = increment + 2;
                } else {
                    increment = increment + 2;
                }
            }
        }
    }//GEN-LAST:event_cbTipoKeyReleased

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
            java.util.logging.Logger.getLogger(MOV_CAIXINHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MOV_CAIXINHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MOV_CAIXINHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MOV_CAIXINHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MOV_CAIXINHA dialog = new MOV_CAIXINHA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cbApelidoCaixa;
    private javax.swing.JComboBox<String> cbCentroCusto;
    private javax.swing.JComboBox<String> cbEquipamento;
    private javax.swing.JComboBox<String> cbNatureza;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JComboBox<String> cbTipoDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextArea txtObs;
    private javax.swing.JFormattedTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
