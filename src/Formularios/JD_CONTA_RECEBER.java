/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Controller.ControlContaPagar;
import Controller.ControlContaReceber;
import FormNotificacao.ANP_CPG01;
import FormNotificacao.Confirmacao;
import FormulariosConsultas.ConsultaCliente;
import FormulariosConsultas.ConsultaFornecedor;
import Model.ModelContaPagar;
import Model.ModelContaReceber;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Conexao;
import util.Cores;

/**
 *
 * @author Edicarlos
 */
public class JD_CONTA_RECEBER extends javax.swing.JDialog {

    int titulo;
    String vencimento;
    Conexao conn = new Conexao();
    Cores cor = new Cores();
    String tipoCompra;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    public JD_CONTA_RECEBER(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        carregaComboNatureza();
        txtIntervalo.setEnabled(false);
        txtValorTitulo.setEditable(false);
        txtNumParcela.setText(String.valueOf(1));
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        buscaUltimoTitulo();
        txtTitulo.setBackground(cor.getCorCampoDesabilitado());
        if (txtTitulo.getText().equals("")) {
            txtTitulo.setText(String.valueOf(1));
        }
        txtCodCompra.requestFocus();
        txtTitulo.setBackground(Color.WHITE);
        btnCancelar.setBackground(Color.white);
        txtData.setText(dataAtual);
    }

     public void carregaComboNatureza() {
        cbNatureza.removeAll();
        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select natureza from natureza_finan where tipo = 'RECEITA'");
            rs = pst.executeQuery();
            while (rs.next()) {

                cbNatureza.addItem(rs.getString("natureza"));

            }

            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }
    
    public void addItemTabela() {
        int parcela = Integer.parseInt(txtNumParcela.getText());
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        int totalParcela = Integer.parseInt(txtTotalParcelas.getText());

        model.addRow(new Object[]{
            (tabela.getRowCount() + 1),
            titulo,
            txtValorTitulo.getText(),
            vencimento,
            (tabela.getRowCount() + 1 + " de " + totalParcela),
            txtDocumento.getText(),});
        CorLinhaTabela();
        parcela = parcela + 1;
        txtNumParcela.setText(String.valueOf(parcela));

    }

    public void addItemTabelaIndividual() {
        int parcela = Integer.parseInt(txtNumParcela.getText());
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        int totalParcela = Integer.parseInt(txtTotalParcelas.getText());

        if (parcela > totalParcela) {
            JOptionPane.showMessageDialog(null, "Total de parcelas lançado não pode maior que o especificado no campo PARCELAS",
                    "Mensagem", 1);
        } else {

            model.addRow(new Object[]{
                (tabela.getRowCount() + 1),
                titulo,
                txtValorTitulo.getText(),
                vencimento,
                (txtNumParcela.getText() + " de " + totalParcela),
                txtDocumento.getText(),});
            CorLinhaTabela();
            parcela = parcela + 1;
            txtNumParcela.setText(String.valueOf(parcela));
        }
    }

