/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Formularios;

import Controller.ControlCaixaPDV;
import FormNotificacao.ANP_CXPDV01;
import FormNotificacao.Confirmacao;
import Model.ModelOperacaoCaixaPDV;
import Model.ModelUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import static java.awt.SystemColor.control;
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
import util.Conexao;
import util.Cores;

/**
 *
 * @author Edicarlos
 */
public class Caixa_PDV extends javax.swing.JDialog {

    ModelUsuario user = new ModelUsuario();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    Conexao conn = new Conexao();

    private int caixaSelecionado;
    private String inicio;
    private String entrada;
    private String saida;
    private String saldo;
    private String pegaRespApv;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPegaRespApv() {
        return pegaRespApv;
    }

    public void setPegaRespApv(String pegaRespApv) {
        this.pegaRespApv = pegaRespApv;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public int getCaixaSelecionado() {
        return caixaSelecionado;
    }

    public void setCaixaSelecionado(int caixaSelecionado) {
        this.caixaSelecionado = caixaSelecionado;
    }

    /**
     * Creates new form Caixa_PDV
     */
    public Caixa_PDV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();
        carregaTabela();
        btnAbrirCaixa.requestFocus();

        configIniciais();
        carregaTabela();
        btnDetalhes.setBackground(Color.WHITE);
        btnEncerrar.setBackground(Color.WHITE);
        btnExcluir.setBackground(Color.WHITE);
     
        btnSangria.setBackground(Color.WHITE);
    }

    public void resp(Aprovador_Caixa_PDV apv) {
        this.setPegaRespApv(apv.getResp());
    }

