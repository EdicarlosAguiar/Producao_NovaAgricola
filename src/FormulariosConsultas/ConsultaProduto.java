/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package FormulariosConsultas;

import FormNotificacao.SelecaoInvalida;
import FormNotificacao.erroPesquisa;
import Formularios.ESTOQUE;

import Model.ModelProduto;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
 * @author Edicarlos
 */
public class ConsultaProduto extends javax.swing.JDialog {

    private String codigo;
    private String codBarra;
    private String nomeProd;
    private String unidade;
    private String preçoVenda;
    private float estoque, valor;

    public float getEstoque() {
        return estoque;
    }

    public void setEstoque(float estoque) {
        this.estoque = estoque;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getPreçoVenda() {
        return preçoVenda;
    }

    public void setPreçoVenda(String preçoVenda) {
        this.preçoVenda = preçoVenda;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getNomeProd() {
        return nomeProd;
    }

    public void setNomeProd(String nomeProd) {
        this.nomeProd = nomeProd;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public String getCodigo() {
        return codigo;
    }
    static ArrayList<String> produtos = new ArrayList();

    public ConsultaProduto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        txtPesquisa.requestFocus();

        Cores cor = new Cores();
        // tabela.setSelectionBackground(cor.getCorLinhaSelecionada());
        //Metodo altera a altura do cabeçalho da tabela
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);
    }

    public void buscaProduto() {

        Cores cor = new Cores();
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setNumRows(0);
        int totalItensArray = produtos.size();
        int indice = 0;
        boolean busca = false;
        while (busca == false) {

            String pesquisaArray = produtos.get(indice + 1);
            pesquisaArray = pesquisaArray.substring(0, txtPesquisa.getText().length());
            String pesquisaForm = txtPesquisa.getText();

            if (pesquisaArray.equals(pesquisaForm)) {

                modelo.addRow(new Object[]{
                    tabela.getRowCount() + 1,
                    produtos.get(indice),//Codigo de Barras
                    produtos.get(indice + 8),//Codigo do Produto
                    produtos.get(indice + 1),//Nome do Produto
                    produtos.get(indice + 2),//Unidade de Medida
                    produtos.get(indice + 3), //Preco de Venda
                }
                );
                indice = indice + 9;
            } else if (totalItensArray - 9 <= indice) {
                busca = true;
            } else {
                indice = indice + 9;
            }

        }
        CorLinhaTabela();

    }

    public void carregaTabela() {
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        int indice = 0;
        int totalItensArray = produtos.size();
        while (totalItensArray - 9 >= indice) {
            modelo.addRow(new Object[]{
                tabela.getRowCount() + 1,
                produtos.get(indice),//Codigo de Barras
                produtos.get(indice + 8),//Codigo do Produto
                produtos.get(indice + 1),//Nome do Produto
                produtos.get(indice + 2),//Unidade de Medida
                produtos.get(indice + 3), //Preco de Venda
            }
            );
            indice = indice + 9;
        }

        CorLinhaTabela();

    }

    public void pesquisaPorCodBarra() {
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select codBarra, codigo,nome,und,precoVenda"
                    + " from tb_produtos where codBarra =?");
            String pesquisa = txtPesquisa.getText();
            pst.setString(1, pesquisa);
            rs = pst.executeQuery();

            while (rs.next()) {

                {

                    DecimalFormat df = new DecimalFormat("#,##0.0000");

                    Double precoPrduto = Double.parseDouble(rs.getString("precoVenda").replace(".", "").replaceAll(",", "."));
                    float estoque = rs.getFloat("atual");

                    String preco = df.format(precoPrduto);
                    String estoqueSaldo = df.format(estoque);

                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(1),//Codigo de Barras
                        rs.getString(2),//Codigo do Produto
                        rs.getString(3),//Nome do Produto
                        rs.getString(4),//Unidade de Medida
                        preco,//Preco
                    }
                    );

                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();

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
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);

