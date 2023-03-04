/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import Model.ModelUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class ESTOQUE extends javax.swing.JFrame {
    
    ModelUsuario user = new ModelUsuario();
    utilitario util = new utilitario();
    String entrada = null;
    
    String nomeCrescente = "ASC";
    String nomeDecrecente = "ESC";
    String precoCrescente = "ASC";
    String precoDecrecente = "ESC";
    Conexao conn = new Conexao();
    
    public ESTOQUE() {
        initComponents();
        carregaTabela();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));
        
        int maxHeaderHeight = 35;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);
        
        btnVoltar.setBackground(Color.WHITE);
        btnInvent.setBackground(Color.white);
    }
    
    public void imprimir() {
        
    }
    
    public void carregaTabela() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und,"
                    + " atual,valor,precoVenda from tb_produtos");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
    }
    
    public void pesquisarCodigo() {
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, atual,"
                    + "valor,precoVenda from tb_produtos where codigo =?");
            pst.setString(1, txtPesquisa.getText());
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
    }
    
    public void pesquisarCodBarra() {
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, atual,"
                    + "valor,precoVenda from tb_produtos where codBarra =?");
            pst.setString(1, txtPesquisa.getText());
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();      //AlinharColunas();
    }
    
    public void pesquisarNome() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, atual,"
                    + "valor,precoVenda from tb_produtos  where nome LIKE?");
            String pesquisa = txtPesquisa.getText();
            pst.setString(1, "%" + pesquisa + "%");
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
    }
    
    public void ordenarPorNomeCres() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, "
                    + "atual,valor,precoVenda from tb_produtos order by nome desc");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();//AlinharColunas();
    }
    
    public void ordenarPorNomeDecres() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, "
                    + "atual,valor,precoVenda from tb_produtos order by nome asc");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),//Preco de Venda
                    }
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();//AlinharColunas();    }
    }
    
    public void ordenarPorSaldoDesc() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, "
                    + "atual,valor,precoVenda from tb_produtos order by atual desc");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();//AlinharColunas();    }
        //AlinharColunas();
    }
    
    public void ordenarPorSaldoAsc() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, "
                    + "atual,valor,precoVenda from tb_produtos order by atual asc");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio;
                
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();//AlinharColunas();    }
        //AlinharColunas();
    }
    
    public void ordenarPorPrecoVendaDesc() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, "
                    + "atual,valor,precoVenda, sum(valor/atual) as CustoM from tb_produtos group by codigo order by CustoM desc");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio = rs.getFloat("CustoM");
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();//AlinharColunas();    }
        //AlinharColunas();
    }
    
    public void ordenarPorPrecoVendaAsc() {
        
        Cores cor = new Cores();
        
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        
        tabela.setRowSorter(new TableRowSorter(modelo));
        
        modelo.setNumRows(0);
        
        try {
            
            Conexao conn = new Conexao();
            PreparedStatement pst, pst1;
            ResultSet rs, rs1;
            int linha = 1;
            
            pst = conn.getConexao().prepareStatement("select codigo, codBarra, nome, und, "
                    + "atual,valor,precoVenda, sum(valor/atual) as CustoM from tb_produtos group by codigo order by CustoM asc");
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                DecimalFormat df = new DecimalFormat("#,##0.0000");
                float estoque = rs.getFloat("atual");
                float valor = rs.getFloat("valor");
                float custoMedio = rs.getFloat("CustoM");
                if (valor == 0) {
                    custoMedio = 0;
                } else {
                    custoMedio = valor / estoque;
                }
                
                String estoqueSaldo = df.format(estoque);
                String valorSaldo = df.format(valor);
                String cm = df.format(custoMedio);
                
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString("codigo"),//Codifo de Produto
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("nome"),//Nome do Produto
                        rs.getString("und"),//Unidade de Medida
                        estoqueSaldo,//Saldo Atual
                        cm,
                        valorSaldo,
                        rs.getString("precoVenda"),}
                    );
                    
                }
                
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();//AlinharColunas();    }
        //AlinharColunas();
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
                    
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centro);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
                    
                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    
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
                    
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centro);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
                    
                    esquerda.setBackground(c);
                    direita.setBackground(c);
                    centro.setBackground(c);
                    
                }

                // 
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
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        cbTipoPesquisa = new javax.swing.JComboBox<>();
        btnIncluir = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        lblTitulo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblDatahora = new javax.swing.JLabel();
        lblDadosEmpresa = new javax.swing.JLabel();
        jrFiltraPositovo = new javax.swing.JRadioButton();
        btnInvent = new javax.swing.JButton();
        ckbAz = new javax.swing.JRadioButton();
        ckbEstoque = new javax.swing.JRadioButton();
        ckbPreco = new javax.swing.JRadioButton();
        btnFecharEstoque = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });

        btnPesquisar.setText("Buscar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        cbTipoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo de Barra", "Codigo", "Descrição" }));
        cbTipoPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoPesquisaActionPerformed(evt);
            }
        });

        btnIncluir.setBackground(new java.awt.Color(0, 153, 153));
        btnIncluir.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnIncluir.setForeground(new java.awt.Color(255, 255, 255));
        btnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnIncluir.setText("Incluir");
        btnIncluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnIncluir.setContentAreaFilled(false);
        btnIncluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIncluir.setEnabled(false);
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

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/back_arrow_icon_134660.png"))); // NOI18N
        btnVoltar.setText("Voltar");
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

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "indice", "Codigo", "Cod Barra", "Produto", "Unidade", "Saldo Quantidade", "Custo Médio", "Valor Total (R$)", "Preço de Venda", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(1);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabela.getColumnModel().getColumn(0).setMaxWidth(1);
            tabela.getColumnModel().getColumn(2).setMinWidth(0);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(2).setMaxWidth(0);
            tabela.getColumnModel().getColumn(3).setMinWidth(500);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(500);
            tabela.getColumnModel().getColumn(3).setMaxWidth(500);
            tabela.getColumnModel().getColumn(8).setMinWidth(0);
            tabela.getColumnModel().getColumn(8).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(8).setMaxWidth(0);
            tabela.getColumnModel().getColumn(9).setMinWidth(3);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(9).setMaxWidth(3);
        }

        lblTitulo.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("Posição atual do Estoque");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jPanel4.setBackground(new java.awt.Color(169, 169, 169));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        lblDatahora.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        lblDatahora.setText("Data e Hora:");

        lblDadosEmpresa.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        lblDadosEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblDadosEmpresa.setText(" Edicarlos Aguiar de Sousa - ME - Todos os direitos reservados");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDatahora, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDadosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDatahora)
                    .addComponent(lblDadosEmpresa))
                .addContainerGap())
        );

        jrFiltraPositovo.setBackground(new java.awt.Color(255, 255, 255));
        jrFiltraPositovo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jrFiltraPositovo.setForeground(new java.awt.Color(0, 51, 102));
        jrFiltraPositovo.setText("Apenas saldo positivo");
        jrFiltraPositovo.setOpaque(true);
        jrFiltraPositovo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jrFiltraPositovoMouseClicked(evt);
            }
        });

        btnInvent.setText("Incluir Inventario");
        btnInvent.setAlignmentY(0.0F);
        btnInvent.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnInvent.setContentAreaFilled(false);
        btnInvent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInvent.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnInvent.setMaximumSize(new java.awt.Dimension(32, 50));
        btnInvent.setMinimumSize(new java.awt.Dimension(32, 50));
        btnInvent.setOpaque(true);
        btnInvent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInventMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInventMouseExited(evt);
            }
        });
        btnInvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventActionPerformed(evt);
            }
        });

        ckbAz.setBackground(new java.awt.Color(255, 255, 255));
        ckbAz.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        ckbAz.setForeground(new java.awt.Color(0, 51, 102));
        ckbAz.setText("Ordenar por nome");
        ckbAz.setOpaque(true);
        ckbAz.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckbAzMouseClicked(evt);
            }
        });

        ckbEstoque.setBackground(new java.awt.Color(255, 255, 255));
        ckbEstoque.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        ckbEstoque.setForeground(new java.awt.Color(0, 51, 102));
        ckbEstoque.setText("Ordenar por Saldo");
        ckbEstoque.setOpaque(true);
        ckbEstoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckbEstoqueMouseClicked(evt);
            }
        });

        ckbPreco.setBackground(new java.awt.Color(255, 255, 255));
        ckbPreco.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        ckbPreco.setForeground(new java.awt.Color(0, 51, 102));
        ckbPreco.setText("Ordenar por Custo");
        ckbPreco.setOpaque(true);
        ckbPreco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckbPrecoMouseClicked(evt);
            }
        });

        btnFecharEstoque.setText("Fechar Periodo");
        btnFecharEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharEstoqueActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(ckbAz, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrFiltraPositovo, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(ckbPreco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ckbEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInvent, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFecharEstoque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckbEstoque)
                            .addComponent(ckbPreco))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jrFiltraPositovo)
                            .addComponent(ckbAz))))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPesquisar)
                        .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnFecharEstoque, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnIncluir)
                            .addComponent(btnInvent, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        String tipo_pesquisa = (String) cbTipoPesquisa.getSelectedItem();
        
        switch (tipo_pesquisa) {
            case "Codigo":
                pesquisarCodigo();
                break;
            case "Descrição":
                pesquisarNome();
                break;
            case "Codigo de Barra":
                pesquisarCodBarra();
                break;
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIncluirActionPerformed
        JD_ESTOQUE_INICIAL saldoIni = new JD_ESTOQUE_INICIAL(this, true);
        saldoIni.setVisible(true);
    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnIncluirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMouseReleased
        btnIncluir.setBackground(new java.awt.Color(0, 204, 204));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnIncluirMouseReleased

    private void btnIncluirMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMousePressed
        
        btnIncluir.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnIncluirMousePressed

    private void btnIncluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMouseExited
        btnIncluir.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnIncluirMouseExited

    private void btnIncluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIncluirMouseEntered
        btnIncluir.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnIncluirMouseEntered

    private void btnIncluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnIncluirFocusGained
        carregaTabela();        // TODO add your handling code here:
    }//GEN-LAST:event_btnIncluirFocusGained

    private void cbTipoPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoPesquisaActionPerformed
        txtPesquisa.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_cbTipoPesquisaActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        btnPesquisar.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void jrFiltraPositovoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jrFiltraPositovoMouseClicked
        if (jrFiltraPositovo.isSelected()) {
            
        } else {
            carregaTabela();
        }
    }//GEN-LAST:event_jrFiltraPositovoMouseClicked

    private void btnInventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventActionPerformed
        JD_DIG_INV invet = new JD_DIG_INV(this, true);
        invet.setVisible(true);
        carregaTabela();
    }//GEN-LAST:event_btnInventActionPerformed

    private void ckbAzMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckbAzMouseClicked
        if (ckbAz.isSelected()) {
            ordenarPorNomeCres();
            ckbPreco.setSelected(false);
            ckbEstoque.setSelected(false);
            jrFiltraPositovo.setSelected(false);
        } else {
            ordenarPorNomeDecres();
        }
    }//GEN-LAST:event_ckbAzMouseClicked

    private void ckbEstoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckbEstoqueMouseClicked
        if (ckbEstoque.isSelected()) {
            ordenarPorSaldoDesc();
            ckbAz.setSelected(false);
            ckbPreco.setSelected(false);
            jrFiltraPositovo.setSelected(false);
        } else {
            ordenarPorSaldoAsc();
        }

    }//GEN-LAST:event_ckbEstoqueMouseClicked

    private void ckbPrecoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckbPrecoMouseClicked
        if (ckbPreco.isSelected()) {
            ordenarPorPrecoVendaDesc();
            ckbAz.setSelected(false);
            ckbEstoque.setSelected(false);
            jrFiltraPositovo.setSelected(false);
        } else {
            ordenarPorPrecoVendaAsc();
        }

    }//GEN-LAST:event_ckbPrecoMouseClicked

    private void btnVoltarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseEntered
        btnVoltar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVoltarMouseEntered

    private void btnInventMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventMouseEntered
        btnInvent.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnInventMouseEntered

    private void btnVoltarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseExited
        btnVoltar.setBackground(new java.awt.Color(255, 255, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnVoltarMouseExited

    private void btnInventMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventMouseExited
        btnInvent.setBackground(new java.awt.Color(255, 255, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnInventMouseExited

    private void btnFecharEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharEstoqueActionPerformed
        FECHAR_PERIODO fechar = new FECHAR_PERIODO(this, true);
        fechar.carregaArray();
        fechar.iniciaContadores();
        fechar.setVisible(true);
        
    }//GEN-LAST:event_btnFecharEstoqueActionPerformed

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
            java.util.logging.Logger.getLogger(ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ESTOQUE.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ESTOQUE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFecharEstoque;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnInvent;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cbTipoPesquisa;
    private javax.swing.JRadioButton ckbAz;
    private javax.swing.JRadioButton ckbEstoque;
    private javax.swing.JRadioButton ckbPreco;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton jrFiltraPositovo;
    private javax.swing.JLabel lblDadosEmpresa;
    private javax.swing.JLabel lblDatahora;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
