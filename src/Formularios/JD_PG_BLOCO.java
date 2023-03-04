/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import FormNotificacao.ConfirmaPagamento;
import FormNotificacao.Confirmacao;
import FormNotificacao.pagamentoNaoAutorizado;
import FormulariosConsultas.ConsultaFornecedor;
import Model.ModelUsuario;
import com.sun.prism.Mesh;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.AguardeGerandoRelatorio;
import util.Conexao;
import util.Cores;

/**
 *
 * @author Desktop
 */
public class JD_PG_BLOCO extends javax.swing.JDialog {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    Conexao conn = new Conexao();
    int processamento;
    int totalTitulosSelecionados;

    public JD_PG_BLOCO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        txtDataPagamento.setText(dataAtual);

        Cores cor = new Cores();
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

    }

    public void AlinharColunas() {
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        //   tabela.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(2).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
        tabela.getColumnModel().getColumn(4).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(7).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(8).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(9).setCellRenderer(centralizado);

    }

    public void carregaTabela() {

        Cores cor = new Cores();
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        String diaDataIni = txtDataIni.getText().substring(0, 2);
        String mesDataIni = txtDataIni.getText().substring(3, 5);
        String anoDataIni = txtDataIni.getText().substring(6, 10);

        String diaDataFim = txtDataFim.getText().substring(0, 2);
        String mesDataFim = txtDataFim.getText().substring(3, 5);
        String anoDataFim = txtDataFim.getText().substring(6, 10);

        int dataCompleta = Integer.parseInt(anoDataIni + mesDataIni + diaDataIni);
        int dataCompletaFim = Integer.parseInt(anoDataFim + mesDataFim + diaDataFim);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select * from contas_pagar where cod_forn=? and STATUS =? and vencimento_base >=? and vencimento_base <=?");
            pst.setInt(1, Integer.parseInt(txtCodFornecedor.getText()));
            pst.setString(2, "EM ABERTO");
            pst.setInt(3, dataCompleta);
            pst.setInt(4, dataCompletaFim);

            rs = pst.executeQuery();
            int linha = 1;

            while (rs.next()) {
                //        JOptionPane.showMessageDialog(this, "Entrou aqui");

                txtNomeFornecedor.setText(rs.getString(5));

                float valor = rs.getFloat(8);

                DecimalFormat df = new DecimalFormat("###,##0.00");
                String valorFormatado = df.format(valor);

                {
                    modelo.addRow(new Object[]{
                        modelo.getRowCount(),
                        false,
                        rs.getInt("titulo"),//Titulo
                        rs.getString("nome_forn"),//Fornecedor
                        rs.getString("vencimento"),//Vencimento
                        rs.getString("parcela"),//Parcela
                        valorFormatado,//Valor
                        "EM ABERTO",
                        rs.getString("numero_titulo"),//Parcela
                        rs.getString("tipo_titulo"),//Parcela
                    });//Status

                }

            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        AlinharColunas();
    }

    public void carregaTodosFornecedores() {

        Cores cor = new Cores();
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        String diaDataIni = txtDataIni.getText().substring(0, 2);
        String mesDataIni = txtDataIni.getText().substring(3, 5);
        String anoDataIni = txtDataIni.getText().substring(6, 10);

        String diaDataFim = txtDataFim.getText().substring(0, 2);
        String mesDataFim = txtDataFim.getText().substring(3, 5);
        String anoDataFim = txtDataFim.getText().substring(6, 10);

        int dataCompleta = Integer.parseInt(anoDataIni + mesDataIni + diaDataIni);
        int dataCompletaFim = Integer.parseInt(anoDataFim + mesDataFim + diaDataFim);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select * from contas_pagar where STATUS =? and vencimento_base >=? "
                    + "and vencimento_base <=? order by vencimento_base asc, cod_forn");

            pst.setString(1, "EM ABERTO");
            pst.setInt(2, dataCompleta);
            pst.setInt(3, dataCompletaFim);

            rs = pst.executeQuery();
            int linha = 1;

            while (rs.next()) {
                //        JOptionPane.showMessageDialog(this, "Entrou aqui");

                //    txtNomeFornecedor.setText(rs.getString(5));
                float valor = rs.getFloat(8);

                DecimalFormat df = new DecimalFormat("###,##0.00");
                String valorFormatado = df.format(valor);

                {
                    modelo.addRow(new Object[]{
                        modelo.getRowCount(),
                        false,
                        rs.getInt("titulo"),//Titulo
                        rs.getString("nome_forn"),//Fornecedor
                        rs.getString("vencimento"),//Vencimento
                        rs.getString("parcela"),//Parcela
                        valorFormatado,//Valor
                        "EM ABERTO",});//Status

                }

            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        AlinharColunas();
    }

    public void buscaFornecedor() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select razaoSocial from fornecedor where ID = ?");
            pst.setString(1, txtCodFornecedor.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtNomeFornecedor.setText(rs.getString(1));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void contaTitulosSelecionados() {
        int increment = 0;
        totalTitulosSelecionados = 0;

        while (tabela.getRowCount() > increment) {
            boolean salvar = (boolean) tabela.getValueAt(increment, 1);
            if (salvar == true) {
                totalTitulosSelecionados += 1;
                increment++;
            } else {
                increment++;
            }
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
        jLabel2 = new javax.swing.JLabel();
        txtCodFornecedor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNomeFornecedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDataIni = new javax.swing.JFormattedTextField();
        txtDataFim = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDataPagamento = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        lblMsgProcessamento = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblFechar = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 4, true));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Parâmetros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel2.setText("Cod Fornecedor:");

        txtCodFornecedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtCodFornecedor.setForeground(new java.awt.Color(51, 51, 51));
        txtCodFornecedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFornecedorFocusLost(evt);
            }
        });
        txtCodFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFornecedorActionPerformed(evt);
            }
        });
        txtCodFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodFornecedorKeyPressed(evt);
            }
        });

        jLabel3.setText("Fornecedor:");

        txtNomeFornecedor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNomeFornecedor.setForeground(new java.awt.Color(51, 51, 51));
        txtNomeFornecedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNomeFornecedor.setEnabled(false);

        jLabel4.setText("Vecimento Inicial:");

        txtDataIni.setForeground(new java.awt.Color(51, 51, 51));
        try {
            txtDataIni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataIni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataIni.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDataIni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataIniActionPerformed(evt);
            }
        });

        txtDataFim.setForeground(new java.awt.Color(51, 51, 51));
        try {
            txtDataFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataFim.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataFim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDataFim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataFimActionPerformed(evt);
            }
        });

        jLabel5.setText("Vencimento Final:");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Find.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel6.setText("Data pagamento:");

        txtDataPagamento.setForeground(new java.awt.Color(51, 51, 51));
        try {
            txtDataPagamento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataPagamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataPagamento.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtDataPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataPagamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addComponent(txtDataIni, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel4)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtNomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNomeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(txtCodFornecedor))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDataIni, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Selecione os titulos para pagamento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "", "Titulo", "Fornecedor", "Vencimento", "Parcela", "Valor ", "Situação", "Nº Doc", "Tipo Doc"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabela.setRowHeight(25);
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(2);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(2);
            tabela.getColumnModel().getColumn(0).setMaxWidth(2);
            tabela.getColumnModel().getColumn(1).setMinWidth(20);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(20);
            tabela.getColumnModel().getColumn(1).setMaxWidth(20);
            tabela.getColumnModel().getColumn(2).setMinWidth(50);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(2).setMaxWidth(50);
            tabela.getColumnModel().getColumn(3).setMinWidth(250);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(250);
            tabela.getColumnModel().getColumn(3).setMaxWidth(250);
            tabela.getColumnModel().getColumn(9).setMinWidth(120);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(9).setMaxWidth(120);
        }

        lblMsgProcessamento.setBackground(new java.awt.Color(255, 255, 255));
        lblMsgProcessamento.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        lblMsgProcessamento.setOpaque(true);

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 153));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblMsgProcessamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE))
                        .addGap(10, 10, 10))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblMsgProcessamento, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Apply.png"))); // NOI18N
        jButton2.setText("Efetuar pagamentos...");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(94, 110, 110));

        jLabel1.setBackground(new java.awt.Color(94, 110, 110));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Contas a pagar - pagamento por seleção");

        lblFechar.setBackground(new java.awt.Color(255, 255, 255));
        lblFechar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblFechar.setForeground(new java.awt.Color(51, 51, 51));
        lblFechar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Close.png"))); // NOI18N
        lblFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFechar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblFechar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Thumbs up.png"))); // NOI18N
        jButton3.setText("Marcar/Desmarcar");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (txtCodFornecedor.getText().equals("")) {
            carregaTodosFornecedores();

        } else {
            carregaTabela();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tabela.clearSelection();
        contaTitulosSelecionados();
        JOptionPane.showMessageDialog(this, "Total de titulos selecionados " + totalTitulosSelecionados);
        jProgressBar1.setMaximum(totalTitulosSelecionados);
        jProgressBar1.setStringPainted(true);
        if (totalTitulosSelecionados == 0) {
            JOptionPane.showMessageDialog(this, "Selecione algum titulo para pagamento", "AGUITECH", 2);
        } else {

            ModelUsuario user = new ModelUsuario();
            lblMsgProcessamento.setVisible(true);
            lblMsgProcessamento.setVisible(true);

            new Thread() {

                public void run() {

                    //  jProgressBar1.setIndeterminate(true);
                    try {
                        int contTitulosSelecionados = 0;
                        PreparedStatement pst;
                        int increment = 0;
                        int total_linha = tabela.getRowCount();

                        while (total_linha > increment) {
                            lblMsgProcessamento.setText("Processando... Por favor aguarde a conclusão sem sair dessa tela!");
                            boolean salvar = (boolean) tabela.getValueAt(increment, 1);
                            if (salvar == true) {
                                pst = conn.getConexao().prepareStatement("UPDATE contas_pagar SET STATUS=?,"
                                        + "DATA_PAGAMENTO =?, VALOR_PAGO=?, JUROS_MULTAS =?, USER_PG =? where titulo =?");
                                pst.setString(1, "TITULO PAGO");//Atualiza o status do titulo
                                pst.setString(2, txtDataPagamento.getText());//Data do Pagamento

                                pst.setFloat(3, Float.parseFloat(tabela.getValueAt(increment, 6).toString().replace(".", "").replaceAll(",", ".")));//Valor Final
                                pst.setFloat(4, (float) 0.00);//Juros
                                pst.setString(5, user.getUsuarioLogado());
                                pst.setInt(6, Integer.parseInt(tabela.getValueAt(increment, 2).toString()));
                                pst.execute();
                                pst.close();
                                conn.getConexao().close();
                                increment++;

                                contTitulosSelecionados = contTitulosSelecionados + 1;
                                jProgressBar1.setValue(contTitulosSelecionados);

                            } else {
                                increment++;
                            }

                        }
                        jProgressBar1.setIndeterminate(false);
                        jProgressBar1.setValue(contTitulosSelecionados);
                        lblMsgProcessamento.setText("Tudo certo! Total de titulos processados: " + contTitulosSelecionados);
                        jProgressBar1.setValue(contTitulosSelecionados);

                        Confirmacao conf = new Confirmacao(null, true);
                        conf.textoPegamentoTitulos();
                        conf.setVisible(true);
                        dispose();

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao processar titulos. " + ex);
                    }

                }
            }.start();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void lblFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFecharMouseClicked
        this.dispose();


    }//GEN-LAST:event_lblFecharMouseClicked

    private void lblFecharMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFecharMousePressed
        lblFechar.setBackground(Color.red);         // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharMousePressed

    private void lblFecharMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFecharMouseReleased
        lblFechar.setBackground(Color.WHITE);         // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharMouseReleased

    private void lblFecharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblFecharKeyPressed
        lblFechar.setBackground(Color.red);

        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharKeyPressed

    private void lblFecharKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblFecharKeyReleased
        lblFechar.setBackground(Color.white);        // TODO add your handling code here:
    }//GEN-LAST:event_lblFecharKeyReleased

    private void txtCodFornecedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFornecedorFocusLost
        if (txtCodFornecedor.getText().equals("")) {
            txtNomeFornecedor.setText("");
        } else {
            buscaFornecedor();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFornecedorFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        int totalLinhas = tabela.getRowCount();
        int increment = 0;

        while (totalLinhas > increment) {
            if (tabela.getValueAt(increment, 1).equals(false)) {
                tabela.setValueAt(true, increment, 1);
                increment++;
            } else {
                tabela.setValueAt(false, increment, 1);
                increment++;
            }

        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtDataPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataPagamentoActionPerformed
        txtCodFornecedor.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataPagamentoActionPerformed

    private void txtCodFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFornecedorActionPerformed
        txtDataIni.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFornecedorActionPerformed

    private void txtDataIniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataIniActionPerformed
        txtDataFim.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_txtDataIniActionPerformed

    private void txtCodFornecedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodFornecedorKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaFornecedor consultaForn = new ConsultaFornecedor(null, true);
            consultaForn.carregaArray();
            consultaForn.carregaTabela();
            consultaForn.setVisible(true);
            String codigo = consultaForn.getCodigo();
            String nome = consultaForn.getNome();
            txtCodFornecedor.setText(codigo);
            txtNomeFornecedor.setText(nome);
            txtDataIni.requestFocus();
        } else {

        }

    }//GEN-LAST:event_txtCodFornecedorKeyPressed

    private void txtDataFimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataFimActionPerformed
        btnBuscar.requestFocus();

    }//GEN-LAST:event_txtDataFimActionPerformed

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
            java.util.logging.Logger.getLogger(JD_PG_BLOCO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_PG_BLOCO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_PG_BLOCO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_PG_BLOCO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_PG_BLOCO dialog = new JD_PG_BLOCO(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFechar;
    private javax.swing.JLabel lblMsgProcessamento;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCodFornecedor;
    private javax.swing.JFormattedTextField txtDataFim;
    private javax.swing.JFormattedTextField txtDataIni;
    private javax.swing.JFormattedTextField txtDataPagamento;
    private javax.swing.JTextField txtNomeFornecedor;
    // End of variables declaration//GEN-END:variables
}
