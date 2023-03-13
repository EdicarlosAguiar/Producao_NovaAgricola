/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.SelecaoInvalida;
import FormNotificacao.exclusaoFinalizada;
import FormNotificacao.pagamentoNaoAutorizado;
import FormulariosConsultas.LEGENDA;
import Model.ModelUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Menu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.ImageIcon;
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
public class BASE_CONTA_PAGAR extends javax.swing.JFrame {

    ModelUsuario user = new ModelUsuario();
    private int tituloSelecionado;
    private String vencimento;
    private String fornecedor;
    private String valorOriginal;
    private String valorFinal;
    private String parcela;
    private String status;

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getValorOriginal() {
        return valorOriginal;
    }

    public void setValorOriginal(String valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

    public int getTituloSelecionado() {
        return tituloSelecionado;
    }

    public void setTituloSelecionado(int tituloSelecionado) {
        this.tituloSelecionado = tituloSelecionado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    Cores cor = new Cores();

    public BASE_CONTA_PAGAR() {
        initComponents();

        carregaTabela();
        this.setExtendedState(MAXIMIZED_BOTH);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));

        utilitario util = new utilitario();
        lblDadosEmpresa.setText(util.getDadosEmpresaRodape());
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);
        // tabelaContaPagar.setSelectionBackground(cor.getCorLinhaSelecionada());
        CorLinhaTabela();
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabelaContaPagar.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabelaContaPagar.getTableHeader().setPreferredSize(d);

