/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Controller.ControlCaixaPDV;
import FormNotificacao.ANP_APVD01;
import Model.ModelOperacaoCaixaPDV;
import Model.ModelUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Conexao;
import util.Cores;

/**
 *
 * @author Edicarlos
 */
public class DETALHE_CAIXA extends javax.swing.JDialog {

    String aprovador;
    ModelUsuario user = new ModelUsuario();
    DateTimeFormatter data2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String dataBase = data2.format(LocalDateTime.now());

    public DETALHE_CAIXA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));
        initComponents();
        txtValor.requestFocus();
        txtValor.setText("R$ 0,00");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "|" + " HH:mm");

        lblOperador.setText(user.getUsuarioLogado());
        Cores cor = new Cores();
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 10));

        somaTabela();
    }

    public void pegaCaixa(Caixa_PDV caixa) {
        lblCodCaixa.setText(String.valueOf(caixa.getCaixaSelecionado()));
        ModelUsuario user = new ModelUsuario();
        lblOperador.setText(user.getUsuarioLogado());

    }

    public void carregaSaldoInicial() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select sum(saldo_inicial) as SaldoIn,aprovador,data from caixa_pdv where codigo =? and operacao =?");
            pst.setInt(1, Integer.parseInt(lblCodCaixa.getText()));
            pst.setString(2, "ABERTURA DE CAIXA");
            //Integer.parseInt(lblCodCaixa.getText())
            rs = pst.executeQuery();

            while (rs.next()) {

                DecimalFormat df = new DecimalFormat("#,##0.00");
                Double saldo = rs.getDouble("SaldoIn");

                //Valores formatados
                String saldo_formatado = df.format(saldo);
                txtValor.setText(" " + "R$ " + saldo_formatado);
                lblAprovador.setText(" " + rs.getString("aprovador"));
                lblData.setText(rs.getString("data"));

            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Caiu aqui " + e);
        }

        //AlinharColunas();
    }

    public void carregaDados() {

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;

            pst = conn.getConexao().prepareStatement("select data,operacao,entradas,saidas,aprovador,status from caixa_pdv where codigo =? and operacao <> 'ABERTURA DE CAIXA'"
                    + "order by id desc");
            pst.setInt(1, Integer.parseInt(lblCodCaixa.getText()));
            //Integer.parseInt(lblCodCaixa.getText())
            rs = pst.executeQuery();

            while (rs.next()) {

                DecimalFormat df = new DecimalFormat("#,##0.00");

                //Valor normias
                Double entrada = rs.getDouble("entradas");
                Double saida = rs.getDouble("saidas");
                //Valores formatados
                String entradas_formatado = df.format(entrada);
                String saldas_formatado = df.format(saida);
                lblStatus.setText(" " + rs.getString("status"));
                modelo.addRow(new Object[]{
                    tabela.getRowCount() + 1,
                    rs.getString("data"),//Data
                    rs.getString("operacao"),//Operacao
                    entradas_formatado,//Valor Entrada
                    saldas_formatado,//Valor Saida
                    rs.getString("aprovador"),//Aprovador
                }
                );

            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Caiu aqui " + e);
        }

        if (lblStatus.getText().equals(" ABERTO")) {
            lblStatus.setForeground(new Color(0, 153, 51));
        } else {
            lblStatus.setForeground(Color.red);

        }

    }

    public void somaTabela() {

        double saldoIn = Double.parseDouble(txtValor.getText().replace("R$ ", "").replace(".", "").replaceAll(",", "."));

        double entradas = 0;
        double saidas = 0;

        for (int i = 0; i < tabela.getRowCount(); i++) {
            entradas += Double.parseDouble(tabela.getValueAt(i, 3).toString().toString().replace(".", "").replaceAll(",", "."));
        }

        for (int i = 0; i < tabela.getRowCount(); i++) {
            saidas += Double.parseDouble(tabela.getValueAt(i, 4).toString().toString().replace(".", "").replaceAll(",", "."));
        }

        DecimalFormat df = new DecimalFormat("#,##0.00");
        double total = saldoIn + entradas - saidas;

        String totalFormatado = df.format(total);

        txtSaldoAtual.setText("R$  " + totalFormatado);

        CorLinhaTabela();
    }

    public void CorLinhaTabela() {
        Cores cor = new Cores();

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int colum) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);

                Color c = cor.getCorLinhaImparTabela();
                Color d = cor.getCorFundoLinhaDeletada();
                int indice = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (indice % 2 != 0) {
                    c = c;

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centro.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);//Data
                    tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);//Operacao
                    tabela.getColumnModel().getColumn(3).setCellRenderer(direita);//Valor Entrada
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);//Valor Siada
                    tabela.getColumnModel().getColumn(5).setCellRenderer(esquerda);//Aprovador

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;
                } else {

                    c = cor.getCorLinhaParTabela();

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centro.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);//Data
                    tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);//Operacao
                    tabela.getColumnModel().getColumn(3).setCellRenderer(direita);//Valor Entrada
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);//Valor Siada
                    tabela.getColumnModel().getColumn(5).setCellRenderer(esquerda);//Aprovador

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;

                }

                return label;
            }

        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCodCaixa = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblOperador = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtSaldoAtual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblAprovador = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jPanel1.setPreferredSize(new java.awt.Dimension(300, 300));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Caixa:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("Operador:");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Valor Inicial");

        txtValor.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtValor.setForeground(new java.awt.Color(51, 51, 51));
        txtValor.setBorder(null);
        txtValor.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtValor.setEnabled(false);
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtValorMouseClicked(evt);
            }
        });
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(38, 85, 100));
        jLabel1.setText("Historico de Movimentações");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("x");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        lblCodCaixa.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblCodCaixa.setForeground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Data Abertura:");

        lblData.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblData.setForeground(new java.awt.Color(51, 51, 51));
        lblData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblData.setText("Caixa:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        lblOperador.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblOperador.setForeground(new java.awt.Color(51, 51, 51));
        lblOperador.setText("Operador");

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "indice", "Data", "Operação", "Valor Entrada", "Valor Saida", "Aprovador"
            }
        ));
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        jScrollPane3.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(3);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(0).setMaxWidth(3);
            tabela.getColumnModel().getColumn(5).setMinWidth(210);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(210);
            tabela.getColumnModel().getColumn(5).setMaxWidth(210);
        }

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Saldo Atual");

        txtSaldoAtual.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtSaldoAtual.setForeground(new java.awt.Color(51, 51, 51));
        txtSaldoAtual.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSaldoAtual.setBorder(null);
        txtSaldoAtual.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtSaldoAtual.setEnabled(false);
        txtSaldoAtual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSaldoAtualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSaldoAtualFocusLost(evt);
            }
        });
        txtSaldoAtual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSaldoAtualMouseClicked(evt);
            }
        });
        txtSaldoAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoAtualActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Aprovador abertura:");

        lblAprovador.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lblAprovador.setForeground(new java.awt.Color(51, 51, 51));
        lblAprovador.setText("Aprovador");
        lblAprovador.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Status atual do caixa");

        lblStatus.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(51, 51, 51));
        lblStatus.setText("STATUS");
        lblStatus.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSaldoAtual)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtValor)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblData, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(lblAprovador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOperador))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodCaixa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblData)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAprovador, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSaldoAtual)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStatus)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtValorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusGained
        txtValor.selectAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorFocusGained

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost

    }//GEN-LAST:event_txtValorFocusLost

    private void txtValorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtValorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorMouseClicked

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed


    }//GEN-LAST:event_txtValorActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txtSaldoAtualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaldoAtualFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualFocusGained

    private void txtSaldoAtualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaldoAtualFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualFocusLost

    private void txtSaldoAtualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSaldoAtualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualMouseClicked

    private void txtSaldoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualActionPerformed

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
            java.util.logging.Logger.getLogger(Sangria_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sangria_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sangria_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sangria_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DETALHE_CAIXA dialog = new DETALHE_CAIXA(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAprovador;
    private javax.swing.JLabel lblCodCaixa;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblOperador;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtSaldoAtual;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