    public void CorLinhaTabela() {

        Cores cor = new Cores();

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int colum) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);

                // 
                Color c = cor.getCorLinhaImparTabela();
                int indice = Integer.parseInt(table.getValueAt(row, 0).toString());

                if (indice % 2 != 0) {
                    c = c;

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();

                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centralizado.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(centralizado);//Titulo
                    tabela.getColumnModel().getColumn(2).setCellRenderer(direita);//Valor
                    tabela.getColumnModel().getColumn(3).setCellRenderer(centralizado);//Vencimento
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centralizado);//Parcela
                    tabela.getColumnModel().getColumn(5).setCellRenderer(centralizado);//Parcela

                    centralizado.setBackground(c);
                    //  esquerda.setBackground(c);
                    direita.setBackground(c);

                } else {
                    c = cor.getCorLinhaParTabela();

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();

                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centralizado.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(centralizado);//Titulo
                    tabela.getColumnModel().getColumn(2).setCellRenderer(direita);//Valor
                    tabela.getColumnModel().getColumn(3).setCellRenderer(centralizado);//Vencimento
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centralizado);//Parcela
                    tabela.getColumnModel().getColumn(5).setCellRenderer(centralizado);//Parcela

                    centralizado.setBackground(c);
                    //   esquerda.setBackground(c);
                    direita.setBackground(c);

                }

                // 
                return label;
            }

        });

    }

    public void buscaDadosDaVenda() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select codCliente,cliente,valorFinal,"
                    + "condPagamento from venda where codigo = ?");
            pst.setString(1, txtCodCompra.getText());
            rs = pst.executeQuery();

            double valor = 0;

            while (rs.next()) {

                tipoCompra = rs.getString(4);

                txtCodForn.setText(String.valueOf(rs.getInt(1)));
                txtFornecedor.setText(" " + rs.getString(2));
                double valor1 = Double.parseDouble(rs.getString(3).replace(".", "").replaceAll(",", "."));

                valor = valor1 + valor;
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                String totalFormatado = df.format(valor);

                txtValor.setText("R$ " + totalFormatado);
            }
            conn.getConexao().close();
            pst.close();
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }

    }

    public void buscaUltimoTitulo() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select titulo from contas_receber");
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.equals("")) {
                    txtTitulo.setText(String.valueOf(1));
                } else {
                    txtTitulo.setText(String.valueOf(rs.getInt(1) + 1));
                }

            }

            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void calculaValorTitulo() {

        String resp = (String) cbFixaValorParcela.getSelectedItem();
        double valorCompra = Double.parseDouble(txtValor.getText().replace(".", "").replace("R$ ", "").replaceAll(",", "."));
        int totalParcelas = Integer.parseInt(txtTotalParcelas.getText());
        double valorParcela = valorCompra / totalParcelas;
        if (resp.equals("S")) {

            DecimalFormat df = new DecimalFormat("#,##0.0000");
            String valorFormatado = df.format(valorParcela);
            txtValorTitulo.setText(valorFormatado);
            txtValorTitulo.setEnabled(false);
        } else {
            txtValorTitulo.setText("");
            txtValorTitulo.setEnabled(true);
        }
    }

    public void somaTabela() {

        double somaTotal = 0;
        for (int i = 0; i < tabela.getRowCount(); i++) {
            somaTotal += Double.parseDouble(tabela.getValueAt(i, 2).toString().toString().replace(".", "").replaceAll(",", "."));
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(somaTotal);
        lblTotalParcelas.setText("R$  " + totalFormatado);

    }

    public void buscaCliente() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select razaoSocial from cliente where codigo = ?");
            pst.setString(1, txtCodForn.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtFornecedor.setText(rs.getString(1));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
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
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTotalParcelas = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNumParcela = new javax.swing.JTextField();
        btnAdicionarTodas = new javax.swing.JButton();
        btnAdiciarIndividual = new javax.swing.JButton();
        txtVencimento = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        cbFixaDia = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        txtValorTitulo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        cbFixaValorParcela = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtIntervalo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        lblTotalParcelas = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtCodCompra = new javax.swing.JTextField();
        txtCodForn = new javax.swing.JTextField();
        txtFornecedor = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cbNatureza = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        cbTipoDoc = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel4.setBackground(new java.awt.Color(252, 252, 252));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Duplicatas"));

        jLabel5.setText("Parcelas:");

        txtTotalParcelas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalParcelas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtTotalParcelas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotalParcelasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalParcelasFocusLost(evt);
            }
        });
        txtTotalParcelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalParcelasActionPerformed(evt);
            }
        });

        jLabel6.setText("Num da parcela:");

        txtNumParcela.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumParcela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnAdicionarTodas.setText("Gerar Todos");
        btnAdicionarTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarTodasActionPerformed(evt);
            }
        });

        btnAdiciarIndividual.setText("Adicionar Individual");
        btnAdiciarIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdiciarIndividualActionPerformed(evt);
            }
        });

        txtVencimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        try {
            txtVencimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtVencimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtVencimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVencimentoActionPerformed(evt);
            }
        });

        jLabel19.setText("Vencimento:");

        cbFixaDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "N" }));
        cbFixaDia.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                cbFixaDiaAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        cbFixaDia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbFixaDiaFocusLost(evt);
            }
        });
        cbFixaDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFixaDiaActionPerformed(evt);
            }
        });
        cbFixaDia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbFixaDiaKeyReleased(evt);
            }
        });

        jLabel8.setText("Fixa dia?");

        tabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indice", "Titulo", "Valor", "Vencimento", "Parcela", "Nº Doc"
            }
        ));
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(1);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabela.getColumnModel().getColumn(0).setMaxWidth(1);
        }

        jLabel10.setText("Valor da Duplicata:");

        txtValorTitulo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtValorTitulo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtValorTitulo.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel11.setText("Valor Fixo?");

        cbFixaValorParcela.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "N" }));
        cbFixaValorParcela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbFixaValorParcelaFocusLost(evt);
            }
        });
        cbFixaValorParcela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFixaValorParcelaActionPerformed(evt);
            }
        });
        cbFixaValorParcela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbFixaValorParcelaKeyReleased(evt);
            }
        });

        jLabel12.setText("Int. Ent Parcelas");

        txtIntervalo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIntervalo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtIntervalo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIntervaloActionPerformed(evt);
            }
        });

        jLabel18.setText("Nº Documento:");

        txtDocumento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocumento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtDocumento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotalParcelas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(cbFixaDia, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(cbFixaValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIntervalo)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(93, 93, 93))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnAdiciarIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdicionarTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addComponent(txtValorTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtNumParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDocumento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTotalParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbFixaDia))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cbFixaValorParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtIntervalo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValorTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNumParcela, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdiciarIndividual)
                            .addComponent(btnAdicionarTodas)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(94, 110, 110));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("  Contas a Receber - Incluir");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        btnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnConfirmar.setOpaque(true);
        btnConfirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnConfirmarFocusGained(evt);
            }
        });
        btnConfirmar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnConfirmarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnConfirmarMouseReleased(evt);
            }
        });
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
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

        jLabel14.setText("SOMA DAS PARCELAS =>");

        lblTotalParcelas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTotalParcelas.setForeground(new java.awt.Color(51, 102, 255));
        lblTotalParcelas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotalParcelas.setText("R$ 0,00");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vincular Venda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 51, 204))); // NOI18N

        txtCodCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCompraFocusLost(evt);
            }
        });
        txtCodCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCompraActionPerformed(evt);
            }
        });

        txtCodForn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodForn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodForn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFornFocusLost(evt);
            }
        });
        txtCodForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFornActionPerformed(evt);
            }
        });
        txtCodForn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodFornKeyPressed(evt);
            }
        });

        txtFornecedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFornecedor.setEnabled(false);

        txtValor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtValor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFocusLost(evt);
            }
        });
        txtValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorActionPerformed(evt);
            }
        });

        jLabel2.setText("Cod Venda:");

        jLabel3.setText("Cod Cliente:");

        jLabel4.setText("Nome do Cliente:");

        jLabel7.setText("Valor:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtCodCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodForn, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodForn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel15.setText("Data:");

        jLabel16.setText("Natureza Financeira:");

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtData.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel17.setText("Tipo do Titulo:");

        cbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BOLETO BANCARIO", "FATURA AGUA/LUZ", "FATURA CARTAO", "PROMISSORIA", "VALE", "CONTRATO", "OUTROS TITULOS", " ", " ", " ", " " }));

        jLabel1.setText("Titulo:");

        txtTitulo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTitulo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtTitulo.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(88, 88, 88)
                .addComponent(lblTotalParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(cbNatureza, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(cbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbNatureza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTotalParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void txtVencimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVencimentoActionPerformed
        btnAdicionarTodas.requestFocus();


    }//GEN-LAST:event_txtVencimentoActionPerformed

    private void txtCodCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCompraFocusLost
        if (txtCodCompra.getText().equals("") || txtCodCompra.getText().equals("0")) {
            txtCodForn.requestFocus();
        } else {

            buscaDadosDaVenda();
            if (tipoCompra.equals("A VISTA")) {

                ANP_CPG01 anp = new ANP_CPG01(new javax.swing.JFrame(), true);
                anp.setVisible(true);
                tipoCompra = "";
                txtCodCompra.setText("");
                txtCodForn.setText("");
                txtFornecedor.setText("");
                txtValor.setText("");
                txtCodCompra.requestFocus();

            } else {
                txtTotalParcelas.requestFocus();
            }
        }

    }//GEN-LAST:event_txtCodCompraFocusLost

    private void cbFixaValorParcelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbFixaValorParcelaFocusLost
        calculaValorTitulo();
        txtValorTitulo.setBackground(cor.getCorCampoDesabilitado());
    }//GEN-LAST:event_cbFixaValorParcelaFocusLost

    private void btnAdicionarTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarTodasActionPerformed
        int parcela = Integer.parseInt(txtNumParcela.getText());
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        int totalParcela = Integer.parseInt(txtTotalParcelas.getText());

        if (parcela > totalParcela) {
            JOptionPane.showMessageDialog(null, "Total de parcelas lançado não pode maior que o especificado no campo PARCELAS",
                    "Mensagem", 1);
        } else {

            int totalParcelas = Integer.parseInt(txtTotalParcelas.getText());
            int contParcela = 1;
            titulo = Integer.parseInt(txtTitulo.getText());
            if (cbFixaDia.getSelectedItem() == "S") {
                vencimento = txtVencimento.getText();

                while (totalParcelas >= contParcela) {

                    try {

                        addItemTabela();
                        incrementaMes();
                        contParcela = contParcela + 1;
                        titulo = titulo + 1;
                    } catch (ParseException ex) {
                        Logger.getLogger(JD_CONTA_RECEBER.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } else {
                vencimento = txtVencimento.getText();
                while (totalParcelas >= contParcela) {
                    try {

                        addItemTabela();
                        incrementaDia();
                        contParcela = contParcela + 1;
                        titulo = titulo + 1;
                    } catch (ParseException ex) {
                        Logger.getLogger(JD_CONTA_RECEBER.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        somaTabela();
    }//GEN-LAST:event_btnAdicionarTodasActionPerformed

    public void incrementaDia() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        java.util.Date atual = sdf.parse(vencimento);
        // java.util.Date atual = sdf.parse(txtVencimento.getText());

        cal.setTime(atual);

        cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(txtIntervalo.getText()));

        atual = cal.getTime();

        vencimento = sdf.format(atual);
        //txtVencimento.setText(String.valueOf(sdf.format(atual)));

    }

    public void incrementaMes() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();

        java.util.Date atual = sdf.parse(vencimento);
        // java.util.Date atual = sdf.parse(txtVencimento.getText());

        cal.setTime(atual);

        cal.add(Calendar.MONTH, 1);

        atual = cal.getTime();

        vencimento = sdf.format(atual);
        //txtVencimento.setText(String.valueOf(sdf.format(atual)));

    }
    private void btnAdiciarIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdiciarIndividualActionPerformed

        titulo = Integer.parseInt(txtTitulo.getText());
        vencimento = txtVencimento.getText();
        addItemTabelaIndividual();
        titulo = titulo + 1;
        txtTitulo.setText(String.valueOf(titulo));
        txtVencimento.requestFocus();
        somaTabela();
    }//GEN-LAST:event_btnAdiciarIndividualActionPerformed

    private void txtCodCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCompraActionPerformed

        if (txtCodCompra.getText().equals("") || txtCodCompra.getText().equals("0")) {
            txtCodForn.requestFocus();
        } else {

            buscaDadosDaVenda();
            if (tipoCompra.equals("A VISTA")) {

                ANP_CPG01 anp = new ANP_CPG01(new javax.swing.JFrame(), true);
                anp.setVisible(true);
                tipoCompra = "";
                txtCodCompra.setText("");
                txtCodForn.setText("");
                txtFornecedor.setText("");
                txtValor.setText("");
                txtCodCompra.requestFocus();

            } else {
                txtTotalParcelas.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodCompraActionPerformed

    private void txtTotalParcelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalParcelasActionPerformed
        cbFixaDia.requestFocus();
        calculaValorTitulo();

    }//GEN-LAST:event_txtTotalParcelasActionPerformed

    private void txtTotalParcelasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalParcelasFocusLost
        if (txtValor.getText().equals("")) {

        } else {
            calculaValorTitulo();
        }

    }//GEN-LAST:event_txtTotalParcelasFocusLost

    private void cbFixaDiaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbFixaDiaAncestorAdded
        if (cbFixaDia.getSelectedItem() == "N") {
            txtIntervalo.setEnabled(true);
        } else {
            txtIntervalo.setEnabled(false);
            txtIntervalo.setBackground(cor.getCorCampoDesabilitado());
        }
    }//GEN-LAST:event_cbFixaDiaAncestorAdded

    private void cbFixaDiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbFixaDiaFocusLost
        if (cbFixaDia.getSelectedItem() == "N") {
            txtIntervalo.setEnabled(true);
            txtIntervalo.requestFocus();
        } else {
            txtIntervalo.setEnabled(false);
            txtIntervalo.setBackground(cor.getCorCampoDesabilitado());
        }
    }//GEN-LAST:event_cbFixaDiaFocusLost

    private void txtIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIntervaloActionPerformed
        txtVencimento.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_txtIntervaloActionPerformed

    private void btnConfirmarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusGained
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarFocusGained

    private void btnConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseEntered
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarMouseEntered

    private void btnConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseExited
        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarMouseExited

    private void btnConfirmarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMousePressed

        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarMousePressed

    private void btnConfirmarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseReleased
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));

    }//GEN-LAST:event_btnConfirmarMouseReleased

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        tabela.clearSelection();
        Model.ModelContaReceber mod = new ModelContaReceber();
        ControlContaReceber control = new ControlContaReceber();
        try {
            int totalLinha = tabela.getRowCount();
            int lin = 1;

            while (totalLinha >= lin) {

                String venc = tabela.getValueAt(lin - 1, 3).toString(); //Vencimento
                String vencimentoConvert = venc.substring(6, 10) + venc.substring(3, 5) + venc.substring(0, 2);
                String valor = tabela.getValueAt(lin - 1, 2).toString();
                float valorTitulo = Float.parseFloat(String.valueOf(valor).replace(".", "").replaceAll(",", "."));

                mod.setTitulo(Integer.parseInt(tabela.getValueAt(lin - 1, 1).toString())); //Titulo
                mod.setCodVenda(Integer.parseInt(txtCodCompra.getText()));//Cod Compra
                mod.setCodCliente(Integer.parseInt(txtCodForn.getText()));//Cod Fornecedor
                mod.setCliente(txtFornecedor.getText());//Nome Fornecedor
                mod.setVencimento(tabela.getValueAt(lin - 1, 3).toString()); //Vencimento
                mod.setValorTitulo(valorTitulo); //valor
                mod.setParcela(tabela.getValueAt(lin - 1, 4).toString()); //Parcela
                mod.setStatus("EM ABERTO");//Status
                mod.setVencimento_base(vencimentoConvert);
                mod.setNatureza((String) cbNatureza.getSelectedItem());
                mod.setTitulo_origem(tabela.getValueAt(lin - 1, 5).toString()); //Documento
                mod.setTipo_titulo((String) cbTipoDoc.getSelectedItem());
                lin = lin + 1;
                control.inserir(mod);
            }
            Confirmacao ok = new Confirmacao(new javax.swing.JFrame(), true);
            ok.setVisible(true);
            dispose();
        } catch (Exception ex) {
            Logger.getLogger(JD_CONTA_RECEBER.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbFixaDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFixaDiaActionPerformed
        cbFixaValorParcela.requestFocus();
    }//GEN-LAST:event_cbFixaDiaActionPerformed

    private void cbFixaValorParcelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFixaValorParcelaActionPerformed
        txtIntervalo.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cbFixaValorParcelaActionPerformed

    private void cbFixaDiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFixaDiaKeyReleased
        cbFixaValorParcela.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_cbFixaDiaKeyReleased

    private void cbFixaValorParcelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFixaValorParcelaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFixaValorParcelaKeyReleased

    private void txtTotalParcelasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalParcelasFocusGained


    }//GEN-LAST:event_txtTotalParcelasFocusGained

    private void txtValorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFocusLost
        if (txtValor.getText().equals("")) {
            txtDocumento.requestFocus();
        } else {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
            String valorFormat = df.format(valor);
            txtValor.setText(valorFormat);
            txtDocumento.requestFocus();
        }
    }//GEN-LAST:event_txtValorFocusLost

    private void txtCodFornKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodFornKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaCliente consult = new ConsultaCliente(null, true);
            consult.carregaTabela();
            consult.setVisible(true);
            txtValor.requestFocus();
            String codigo = consult.getCodigo();
            String nome = consult.getNome();
            txtCodForn.setText(codigo);
            txtFornecedor.setText(nome);

        } else {

        }

    }//GEN-LAST:event_txtCodFornKeyPressed

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        if (txtValor.getText().equals("")) {
            txtDocumento.requestFocus();
        } else {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            float valor = Float.parseFloat(txtValor.getText().replace(".", "").replaceAll(",", "."));
            String valorFormat = df.format(valor);
            txtValor.setText(valorFormat);
            txtDocumento.requestFocus();
        }

    }//GEN-LAST:event_txtValorActionPerformed

    private void txtCodFornFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFornFocusLost
        if (txtCodForn.getText().equals("")) {
            txtValor.requestFocus();
        } else {
            buscaCliente();
            txtValor.requestFocus();
        }

    }//GEN-LAST:event_txtCodFornFocusLost

    private void txtCodFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFornActionPerformed
        if (txtCodForn.getText().equals("")) {
            txtValor.requestFocus();
        } else {
            buscaCliente();
            txtValor.requestFocus();
        }
    }//GEN-LAST:event_txtCodFornActionPerformed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        txtTotalParcelas.requestFocus();
    }//GEN-LAST:event_txtDocumentoActionPerformed

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
            java.util.logging.Logger.getLogger(JD_CONTA_RECEBER.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_CONTA_RECEBER.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_CONTA_RECEBER.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_CONTA_RECEBER.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_CONTA_RECEBER dialog = new JD_CONTA_RECEBER(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdiciarIndividual;
    private javax.swing.JButton btnAdicionarTodas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cbFixaDia;
    private javax.swing.JComboBox<String> cbFixaValorParcela;
    private javax.swing.JComboBox<String> cbNatureza;
    private javax.swing.JComboBox<String> cbTipoDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalParcelas;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCodCompra;
    private javax.swing.JTextField txtCodForn;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtFornecedor;
    private javax.swing.JTextField txtIntervalo;
    private javax.swing.JTextField txtNumParcela;
    private javax.swing.JTextField txtTitulo;
    private javax.swing.JTextField txtTotalParcelas;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtValorTitulo;
    private javax.swing.JFormattedTextField txtVencimento;
    // End of variables declaration//GEN-END:variables
}
