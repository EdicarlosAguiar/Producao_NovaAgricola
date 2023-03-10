/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.CONFUPDATEPROD;
import FormNotificacao.Confirmacao;
import FormNotificacao.JD_FATO_PROD;
import FormNotificacao.MSGA_PDV01;
import FormNotificacao.SelecaoInvalida;
import FormNotificacao.campoObrigatorio;
import FormNotificacao.exclusaoNaoAutorizada;
import FormNotificacao.produtoDuplicado;
import FormulariosConsultas.ConsultaCentroCusto;
import FormulariosConsultas.ConsultaEquipamentos;
import FormulariosConsultas.ConsultaProduto2;
import Model.ModelUsuario;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sun.javafx.geom.ConcentricShapePair;
import java.awt.CardLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.hadoop.hdfs.web.JsonUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class BASE_PRODUCAO extends javax.swing.JFrame {
    
    String op;
    float totalHectare;
    float hectare;
    String cultura;
    float estoque;
    float valor;
    float custoM;
    float custoTotal;
    float estoqueFinal;
    float valorFinal;
    String codCentroCusto, nomeCentroCusto;
    String codEquipamento, nomeEquipamento;
    
    public String getCodEquipamento() {
        return codEquipamento;
    }
    
    public void setCodEquipamento(String codEquipamento) {
        this.codEquipamento = codEquipamento;
    }
    
    public String getNomeEquipamento() {
        return nomeEquipamento;
    }
    
    public void setNomeEquipamento(String nomeEquipamento) {
        this.nomeEquipamento = nomeEquipamento;
    }
    
    public String getCodCentroCusto() {
        return codCentroCusto;
    }
    
    public void setCodCentroCusto(String codCentroCusto) {
        this.codCentroCusto = codCentroCusto;
    }
    
    public String getNomeCentroCusto() {
        return nomeCentroCusto;
    }
    
    public void setNomeCentroCusto(String nomeCentroCusto) {
        this.nomeCentroCusto = nomeCentroCusto;
    }
    
    Conexao conecta = new Conexao();
    
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());
    
    DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm:SS");
    String horaAtual = hora.format(LocalDateTime.now());
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String dataAtual = dtf.format(LocalDateTime.now());
    
    String reqSelecionada;
    
    public String getReqSelecionada() {
        return reqSelecionada;
    }
    
    public void setReqSelecionada(String reqSelecionada) {
        this.reqSelecionada = reqSelecionada;
    }
    
    ModelUsuario user = new ModelUsuario();
    static ArrayList<String> produtos = new ArrayList();
    static ArrayList<String> centro_custo = new ArrayList();
    static ArrayList<String> requisicao = new ArrayList();
    static ArrayList<String> opAgregada = new ArrayList();
    static ArrayList<String> Ops = new ArrayList();
    static ArrayList<String> equipamento = new ArrayList();
    
    String ordProdAgregadas;
    
    public BASE_PRODUCAO() {
        
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));
        
        initComponents();
        lblMsgExclusao.setVisible(false);
        carregaTabela();
        this.setExtendedState(MAXIMIZED_BOTH);
        configInicias();
        
        utilitario util = new utilitario();
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));
        lblDadosEmpresa.setText(util.getDadosEmpresaRodape());
        
        configIniciasForMOvimentacoes();
        carregaOP();
        btnConfirmar.setBackground(Color.WHITE);
        
    }
    
    public void GeraCodBarra() {
        
        Document documentoPDF = new Document(PageSize.A4, 5, 5, 5, 5);
        OutputStream outPutStream = null;
        
        PdfWriter writer;
        try {
            writer = PdfWriter.getInstance(documentoPDF, new FileOutputStream("C:\\Users\\Edicarlos\\Documents\\DOCUMENTOS EDICARLOS\\DOCUMENTOTEST.pdf"));
            PdfContentByte cb = writer.getDirectContent();
            // BarcodeEAN codeEAN = new BarcodeEAN();

            Barcode128 codeEAN = new Barcode128();
            
            codeEAN.setCodeType(Barcode128.CODE128);
            codeEAN.setCode("212131313131");
            Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BASE_PRODUCAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(BASE_PRODUCAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void configIniciasForMOvimentacoes() {
        Cores cor = new Cores();
        int maxHeaderHeight = 30;
        Dimension d = new Dimension(TabelaItens.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        
        TabelaItens.getTableHeader().setPreferredSize(d);
        tabelaOp.getTableHeader().setPreferredSize(d);
        
        tabelaOp.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabe??alho
        tabelaOp.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabe??alho

        tabelaOp.getTableHeader().setOpaque(false);
        tabelaOp.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));
        tabelaOp.setForeground(cor.getCorFonteDadosTabela());
        
        TabelaItens.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabe??alho
        TabelaItens.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabe??alho

        TabelaItens.getTableHeader().setOpaque(false);
        TabelaItens.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.NORMAL, 11));
        TabelaItens.setForeground(cor.getCorFonteDadosTabela());
        txtProduto.setBackground(Color.WHITE);
        
    }
    
    public void CorLinhaTabelaForMovimentacoes() {
        Cores cor = new Cores();
        
        TabelaItens.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
                    
                    TabelaItens.getColumnModel().getColumn(1).setCellRenderer(centro);//Item
                    TabelaItens.getColumnModel().getColumn(2).setCellRenderer(centro);//Codigo INterno
                    TabelaItens.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Produto
                    TabelaItens.getColumnModel().getColumn(4).setCellRenderer(centro);//Unidade
                    TabelaItens.getColumnModel().getColumn(5).setCellRenderer(esquerda);//qUANTIDADE
                    TabelaItens.getColumnModel().getColumn(6).setCellRenderer(direita);//Custo
                    TabelaItens.getColumnModel().getColumn(7).setCellRenderer(direita);//Custo

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
                    TabelaItens.getColumnModel().getColumn(1).setCellRenderer(centro);//Item
                    TabelaItens.getColumnModel().getColumn(2).setCellRenderer(centro);//Codigo INterno
                    TabelaItens.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Produto
                    TabelaItens.getColumnModel().getColumn(4).setCellRenderer(centro);//Unidade
                    TabelaItens.getColumnModel().getColumn(5).setCellRenderer(esquerda);//qUANTIDADE
                    TabelaItens.getColumnModel().getColumn(6).setCellRenderer(direita);//Custo
                    TabelaItens.getColumnModel().getColumn(7).setCellRenderer(direita);//Custo

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;
                    
                }
                
                return label;
            }
            
        });
        
    }
    
    public void configInicias() {
        
        Cores cor = new Cores();
        //  tabela.setSelectionBackground(cor.getCorLinhaSelecionada());
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);
        
    }
    
    public void configuraTabelaItensRequisicao() {
        
        Cores cor = new Cores();
        
        TabelaItens.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
                    
                    TabelaItens.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(5).setCellRenderer(direita);
                    TabelaItens.getColumnModel().getColumn(6).setCellRenderer(direita);
                    
                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    
                } else {
                    c = cor.getCorLinhaParTabela();
                    
                    label.setBackground(c);
                    
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    TabelaItens.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    TabelaItens.getColumnModel().getColumn(5).setCellRenderer(direita);
                    TabelaItens.getColumnModel().getColumn(6).setCellRenderer(direita);
                    
                    esquerda.setBackground(c);
                    direita.setBackground(c);
                    
                }

                // 
                return label;
            }
            
        });
        
    }
    
    public void configuraTabelaOp() {
        
        Cores cor = new Cores();
        
        tabelaOp.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
                    
                    tabelaOp.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabelaOp.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabelaOp.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    
                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    
                } else {
                    c = cor.getCorLinhaParTabela();
                    
                    label.setBackground(c);
                    
                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    tabelaOp.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabelaOp.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabelaOp.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    
                    esquerda.setBackground(c);
                    direita.setBackground(c);
                    
                }

                // 
                return label;
            }
            
        });
        
    }
    
    public void configuraTabelaIBrowser() {
        
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
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
                    
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
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
                    
                    esquerda.setBackground(c);
                    direita.setBackground(c);
                    
                }

                // 
                return label;
            }
            
        });
        
    }
    
    public void carregaTabela() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, cor.getSizeFonteHenderTable()));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        modelo.setNumRows(0);
        
        try {
            
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select *from producao order by id desc limit 200");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                //    JOptionPane.showConfirmDialog(null, "Come??ou");
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float quantidade = rs.getFloat("quantidade");
                String quantidadeFormatada = df.format(quantidade);
                float cachos = rs.getFloat("cacho");
                String cachoFormatada = df.format(cachos);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(2),//Data
                        rs.getString("documento"),//Documento
                        rs.getString("codProduto"),//Codugo
                        rs.getString("produto"),//Produto
                        rs.getString("und"),//Unidade
                        quantidadeFormatada,//Unidade
                        rs.getString("op"),//Ordem de Producao
                        cachoFormatada,//Ordem de Producao
                    });
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        
        configuraTabelaIBrowser();
    }
    
    public void somaCusto() {
        
        double somaTotal = 0;
        for (int i = 0; i < TabelaItens.getRowCount(); i++) {
            somaTotal += Double.parseDouble(TabelaItens.getValueAt(i, 5).toString().toString().replace(".", "").replaceAll(",", "."));
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(somaTotal);
        lblProducaoTotal.setText(totalFormatado);
        
    }
    
    public void somaHectare() {
        totalHectare = 0;
        double somaTotal = 0;
        for (int i = 0; i < tabelaOp.getRowCount(); i++) {
            totalHectare += Double.parseDouble(tabelaOp.getValueAt(i, 2).toString().toString());
        }
        lblTotalHectares.setText(String.valueOf(totalHectare));
    }
    
    public void carregaDadosNoArray() {
        
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        
        requisicao.clear();
        
        try {
            
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select data,documento,tipoMov,op_agregada,codProd,produto,"
                    + "unidade,sum(quantidade) as quantidade from requisicao where documento =?"
                    + "group by codProd ");
            
            pst.setString(1, reqSelecionada);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                String quantidadeFormatada = df.format(rs.getFloat("quantidade"));

                //Preenchendo a Lista de Produtos
                requisicao.add(rs.getString("data"));//Data a requisicao
                requisicao.add(rs.getString("documento")); //Documento
                requisicao.add(rs.getString("tipoMov")); //Tipo da Requisicao
                requisicao.add(rs.getString("op_agregada")); //Ordem de Produto
                requisicao.add(rs.getString("codProd")); //Codigo
                requisicao.add(rs.getString("produto")); //Produto
                requisicao.add(rs.getString("unidade")); //Unidade
                requisicao.add(quantidadeFormatada); //Quantidade

            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }
        //   JOptionPane.showMessageDialog(this, produtos);
    }
    
    public void carregaArrayProdutos() {
        
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        
        produtos.clear();
        
        try {
            
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select codigo, nome, und, atual,valor, custoMedio "
                    + "from tb_produtos where codCat = '10' order by codigo");
            rs = pst.executeQuery();
            
            while (rs.next()) {

                //Preenchendo a Lista de Produtos
                produtos.add(rs.getString("codigo"));//Data a requisicao
                produtos.add(rs.getString("nome")); //Documento
                produtos.add(rs.getString("und")); //Tipo da Requisicao
                produtos.add(rs.getString("atual")); //Ordem de Produto
                produtos.add(rs.getString("valor")); //Codigo
                produtos.add(rs.getString("custoMedio")); //Produto

            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }
        //   JOptionPane.showMessageDialog(this, produtos);
    }
    
    public void carregaArrayCentroCusto() {
        
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        
        centro_custo.clear();
        
        try {
            
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select CODIGO, DESCRICAO from centro_custo");
            rs = pst.executeQuery();
            
            while (rs.next()) {

                //Preenchendo a Lista de Produtos
                centro_custo.add(rs.getString("CODIGO"));//Data a requisicao
                centro_custo.add(rs.getString("DESCRICAO")); //Document

            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar array " + e);
        }
        //   JOptionPane.showMessageDialog(this, produtos);
    }
    
    public void excluirRegistro() {
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst;
            
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("delete from producao where documento=?");
            pst.setString(1, txtDocumento.getText());
            pst.executeUpdate();
            
            pst.close();
            
            carregaDadosNoArray();
            carregaTabela();
            
        } catch (Exception e) {
        }
        Confirmacao conf = new Confirmacao(this, true);
        conf.textoExclusaoProducao();
        conf.setVisible(true);
        
    }
    
    public void parametrosExcluir() {
        lblTituloForm.setText("Produ????o - Excluir");
        painelCabecalho.setEnabled(false);
        
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
        PainelBrowser = new javax.swing.JPanel();
        PainelTabelaTitulos = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtPesquisa = new javax.swing.JTextField();
        btnBusca = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btnIncluir = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        lblDatahora = new javax.swing.JLabel();
        lblDadosEmpresa = new javax.swing.JLabel();
        PainelForm = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        painelCabecalho = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        cbOp = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaOp = new javax.swing.JTable();
        btnInserirOp = new javax.swing.JButton();
        btnRemoverOp = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbSemana = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnAddProduto = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        txtProduto = new javax.swing.JTextField();
        txtUnidade = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtCacho = new javax.swing.JTextField();
        txtData = new javax.swing.JFormattedTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        TabelaItens = new javax.swing.JTable();
        painelTituloForm = new javax.swing.JPanel();
        lblTituloForm = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblMsgExclusao = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnConfirmar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblTotalHectares = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblProducaoHa = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblProducaoTotal = new javax.swing.JLabel();

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
        setTitle("Requisi????o");

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        painelCorpo.setLayout(new java.awt.CardLayout());

        PainelBrowser.setBackground(new java.awt.Color(255, 255, 255));
        PainelBrowser.setPreferredSize(new java.awt.Dimension(900, 800));

        PainelTabelaTitulos.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ordem Prod", "Codigo" }));

        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisa.setForeground(new java.awt.Color(102, 102, 102));

        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tabela.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Data Colheita", "Documento", "Codigo", "Produto", "Und", "Quantidae", "Odem de Prod", "Cachos", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(239, 236, 236));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(1);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabela.getColumnModel().getColumn(0).setMaxWidth(1);
            tabela.getColumnModel().getColumn(4).setMinWidth(350);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(350);
            tabela.getColumnModel().getColumn(4).setMaxWidth(350);
            tabela.getColumnModel().getColumn(5).setMinWidth(50);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(5).setMaxWidth(50);
            tabela.getColumnModel().getColumn(9).setMinWidth(3);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(9).setMaxWidth(3);
        }
        tabela.getAccessibleContext().setAccessibleParent(painelCorpo);

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
        btnVisualizar.setEnabled(false);
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

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/print.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setAlignmentY(0.0F);
        btnImprimir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setEnabled(false);
        btnImprimir.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnImprimir.setMaximumSize(new java.awt.Dimension(32, 50));
        btnImprimir.setMinimumSize(new java.awt.Dimension(32, 50));
        btnImprimir.setOpaque(true);
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImprimirMouseExited(evt);
            }
        });
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel2.add(btnImprimir);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("Lan??amentos de Produ????o");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblDatahora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblDatahora.setForeground(new java.awt.Color(38, 85, 100));
        lblDatahora.setText("Data e Hora:");

        lblDadosEmpresa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblDadosEmpresa.setForeground(new java.awt.Color(38, 85, 100));
        lblDadosEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDadosEmpresa.setText(" Edicarlos Aguiar de Sousa - ME - Todos os direitos reservados");

        javax.swing.GroupLayout PainelTabelaTitulosLayout = new javax.swing.GroupLayout(PainelTabelaTitulos);
        PainelTabelaTitulos.setLayout(PainelTabelaTitulosLayout);
        PainelTabelaTitulosLayout.setHorizontalGroup(
            PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                        .addComponent(lblDatahora, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                        .addComponent(lblDadosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
            .addComponent(jScrollPane1)
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
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatahora)
                    .addComponent(lblDadosEmpresa))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout PainelBrowserLayout = new javax.swing.GroupLayout(PainelBrowser);
        PainelBrowser.setLayout(PainelBrowserLayout);
        PainelBrowserLayout.setHorizontalGroup(
            PainelBrowserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelTabelaTitulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PainelBrowserLayout.setVerticalGroup(
            PainelBrowserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelTabelaTitulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        painelCorpo.add(PainelBrowser, "CardBase");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Ordem de Produ????o", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(51, 51, 51))); // NOI18N

        tabelaOp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indice", "Ord Producao", "Hectare", "Cultura"
            }
        ));
        tabelaOp.setFillsViewportHeight(true);
        tabelaOp.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPane3.setViewportView(tabelaOp);
        if (tabelaOp.getColumnModel().getColumnCount() > 0) {
            tabelaOp.getColumnModel().getColumn(0).setMinWidth(1);
            tabelaOp.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabelaOp.getColumnModel().getColumn(0).setMaxWidth(1);
            tabelaOp.getColumnModel().getColumn(3).setMinWidth(120);
            tabelaOp.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabelaOp.getColumnModel().getColumn(3).setMaxWidth(120);
        }

        btnInserirOp.setText("Inserir");
        btnInserirOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirOpActionPerformed(evt);
            }
        });

        btnRemoverOp.setText("Remover");
        btnRemoverOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverOpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbOp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnInserirOp, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoverOp, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbOp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInserirOp)
                    .addComponent(btnRemoverOp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Emiss??o:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Documento:");

        txtDocumento.setForeground(new java.awt.Color(51, 51, 51));
        txtDocumento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDocumento.setEnabled(false);
        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Semana:");

        cbSemana.setForeground(new java.awt.Color(51, 51, 51));
        cbSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01_2023", "02_2023", "03_2023", "04_2023", "05_2023", "06_2023", "07_2023", "08_2023", "09_2023", "10_2023", "11_2023", "12_2023", "10_2023", "11_2023", "12_2023", "13_2023", "14_2023", "15_2023", "16_2023", "17_2023", "18_2023", "19_2023", "20_2023", "21_2023", "22_2023", "23_2023", "24_2023", "25_2023", "26_2023", "27_2023", "28_2023", "29_2023", "30_2023", "31_2023", "32_2023", "33_2023", "34_2023", "35_2023", "36_2023", "37_2023", "38_2023", "39_2023", "40_2023", "41_2023", "42_2023", "43_2023", "44_2023", "45_2023", "46_2023", "47_2023", "48_2023", "49_2023", "50_2023", "51_2023", "52_2023", "53_2023", " ", " ", " " }));
        cbSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSemanaActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), "Produtos"));

        btnAddProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Create.png"))); // NOI18N
        btnAddProduto.setText("Adicionar");
        btnAddProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProdutoActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Delete.png"))); // NOI18N
        jButton6.setText("Remover");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txtCodigo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(51, 51, 51));
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        txtProduto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtProduto.setForeground(new java.awt.Color(51, 51, 51));
        txtProduto.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtProduto.setEnabled(false);

        txtUnidade.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUnidade.setForeground(new java.awt.Color(51, 51, 51));
        txtUnidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUnidadeFocusLost(evt);
            }
        });
        txtUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUnidadeActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Codigo:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Descri????o do Produto:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Und");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Zoom.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        txtQuantidade.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtQuantidade.setForeground(new java.awt.Color(51, 51, 51));
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusLost(evt);
            }
        });
        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Produ????o:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Cachos:");

        txtCacho.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCacho.setForeground(new java.awt.Color(51, 51, 51));
        txtCacho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCachoFocusLost(evt);
            }
        });
        txtCacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCachoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(txtCodigo)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(txtCacho, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(txtProduto)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUnidade))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQuantidade))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCacho)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddProduto)
                    .addComponent(jButton6))
                .addGap(13, 13, 13))
        );

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelCabecalhoLayout = new javax.swing.GroupLayout(painelCabecalho);
        painelCabecalho.setLayout(painelCabecalhoLayout);
        painelCabecalhoLayout.setHorizontalGroup(
            painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCabecalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCabecalhoLayout.createSequentialGroup()
                        .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCabecalhoLayout.createSequentialGroup()
                                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelCabecalhoLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabel7))
                                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(painelCabecalhoLayout.createSequentialGroup()
                        .addGap(551, 551, 551)
                        .addComponent(jScrollPane2)
                        .addGap(262, 262, 262))))
        );
        painelCabecalhoLayout.setVerticalGroup(
            painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCabecalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelCabecalhoLayout.createSequentialGroup()
                        .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(painelCabecalhoLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(30, 30, 30))
                            .addGroup(painelCabecalhoLayout.createSequentialGroup()
                                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5))
        );

        TabelaItens.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        TabelaItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ITEM", "CODIGO", "NOME DO PRODUTO", "UNID M", "QUANTIDADE", "CACHOS", "Title 8"
            }
        ));
        TabelaItens.setFillsViewportHeight(true);
        TabelaItens.setGridColor(new java.awt.Color(204, 204, 204));
        TabelaItens.setRowHeight(25);
        jScrollPane4.setViewportView(TabelaItens);
        if (TabelaItens.getColumnModel().getColumnCount() > 0) {
            TabelaItens.getColumnModel().getColumn(0).setMinWidth(1);
            TabelaItens.getColumnModel().getColumn(0).setPreferredWidth(1);
            TabelaItens.getColumnModel().getColumn(0).setMaxWidth(1);
            TabelaItens.getColumnModel().getColumn(1).setMinWidth(50);
            TabelaItens.getColumnModel().getColumn(1).setPreferredWidth(50);
            TabelaItens.getColumnModel().getColumn(1).setMaxWidth(50);
            TabelaItens.getColumnModel().getColumn(7).setMinWidth(3);
            TabelaItens.getColumnModel().getColumn(7).setPreferredWidth(3);
            TabelaItens.getColumnModel().getColumn(7).setMaxWidth(3);
        }

        painelTituloForm.setBackground(new java.awt.Color(94, 110, 110));
        painelTituloForm.setForeground(new java.awt.Color(242, 242, 242));
        painelTituloForm.setName(""); // NOI18N
        painelTituloForm.setPreferredSize(new java.awt.Dimension(0, 3));

        lblTituloForm.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTituloForm.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloForm.setText("Produ????o - Incluir");

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

        lblMsgExclusao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMsgExclusao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Error.png"))); // NOI18N
        lblMsgExclusao.setText("Mensagem exclusao..");

        javax.swing.GroupLayout painelTituloFormLayout = new javax.swing.GroupLayout(painelTituloForm);
        painelTituloForm.setLayout(painelTituloFormLayout);
        painelTituloFormLayout.setHorizontalGroup(
            painelTituloFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloForm, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(lblMsgExclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelTituloFormLayout.setVerticalGroup(
            painelTituloFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloFormLayout.createSequentialGroup()
                .addGroup(painelTituloFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTituloForm, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMsgExclusao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        btnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(51, 51, 51));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnConfirmar.setText("Finalizar");
        btnConfirmar.setActionCommand("");
        btnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
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

        jLabel9.setText("Total de hectares:");

        lblTotalHectares.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTotalHectares.setForeground(new java.awt.Color(255, 0, 0));
        lblTotalHectares.setText("0,00");

        jLabel12.setText("Produ????o por H??:");

        lblProducaoHa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProducaoHa.setForeground(new java.awt.Color(255, 0, 0));
        lblProducaoHa.setText("0,00");

        jLabel13.setText("Produ????o Total:");

        lblProducaoTotal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblProducaoTotal.setForeground(new java.awt.Color(255, 0, 0));
        lblProducaoTotal.setText("0,00");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(lblTotalHectares, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblProducaoHa, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblProducaoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lblProducaoTotal))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblTotalHectares)
                            .addComponent(jLabel12)
                            .addComponent(lblProducaoHa)))
                    .addComponent(btnConfirmar))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
            .addComponent(painelTituloForm, javax.swing.GroupLayout.DEFAULT_SIZE, 1145, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(painelTituloForm, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout PainelFormLayout = new javax.swing.GroupLayout(PainelForm);
        PainelForm.setLayout(PainelFormLayout);
        PainelFormLayout.setHorizontalGroup(
            PainelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PainelFormLayout.setVerticalGroup(
            PainelFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        painelCorpo.add(PainelForm, "CardLancamentos");

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
        reqSelecionada = ((String) tabela.getValueAt(linha, 2).toString());

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();
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
        /*        JD_REQ req = new JD_REQ(this, true);
        req.setVisible(true);*/
        
        CardLayout cl = (CardLayout) painelCorpo.getLayout();
        cl.show(painelCorpo, "CardLancamentos");
        
        geraNumeroProducao();
        ConsultaProduto2 prod = new ConsultaProduto2(this, true);
        prod.carregaArrayProdutos();
        
        painelTituloForm.setBackground(new Color(94, 110, 110));
        lblTituloForm.setForeground(new Color(255, 255, 255));
        lblTituloForm.setText("Produ????o - Incluir");
        lblMsgExclusao.setVisible(false);
        
        txtData.requestFocus();
        txtData.setText(dataAtual);
        
        if (txtDocumento.getText().equals("")) {
            txtDocumento.setText("000001");
        } else {
            
        }
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnIncluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnIncluirFocusGained
//        carregaTabela();

    }//GEN-LAST:event_btnIncluirFocusGained

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        if (reqSelecionada == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
//            NOVO_CADASTRO_PRODUTO_01 prod = new NOVO_CADASTRO_PRODUTO_01();
            //      prod.pegaProduto(this);
            //  prod.parametrosVisualizar();
            // prod.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (reqSelecionada == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            txtDocumento.setText(reqSelecionada);
            CardLayout cl = (CardLayout) painelCorpo.getLayout();
            cl.show(painelCorpo, "CardLancamentos");
            buscaDadosRequisicao();
            parametrosExcluir();
            configuraTabelaItensRequisicao();
            somaCusto();
            somaHectare();
            painelTituloForm.setBackground(new Color(255, 255, 204));
            lblTituloForm.setForeground(new Color(51, 51, 51));
            lblMsgExclusao.setVisible(true);
            lblMsgExclusao.setText(" Aten????o...! Essa opera????o n??o poder?? ser desfeita ap??s a confirma????o!");
            lblMsgExclusao.setForeground(new Color(51, 51, 51));
            
        }
        tabela.clearSelection();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnVisualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseEntered
        btnVisualizar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVisualizarMouseEntered

    private void btnVoltarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseEntered
        btnVoltar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVoltarMouseEntered

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
        Conexao conn = new Conexao();
        Cores cor = new Cores();
        
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
            
            while (rs.next()) {
                //    JOptionPane.showConfirmDialog(null, "Come??ou");

                DecimalFormat df = new DecimalFormat("#,##0.00");
                Double preco = Double.parseDouble(rs.getString(15).replace(".", "").replaceAll(",", "."));
                String precoVenda = df.format(preco);
                String status = rs.getString(8);
                
                if (status == "N") {
                    status = "Bloqueado";
                } else {
                    status = "Ativo";
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
                        rs.getString(11),//Codigo de Barras
                        status,//Status
                    });
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        configuraTabelaIBrowser();

    }//GEN-LAST:event_btnBuscaActionPerformed

    //Metodos da Tela de Movimenta????es
    public void addOp() {
        
        DefaultTableModel model = (DefaultTableModel) tabelaOp.getModel();
        model.addRow(new Object[]{
            model.getRowCount() + 1,
            cbOp.getSelectedItem(),
            hectare,
            cultura,}
        );
        configuraTabelaOp();
    }
    
    public void carregaOP() {
        
        try {
            
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select ordem_pro from plantio");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                
                cbOp.addItem(rs.getString("ordem_pro"));
                
            }
            
            conecta.getConexao().close();
            
        } catch (Exception ex) {
            
        }
    }
    
    public void addProduto() {
        String cacho;
        if (txtCacho.getText().equals("")) {
            cacho = "0,00";
        } else {
            cacho = txtCacho.getText();
        }
        
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String custoFormatado = df.format(custoTotal);
        
        DefaultTableModel model = (DefaultTableModel) TabelaItens.getModel();
        model.addRow(new Object[]{
            TabelaItens.getRowCount() + 1,
            TabelaItens.getRowCount() + 1,
            txtCodigo.getText(),
            txtProduto.getText(),
            txtUnidade.getText(),
            txtQuantidade.getText(),
            cacho,}
        );
        
        configuraTabelaItensRequisicao();
        txtCodigo.setText("");
        txtProduto.setText("");
        txtUnidade.setText("");
        txtQuantidade.setText("");
        txtCacho.setText("");
        txtCodigo.requestFocus();
        
    }
    
    public void buscaDadosRequisicao() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();

        /*  tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 10));*/
        DefaultTableModel modelo = (DefaultTableModel) TabelaItens.getModel();
        
        modelo.setNumRows(0);
        
        try {
            
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select sum(quantidade) as qtde, "
                    + "dataColheita, documento, codProduto, produto, und, semana from producao where documento =? "
                    + "group by codProduto");
            pst.setString(1, reqSelecionada);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                txtData.setText(rs.getString(2));
                txtDocumento.setText(rs.getString(3));
                cbSemana.setSelectedItem("semana");
                
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float quantidade = rs.getFloat("qtde");
                String quantidadeFormatada = df.format(quantidade);
                
                {

                    //Preenche tabelas com Produtos
                    modelo.addRow(new Object[]{
                        linha,
                        linha,
                        rs.getString("codProduto"),//Codugo
                        rs.getString("produto"),//Produto
                        rs.getString("und"),//Unidade
                        quantidadeFormatada,//Quantidade
                    });
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados" + e);
        }
        
        configuraTabelaIBrowser();
    }
    
    public void buscaProduto() {
        int totalItensArray = produtos.size();
        int indice = 0;
        boolean busca = false;
        while (busca == false) {
            //   JOptionPane.showMessageDialog(this, "Indice pesquisado " + produtos.get(indice));
            //   JOptionPane.showMessageDialog(this, "Total produso no Array " + totalItensArray);
            //   JOptionPane.showMessageDialog(this, "Total tentativas " + indice);

            if (txtCodigo.getText().equals(produtos.get(indice))) {
                
                txtCodigo.setText(produtos.get(indice));
                txtProduto.setText(produtos.get(indice + 1));
                
                estoque = Float.parseFloat(produtos.get(indice + 3));
                valor = Float.parseFloat(produtos.get(indice + 4));
                custoM = Float.parseFloat(produtos.get(indice + 5));
                busca = true;
                
            } else if (totalItensArray - 4 < indice) {
                busca = true;
            } else {
                indice++;
            }
        }
    }
    
    public void geraNumeroProducao() {
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select documento from producao");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int numero = rs.getInt("documento") + 1;
                
                if (numero < 10) {
                    String numetoReq = "00000" + numero;
                    txtDocumento.setText(numetoReq);
                } else if (numero < 100) {
                    String numetoReq = "0000" + numero;
                    txtDocumento.setText(numetoReq);
                    
                } else if (numero < 1000) {
                    String numetoReq = "000" + numero;
                    txtDocumento.setText(numetoReq);
                    
                } else {
                    String numetoReq = "00" + numero;
                    txtDocumento.setText(numetoReq);
                    
                }
                
            }
            
            conn.getConexao().close();
            pst.close();
            rs.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }
    
    public void buscaHectare() {
        
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select hectare,cultura from plantio where ordem_pro = ?");
            pst.setString(1, (String) cbOp.getSelectedItem());
            rs = pst.executeQuery();
            
            while (rs.next()) {
                hectare = rs.getFloat("hectare");
                cultura = rs.getString("cultura");
                
            }
            
            conn.getConexao().close();
            pst.close();
            rs.close();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }
    
    public void AgregarOps() {
        
        int totalOp = tabelaOp.getRowCount();
        int incremente_op = 1;
        
        while (totalOp >= incremente_op) {
            
            opAgregada.add((String) tabelaOp.getValueAt(incremente_op - 1, 1)); //Cod Produto

            incremente_op++;
            
        }
        ordProdAgregadas = opAgregada.toString().replaceAll(",", " - ");
        
    }
    
    public void salvar() {
        AgregarOps();
        Conexao conn = new Conexao();
        try {
            int totalOp = tabelaOp.getRowCount();
            int incremente_op = 1;
            while (totalOp >= incremente_op) {
                int totalProdutos = TabelaItens.getRowCount();
                String op = (String) tabelaOp.getValueAt(incremente_op - 1, 1); //Cod Produto
                incremente_op = incremente_op + 1;
                int incremente_linda = 1;
                
                while (totalProdutos >= incremente_linda) {
                    PreparedStatement pst = conn.getConexao().prepareStatement("insert into producao(dataColheita,documento,"
                            + "codProduto,produto,und,quantidade,op,semana,CACHO)VALUES(?,?,?,?,?,?,?,?,?)");
                    
                    pst.setString(1, txtData.getText());//Data
                    pst.setString(2, txtDocumento.getText()); // Documento
                    pst.setString(3, TabelaItens.getValueAt(incremente_linda - 1, 2).toString()); //Cod Produto
                    pst.setString(4, TabelaItens.getValueAt(incremente_linda - 1, 3).toString()); //Nome Produto
                    pst.setString(5, TabelaItens.getValueAt(incremente_linda - 1, 4).toString()); //Unidade
                    pst.setFloat(6, Float.parseFloat(TabelaItens.getValueAt(incremente_linda - 1, 5).toString().replace(".",
                            "").replaceAll(",", "."))); //Qauntidade; 
                    pst.setString(7, op);//Ordem de Produ??ao
                    pst.setString(8, (String) cbSemana.getSelectedItem());//Semana
                    pst.setFloat(9, Float.parseFloat(TabelaItens.getValueAt(incremente_linda - 1, 6).toString().replace(".",
                            "").replaceAll(",", "."))); //Qauntidade; 

                    pst.execute();
                    incremente_linda++;
                    pst.close();
                    
                }
                
            }
            Confirmacao conf = new Confirmacao(this, true);
            conf.setVisible(true);
            carregaTabela();
            limpaTela();
            geraNumeroProducao();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processr requisi????o " + e);
            
        }
    }
    
    public void limpaTela() {
        
        txtDocumento.setText("");
        txtCodigo.setText("");
        txtProduto.setText("");
        txtUnidade.setText("");
        geraNumeroProducao();
        cbSemana.requestFocus();
        lblTotalHectares.setText("0.0");
        
        lblProducaoHa.setText("R$ 0,00");
        lblProducaoTotal.setText("R$ 0,00");
        
        DefaultTableModel Tabela = (DefaultTableModel) this.TabelaItens.getModel();
        DefaultTableModel Tabela2 = (DefaultTableModel) this.tabelaOp.getModel();
        //Limpeza Tabela de Produtos
        int lin = Tabela.getRowCount();
        //  JOptionPane.showMessageDialog(this, lin);
        for (int i = 1; lin >= i; i++) {
            Tabela.removeRow(0);
            
        }
        
        int lin2 = Tabela2.getRowCount();
        //  JOptionPane.showMessageDialog(this, lin2);
        for (int i2 = 1; lin2 >= i2; i2++) {
            Tabela2.removeRow(0);
            
        }
        
    }
    

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        if (txtCodigo.getText().equals("")) {
            
        } else {
            buscaProduto();
        }
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        txtUnidade.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            txtCodigo.setText("");
            ConsultaProduto2 consulta = new ConsultaProduto2(this, true);
            consulta.setVisible(true);
            String codigo = consulta.getCodigo();
            String nome = consulta.getNomeProd();
            String codProd = consulta.getCodigo();
            String und = consulta.getUnidade();
            txtCodigo.setText(codigo);
            txtProduto.setText(nome);
            txtUnidade.setText(und);
            txtQuantidade.requestFocus();
            
        } else {
            
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void btnAddProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProdutoActionPerformed
        
        addProduto();
        somaCusto();
        

    }//GEN-LAST:event_btnAddProdutoActionPerformed

    private void txtUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUnidadeActionPerformed
        btnAddProduto.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadeActionPerformed

    private void btnInserirOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirOpActionPerformed
        buscaHectare();
        
        addOp();
        somaHectare();
        
        if (lblProducaoTotal.getText().equals("0,00")) {
            
        } else {
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            float totalHa = Float.parseFloat(lblTotalHectares.getText());
            float custoTotal = Float.parseFloat(lblProducaoTotal.getText().replace(".", "").replaceAll(",", "."));
            float custoHa = custoTotal / totalHa;
            String custoHaFormatado = df.format(custoHa);
            lblProducaoHa.setText(custoHaFormatado);
            
        }
        

    }//GEN-LAST:event_btnInserirOpActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        CardLayout cl = (CardLayout) painelCorpo.getLayout();
        cl.show(painelCorpo, "CardBase");
        limpaTela();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void btnConfirmarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusGained

    }//GEN-LAST:event_btnConfirmarFocusGained

    private void btnConfirmarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusLost

    }//GEN-LAST:event_btnConfirmarFocusLost

    private void btnConfirmarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseEntered

    }//GEN-LAST:event_btnConfirmarMouseEntered

    private void btnConfirmarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseExited
        btnConfirmar.setBackground(new java.awt.Color(255, 255, 255));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarMouseExited

    private void btnConfirmarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMousePressed
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarMousePressed

    private void btnConfirmarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfirmarMouseReleased
        btnConfirmar.setBackground(new java.awt.Color(255, 255, 255));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnConfirmarMouseReleased

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        
        switch (lblTituloForm.getText()) {
            case "Produ????o - Incluir":
                salvar();
                
                break;
            case "Cadastro de Produto - Alterar":
                
                break;
            case "Produ????o - Excluir":
                
                int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclus??o da produ????o " + txtDocumento.getText() + "?", "AGUITECH", JOptionPane.YES_NO_OPTION);
                if (resp1 == 0) {
                    excluirRegistro();
                    CardLayout cl = (CardLayout) painelCorpo.getLayout();
                    cl.show(painelCorpo, "CardBase");
                    limpaTela();
                    
                } else {
                    
                }
                
                break;
            
        }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void txtUnidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUnidadeFocusLost
        

    }//GEN-LAST:event_txtUnidadeFocusLost

    private void btnRemoverOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverOpActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tabelaOp.getModel();
        dtm.removeRow(tabelaOp.getSelectedRow());
        somaHectare();
        somaCusto();
        
        if (lblProducaoHa.getText().equals("R$ 0,00") || lblTotalHectares.getText().equals("0.0")) {
            lblProducaoTotal.setText("R$ 0,00");
            
        } else {
            
            DecimalFormat df = new DecimalFormat("#,##0.00");
            float totalHa = Float.parseFloat(lblTotalHectares.getText());
            float custoTotal = Float.parseFloat(lblProducaoHa.getText().replace("R$", "").replace(".", "").replaceAll(",", "."));
            float custoHa = custoTotal / totalHa;
            String custoHaFormatado = df.format(custoHa);
            lblProducaoTotal.setText(custoHaFormatado);
            
        }

    }//GEN-LAST:event_btnRemoverOpActionPerformed

    private void btnImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirMouseEntered

    private void btnImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirMouseExited

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        if (reqSelecionada == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            carregaDadosNoArray();
            
            Document documentoPDF = new Document();
            OutputStream outPutStream = null;
            documentoPDF.setMargins(15, 0, 15, 0);
            //Fonte cabecalho tabela
            Font fontPadrao = new Font();
            fontPadrao.setSize(8);
            fontPadrao.setStyle(0);
            fontPadrao.setFamily("Arial");
            fontPadrao.setColor(Color.BLACK);
            
            try {

                //Local onde o arquivo ser?? salvo
                PdfWriter.getInstance(documentoPDF, new FileOutputStream("C:\\print\\RelReq.pdf"));
                
                Image logo = Image.getInstance("C:\\Users\\edica\\Documents\\NetBeansProjects\\NOVAAGRICOLA_02\\src\\Imagens2\\logoNovaAgricola.png");
                logo.scaleToFit(48, 48);

                //Abrindo o documento
                //  documentoPDF.newPage();
                documentoPDF.open();
                
                documentoPDF.setPageSize(PageSize.A5);
                documentoPDF.addTitle("AguiTech - Relatorio de Produtos");

                //Fonte cabecalho tabela
                Font fontTituloTabela = new Font();
                fontTituloTabela.setSize(8);
                fontTituloTabela.setStyle(1);
                fontTituloTabela.setFamily("Arial");
                fontTituloTabela.setColor(Color.BLACK);

                //Fonte cabecalho tabela
                Font fontOp = new Font();
                fontOp.setSize(6);
                fontOp.setStyle(1);
                fontOp.setFamily("Arial");
                fontOp.setColor(Color.BLACK);

                // Tabela para o cabecalho do forulario
                Font tituLoDocumento = new Font();
                tituLoDocumento.setSize(12);
                tituLoDocumento.setStyle(1);
                tituLoDocumento.setColor(Color.black);

                //Cabechalho Primeira Via
                PdfPTable cabeca = new PdfPTable(4);
                PdfPCell empresa = new PdfPCell(logo);
                PdfPCell titulo = new PdfPCell(new Paragraph("Requisi????o ao Almoxarifado", tituLoDocumento));
                PdfPCell Doc = new PdfPCell(new Paragraph("N?? Doc: " + "\n" + "Emiss??o: ", fontTituloTabela));
                PdfPCell Data = new PdfPCell(new Paragraph(requisicao.get(1) + "\n" + requisicao.get(0), fontPadrao));

                //Cabechalho segunda Via
                PdfPTable cabecaSegundaVia = new PdfPTable(4);
                PdfPCell empresaSegundaVia = new PdfPCell(logo);
                PdfPCell tituloSegundaVia = new PdfPCell(new Paragraph("Requisi????o ao Almoxarifado", tituLoDocumento));
                PdfPCell DocSegundaVia = new PdfPCell(new Paragraph("N?? Doc: " + "\n" + "Emiss??o: ", fontTituloTabela));
                PdfPCell DataSegundaVia = new PdfPCell(new Paragraph(requisicao.get(1) + "\n" + requisicao.get(0), fontPadrao));;

                //Tabela / Sessao para Ordem de Producao, Data, Usuario
                PdfPTable dadosMestres = new PdfPTable(2);
                
                PdfPCell rotuloOp = new PdfPCell(new Paragraph("Ordem de Produ????o:", fontPadrao));
                PdfPCell op = new PdfPCell(new Paragraph(requisicao.get(3), fontOp));
                PdfPCell rotuloTipoMov = new PdfPCell(new Paragraph("Tipo da Requisi????o:", fontPadrao));
                PdfPCell tipomov = new PdfPCell(new Paragraph(requisicao.get(2), fontTituloTabela));
                
                rotuloOp.setBorder(PdfCell.NO_BORDER);
                op.setBorder(PdfCell.NO_BORDER);
                rotuloOp.setHorizontalAlignment(0);
                op.setHorizontalAlignment(0);
                rotuloTipoMov.setBorder(PdfCell.NO_BORDER);
                tipomov.setBorder(PdfCell.NO_BORDER);
                rotuloTipoMov.setHorizontalAlignment(0);
                tipomov.setHorizontalAlignment(0);
                
                dadosMestres.addCell(rotuloOp);
                dadosMestres.addCell(rotuloTipoMov);
                dadosMestres.addCell(op);
                dadosMestres.addCell(tipomov);
                
                PdfPTable usuario_hora = new PdfPTable(2);
                PdfPTable usuario_horaSegundaVia = new PdfPTable(2);
                
                PdfPCell rotuloUser = new PdfPCell(new Paragraph("\n" + "Impresso por:", fontPadrao));
                PdfPCell usuario = new PdfPCell(new Paragraph(user.getUsuarioLogado() + " | " + horaAtual, fontTituloTabela));
                PdfPCell via = new PdfPCell(new Paragraph("\n" + "1?? Via", fontPadrao));
                PdfPCell via2 = new PdfPCell(new Paragraph("\n" + "2?? Via", fontPadrao));
                PdfPCell destVia = new PdfPCell(new Paragraph("Almoxarifado", fontTituloTabela));
                PdfPCell destVia2 = new PdfPCell(new Paragraph("Solicitante", fontTituloTabela));
                
                rotuloUser.setBorder(PdfCell.NO_BORDER);
                rotuloUser.setHorizontalAlignment(0);
                usuario.setBorder(PdfCell.NO_BORDER);
                usuario.setHorizontalAlignment(0);
                
                via.setBorder(PdfCell.NO_BORDER);
                via.setHorizontalAlignment(2);
                destVia.setBorder(PdfCell.NO_BORDER);
                destVia.setHorizontalAlignment(2);
                via2.setBorder(PdfCell.NO_BORDER);
                via2.setHorizontalAlignment(2);
                destVia2.setBorder(PdfCell.NO_BORDER);
                destVia2.setHorizontalAlignment(2);
                
                usuario_hora.addCell(rotuloUser);
                usuario_hora.addCell(via);
                usuario_hora.addCell(usuario);
                usuario_hora.addCell(destVia);
                
                usuario_horaSegundaVia.addCell(rotuloUser);
                usuario_horaSegundaVia.addCell(via2);
                usuario_horaSegundaVia.addCell(usuario);
                usuario_horaSegundaVia.addCell(destVia2);

                //Assinaturas
                PdfPTable assinatura = new PdfPTable(3);
                
                PdfPCell respAlmox = new PdfPCell(new Paragraph("Resp. Almoxarifado:" + "\n" + "\n"
                        + "____________________________", fontPadrao));
                PdfPCell respGestao = new PdfPCell(new Paragraph("Gestor:" + "\n" + "\n"
                        + "____________________________", fontPadrao));
                PdfPCell solicitante = new PdfPCell(new Paragraph("Solicitante:" + "\n" + "\n"
                        + "____________________________", fontPadrao));
                
                respAlmox.setBorderColor(Color.white);
                solicitante.setBorderColor(Color.white);
                respGestao.setBorderColor(Color.white);
                
                respAlmox.setHorizontalAlignment(0);
                respGestao.setHorizontalAlignment(1);
                solicitante.setHorizontalAlignment(2); //0 = esquerda, 1 = centro, 2 direita;

                assinatura.addCell(respAlmox);
                assinatura.addCell(respGestao);
                assinatura.addCell(solicitante);

                // Configura????o 1 via
                titulo.setPadding(10);
                
                Doc.setPaddingTop(5);
                Data.setPaddingTop(5);
                Doc.setPaddingBottom(5);
                Data.setPaddingBottom(5);
                
                titulo.setHorizontalAlignment(1);
                Doc.setHorizontalAlignment(2);
                Data.setHorizontalAlignment(2); //0 = esquerda, 1 = centro, 2 direita;

                empresa.setBorderWidthBottom(1);
                empresa.setVerticalAlignment(1);
                empresa.setBorderColorLeft(Color.WHITE);
                empresa.setBorderColorTop(Color.WHITE);
                empresa.setBorderColorBottom(Color.BLACK);
                
                titulo.setBorderWidthBottom(1);
                titulo.setBorderColorTop(Color.WHITE);
                titulo.setBorderColorBottom(Color.BLACK);
                
                Doc.setBorderWidthBottom(1);
                Doc.setBorderColorTop(Color.WHITE);
                Doc.setBorderColorRight(Color.WHITE);
                Doc.setBorderColorBottom(Color.BLACK);
                
                Data.setBorderWidthBottom(1);
                Data.setBorderColorTop(Color.WHITE);
                Data.setBorderColorRight(Color.WHITE);
                Data.setBorderColorLeft(Color.WHITE);
                Data.setBorderColorBottom(Color.BLACK);
                empresa.setHorizontalAlignment(1);
                
                cabeca.addCell(empresa);
                cabeca.addCell(titulo);
                cabeca.addCell(Doc);
                cabeca.addCell(Data);
                //Fim da configura????o da primeira via

                //Configura????o 2 via
                empresaSegundaVia.setPadding(10);
                tituloSegundaVia.setPadding(10);
                
                DocSegundaVia.setPaddingTop(5);
                DataSegundaVia.setPaddingTop(5);
                DocSegundaVia.setPaddingBottom(5);
                DataSegundaVia.setPaddingBottom(5);
                
                empresaSegundaVia.setHorizontalAlignment(1);
                tituloSegundaVia.setHorizontalAlignment(1);
                DocSegundaVia.setHorizontalAlignment(2);
                DataSegundaVia.setHorizontalAlignment(0); //0 = esquerda, 1 = centro, 2 direita;

                empresaSegundaVia.setBorderColor(Color.black);
                titulo.setBorderColor(Color.black);
                Doc.setBorderColor(Color.black);
                Data.setBorderColor(Color.black);
                
                cabecaSegundaVia.addCell(empresa);
                cabecaSegundaVia.addCell(titulo);
                cabecaSegundaVia.addCell(Doc);
                cabecaSegundaVia.addCell(Data);
                //Fim da configura????o da 2 via

                //criando uma tabela
                Paragraph linhaEmBranco = new Paragraph(" ");
                
                PdfPTable tabela = new PdfPTable(5);

                //Fonte cabecalho tabela
                Font fontDadosTabelaProdutos = new Font();
                fontDadosTabelaProdutos.setSize(6);
                fontDadosTabelaProdutos.setStyle(0);
                fontDadosTabelaProdutos.setColor(Color.BLACK);

                //Fonte cabecalho tabela
                Font fontTitulosColunaTbProdutos = new Font();
                fontTitulosColunaTbProdutos.setSize(9);
                fontTitulosColunaTbProdutos.setStyle(1);
                fontTitulosColunaTbProdutos.setColor(Color.BLACK);

                // PdfPCell cabecalho = new PdfPCell(new Paragraph("Lista de Produtos", fontTitulosColunaTbProdutos));
                //  cabecalho.setHorizontalAlignment(1);
                //  cabecalho.setVerticalAlignment(1);
                //  cabecalho.setBorder(PdfCell.NO_BORDER);
                // cabecalho.setBackgroundColor(new Color(240, 240, 240));
                //cabecalho.setColspan(5);
                PdfPCell coluna1 = new PdfPCell(new Paragraph("Item", fontTituloTabela));
                PdfPCell coluna2 = new PdfPCell(new Paragraph("Codigo", fontTituloTabela));
                PdfPCell coluna3 = new PdfPCell(new Paragraph("Descri????o do Item", fontTituloTabela));
                PdfPCell coluna4 = new PdfPCell(new Paragraph("Unid M", fontTituloTabela));
                PdfPCell coluna5 = new PdfPCell(new Paragraph("Quantidade", fontTituloTabela));

                //Formata????o dos rotulos da tabela de produtos
                coluna1.setPadding(5);
                coluna2.setPadding(5);
                coluna3.setPadding(5);
                coluna4.setPadding(5);
                coluna5.setPadding(5);
                
                coluna1.setHorizontalAlignment(1);//Item
                coluna2.setHorizontalAlignment(1);//Codigo
                coluna3.setHorizontalAlignment(0);//Descri????o
                coluna4.setHorizontalAlignment(1); //Unidade
                coluna5.setHorizontalAlignment(1);//Quantidade
                //Fim da formata????o dos rotulos

                // tabela.addCell(cabecalho);
                tabela.addCell(coluna1);
                tabela.addCell(coluna2);
                tabela.addCell(coluna3);
                tabela.addCell(coluna4);
                tabela.addCell(coluna5);
                
                int increment = 1;
                int indice = 0;
                
                int totalProduto = requisicao.size();
                
                while (indice <= totalProduto - 8) {
                    
                    PdfPCell item = new PdfPCell(new Paragraph(("Item " + increment), fontDadosTabelaProdutos));
                    PdfPCell codigo = new PdfPCell(new Paragraph(requisicao.get(indice + 4), fontDadosTabelaProdutos));
                    PdfPCell nome = new PdfPCell(new Paragraph((" " + requisicao.get(indice + 5)), fontDadosTabelaProdutos));
                    PdfPCell und = new PdfPCell(new Paragraph((requisicao.get(indice + 6)), fontDadosTabelaProdutos));
                    PdfPCell quantidade = new PdfPCell(new Paragraph(requisicao.get(indice + 7), fontDadosTabelaProdutos));
                    
                    item.setPadding(3);
                    item.setHorizontalAlignment(1);
                    codigo.setHorizontalAlignment(1);
                    und.setHorizontalAlignment(1);
                    quantidade.setHorizontalAlignment(2);
                    
                    if (increment % 2 == 0) {
                        
                        item.setBackgroundColor(new java.awt.Color(217, 211, 211));//217,211,211)
                        codigo.setBackgroundColor(new java.awt.Color(217, 211, 211));
                        nome.setBackgroundColor(new java.awt.Color(217, 211, 211));
                        und.setBackgroundColor(new java.awt.Color(217, 211, 211));
                        quantidade.setBackgroundColor(new java.awt.Color(217, 211, 211));
                        
                        tabela.addCell(item);
                        tabela.addCell(codigo);
                        tabela.addCell(nome);
                        tabela.addCell(und);
                        tabela.addCell(quantidade);
                        
                    } else {
                        
                        item.setBackgroundColor(new java.awt.Color(255, 255, 255));
                        codigo.setBackgroundColor(new java.awt.Color(255, 255, 255));
                        nome.setBackgroundColor(new java.awt.Color(255, 255, 255));
                        und.setBackgroundColor(new java.awt.Color(255, 255, 255));
                        quantidade.setBackgroundColor(new java.awt.Color(255, 255, 255));
                        
                        tabela.addCell(item);
                        tabela.addCell(codigo);
                        tabela.addCell(nome);
                        tabela.addCell(und);
                        tabela.addCell(quantidade);
                        
                    }
                    
                    indice = indice + 8;
                    increment++;
                }
                
                if (increment < 15) {
                    int linBranco = 1;
                    
                    while (increment - 1 + linBranco <= 14) {
                        
                        while (linBranco == 1) {
                            PdfPCell itemBranco = new PdfPCell(new Paragraph(" ", fontTitulosColunaTbProdutos));
                            itemBranco.setBorderColorTop(Color.black);
                            itemBranco.setBorderWidthBottom(0);
                            itemBranco.setBorderWidthRight(0);
                            itemBranco.setBorderWidthLeft(0);
                            
                            tabela.addCell(itemBranco);
                            tabela.addCell(itemBranco);
                            tabela.addCell(itemBranco);
                            tabela.addCell(itemBranco);
                            tabela.addCell(itemBranco);
                            linBranco++;
                        }
                        
                        PdfPCell itemBranco = new PdfPCell(new Paragraph(" ", fontTitulosColunaTbProdutos));
                        itemBranco.setBorderColor(Color.WHITE);
                        
                        tabela.addCell(itemBranco);
                        tabela.addCell(itemBranco);
                        tabela.addCell(itemBranco);
                        tabela.addCell(itemBranco);
                        tabela.addCell(itemBranco);
                        
                        linBranco++;
                        
                    }
                }

                //Define a largura das colunas das tabelas
                float[] columnWidths = new float[]{80f, 120f, 550f, 80f, 150f};
                tabela.setWidths(columnWidths);

                //Cabecalho
                float[] columnWidths2 = new float[]{120f, 400f, 60f, 70f};
                cabeca.setWidths(columnWidths2);
                cabecaSegundaVia.setWidths(columnWidths2);

                //Sub Cabecalho
                float[] columnWidthsDadosMestes = new float[]{200f, 65f};
                dadosMestres.setWidths(columnWidthsDadosMestes);

                //adicionando as tabelas no formularios
                documentoPDF.add(cabeca);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(dadosMestres);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(tabela);
                // documentoPDF.add(usuario_hora);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(assinatura);
                documentoPDF.add(usuario_hora);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(cabecaSegundaVia);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(dadosMestres);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(tabela);
                // documentoPDF.add(usuario_horaSegundaVia);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(linhaEmBranco);
                documentoPDF.add(assinatura);
                documentoPDF.add(usuario_horaSegundaVia);

                //Abrir o PDF no padr??o do arquivo.
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File("C:\\print\\RelReq.pdf"));
                
            } catch (FileNotFoundException ex) {
                JOptionPane.showConfirmDialog(this, "Documento n??o entradao " + ex);
            } catch (DocumentException ex) {
                JOptionPane.showConfirmDialog(this, "Err " + ex);
            } catch (IOException ex) {
                JOptionPane.showConfirmDialog(this, "Erro " + ex);
                
            } finally {
                documentoPDF.close();
            }

            /*      try {
            PDDocument pdDocument;
            pdDocument = PDDocument.load(new File("C:\\Users\\\\edica\\Documents\\NetBeansProjects\\NOVAAGRICOLA_02\\src\\RelProd.pdf"));
            //  PrintService printService = null;
            PrintService servico = PrintServiceLookup.lookupDefaultPrintService();
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(pdDocument));
            job.setPrintService(servico);
            if (job.printDialog()) {

                // job.pageDialog((PrintRequestAttributeSet) pdDocument);
                job.print();
            }

            pdDocument.close();
        } catch (PrinterException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BASE_PROD.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        
        txtCodigo.setText("");
        ConsultaProduto2 consulta = new ConsultaProduto2(new javax.swing.JFrame(), true);
        consulta.setVisible(true);
        String codigo = consulta.getCodigo();
        String nome = consulta.getNomeProd();
        String codProd = consulta.getCodigo();
        String und = consulta.getUnidade();
        float estoqueAtual = consulta.getEstoque();
        float valorAtual = consulta.getValor();
        
        txtCodigo.setText(codigo);
        txtProduto.setText(nome);
        
        estoque = estoqueAtual;
        valor = valorAtual;
        custoM = valor / estoque;
        txtUnidade.requestFocus();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) TabelaItens.getModel();
        dtm.removeRow(TabelaItens.getSelectedRow());
        somaHectare();
        somaCusto();
        if (lblTotalHectares.getText().equals("0,00")) {
            lblProducaoTotal.setText("R$ 0,00");
        } else {
            somaHectare();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed

    }//GEN-LAST:event_txtDataActionPerformed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed

    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void cbSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSemanaActionPerformed

    }//GEN-LAST:event_cbSemanaActionPerformed

    private void txtQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusLost
        if (txtQuantidade.getText().equals("")) {
            
        } else {
            DecimalFormat df = new DecimalFormat("#,##0.0000");
            float quantidade = Float.parseFloat(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));
            String quantidadeFormat = df.format(quantidade);
            txtQuantidade.setText(quantidadeFormat);
        }
    }//GEN-LAST:event_txtQuantidadeFocusLost

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        txtCacho.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void txtCachoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCachoFocusLost
        if (txtCacho.getText().equals("")) {
            
        } else {
            DecimalFormat df = new DecimalFormat("#,##0.0000");
            float quantidade = Float.parseFloat(txtCacho.getText().replace(".", "").replaceAll(",", "."));
            String quantidadeFormat = df.format(quantidade);
            txtCacho.setText(quantidadeFormat);
        }
    }//GEN-LAST:event_txtCachoFocusLost

    private void txtCachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCachoActionPerformed
        btnAddProduto.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCachoActionPerformed

    private void tabelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyReleased
        JD_FATO_PROD fato = new JD_FATO_PROD(this, true);
        int tecla = evt.getKeyCode();
        if (tecla == 115) {
            int linha = tabela.getSelectedRow();
            int codCaixa = Integer.parseInt(tabela.getValueAt(linha, 3).toString());
            fato.setVisible(true);
            
        } else {
            
        }
    }//GEN-LAST:event_tabelaKeyReleased

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
            java.util.logging.Logger.getLogger(BASE_PRODUCAO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BASE_PRODUCAO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BASE_PRODUCAO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BASE_PRODUCAO.class
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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new BASE_PRODUCAO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelBrowser;
    private javax.swing.JPanel PainelForm;
    public javax.swing.JPanel PainelTabelaTitulos;
    private javax.swing.JTable TabelaItens;
    private javax.swing.JButton btnAddProduto;
    public javax.swing.JButton btnBusca;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnInserirOp;
    private javax.swing.JButton btnRemoverOp;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbOp;
    private javax.swing.JComboBox<String> cbSemana;
    private javax.swing.JButton jButton6;
    public javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblDadosEmpresa;
    private javax.swing.JLabel lblDatahora;
    private javax.swing.JLabel lblMsgExclusao;
    private javax.swing.JLabel lblProducaoHa;
    private javax.swing.JLabel lblProducaoTotal;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTituloForm;
    private javax.swing.JLabel lblTotalHectares;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelTituloForm;
    public javax.swing.JTable tabela;
    private javax.swing.JTable tabelaOp;
    private javax.swing.JTextField txtCacho;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JTextField txtDocumento;
    public javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtProduto;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtUnidade;
    // End of variables declaration//GEN-END:variables
}
