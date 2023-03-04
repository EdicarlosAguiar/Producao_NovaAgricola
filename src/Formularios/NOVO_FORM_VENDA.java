/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.Confirmacao;
import FormNotificacao.PROD_NC;
import FormNotificacao.SelecaoInvalida;
import FormNotificacao.exclusaoFinalizada;
import FormulariosConsultas.ConsultaCliente;

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
public class NOVO_FORM_VENDA extends javax.swing.JFrame {

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

    public NOVO_FORM_VENDA() {
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));
        initComponents();
        lblMsgExclusao.setVisible(false);
        txtValorFrete.setText("0,00");
        this.setExtendedState(MAXIMIZED_BOTH);
        txtDocumento.requestFocus();
        util.inserirIcon(this);
        configInicias();

        jPanel3.requestFocus();
        txtData.requestFocus();

    }

    public void pegaVenda(BROWSER_VENDAS base) {
        txtCodVenda.setText(String.valueOf(base.getVendaSelecionada()));
    }

    public void configInicias() {

        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);

        Cores cor = new Cores();
        lblTitulo.setBackground(cor.getCorPreenchimentoTituloBase());
        painelCorpoForm.setBackground(cor.getCorPainelCorpoForm());

        //Cores da Tabela
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabeçalho
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabeçalho

        txtCodVenda.setForeground(cor.getFontePradraoCamposEntrada());
        txtData.setForeground(cor.getFontePradraoCamposEntrada());
        txtCodCliente.setForeground(cor.getFontePradraoCamposEntrada());
        txtNomeCliente.setForeground(cor.getFontePradraoCamposEntrada());
        txtNomeCliente.setBackground(cor.getCorCampoDesabilitado());
        cbCondPagamento.setForeground(cor.getFontePradraoCamposEntrada());
        cbFormaPagamento.setForeground(cor.getFontePradraoCamposEntrada());

        txtValorFinal.setForeground(cor.getCorTotal());
        txtDesconto.setForeground(cor.getCorDesconto());
        txtSubTotal.setForeground(cor.getSubTotal());

        //Outras configurações de tabela.
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 11));
        tabela.setForeground(cor.getCorFonteDadosTabela());

        txtSubTotal.setText("0,00");
        txtDesconto.setText("0,00");
        txtValorFinal.setText("0,00");
        txtCodVenda.setEnabled(false);
        txtCodVenda.setBackground(cor.getCorCampoDesabilitado());
        txtSubTotal.setEditable(false);
        txtValorFinal.setEditable(false);
        txtSubTotal.setBackground(Color.WHITE);
        txtValorFinal.setBackground(Color.WHITE);
        txtNomeCliente.setEnabled(false);
        txtNomeCliente.setBackground(cor.getCorCampoDesabilitado());
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
                    tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Codigo INterno
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Codigo INterno
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centro);//Produto
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);//Unidade
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qauntidade
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//Preco
                    tabela.getColumnModel().getColumn(8).setCellRenderer(centro);//Total

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
                    tabela.getColumnModel().getColumn(2).setCellRenderer(centro);//Codigo INterno
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Codigo INterno
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centro);//Produto
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);//Unidade
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qauntidade
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//Preco
                    tabela.getColumnModel().getColumn(8).setCellRenderer(centro);//Total

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

    public void limpaTabela() {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

        modelo.setNumRows(0);

    }

    public void salvar() {

        //Validação dos campos obrigatorios
        if (txtNomeCliente.getText().equals("") || txtCodCliente.getText().equals("")
                || txtSubTotal.getText().equals("0,00")) {
            JOptionPane.showMessageDialog(this, "Algum campo obrigatório não foi preenchido!",
                    "AGUITECH", 2);
        } else {

            ModelUsuario user = new ModelUsuario();
            try {

                int total_linha = tabela.getRowCount();
                int linha = 1;

                while (total_linha >= linha) {

                    PreparedStatement pst = conn.getConexao().prepareStatement("insert into venda(codigo,data,"
                            + "codCliente,cliente,codProduto,produto,unidade,"
                            + "quantidade,preco,total,documento,desconto,valorFinal,"
                            + "formPagamento,condPagamento,data2,usuario,ord_prod,tipo_doc)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                    /*  PreparedStatement pst = conn.getConexao().prepareStatement("insert into venda(codigo,data,"
                        + "codCliente,cliente,codProduto,produto,unidade,"
                        + "quantidade,preco, total, documento,desconto,valorFinal,"
                        + "formPagamento,usuario)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");*/
                    pst.setInt(1, Integer.parseInt(txtCodVenda.getText()));//Codigo da Venda
                    pst.setString(2, txtData.getText());  //Data da Venda
                    pst.setInt(3, Integer.parseInt(txtCodCliente.getText()));//Codigo de Cliente
                    pst.setString(4, txtNomeCliente.getText());//Nome do Cliente
                    pst.setString(5, tabela.getValueAt(linha - 1, 2).toString()); //Codigo Produto
                    pst.setString(6, tabela.getValueAt(linha - 1, 3).toString()); //Nome Produto
                    pst.setString(7, tabela.getValueAt(linha - 1, 4).toString()); //Unidade Produto
                    pst.setFloat(8, Float.parseFloat(tabela.getValueAt(linha - 1, 5).toString().toString().replace(".", "").replaceAll(",", ".")));//Quantidade
                    pst.setFloat(9, Float.parseFloat(tabela.getValueAt(linha - 1, 6).toString().toString().replace(".", "").replaceAll(",", ".")));//preco
                    pst.setFloat(10, Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", ".")));//Total
                    pst.setString(11, txtDocumento.getText());

                    //Calculos descontos___________________________!
                    float total_item = Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", "."));//Total
                    float subTotal = Float.parseFloat(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                    float desconto_total = Float.parseFloat(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
                    float desconto_percentual = desconto_total / subTotal;
                    float valorFinal = total_item - (total_item * desconto_percentual);
                    float desconto_no_item = total_item * desconto_percentual;
                    //Fim do calculo do descontos___________________________!

                    pst.setFloat(12, desconto_no_item);
                    pst.setFloat(13, valorFinal);
                    pst.setString(14, (String) cbCondPagamento.getSelectedItem());
                    pst.setString(15, (String) cbFormaPagamento.getSelectedItem());
                    pst.setString(16, dataReferencia);
                    pst.setString(17, user.getUsuarioLogado());
                    pst.setString(18, tabela.getValueAt(linha - 1, 8).toString()); //Ordem de Produção
                    pst.setString(19, (String) cbTipoDoc.getSelectedItem());//Tipo do documento
                    pst.execute();

                    pst.close();
                    linha = linha + 1;
                }
                Confirmacao conf = new Confirmacao(this, true);
                conf.textoVendaSalva();
                conf.setVisible(true);
                buscaUltimaVenda();
                //Limpar os Campos da Tela
                txtValorFinal.setText("0,00");
                txtSubTotal.setText("0,00");
                lblProduto.setText("");
                lblProduto.setText("");

                limpaTabela();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda " + ex);
            }
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

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a alteração na compra " + txtCodVenda.getText() + "?", "Alteração de Compra", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                int total_linha = tabela.getRowCount();
                int incremente_linda = 1;

                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE compra SET data=?,codForn=?,nomeForn=?,condPagamento=?,"
                        + "formPagamento=?,"
                        + "codProduto=?,nomeProduto=?,unidade=?,quantidade=?,preco=?,total=?,desconto=?,valorFinal=?,dataDigitacao=?,"
                        + "usuario=?,documento=?,valorFinalDouble=?,qtdeFloat=?,precoFloat=?,totalFloat=?,descontoFloat=?,codCat=?,"
                        + "cat=?,codSub=?,sub=? where codigo =?");

                pst.setInt(26, Integer.parseInt(txtCodVenda.getText()));//Codigo da Compra
                pst.setString(1, txtData.getText()); // Data da Compra
                pst.setInt(2, Integer.parseInt(txtCodCliente.getText())); // Codigo do Fornecedor
                pst.setString(3, txtNomeCliente.getText()); //Nome do Fornecedor
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

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão da compra " + txtCodVenda.getText() + "?", "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                PreparedStatement pst = conn.getConexao().prepareStatement("delete from venda where codigo =?");
                pst.setInt(1, Integer.parseInt(txtCodVenda.getText()));
                pst.executeUpdate();

                Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);

                conf.setVisible(true);

                BROWSER_VENDAS base = new BROWSER_VENDAS();
                base.carregaTabela();
                base.setVisible(true);
                this.dispose();
                pst.close();
            } else {

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir compra");
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
            pst = conn.getConexao().prepareStatement("select codigo, codCliente,cliente,documento,"
                    + "condPagamento, formPagamento,data,codProduto,produto,unidade,"
                    + "quantidade,preco,total,ord_prod,desconto,tipo_doc from venda where codigo =?");

            pst.setString(1, txtCodVenda.getText());
            rs = pst.executeQuery();
            float descAcumulado = 0;
            while (rs.next()) {
                {
                    DecimalFormat df = new DecimalFormat("#,##0.0000");
                    float quantidade = rs.getFloat("quantidade");
                    float preco = rs.getFloat("preco");
                    float total = rs.getFloat("total");
                    float desconto = rs.getFloat("desconto");
                    descAcumulado = descAcumulado + desconto;

                    String quantFormat = df.format(quantidade);
                    String precoFormat = df.format(preco);
                    String totalFormat = df.format(total);
                    String descFormat = df.format(descAcumulado);

                    txtCodCliente.setText(String.valueOf(rs.getInt("codigo")));
                    txtNomeCliente.setText(rs.getString("cliente"));
                    txtDocumento.setText(rs.getString("documento"));
                    cbCondPagamento.setSelectedItem(rs.getString("condPagamento"));//
                    cbFormaPagamento.setSelectedItem(rs.getString("formPagamento"));
                    cbTipoDoc.setSelectedItem(rs.getString("tipo_doc"));
                    txtData.setText(rs.getString("data"));
                    txtDesconto.setText(descFormat);

                    modelo.addRow(new Object[]{
                        tabela.getRowCount() + 1,//Indice,
                        tabela.getRowCount() + 1,//Item
                        rs.getString("codProduto"),//Codigo do Produto
                        rs.getString("produto"),//Descrição do Produto
                        rs.getString("unidade"),//Unidade de Medida
                        quantFormat,//Quantidade
                        precoFormat,//Preço
                        totalFormat,//Total
                        rs.getString("ord_prod"),//Sub Categora
                    }
                    );

                }
                linha = linha + 1;
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados da venda" + e, "AGUITECH", 3);
        }
        CorLinhaTabela();

    }

    public void tituloIncluir() {
        lblTitulo.setText("Documento de saida - INCLUIR");
        lblMsgExclusao.setVisible(false);
    }

    public void tituloAlterar() {
        txtDescPorc.setText("0,00");
        lblTitulo.setText("Documento de saida - ALTERAR");
        lblMsgExclusao.setVisible(false);
    }

    public void tituloExcluir() {
        lblTitulo.setText("Documento de saida - EXCLUIR");
        lblMsgExclusao.setVisible(true);
        painelTitulo.setBackground(new Color(255, 255, 204));
    }

    public void tituloVisualizar() {
        Cores cor = new Cores();
        txtDescPorc.setText("0,00");
        lblTitulo.setText("Documento de saida - VISUALIZAR");
        txtCodInterno.setEnabled(false);
        txtCodInterno.setBackground(cor.getCorCampoDesabilitado());
        txtCodVenda.setEnabled(false);
        txtCodVenda.setBackground(cor.getCorCampoDesabilitado());
        txtData.setEnabled(false);
        txtDocumento.setEnabled(false);
        txtCodCliente.setEnabled(false);
        txtNomeCliente.setEnabled(false);
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

        //Calculando o custo da requisição
        //  float quantItem = Float.parseFloat(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));
        float valorItem = Float.parseFloat(txtTotal.getText().replace(".", "").replaceAll(",", "."));

        int lin = 1;
        int totalLin = tabela.getRowCount();
        float quantidade = Float.parseFloat(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));

        quantidadeNaTabela = 0;
        valorNaTabela = 0;
        while (lin <= totalLin) {

            String codigo = tabela.getValueAt(lin - 1, 3).toString();

            if (codigo.equals(txtCodInterno.getText())) {

                quantidadeNaTabela += Float.parseFloat(tabela.getValueAt(lin - 1, 6).toString().replace(".", "").replaceAll(",", "."));
                valorNaTabela += Float.parseFloat(tabela.getValueAt(lin - 1, 8).toString().replace(".", "").replaceAll(",", "."));
            } else {

            }
            lin = lin + 1;

        }

        model.addRow(new Object[]{
            (tabela.getRowCount() + 1),
            contItem,
            txtCodInterno.getText(),
            lblProduto.getText(),
            lblUnidade.getText(),
            txtQuantidade.getText(),
            txtPreco.getText(),
            txtTotal.getText(),
            cbOrdemProd.getSelectedItem(),}
        );
        txtCodInterno.setText("");
        txtCodInterno.setText("");
        lblProduto.setText("");
        lblUnidade.setText("");
        txtQuantidade.setText("0,00");
        txtPreco.setText("0,00");
        txtTotal.setText("0,00");
        cbOrdemProd.setSelectedItem("");

        txtCodInterno.requestFocus();

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

        lblProduto.setText("");//Nome do Produto
        lblUnidade.setText(""); //Unidade
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select codigo,nome,und from tb_produtos where codigo = ?");
            pst.setString(1, txtCodInterno.getText());
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
            pst = conn.getConexao().prepareStatement("select razaoSocial from cliente where codigo = ?");
            pst.setString(1, txtCodCliente.getText());
            rs = pst.executeQuery();

            while (rs.next()) {
                txtNomeCliente.setText(rs.getString(1));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void buscaUltimaVenda() {

        try {
            Conexao conn = new Conexao();

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select codigo from venda");
            rs = pst.executeQuery();

            while (rs.next()) {
                int ultimaCompra = rs.getInt(1);
                int novaCompra = ultimaCompra + 1;
                txtCodVenda.setText(String.valueOf(novaCompra));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }

        if (txtCodVenda.getText().equals("")) {
            txtCodVenda.setText(String.valueOf(1));
        } else {

        }
    }

    public void somaTabela() {

        double somaTotal = 0;
        for (int i = 0; i < tabela.getRowCount(); i++) {
            somaTotal += Double.parseDouble(tabela.getValueAt(i, 7).toString().toString().replace(".", "").replaceAll(",", "."));
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
        txtCodVenda.setText(String.valueOf(base.getCompraSelecionada()));
    }

    public void buscaDesconto() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;

            pst = conn.getConexao().prepareStatement("select desconto from compra where codigo = ?");

            pst.setString(1, txtCodVenda.getText());
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
                txtCodVenda.setText(String.valueOf(rs.getInt(2)));
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

    public void carregaOP() {

        try {

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select ordem_pro from plantio where FinCiclo =?");
            pst.setString(1, "  /  /    ");
            rs = pst.executeQuery();

            while (rs.next()) {

                cbOrdemProd.addItem(rs.getString("ordem_pro"));

            }

            conn.getConexao().close();

        } catch (Exception ex) {

        }
    }

    public void carregaNaturezaFinanceira() {

        try {

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select NATUREZA from natureza_finan where TIPO='RECEITA'");
            rs = pst.executeQuery();

            while (rs.next()) {

                cbTipoVenda.addItem(rs.getString("NATUREZA"));

            }

            conn.getConexao().close();

        } catch (Exception ex) {

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
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
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
        txtCodInterno = new javax.swing.JTextField();
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
        jLabel7 = new javax.swing.JLabel();
        cbOrdemProd = new javax.swing.JComboBox<>();
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
        txtCodVenda = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtCodCliente = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cbCondPagamento = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cbFormaPagamento = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        txtNomeCliente = new javax.swing.JTextField();
        txtData = new javax.swing.JFormattedTextField();
        cbTipoDoc = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        cbTipoVenda = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Movimentos de Venda");

        painelCorpoForm.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpoForm.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Listagem de Produtos");

        jTabbedPane1.setBackground(new java.awt.Color(94, 110, 110));
        jTabbedPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        jTabbedPane1.setForeground(new java.awt.Color(0, 51, 102));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

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
                .addContainerGap(687, Short.MAX_VALUE))
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

        jTabbedPane1.addTab("Totais", jPanel5);

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

        jTabbedPane1.addTab("Outros Valores", jPanel6);

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setOpaque(true);

        jPanel4.setBackground(new java.awt.Color(235, 235, 235));

        jLabel5.setText("Codigo:");

        txtCodInterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCodInterno.setForeground(new java.awt.Color(51, 51, 51));
        txtCodInterno.setPreferredSize(new java.awt.Dimension(10, 25));
        txtCodInterno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodInternoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodInternoFocusLost(evt);
            }
        });
        txtCodInterno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodInternoMouseClicked(evt);
            }
        });
        txtCodInterno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodInternoActionPerformed(evt);
            }
        });
        txtCodInterno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodInternoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodInternoKeyReleased(evt);
            }
        });

        jLabel9.setText("Quantidade:");

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/18x18/OK.png"))); // NOI18N
        btnAdicionar.setText("Inserir Item");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Indice", "Item", "Codigo", "Descrição do Produto", "Unidade", "Quantidade", "Preco Unit", "Valor Total", "Ordem Prod"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
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
            tabela.getColumnModel().getColumn(3).setMinWidth(300);
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

        jLabel7.setText("Ord Prod:");

        cbOrdemProd.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Não se aplica" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cbOrdemProd, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtQuantidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbOrdemProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
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

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulo.setText("Documento de saida - INCLUIR");
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
                .addComponent(lblMsgExclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        painelTituloLayout.setVerticalGroup(
            painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTituloLayout.createSequentialGroup()
                .addGroup(painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelTituloLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(painelTituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfirmar)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblMsgExclusao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(1310, 200));
        jPanel3.setMinimumSize(new java.awt.Dimension(1310, 200));
        jPanel3.setPreferredSize(new java.awt.Dimension(1310, 200));

        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Codigo:");

        txtCodVenda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtCodVenda.setForeground(new java.awt.Color(51, 51, 51));
        txtCodVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodVenda.setEnabled(false);

        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Emissão:");

        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Cod Cli:");

        txtCodCliente.setForeground(new java.awt.Color(51, 51, 51));
        txtCodCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodCliente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txtCodCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClienteFocusLost(evt);
            }
        });
        txtCodCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClienteActionPerformed(evt);
            }
        });
        txtCodCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodClienteKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodClienteKeyReleased(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Nome do Cliente:");

        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Cond Pagamento:");

        cbCondPagamento.setForeground(new java.awt.Color(51, 51, 51));
        cbCondPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A VISTA", "A PRAZO" }));
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
        cbFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DINHEIRO", "PIX", "CARTAO DE CREDITO", "CARTAO DEBITO", "BOLETO BANCARIO", "DEPOSITO/TRANSFERENCIA", "CHEQUE" }));
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

        txtNomeCliente.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtNomeCliente.setEnabled(false);

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
        cbTipoDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOTA FISCAL", "RECIBO", "BOLETO", "PROMISSORIA", "OUTROS" }));
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
        jLabel25.setText("Tipo da Venda:");

        cbTipoVenda.setForeground(new java.awt.Color(51, 51, 51));
        cbTipoVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbTipoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbTipoVendaFocusLost(evt);
            }
        });
        cbTipoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoVendaActionPerformed(evt);
            }
        });
        cbTipoVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbTipoVendaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbTipoVendaKeyReleased(evt);
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
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbTipoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtCodVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                            .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(278, Short.MAX_VALUE))
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
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtCodVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                        .addComponent(cbCondPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTipoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73))
        );

        jScrollPane2.setViewportView(jPanel3);

        javax.swing.GroupLayout painelCorpoFormLayout = new javax.swing.GroupLayout(painelCorpoForm);
        painelCorpoForm.setLayout(painelCorpoFormLayout);
        painelCorpoFormLayout.setHorizontalGroup(
            painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(painelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(painelCorpoFormLayout.createSequentialGroup()
                .addGroup(painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCorpoFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1))
                    .addGroup(painelCorpoFormLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCorpoFormLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addGroup(painelCorpoFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(lblProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelCorpoFormLayout.setVerticalGroup(
            painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoFormLayout.createSequentialGroup()
                .addComponent(painelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(painelCorpoFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(lblProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtCodClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClienteFocusLost
        txtNomeCliente.setText("");
        buscaFornecedor();
    }//GEN-LAST:event_txtCodClienteFocusLost

    private void txtCodClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClienteActionPerformed
        txtNomeCliente.setText("");
        cbCondPagamento.requestFocus();
        buscaFornecedor();
    }//GEN-LAST:event_txtCodClienteActionPerformed

    private void txtCodClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodClienteKeyPressed

        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaCliente consulta = new ConsultaCliente(this, true);
            consulta.carregaTabela();
            consulta.setVisible(true);
            String codigo = consulta.getCodigo();
            String nome = consulta.getNome();
            txtCodCliente.setText(codigo);
            txtNomeCliente.setText(nome);
            txtCodInterno.requestFocus();

        } else {

        }


    }//GEN-LAST:event_txtCodClienteKeyPressed

    private void txtCodClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodClienteKeyReleased

    }//GEN-LAST:event_txtCodClienteKeyReleased

    private void cbCondPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCondPagamentoActionPerformed
        cbFormaPagamento.requestFocus();
    }//GEN-LAST:event_cbCondPagamentoActionPerformed

    private void cbCondPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCondPagamentoKeyPressed
        cbFormaPagamento.requestFocus();
    }//GEN-LAST:event_cbCondPagamentoKeyPressed

    private void cbFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormaPagamentoActionPerformed
        txtCodInterno.requestFocus();
    }//GEN-LAST:event_cbFormaPagamentoActionPerformed

    private void cbFormaPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFormaPagamentoKeyPressed
        txtCodInterno.requestFocus();

    }//GEN-LAST:event_cbFormaPagamentoKeyPressed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        txtCodCliente.requestFocus();
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
            case "Documento de saida - INCLUIR":
                salvar();
                break;
            case "Documento de saida - Alterar":
                alterarCompra();

                break;
            case "Documento de saida - EXCLUIR":
                excluirCompra();
                break;
        }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        BROWSER_VENDAS venda = new BROWSER_VENDAS();
        venda.carregaTabela();
        CorLinhaTabela();
        venda.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDescPorcFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescPorcFocusLost
        Calculos calc = new Calculos();

        double percentagem = Double.parseDouble(txtDescPorc.getText()) / 100;
        double valor = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));

        double desconto = valor * percentagem;

        txtDescPorc.setText(percentagem + "%");
        txtDesconto.setText(String.valueOf(desconto));
    }//GEN-LAST:event_txtDescPorcFocusLost

    private void txtDescontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusGained
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoFocusGained

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

    private void txtDescontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescontoMouseClicked
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoMouseClicked

    private void txtDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoActionPerformed
        btnConfirmar.requestFocus();
    }//GEN-LAST:event_txtDescontoActionPerformed

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

    private void txtCodInternoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodInternoKeyReleased
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
    }//GEN-LAST:event_txtCodInternoKeyReleased

    private void cbFormaPagamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFormaPagamentoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbFormaPagamentoKeyReleased

    private void txtCodInternoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodInternoFocusLost
        if (txtCodInterno.getText().equals("")) {
            txtQuantidade.requestFocus();

        } else {
            buscaProdutoPeloCodBarra();
            txtQuantidade.requestFocus();
        }

    }//GEN-LAST:event_txtCodInternoFocusLost

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        //  verificaDuplicidadeDeProduto();
        addItemTabela();
        somaTabela();


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

    private void txtCodInternoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodInternoActionPerformed
        if (txtCodInterno.getText().equals("")) {
            txtQuantidade.requestFocus();

        } else {
            buscaProdutoPeloCodBarra();
            txtQuantidade.requestFocus();
        }

    }//GEN-LAST:event_txtCodInternoActionPerformed

    private void txtCodInternoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodInternoKeyPressed
        txtQuantidade.requestFocus();
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            txtCodInterno.setText("");
            ConsultaProduto2 consulta = new ConsultaProduto2(this, true);
            consulta.carregaTabela();
            consulta.setVisible(true);
            String codigo = consulta.getCodBarra();
            String nome = consulta.getNomeProd();
            String codProd = consulta.getCodigo();
            String und = consulta.getUnidade();

            txtCodInterno.setText(codProd);
            lblProduto.setText(nome);
            lblUnidade.setText(und);

            txtQuantidade.requestFocus();

        } else {

        }

    }//GEN-LAST:event_txtCodInternoKeyPressed

    private void txtCodInternoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodInternoFocusGained
        tabela.clearSelection();
        txtCodInterno.selectAll();
    }//GEN-LAST:event_txtCodInternoFocusGained

    private void txtCodInternoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodInternoMouseClicked
        txtCodInterno.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodInternoMouseClicked

    private void txtValorFreteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorFreteFocusLost
        if (txtValorFrete.getText().equals("0,00")) {
            txtDesconto.requestFocus();

        } else {
            valorFinal();
            txtDesconto.requestFocus();
        }

    }//GEN-LAST:event_txtValorFreteFocusLost

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
        txtCodInterno.requestFocus();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void cbTipoVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbTipoVendaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoVendaFocusLost

    private void cbTipoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoVendaActionPerformed

    private void cbTipoVendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoVendaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoVendaKeyPressed

    private void cbTipoVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbTipoVendaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoVendaKeyReleased

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
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cbCondPagamento;
    private javax.swing.JComboBox<String> cbFormaPagamento;
    private javax.swing.JComboBox<String> cbOrdemProd;
    private javax.swing.JComboBox<String> cbTipoDoc;
    private javax.swing.JComboBox<String> cbTipoVenda;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMsgExclusao;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUnidade;
    private javax.swing.JPanel painelCorpoForm;
    private javax.swing.JPanel painelTitulo;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtCodCliente;
    private javax.swing.JTextField txtCodInterno;
    private javax.swing.JTextField txtCodVenda;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JFormattedTextField txtDescPorc;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtPreco;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JFormattedTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JFormattedTextField txtValorFinal;
    private javax.swing.JFormattedTextField txtValorFrete;
    // End of variables declaration//GEN-END:variables
}
