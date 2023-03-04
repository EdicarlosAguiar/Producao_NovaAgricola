/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Model.ModelProduto;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class JD_MOV_ESTOQUE extends javax.swing.JDialog {

    Cores cor = new Cores();
    utilitario util = new utilitario();
    double saldoAtual;
    double custoMedioMacro;

    /**
     * Creates new form JD_MOV_ESTOQUE
     */
    public JD_MOV_ESTOQUE(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        configIniciais();
        txtCodigo.requestFocus();
        txtSaldoAnterior.setText("0,00");
        txtValorAnterior.setText("0,00");
        txtCustoMedio.setText("0,00");

    }

    public void configIniciais() {

        Cores cor = new Cores();
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabeçalho
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabeçalho
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);
        tabela.setVisible(false);
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
                    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centro.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(9).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(10).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(11).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(12).setCellRenderer(esquerda);

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

                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(9).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(10).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(11).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(12).setCellRenderer(esquerda);

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;

                }
                return label;
            }

        });

    }

    public void saldoAnterior() {

        String data = txtDataInicio.getText();
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);
        String dataInteiro = ano + mes + dia;

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select qFim from fechamento_estoque where codProduto=?");
            String parametro1 = txtCodigo.getText();
            pst.setString(1, parametro1);
            rs = pst.executeQuery();

            while (rs.next()) {

                double entradas = rs.getDouble("qFim");
                /*   double saidas = rs.getDouble("saidas");
                    double entradaValor = rs.getDouble("valorEntrada");
                    double saldo = entradas - saidas;
                    double saldoValor = entradaValor;*/

                DecimalFormat df = new DecimalFormat("#,###0.00");
                String saldoAnteriorFormatado = df.format(entradas);
                /* String saldoValorFormatado = df.format(saldoValor);*/

                txtSaldoAnterior.setText(saldoAnteriorFormatado);
                /*   txtValorAnterior.setText(saldoValorFormatado);*/

            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
    }

    public void carregaCalendario() {

        Conexao conn2 = new Conexao();
        PreparedStatement pst2 = null;
        ResultSet rs2;
        try {
            pst2 = conn2.getConexao().prepareStatement("select * from calendario");
            rs2 = pst2.executeQuery();
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

            modelo.setNumRows(0);
            while (rs2.next()) {

                {
                    modelo.addRow(new Object[]{
                        tabela.getRowCount(),
                        rs2.getString("data"),});//Status

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(JD_MOV_ESTOQUE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultaMov() {

        String dataIn = txtDataInicio.getText();
        String dataFim = txtDataFim.getText();

        String diaIn = dataIn.substring(0, 2);
        String mesIn = dataIn.substring(3, 5);
        String anoIn = dataIn.substring(6, 10);

        String diaFim = dataFim.substring(0, 2);
        String mesFim = dataFim.substring(3, 5);
        String anoFim = dataFim.substring(6, 10);

        String dataInteiroIn = anoIn + mesIn + diaIn;
        String dataInteiroFim = anoFim + mesFim + diaFim;

        //   JOptionPane.showMessageDialog(null, dataInteiroIn+ " | "+dataInteiroFim);
        try {

            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            modelo.setRowCount(0);
            pst = conn.getConexao().prepareStatement("select * from kardex where codProduto =? "
                    + "and data_do_movimento >=? and data_do_movimento <=?");

            String parametro1 = txtCodigo.getText();
            String parametro2 = dataInteiroIn;
            String parametro3 = dataInteiroFim;

            pst.setString(1, parametro1);
            pst.setString(2, parametro2);
            pst.setString(3, parametro3);

            rs = pst.executeQuery();

            while (rs.next()) {

                double entradas = rs.getDouble(7);
                double saidas = rs.getDouble(9);
                double entradasValor = rs.getDouble(8);
                double saidasValor = rs.getDouble(10);
                double custoMedio = entradasValor / entradas;

                DecimalFormat df = new DecimalFormat("#,###0.00");
                String entradasFormatado = df.format(entradas);
                String saidasFormatado = df.format(saidas);
                String entradaValorFormatado = df.format(entradasValor);
                String saidaValorFormatado = df.format(saidasValor);
                String custoMedioFortado = df.format(custoMedio);

                modelo.addRow(new Object[]{
                    tabela.getRowCount() + 1,
                    rs.getString(2),//Data
                    rs.getString(4),//Codigo
                    rs.getString(5),//Produto
                    entradasFormatado,//Entrada Quantidade
                    entradaValorFormatado,//Entrada Valor
                    "",//Custo Medio
                    saidasFormatado,//Saida Quantidade
                    "",//Saida Valor
                    "",//Saldo Quantidade
                    "",//Saldo Valor
                    rs.getString(15),//Doc Origem
                    rs.getString(16),//Tipo Mov
                }
                );

            }

            conn.getConexao().close();

        } catch (Exception e) {
        }

        somaTabela();
        calculaSaldo();

    }

    public void calculaSaldo() {

        int linha = tabela.getRowCount();
        int linIndice = 1;
        int linhaCount = 1;
        double saldoAnterior = Double.parseDouble(txtSaldoAnterior.getText().replace(".", "").replaceAll(",", "."));

        while (linha >= linhaCount) {

            double entradas = Double.parseDouble(tabela.getValueAt(linIndice - 1, 4).toString().replace(".", "").replaceAll(",", "."));
            double saidas = Double.parseDouble(tabela.getValueAt(linIndice - 1, 7).toString().replace(".", "").replaceAll(",", "."));
            saldoAtual = saldoAnterior + entradas - saidas;

            double saidaValor = saidas * custoMedioMacro;
            double saldoValor = saldoAtual * custoMedioMacro;

            DecimalFormat df = new DecimalFormat("#,###0.00");

            String saldoFormatado = df.format(saldoAtual);
            String custoMedioFormatado = df.format(custoMedioMacro);
            String saidaValorFormatado = df.format(saidaValor);
            String saldoValorFormatado = df.format(saldoValor);

            tabela.setValueAt(custoMedioFormatado, linIndice - 1, 6);//Custo Medio
            tabela.setValueAt(saidaValorFormatado, linIndice - 1, 8);//Saida Valor
            tabela.setValueAt(saldoFormatado, linIndice - 1, 9); //Saldo Quantidade
            tabela.setValueAt(saldoValorFormatado, linIndice - 1, 10);//Saldo Valor

            saldoAnterior = saldoAtual;
            linIndice = linIndice + 1;
            linhaCount = linhaCount + 1;
        }

    }

    public void somaTabela() {

        double totalEntradas = 0;
        double valorEntradas = 0;
        double totalSaidas = 0;

        for (int i = 0; i < tabela.getRowCount(); i++) {
            totalEntradas += Double.parseDouble(tabela.getValueAt(i, 4).toString().toString().replace(".", "").replaceAll(",", "."));
            totalSaidas += Double.parseDouble(tabela.getValueAt(i, 7).toString().toString().replace(".", "").replaceAll(",", "."));
            valorEntradas += Double.parseDouble(tabela.getValueAt(i, 5).toString().toString().replace(".", "").replaceAll(",", "."));

        }

        valorEntradas = valorEntradas + Double.parseDouble(txtValorAnterior.getText().replace(".", " ").replaceAll(",", "."));
        double entradaTotais = totalEntradas + Double.parseDouble(txtSaldoAnterior.getText().replace(".", " ").replaceAll(",", "."));
        double saldoAnterior = Double.parseDouble(txtSaldoAnterior.getText().replace(".", " ").replaceAll(",", "."));
        double saldo = totalEntradas + saldoAnterior - totalSaidas;

        custoMedioMacro = valorEntradas / entradaTotais;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String entradasFormatado = df.format(totalEntradas);
        String saidasFormatado = df.format(totalSaidas);
        String saldoFormatado = df.format(saldo);
        String custoMedioFormatado = df.format(custoMedioMacro);

        txtTotalEntradas.setText(entradasFormatado);
        txtTotalSaidas.setText(saidasFormatado);
        txtSaldoAtual.setText(saldoFormatado);
        txtCustoMedio.setText(custoMedioFormatado);
    }

    public void pegaCodigo(ModelProduto mod) {

        txtCodigo.setText(String.valueOf(mod.getProdutoPesquisado()));

    }

    public void buscaProduto() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select nome from tb_produtos where codigo = ?");
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtNome.setText(rs.getString("nome"));
                txtDataInicio.requestFocus();

            }
            conn.getConexao().close();
            pst.close();
            rs.close();

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
        jPanel2 = new javax.swing.JPanel();
        btnConsulta = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtDataInicio = new javax.swing.JFormattedTextField();
        txtDataFim = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotalSaidas = new javax.swing.JTextField();
        txtTotalEntradas = new javax.swing.JTextField();
        txtSaldoAtual = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtSaldoAnterior = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCustoMedio = new javax.swing.JTextField();
        txtValorAnterior = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Parametros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N

        btnConsulta.setText("Gerar Consulta");
        btnConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaActionPerformed(evt);
            }
        });

        jLabel2.setText("Codigo:");

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
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        txtNome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNome.setEnabled(false);

        try {
            txtDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataInicioFocusLost(evt);
            }
        });
        txtDataInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataInicioActionPerformed(evt);
            }
        });

        try {
            txtDataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataFim.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataFim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataFimFocusLost(evt);
            }
        });
        txtDataFim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataFimActionPerformed(evt);
            }
        });

        jLabel3.setText("Produto:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Data Inicio:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Data Fim:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDataInicio)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(txtDataFim))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConsulta)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConsulta)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), "Resumo da consulta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Total Entradas(+):");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Total de Saidas (-):");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Saldo Atual(=):");

        txtTotalSaidas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalSaidas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalSaidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalSaidasActionPerformed(evt);
            }
        });

        txtTotalEntradas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTotalEntradas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalEntradasActionPerformed(evt);
            }
        });

        txtSaldoAtual.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSaldoAtual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoAtualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotalEntradas, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotalSaidas, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(51, 51, 51)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSaldoAtual, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(233, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalSaidas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalEntradas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(94, 110, 110));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(" Analise das Movimentações de Estoque");

        btnCancelar.setBackground(new java.awt.Color(94, 110, 110));
        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(204, 204, 204));
        btnCancelar.setText("X");
        btnCancelar.setAlignmentY(0.8F);
        btnCancelar.setBorder(null);
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Saldo Anterior a data da consulta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 51, 102))); // NOI18N

        jLabel6.setText("Saldo Anteior:");

        txtSaldoAnterior.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtSaldoAnterior.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel10.setText("Custo Medio");

        txtCustoMedio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCustoMedio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtValorAnterior.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtValorAnterior.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel11.setText("Saldo Valor");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtSaldoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCustoMedio, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 41, Short.MAX_VALUE))
                    .addComponent(txtValorAnterior))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCustoMedio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSaldoAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indice", "Data", "Codigo", "Produto", "Q_Entrada", "V_Entrada", "CM", "Q_Saida", "V_Saida", "Q_Saldo", "V_Saldo", "Doc", "TM"
            }
        ));
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        tabela.setRowHeight(25);
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(1);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabela.getColumnModel().getColumn(0).setMaxWidth(1);
            tabela.getColumnModel().getColumn(1).setMinWidth(100);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setMaxWidth(100);
            tabela.getColumnModel().getColumn(2).setMinWidth(0);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(2).setMaxWidth(0);
            tabela.getColumnModel().getColumn(3).setMinWidth(0);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaActionPerformed
        saldoAnterior();
        carregaCalendario();
        tabela.setVisible(true);
        //  consultaMov();
        //  CorLinhaTabela();

    }//GEN-LAST:event_btnConsultaActionPerformed

    private void txtTotalSaidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalSaidasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalSaidasActionPerformed

    private void txtTotalEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalEntradasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalEntradasActionPerformed

    private void txtSaldoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        txtDataInicio.requestFocus();
        buscaProduto();
// TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtDataInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataInicioActionPerformed
        txtDataFim.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataInicioActionPerformed

    private void txtDataFimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataFimActionPerformed
        btnConsulta.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataFimActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

        } else {

        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        buscaProduto();
        txtDataInicio.requestFocus();// TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtDataInicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataInicioFocusLost
        txtDataFim.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataInicioFocusLost

    private void txtDataFimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFimFocusLost
        btnConsulta.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_txtDataFimFocusLost

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new java.awt.Color(114, 135, 135));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new java.awt.Color(94, 110, 110));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JD_MOV_ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_MOV_ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_MOV_ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_MOV_ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_MOV_ESTOQUE dialog = new JD_MOV_ESTOQUE(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConsulta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCustoMedio;
    private javax.swing.JFormattedTextField txtDataFim;
    private javax.swing.JFormattedTextField txtDataInicio;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSaldoAnterior;
    private javax.swing.JTextField txtSaldoAtual;
    private javax.swing.JTextField txtTotalEntradas;
    private javax.swing.JTextField txtTotalSaidas;
    private javax.swing.JTextField txtValorAnterior;
    // End of variables declaration//GEN-END:variables
}
