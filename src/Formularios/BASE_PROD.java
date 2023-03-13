/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.SelecaoInvalida;
import FormulariosConsultas.ConsultaCategoria;
import FormulariosConsultas.ConsultaSubCategoria;
import Model.ModelUsuario;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Font;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class BASE_PROD extends javax.swing.JFrame {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + ": HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataHora_Minuto = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    String produtoSelecionado;

    public String getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(String produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    ModelUsuario user = new ModelUsuario();

    private static ArrayList<String> produtos = new ArrayList();

    public BASE_PROD() {
        initComponents();
        carregaArray();
        //    carregaTabela();

        this.setExtendedState(MAXIMIZED_BOTH);
        configInicias();

        utilitario util = new utilitario();
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));
        lblDadosEmpresa.setText(util.getDadosEmpresaRodape());
        //  btnImprimir.setVisible(false);

        btnAlterar.setBackground(Color.WHITE);
        btnExcluir.setBackground(Color.WHITE);
        btnVisualizar.setBackground(Color.WHITE);

        btnVoltar.setBackground(Color.WHITE);
    }

    public void configInicias() {

        Cores cor = new Cores();
        //  tabela.setSelectionBackground(cor.getCorLinhaSelecionada());
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

    }

    public void geraRelatorio(String caminho) {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/edicarlos?characterEncoding=utf8", "root", "");

            //JRResultSetDataSource jrRS = new JRResultSetDataSource(getResultSet());
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("ArquivosJasper/relatorioVendas.jasper");

            JasperReport relatorio = JasperCompileManager.compileReport(caminho);
            JasperPrint relatorio_preenchido = JasperFillManager.fillReport(caminhoRelatorio, null, conn);
            JasperViewer.viewReport(relatorio_preenchido);

            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
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
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);

                    direita.setBackground(c);
                    esquerda.setBackground(c);

                } else {
                    c = cor.getCorLinhaParTabela();

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);

                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);

                    esquerda.setBackground(c);
                    direita.setBackground(c);

                }

                // 
                return label;
            }

        });

    }

    public void carregaArray() {
        produtos.clear();
        Conexao conn = new Conexao();

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos order by codigo asc limit 1000");
            rs = pst.executeQuery();

            while (rs.next()) {

                produtos.add(rs.getString("codigo")); //Codigo 0
                produtos.add(rs.getString("nome")); //Nome do Produto 1
                produtos.add(rs.getString("und")); //Unidade de Medida 2
                produtos.add(rs.getString("categoria")); // 3
                produtos.add(rs.getString("subCategoria")); // 4
                produtos.add(rs.getString("precoVenda")); // 5
                produtos.add(rs.getString("codBarra")); // 6
                produtos.add(rs.getString("bloqueado")); // 7

                //  JOptionPane.showMessageDialog(null, produtos.get(1));
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

    }

    public void carregaTabela() {

        //   JOptionPane.showConfirmDialog(this, produtos.get(1));
        Conexao conn = new Conexao();
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, cor.getSizeFonteHenderTable()));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        int linha = 1;

        int indice = 0;

        DecimalFormat df = new DecimalFormat("#,##0.00");

        for (int i = 0; i < produtos.size(); i = i + 8) {

            modelo.addRow(new Object[]{
                linha,
                produtos.get(i),//Codigo
                produtos.get(i + 1),//nome
                produtos.get(i + 2),//Unidade de Medida
                produtos.get(i + 3),//Categoria
                produtos.get(i + 4),//Sub Categoria
                "R$",
                produtos.get(i + 5),//Preco de Venda
                produtos.get(i + 6),//Codigo de Barras
                produtos.get(i + 7),//Status
            /* rs.getString(2),//Codigo
                        rs.getString(6),//nome
                        rs.getString(7),//Unidade de Medida
                        rs.getString(8),//Categoria
                        rs.getString(9),//Sub Categoria
                        "R$",
                        rs.getString(15),//Preco de Venda
                        rs.getString(16),//Codigo de Barras
                        status,//Status*/});

            linha = linha + 1;

        }

        CorLinhaTabela();
    }

    public void buscaProdutoNome() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        Font font = new Font();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos where nome LIKE? order by nome");

            String pesquisa = txtPesquisa.getText();
            pst.setString(1, pesquisa + "%");
            rs = pst.executeQuery();
            String precoVenda;
            while (rs.next()) {
                //    JOptionPane.showConfirmDialog(null, "Começou");

                String status = rs.getString(8);

                if (status == "N") {
                    status = "Bloqueado";
                } else {
                    status = "Ativo";
                }
                if (rs.getString("precoVenda").equals("")) {
                    precoVenda = "0,00";
                } else {
                    precoVenda = rs.getString("precoVenda");

                }

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(2),//Codigo
                        rs.getString(6),//nome
                        rs.getString(7),//Unidade de Medida
                        rs.getString(8),//Categoria
                        rs.getString(9),//Sub Categoria
                        "R$",
                        precoVenda,//Preco de Venda
                        rs.getString(16),//Codigo de Barras
                        status,//Status
                    });
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

        CorLinhaTabela();

    }

    public void buscaProdutoCodigoInterno() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        Font font = new Font();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos where codigo =? order by nome");

            String pesquisa = txtPesquisa.getText();
            pst.setString(1, pesquisa);
            rs = pst.executeQuery();
            String precoVenda;
            while (rs.next()) {

                String status = rs.getString(8);

                if (status == "N") {
                    status = "Bloqueado";
                } else {
                    status = "Ativo";
                }
                if (rs.getString("precoVenda").equals("")) {
                    precoVenda = "0,00";
                } else {
                    precoVenda = rs.getString("precoVenda");

                }

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(2),//Codigo
                        rs.getString(6),//nome
                        rs.getString(7),//Unidade de Medida
                        rs.getString(8),//Categoria
                        rs.getString(9),//Sub Categoria
                        "R$",
                        precoVenda,//Preco de Venda
                        rs.getString(16),//Codigo de Barras
                        status,//Status
                    });
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

        CorLinhaTabela();

    }

    public void buscaProdutoCodigoBarra() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        Font font = new Font();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos where codBarra =?");

            String pesquisa = txtPesquisa.getText();
            pst.setString(1, pesquisa);
            rs = pst.executeQuery();
            String precoVenda;
            while (rs.next()) {
                //    JOptionPane.showConfirmDialog(null, "Começou");

                String status = rs.getString(8);

                if (status == "N") {
                    status = "Bloqueado";
                } else {
                    status = "Ativo";
                }
                if (rs.getString("precoVenda").equals("")) {
                    precoVenda = "0,00";
                } else {
                    precoVenda = rs.getString("precoVenda");

                }
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(2),//Codigo
                        rs.getString(6),//nome
                        rs.getString(7),//Unidade de Medida
                        rs.getString(8),//Categoria
                        rs.getString(9),//Sub Categoria
                        "R$",
                        precoVenda,//Preco de Venda
                        rs.getString(16),//Codigo de Barras
                        status,//Status
                    });
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

        CorLinhaTabela();

    }

    public void imprimir(String caminho) {

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
        buttonGroup1 = new javax.swing.ButtonGroup();
        painelCorpo = new javax.swing.JPanel();
        painelPrincipal = new javax.swing.JPanel();
        PainelTabelaTitulos = new javax.swing.JPanel();
        cbTipoPesquisa = new javax.swing.JComboBox<>();
        txtPesquisa = new javax.swing.JTextField();
        btnBusca = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblDatahora = new javax.swing.JLabel();
        lblDadosEmpresa = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnIncluir = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Vendas");

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        PainelTabelaTitulos.setBackground(new java.awt.Color(255, 255, 255));
        PainelTabelaTitulos.setForeground(new java.awt.Color(38, 85, 100));

        cbTipoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Nome", " " }));

        txtPesquisa.setForeground(new java.awt.Color(102, 102, 102));

        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Codigo", "Produto", "Und", "Categoria", "Sub Categoria", "", "Preço Venda", "Codigo de Barra", "Status"
            }
        ));
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(239, 236, 236));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(1);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabela.getColumnModel().getColumn(0).setMaxWidth(1);
            tabela.getColumnModel().getColumn(1).setMinWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setMaxWidth(80);
            tabela.getColumnModel().getColumn(2).setMinWidth(350);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(350);
            tabela.getColumnModel().getColumn(2).setMaxWidth(350);
            tabela.getColumnModel().getColumn(3).setMinWidth(50);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(3).setMaxWidth(50);
            tabela.getColumnModel().getColumn(4).setMinWidth(200);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(4).setMaxWidth(200);
            tabela.getColumnModel().getColumn(5).setMinWidth(300);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(300);
            tabela.getColumnModel().getColumn(5).setMaxWidth(300);
            tabela.getColumnModel().getColumn(6).setMinWidth(0);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(6).setMaxWidth(0);
            tabela.getColumnModel().getColumn(7).setMinWidth(0);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(7).setMaxWidth(0);
            tabela.getColumnModel().getColumn(8).setMinWidth(0);
            tabela.getColumnModel().getColumn(8).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(8).setMaxWidth(0);
        }
        tabela.getAccessibleContext().setAccessibleParent(painelCorpo);

        jPanel3.setForeground(new java.awt.Color(38, 85, 100));

        lblDatahora.setForeground(new java.awt.Color(38, 85, 100));
        lblDatahora.setText("Data e Hora:");

        lblDadosEmpresa.setForeground(new java.awt.Color(38, 85, 100));
        lblDadosEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDadosEmpresa.setText(" Edicarlos Aguiar de Sousa - ME - Todos os direitos reservados");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDatahora, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDadosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatahora)
                    .addComponent(lblDadosEmpresa))
                .addContainerGap())
        );

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnIncluir.setBackground(new java.awt.Color(0, 153, 153));
        btnIncluir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnIncluir.setForeground(new java.awt.Color(255, 255, 255));
        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Add.png"))); // NOI18N
        btnIncluir.setText("Incluir");
        btnIncluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnIncluir.setContentAreaFilled(false);
        btnIncluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluir.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnIncluir.setOpaque(true);
        btnIncluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnIncluirFocusGained(evt);
            }
        });
        btnIncluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIncluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIncluirMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnIncluirMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnIncluirMouseReleased(evt);
            }
        });
        btnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIncluirActionPerformed(evt);
            }
        });
        jPanel2.add(btnIncluir);

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Update.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setAlignmentY(0.8F);
        btnAlterar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAlterar.setContentAreaFilled(false);
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnAlterar.setMaximumSize(new java.awt.Dimension(85, 41));
        btnAlterar.setMinimumSize(new java.awt.Dimension(85, 41));
        btnAlterar.setOpaque(true);
        btnAlterar.setPreferredSize(new java.awt.Dimension(85, 41));
        btnAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAlterarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAlterarMouseExited(evt);
            }
        });
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAlterar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Delete.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setAlignmentY(0.8F);
        btnExcluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnExcluir.setMaximumSize(new java.awt.Dimension(32, 50));
        btnExcluir.setMinimumSize(new java.awt.Dimension(32, 50));
        btnExcluir.setOpaque(true);
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel2.add(btnExcluir);

        btnVisualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Visualizar.png"))); // NOI18N
        btnVisualizar.setText("Visualizar");
        btnVisualizar.setAlignmentY(0.8F);
        btnVisualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVisualizar.setContentAreaFilled(false);
        btnVisualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisualizar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVisualizar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnVisualizar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnVisualizar.setOpaque(true);
        btnVisualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVisualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVisualizarMouseExited(evt);
            }
        });
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnVisualizar);

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Home.png"))); // NOI18N
        btnVoltar.setText("Inicio");
        btnVoltar.setAlignmentY(0.0F);
        btnVoltar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVoltar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnVoltar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnVoltar.setOpaque(true);
        btnVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVoltarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVoltarMouseExited(evt);
            }
        });
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });
        jPanel2.add(btnVoltar);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("Listagem de Produtos");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout PainelTabelaTitulosLayout = new javax.swing.GroupLayout(PainelTabelaTitulos);
        PainelTabelaTitulos.setLayout(PainelTabelaTitulosLayout);
        PainelTabelaTitulosLayout.setHorizontalGroup(
            PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PainelTabelaTitulosLayout.setVerticalGroup(
            PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblTitulo)
                .addGap(20, 20, 20)
                .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisa)
                            .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addComponent(PainelTabelaTitulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelTabelaTitulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout painelCorpoLayout = new javax.swing.GroupLayout(painelCorpo);
        painelCorpo.setLayout(painelCorpoLayout);
        painelCorpoLayout.setHorizontalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelCorpoLayout.setVerticalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();
        produtoSelecionado = ((String) tabela.getValueAt(linha, 1).toString());

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if (produtoSelecionado == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            CADASTRO_PRODUTO prod = new CADASTRO_PRODUTO();
            prod.pegaProduto(this);
            prod.parametrosAlterar();
            prod.setVisible(true);
            this.dispose();
        }


    }//GEN-LAST:event_btnAlterarActionPerformed


    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        //  Conexao con = new Conexao();
        //   try {
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();

        /* Class.forName("com.mysql.jdbc.Driver");
 
            JasperDesign jdesign = JRXmlLoader.load("src/relatorios/Compra.jrxml");
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null,con.conexao);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        }
    /*    Gerarelatorios gera = new Gerarelatorios();
        try {
            gera.gerar("src/relatorios/Venda.jrxml");
        } catch (JRException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnIncluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMouseEntered
        btnIncluir.setBackground(new java.awt.Color(0, 204, 204));

    }//GEN-LAST:event_btnIncluirMouseEntered

    private void btnIncluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMouseExited
        btnIncluir.setBackground(new java.awt.Color(0, 153, 153));

    }//GEN-LAST:event_btnIncluirMouseExited

    private void btnIncluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMousePressed
        btnIncluir.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnIncluirMousePressed

    private void btnIncluirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMouseReleased
        btnIncluir.setBackground(new java.awt.Color(0, 204, 204));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnIncluirMouseReleased

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        CADASTRO_PRODUTO prod = new CADASTRO_PRODUTO();
        ConsultaCategoria cat = new ConsultaCategoria(this, true);
        cat.carregaArray();
        ConsultaSubCategoria sub = new ConsultaSubCategoria(this, true);
        sub.carregaArray();
        prod.tituloIncluir();
        prod.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnIncluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnIncluirFocusGained


    }//GEN-LAST:event_btnIncluirFocusGained

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        if (produtoSelecionado == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            CADASTRO_PRODUTO prod = new CADASTRO_PRODUTO();
            prod.pegaProduto(this);
            prod.parametrosVisualizar();
            prod.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (produtoSelecionado == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            NOVO_CADASTRO_PRODUTO_EXCLUIR prod = new NOVO_CADASTRO_PRODUTO_EXCLUIR();
            prod.pegaProduto(this);
            prod.parametrosExcluir();
            prod.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAlterarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseEntered
        btnAlterar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnAlterarMouseEntered

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnVisualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseEntered
        btnVisualizar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVisualizarMouseEntered

    private void btnVoltarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseEntered
        btnVoltar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVoltarMouseEntered

    private void btnAlterarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseExited
        btnAlterar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnAlterarMouseExited

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(Color.white);
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnVisualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseExited
        btnVisualizar.setBackground(Color.white);
    }//GEN-LAST:event_btnVisualizarMouseExited

    private void btnVoltarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseExited
        btnVoltar.setBackground(Color.white);
    }//GEN-LAST:event_btnVoltarMouseExited

    private void btnBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscaActionPerformed
        if (txtPesquisa.getText().equals("")) {
            carregaTabela();
        } else if (cbTipoPesquisa.getSelectedItem().equals("Nome")) {
            buscaProdutoNome();
        } else if (cbTipoPesquisa.getSelectedItem().equals("Codigo")) {
            buscaProdutoCodigoInterno();
        } else {
            buscaProdutoCodigoBarra();
        }
    }//GEN-LAST:event_btnBuscaActionPerformed

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
            java.util.logging.Logger.getLogger(BASE_PROD.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BASE_PROD.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BASE_PROD.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BASE_PROD.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BASE_PROD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel PainelTabelaTitulos;
    private javax.swing.JButton btnAlterar;
    public javax.swing.JButton btnBusca;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JComboBox<String> cbTipoPesquisa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDadosEmpresa;
    private javax.swing.JLabel lblDatahora;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelPrincipal;
    public javax.swing.JTable tabela;
    public javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
