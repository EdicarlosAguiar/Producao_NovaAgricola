/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.SelecaoInvalida;
import Model.ModelUsuario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
public class BASE_PLANTIO extends javax.swing.JFrame {

    String opselecionada;

    public String getOpselecionada() {
        return opselecionada;
    }

    public void setOpselecionada(String opselecionada) {
        this.opselecionada = opselecionada;
    }

    ModelUsuario user = new ModelUsuario();

    public BASE_PLANTIO() {
        initComponents();
        carregaTabela();
        this.setExtendedState(MAXIMIZED_BOTH);
        configInicias();

        utilitario util = new utilitario();
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));
        lblDadosEmpresa.setText(util.getDadosEmpresaRodape());
        //  btnImprimir.setVisible(false);

    }

    public void configInicias() {

        Cores cor = new Cores();
        //  tabela.setSelectionBackground(cor.getCorLinhaSelecionada());
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        btnAlterar.setBackground(Color.WHITE);
        btnExcluir.setBackground(Color.WHITE);
        btnVisualizar.setBackground(Color.WHITE);
        btnVoltar.setBackground(Color.WHITE);

    }

    public void CorLinhaTabela() {

        Cores cor = new Cores();

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int colum) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);

                // 
                Color c = cor.getCorLinhaImparTabela();
                Color desativa = new Color(255, 255, 102);
                int indice = Integer.parseInt(table.getValueAt(row, 0).toString());
                String status = (String) table.getValueAt(row, 9);

                if (indice % 2 != 0) {
                    if (status.equals("ENCERRADO")) {
                        c = desativa;
                    } else {
                        c = c;
                    }

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
                    tabela.getColumnModel().getColumn(7).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(10).setCellRenderer(esquerda);

                    direita.setBackground(c);
                    esquerda.setBackground(c);

                } else {
                    if (status.equals("ENCERRADO")) {
                        c = desativa;
                    } else {
                        c = cor.getCorLinhaParTabela();
                    }

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
                    tabela.getColumnModel().getColumn(7).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(8).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(10).setCellRenderer(esquerda);
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
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, cor.getSizeFonteHenderTable()));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from plantio ORDER BY lote, setor");
            rs = pst.executeQuery();

            while (rs.next()) {

                String FimCiclo = rs.getString("FinCiclo");
                String cultura = rs.getString("cultura");

                if (FimCiclo.equals("") || FimCiclo.equals("  /  /    ")) {
                    FimCiclo = "ATIVO";
                } else {
                    FimCiclo = "ENCERRADO";
                }

                if (cultura.equals("BN")) {
                    cultura = cultura + " - BANANA";
                } else if (cultura.equals("GO")) {
                    cultura = cultura + " - GOIABA";
                } else {
                    cultura = cultura + " - ACEROLA";
                }

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(3),//Inicioo
                        rs.getString(4),//nome
                        rs.getString(6),//Unidade de Medida
                        rs.getString(7),//Categoria
                        rs.getString(8),//Sub Categoria
                        rs.getString(9),
                        cultura,
                        rs.getString(11),
                        FimCiclo,
                        rs.getString("estagio"),});
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

        CorLinhaTabela();
    }

    public void busca() {
        Conexao conn = new Conexao();
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;

            String chavePesquisa = (String) cbTipoPesquisa.getSelectedItem();

            if (chavePesquisa.equals("Lote")) {
                pst = conn.getConexao().prepareStatement("select * from plantio where lote =?");

            } else if (chavePesquisa.equals("Setor")) {
                pst = conn.getConexao().prepareStatement("select * from plantio where setor =?");
            } else if (chavePesquisa.equals("Cultura")) {
                pst = conn.getConexao().prepareStatement("select * from plantio where cultura =?");
            } else if (chavePesquisa.equals("Variedade")) {
                pst = conn.getConexao().prepareStatement("select * from plantio where variedade =?");

            } else {
                pst = conn.getConexao().prepareStatement("select * from plantio where ordem_pro =?");
            }

            pst.setString(1, txtPesquisa.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                String FimCiclo = rs.getString("FinCiclo");
                String cultura = rs.getString("cultura");
                if (FimCiclo.equals("") || FimCiclo.equals("  /  /    ")) {
                    FimCiclo = "ATIVO";
                } else {
                    FimCiclo = "ENCERRADO";
                }

                if (cultura.equals("BN")) {
                    cultura = cultura + " - BANANA";
                } else if (cultura.equals("GO")) {
                    cultura = cultura + " - GOIABA";
                } else {
                    cultura = cultura + " - ACEROLA";
                }

                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(3),//Inicioo
                        rs.getString(4),//nome
                        rs.getString(6),//Unidade de Medida
                        rs.getString(7),//Categoria
                        rs.getString(8),//Sub Categoria
                        rs.getString(9),
                        cultura,
                        rs.getString(11),
                        FimCiclo,});
                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

        CorLinhaTabela();
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

        cbTipoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lote", "Setor", "Cultura", "Variedade", "Op" }));

        txtPesquisa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPesquisa.setForeground(new java.awt.Color(102, 102, 102));

        btnBusca.setText("Buscar");
        btnBusca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscaActionPerformed(evt);
            }
        });

        tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tabela.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Inicio", "Seq Plantio", "Ordem de Produção", "Lote", "Setor", "Hectare", "Cultura", "Variedade", "Status", "Estagio Atual"
            }
        ));
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
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
            tabela.getColumnModel().getColumn(2).setMinWidth(80);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(2).setMaxWidth(80);
            tabela.getColumnModel().getColumn(3).setMinWidth(150);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(3).setMaxWidth(150);
            tabela.getColumnModel().getColumn(4).setMinWidth(80);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(4).setMaxWidth(80);
            tabela.getColumnModel().getColumn(5).setMinWidth(80);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(5).setMaxWidth(80);
            tabela.getColumnModel().getColumn(6).setMinWidth(80);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(6).setMaxWidth(80);
        }
        tabela.getAccessibleContext().setAccessibleParent(painelCorpo);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
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
        btnExcluir.setEnabled(false);
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

        lblTitulo.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setText("Controle de Produção - Plantio");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout PainelTabelaTitulosLayout = new javax.swing.GroupLayout(PainelTabelaTitulos);
        PainelTabelaTitulos.setLayout(PainelTabelaTitulosLayout);
        PainelTabelaTitulosLayout.setHorizontalGroup(
            PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelTabelaTitulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PainelTabelaTitulosLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
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
        opselecionada = ((String) tabela.getValueAt(linha, 3).toString());

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        if (opselecionada == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {

            JD_REQ req = new JD_REQ(this, true);
            req.pegaOpSelecionada(this);
            req.carregaDados();
            req.parametrosAlterar();

            req.setVisible(true);
            carregaTabela();
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

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
        JD_REQ req = new JD_REQ(this, true);
        req.setVisible(true);
        carregaTabela();

    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnIncluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnIncluirFocusGained


    }//GEN-LAST:event_btnIncluirFocusGained

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        if (opselecionada == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {

            JD_REQ req = new JD_REQ(this, true);
            req.pegaOpSelecionada(this);
            req.carregaDados();
            carregaTabela();
            req.parametrosVisualizar();
            req.setVisible(true);

        }
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (opselecionada == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            NOVO_CADASTRO_PRODUTO_EXCLUIR prod = new NOVO_CADASTRO_PRODUTO_EXCLUIR();
            //        prod.pegaProduto(this);
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
        } else {
            busca();
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
            java.util.logging.Logger.getLogger(BASE_PLANTIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BASE_PLANTIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BASE_PLANTIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BASE_PLANTIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BASE_PLANTIO().setVisible(true);
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
