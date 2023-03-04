/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import FormulariosConsultas.ConsultaProduto2;
import Model.ModelUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.print.attribute.Size2DSyntax.MM;
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
 * @author Desktop
 */
public class JD_REQ extends javax.swing.JDialog {

    String op;
    float totalHectare;
    float hectare;
    float estoque;
    float valor;
    float custoM;
    float custoTotal;
    float estoqueFinal;
    float valorFinal;
    Conexao conecta = new Conexao();
    ModelUsuario user = new ModelUsuario();
    Conexao conn = new Conexao();
    String opSelecionada;

    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    public JD_REQ(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));

        initComponents();
        configInicias();
        
        if (lblTitulo.getText().equals("Registro de plantio - Alterar")) {
            btnAddProduto.setEnabled(false);
        } else {

        }

    }

    public void configInicias() {
        Cores cor = new Cores();
        int maxHeaderHeight = 20;
        Dimension d = new Dimension(Tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        Tabela.getTableHeader().setPreferredSize(d);

        Tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabeçalho
        Tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabeçalho

        Tabela.getTableHeader().setOpaque(false);
        Tabela.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 11));
        Tabela.setForeground(cor.getCorFonteDadosTabela());

        // CorLinhaTabela();
        
    }

    public void CorLinhaTabela() {
        Cores cor = new Cores();

        Tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                    Tabela.getColumnModel().getColumn(1).setCellRenderer(centro);//Item
                    Tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Codigo INterno
                    Tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Produto
                    Tabela.getColumnModel().getColumn(4).setCellRenderer(centro);//Unidade
                    Tabela.getColumnModel().getColumn(5).setCellRenderer(direita);//qUANTIDADE
                    Tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qUANTIDADE
                    Tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//qUANTIDADE
                    Tabela.getColumnModel().getColumn(8).setCellRenderer(direita);//qUANTIDADE
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

                    Tabela.getColumnModel().getColumn(1).setCellRenderer(centro);//Item
                    Tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Codigo INterno
                    Tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Produto
                    Tabela.getColumnModel().getColumn(4).setCellRenderer(centro);//Unidade
                    Tabela.getColumnModel().getColumn(5).setCellRenderer(direita);//qUANTIDADE
                    Tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qUANTIDADE
                    Tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//qUANTIDADE
                    Tabela.getColumnModel().getColumn(8).setCellRenderer(direita);//qUANTIDADE

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;

                }

                return label;
            }

        });

    }

    public void addCiclo() {
        String cultura = (String) cbCultura.getSelectedItem();
        String lote = (String) cbLote.getSelectedItem();
        String setor = (String) cbSetor.getSelectedItem();

        String ordemProd = cultura + "-" + lote + "-" + setor + "-" + txtSequencial.getText();

        DefaultTableModel model = (DefaultTableModel) Tabela.getModel();
        model.addRow(new Object[]{
            Tabela.getRowCount() + 1,
            ordemProd,
            txtSequencial.getText(),
            txtDataInicioCliclo.getText(),
            txtPrimeiraColheita.getText(),
            txtProdutividade.getText(),
            txtPrevisaoEnc.getText(),
            cbVariedade.getSelectedItem(),
            cbEstagio.getSelectedItem()

        }
        );
        CorLinhaTabela();
    }

    public void salvar() {

        try {

            int total_linha = Tabela.getRowCount();
            int incremente_linda = 1;

            while (total_linha >= incremente_linda) {

                PreparedStatement pst = conn.getConexao().prepareStatement("insert into plantio (dataCriacao,"
                        + "inicioCiclo,seqCiclo,FinCiclo,ordem_pro,"
                        + "lote, setor, hectare, cultura, variedade,erradicacao,status,inicioColheita, produtividade,"
                        + "ESTAGIO)"
                        + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pst.setString(1, txtData.getText()); // Data da Criacao
                pst.setString(6, (String) cbLote.getSelectedItem());
                pst.setString(7, (String) cbSetor.getSelectedItem());
                pst.setString(9, (String) cbCultura.getSelectedItem());
                pst.setFloat(8, Float.parseFloat(txtHectare.getText()));
                pst.setString(11, txtErradicacao.getText());
                pst.setString(12, "ATIVO");

                //Itens da tabela
                pst.setString(5, Tabela.getValueAt(incremente_linda - 1, 1).toString()); //Codigo de Barras
                pst.setString(3, Tabela.getValueAt(incremente_linda - 1, 2).toString()); //Codigo Produto
                pst.setString(2, Tabela.getValueAt(incremente_linda - 1, 3).toString()); //Nome Produto
                pst.setString(13, Tabela.getValueAt(incremente_linda - 1, 4).toString()); //Unidade
                pst.setFloat(14, Float.parseFloat(Tabela.getValueAt(incremente_linda - 1, 5).toString().replace(".", "").replaceAll(",", "."))); //Quantidade Produto
                pst.setString(4, Tabela.getValueAt(incremente_linda - 1, 6).toString()); //Preço Produto
                pst.setString(10, Tabela.getValueAt(incremente_linda - 1, 7).toString()); //Total Produto
                pst.setString(15, (String) cbEstagio.getSelectedItem());

                pst.execute();
                incremente_linda++;
                pst.close();
            }
            Confirmacao conf = new Confirmacao(null, true);
            conf.textoPlantioSalva();
            conf.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);

        }
    }

    public void alterar() {

        try {

            int total_linha = Tabela.getRowCount();
            int incremente_linda = 1;

            while (total_linha >= incremente_linda) {

                PreparedStatement pst = conn.getConexao().prepareStatement("update plantio set dataCriacao=?,"
                        + "lote=?, setor=?,cultura=?,hectare=?,erradicacao=? where ordem_pro=?");

                pst.setString(1, txtData.getText()); // Data da Criacao
                pst.setString(2, (String) cbLote.getSelectedItem());
                pst.setString(3, (String) cbSetor.getSelectedItem()); // Data da Criacao
                pst.setString(4, (String) cbCultura.getSelectedItem());
                pst.setFloat(5, Float.parseFloat(txtHectare.getText()));
                pst.setString(6, txtErradicacao.getText());
                pst.setString(7, opSelecionada);

                //Itens da tabela
                /*     pst.setString(5, Tabela.getValueAt(incremente_linda - 1, 1).toString()); //Codigo de Barras
                pst.setString(3, Tabela.getValueAt(incremente_linda - 1, 2).toString()); //Codigo Produto
                pst.setString(2, Tabela.getValueAt(incremente_linda - 1, 3).toString()); //Nome Produto
                pst.setString(13, Tabela.getValueAt(incremente_linda - 1, 4).toString()); //Unidade
                pst.setFloat(14, Float.parseFloat(Tabela.getValueAt(incremente_linda - 1, 5).toString().replace(".", "").replaceAll(",", "."))); //Quantidade Produto
                pst.setString(4, Tabela.getValueAt(incremente_linda - 1, 6).toString()); //Preço Produto
                pst.setString(10, Tabela.getValueAt(incremente_linda - 1, 7).toString()); //Total Produto*/
                pst.execute();
                incremente_linda++;
                pst.close();
            }
            Confirmacao conf = new Confirmacao(null, true);
            conf.textoPlantioAltera();
            conf.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);

        }
    }

    public void alterarItemPlantio() {

        try {

            int total_linha = Tabela.getRowCount();
            int incremente_linda = 1;

            while (total_linha >= incremente_linda) {

                PreparedStatement pst = conn.getConexao().prepareStatement("update plantio set ordem_pro=?,"
                        + "seqCiclo=?, inicioCiclo=?,inicioColheita=?,produtividade=?,"
                        + "FinCiclo=?,variedade=?,estagio=? where ordem_pro=?");

                //Itens da tabela
                pst.setString(1, Tabela.getValueAt(incremente_linda - 1, 1).toString()); //Codigo de Barras
                pst.setString(2, txtSequencial.getText()); //Codigo Produto
                pst.setString(3, txtDataInicioCliclo.getText()); //Nome Produto
                pst.setString(4, txtPrimeiraColheita.getText()); //Unidade
                pst.setFloat(5, Float.parseFloat(txtProdutividade.getText()));
                pst.setString(6, txtPrevisaoEnc.getText()); //Preço Produto
                pst.setString(7, (String) cbVariedade.getSelectedItem());
                pst.setString(8, (String) cbEstagio.getSelectedItem());
                pst.setString(9, opSelecionada);

                pst.execute();
                incremente_linda++;
                pst.close();
            }
            Confirmacao conf = new Confirmacao(null, true);
            conf.textoPlantioAltera();
            conf.setVisible(true);
            //  this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);

        }
    }

    public void carregaDados() {

        int total_linha = Tabela.getRowCount();
        int incremente_linda = 1;

        try {
            ResultSet rs = null;
            PreparedStatement pst;
            pst = conn.getConexao().prepareStatement("select * from plantio where ordem_pro =?");

            pst.setString(1, opSelecionada);
            rs = pst.executeQuery();

            DefaultTableModel modelo = (DefaultTableModel) Tabela.getModel();

            modelo.setNumRows(0);
            while (rs.next()) {

                txtData.setText(rs.getString("dataCriacao"));
                cbLote.setSelectedItem(rs.getString("lote"));
                cbSetor.setSelectedItem(rs.getString("setor"));
                cbCultura.setSelectedItem(rs.getString("cultura"));
                cbVariedade.setSelectedItem(rs.getString("variedade"));
                txtHectare.setText(String.valueOf(rs.getFloat("hectare")));
                txtErradicacao.setText(rs.getString("erradicacao"));

                modelo.addRow(new Object[]{
                    modelo.getRowCount() + 1,
                    rs.getString("ordem_pro"),//Codigo de Barra
                    rs.getString("seqCiclo"),//Codigo do Produto
                    rs.getString("inicioCiclo"),//Descrição do Produto
                    rs.getString("inicioColheita"),//Unidade de Medida
                    rs.getString("produtividade"),//Quantidade
                    rs.getString("FinCiclo"),//Preço
                    rs.getString("variedade"),//Total
                    rs.getString("estagio"),//Total
                }
                );

            }

            pst.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela. " + ex);
        }

    }

    public void pegaOpSelecionada(BASE_PLANTIO plantio) {
        opSelecionada = plantio.getOpselecionada();

    }

    public void parametrosAlterar() {
        lblTitulo.setText("Registro de plantio - Alterar");
    }

    public void parametrosVisualizar() {
        lblTitulo.setText("Registro de plantio - Visualizar");
        btnAddProduto.setEnabled(false);
        btnConfirmar.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnRemover.setEnabled(false);
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtHectare = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbCultura = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbLote = new javax.swing.JComboBox<>();
        cbSetor = new javax.swing.JComboBox<>();
        txtData = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtErradicacao = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabela = new javax.swing.JTable();
        btnAddProduto = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        txtSequencial = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtProdutividade = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        cbVariedade = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDataInicioCliclo = new javax.swing.JFormattedTextField();
        txtPrimeiraColheita = new javax.swing.JFormattedTextField();
        txtPrevisaoEnc = new javax.swing.JFormattedTextField();
        Estagio = new javax.swing.JLabel();
        cbEstagio = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Dt Criação:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Hectare:");

        txtHectare.setForeground(new java.awt.Color(51, 51, 51));
        txtHectare.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHectareFocusLost(evt);
            }
        });
        txtHectare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHectareActionPerformed(evt);
            }
        });
        txtHectare.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHectareKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Lote:");

        cbCultura.setForeground(new java.awt.Color(51, 51, 51));
        cbCultura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BN", "GO", "AC", "LA", "LI" }));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Cultura:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Setor:");

        cbLote.setForeground(new java.awt.Color(51, 51, 51));
        cbLote.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "30", "31", "33", "37", "41", "80", "130", "167", "198", "219", "424", "425" }));

        cbSetor.setForeground(new java.awt.Color(51, 51, 51));
        cbSetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", " " }));

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtData.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Erradicação:");

        try {
            txtErradicacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtErradicacao.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(cbLote, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbCultura, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHectare, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtErradicacao, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 142, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(30, 30, 30))
                    .addComponent(txtErradicacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHectare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbCultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(28, 28, 28)))
                .addContainerGap(124, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(94, 110, 110));
        jPanel5.setForeground(new java.awt.Color(242, 242, 242));
        jPanel5.setName(""); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 3));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Registro de plantio - Incluir");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("X");
        jLabel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(142, 136, 136), 1, true));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.setMaximumSize(new java.awt.Dimension(40, 40));
        jLabel10.setMinimumSize(new java.awt.Dimension(40, 40));
        jLabel10.setPreferredSize(new java.awt.Dimension(40, 40));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        btnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnConfirmar.setText("Finalizar");
        btnConfirmar.setActionCommand("");
        btnConfirmar.setBorder(null);
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnConfirmar.setOpaque(true);
        btnConfirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnConfirmarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnConfirmarFocusLost(evt);
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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnConfirmar)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Ciclo Produtivo"));

        Tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        Tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Ordem de Producao", "Seq.", "Inicio", "Primeira Colheita", "Produtividade", "Data Encerramento", "Variedade", "Estagio"
            }
        ));
        Tabela.setFillsViewportHeight(true);
        Tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Tabela);
        if (Tabela.getColumnModel().getColumnCount() > 0) {
            Tabela.getColumnModel().getColumn(0).setMinWidth(3);
            Tabela.getColumnModel().getColumn(0).setPreferredWidth(3);
            Tabela.getColumnModel().getColumn(0).setMaxWidth(3);
        }

        btnAddProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Create.png"))); // NOI18N
        btnAddProduto.setText("Adicionar");
        btnAddProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdutoActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Modify.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Delete.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.setEnabled(false);
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        txtSequencial.setForeground(new java.awt.Color(51, 51, 51));
        txtSequencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSequencialActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Seq. ");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Ini. Colheita:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Produtividade(Kg):");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Fim do Ciclo");

        cbVariedade.setForeground(new java.awt.Color(51, 51, 51));
        cbVariedade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PRATA CATARINA", "PALUMA", "ACEROLA" }));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Variedade:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Inicio do Ciclo:");

        try {
            txtDataInicioCliclo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataInicioCliclo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        try {
            txtPrimeiraColheita.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtPrimeiraColheita.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        try {
            txtPrevisaoEnc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtPrevisaoEnc.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Estagio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Estagio.setText("Estagio:");

        cbEstagio.setForeground(new java.awt.Color(51, 51, 51));
        cbEstagio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PREPARACAO", "DESENVOLVIMENTO", "PRODUZINDO", "DESATIVANDO", "ENCERRADA", " " }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 926, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtDataInicioCliclo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtSequencial, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                            .addComponent(txtPrimeiraColheita))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtProdutividade, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPrevisaoEnc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbVariedade, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Estagio, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbEstagio, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnAddProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(Estagio)
                                .addGap(8, 8, 8)
                                .addComponent(cbEstagio))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSequencial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(6, 6, 6)
                                .addComponent(txtPrimeiraColheita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtProdutividade, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(8, 8, 8)
                                .addComponent(cbVariedade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(30, 30, 30))
                            .addComponent(txtDataInicioCliclo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrevisaoEnc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProduto)
                    .addComponent(btnAlterar)
                    .addComponent(btnRemover))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtHectareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHectareActionPerformed
        txtSequencial.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtHectareActionPerformed

    private void btnConfirmarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusGained
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarFocusGained

    private void btnConfirmarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusLost
        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_btnConfirmarFocusLost

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

        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarMouseReleased

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        switch (lblTitulo.getText()) {
            case "Registro de plantio - Incluir":
                salvar();
                break;
            case "Registro de plantio - Alterar":
                alterar();

                break;
            case "Compras - Excluir":
                //   excluir();
                break;
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtHectareKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHectareKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            txtHectare.setText("");
            ConsultaProduto2 consulta = new ConsultaProduto2(new javax.swing.JFrame(), true);
            consulta.setVisible(true);
            String codigo = consulta.getCodigo();
            String nome = consulta.getNomeProd();
            String codProd = consulta.getCodigo();
            String und = consulta.getUnidade();
            float estoqueAtual = consulta.getEstoque();
            float valorAtual = consulta.getValor();

            txtHectare.setText(codigo);
            //   txtProduto.setText(nome);
            //   txtUnidade.setText(und);
            estoque = estoqueAtual;
            valor = valorAtual;
            custoM = valor / estoque;

        } else {

        }
    }//GEN-LAST:event_txtHectareKeyPressed

    private void txtSequencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSequencialActionPerformed
        btnAddProduto.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtSequencialActionPerformed

    private void btnAddProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdutoActionPerformed
        String op = cbSetor.getSelectedItem() + "_" + cbLote.getSelectedItem();
        addCiclo();

    }//GEN-LAST:event_btnAddProdutoActionPerformed

    private void txtHectareFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHectareFocusLost

    }//GEN-LAST:event_txtHectareFocusLost

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void TabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaMouseClicked

        txtDataInicioCliclo.setText((String) Tabela.getValueAt(Tabela.getSelectedRow(), 3));
        txtSequencial.setText((String) Tabela.getValueAt(Tabela.getSelectedRow(), 2));
        txtPrimeiraColheita.setText((String) Tabela.getValueAt(Tabela.getSelectedRow(), 4));
        txtProdutividade.setText((String) Tabela.getValueAt(Tabela.getSelectedRow(), 5));
        txtPrevisaoEnc.setText((String) Tabela.getValueAt(Tabela.getSelectedRow(), 6));
        cbVariedade.setSelectedItem((String) Tabela.getValueAt(Tabela.getSelectedRow(), 7));
        cbEstagio.setSelectedItem((String) Tabela.getValueAt(Tabela.getSelectedRow(), 8));
    }//GEN-LAST:event_TabelaMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        alterarItemPlantio();
        carregaDados();

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed

    }//GEN-LAST:event_btnRemoverActionPerformed

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
            java.util.logging.Logger.getLogger(JD_REQ.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_REQ.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_REQ.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_REQ.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_REQ dialog = new JD_REQ(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel Estagio;
    private javax.swing.JTable Tabela;
    private javax.swing.JButton btnAddProduto;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnRemover;
    private javax.swing.JComboBox<String> cbCultura;
    private javax.swing.JComboBox<String> cbEstagio;
    private javax.swing.JComboBox<String> cbLote;
    private javax.swing.JComboBox<String> cbSetor;
    private javax.swing.JComboBox<String> cbVariedade;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JFormattedTextField txtDataInicioCliclo;
    private javax.swing.JFormattedTextField txtErradicacao;
    private javax.swing.JTextField txtHectare;
    private javax.swing.JFormattedTextField txtPrevisaoEnc;
    private javax.swing.JFormattedTextField txtPrimeiraColheita;
    private javax.swing.JFormattedTextField txtProdutividade;
    private javax.swing.JTextField txtSequencial;
    // End of variables declaration//GEN-END:variables
}