                    esquerda.setBackground(c);
                    direita.setBackground(c);

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
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);

                    esquerda.setBackground(c);
                    direita.setBackground(c);

                }

                // 
                return label;
            }

        });

    }

    public void carregaArrayProdutos() {
   
        produtos.clear();
        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select nome,und,codigo,precoVenda,"
                    + "codCat,categoria,codSub,subCategoria,codBarra,atual,valor from tb_produtos order by codigo asc");
            rs = pst.executeQuery();

            while (rs.next()) {

                produtos.add(rs.getString("codBarra")); //0 Codigo de Abrras
                produtos.add(rs.getString("nome")); //1 Nome do Produto
                produtos.add(rs.getString("und")); //2 Unidade de Medida
                produtos.add(rs.getString("precoVenda")); //3
                produtos.add(rs.getString("codCat"));//4
                produtos.add(rs.getString("categoria"));//5
                produtos.add(rs.getString("codSub"));//6
                produtos.add(rs.getString("subCategoria"));//7
                produtos.add(rs.getString("codigo")); //10 Codigo Interno

            }
      
            conn.getConexao().close();
        } catch (Exception e) {
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
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        cbTipoPesquisa = new javax.swing.JComboBox<>();
        txtPesquisa = new javax.swing.JTextField();
        btnBucar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        tabela.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indece", "Cod Barra", "Codigo", "Descrição do Produto", "U_M", "Preco de Venda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        tabela.setRowHeight(25);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setMinWidth(1);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
            tabela.getColumnModel().getColumn(0).setMaxWidth(1);
            tabela.getColumnModel().getColumn(3).setMinWidth(450);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(450);
            tabela.getColumnModel().getColumn(3).setMaxWidth(450);
        }

        cbTipoPesquisa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Codigo de Barra" }));
        cbTipoPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoPesquisaActionPerformed(evt);
            }
        });

        txtPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesquisaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesquisaFocusLost(evt);
            }
        });
        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        btnBucar.setText("Buscar");
        btnBucar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBucarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Yes.png"))); // NOI18N
        jButton2.setText("Ok");
        jButton2.setToolTipText("");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton2)
                .addGap(10, 10, 10))
        );

        jPanel9.setBackground(new java.awt.Color(94, 110, 110));

        lblTitulo.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Consulta padrão - Cadastro de Produtos");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBucar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBucar, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        int linhaSelecionada = tabela.getSelectedRow();
        codBarra = tabela.getValueAt(linhaSelecionada, 1).toString();
        codigo = tabela.getValueAt(linhaSelecionada, 2).toString();
        nomeProd = tabela.getValueAt(linhaSelecionada, 3).toString();
        unidade = tabela.getValueAt(linhaSelecionada, 4).toString();
        preçoVenda = tabela.getValueAt(linhaSelecionada, 5).toString();


    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed

        int tecla = 10;
        int tecla2 = evt.getKeyCode();

        if (tecla == tecla2) {

            this.dispose();
        } else {

        }

    }//GEN-LAST:event_tabelaKeyPressed

    private void btnBucarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBucarActionPerformed

        String tipo_pesquisa = (String) cbTipoPesquisa.getSelectedItem();

        if (txtPesquisa.getText().equals("")) {
            carregaTabela();
            tabela.setRowSelectionInterval(0, 0);
            tabela.requestFocus();
        } else if (tipo_pesquisa == "Nome") {
            buscaProduto();
            tabela.setRowSelectionInterval(0, 0);
            tabela.requestFocus();

        } else {
            pesquisaPorCodBarra();
            tabela.setRowSelectionInterval(0, 0);
            tabela.requestFocus();

        }
    }//GEN-LAST:event_btnBucarActionPerformed

    private void tabelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyReleased
        int linhaSelecionada = tabela.getSelectedRow();
        codBarra = tabela.getValueAt(linhaSelecionada, 1).toString();
        codigo = tabela.getValueAt(linhaSelecionada, 2).toString();
        nomeProd = tabela.getValueAt(linhaSelecionada, 3).toString();
        unidade = tabela.getValueAt(linhaSelecionada, 4).toString();
        preçoVenda = tabela.getValueAt(linhaSelecionada, 5).toString();


    }//GEN-LAST:event_tabelaKeyReleased

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        btnBucar.requestFocus();
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased

    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void cbTipoPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoPesquisaActionPerformed
        txtPesquisa.setText("");
        txtPesquisa.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_cbTipoPesquisaActionPerformed

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        if (txtPesquisa.getText().equals("")) {

        } else {
            txtPesquisa.setText(txtPesquisa.getText().toUpperCase());
        }
    }//GEN-LAST:event_txtPesquisaFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusGained
      txtPesquisa.selectAll();
    }//GEN-LAST:event_txtPesquisaFocusGained

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
            java.util.logging.Logger.getLogger(ConsultaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConsultaProduto dialog = new ConsultaProduto(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBucar;
    private javax.swing.JComboBox<String> cbTipoPesquisa;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