        btnAlterar.setBackground(Color.WHITE);
        btnExcluir.setBackground(Color.WHITE);
        btnVisualizar.setBackground(Color.WHITE);
        btnVoltar.setBackground(Color.WHITE);
        btnPagar.setBackground(Color.WHITE);
    }

    public void ataulizaTabela() {

        carregaTabela();

    }

    public void carregaTabela() {

        Cores cor = new Cores();

        tabelaContaPagar.getTableHeader().setOpaque(false);
        tabelaContaPagar.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabelaContaPagar.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabelaContaPagar.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, cor.getSizeFonteHenderTable()));

        DefaultTableModel modelo = (DefaultTableModel) tabelaContaPagar.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select * from contas_pagar order by status asc, vencimento_base asc limit 100");
            rs = pst.executeQuery();
            int linha = 1;

            while (rs.next()) {

                float valor = rs.getFloat(8);

                DecimalFormat df = new DecimalFormat("###,##0.00");
                String valorFormatado = df.format(valor);

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getInt(2),//Titulo
                        rs.getInt(3),//Cod Compra
                        rs.getInt(4),//Cod Forn
                        rs.getString(5),//Fornecedor
                        rs.getString(6),//Vencimento
                        rs.getString(7),//Parcela
                        valorFormatado,//Valor
                        rs.getString(9),});//Status

                }
                linha = linha + 1;
                CorLinhaTabela();
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
        if (tabelaContaPagar.getRowCount() < 1) {

            lblNaoHaDados.setText(" Não há dados a serem exibidos");
            lblNaoHaDados.setForeground(Color.red);

        } else {
            lblNaoHaDados.setText("");
            lblNaoHaDados.setForeground(Color.BLACK);
        }
    }

    public void busca() {

        Cores cor = new Cores();

        tabelaContaPagar.getTableHeader().setOpaque(false);
        tabelaContaPagar.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabelaContaPagar.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabelaContaPagar.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabelaContaPagar.getModel();

        modelo.setNumRows(0);

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            String chavePesquisa = (String) cbChavePesquisa.getSelectedItem();

            if (chavePesquisa.equals("Titulo")) {
                pst = conn.getConexao().prepareStatement("select * from contas_pagar where titulo =?");
                pst.setString(1, txtPesquisa.getText());

            } else if (chavePesquisa.equals("Vencimento")) {
                pst = conn.getConexao().prepareStatement("select * from contas_pagar where vencimento =?");
                pst.setString(1, txtPesquisa.getText());
            } else {
                pst = conn.getConexao().prepareStatement("select * from contas_pagar where nome_forn LIKE? order by status,vencimento_base");
                pst.setString(1, "%" + txtPesquisa.getText() + "%");
            }

            rs = pst.executeQuery();

            int linha = 1;

            while (rs.next()) {

                float valor = rs.getFloat(8);

                DecimalFormat df = new DecimalFormat("###,##0.00");
                String valorFormatado = df.format(valor);

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getInt(2),//Titulo
                        rs.getInt(3),//Cod Compra
                        rs.getInt(4),//Cod Forn
                        rs.getString(5),//Fornecedor
                        rs.getString(6),//Vencimento
                        rs.getString(7),//Parcela
                        valorFormatado,//Valor
                        rs.getString(9),});//Status

                }
                linha = linha + 1;
                CorLinhaTabela();
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
        if (tabelaContaPagar.getRowCount() < 1) {

            lblNaoHaDados.setText(" Não há dados a serem exibidos");
            lblNaoHaDados.setForeground(Color.red);

        } else {
            lblNaoHaDados.setText("");
            lblNaoHaDados.setForeground(Color.BLACK);
        }
    }

    public void filtroTitulosEmaberto() {

        Cores cor = new Cores();

        tabelaContaPagar.getTableHeader().setOpaque(false);
        tabelaContaPagar.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabelaContaPagar.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabelaContaPagar.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabelaContaPagar.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select * from contas_pagar where STATUS = 'EM ABERTO'");
            rs = pst.executeQuery();
            int linha = 1;

            while (rs.next()) {

                float valor = rs.getFloat(8);

                DecimalFormat df = new DecimalFormat("###,##0.00");
                String valorFormatado = df.format(valor);

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getInt(2),//Titulo
                        rs.getInt(3),//Cod Compra
                        rs.getInt(4),//Cod Forn
                        rs.getString(5),//Fornecedor
                        rs.getString(6),//Vencimento
                        rs.getString(7),//Parcela
                        valorFormatado,//Valor
                        rs.getString(9),});//Status

                }
                linha = linha + 1;
                CorLinhaTabela();
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
        if (tabelaContaPagar.getRowCount() < 1) {

            lblNaoHaDados.setText("  Não há dados a serem exibidos");
            lblNaoHaDados.setForeground(Color.red);

        } else {
            lblNaoHaDados.setText("");
            lblNaoHaDados.setForeground(Color.BLACK);
        }

    }

    public void CorLinhaTabela() {

        Cores cor = new Cores();

        tabelaContaPagar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                    tabelaContaPagar.getColumnModel().getColumn(1).setCellRenderer(esquerda);//Titulo
                    tabelaContaPagar.getColumnModel().getColumn(2).setCellRenderer(esquerda);//
                    tabelaContaPagar.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabelaContaPagar.getColumnModel().getColumn(8).setCellRenderer(centro);

                    direita.setBackground(c);
                    esquerda.setBackground(c);

                } else {
                    c = cor.getCorLinhaParTabela();

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centro.setHorizontalAlignment(SwingConstants.CENTER);

                    tabelaContaPagar.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(2).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(3).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(4).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(5).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(6).setCellRenderer(esquerda);
                    tabelaContaPagar.getColumnModel().getColumn(7).setCellRenderer(direita);
                    tabelaContaPagar.getColumnModel().getColumn(8).setCellRenderer(centro);

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

        jPanel1 = new javax.swing.JPanel();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaContaPagar = new javax.swing.JTable();
        cbChavePesquisa = new javax.swing.JComboBox<>();
        txtPesquisa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblDatahora = new javax.swing.JLabel();
        lblDadosEmpresa = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        btnEntrar = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        ckTitulosAberto = new javax.swing.JRadioButton();
        lblMsgFiltro = new javax.swing.JLabel();
        lblNaoHaDados = new javax.swing.JLabel();

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

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Financeiro - Contas a Pagar");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        tabelaContaPagar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tabelaContaPagar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indice", "Titulo", "Compra", "Cod Forn", "Fornecedor", "Vencimento", "Parcela", "Valor", "Status"
            }
        ));
        tabelaContaPagar.setFillsViewportHeight(true);
        tabelaContaPagar.setGridColor(new java.awt.Color(239, 236, 236));
        tabelaContaPagar.setRowHeight(25);
        tabelaContaPagar.setSelectionBackground(new java.awt.Color(0, 102, 153));
        tabelaContaPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaContaPagarMouseClicked(evt);
            }
        });
        tabelaContaPagar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaContaPagarKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaContaPagar);
        if (tabelaContaPagar.getColumnModel().getColumnCount() > 0) {
            tabelaContaPagar.getColumnModel().getColumn(0).setMinWidth(1);
            tabelaContaPagar.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabelaContaPagar.getColumnModel().getColumn(0).setMaxWidth(1);
            tabelaContaPagar.getColumnModel().getColumn(1).setMinWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(1).setMaxWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(2).setMinWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(2).setMaxWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(3).setMinWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(3).setMaxWidth(80);
            tabelaContaPagar.getColumnModel().getColumn(5).setMinWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(5).setMaxWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(6).setMinWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(6).setPreferredWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(6).setMaxWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(7).setMinWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(7).setPreferredWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(7).setMaxWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(8).setMinWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(8).setPreferredWidth(100);
            tabelaContaPagar.getColumnModel().getColumn(8).setMaxWidth(100);
        }

        cbChavePesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Titulo", "Fornecedor", "Vencimento" }));

        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisa.setForeground(new java.awt.Color(102, 102, 102));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

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
                .addComponent(lblDadosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblDatahora, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
            .addComponent(lblDadosEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("Financeiro - Contas a Pagar");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));
        btnEntrar.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Add.png"))); // NOI18N
        btnEntrar.setText("Incluir");
        btnEntrar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar.setMaximumSize(new java.awt.Dimension(100, 40));
        btnEntrar.setMinimumSize(new java.awt.Dimension(100, 40));
        btnEntrar.setOpaque(true);
        btnEntrar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnEntrar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrarFocusGained(evt);
            }
        });
        btnEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrarMouseReleased(evt);
            }
        });
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        btnPagar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Dollar.png"))); // NOI18N
        btnPagar.setText("Pagar");
        btnPagar.setAlignmentY(0.8F);
        btnPagar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnPagar.setContentAreaFilled(false);
        btnPagar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPagar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnPagar.setMaximumSize(new java.awt.Dimension(100, 40));
        btnPagar.setMinimumSize(new java.awt.Dimension(100, 40));
        btnPagar.setOpaque(true);
        btnPagar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnPagar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPagarFocusGained(evt);
            }
        });
        btnPagar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPagarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPagarMouseExited(evt);
            }
        });
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Update.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.setAlignmentY(0.8F);
        btnAlterar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAlterar.setContentAreaFilled(false);
        btnAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAlterar.setEnabled(false);
        btnAlterar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnAlterar.setMaximumSize(new java.awt.Dimension(100, 40));
        btnAlterar.setMinimumSize(new java.awt.Dimension(100, 40));
        btnAlterar.setOpaque(true);
        btnAlterar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnAlterar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAlterarFocusGained(evt);
            }
        });
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

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Delete.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setAlignmentY(0.8F);
        btnExcluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnExcluir.setMaximumSize(new java.awt.Dimension(100, 40));
        btnExcluir.setMinimumSize(new java.awt.Dimension(100, 40));
        btnExcluir.setOpaque(true);
        btnExcluir.setPreferredSize(new java.awt.Dimension(100, 40));
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
        });
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

        btnVisualizar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnVisualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Copy.png"))); // NOI18N
        btnVisualizar.setText("Pg_Mult");
        btnVisualizar.setAlignmentY(0.8F);
        btnVisualizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVisualizar.setContentAreaFilled(false);
        btnVisualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisualizar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVisualizar.setMaximumSize(new java.awt.Dimension(100, 40));
        btnVisualizar.setMinimumSize(new java.awt.Dimension(100, 40));
        btnVisualizar.setOpaque(true);
        btnVisualizar.setPreferredSize(new java.awt.Dimension(100, 40));
        btnVisualizar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnVisualizarFocusGained(evt);
            }
        });
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

        btnVoltar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Home.png"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setAlignmentY(0.0F);
        btnVoltar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnVoltar.setMaximumSize(new java.awt.Dimension(100, 40));
        btnVoltar.setMinimumSize(new java.awt.Dimension(100, 40));
        btnVoltar.setOpaque(true);
        btnVoltar.setPreferredSize(new java.awt.Dimension(100, 40));
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

        ckTitulosAberto.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        ckTitulosAberto.setText("Exibir somente titulos em aberto");
        ckTitulosAberto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckTitulosAbertoMouseClicked(evt);
            }
        });

        lblMsgFiltro.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblMsgFiltro.setForeground(new java.awt.Color(0, 153, 204));

        lblNaoHaDados.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblNaoHaDados.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(cbChavePesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNaoHaDados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMsgFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ckTitulosAberto)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(ckTitulosAberto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMsgFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNaoHaDados, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtPesquisa)
                                .addComponent(cbChavePesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVisualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnVoltarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseExited
        btnVoltar.setBackground(Color.white);
    }//GEN-LAST:event_btnVoltarMouseExited

    private void btnVoltarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVoltarMouseEntered
        btnVoltar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVoltarMouseEntered

    private void btnVisualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseExited
        btnVisualizar.setBackground(Color.white);
    }//GEN-LAST:event_btnVisualizarMouseExited

    private void btnVisualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVisualizarMouseEntered
        btnVisualizar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnVisualizarMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(Color.white);
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAlterarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseExited
        btnAlterar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnAlterarMouseExited

    private void btnAlterarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseEntered
        btnAlterar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnAlterarMouseEntered

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed

        JD_CONTA_PAGAR conta = new JD_CONTA_PAGAR(this, true);
        conta.buscaUltimoTitulo();
        conta.carregaComboNatureza();
        conta.setVisible(true);
        carregaTabela();
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnEntrarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrarMouseReleased

    private void btnEntrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMousePressed

        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnEntrarMousePressed

    private void btnEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseExited
        btnEntrar.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrarMouseExited

    private void btnEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrarMouseEntered
        btnEntrar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnEntrarMouseEntered

    private void btnEntrarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrarFocusGained

    }//GEN-LAST:event_btnEntrarFocusGained

    private void btnPagarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagarMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPagarMouseEntered

    private void btnPagarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPagarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPagarMouseExited

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        if (this.getStatus() == null) {
            SelecaoInvalida erro = new SelecaoInvalida(this, true);
            erro.setVisible(true);
            this.setVisible(true);
        } else {

            if (this.getStatus().equals("TITULO PAGO")) {
                pagamentoNaoAutorizado erro = new pagamentoNaoAutorizado(this, true);
                erro.msgPagamento();
                erro.setVisible(true);
                this.setStatus(null);
            } else {
                JD_PAGAR_TITULO pagar = new JD_PAGAR_TITULO(this, true);
                pagar.pegarTitulo(this);
                pagar.setVisible(true);

                this.setStatus(null);

            }
        }
    }//GEN-LAST:event_btnPagarActionPerformed

    private void tabelaContaPagarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaContaPagarMouseClicked
        int linha = tabelaContaPagar.getSelectedRow();
        setTituloSelecionado(Integer.parseInt(tabelaContaPagar.getValueAt(linha, 1).toString()));
        setFornecedor(tabelaContaPagar.getValueAt(linha, 4).toString());
        setVencimento(tabelaContaPagar.getValueAt(linha, 5).toString());
        setValorOriginal(tabelaContaPagar.getValueAt(linha, 7).toString());
        setValorFinal(tabelaContaPagar.getValueAt(linha, 7).toString());
        setParcela(tabelaContaPagar.getValueAt(linha, 6).toString());
        setStatus(tabelaContaPagar.getValueAt(linha, 8).toString());


    }//GEN-LAST:event_tabelaContaPagarMouseClicked

    private void btnPagarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPagarFocusGained

