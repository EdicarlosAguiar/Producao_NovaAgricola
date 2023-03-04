/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import FormNotificacao.PROD_NC;
import FormNotificacao.SelecaoInvalida;
import FormNotificacao.exclusaoFinalizada;
import static Formularios.BASE_REQ1.Ops;
import FormulariosConsultas.ConsultaCentroCusto;
import FormulariosConsultas.ConsultaEquipamentos;

import FormulariosConsultas.ConsultaFornecedor;

import FormulariosConsultas.ConsultaProduto2;
import Model.ModelKardex;
import Model.ModelProduto;
import Model.ModelUsuario;

import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import util.Calculos;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class NOVO_FORM_COMPRA extends javax.swing.JFrame {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());
    String codCat;
    String cat;
    String codSub;
    String sub;
    float estoque;
    float valorEstoque;
    float novoEstoque;
    float novoValor;
    float quantidadeNaTabela;
    float valorNaTabela;
    private int parametroImport1;
    private int parametroImport2;

    public int getParametroImport1() {
        return parametroImport1;
    }

    public void setParametroImport1(int parametroImport1) {
        this.parametroImport1 = parametroImport1;
    }

    public int getParametroImport2() {
        return parametroImport2;
    }

    public void setParametroImport2(int parametroImport2) {
        this.parametroImport2 = parametroImport2;
    }

    BASE_COMPRA base = new BASE_COMPRA();
    utilitario util = new utilitario();

    public NOVO_FORM_COMPRA() {
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));
        initComponents();
        lblMsgExclusao.setVisible(false);
        txtValorFrete.setText("0,00");
        this.setExtendedState(MAXIMIZED_BOTH);
        buscaUltimaCompra();
        txtDocumento.requestFocus();
        util.inserirIcon(this);
        configInicias();

        jPanel3.requestFocus();
        txtData.requestFocus();
        txtCodInterno.setVisible(false);

        txtCentroCusto.setEnabled(false);
        txtEquipamento.setEnabled(false);
        cbOp.setEnabled(false);
        txtHodometro.setEnabled(false);
        txtHodometro.setText(String.valueOf(0));

    }

    public void configInicias() {

        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        lblTitulo.setText("Documento de entrada - Incluir");
        //Cores de Paineis e Titulos
        Cores cor = new Cores();
        lblTitulo.setBackground(cor.getCorPreenchimentoTituloBase());
        painelCorpoForm.setBackground(cor.getCorPainelCorpoForm());

        //Cores da Tabela
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabeçalho
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabeçalho

        //tabela.setValueAt(String.valueOf(1), 0, 0);
        // tabela.setValueAt(String.valueOf(1), 0, 12);
        //Cores da fonte dos campos de entrada de texto
        txtCodCompra.setForeground(cor.getFontePradraoCamposEntrada());
        txtData.setForeground(cor.getFontePradraoCamposEntrada());
        txtCodForn.setForeground(cor.getFontePradraoCamposEntrada());
        txtFornecedor.setForeground(cor.getFontePradraoCamposEntrada());
        txtFornecedor.setBackground(cor.getCorCampoDesabilitado());
        cbCondPagamento.setForeground(cor.getFontePradraoCamposEntrada());
        cbFormaPagamento.setForeground(cor.getFontePradraoCamposEntrada());
        //     txtCodigo.setForeground(cor.getFontePradraoCamposEntrada());
        txtValorFinal.setForeground(cor.getCorTotal());
        txtDesconto.setForeground(cor.getCorDesconto());
        txtSubTotal.setForeground(cor.getSubTotal());

        //Outras configurações de tabela.
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 11));
        tabela.setForeground(cor.getCorFonteDadosTabela());

        //Outras Configurações
        // txtData.setDateFormatString((dataAtual));
        //   buscaUltimaCompra();
        txtSubTotal.setText("0,00");
        txtDesconto.setText("0,00");
        txtValorFinal.setText("0,00");
        txtCodCompra.setEnabled(false);
        txtCodCompra.setBackground(cor.getCorCampoDesabilitado());
        txtSubTotal.setEditable(false);
        txtValorFinal.setEditable(false);
        txtSubTotal.setBackground(Color.WHITE);
        txtValorFinal.setBackground(Color.WHITE);
        txtFornecedor.setEnabled(false);
        txtFornecedor.setBackground(cor.getCorCampoDesabilitado());
        CorLinhaTabela();
        btnCancelar.setBackground(Color.WHITE);
    }

    public void verificaDuplicidadeDeProduto() {

        /*
        Esse metodo vai verificar se o mesmo produto foi digitado duas vezes no ato da compra
        Caso tenha sido, ele vai atualizar o estoque do item
        
         */
        int lin = 1;
        int totalLin = tabela.getRowCount();
        float quantidade = Float.parseFloat(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));

        while (lin <= totalLin) {

            String codigo = tabela.getValueAt(lin - 1, 3).toString();
            if (codigo.equals(txtCodInterno.getText())) {
                float quantidadeNaTabela = Float.parseFloat(tabela.getValueAt(lin - 1, 6).toString().replace(".", "").replaceAll(",", "."));
                estoque = quantidadeNaTabela + quantidade;
            } else {

            }
            lin = lin + 1;
        }
    }

    public void pegaCodigo(ModelProduto mod) {

        txtCodInterno.setText(String.valueOf(mod.getProdutoPesquisado()));
        buscaProduto();

    }

    public void CorLinhaTabela() {
        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                    tabela.getColumnModel().getColumn(1).setCellRenderer(centro);//Item
                    tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Cod Barra
                    tabela.getColumnModel().getColumn(3).setCellRenderer(centro);//Codigo INterno
                    tabela.getColumnModel().getColumn(4).setCellRenderer(esquerda);//Produto
                    tabela.getColumnModel().getColumn(5).setCellRenderer(centro);//Unidade
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qauntidade
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//Preco
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);//Total
                    tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);//Cd Cat
                    tabela.getColumnModel().getColumn(10).setCellRenderer(esquerda);//Categoria
                    tabela.getColumnModel().getColumn(11).setCellRenderer(esquerda); //Equipamente
                    tabela.getColumnModel().getColumn(12).setCellRenderer(esquerda); //Horimetro
                    tabela.getColumnModel().getColumn(13).setCellRenderer(esquerda); //Horimetro

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

                    tabela.getColumnModel().getColumn(1).setCellRenderer(centro);//Item
                    tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Cod Barra
                    tabela.getColumnModel().getColumn(3).setCellRenderer(centro);//Codigo INterno
                    tabela.getColumnModel().getColumn(4).setCellRenderer(esquerda);//Produto
                    tabela.getColumnModel().getColumn(5).setCellRenderer(centro);//Unidade
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qauntidade
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//Preco
                    tabela.getColumnModel().getColumn(8).setCellRenderer(direita);//Total
                    tabela.getColumnModel().getColumn(9).setCellRenderer(esquerda);//Cd Cat
                    tabela.getColumnModel().getColumn(10).setCellRenderer(esquerda);//Categoria
                    tabela.getColumnModel().getColumn(11).setCellRenderer(esquerda); //Equipamente
                    tabela.getColumnModel().getColumn(12).setCellRenderer(esquerda); //Horimetro
                    tabela.getColumnModel().getColumn(13).setCellRenderer(esquerda); //Horimetro

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;

                }

                return label;
            }

        });

    }
    Conexao conn = new Conexao();
    ModelUsuario user = new ModelUsuario();

    public void salvarCompra() {

        try {

            int total_linha = tabela.getRowCount();
            int incremente_linda = 1;

            while (total_linha >= incremente_linda) {

                PreparedStatement pst = conn.getConexao().prepareStatement("insert into compra(codigo,data,codForn,nomeForn,condPagamento,"
                        + "formPagamento,"
                        + "codProduto,nomeProduto,unidade, quantidade,preco,total,desconto,valorFinal,dataDigitacao,usuario,documento,"
                        + "valorFinalDouble,qtdeFloat,precoFloat,totalFloat,descontoFloat,codCat,cat,codSub,"
                        + "sub, dataReferencia,codBarra,at_estoque,ordem_pro,centro_custo,equipamento,hor_hod,natureza_finan)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pst.setInt(1, Integer.parseInt(txtCodCompra.getText()));//Codigo da Compra
                pst.setString(2, txtData.getText()); // Data da Compra
                pst.setInt(3, Integer.parseInt(txtCodForn.getText())); // Codigo do Fornecedor
                pst.setString(4, txtFornecedor.getText());//txtFornecedor.getText()); //Nome do Fornecedor
                pst.setString(5, (String) cbCondPagamento.getSelectedItem());//Codicoes de Pagamento
                pst.setString(6, (String) cbFormaPagamento.getSelectedItem()); //Forma de Pagamento

                //Itens da tabela
                pst.setString(28, tabela.getValueAt(incremente_linda - 1, 2).toString()); //Codigo de Barras
                pst.setString(7, tabela.getValueAt(incremente_linda - 1, 3).toString()); //Codigo Produto
                pst.setString(8, tabela.getValueAt(incremente_linda - 1, 4).toString()); //Nome Produto
                pst.setString(9, tabela.getValueAt(incremente_linda - 1, 5).toString()); //Unidade
                pst.setString(10, tabela.getValueAt(incremente_linda - 1, 6).toString()); //Quantidade Produto
                pst.setString(11, tabela.getValueAt(incremente_linda - 1, 7).toString()); //Preço Produto
                pst.setString(12, tabela.getValueAt(incremente_linda - 1, 8).toString()); //Total Produto

                double quantidade = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 6).toString().toString().replace(".", "").replaceAll(",", "."));
                double preco = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 7).toString().toString().replace(".", "").replaceAll(",", "."));
                //Variaveis
                double total_item = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 8).toString().replace(".", "").replaceAll(",", "."));
                double sub_total = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
                double desc_proporcional = desconto / sub_total;
                double valor_final_item = total_item - (total_item * desc_proporcional);
                double desc_item = total_item * desc_proporcional;

                //Convertenado pra String - Formatando o valor
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String totalFormatadoItem = df.format(valor_final_item);
                String desconFormatado = df.format(desc_proporcional);
                String desc_do_item = df.format(desc_item);

                pst.setString(13, desc_do_item); //Desconto
                pst.setString(14, totalFormatadoItem); //Valor Final
                pst.setString(15, dataDigiatacao);// Data Digitação
                pst.setString(16, user.getUsuarioLogado()); //Usuario
                pst.setString(17, txtDocumento.getText()); // Documento
                pst.setDouble(18, (Double) valor_final_item);  //Valor Final 2
                pst.setFloat(19, (float) quantidade); // Quatidade 2
                pst.setFloat(20, (float) preco); // Preco 2
                pst.setFloat(21, (float) total_item); // Tota 2
                pst.setFloat(22, (float) desc_item); // Desconto 2

                pst.setString(23, ""); //Codigo da Categoria
                pst.setString(24, ""); //Categoria
                pst.setString(25, ""); //Codigo da Sub Categoria
                pst.setString(26, ""); //Sub Categoria
                pst.setString(27, dataReferencia); //Data Referencia*/
                pst.setString(29, (String) cbAtEstoque.getSelectedItem()); //Atualiza estoque?
                pst.setString(30, tabela.getValueAt(incremente_linda - 1, 9).toString()); //Ordem Produção
                pst.setString(31, tabela.getValueAt(incremente_linda - 1, 10).toString()); //Centro de Custo
                pst.setString(32, tabela.getValueAt(incremente_linda - 1, 11).toString()); //Equipamento
                pst.setString(33, tabela.getValueAt(incremente_linda - 1, 12).toString()); //Horimetro
                pst.setString(34, tabela.getValueAt(incremente_linda - 1, 13).toString()); //Natureza

                pst.execute();
                incremente_linda++;
                pst.close();
            }
            Confirmacao conf = new Confirmacao(this, true);
            conf.textoCompraSalva();
            conf.setVisible(true);

            if (cbAtEstoque.getSelectedItem().equals("Sim")) {
                atualizaEstoque();
                BASE_COMPRA compra = new BASE_COMPRA();
                compra.carregaTabela();
                compra.setVisible(true);
                this.dispose();

            } else {
                BASE_COMPRA compra = new BASE_COMPRA();
                compra.carregaTabela();
                compra.setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);

        }
    }

    public void atualizaEstoque() {

        int totalProdutos = tabela.getRowCount();
        int increment = 1;
        try {
            while (totalProdutos >= increment) {

                Conexao conn = new Conexao();
                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE tb_produtos SET atual=atual+?,"
                        + "valor=valor+?,dataUltCompra=?,custoMedio=valor/atual where codigo =?");

                pst.setFloat(1, Float.parseFloat(tabela.getValueAt(increment - 1, 6).toString().replace(".", "").replaceAll(",", ".")));//Atualizar o saldo do produto
                pst.setFloat(2, Float.parseFloat(tabela.getValueAt(increment - 1, 8).toString().replace(".", "").replaceAll(",", ".")));//Atualizar o saldo do produto
                pst.setString(3, dataDigiatacao);//Atualiza Valor
                pst.setString(4, (String) tabela.getValueAt(increment - 1, 3));

                pst.executeUpdate();

                increment++;
                pst.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque! " + e, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void atualizaEstoqueAposExclusao() {

        int totalProdutos = tabela.getRowCount();
        int increment = 1;
        try {
            while (totalProdutos >= increment) {

                Conexao conn = new Conexao();
                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE tb_produtos SET valor=valor-?,"
                        + "atual=atual-?, custoMedio=(valor/atual)"
                        + "where codigo =?");

                pst.setFloat(2, Float.parseFloat(tabela.getValueAt(increment - 1, 6).toString().replace(".", "").replaceAll(",", ".")));//Atualizar o saldo do produto
                pst.setFloat(1, Float.parseFloat(tabela.getValueAt(increment - 1, 8).toString().replace(".", "").replaceAll(",", ".")));//Atualizar o saldo do produto

                pst.setString(3, (String) tabela.getValueAt(increment - 1, 3));

                pst.executeUpdate();

                increment++;
                pst.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque! " + e, "Atualização de Estoque", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void alterarCompra() {

        try {

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a alteração na compra " + txtCodCompra.getText() + "?", "Alteração de Compra", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                int total_linha = tabela.getRowCount();
                int incremente_linda = 1;

                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE compra SET data=?,codForn=?,nomeForn=?,condPagamento=?,"
                        + "formPagamento=?,"
                        + "codProduto=?,nomeProduto=?,unidade=?,quantidade=?,preco=?,total=?,desconto=?,valorFinal=?,dataDigitacao=?,"
                        + "usuario=?,documento=?,valorFinalDouble=?,qtdeFloat=?,precoFloat=?,totalFloat=?,descontoFloat=?,codCat=?,"
                        + "cat=?,codSub=?,sub=? where codigo =?");

                pst.setInt(26, Integer.parseInt(txtCodCompra.getText()));//Codigo da Compra
                pst.setString(1, txtData.getText()); // Data da Compra
                pst.setInt(2, Integer.parseInt(txtCodForn.getText())); // Codigo do Fornecedor
                pst.setString(3, txtFornecedor.getText()); //Nome do Fornecedor
                pst.setString(4, (String) cbCondPagamento.getSelectedItem());//Codicoes de Pagamento
                pst.setString(5, (String) cbFormaPagamento.getSelectedItem()); //Forma de Pagamento

                while (total_linha >= incremente_linda) {

                    //Itens da tabela
                    pst.setString(6, tabela.getValueAt(incremente_linda - 1, 1).toString()); //Codigo Produto
                    pst.setString(7, tabela.getValueAt(incremente_linda - 1, 2).toString()); //Nome Produto
                    pst.setString(8, tabela.getValueAt(incremente_linda - 1, 3).toString()); //Unidade Produto
                    pst.setString(9, tabela.getValueAt(incremente_linda - 1, 4).toString()); //Quantidade Produto
                    pst.setString(10, tabela.getValueAt(incremente_linda - 1, 5).toString()); //Preço Produto
                    pst.setString(11, tabela.getValueAt(incremente_linda - 1, 6).toString()); //Total Produto

                    double quantidade = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 4).toString().toString().replace(".", "").replaceAll(",", "."));
                    double preco = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 5).toString().toString().replace(".", "").replaceAll(",", "."));
                    //Variaveis
                    double total_item = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 6).toString().replace(".", "").replaceAll(",", "."));
                    double sub_total = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                    double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
                    double desc_proporcional = desconto / sub_total;
                    double valor_final_item = total_item - (total_item * desc_proporcional);
                    double desc_item = total_item * desc_proporcional;

                    //Convertenado pra String - Formatando o valor
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    String totalFormatadoItem = df.format(valor_final_item);
                    String desconFormatado = df.format(desc_proporcional);
                    String desc_do_item = df.format(desc_item);

                    pst.setString(12, desc_do_item); //Desconto
                    pst.setString(13, totalFormatadoItem); //Valor Final
                    pst.setString(14, dataDigiatacao);// Data Digitação
                    pst.setString(15, user.getUsuarioLogado()); //Usuario
                    pst.setString(16, txtDocumento.getText()); // Documento
                    pst.setFloat(17, (float) valor_final_item);  //Valor Final 2
                    pst.setFloat(18, (float) quantidade); // Quatidade 2
                    pst.setFloat(19, (float) preco); // Preco 2
                    pst.setFloat(20, (float) total_item); // Tota 2
                    pst.setFloat(21, (float) desc_item); // Desconto 2
                    pst.setString(22, tabela.getValueAt(incremente_linda - 1, 8).toString()); //Codigo da Categoria
                    pst.setString(23, tabela.getValueAt(incremente_linda - 1, 9).toString()); //Categoria
                    pst.setString(24, tabela.getValueAt(incremente_linda - 1, 10).toString()); //Codigo da Sub Categoria
                    pst.setString(25, tabela.getValueAt(incremente_linda - 1, 11).toString()); //Sub Categoria
                    pst.executeUpdate();
                    incremente_linda++;
                }

                //          PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE compra SET data=? where codigo =?");
                //        pst.setString(1, txtData.getText()); // Data da Compra
                //      pst.setInt(2, Integer.parseInt(txtCodCompra.getText())); // Codigo
                //    pst.executeUpdate();
                Confirmacao ok = new Confirmacao(new javax.swing.JFrame(), true);
                ok.setVisible(true);
                BASE_COMPRA compra = new BASE_COMPRA();
                compra.setVisible(true);
                this.dispose();
                pst.close();
            } else {

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);
        }

    }

    public void excluirCompra() {

        try {

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão da compra " + txtCodCompra.getText() + "?", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                PreparedStatement pst = conn.getConexao().prepareStatement("delete from compra where codigo =?");
                pst.setInt(1, Integer.parseInt(txtCodCompra.getText()));
                pst.executeUpdate();

                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
                conf.textoCompraExcluida();
                conf.setVisible(true);

                BASE_COMPRA base = new BASE_COMPRA();
                base.carregaTabela();
                base.setVisible(true);
                this.dispose();
                pst.close();
            } else {

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir compra");
        }

        if (cbAtEstoque.getSelectedItem().equals("Sim")) {
            atualizaEstoqueAposExclusao();
            BASE_COMPRA compra = new BASE_COMPRA();
            compra.carregaTabela();
            compra.setVisible(true);
            this.dispose();

        } else {
            BASE_COMPRA compra = new BASE_COMPRA();
            compra.carregaTabela();
            compra.setVisible(true);
            this.dispose();
        }

    }

    public void carregaTabela() {

        tabela.removeAll();
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
            pst = conn.getConexao().prepareStatement("select * from compra where codigo =?");
            BASE_COMPRA base = new BASE_COMPRA();
            pst.setString(1, txtCodCompra.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                {
                    txtCodForn.setText(String.valueOf(rs.getInt("codForn")));
                    txtFornecedor.setText(rs.getString("nomeForn"));
                    txtDocumento.setText(rs.getString("documento"));
                    cbCondPagamento.setSelectedItem(rs.getString("condPagamento"));//
                    cbFormaPagamento.setSelectedItem(rs.getString("formPagamento"));
                    txtData.setText(rs.getString("data"));
                    cbAtEstoque.setSelectedItem(rs.getString("at_estoque"));

                    modelo.addRow(new Object[]{
                        linha,//Indice,
                        linha,//Item
                        rs.getString("codBarra"),//Codigo de Barra
                        rs.getString("codProduto"),//Codigo do Produto
                        rs.getString("nomeProduto"),//Descrição do Produto
                        rs.getString("unidade"),//Unidade de Medida
                        rs.getString("quantidade"),//Quantidade
                        rs.getString("preco"),//Preço
                        rs.getString("total"),//Total
               
                        rs.getString("ordem_pro"),//ORdem de Producao
                        rs.getString("centro_custo"),//SCentro de Custo
                        rs.getString("equipamento"),//Equipamento
                        rs.getString("hor_hod"),//Horimetro
                        rs.getString("natureza_finan"),//Horimetro
                    }
                    );

                }
                linha = linha + 1;
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
        }
        CorLinhaTabela();

    }

    public void tituloIncluir() {
        lblTitulo.setText("Documento de entrada - Incluir");
        lblMsgExclusao.setVisible(false);
    }

    public void tituloAlterar() {
        txtDescPorc.setText("0,00");
        lblTitulo.setText("Documento de entrada - Alterar");
        lblMsgExclusao.setVisible(false);
    }

    public void tituloExcluir() {
        lblTitulo.setText("Documento de entrada - Excluir");
        lblMsgExclusao.setVisible(true);
        painelTitulo.setBackground(new Color(255, 255, 204));
    }

    public void addItemTabela() {
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        String contItem = null;

        if ((tabela.getRowCount() + 1) < 10) {
            contItem = "0000" + (tabela.getRowCount() + 1);
        } else if ((tabela.getRowCount() + 1) < 100 && (tabela.getRowCount() + 1) > 9) {
            contItem = "000" + (tabela.getRowCount() + 1);
        } else {
            contItem = "00" + (tabela.getRowCount() + 1);
        }
        float valorItem = Float.parseFloat(txtTotal.getText().replace(".", "").replaceAll(",", "."));
        float quantidade = Float.parseFloat(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));
        model.addRow(new Object[]{
            (tabela.getRowCount() + 1),
            contItem,
            txtCodBarra.getText(),
            txtCodBarra.getText(),
            lblProduto.getText(),
            lblUnidade.getText(),
            txtQuantidade.getText(),
            txtPreco.getText(),
            txtTotal.getText(),
            cbOp.getSelectedItem(),
            txtCentroCusto.getText(),
            txtEquipamento.getText(),
            txtHodometro.getText(),
            cbNaturezaFinanceira.getSelectedItem()
        }
        );
        txtCodBarra.setText("");
        txtCodInterno.setText("");
        lblProduto.setText("");
        lblUnidade.setText("");
        txtQuantidade.setText("0,00");
        txtPreco.setText("0,00");
        txtTotal.setText("0,00");
        cbOp.setSelectedItem("Não se aplica");
        txtCentroCusto.setText("");
        txtEquipamento.setText("");
        txtHodometro.setText(String.valueOf(0));
        txtCodBarra.requestFocus();

    }

    public void buscaProduto() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos where codigo = ?");
            pst.setString(1, (String) tabela.getValueAt(tabela.getSelectedRow(), 2));
            rs = pst.executeQuery();

            while (rs.next()) {
                tabela.setValueAt(" " + rs.getString(3), tabela.getSelectedRow(), 3);
                tabela.setValueAt(rs.getString(4), tabela.getSelectedRow(), 4);
                tabela.setValueAt(rs.getString(16), tabela.getSelectedRow(), 8);

                tabela.setValueAt(rs.getString(5), tabela.getSelectedRow(), 9);
                tabela.setValueAt(rs.getString(6), tabela.getSelectedRow(), 10);
                tabela.setValueAt(rs.getString(7), tabela.getSelectedRow(), 11);
                tabela.setValueAt(rs.getString(8), tabela.getSelectedRow(), 12);

            }

            conn.getConexao().close();
            pst.close();
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }

    }

    public void buscaProdutoPeloCodBarra() {
        txtCodInterno.setText(""); //Codigo Interno
        lblProduto.setText("");//Nome do Produto
        lblUnidade.setText(""); //Unidade
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select codigo,nome,"
                    + "und from tb_produtos where codigo = ?");
            pst.setString(1, txtCodBarra.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtCodInterno.setText(rs.getString("codigo")); //Codigo Interno
                lblProduto.setText(rs.getString("nome"));//Nome do Produto
                lblUnidade.setText(rs.getString("und")); //Unidade
            }

            conn.getConexao().close();
            pst.close();
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void buscaFornecedor() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select razaoSocial from fornecedor where ID = ?");
            pst.setString(1, txtCodForn.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtFornecedor.setText(rs.getString(1));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void buscaUltimaCompra() {

        try {
            Conexao conn = new Conexao();

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select codigo from compra");
            rs = pst.executeQuery();

            while (rs.next()) {
                int ultimaCompra = rs.getInt(1);
                int novaCompra = ultimaCompra + 1;
                txtCodCompra.setText(String.valueOf(novaCompra));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }

        if (txtCodCompra.getText().equals("")) {
            txtCodCompra.setText(String.valueOf(1));
        } else {

        }
    }

    public void somaTabela() {

        double somaTotal = 0;
        for (int i = 0; i < tabela.getRowCount(); i++) {
            somaTotal += Double.parseDouble(tabela.getValueAt(i, 8).toString().toString().replace(".", "").replaceAll(",", "."));
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(somaTotal);
        txtSubTotal.setText(totalFormatado);
        valorFinal();
        CorLinhaTabela();
    }

    public void valorFinal() {

        double subTotal = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
        double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
        double frete = Double.parseDouble(txtValorFrete.getText().replace(".", "").replaceAll(",", "."));

        double valorFinal = subTotal - desconto + frete;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(valorFinal);
        txtValorFinal.setText(totalFormatado);
    }

    public void atualizaValor() {

        int linha = tabela.getSelectedRow();
        Calculos calc = new Calculos();

        double quantidade = Double.parseDouble(tabela.getValueAt(linha, 6).toString().replace(".", "").replaceAll(",", "."));
        double preco = Double.parseDouble(tabela.getValueAt(linha, 7).toString().replace(".", "").replaceAll(",", "."));

        double total = quantidade * preco;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(total);

        tabela.setValueAt(totalFormatado, linha, 8);

        somaTabela();
    }

    public void pegaCompra(BASE_COMPRA base) {
        txtCodCompra.setText(String.valueOf(base.getCompraSelecionada()));
    }

    public void buscaDesconto() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select desconto from compra where codigo = ?");

            pst.setString(1, txtCodCompra.getText());
            rs = pst.executeQuery();
            //   double resultado = Double.parseDouble(rs.getString(1).replace(",", ".").replaceAll(".", ""));

            // DecimalFormat df = new DecimalFormat("#,##0.00");
            //String totalFormatado = df.format(valorFinal);
            //txtValorFinal.setText("R$ " + totalFormatado);
            while (rs.next()) {
                String desc = (rs.getString(1)).replace(".", "").replaceAll(",", ".");
                double desconto = +Double.parseDouble(desc);

                DecimalFormat df = new DecimalFormat("#,##0.00");
                String descontoFormatado = df.format(desconto);
                txtDesconto.setText(descontoFormatado);

            }
            conn.getConexao().close();
            pst.close();
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void parametrosVisualizacao() {

        Cores cor = new Cores();
        lblTitulo.setText("Compras - Visualizar");

        txtCodInterno.setEnabled(false);
        txtCodInterno.setBackground(cor.getCorCampoDesabilitado());
        txtCodCompra.setEnabled(false);
        txtCodCompra.setBackground(cor.getCorCampoDesabilitado());
        txtData.setEnabled(false);
        txtDocumento.setEnabled(false);
        txtCodForn.setEnabled(false);
        txtFornecedor.setEnabled(false);
        cbCondPagamento.setEnabled(false);
        cbFormaPagamento.setEnabled(false);
        txtSubTotal.setEnabled(false);
        txtDesconto.setEnabled(false);
        txtValorFinal.setEnabled(false);
        btnConfirmar.setEnabled(false);
        txtSubTotal.setBackground(Color.WHITE);
        txtDesconto.setBackground(Color.WHITE);
        txtValorFinal.setBackground(Color.WHITE);
        lblMsgExclusao.setVisible(false);

    }

    public void buscaCompra() {

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
            pst = conn.getConexao().prepareStatement("select * from compra where codigo =?");
            //  pst.setInt(1, Integer.parseInt(txtParametro1.getText()));
            rs = pst.executeQuery();

            while (rs.next()) {

                String preco = rs.getString(15);
                double preco2 = Double.parseDouble(String.valueOf(preco).replace(".", "").replaceAll(",", "."));
                double preceFinal = preco2 - (preco2 * 0.6);
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String precoFormatado = df.format(preceFinal);
                txtCodCompra.setText(String.valueOf(rs.getInt(2)));
                //   txtData.add(rs.getString(3));
                txtData.setText(rs.getString(3));
                txtDocumento.setText(rs.getString(18));
                {
                    modelo.addRow(new Object[]{
                        linha,
                        rs.getString(8),//Codigo do Produto
                        rs.getString(9),//Nome do Produto
                        rs.getString(10),//Unidade de Medida
                        rs.getString(11),//Unidade de Medida
                        rs.getString(12),//Unidade de Medida
                        rs.getString(13),//Unidade de Medida
                        "",
                        rs.getString(24),//Codigo daa Categoria
                        rs.getString(25),//Categoria
                        rs.getString(26),//Codigo da Sub Categoria
                        rs.getString(25),//Sub Categria
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

    public void importarProdutos() {

        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        //   modelo.setNumRows(0);
        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos where ID >=? and ID <=?");
            pst.setInt(1, parametroImport1);
            pst.setInt(2, parametroImport2);
            rs = pst.executeQuery();

            while (rs.next()) {

                String preco = rs.getString(15);
                String qtde = rs.getString(18);
                double preco2 = Double.parseDouble(String.valueOf(preco).replace(".", "").replaceAll(",", "."));
                double quantidade = 1;

                double preceFinal = preco2 - (preco2 * 0.4);
                double total = preceFinal * quantidade;

                DecimalFormat df = new DecimalFormat("#,##0.00");
                String precoFormatado = df.format(preceFinal);
                String totalFormatado = df.format(total);
                String quantidadeFormatado = df.format(quantidade);

                {

                    String contItem = null;
                    if ((tabela.getRowCount() + 1) < 10) {
                        contItem = "0000" + (tabela.getRowCount() + 1);
                    } else if ((tabela.getRowCount() + 1) < 100 && (tabela.getRowCount() + 1) > 9) {
                        contItem = "000" + (tabela.getRowCount() + 1);
                    } else {
                        contItem = "00" + (tabela.getRowCount() + 1);
                    }

                    modelo.addRow(new Object[]{
                        linha,
                        contItem,
                        rs.getString(16),//Codigo de Barras
                        rs.getString(2),//Codigo do Produto
                        rs.getString(3),//Nome do Produto
                        rs.getString(4),//Unidade de Medida
                        quantidadeFormatado,
                        precoFormatado,//Preco
                        totalFormatado,//Total
                        rs.getString(5),//Codigo daa Categoria
                        rs.getString(6),//Categoria
                        rs.getString(7),//Codigo da Sub Categoria
                        rs.getString(8),//Sub Categria
                    }
                    );

                }
                linha = linha + 1;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }
        CorLinhaTabela();
        somaTabela();
    }

    public void excluirLinhaTablea() {

    }

    public void carregaCombo() {
        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select ordem_pro,hectare,"
                    + "cultura,estagio from plantio where FinCiclo =?");
            pst.setString(1, "  /  /    ");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbOp.addItem(rs.getString("ordem_pro"));
            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combo de Ops " + ex);
        }
    }

    public void carregaComboNateueza() {
        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select natureza from natureza_finan where tipo = 'DESPESA'");
            rs = pst.executeQuery();
            while (rs.next()) {
                cbNaturezaFinanceira.addItem(rs.getString("natureza"));
            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combo de Ops " + ex);
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

        painelCorpoForm = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        PainelRodape = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JFormattedTextField();
        txtDesconto = new javax.swing.JTextField();
        txtDescPorc = new javax.swing.JFormattedTextField();
        txtValorFinal = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtValorFrete = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCodBarra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtQuantidade = new javax.swing.JTextField();
        txtPreco = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        txtCentroCusto = new javax.swing.JTextField();
        txtEquipamento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbOp = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        txtHodometro = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cbNaturezaFinanceira = new javax.swing.JComboBox<>();
        lblProduto = new javax.swing.JLabel();
        lblUnidade = new javax.swing.JLabel();
        painelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblMsgExclusao = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtCodCompra = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtCodForn = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cbCondPagamento = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cbFormaPagamento = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        txtFornecedor = new javax.swing.JTextField();
        txtData = new javax.swing.JFormattedTextField();
        cbTipoDoc = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cbAtEstoque = new javax.swing.JComboBox<>();
        txtCodInterno = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Movimentos de Entrada");

        painelCorpoForm.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpoForm.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Listagem de Produtos");

        PainelRodape.setBackground(new java.awt.Color(94, 110, 110));
        PainelRodape.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        PainelRodape.setForeground(new java.awt.Color(0, 51, 102));
        PainelRodape.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jPanel5.setForeground(new java.awt.Color(0, 51, 102));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Valor Total dos Produtos (=):");

        txtSubTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtSubTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtDesconto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDesconto.setForeground(new java.awt.Color(102, 102, 102));
        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDesconto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescontoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoFocusLost(evt);
            }
        });
        txtDesconto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDescontoMouseClicked(evt);
            }
        });
        txtDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescontoActionPerformed(evt);
            }
        });

        txtDescPorc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtDescPorc.setForeground(new java.awt.Color(255, 0, 51));
        txtDescPorc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("R$ #,##0.00"))));
        txtDescPorc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescPorc.setFont(new java.awt.Font("Tahoma", 1, 9)); // NOI18N
        txtDescPorc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescPorcFocusLost(evt);
            }
        });

        txtValorFinal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtValorFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValorFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorFinal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Desc %:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Valor Fina(=):");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Desconto (-):");

        txtValorFrete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtValorFrete.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValorFrete.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorFrete.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtValorFrete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtValorFreteFocusLost(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Frete (+):");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(txtSubTotal))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValorFrete)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDescPorc)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValorFinal)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(660, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtValorFrete))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubTotal))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtDescPorc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        PainelRodape.addTab("Totais", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PainelRodape.addTab("Outros Valores", jPanel6);

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setOpaque(true);

        jPanel4.setBackground(new java.awt.Color(235, 235, 235));

        jLabel5.setText("Codigo:");

        txtCodBarra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodBarra.setForeground(new java.awt.Color(51, 51, 51));
        txtCodBarra.setPreferredSize(new java.awt.Dimension(10, 25));
        txtCodBarra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBarraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBarraFocusLost(evt);
            }
        });
        txtCodBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodBarraMouseClicked(evt);
            }
        });
        txtCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBarraActionPerformed(evt);
            }
        });
        txtCodBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodBarraKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodBarraKeyReleased(evt);
            }
        });

        jLabel9.setText("Quantidade:");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/OK.png"))); // NOI18N
        btnAdicionar.setText("Inserir");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indice", "Item", "Cod Barra", "Codigo", "Descrição do Produto", "Unidade", "Quantidade", "Preco Unit", "Valor Total", "Ordem Prod", "Centro Custo", "Equipamento", "Hor/Hod", "Natureza Financeira"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new java.awt.Color(204, 204, 204));
        tabela.setRowHeight(25);
        tabela.setRowMargin(2);
        tabela.setSelectionBackground(new java.awt.Color(0, 102, 153));
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tabela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tabelaFocusLost(evt);
            }
        });
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
            tabela.getColumnModel().getColumn(1).setMinWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setMaxWidth(50);
            tabela.getColumnModel().getColumn(2).setMinWidth(0);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(2).setMaxWidth(0);
            tabela.getColumnModel().getColumn(4).setMinWidth(300);
            tabela.getColumnModel().getColumn(13).setMinWidth(200);
            tabela.getColumnModel().getColumn(13).setPreferredWidth(200);
            tabela.getColumnModel().getColumn(13).setMaxWidth(200);
        }

        txtQuantidade.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtQuantidade.setForeground(new java.awt.Color(51, 51, 51));
        txtQuantidade.setPreferredSize(new java.awt.Dimension(10, 25));
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantidadeFocusLost(evt);
            }
        });
        txtQuantidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtQuantidadeMouseClicked(evt);
            }
        });
        txtQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantidadeActionPerformed(evt);
            }
        });

        txtPreco.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPreco.setForeground(new java.awt.Color(51, 51, 51));
        txtPreco.setPreferredSize(new java.awt.Dimension(10, 25));
        txtPreco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecoFocusLost(evt);
            }
        });
        txtPreco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecoMouseClicked(evt);
            }
        });
        txtPreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoActionPerformed(evt);
            }
        });

        txtTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(51, 51, 51));
        txtTotal.setPreferredSize(new java.awt.Dimension(10, 25));

        jLabel11.setText("Preço:");

        jLabel12.setText("Total:");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/Delete.png"))); // NOI18N
        jButton6.setText("Remover");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        txtCentroCusto.setEnabled(false);
        txtCentroCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCentroCustoKeyPressed(evt);
            }
        });

        txtEquipamento.setEnabled(false);
        txtEquipamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEquipamentoKeyPressed(evt);
            }
        });

        jLabel7.setText("Ord Prod:");

        jLabel13.setText("C. Custo:");

        jLabel14.setText("Eqp/Prefixo:");

        cbOp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não se aplica" }));

        jLabel15.setText("Hod/Hor:");

        txtHodometro.setEnabled(false);
        txtHodometro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHodometroKeyPressed(evt);
            }
        });

        jLabel16.setText("Natureza Financeira:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(cbOp, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(txtEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(txtHodometro, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbNaturezaFinanceira, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtPreco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cbOp, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel15)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtHodometro, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel13)
                                                .addComponent(jLabel14))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel16)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(cbNaturezaFinanceira, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(1, 1, 1))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
        );

        lblProduto.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblProduto.setForeground(new java.awt.Color(0, 102, 153));
        lblProduto.setMaximumSize(new java.awt.Dimension(338, 25));
        lblProduto.setMinimumSize(new java.awt.Dimension(338, 25));
        lblProduto.setPreferredSize(new java.awt.Dimension(338, 25));

        lblUnidade.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblUnidade.setForeground(new java.awt.Color(0, 102, 153));
        lblUnidade.setPreferredSize(new java.awt.Dimension(130, 25));

        painelTitulo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(51, 51, 51)));
        painelTitulo.setForeground(new java.awt.Color(204, 204, 204));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(38, 85, 100));
        lblTitulo.setText("Documento de entrada - INCLUIR");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        btnConfirmar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
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

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setAlignmentY(0.8F);
        btnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setMargin(new java.awt.Insets(8, 5, 8, 5));
        btnCancelar.setMaximumSize(new java.awt.Dimension(32, 50));
        btnCancelar.setMinimumSize(new java.awt.Dimension(32, 50));
        btnCancelar.setOpaque(true);
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblMsgExclusao.setBackground(new java.awt.Color(255, 255, 102));
        lblMsgExclusao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMsgExclusao.setForeground(new java.awt.Color(51, 51, 51));
        lblMsgExclusao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/24x24/Error.png"))); // NOI18N
        lblMsgExclusao.setText("Atenção...! Essa operação não poderá ser desfeita após a confirmação!");

        javax.swing.GroupLayout painelTituloLayout = new javax.swing.GroupLayout(painelTitulo);
        painelTitulo.setLayout(painelTituloLayout);
        painelTituloLayout.setHorizontalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMsgExclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        painelTituloLayout.setVerticalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addGroup(painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnConfirmar)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(lblMsgExclusao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(1310, 200));
        jPanel3.setMinimumSize(new java.awt.Dimension(1310, 200));
        jPanel3.setPreferredSize(new java.awt.Dimension(1310, 200));

        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Codigo:");

        txtCodCompra.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCodCompra.setForeground(new java.awt.Color(51, 51, 51));
        txtCodCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodCompra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodCompra.setEnabled(false);

        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Emissão:");

        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Cod Forn:");

        txtCodForn.setForeground(new java.awt.Color(51, 51, 51));
        txtCodForn.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodForn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodForn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFornFocusLost(evt);
            }
        });
        txtCodForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFornActionPerformed(evt);
            }
        });
        txtCodForn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodFornKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodFornKeyReleased(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Fornecedor:");

        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Cond Pagamento:");

        cbCondPagamento.setForeground(new java.awt.Color(51, 51, 51));
        cbCondPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A VISTA", "A PRAZO", "AJUSTE DE ESTOQUE" }));
        cbCondPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbCondPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCondPagamentoActionPerformed(evt);
            }
        });
        cbCondPagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbCondPagamentoKeyPressed(evt);
            }
        });

        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("Form Pagamento:");

        cbFormaPagamento.setForeground(new java.awt.Color(51, 51, 51));
        cbFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DINHEIRO", "PIX", "CARTAO DE CREDITO", "CARTAO DEBITO", "BOLETO BANCARIO", "DEPOSITO/TRANSFERENCIA", "CHEQUE", "AJUSTE DE ESTOQUE" }));
        cbFormaPagamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbFormaPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbFormaPagamentoFocusLost(evt);
            }
        });
        cbFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFormaPagamentoActionPerformed(evt);
            }
        });
        cbFormaPagamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbFormaPagamentoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbFormaPagamentoKeyReleased(evt);
            }
        });

        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("Documento:");

        txtDocumento.setForeground(new java.awt.Color(51, 51, 51));
        txtDocumento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDocumento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        txtFornecedor.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtFornecedor.setEnabled(false);

        try {
            txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });

        cbTipoDoc.setForeground(new java.awt.Color(51, 51, 51));
        cbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOTA FISCAL", "RECIBO", "BOLETO", "PROMISSORIA", "OUTROS", "AJUSTE DE ESTOQUE" }));
        cbTipoDoc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbTipoDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbTipoDocFocusLost(evt);
            }
        });
        cbTipoDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoDocActionPerformed(evt);
            }
        });
        cbTipoDoc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTipoDocKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbTipoDocKeyReleased(evt);
            }
        });

        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Tipo do Documento:");

        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("Atualiza Estoque?");

        cbAtEstoque.setForeground(new java.awt.Color(51, 51, 51));
        cbAtEstoque.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sim", "Não" }));
        cbAtEstoque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbAtEstoque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbAtEstoqueFocusLost(evt);
            }
        });
        cbAtEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAtEstoqueActionPerformed(evt);
            }
        });
        cbAtEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbAtEstoqueKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbAtEstoqueKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22)
                            .addComponent(cbCondPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtCodCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(txtCodForn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jLabel29)
                        .addComponent(jLabel21))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(jLabel19))
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCodCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodForn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbAtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTipoDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCondPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73))
        );

        jScrollPane2.setViewportView(jPanel3);

        txtCodInterno.setEnabled(false);
        txtCodInterno.setPreferredSize(new java.awt.Dimension(10, 25));

        javax.swing.GroupLayout painelCorpoFormLayout = new javax.swing.GroupLayout(painelCorpoForm);
        painelCorpoForm.setLayout(painelCorpoFormLayout);
        painelCorpoFormLayout.setHorizontalGroup(
            painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(painelCorpoFormLayout.createSequentialGroup()
                .addGroup(painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(painelCorpoFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelCorpoFormLayout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(lblProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)
                                .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(PainelRodape, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        painelCorpoFormLayout.setVerticalGroup(
            painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoFormLayout.createSequentialGroup()
                .addComponent(painelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodInterno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(PainelRodape, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpoForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCorpoForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodFornFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFornFocusLost
        txtFornecedor.setText("");
        buscaFornecedor();
    }//GEN-LAST:event_txtCodFornFocusLost

    private void txtCodFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFornActionPerformed
        txtFornecedor.setText("");
        cbCondPagamento.requestFocus();
        buscaFornecedor();
    }//GEN-LAST:event_txtCodFornActionPerformed

    private void txtCodFornKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodFornKeyPressed

        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaFornecedor consultaForn = new ConsultaFornecedor(this, true);
            consultaForn.carregaTabela();
            consultaForn.setVisible(true);
            String codigo = consultaForn.getCodigo();
            String nome = consultaForn.getNome();
            txtCodForn.setText(codigo);
            txtFornecedor.setText(nome);
            txtCodBarra.requestFocus();
        } else {

        }


    }//GEN-LAST:event_txtCodFornKeyPressed

    private void txtCodFornKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodFornKeyReleased

    }//GEN-LAST:event_txtCodFornKeyReleased

    private void cbCondPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCondPagamentoActionPerformed
        cbFormaPagamento.requestFocus();
    }//GEN-LAST:event_cbCondPagamentoActionPerformed

    private void cbCondPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCondPagamentoKeyPressed
        cbFormaPagamento.requestFocus();
    }//GEN-LAST:event_cbCondPagamentoKeyPressed

    private void cbFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormaPagamentoActionPerformed
        txtCodBarra.requestFocus();
    }//GEN-LAST:event_cbFormaPagamentoActionPerformed

    private void cbFormaPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFormaPagamentoKeyPressed
        txtCodBarra.requestFocus();

    }//GEN-LAST:event_cbFormaPagamentoKeyPressed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        txtCodForn.requestFocus();
    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyReleased
    }//GEN-LAST:event_tabelaKeyReleased

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
            case "Documento de entrada - Incluir":
                salvarCompra();
                break;
            case "Documento de entrada - Alterar":
                alterarCompra();

                break;
            case "Documento de entrada - Excluir":
                excluirCompra();
                break;
        }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        BASE_COMPRA compra = new BASE_COMPRA();
        compra.carregaTabela();
        CorLinhaTabela();
        compra.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered

        btnCancelar.setBackground(new java.awt.Color(239, 237, 237));        // TODO add your handling code here:

        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(Color.WHITE);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMouseExited

    private void tabelaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaFocusLost

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed

    }//GEN-LAST:event_tabelaKeyPressed

    private void cbFormaPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbFormaPagamentoFocusLost

        // TODO add your handling code here:
    }//GEN-LAST:event_cbFormaPagamentoFocusLost

    private void tabelaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaFocusGained
