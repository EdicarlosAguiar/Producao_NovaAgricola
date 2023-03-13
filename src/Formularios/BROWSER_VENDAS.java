/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.SelecaoInvalida;
import FormulariosConsultas.ConsultaCliente;
import FormulariosConsultas.ConsultaProduto2;
import Model.ModelCompra;
import Model.ModelProduto;
import Model.ModelUsuario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Menu;
import java.io.File;
import java.io.InputStream;
import static java.lang.Thread.State.NEW;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import util.AguardeGerandoRelatorio;
import util.Conexao;
import util.Cores;
import util.progresso;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class BROWSER_VENDAS extends javax.swing.JFrame {

    String vendaSelecionada;
    int altura;
    int largura;

    public String getVendaSelecionada() {
        return vendaSelecionada;
    }

    public void setVendaSelecionada(String vendaSelecionada) {
        this.vendaSelecionada = vendaSelecionada;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    ModelUsuario user = new ModelUsuario();
    AguardeGerandoRelatorio aguard = new AguardeGerandoRelatorio();

    public BROWSER_VENDAS() {
        initComponents();

        this.setExtendedState(MAXIMIZED_BOTH);
        configInicias();
        altura = getSize().height;
        largura = getSize().width;
        utilitario util = new utilitario();
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy" + " - " + "HH:mm:ss");
        lblDatahora.setText("Usuario: " + user.getUsuarioLogado() + " | Data-Hora: " + dtf.format(LocalDateTime.now()));
        lblDadosEmpresa.setText(util.getDadosEmpresaRodape());
        aguard.setVisible(false);
    }

    public void mostraComponetes() {

        PainelTabelaTitulos.setVisible(true);

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

    public void AlinharColunas() {
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        tabela.getColumnModel().getColumn(7).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(8).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(9).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(11).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(12).setCellRenderer(direita);

        int linha = 0;
        int indice = Integer.parseInt(tabela.getValueAt(linha, 19).toString());
        while (linha < 10) {
            if (linha == 0) {
                JOptionPane.showMessageDialog(rootPane, indice);

            } else {
                direita.setBackground(Color.green);
            }
            linha = linha + 1;
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
                    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centro.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(1).setCellRenderer(centro); //Codigo
                    tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Data
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Cliente
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);//Valor da Venda
                    tabela.getColumnModel().getColumn(5).setCellRenderer(centro);//Forma de Pagamento
                    tabela.getColumnModel().getColumn(6).setCellRenderer(centro); //Cod Pagamento

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

                    tabela.getColumnModel().getColumn(1).setCellRenderer(centro); //Codigo
                    tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Data
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Cliente
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);//Valor da Venda
                    tabela.getColumnModel().getColumn(5).setCellRenderer(centro);//Forma de Pagamento
                    tabela.getColumnModel().getColumn(6).setCellRenderer(centro); //Cod Pagamento

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);

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

            pst = conn.getConexao().prepareStatement("select SUM(valorFinal) as Valor, codigo,"
                    + " data,cliente, formPagamento,condPagamento from venda group by CODIGO order by CODIGO DESC limit 50");
            rs = pst.executeQuery();

            while (rs.next()) {

                DecimalFormat df = new DecimalFormat("#,##0.00");
                float valorFinal = (rs.getFloat("Valor"));
                String valorFormatado = df.format(valorFinal);

                {
                    modelo.addRow(new Object[]{
                        tabela.getRowCount() + 1,
                        rs.getInt("codigo"),//Sequencial
                        rs.getString("data"),//Data
                        rs.getString("cliente"),//Data
                        valorFormatado,
                        rs.getString("formPagamento"),
                        rs.getString("condPagamento"),});//Codicoes de Pagamento
                }
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

        CorLinhaTabela();
    }

    public boolean gerarRelatorioVenda(int pCodigo) {
        Conexao conn = new Conexao();
        try {
            PreparedStatement pst;
            ResultSet rs;
            int linha = 3;
            pst = conn.getConexao().prepareStatement("select SUM(valorFinal) as Valor, codigo,"
                    + " data,formPagamento,condPagamento from venda group by CODIGO order by CODIGO DESC limit 50");
            rs = pst.executeQuery();

            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            // caminho do arquivo dentro dos pacotes  
            InputStream caminhoRelatorio = this.getClass().getClassLoader().getResourceAsStream("relatorios/Vendas.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoRelatorio, new HashMap(), jrRS);

            String nomeArquivo = "C:/BLVendasPdvMySQL/rel.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, nomeArquivo);
            File file = new File(nomeArquivo);
            try {
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
            }
            file.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro:", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
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

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Codigo", "Nome", "Categoria", "Sub Categoria" }));

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jButton1.setText("Buscar");

        tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        tabela.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cod Venda", "Data", "Cliente", "Valor Final", "Forma de Pagamento", "Codições de Pagamento", "nulo"
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
            tabela.getColumnModel().getColumn(3).setMinWidth(300);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
            tabela.getColumnModel().getColumn(3).setMaxWidth(300);
            tabela.getColumnModel().getColumn(7).setMinWidth(3);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(7).setMaxWidth(3);
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
        btnAlterar.setEnabled(false);
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
        lblTitulo.setText("Listagem de Vendas");
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
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(jTextField1)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        int linhaSelecionada = tabela.getSelectedRow();

        vendaSelecionada = (String) (tabela.getValueAt(linhaSelecionada, 1).toString());


    }//GEN-LAST:event_tabelaMouseClicked

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed


    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        this.dispose();

        /*  gerarRelatorioVenda((int) tabela.getValueAt(tabela.getSelectedRow(), 1));
        
        
          try {
            PreparedStatement comandoSQL;
            comandoSQL = Conexao.getConexao().prepareStatement("select * from vendas");
            ResultSet rs = comandoSQL.executeQuery();

            JRResultSetDataSource relResult = new JRResultSetDataSource(rs);
            JasperPrint jpPrint = JasperFillManager.fillReport("Relatorios\\Venda.jasper", new HashMap(), relResult);
            JasperViewer jv = new JasperViewer(jpPrint,false);
            jv.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (JRException ex) {
         //   Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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

        NOVO_FORM_VENDA venda = new NOVO_FORM_VENDA();
        ConsultaProduto2 consult = new ConsultaProduto2(this, true);
        consult.carregaArrayProdutos();
        ConsultaCliente consultaCliente = new ConsultaCliente(this,true);
        consultaCliente.carregaArray();
        venda.buscaUltimaVenda();
        venda.tituloIncluir();
        venda.carregaNaturezaFinanceira();
        venda.carregaOP();
        venda.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnIncluirActionPerformed

    private void btnIncluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnIncluirFocusGained


    }//GEN-LAST:event_btnIncluirFocusGained

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
       if (vendaSelecionada == null) {

            SelecaoInvalida erro = new SelecaoInvalida(this, true);
            erro.setVisible(true);

        } else {
            NOVO_FORM_VENDA venda = new NOVO_FORM_VENDA();

            venda.pegaVenda(this);
            venda.carregaTabela();
            venda.somaTabela();
            venda.buscaDesconto();
            venda.valorFinal();
            venda.tituloVisualizar();
            venda.setVisible(true);
            dispose();

        }
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (vendaSelecionada == null) {

            SelecaoInvalida erro = new SelecaoInvalida(this, true);
            erro.setVisible(true);

        } else {
            NOVO_FORM_VENDA venda = new NOVO_FORM_VENDA();

            venda.pegaVenda(this);
            venda.carregaTabela();
            venda.somaTabela();
            venda.buscaDesconto();
            venda.valorFinal();
            venda.tituloExcluir();
            venda.setVisible(true);
            dispose();

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
            java.util.logging.Logger.getLogger(BROWSER_VENDAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BROWSER_VENDAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BROWSER_VENDAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BROWSER_VENDAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new BROWSER_VENDAS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel PainelTabelaTitulos;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnIncluir;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.ButtonGroup buttonGroup1;
    public javax.swing.JButton jButton1;
    public javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblDadosEmpresa;
    private javax.swing.JLabel lblDatahora;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JPanel painelPrincipal;
    public javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