    public void configIniciais() {

        Cores cor = new Cores();
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 10));

        ckOcultarExibir.setSelected(true);

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);

    }

    public void CorLinhaTabela() {

        Cores cor = new Cores();

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int colum) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);

                // 
                Color c = cor.getCorLinhaImparTabela();
                int indice = Integer.parseInt(table.getValueAt(row, 7).toString());

                if (indice % 2 != 0) {
                    c = c;

                    label.setBackground(c);

                    DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer direita = new DefaultTableCellRenderer();
                    DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
                    direita.setHorizontalAlignment(SwingConstants.RIGHT);
                    esquerda.setHorizontalAlignment(SwingConstants.LEFT);
                    centro.setHorizontalAlignment(SwingConstants.CENTER);

                    tabela.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);

                    tabela.getColumnModel().getColumn(2).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);

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

                    tabela.getColumnModel().getColumn(0).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
                    tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);

                    tabela.getColumnModel().getColumn(2).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(3).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(4).setCellRenderer(direita);
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);

                    esquerda.setBackground(c);
                    direita.setBackground(c);
                    centro.setBackground(c);

                }

                // 
                return label;
            }

        });

    }

    public void carregaTabela() {

        /* if (ckOcultarExibir.isSelected()) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            modelo.setNumRows(0);
            lblAvisoFiltro.setVisible(true);
        } else {*/
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);

        try {

            //      JOptionPane.showMessageDialog(new javax.swing.JFrame(), "Carrega tabela");
            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;

            pst = conn.getConexao().prepareStatement("select saldo_inicial,sum(entradas) as entradas,"
                    + "sum(saidas) as saidas,data,codigo,"
                    + "status from caixa_pdv "
                    + "group by codigo order by id desc");
            rs = pst.executeQuery();

            while (rs.next()) {

                DecimalFormat df = new DecimalFormat("#,##0.00");
                float saldo_inicial = rs.getFloat("saldo_inicial");
                float entradas = rs.getFloat("entradas");
                float saidas = rs.getFloat("saidas");
                float saldo = saldo_inicial + entradas - saidas;

                String saldo_inicial_formatado = df.format(saldo_inicial);
                String entradas_formatado = df.format(entradas);
                String saidas_formatado = df.format(saidas);
                String saldo_formatado = df.format(saldo);

                modelo.addRow(new Object[]{
                    rs.getString("data"),//Data
                    rs.getInt("codigo"),//Codigo Fornecedor
                    saldo_inicial_formatado,//SAldo Incial
                    entradas_formatado,//Entradas
                    saidas_formatado,//saidas
                    saldo_formatado,//Saldos
                    rs.getString("status"),//Data
                    tabela.getRowCount() + 1,}
                );

                linha = linha + 1;
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
        //AlinharColunas();

    }

    public void ExcluirCaixa() {

        try {

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do Caixa Diário " + caixaSelecionado + "?"
                    + "\n" + "Após a confirmação todos os registros de entrada e saida não poderão ser recuperados!",
                    "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                Conexao conn = new Conexao();
                PreparedStatement pst;
                ResultSet rs;

                pst = conn.getConexao().prepareStatement("delete from caixa_pdv where codigo=?");
                pst.setInt(1, caixaSelecionado);
                pst.executeUpdate();

                pst.close();

                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                conf.textoCaixaExcluido();
                conf.setVisible(true);
                carregaTabela();

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar excluir caixa" + "\n" + e);
        }

    }

    public void AlinharColunas() {

        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        tabela.getColumnModel().getColumn(0).setCellRenderer(esquerda);
        tabela.getColumnModel().getColumn(1).setCellRenderer(esquerda);
        tabela.getColumnModel().getColumn(6).setCellRenderer(esquerda);

        tabela.getColumnModel().getColumn(2).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(3).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(4).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(5).setCellRenderer(direita);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        ckOcultarExibir = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnFechar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnAbrirCaixa = new javax.swing.JButton();
        btnSangria = new javax.swing.JButton();
        btnEncerrar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnDetalhes = new javax.swing.JButton();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "Caixa", "Saldo Inicial", "Entradas", "Saidas", "Saldo", "Status", "indice"
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
            tabela.getColumnModel().getColumn(7).setMinWidth(3);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(7).setMaxWidth(3);
        }

        ckOcultarExibir.setText("Exibir/Ocultar");
        ckOcultarExibir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ckOcultarExibirMouseClicked(evt);
            }
        });
        ckOcultarExibir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckOcultarExibirActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(94, 110, 110));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gerenciamento do CAIXA  PDV");

        btnFechar.setBackground(new java.awt.Color(94, 110, 110));
        btnFechar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnFechar.setForeground(new java.awt.Color(204, 204, 204));
        btnFechar.setText("X");
        btnFechar.setAlignmentY(0.8F);
        btnFechar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnFechar.setContentAreaFilled(false);
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnFechar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnFechar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnFechar.setOpaque(true);
        btnFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFecharMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFecharMouseExited(evt);
            }
        });
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnAbrirCaixa.setBackground(new java.awt.Color(0, 153, 153));
        btnAbrirCaixa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAbrirCaixa.setForeground(new java.awt.Color(255, 255, 255));
        btnAbrirCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Upload.png"))); // NOI18N
        btnAbrirCaixa.setText("Abir Caixa");
        btnAbrirCaixa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAbrirCaixa.setContentAreaFilled(false);
        btnAbrirCaixa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirCaixa.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnAbrirCaixa.setOpaque(true);
        btnAbrirCaixa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAbrirCaixaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnAbrirCaixaFocusLost(evt);
            }
        });
        btnAbrirCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAbrirCaixaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAbrirCaixaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAbrirCaixaMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnAbrirCaixaMouseReleased(evt);
            }
        });
        btnAbrirCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirCaixaActionPerformed(evt);
            }
        });
        jPanel2.add(btnAbrirCaixa);

        btnSangria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Load.png"))); // NOI18N
        btnSangria.setText("Sangria");
        btnSangria.setAlignmentY(0.8F);
        btnSangria.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnSangria.setContentAreaFilled(false);
        btnSangria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSangria.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnSangria.setMaximumSize(new java.awt.Dimension(85, 41));
        btnSangria.setMinimumSize(new java.awt.Dimension(85, 41));
        btnSangria.setOpaque(true);
        btnSangria.setPreferredSize(new java.awt.Dimension(85, 41));
        btnSangria.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSangriaFocusGained(evt);
            }
        });
        btnSangria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSangriaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSangriaMouseExited(evt);
            }
        });
        btnSangria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSangriaActionPerformed(evt);
            }
        });
        jPanel2.add(btnSangria);

        btnEncerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Forbidden.png"))); // NOI18N
        btnEncerrar.setText("Encerrar");
        btnEncerrar.setAlignmentY(0.8F);
        btnEncerrar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEncerrar.setContentAreaFilled(false);
        btnEncerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEncerrar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnEncerrar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnEncerrar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnEncerrar.setOpaque(true);
        btnEncerrar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEncerrarFocusGained(evt);
            }
        });
        btnEncerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEncerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEncerrarMouseExited(evt);
            }
        });
        btnEncerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncerrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEncerrar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/apagar.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setAlignmentY(0.8F);
        btnExcluir.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnExcluir.setContentAreaFilled(false);
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnExcluir.setMaximumSize(new java.awt.Dimension(32, 50));
        btnExcluir.setMinimumSize(new java.awt.Dimension(32, 50));
        btnExcluir.setOpaque(true);
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
        jPanel2.add(btnExcluir);

        btnDetalhes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Visualizar.png"))); // NOI18N
        btnDetalhes.setText("Detalhes");
        btnDetalhes.setAlignmentY(0.8F);
        btnDetalhes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnDetalhes.setContentAreaFilled(false);
        btnDetalhes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetalhes.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnDetalhes.setMaximumSize(new java.awt.Dimension(32, 50));
        btnDetalhes.setMinimumSize(new java.awt.Dimension(32, 50));
        btnDetalhes.setOpaque(true);
        btnDetalhes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnDetalhesFocusGained(evt);
            }
        });
        btnDetalhes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDetalhesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDetalhesMouseExited(evt);
            }
        });
        btnDetalhes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalhesActionPerformed(evt);
            }
        });
        jPanel2.add(btnDetalhes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                        .addComponent(ckOcultarExibir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckOcultarExibir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
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

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();
        setCaixaSelecionado(Integer.parseInt(tabela.getValueAt(linha, 1).toString()));
        setInicio(tabela.getValueAt(linha, 2).toString());
        setEntrada(tabela.getValueAt(linha, 3).toString());
        setSaida(tabela.getValueAt(linha, 4).toString());
        setSaldo(tabela.getValueAt(linha, 5).toString());
        setStatus(tabela.getValueAt(linha, 6).toString());

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnAbrirCaixaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAbrirCaixaFocusGained
        carregaTabela();
        btnAbrirCaixa.setBackground(new java.awt.Color(0, 204, 204));

    }//GEN-LAST:event_btnAbrirCaixaFocusGained

    private void btnAbrirCaixaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseEntered
        btnAbrirCaixa.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnAbrirCaixaMouseEntered

    private void btnAbrirCaixaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseExited
        btnAbrirCaixa.setBackground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_btnAbrirCaixaMouseExited

    private void btnAbrirCaixaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMousePressed
        btnAbrirCaixa.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnAbrirCaixaMousePressed

    private void btnAbrirCaixaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseReleased
        btnAbrirCaixa.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnAbrirCaixaMouseReleased

    private void btnAbrirCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirCaixaActionPerformed
        ABRIR_CAIXA abrir = new ABRIR_CAIXA(new javax.swing.JFrame(), true);
        abrir.setVisible(true);
    }//GEN-LAST:event_btnAbrirCaixaActionPerformed

    private void btnSangriaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSangriaFocusGained
        carregaTabela();
    }//GEN-LAST:event_btnSangriaFocusGained

    private void btnSangriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSangriaMouseEntered
        btnSangria.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnSangriaMouseEntered

    private void btnSangriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSangriaMouseExited
        btnSangria.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnSangriaMouseExited

    private void btnSangriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSangriaActionPerformed

        Sangria_PDV sangria = new Sangria_PDV(new javax.swing.JFrame(), true);
        sangria.pegaCaixa(this);
        sangria.setVisible(true);

    }//GEN-LAST:event_btnSangriaActionPerformed

    private void btnEncerrarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEncerrarFocusGained
        carregaTabela();        // TODO add your handling code here:
    }//GEN-LAST:event_btnEncerrarFocusGained

    private void btnEncerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEncerrarMouseEntered
        btnEncerrar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnEncerrarMouseEntered

    private void btnEncerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEncerrarMouseExited
        btnEncerrar.setBackground(Color.white);
    }//GEN-LAST:event_btnEncerrarMouseExited

    private void btnEncerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncerrarActionPerformed
        if (this.getStatus().equals("FECHADO")) {
            ANP_CXPDV01 anp = new ANP_CXPDV01(new javax.swing.JFrame(), true);
            anp.setVisible(true);
        } else {

            JD_FECHAR_CAIXA fechar = new JD_FECHAR_CAIXA(new javax.swing.JFrame(), true);
            fechar.pegaCaixa(this);
            carregaTabela();
            fechar.setVisible(true);
        }
    }//GEN-LAST:event_btnEncerrarActionPerformed

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        carregaTabela();

    }//GEN-LAST:event_btnExcluirFocusGained

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(Color.white);        // TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        ExcluirCaixa();
// TODO add your handling code here:
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAbrirCaixaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAbrirCaixaFocusLost
        btnAbrirCaixa.setBackground(new java.awt.Color(0, 153, 153));

    }//GEN-LAST:event_btnAbrirCaixaFocusLost

    private void ckOcultarExibirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ckOcultarExibirMouseClicked
        if (ckOcultarExibir.isSelected()) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            modelo.setNumRows(0);

        } else {
            ModelOperacaoCaixaPDV modCaixa = new ModelOperacaoCaixaPDV();
            modCaixa.setOperacao("VISUALIZAR CAIXA");

            Aprovador_Caixa_PDV aprovador = new Aprovador_Caixa_PDV(new javax.swing.JFrame(), true);
            aprovador.pegaOperacaoCaixa(modCaixa);

            aprovador.setVisible(true);
            resp(aprovador);

            if (this.getPegaRespApv().equals("OK")) {
                carregaTabela();

            } else {
                ckOcultarExibir.setSelected(true);

            }

        }
    }//GEN-LAST:event_ckOcultarExibirMouseClicked

    private void btnDetalhesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnDetalhesFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDetalhesFocusGained

    private void btnDetalhesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetalhesMouseEntered
    btnDetalhes.setBackground(new java.awt.Color(239, 237, 237)); 
    }//GEN-LAST:event_btnDetalhesMouseEntered

    private void btnDetalhesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDetalhesMouseExited
   btnDetalhes.setBackground(new java.awt.Color(255, 255, 255)); 
    }//GEN-LAST:event_btnDetalhesMouseExited

    private void btnDetalhesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalhesActionPerformed

        DETALHE_CAIXA dt = new DETALHE_CAIXA(new javax.swing.JFrame(), true);
        dt.pegaCaixa(this);
        dt.carregaSaldoInicial();
        dt.carregaDados();
        dt.somaTabela();
        dt.setVisible(true);

    }//GEN-LAST:event_btnDetalhesActionPerformed

    private void ckOcultarExibirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckOcultarExibirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckOcultarExibirActionPerformed

    private void btnFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFecharMouseEntered
        btnFechar.setBackground(new java.awt.Color(114, 135, 135));
    }//GEN-LAST:event_btnFecharMouseEntered

    private void btnFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFecharMouseExited
        btnFechar.setBackground(new java.awt.Color(94, 110, 110));
    }//GEN-LAST:event_btnFecharMouseExited

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

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
            java.util.logging.Logger.getLogger(Caixa_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caixa_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caixa_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caixa_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Caixa_PDV dialog = new Caixa_PDV(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAbrirCaixa;
    private javax.swing.JButton btnDetalhes;
    private javax.swing.JButton btnEncerrar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnSangria;
    private javax.swing.JRadioButton ckOcultarExibir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