// TODO add your handling code here:
    }//GEN-LAST:event_btnPagarFocusGained

    private void tabelaContaPagarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaContaPagarKeyReleased

        int linha = tabelaContaPagar.getSelectedRow();

        setTituloSelecionado(Integer.parseInt(tabelaContaPagar.getValueAt(linha, 1).toString()));
        setFornecedor(tabelaContaPagar.getValueAt(linha, 4).toString());
        setVencimento(tabelaContaPagar.getValueAt(linha, 5).toString());
        setValorOriginal(tabelaContaPagar.getValueAt(linha, 7).toString());
        setValorFinal(tabelaContaPagar.getValueAt(linha, 7).toString());
        setParcela(tabelaContaPagar.getValueAt(linha, 6).toString());
        setStatus(tabelaContaPagar.getValueAt(linha, 8).toString());
    }//GEN-LAST:event_tabelaContaPagarKeyReleased

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        JD_PG_BLOCO pg = new JD_PG_BLOCO(this, true);
        pg.setVisible(true);
        carregaTabela();
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnVisualizarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnVisualizarFocusGained


    }//GEN-LAST:event_btnVisualizarFocusGained

    private void ckTitulosAbertoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckTitulosAbertoMouseClicked
        if (ckTitulosAberto.isSelected()) {

            lblMsgFiltro.setVisible(true);
            lblMsgFiltro.setOpaque(true);
            lblMsgFiltro.setBackground(new Color(255, 255, 102));
            // lblMsgFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Atencao.png")));
            lblMsgFiltro.setText("  Existe filtros aplicados a tabela");
            filtroTitulosEmaberto();

        } else {
            lblMsgFiltro.setOpaque(false);
            lblMsgFiltro.setText("");
            carregaTabela();
        }
    }//GEN-LAST:event_ckTitulosAbertoMouseClicked

    private void btnAlterarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAlterarFocusGained
        ataulizaTabela();
    }//GEN-LAST:event_btnAlterarFocusGained

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        ataulizaTabela();
    }//GEN-LAST:event_btnExcluirFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtPesquisa.getText().equals("")) {
            carregaTabela();
        } else {
            busca();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
            if (this.getStatus() == null) {
            SelecaoInvalida erro = new SelecaoInvalida(this, true);
            erro.setVisible(true);
            this.setVisible(true);
        } else {
            try {
                int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão da movimentação"
                        + "\n" + "Cod Titulo: " + tituloSelecionado + "\n" + "Fornecedor: " + fornecedor + "\n" + "Valor: " + "R$ "+valorFinal,
                        "AGUITECH", JOptionPane.YES_NO_OPTION);
                if (resp1 == 0) {
                    Conexao conn = new Conexao();
                    PreparedStatement pst;
                    ResultSet rs;

                    pst = conn.getConexao().prepareStatement("delete from contas_pagar where titulo=?");
                    pst.setInt(1, tituloSelecionado);
                    pst.executeUpdate();
                    exclusaoFinalizada confirmacao = new exclusaoFinalizada(this, true);
                    confirmacao.setVisible(true);
                    pst.close();
                    ataulizaTabela();

                } else {

                }

            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_btnExcluirActionPerformed

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
            java.util.logging.Logger.getLogger(BASE_CONTA_PAGAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BASE_CONTA_PAGAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BASE_CONTA_PAGAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BASE_CONTA_PAGAR.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BASE_CONTA_PAGAR().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cbChavePesquisa;
    private javax.swing.JRadioButton ckTitulosAberto;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDadosEmpresa;
    private javax.swing.JLabel lblDatahora;
    private javax.swing.JLabel lblMsgFiltro;
    private javax.swing.JLabel lblNaoHaDados;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tabelaContaPagar;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