// TODO add your handling code here:
    }//GEN-LAST:event_tabelaFocusGained

    private void txtCodBarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraKeyReleased
        int tecla = 116;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            IMPOT_DADOS_COMPRA importarDados = new IMPOT_DADOS_COMPRA(this, true);
            importarDados.setVisible(true);

            this.setParametroImport1(importarDados.getParametroInicial());
            this.setParametroImport2(importarDados.getParametroFinal());

            importarProdutos();

        } else {

        }
    }//GEN-LAST:event_txtCodBarraKeyReleased

    private void cbFormaPagamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFormaPagamentoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFormaPagamentoKeyReleased

    private void txtCodBarraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarraFocusLost
        if (txtCodBarra.getText().equals("")) {
            //   txtQuantidade.requestFocus();
        } else if (lblProduto.getText().equals("")) {
            buscaProdutoPeloCodBarra();
            //      txtQuantidade.requestFocus();
        } else {
            //    txtQuantidade.requestFocus();
        }

    }//GEN-LAST:event_txtCodBarraFocusLost

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        String geraEstoque = (String) cbAtEstoque.getSelectedItem();
        if (geraEstoque == "Não") {

            if (cbOp.getSelectedItem().equals("Não se aplica") && txtCentroCusto.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Para compra que não geram estoque, é necessario informar Odem de Produção "
                        + "ou Centro de Custo");
            } else {
                //  verificaDuplicidadeDeProduto();
                addItemTabela();
                somaTabela();
            }

        } else {
            //  verificaDuplicidadeDeProduto();
            addItemTabela();
            somaTabela();
        }


    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusLost
        if (txtQuantidade.getText().equals("")) {

        } else {
            DecimalFormat df = new DecimalFormat("#,##0.00");

            Double qtde = Double.parseDouble(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));

            String quantidade = df.format(qtde);
            txtQuantidade.setText(quantidade);

        }
    }//GEN-LAST:event_txtQuantidadeFocusLost

    private void txtQuantidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusGained
        txtQuantidade.selectAll();


    }//GEN-LAST:event_txtQuantidadeFocusGained

    private void txtQuantidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQuantidadeMouseClicked
        txtQuantidade.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantidadeMouseClicked

    private void txtPrecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoFocusLost
        if (txtPreco.getText().equals("")) {

        } else {

            DecimalFormat df = new DecimalFormat("#,##0.00");

            Double prc = Double.parseDouble(txtPreco.getText().replace(".", "").replaceAll(",", "."));

            String preco = df.format(prc);
            txtPreco.setText(preco);

            //Calculo o Total
            Double qtde = Double.parseDouble(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));

            Double total = qtde * prc;
            String totalFormat = df.format(total);
            txtTotal.setText(totalFormat);

            btnAdicionar.requestFocus();
        }
    }//GEN-LAST:event_txtPrecoFocusLost

    private void txtPrecoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoFocusGained
        txtPreco.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoFocusGained

    private void txtPrecoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecoMouseClicked
        txtPreco.selectAll();         // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoMouseClicked

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Double qtde = Double.parseDouble(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));
        String quantidade = df.format(qtde);
        txtQuantidade.setText(quantidade);
        txtPreco.requestFocus();

    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void txtPrecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoActionPerformed
        DecimalFormat df = new DecimalFormat("#,##0.00");

        Double prc = Double.parseDouble(txtPreco.getText().replace(".", "").replaceAll(",", "."));

        String preco = df.format(prc);
        txtPreco.setText(preco);

        //Calculo o Total
        Double qtde = Double.parseDouble(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));

        Double total = qtde * prc;
        String totalFormat = df.format(total);
        txtTotal.setText(totalFormat);

        btnAdicionar.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoActionPerformed

    private void txtCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBarraActionPerformed
        if (txtCodBarra.getText().equals("")) {
            txtQuantidade.requestFocus();
        } else if (lblProduto.getText().equals("")) {
            //  buscaProdutoPeloCodBarra();
            txtQuantidade.requestFocus();
        } else {
            txtQuantidade.requestFocus();
        }

    }//GEN-LAST:event_txtCodBarraActionPerformed

    private void txtCodBarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraKeyPressed

        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            txtCodBarra.setText("");
            ConsultaProduto2 consulta = new ConsultaProduto2(this, true);
            consulta.carregaTabela();
            consulta.setVisible(true);
            String codigo = consulta.getCodBarra();
            String nome = consulta.getNomeProd();
            String codProd = consulta.getCodigo();
            String und = consulta.getUnidade();
            float saldoQuant = consulta.getEstoque();
            float saldoValor = consulta.getValor();

            txtCodBarra.setText(codProd);
            txtCodInterno.setText(codProd);
            lblProduto.setText(nome);
            lblUnidade.setText(und);
            estoque = saldoQuant;
            valorEstoque = saldoValor;
            txtQuantidade.requestFocus();

        } else {

        }

    }//GEN-LAST:event_txtCodBarraKeyPressed

    private void txtCodBarraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarraFocusGained
        tabela.clearSelection();
        txtCodBarra.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodBarraFocusGained

    private void txtCodBarraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodBarraMouseClicked
        txtCodBarra.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodBarraMouseClicked

    private void cbTipoDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbTipoDocFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoDocFocusLost

    private void cbTipoDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoDocActionPerformed

    private void cbTipoDocKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoDocKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoDocKeyPressed

    private void cbTipoDocKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoDocKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoDocKeyReleased

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        txtDocumento.requestFocus();
// TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tabela.getModel();
        dtm.removeRow(tabela.getSelectedRow());
        somaTabela();
        valorFinal();
        txtCodBarra.requestFocus();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void cbAtEstoqueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbAtEstoqueFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAtEstoqueFocusLost

    private void cbAtEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAtEstoqueActionPerformed
        if (cbAtEstoque.getSelectedItem().equals("Sim")) {
            cbOp.setEnabled(false);
            txtCentroCusto.setEnabled(false);
            txtEquipamento.setEnabled(false);
            txtHodometro.setEnabled(false);
            txtHodometro.setText(String.valueOf(0));

        } else {
            cbOp.setEnabled(true);
            txtCentroCusto.setEnabled(true);
            txtEquipamento.setEnabled(true);
            txtHodometro.setEnabled(true);
        }
    }//GEN-LAST:event_cbAtEstoqueActionPerformed

    private void cbAtEstoqueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbAtEstoqueKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAtEstoqueKeyPressed

    private void cbAtEstoqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbAtEstoqueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbAtEstoqueKeyReleased

    private void txtCentroCustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCentroCustoKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            txtCentroCusto.setText("");
            ConsultaCentroCusto centroCusto = new ConsultaCentroCusto(this, true);
            centroCusto.carregaArray();
            centroCusto.carregaTabela();
            centroCusto.setVisible(true);
            String codCentroCusto = centroCusto.getCodigo();
            txtCentroCusto.setText(codCentroCusto);

        } else {

        };
    }//GEN-LAST:event_txtCentroCustoKeyPressed

    private void txtEquipamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEquipamentoKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            txtEquipamento.setText("");
            ConsultaEquipamentos centroEq = new ConsultaEquipamentos(this, true);
            centroEq.carregaArray();
            centroEq.carregaTabela();
            centroEq.setVisible(true);
            String codEquipamento = centroEq.getCodigo();
            txtEquipamento.setText(codEquipamento);

        } else {

        }
    }//GEN-LAST:event_txtEquipamentoKeyPressed

    private void txtValorFreteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFreteFocusLost
        if (txtValorFrete.getText().equals("0,00")) {
            txtDesconto.requestFocus();

        } else {
            valorFinal();
            txtDesconto.requestFocus();
        }
    }//GEN-LAST:event_txtValorFreteFocusLost

    private void txtDescPorcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescPorcFocusLost
        Calculos calc = new Calculos();

        double percentagem = Double.parseDouble(txtDescPorc.getText()) / 100;
        double valor = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));

        double desconto = valor * percentagem;

        txtDescPorc.setText(percentagem + "%");
        txtDesconto.setText(String.valueOf(desconto));
    }//GEN-LAST:event_txtDescPorcFocusLost

    private void txtDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoActionPerformed
        btnConfirmar.requestFocus();
    }//GEN-LAST:event_txtDescontoActionPerformed

    private void txtDescontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescontoMouseClicked
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoMouseClicked

    private void txtDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusLost
        if (txtDesconto.getText().equals("0,00")) {
            btnConfirmar.requestFocus();
        } else {
            double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
            DecimalFormat df = new DecimalFormat("#,##0.00");
            String totalFormatado = df.format(desconto);
            txtDesconto.setText(totalFormatado);
            valorFinal();

            double desc_total = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
            double sub_total = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
            double desc_proporcional = desc_total / sub_total * 100;
            String desc_prop_format = df.format(desc_proporcional);
            txtDescPorc.setText(desc_prop_format + "%");
            btnConfirmar.requestFocus();
        }
    }//GEN-LAST:event_txtDescontoFocusLost

    private void txtDescontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusGained
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoFocusGained

    private void txtHodometroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHodometroKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHodometroKeyPressed

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
            java.util.logging.Logger.getLogger(NOVO_FORM_COMPRA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NOVO_FORM_COMPRA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NOVO_FORM_COMPRA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NOVO_FORM_COMPRA.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NOVO_FORM_COMPRA().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane PainelRodape;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cbAtEstoque;
    private javax.swing.JComboBox<String> cbCondPagamento;
    private javax.swing.JComboBox<String> cbFormaPagamento;
    private javax.swing.JComboBox<String> cbNaturezaFinanceira;
    private javax.swing.JComboBox<String> cbOp;
    private javax.swing.JComboBox<String> cbTipoDoc;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMsgExclusao;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUnidade;
    private javax.swing.JPanel painelCorpoForm;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCentroCusto;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JTextField txtCodCompra;
    private javax.swing.JTextField txtCodForn;
    private javax.swing.JTextField txtCodInterno;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JFormattedTextField txtDescPorc;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtFornecedor;
    private javax.swing.JTextField txtHodometro;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JFormattedTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JFormattedTextField txtValorFinal;
    private javax.swing.JFormattedTextField txtValorFrete;
    // End of variables declaration//GEN-END:variables
}
