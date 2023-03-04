/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import ARRAY_STATIC.ArrayVendedores;
import FormNotificacao.Confirmacao;
import FormNotificacao.MSGA_PDV01;
import FormNotificacao.SelecaoInvalida;
import static Formularios.BASE_CATEGORIA.categoriaSelecionada;
import FormulariosConsultas.ConsultaFornecedor;
import Model.ModelKardex;
import Model.ModelOperacaoCaixaPDV;
import Model.ModelUsuario;
import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Edicarlos
 */
public class PDV extends javax.swing.JFrame {

    ArrayList<String> produtos = new ArrayList();
    ArrayList<String> vendedores = new ArrayList();

    private float custoTotal;
    private String statusCaixa;
    private int codCaixa, codVenda;
    private String codBarra, unidade;
    private String produtoSelecioando, parametro_estoque;
    float estoque, valor;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy " + "|" + " HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataInt = DateTimeFormatter.ofPattern("yyyyMMdd");
    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataInteiro = dataInt.format(LocalDateTime.now());
    String preco;
    String totalProduto;
    String codCat, codSub;
    String nomeCat, nomeSub, planoCartao;
    private float estoqueAtual;
    private float valorAtual;
    private float novoEstoque;
    private float novoValor;
    private String pago;
    private int linhaSelecionada;
    private String acaoNoTroco;
    private float comissaoVendedor;
    private float valorDinheiro;
    private float valorCartaoCredito;
    private float valorPix;
    private float valorCartaoDebito;
    private String valorParaCalculoParcela;

    public float getValorDinheiro() {
        return valorDinheiro;
    }

    public void setValorDinheiro(float valorDinheiro) {
        this.valorDinheiro = valorDinheiro;
    }

    public float getValorCartaoCredito() {
        return valorCartaoCredito;
    }

    public void setValorCartaoCredito(float valorCartaoCredito) {
        this.valorCartaoCredito = valorCartaoCredito;
    }

    public float getValorPix() {
        return valorPix;
    }

    public void setValorPix(float valorPix) {
        this.valorPix = valorPix;
    }

    public float getValorCartaoDebito() {
        return valorCartaoDebito;
    }

    public void setValorCartaoDebito(float valorCartaoDebito) {
        this.valorCartaoDebito = valorCartaoDebito;
    }

    public String getPlanoCartao() {
        return planoCartao;
    }

    public void setPlanoCartao(String planoCartao) {
        this.planoCartao = planoCartao;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public String getValorParaCalculoParcela() {
        return valorParaCalculoParcela;
    }

    public void setValorParaCalculoParcela(String valorParaCalculoParcela) {
        this.valorParaCalculoParcela = valorParaCalculoParcela;
    }

    public float getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(float estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public float getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(float valorAtual) {
        this.valorAtual = valorAtual;
    }

    public float getNovoEstoque() {
        return novoEstoque;
    }

    public void setNovoEstoque(float novoEstoque) {
        this.novoEstoque = novoEstoque;
    }

    public float getNovoValor() {
        return novoValor;
    }

    public void setNovoValor(float novoValor) {
        this.novoValor = novoValor;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

    public int getLinhaSelecionada() {
        return linhaSelecionada;
    }

    public void setLinhaSelecionada(int linhaSelecionada) {
        this.linhaSelecionada = linhaSelecionada;
    }
    private static String codAlterar, nomeAlterar, qtdeAlterar, precoAlterar, totalAlterar, codBarraAlterar;

    public String getCodBarraAlterar() {
        return codBarraAlterar;
    }

    public void setCodBarraAlterar(String codBarraAlterar) {
        this.codBarraAlterar = codBarraAlterar;
    }

    public String getCodAlterar() {
        return codAlterar;
    }

    public void setCodAlterar(String codAlterar) {
        this.codAlterar = codAlterar;
    }

    public String getNomeAlterar() {
        return nomeAlterar;
    }

    public void setNomeAlterar(String nomeAlterar) {
        this.nomeAlterar = nomeAlterar;
    }

    public String getQtdeAlterar() {
        return qtdeAlterar;
    }

    public void setQtdeAlterar(String qtdeAlterar) {
        this.qtdeAlterar = qtdeAlterar;
    }

    public String getPrecoAlterar() {
        return precoAlterar;
    }

    public void setPrecoAlterar(String precoAlterar) {
        this.precoAlterar = precoAlterar;
    }

    public String getTotalAlterar() {
        return totalAlterar;
    }

    public void setTotalAlterar(String totalAlterar) {
        this.totalAlterar = totalAlterar;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    Cores cor = new Cores();

    Conexao conn = new Conexao();
    ModelUsuario user = new ModelUsuario();

    public String getProdutoSelecioando() {
        return produtoSelecioando;
    }

    public void setProdutoSelecioando(String produtoSelecioando) {
        this.produtoSelecioando = produtoSelecioando;
    }

    public void acaoTroco(CALCULA_TROCO troco) {
        acaoNoTroco = "";
        acaoNoTroco = troco.getAcao();

        //  JOptionPane.showMessageDialog(null, acaoNoTroco);
        if (acaoNoTroco == "Cancelar") {

        } else {
            salvar_atualizando_caixa();
        }

    }

    public void excluirLinhaTablea() {

        double somaTotal = 0;
        DefaultTableModel dtm = (DefaultTableModel) tabela.getModel();
        dtm.removeRow(tabela.getSelectedRow());
        for (int i = 0; i < tabela.getRowCount(); i++) {

            somaTotal += Double.parseDouble(tabela.getValueAt(i, 6).toString().toString().replace(".", "").replaceAll(",", "."));
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(somaTotal);
        txtSubTotal.setText(totalFormatado);
        descontoCondicional();
        valorFinal();

    }

    public void descontoCondicional() {

        String formaPg = (String) cbFormaPagamento.getSelectedItem();
        String pix = "PIX";
        String din = "DINHEIRO";

        if ((formaPg == pix) || (formaPg == din)) {

            double subTotal = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
            double acrescimo = Double.parseDouble(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));

            double desconto = subTotal * 0.05;

            double valorFinal = subTotal - desconto + acrescimo;

            DecimalFormat df = new DecimalFormat("#,##0.00");
            String descontoFormatado = df.format(desconto);
            String valorFinalFormatado = df.format(valorFinal);
            txtDesconto.setText(descontoFormatado);
            lblValorFinal.setText(valorFinalFormatado);
            txtTaxasCartao.setText("0,00");
            lblParcelamento.setText("");
            cbCondPagamento.setSelectedItem("A VISTA");
            planoCartao = "";

        } else {

            double subTotal = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
            double desconto = 0;
            double valorFinal = subTotal - desconto;

            DecimalFormat df = new DecimalFormat("#,##0.00");
            String descontoFormatado = df.format(desconto);
            String valorFinalFormatado = df.format(valorFinal);
            txtDesconto.setText(descontoFormatado);
            lblValorFinal.setText(valorFinalFormatado);
            btnFinalizar.requestFocus();
            cbCondPagamento.setSelectedItem("A PRAZO");

        }

    }

    public void troco() {

        String formaPg = (String) cbFormaPagamento.getSelectedItem();
        String din = "DINHEIRO";
        CALCULA_TROCO troco = new CALCULA_TROCO(this, true);
        if (formaPg == din) {
            this.setPago(lblValorFinal.getText().replace("R$ ", ""));
            acaoNoTroco = troco.getAcao();
            troco.pegaValor(this);
            troco.setVisible(true);
            acaoNoTroco = troco.getAcao();
            acaoTroco(troco);

        } else {

        }

    }

    utilitario util = new utilitario();

    public PDV() {
        UIManager.put("ComboBox.background", new ColorUIResource(Color.WHITE));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(new java.awt.Color(0, 204, 204)));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(new Color(0, 102, 102)));
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        txtQuantidade.setText("1,00");
        lblUsuario.setText(" " + user.getUsuarioLogado());

        lblCliente.setText("CLIENTE PADRÃO");
        txtItens.setText("0");
        lblValorFinal.setText("R$ 0,00");
        txtDesconto.setText("0,00");
        txtSubTotal.setText("0,00");
        txtAcrescimo.setText("0,00");
        txtTaxasCartao.setText("0,00");
        txtCodBarras.requestFocus();
        lblPrecoVenda.setVisible(false);
        lblCodCat.setVisible(false);
        lblCategoria.setVisible(false);
        lblCodSub.setVisible(false);
        lblSubCategoria.setVisible(false);
        lblEstoqueAtual.setVisible(false);

        lblData.setText(dataDigiatacao);

        buscaUltimaVenda();
        cbBoxInputLeitor.setSelected(true);
        cbBoxCodBarra.setSelected(true);
        txtCodInterno.setEnabled(false);
        this.setExtendedState(MAXIMIZED_BOTH);

        txtDesconto.setBackground(cor.getCorCampoDesabilitado());
        buscaStatusCaixa();
        if (statusCaixa.equals("FECHADO")) {
            JOptionPane.showMessageDialog(null, "Situação atual do caixa: " + statusCaixa, "STATUS CAIXA", 1);
        } else {

        }
        this.setTitle(util.getTituloPrincipal());
        util.inserirIcon(this);

        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));
        int maxHeaderHeight = 40;
        Dimension d = new Dimension(tabela.getTableHeader().getPreferredSize().width, maxHeaderHeight);
        tabela.getTableHeader().setPreferredSize(d);
        //Cores da Tabela
        tabela.getTableHeader().setBackground(cor.getCorPreenchimentoCabecalho());//Cor do preenchimento do cabeçalho
        tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho()); // Cor da fonte do cabeçalho

        carregaComboVendedor();
        lblStatusCaixa.setText("<< Caixa livre >>");
        carregaArrayList();

    }

    public void carregaArrayList() {

        try {

            Conexao conn = new Conexao();
            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select nome,und,codigo,precoVenda,"
                    + "codCat,categoria,codSub,subCategoria,codBarra,atual,valor from tb_produtos");
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
                produtos.add(rs.getString("atual"));//8
                produtos.add(rs.getString("valor"));//9
                produtos.add(rs.getString("codigo")); //10 Codigo Interno

            }

            conn.getConexao().close();
        } catch (Exception e) {
        }

    }

    public void buscaProdutoDoArryay() {

        int indiceArryay = 0;
        boolean buscar = false;

        while (buscar == false) {

            String codigo = produtos.get(indiceArryay).toString();
            String codForm = txtCodBarras.getText();
            JOptionPane.showConfirmDialog(this, txtCodBarras.getText());
            if (codForm.equals(codigo)) {

                buscar = true;
                codBarra = produtos.get(indiceArryay).toString();//Codigo de Abrras

                produtoSelecioando = produtos.get(indiceArryay + 1);//Nome do Produto
                lblProduto.setText(produtos.get(indiceArryay + 1));//Nome do Produto

                lblUnidade.setText(produtos.get(indiceArryay + 2));//Unidade de Medida
                unidade = lblUnidade.getText();

                lblPrecoVenda.setText(produtos.get(indiceArryay + 3));
                preco = produtos.get(indiceArryay + 3);

                codCat = produtos.get(indiceArryay + 4);
                lblCodCat.setText(codCat);

                nomeCat = produtos.get(indiceArryay + 5);
                lblCategoria.setText(nomeCat);

                codSub = produtos.get(indiceArryay + 6);
                lblCodSub.setText(codSub);

                nomeSub = produtos.get(indiceArryay + 7);
                lblSubCategoria.setText(nomeSub);

                lblCodInterno.setText(produtos.get(indiceArryay + 10));

            } else {
                indiceArryay = indiceArryay + 1;
                buscar = false;

            }

        }
    }

    public void buscaComissaoVendedor() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select comissao from vendedores where vendedor =?");
            pst.setString(1, (String) cbVendedor.getSelectedItem());
            rs = pst.executeQuery();

            while (rs.next()) {
                comissaoVendedor = rs.getFloat(1);
                //  JOptionPane.showMessageDialog(this, comissaoVendedor);
            }

            rs.close();
            pst.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void CorLinhaTabelaItensVenda() {

        Cores cor = new Cores();

        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int colum) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colum);

                Color c = cor.getCorLinhaImparTabela();
                Color d = cor.getCorFundoLinhaDeletada();
                int indice = Integer.parseInt(tabela.getValueAt(row, 0).toString());

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
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Codigo INterno
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centro);//Produto
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);//Unidade
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qauntidade
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//Preco

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
                    tabela.getColumnModel().getColumn(3).setCellRenderer(esquerda);//Codigo INterno
                    tabela.getColumnModel().getColumn(4).setCellRenderer(centro);//Produto
                    tabela.getColumnModel().getColumn(5).setCellRenderer(direita);//Unidade
                    tabela.getColumnModel().getColumn(6).setCellRenderer(direita);//qauntidade
                    tabela.getColumnModel().getColumn(7).setCellRenderer(direita);//Preco

                    direita.setBackground(c);
                    esquerda.setBackground(c);
                    centro.setBackground(c);
                    indice = indice + 1;

                }

                return label;
            }

        });

    }

    public void custeio() {

        tabela.clearSelection();
        ModelUsuario user = new ModelUsuario();

        int total_linha = tabela.getRowCount();
        int incremente_linda = 1;

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into custeio(DATA,DOCUMENTO,"
                    + "TM,NATUREZA,COD_PROD,DES_PROD,QUANT,CUSTO_UNIT,CUSTO_TOTAL,"
                    + "TIPO_GASTO,DATA2)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");

            while (total_linha >= incremente_linda) {
                String data = lblData.getText();
                String dia = data.substring(0, 2);
                String mes = data.substring(3, 5);
                String ano = data.substring(6, 10);
                int dataInteiro = Integer.parseInt(ano + mes + dia);

                pst.setString(1, lblData.getText());//Data1
                pst.setString(2, lblVenda.getText());//Docuemto
                pst.setString(3, "VENDA");//Tipo do Movimento
                pst.setString(4, "CUSTO DIRETO");//Tipo do Movimento
                pst.setString(5, tabela.getValueAt(incremente_linda - 1, 1).toString()); //Codigo Produto
                pst.setString(6, tabela.getValueAt(incremente_linda - 1, 2).toString()); //Nome Produto
                pst.setDouble(7, Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 4).toString().replace(".", "").replaceAll(",", ".")));//Quantidade

                //Variaveis
                double quantidade = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 4).toString().replace(".", "").replaceAll(",", "."));//Quantidade
                double custo = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 12).toString());//Quantidade
                double custoTotal = Double.parseDouble(tabela.getValueAt(incremente_linda - 1, 13).toString());//Quantidade

                pst.setDouble(8, custo);//Custo Medio
                pst.setDouble(9, custoTotal);//Custo Total
                pst.setString(10, "CUSTO");//Tipo do gasto
                pst.setInt(11, dataInteiro); //Data2
                incremente_linda++;

                pst.execute();
            }

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar custo" + e);
        }

    }

    public void calculoValorFinal() {

        double subTotal = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
        double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
        double accrecimo = Double.parseDouble(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));
        double valorFinal = subTotal - desconto + accrecimo;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(valorFinal);
        lblValorFinal.setText(totalFormatado);

    }

    public void salvar_atualizando_caixa() {

        try {

            int total_linha = tabela.getRowCount();
            int linha = 1;

            while (total_linha >= linha) {

                PreparedStatement pst = conn.getConexao().prepareStatement("insert into venda(codigo,data,codCliente,"
                        + "cliente,codProduto,produto,unidade,quantidade,preco, total, documento,codCat,categoria,"
                        + "codSub,subCategoria,desconto,valorFinal,formPagamento,condPagamento,codBarra,data2,"
                        + "usuario,vendedor,perc_comissao,valorComissao,taxa_cartao,acrescimo,parcelamento,plano,valorDinheiro,"
                        + "valorPix, valorDebito,valorCredito)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pst.setInt(1, Integer.parseInt(lblVenda.getText()));
                pst.setString(2, lblData.getText());
                pst.setInt(3, Integer.parseInt(String.valueOf(1)));
                pst.setString(4, lblCliente.getText());
                pst.setString(5, tabela.getValueAt(linha - 1, 2).toString()); //Codigo Produto
                pst.setString(6, tabela.getValueAt(linha - 1, 3).toString()); //Nome Produto
                pst.setString(7, tabela.getValueAt(linha - 1, 4).toString()); //Unidade Produto
                pst.setFloat(8, Float.parseFloat(tabela.getValueAt(linha - 1, 5).toString().toString().replace(".", "").replaceAll(",", ".")));//Quantidade
                pst.setFloat(9, Float.parseFloat(tabela.getValueAt(linha - 1, 6).toString().toString().replace(".", "").replaceAll(",", ".")));//preco
                pst.setFloat(10, Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", ".")));//Total
                pst.setString(11, "VIA PDV");
                pst.setInt(12, Integer.parseInt(tabela.getValueAt(linha - 1, 8).toString())); //Cod Cat
                pst.setString(13, tabela.getValueAt(linha - 1, 9).toString()); //Nome Categoria
                pst.setInt(14, Integer.parseInt(tabela.getValueAt(linha - 1, 10).toString())); //Cod Sub
                pst.setString(15, tabela.getValueAt(linha - 1, 11).toString()); //Nome SubCategoria

                //Calculos descontos
                float total_item = Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", "."));//Total
                float subTotal = Float.parseFloat(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                float desconto_total = Float.parseFloat(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
                float acrescimo_total = Float.parseFloat(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));

                float acrescimo_percentual = acrescimo_total / subTotal;
                float desconto_percentual = desconto_total / subTotal;
                float valorFinal = total_item - (total_item * desconto_percentual);
                float desconto_no_item = total_item * desconto_percentual;
                float acerscimo_no_item = total_item * acrescimo_percentual;

                //Calculos Taxas
                float taxa_total = Float.parseFloat(txtTaxasCartao.getText().replace(".", "").replaceAll(",", "."));
                float taxa_percentual = taxa_total / subTotal;
                float taxaProporcional = total_item * taxa_percentual;

                //Calculo da comissao do vendedarl
                float percentualComissao = comissaoVendedor / 100;
                float valorComissao = (float) (percentualComissao * valorFinal);

                //Fim do calculo___________________________
                pst.setFloat(16, desconto_no_item);
                pst.setFloat(17, valorFinal);
                pst.setString(19, (String) cbCondPagamento.getSelectedItem());
                pst.setString(18, (String) cbFormaPagamento.getSelectedItem());
                pst.setString(20, tabela.getValueAt(linha - 1, 11).toString()); //Codigo de barras
                pst.setString(21, dataInteiro);
                pst.setString(22, user.getUsuarioLogado());
                pst.setString(23, (String) cbVendedor.getSelectedItem());
                pst.setFloat(24, comissaoVendedor);
                pst.setFloat(25, valorComissao);
                pst.setFloat(26, taxaProporcional);
                pst.setFloat(27, acerscimo_no_item);
                pst.setString(28, "");
                pst.setString(29, "");
                pst.setFloat(30, this.getValorDinheiro());
                pst.setFloat(31, this.getValorPix());
                pst.setFloat(32, this.getValorCartaoDebito());
                pst.setFloat(33, this.getValorCartaoCredito());
                pst.execute();

                pst.close();
                linha = linha + 1;
            }

            // troco();
            atualizaEntradaCaixaPVD();
            atualizaEstoque();

            Confirmacao conf = new Confirmacao(this, true);
            conf.textoVendaSalva();
            conf.setVisible(true);
            buscaUltimaVenda();
            txtCodBarras.requestFocus();

            lblValorFinal.setText("0,00");
            txtSubTotal.setText("0,00");
            txtItens.setText("0");
            lblProduto.setText("");
            lblProduto.setText("");
            lblCodInterno.setText("");
            lblUnidade.setText("");
            txtDesconto.setText("0,00");
            limpaTabela();

            txtCodBarras.requestFocus();

            lblValorFinal.setText("0,00");
            txtSubTotal.setText("0,00");
            txtItens.setText("0");
            lblProduto.setText("");
            lblProduto.setText("");
            lblCodInterno.setText("");
            lblUnidade.setText("");
            txtDesconto.setText("0,00");
            lblStatusCaixa.setText("CAIXA LIVRE");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda " + ex);
        }

    }

    public void salvar() {
        ModelUsuario user = new ModelUsuario();
        try {

            int total_linha = tabela.getRowCount();
            int linha = 1;

            while (total_linha >= linha) {

                PreparedStatement pst = conn.getConexao().prepareStatement("insert into venda(codigo,data,codCliente,"
                        + "cliente,codProduto,produto,unidade,quantidade,preco, total, documento,codCat,categoria,"
                        + "codSub,subCategoria,desconto,valorFinal,formPagamento,condPagamento,codBarra,data2,"
                        + "usuario,vendedor,perc_comissao,valorComissao,taxa_cartao,acrescimo,parcelamento,plano,valorDinheiro,"
                        + "valorPix, valorDebito,valorCredito)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pst.setInt(1, Integer.parseInt(lblVenda.getText()));
                pst.setString(2, lblData.getText());
                pst.setInt(3, Integer.parseInt(String.valueOf(1)));
                pst.setString(4, lblCliente.getText());
                pst.setString(5, tabela.getValueAt(linha - 1, 2).toString()); //Codigo Produto
                pst.setString(6, tabela.getValueAt(linha - 1, 3).toString()); //Nome Produto
                pst.setString(7, tabela.getValueAt(linha - 1, 4).toString()); //Unidade Produto
                pst.setFloat(8, Float.parseFloat(tabela.getValueAt(linha - 1, 5).toString().toString().replace(".", "").replaceAll(",", ".")));//Quantidade
                pst.setFloat(9, Float.parseFloat(tabela.getValueAt(linha - 1, 6).toString().toString().replace(".", "").replaceAll(",", ".")));//preco
                pst.setFloat(10, Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", ".")));//Total
                pst.setString(11, "VIA PDV");
                pst.setString(12, tabela.getValueAt(linha - 1, 8).toString()); //Cod Cat
                pst.setString(13, tabela.getValueAt(linha - 1, 9).toString()); //Nome Categoria
                pst.setString(14, tabela.getValueAt(linha - 1, 10).toString()); //Cod Sub
                pst.setString(15, tabela.getValueAt(linha - 1, 11).toString()); //Nome SubCategoria

                //Calculos descontos
                float total_item = Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", "."));//Total
                float subTotal = Float.parseFloat(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                float desconto_total = Float.parseFloat(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
                float acrescimo_total = Float.parseFloat(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));

                float acrescimo_percentual = acrescimo_total / subTotal;
                float desconto_percentual = desconto_total / subTotal;
                float valorFinal = total_item - (total_item * desconto_percentual);
                float desconto_no_item = total_item * desconto_percentual;
                float acerscimo_no_item = total_item * acrescimo_percentual;

                //Calculos Taxas
                float taxa_total = Float.parseFloat(txtTaxasCartao.getText().replace(".", "").replaceAll(",", "."));
                float taxa_percentual = taxa_total / subTotal;
                float taxaProporcional = total_item * taxa_percentual;

                //Calculo da comissao do vendedarl
                float percentualComissao = comissaoVendedor / 100;
                float valorComissao = (float) (percentualComissao * valorFinal);

                //Fim do calculo___________________________
                pst.setFloat(16, desconto_no_item);
                pst.setFloat(17, valorFinal);
                pst.setString(19, (String) cbCondPagamento.getSelectedItem());
                pst.setString(18, (String) cbFormaPagamento.getSelectedItem());
                pst.setString(20, tabela.getValueAt(linha - 1, 11).toString()); //Codigo de barras
                pst.setString(21, dataInteiro);
                pst.setString(22, user.getUsuarioLogado());
                pst.setString(23, (String) cbVendedor.getSelectedItem());
                pst.setFloat(24, comissaoVendedor);
                pst.setFloat(25, valorComissao);
                pst.setFloat(26, taxaProporcional);
                pst.setFloat(27, acerscimo_no_item);
                pst.setString(28, lblParcelamento.getText().replace("Parcelamento: ", ""));
                pst.setString(29, this.getPlanoCartao());
                pst.setFloat(30, this.getValorDinheiro());
                pst.setFloat(31, this.getValorPix());
                pst.setFloat(32, this.getValorCartaoDebito());
                pst.setFloat(33, this.getValorCartaoCredito());

                pst.execute();

                pst.close();
                linha = linha + 1;
            }

            atualizaEstoque();
            Confirmacao conf = new Confirmacao(this, true);
            conf.textoVendaSalva();
            conf.setVisible(true);
            buscaUltimaVenda();
            txtCodBarras.requestFocus();

            //Limpar os Campos da Tela
            lblValorFinal.setText("0,00");
            txtSubTotal.setText("0,00");
            txtItens.setText("0");
            lblProduto.setText("");
            lblProduto.setText("");
            lblCodInterno.setText("");
            lblUnidade.setText("");
            txtDesconto.setText("0,00");
            txtTaxasCartao.setText("0,00");
            cbCondPagamento.setSelectedItem("A VISTA");
            cbFormaPagamento.setSelectedItem("DINHEIRO");

            lblValorFinal.setText("0,00");
            txtSubTotal.setText("0,00");
            txtItens.setText("0");
            lblProduto.setText("");
            lblProduto.setText("");
            lblCodInterno.setText("");
            lblUnidade.setText("");
            txtDesconto.setText("0,00");
            lblStatusCaixa.setText("CAIXA LIVRE");
            txtCodBarras.requestFocus();
            limpaTabela();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda " + ex);
        }

    }

    public void salvarComPgMultAtualizandoCaixa() {

        ModelUsuario user = new ModelUsuario();
        try {

            int total_linha = tabela.getRowCount();
            int linha = 1;

            while (total_linha >= linha) {

                PreparedStatement pst = conn.getConexao().prepareStatement("insert into venda(codigo,data,codCliente,"
                        + "cliente,codProduto,produto,unidade,quantidade,preco, total, documento,codCat,categoria,"
                        + "codSub,subCategoria,desconto,valorFinal,formPagamento,condPagamento,codBarra,data2,"
                        + "usuario,vendedor,perc_comissao,valorComissao,taxa_cartao,acrescimo,parcelamento,plano,valorDinheiro,"
                        + "valorPix, valorDebito,valorCredito)"
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                pst.setInt(1, Integer.parseInt(lblVenda.getText()));
                pst.setString(2, lblData.getText());
                pst.setInt(3, Integer.parseInt(String.valueOf(1)));
                pst.setString(4, lblCliente.getText());
                pst.setString(5, tabela.getValueAt(linha - 1, 2).toString()); //Codigo Produto
                pst.setString(6, tabela.getValueAt(linha - 1, 3).toString()); //Nome Produto
                pst.setString(7, tabela.getValueAt(linha - 1, 4).toString()); //Unidade Produto
                pst.setFloat(8, Float.parseFloat(tabela.getValueAt(linha - 1, 5).toString().toString().replace(".", "").replaceAll(",", ".")));//Quantidade
                pst.setFloat(9, Float.parseFloat(tabela.getValueAt(linha - 1, 6).toString().toString().replace(".", "").replaceAll(",", ".")));//preco
                pst.setFloat(10, Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", ".")));//Total
                pst.setString(11, "VIA PDV");
                pst.setInt(12, Integer.parseInt(tabela.getValueAt(linha - 1, 8).toString())); //Cod Cat
                pst.setString(13, tabela.getValueAt(linha - 1, 9).toString()); //Nome Categoria
                pst.setInt(14, Integer.parseInt(tabela.getValueAt(linha - 1, 10).toString())); //Cod Sub
                pst.setString(15, tabela.getValueAt(linha - 1, 11).toString()); //Nome SubCategoria

                //Calculos descontos
                float total_item = Float.parseFloat(tabela.getValueAt(linha - 1, 7).toString().toString().replace(".", "").replaceAll(",", "."));//Total
                float subTotal = Float.parseFloat(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                float desconto_total = Float.parseFloat(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
                float acrescimo_total = Float.parseFloat(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));

                float acrescimo_percentual = acrescimo_total / subTotal;
                float desconto_percentual = desconto_total / subTotal;
                float valorFinal = total_item - (total_item * desconto_percentual);
                float desconto_no_item = total_item * desconto_percentual;
                float acerscimo_no_item = total_item * acrescimo_percentual;

                //Calculos Taxas
                float taxa_total = Float.parseFloat(txtTaxasCartao.getText().replace(".", "").replaceAll(",", "."));
                float taxa_percentual = taxa_total / subTotal;
                float taxaProporcional = total_item * taxa_percentual;

                //Calculo da comissao do vendedarl
                float percentualComissao = comissaoVendedor / 100;
                float valorComissao = (float) (percentualComissao * valorFinal);

                //Fim do calculo___________________________
                pst.setFloat(16, desconto_no_item);
                pst.setFloat(17, valorFinal);
                pst.setString(19, (String) cbCondPagamento.getSelectedItem());
                pst.setString(18, (String) cbFormaPagamento.getSelectedItem());
                pst.setString(20, tabela.getValueAt(linha - 1, 11).toString()); //Codigo de barras
                pst.setString(21, dataInteiro);
                pst.setString(22, user.getUsuarioLogado());
                pst.setString(23, (String) cbVendedor.getSelectedItem());
                pst.setFloat(24, comissaoVendedor);
                pst.setFloat(25, valorComissao);
                pst.setFloat(26, taxaProporcional);
                pst.setFloat(27, acerscimo_no_item);
                pst.setString(28, lblParcelamento.getText().replace("Parcelamento: ", ""));
                pst.setString(29, this.getPlanoCartao());
                pst.setFloat(30, this.getValorDinheiro());
                pst.setFloat(31, this.getValorPix());
                pst.setFloat(32, this.getValorCartaoDebito());
                pst.setFloat(33, this.getValorCartaoCredito());

                pst.execute();

                pst.close();
                linha = linha + 1;
            }

            atualizaEstoque();
            atualizaCaixaMultPG();
            Confirmacao conf = new Confirmacao(this, true);
            conf.textoVendaSalva();
            conf.setVisible(true);
            buscaUltimaVenda();
            txtCodBarras.requestFocus();

            //Limpar os Campos da Tela
            lblValorFinal.setText("0,00");
            txtSubTotal.setText("0,00");
            txtItens.setText("0");
            lblProduto.setText("");
            lblProduto.setText("");
            lblCodInterno.setText("");
            lblUnidade.setText("");
            txtDesconto.setText("0,00");
            limpaTabela();

            txtCodBarras.requestFocus();

            lblValorFinal.setText("0,00");
            txtSubTotal.setText("0,00");
            txtItens.setText("0");
            lblProduto.setText("");
            lblProduto.setText("");
            lblCodInterno.setText("");
            lblUnidade.setText("");
            txtDesconto.setText("0,00");
            lblStatusCaixa.setText("CAIXA LIVRE");
            txtTaxasCartao.setText("0,00");
            cbFormaPagamento.setSelectedItem("DINHEIRO");
            cbCondPagamento.setSelectedItem("A VISTA");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao finalizar a venda " + ex);
        }

    }

    public void limpaTabela() {
        DefaultTableModel tabela = (DefaultTableModel) this.tabela.getModel();

        if (tabela.getRowCount() >= 0) {
            int lin = tabela.getColumnCount();
            for (int i = 0; lin >= i; i = i) {
                tabela.removeRow(0);
                somaTabela();
                lblStatusCaixa.setText("CAIXA LIVRE");
                lblStatusCaixa.setForeground(Color.BLACK);
            }

        } else {

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
                int ultimaVenda = rs.getInt(1);
                int novaVenda = ultimaVenda + 1;
                lblVenda.setText(String.valueOf(novaVenda));

            }
            conn.getConexao().close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }

        if (lblVenda.getText().equals("")) {
            lblVenda.setText(String.valueOf(1));
        } else {

        }
    }

    public void AlinharColunas() {
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);

        tabela.getColumnModel().getColumn(0).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        tabela.getColumnModel().getColumn(4).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(5).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(6).setCellRenderer(direita);
        tabela.getColumnModel().getColumn(11).setCellRenderer(centralizado);

        Cores cor = new Cores();

        tabela.getTableHeader().setOpaque(false);
        // tabela.getTableHeader().setBackground(cor.getCorPreechimentoCabecalhoPDV());
        // tabela.getTableHeader().setForeground(cor.getCorFonteCabecalho());
        tabela.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 10));

    }

    public void buscaProduto() {

        if (txtCodBarras.getText().equals("")) {

        } else {

            try {
                Conexao conn = new Conexao();
                PreparedStatement pst = null;
                ResultSet rs;
                pst = conn.getConexao().prepareStatement("select nome,und,codigo,precoVenda,"
                        + "codCat,categoria,codSub,subCategoria,codBarra,atual,valor,PETQ_001"
                        + " from tb_produtos where codBarra = ?");
                pst.setString(1, txtCodBarras.getText());
                rs = pst.executeQuery();

                while (rs.next()) {

                    produtoSelecioando = (rs.getString(1));
                    lblCodInterno.setText(rs.getString(3));
                    unidade = (rs.getString(2));
                    preco = (rs.getString(4));
                    codCat = (rs.getString(5));
                    nomeCat = (rs.getString(6));
                    codSub = (rs.getString(7));
                    nomeSub = (rs.getString(8));
                    codBarra = (rs.getString(9));
                    lblProduto.setText(produtoSelecioando);
                    lblUnidade.setText(unidade);
                    lblPrecoVenda.setText(preco);
                    estoque = rs.getFloat("atual");
                    valor = rs.getFloat("valor");
                    parametro_estoque = rs.getString("PETQ_001");

                }

                conn.getConexao().close();
                pst.close();
                rs.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
            }

            totalItem();
            txtCodBarras.requestFocus();

        }

    }

    public void buscaStatusCaixa() {

        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select status,codigo from caixa_pdv where data = ? and status= 'ABERTO'");
            pst.setString(1, dataAtual);
            rs = pst.executeQuery();

            while (rs.next()) {
                statusCaixa = rs.getString(1);
                codCaixa = rs.getInt(2);
            }

            conn.getConexao().close();
            pst.close();
            rs.close();
            txtCodBarras.requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
        if (statusCaixa == null) {
            statusCaixa = "FECHADO";

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
        descontoCondicional();
        valorFinal();
        CorLinhaTabelaItensVenda();

    }

    public void somaTabelaAposAlteracao() {

        double somaTotal = 0;
        for (int i = 0; i < tabela.getRowCount(); i++) {
            somaTotal += Double.parseDouble(tabela.getValueAt(i, 7).toString().toString().replace(".", "").replaceAll(",", "."));
        }
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(somaTotal);
        txtSubTotal.setText(totalFormatado);
        descontoCondicional();
        valorFinal();

    }

    public void valorFinal() {

        double subTotal = Double.parseDouble(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
        double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
        double accrecimo = Double.parseDouble(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));

        double valorFinal = subTotal - desconto + accrecimo;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String totalFormatado = df.format(valorFinal);
        lblValorFinal.setText("R$ " + totalFormatado);
    }

    public void totalItem() {

        if (preco.equals("")) {

        } else {

            double quantidade = Double.parseDouble(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));
            double precoVenda = Double.parseDouble(String.valueOf(preco).replace(".", "").replaceAll(",", "."));
            double total = quantidade * precoVenda;

            DecimalFormat df = new DecimalFormat("#,##0.00");
            String totalFormatado = df.format(total);
            totalProduto = totalFormatado;
        }

    }

    public void addItemTabela() {

        lblStatusCaixa.setText("Venda em andamento...");
        lblStatusCaixa.setForeground(new Color(0, 153, 51));
        //Calculando o custo da requisição
        float quant = Float.parseFloat(txtQuantidade.getText().replace(".", "").replaceAll(",", "."));
        float estoqueFinal = estoque - quant;
        float valorFinal = valor;
        custoTotal = quant * (valorFinal / estoque);

        valorFinal = valorFinal - custoTotal;

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        model.addRow(new Object[]{
            (model.getRowCount() + 1),
            (model.getRowCount() + 1),
            lblCodInterno.getText(),
            produtoSelecioando,
            unidade,
            txtQuantidade.getText(),
            preco,
            totalProduto,
            codCat,
            nomeCat,
            codSub,
            nomeSub,
            codBarra,
            estoque,
            valor,
            estoqueFinal,
            valorFinal,}
        );

        //  corrColunas();
        txtItens.setText(String.valueOf(tabela.getRowCount()).toString());
        txtQuantidade.setText("1,00");
        txtCodBarras.setText("");

    }

    public void atualizaEntradaCaixaPVD() {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into caixa_pdv(codigo,data,"
                    + "operador,entradas,"
                    + "status,operacao,documento_origem)VALUES(?,?,?,?,?,?,?)");

            pst.setInt(1, codCaixa);//Codigo
            pst.setString(2, lblData.getText());//Data
            pst.setString(3, lblUsuario.getText());//Operador
            pst.setDouble(4, Double.parseDouble(lblValorFinal.getText().replace("R$ ", "").replace(".", "").replaceAll(",", ".")));//Entrada
            pst.setString(5, "ABERTO");//Sttus
            pst.setString(6, "VENDA");//Operação
            pst.setInt(7, Integer.parseInt(lblVenda.getText()));
            pst.execute();

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Abrir Caixa" + e);
        }

    }

    public void atualizaCaixaMultPG() {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into caixa_pdv(codigo,data,"
                    + "operador,entradas,"
                    + "status,operacao,documento_origem)VALUES(?,?,?,?,?,?,?)");

            pst.setInt(1, codCaixa);//Codigo
            pst.setString(2, lblData.getText());//Data
            pst.setString(3, lblUsuario.getText());//Operador
            pst.setDouble(4, valorDinheiro);//Entrada
            pst.setString(5, "ABERTO");//Sttus
            pst.setString(6, "VENDA");//Operação
            pst.setInt(7, Integer.parseInt(lblVenda.getText()));
            pst.execute();

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Abrir Caixa" + e);
        }

    }

    public void pegaDadosAlteracao(ALTERAR_ITEM_PDV altera) {

        tabela.setValueAt(altera.getCodAlterar(), this.getLinhaSelecionada(), 2);
        tabela.setValueAt(altera.getNomeAlterar(), this.getLinhaSelecionada(), 3);
        tabela.setValueAt(altera.getQtdeAlterar(), this.getLinhaSelecionada(), 5);//Quantidade
        tabela.setValueAt(altera.getPrecoAlterar(), this.getLinhaSelecionada(), 6);//Preco
        tabela.setValueAt(altera.getTotalAlterar(), this.getLinhaSelecionada(), 7);//Total
        tabela.setValueAt(altera.getEstoque(), this.getLinhaSelecionada(), 13);//EstoqueAtual
        tabela.setValueAt(altera.getTotalAlterar(), this.getLinhaSelecionada(), 14);//ValorAtual
        tabela.setValueAt(altera.getNovoEstoque(), this.getLinhaSelecionada(), 15);//Novo Estoque
        tabela.setValueAt(altera.getNovoValor(), this.getLinhaSelecionada(), 16);//Novo Valor

    }

    public void carregaComboVendedor() {

        Conexao conn = new Conexao();

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from vendedores limit 1000");
            rs = pst.executeQuery();
            int indice = 0;
            while (rs.next()) {

                vendedores.add(rs.getString("vendedor")); //Codigo
                vendedores.add(rs.getString("comissao")); //Codigo
                cbVendedor.addItem(vendedores.get(indice));
                indice = indice + 2;
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

    }

    public void atualizaEstoque() {

        int totalProdutos = tabela.getRowCount();
        int increment = 1;
        try {
            while (totalProdutos >= increment) {

                Conexao conn = new Conexao();
                PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE tb_produtos SET valor=valor-(custoMedio)*?,"
                        + "atual=atual-?, dataUltVenda=? "
                        + "where codBarra =?");

                pst.setFloat(2, Float.parseFloat(tabela.getValueAt(increment - 1, 5).toString().replace(".", "").replaceAll(",", ".")));//Atualizar o saldo do produto
                pst.setFloat(1, Float.parseFloat(tabela.getValueAt(increment - 1, 5).toString().replace(".", "").replaceAll(",", ".")));//Atualizar o saldo do produto
                pst.setString(3, dataDigiatacao);//Atuaizar o valor do Produto
                pst.setString(4, (String) tabela.getValueAt(increment - 1, 12));

                pst.executeUpdate();

                increment++;
                pst.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque! " + e, "Atualização de Estoque", JOptionPane.ERROR_MESSAGE);
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

        jPanel2 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblCliente = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblVenda = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        cbCondPagamento = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbFormaPagamento = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        txtCodBarras = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        cbBoxInputManual = new javax.swing.JCheckBox();
        cbBoxInputLeitor = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        cbBoxCodBarra = new javax.swing.JCheckBox();
        cbBoxCodInterno = new javax.swing.JCheckBox();
        txtCodInterno = new javax.swing.JTextField();
        lblCod = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtAcrescimo = new javax.swing.JTextField();
        txtDesconto = new javax.swing.JTextField();
        txtItens = new javax.swing.JTextField();
        txtSubTotal = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lblValorFinal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTaxasCartao = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        lblVendedor = new javax.swing.JLabel();
        cbVendedor = new javax.swing.JComboBox();
        lblStatusCaixa = new javax.swing.JLabel();
        lblParcelamento = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lblProduto = new javax.swing.JLabel();
        lblCodInterno = new javax.swing.JLabel();
        lblUnidade = new javax.swing.JLabel();
        lblPrecoVenda = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        btnEntrar6 = new javax.swing.JButton();
        btnEntrar4 = new javax.swing.JButton();
        btnEntrar5 = new javax.swing.JButton();
        btnEntrar3 = new javax.swing.JButton();
        btnAbrirCaixa = new javax.swing.JButton();
        btnEntrar7 = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        btnFinalizar = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblEstoqueAtual = new javax.swing.JLabel();
        lblCodCat = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        lblCodSub = new javax.swing.JLabel();
        lblSubCategoria = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        jPanel3.setBackground(new java.awt.Color(251, 251, 251));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        jPanel7.setBackground(new java.awt.Color(94, 110, 110));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("USUÁRIO:");

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuario.setText("Usuario");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("DATA:");

        lblData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblData.setForeground(new java.awt.Color(255, 255, 255));
        lblData.setText("Data");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
            .addComponent(lblUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(94, 110, 110));

        lblCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblCliente.setText("Nome do Cliente");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("CLIENTE:");

        lblVenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblVenda.setForeground(new java.awt.Color(255, 255, 255));

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("VENDA:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVenda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCliente)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Design sem nome (11).png"))); // NOI18N
        lblLogo.setToolTipText("");
        lblLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        cbCondPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A VISTA", "A PRAZO", "MULT COND" }));

        jLabel1.setText("Cond Pagamento:");

        jLabel2.setText("Forma Pagamento:");

        cbFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DINHEIRO", "PIX", "CARTÃO DE CREDITO", "CARTÃO DE DEBITO", "PROMISSORIA", "MULTI FORMAS" }));
        cbFormaPagamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbFormaPagamentoFocusLost(evt);
            }
        });
        cbFormaPagamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbFormaPagamentoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbFormaPagamentoMouseReleased(evt);
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbCondPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(cbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCondPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(226, 226, 242));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        txtCodBarras.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBarrasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBarrasFocusLost(evt);
            }
        });
        txtCodBarras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodBarrasMouseClicked(evt);
            }
        });
        txtCodBarras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBarrasActionPerformed(evt);
            }
        });
        txtCodBarras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodBarrasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodBarrasKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodBarrasKeyTyped(evt);
            }
        });

        jLabel13.setText("Codigo de Barras:");

        txtQuantidade.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtQuantidade.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQuantidade.addFocusListener(new java.awt.event.FocusAdapter() {
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

        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        cbBoxInputManual.setText("Entrada manual");
        cbBoxInputManual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbBoxInputManualMouseClicked(evt);
            }
        });
        cbBoxInputManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBoxInputManualActionPerformed(evt);
            }
        });

        cbBoxInputLeitor.setText("Entrada leitor");
        cbBoxInputLeitor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbBoxInputLeitorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbBoxInputManual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbBoxInputLeitor)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbBoxInputLeitor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cbBoxInputManual, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        cbBoxCodBarra.setText("Por Cod Barra");
        cbBoxCodBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbBoxCodBarraMouseClicked(evt);
            }
        });
        cbBoxCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBoxCodBarraActionPerformed(evt);
            }
        });

        cbBoxCodInterno.setText("Por Cod Interno");
        cbBoxCodInterno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbBoxCodInternoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbBoxCodBarra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbBoxCodInterno)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbBoxCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(cbBoxCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtCodInterno.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        lblCod.setText("Cod Interno:");

        jButton1.setText("Inserir Item");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(lblCod)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(txtCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtQuantidade))))
                    .addComponent(jButton1))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblCod))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodBarras, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel6.setBackground(new java.awt.Color(94, 110, 110));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel6.setMaximumSize(new java.awt.Dimension(492, 147));
        jPanel6.setMinimumSize(new java.awt.Dimension(492, 147));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Itens:");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sub Total:");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Desconto (-):");

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Acrescimo(+):");

        txtAcrescimo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtAcrescimo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAcrescimo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAcrescimoFocusLost(evt);
            }
        });
        txtAcrescimo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAcrescimoMouseClicked(evt);
            }
        });

        txtDesconto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDesconto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDesconto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
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

        txtItens.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtItens.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtSubTotal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSubTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jPanel4.setBackground(new java.awt.Color(94, 110, 110));

        lblValorFinal.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblValorFinal.setForeground(new java.awt.Color(255, 255, 255));
        lblValorFinal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValorFinal.setText("R$ 1.000,00");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblValorFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(lblValorFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Taxa/Cartão:");

        txtTaxasCartao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTaxasCartao.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Total (R$)");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtItens, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtAcrescimo)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTaxasCartao, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtItens, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(5, 5, 5)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAcrescimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTaxasCartao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        lblVendedor.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblVendedor.setText("Vendedor:");
        lblVendedor.setOpaque(true);

        cbVendedor.setEditable(true);
        cbVendedor.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        cbVendedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbVendedorFocusLost(evt);
            }
        });
        cbVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbVendedorActionPerformed(evt);
            }
        });

        lblStatusCaixa.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblStatusCaixa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusCaixa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lblParcelamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblParcelamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblParcelamento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblStatusCaixa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbVendedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatusCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblParcelamento, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lblProduto.setBackground(new java.awt.Color(94, 110, 110));
        lblProduto.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblProduto.setForeground(new java.awt.Color(255, 255, 255));
        lblProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblProduto.setMaximumSize(new java.awt.Dimension(54, 914));
        lblProduto.setMinimumSize(new java.awt.Dimension(54, 914));
        lblProduto.setOpaque(true);
        lblProduto.setPreferredSize(new java.awt.Dimension(54, 914));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblCodInterno.setBackground(new java.awt.Color(242, 236, 236));
        lblCodInterno.setForeground(new java.awt.Color(51, 51, 51));
        lblCodInterno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodInterno.setOpaque(true);
        lblCodInterno.setVerifyInputWhenFocusTarget(false);

        lblUnidade.setBackground(new java.awt.Color(242, 236, 236));
        lblUnidade.setForeground(new java.awt.Color(51, 51, 51));
        lblUnidade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUnidade.setOpaque(true);
        lblUnidade.setVerifyInputWhenFocusTarget(false);

        lblPrecoVenda.setBackground(new java.awt.Color(242, 236, 236));
        lblPrecoVenda.setForeground(new java.awt.Color(51, 51, 51));
        lblPrecoVenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrecoVenda.setOpaque(true);
        lblPrecoVenda.setVerifyInputWhenFocusTarget(false);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Idice", "ITEM", "COD PROD", "DESCRIÇÃO DO ITEM", "UND M", "QUANT", "PREÇO  UNIT", "TOTAL", "codCat", "Cat", "codSub", "Sub", "COD BARRAS", "Estoque", "Valor", "NovoSaldo", "NovoValor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
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
            tabela.getColumnModel().getColumn(0).setMinWidth(3);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(3);
            tabela.getColumnModel().getColumn(0).setMaxWidth(3);
            tabela.getColumnModel().getColumn(1).setMinWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setMaxWidth(50);
            tabela.getColumnModel().getColumn(3).setMinWidth(350);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(350);
            tabela.getColumnModel().getColumn(3).setMaxWidth(350);
            tabela.getColumnModel().getColumn(4).setMinWidth(50);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(4).setMaxWidth(50);
            tabela.getColumnModel().getColumn(5).setMinWidth(60);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(5).setMaxWidth(60);
            tabela.getColumnModel().getColumn(8).setMinWidth(0);
            tabela.getColumnModel().getColumn(8).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(8).setMaxWidth(0);
            tabela.getColumnModel().getColumn(9).setMinWidth(0);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(9).setMaxWidth(0);
            tabela.getColumnModel().getColumn(10).setMinWidth(0);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(10).setMaxWidth(0);
            tabela.getColumnModel().getColumn(11).setMinWidth(0);
            tabela.getColumnModel().getColumn(11).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(11).setMaxWidth(0);
            tabela.getColumnModel().getColumn(12).setMinWidth(0);
            tabela.getColumnModel().getColumn(12).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(12).setMaxWidth(0);
            tabela.getColumnModel().getColumn(13).setMinWidth(0);
            tabela.getColumnModel().getColumn(13).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(13).setMaxWidth(0);
            tabela.getColumnModel().getColumn(14).setMinWidth(0);
            tabela.getColumnModel().getColumn(14).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(14).setMaxWidth(0);
            tabela.getColumnModel().getColumn(15).setMinWidth(0);
            tabela.getColumnModel().getColumn(15).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(15).setMaxWidth(0);
            tabela.getColumnModel().getColumn(16).setMinWidth(0);
            tabela.getColumnModel().getColumn(16).setPreferredWidth(0);
            tabela.getColumnModel().getColumn(16).setMaxWidth(0);
        }

        jPanel15.setLayout(new java.awt.GridLayout(1, 0));

        btnEntrar6.setBackground(new java.awt.Color(239, 237, 237));
        btnEntrar6.setMnemonic('2');
        btnEntrar6.setText("Diminui Quant (Alt+2)");
        btnEntrar6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar6.setContentAreaFilled(false);
        btnEntrar6.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar6.setOpaque(true);
        btnEntrar6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrar6FocusGained(evt);
            }
        });
        btnEntrar6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrar6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrar6MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrar6MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrar6MouseReleased(evt);
            }
        });
        btnEntrar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrar6ActionPerformed(evt);
            }
        });
        jPanel15.add(btnEntrar6);

        btnEntrar4.setBackground(new java.awt.Color(239, 237, 237));
        btnEntrar4.setMnemonic('F');
        btnEntrar4.setText("Finalizar Venda (Alt+f)");
        btnEntrar4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar4.setContentAreaFilled(false);
        btnEntrar4.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar4.setOpaque(true);
        btnEntrar4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrar4FocusGained(evt);
            }
        });
        btnEntrar4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrar4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrar4MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrar4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrar4MouseReleased(evt);
            }
        });
        btnEntrar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrar4ActionPerformed(evt);
            }
        });
        jPanel15.add(btnEntrar4);

        btnEntrar5.setBackground(new java.awt.Color(239, 237, 237));
        btnEntrar5.setMnemonic('1');
        btnEntrar5.setText("Aumenta quant (Alt+1)");
        btnEntrar5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar5.setContentAreaFilled(false);
        btnEntrar5.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar5.setOpaque(true);
        btnEntrar5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrar5FocusGained(evt);
            }
        });
        btnEntrar5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrar5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrar5MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrar5MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrar5MouseReleased(evt);
            }
        });
        btnEntrar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrar5ActionPerformed(evt);
            }
        });
        jPanel15.add(btnEntrar5);

        btnEntrar3.setBackground(new java.awt.Color(239, 237, 237));
        btnEntrar3.setMnemonic('S');
        btnEntrar3.setText("Cancelar (Alt+S)");
        btnEntrar3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar3.setContentAreaFilled(false);
        btnEntrar3.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar3.setOpaque(true);
        btnEntrar3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrar3FocusGained(evt);
            }
        });
        btnEntrar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrar3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrar3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrar3MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrar3MouseReleased(evt);
            }
        });
        btnEntrar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrar3ActionPerformed(evt);
            }
        });
        jPanel15.add(btnEntrar3);

        btnAbrirCaixa.setBackground(new java.awt.Color(239, 237, 237));
        btnAbrirCaixa.setMnemonic('A');
        btnAbrirCaixa.setText("Acessar o Caixa (Alt+A)");
        btnAbrirCaixa.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnAbrirCaixa.setContentAreaFilled(false);
        btnAbrirCaixa.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnAbrirCaixa.setOpaque(true);
        btnAbrirCaixa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAbrirCaixaFocusGained(evt);
            }
        });
        btnAbrirCaixa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAbrirCaixaMouseClicked(evt);
            }
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
        jPanel15.add(btnAbrirCaixa);

        btnEntrar7.setBackground(new java.awt.Color(239, 237, 237));
        btnEntrar7.setMnemonic('P');
        btnEntrar7.setText("Busca Produto (Alt+P)");
        btnEntrar7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnEntrar7.setContentAreaFilled(false);
        btnEntrar7.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnEntrar7.setOpaque(true);
        btnEntrar7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEntrar7FocusGained(evt);
            }
        });
        btnEntrar7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntrar7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntrar7MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEntrar7MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnEntrar7MouseReleased(evt);
            }
        });
        btnEntrar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrar7ActionPerformed(evt);
            }
        });
        jPanel15.add(btnEntrar7);

        jPanel16.setLayout(new java.awt.GridLayout(1, 0));

        btnFinalizar.setBackground(new java.awt.Color(0, 153, 153));
        btnFinalizar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnFinalizar.setForeground(new java.awt.Color(255, 255, 255));
        btnFinalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnFinalizar.setMnemonic('F');
        btnFinalizar.setText("FINALIZAR VENDA");
        btnFinalizar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnFinalizar.setContentAreaFilled(false);
        btnFinalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFinalizar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnFinalizar.setOpaque(true);
        btnFinalizar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnFinalizarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnFinalizarFocusLost(evt);
            }
        });
        btnFinalizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFinalizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFinalizarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnFinalizarMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnFinalizarMouseReleased(evt);
            }
        });
        btnFinalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarActionPerformed(evt);
            }
        });
        jPanel16.add(btnFinalizar);

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Altar2.png"))); // NOI18N
        btnAlterar.setText("ALTERAR ITEM");
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
        jPanel16.add(btnAlterar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
        btnExcluir.setText("EXCLUIR ITEM");
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
        jPanel16.add(btnExcluir);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/back_arrow_icon_134660.png"))); // NOI18N
        btnCancelar.setText("SAIR - CANECLAR");
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
        jPanel16.add(btnCancelar);

        lblEstoqueAtual.setBackground(new java.awt.Color(242, 236, 236));
        lblEstoqueAtual.setForeground(new java.awt.Color(51, 51, 51));
        lblEstoqueAtual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstoqueAtual.setOpaque(true);
        lblEstoqueAtual.setVerifyInputWhenFocusTarget(false);

        lblCodCat.setBackground(new java.awt.Color(242, 236, 236));
        lblCodCat.setForeground(new java.awt.Color(51, 51, 51));
        lblCodCat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodCat.setOpaque(true);
        lblCodCat.setVerifyInputWhenFocusTarget(false);

        lblCategoria.setBackground(new java.awt.Color(242, 236, 236));
        lblCategoria.setForeground(new java.awt.Color(51, 51, 51));
        lblCategoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCategoria.setOpaque(true);
        lblCategoria.setVerifyInputWhenFocusTarget(false);

        lblCodSub.setBackground(new java.awt.Color(242, 236, 236));
        lblCodSub.setForeground(new java.awt.Color(51, 51, 51));
        lblCodSub.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodSub.setOpaque(true);
        lblCodSub.setVerifyInputWhenFocusTarget(false);

        lblSubCategoria.setBackground(new java.awt.Color(242, 236, 236));
        lblSubCategoria.setForeground(new java.awt.Color(51, 51, 51));
        lblSubCategoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSubCategoria.setOpaque(true);
        lblSubCategoria.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblCodInterno, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEstoqueAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodCat, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodSub, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSubCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblCodInterno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEstoqueAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodCat, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodSub, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(Color.white);
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        if (this.getCodAlterar() == null) {
            SelecaoInvalida erro = new SelecaoInvalida(this, true);
            erro.setVisible(true);
        } else {

            ALTERAR_ITEM_PDV alterar = new ALTERAR_ITEM_PDV(this, true);
            alterar.pegaDados(this);

            alterar.setVisible(true);
            pegaDadosAlteracao(alterar);
            somaTabelaAposAlteracao();
            tabela.clearSelection();
            txtCodBarras.requestFocus();

            this.setCodAlterar(null);
        }

    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnAlterarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseExited
        btnAlterar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnAlterarMouseExited

    private void btnAlterarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlterarMouseEntered
        btnAlterar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnAlterarMouseEntered

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed

        txtCodBarras.requestFocus();
        //  somaTabela();
        buscaStatusCaixa();
        if (statusCaixa.equals("FECHADO")) {

            ImageIcon icon = new ImageIcon("src/imagens/erro.png");
            JOptionPane.showMessageDialog(this, "Venda não autorizada!" + "\n"
                    + "Para prosseguir é necessário efetuar abertura de caixa.",
                    "Atenção!", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {

            String chave = (String) cbCondPagamento.getSelectedItem() + (String) cbFormaPagamento.getSelectedItem();
            if (chave.equals("A VISTADINHEIRO")) {
                troco();
            } else if (cbFormaPagamento.getSelectedItem().equals("MULTI FORMAS") && valorDinheiro > 0) {
                salvarComPgMultAtualizandoCaixa();

            } else {
                //JOptionPane.showMessageDialog(this, "Esta entrada nesse metodo");
                salvar();

            }

        }
        carregaArrayList();
        txtCodBarras.requestFocus();
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnFinalizarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFinalizarMouseReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_btnFinalizarMouseReleased

    private void btnFinalizarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFinalizarMousePressed

        btnFinalizar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnFinalizarMousePressed

    private void btnFinalizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFinalizarMouseExited
        btnFinalizar.setBackground(new java.awt.Color(0, 153, 153));

        // TODO add your handling code here:
    }//GEN-LAST:event_btnFinalizarMouseExited

    private void btnFinalizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFinalizarMouseEntered
        btnFinalizar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnFinalizarMouseEntered

    private void btnFinalizarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnFinalizarFocusGained
        btnFinalizar.setBackground(new java.awt.Color(0, 204, 204));


    }//GEN-LAST:event_btnFinalizarFocusGained

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        if (produtoSelecioando == null) {
            SelecaoInvalida erro = new SelecaoInvalida(new javax.swing.JFrame(), true);
            erro.setVisible(true);
        } else {
            excluirLinhaTablea();
            somaTabela();
            txtItens.setText(String.valueOf(tabela.getRowCount()));
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtCodBarrasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarrasKeyReleased

        if (cbBoxInputManual.isSelected()) {

        } else {

            if (txtCodBarras.getText().equals("")) {

            } else {
                //    buscaProduto();
                buscaProdutoDoArryay();
                if (preco == "") {

                } else {

                    totalItem();
                    if (estoque < 1 && parametro_estoque == "N") {
                        //   JOptionPane.showMessageDialog(null, parametro_estoque);
                        MSGA_PDV01 msg = new MSGA_PDV01(this, true);
                        msg.setVisible(true);
                    } else {
                        addItemTabela();
                        somaTabela();
                        txtCodBarras.requestFocus();
                        txtItens.setText(String.valueOf(tabela.getRowCount()));
                        txtCodBarras.setText("");
                        preco = "";
                        totalProduto = "";
                        txtQuantidade.setText("1,00");

                    }
                }
            }
        }
    }//GEN-LAST:event_txtCodBarrasKeyReleased

    private void txtCodBarrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBarrasActionPerformed

        if (txtCodBarras.getText().equals("")) {
        } else {

            if (cbBoxInputManual.isSelected()) {

            } else {
                //  buscaProduto();
                buscaProdutoDoArryay();
                totalItem();
                if (estoque < 1 && parametro_estoque == "N") {
                    JOptionPane.showMessageDialog(null, parametro_estoque);
                    MSGA_PDV01 msg = new MSGA_PDV01(this, true);
                    msg.setVisible(true);
                } else {
                    addItemTabela();
                    somaTabela();
                    txtCodBarras.requestFocus();
                    txtItens.setText(String.valueOf(tabela.getRowCount()));
                    txtCodBarras.setText("");
                    preco = "";
                    totalProduto = "";
                    txtQuantidade.setText("1,00");

                }

            }
        }

    }//GEN-LAST:event_txtCodBarrasActionPerformed

    private void txtCodBarrasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarrasFocusLost

        if (txtCodBarras.getText().equals("")) {
        } else {

            if (cbBoxInputManual.isSelected()) {

            } else {

                buscaProdutoDoArryay();
                totalItem();
                if (estoque < 1 && parametro_estoque == "N") {
                    JOptionPane.showMessageDialog(null, parametro_estoque);
                    MSGA_PDV01 msg = new MSGA_PDV01(this, true);
                    msg.setVisible(true);
                } else {
                    addItemTabela();
                    somaTabela();
                    txtCodBarras.requestFocus();
                    txtItens.setText(String.valueOf(tabela.getRowCount()));
                    txtCodBarras.setText("");
                    preco = "";
                    totalProduto = "";
                    txtQuantidade.setText("1,00");

                }

            }
        }
    }//GEN-LAST:event_txtCodBarrasFocusLost

    private void btnAbrirCaixaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAbrirCaixaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirCaixaFocusGained

    private void btnAbrirCaixaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirCaixaMouseEntered

    private void btnAbrirCaixaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirCaixaMouseExited

    private void btnAbrirCaixaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirCaixaMousePressed

    private void btnAbrirCaixaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAbrirCaixaMouseReleased

    private void btnAbrirCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirCaixaActionPerformed
        buscaStatusCaixa();
        btnAbrirCaixa.setBackground(new Color(0, 255, 153));
        btnAbrirCaixa.setForeground(Color.BLACK);
        ModelOperacaoCaixaPDV modCaixa = new ModelOperacaoCaixaPDV();
        modCaixa.setOperacao("ACESSAR CAIXA");
        Aprovador_Caixa_PDV aprovador = new Aprovador_Caixa_PDV(this, true);
        aprovador.setVisible(true);

// Caixa_PDV caixa = new Caixa_PDV(this, true);
        //caixa.setVisible(true);
        btnAbrirCaixa.setBackground(new Color(239, 237, 237));
        btnAbrirCaixa.setForeground(Color.BLACK);

    }//GEN-LAST:event_btnAbrirCaixaActionPerformed

    private void btnEntrar4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrar4FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar4FocusGained

    private void btnEntrar4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar4MouseEntered

    private void btnEntrar4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar4MouseExited

    private void btnEntrar4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar4MousePressed

    private void btnEntrar4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar4MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar4MouseReleased

    private void btnEntrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrar4ActionPerformed
        buscaStatusCaixa();
        if (statusCaixa.equals("FECHADO")) {

            ImageIcon icon = new ImageIcon("src/imagens/erro.png");
            JOptionPane.showMessageDialog(null, "Venda não autorizada!" + "\n"
                    + "Para processeguir é necessário efetuar abertuda de caixa.",
                    "Atenção!", JOptionPane.INFORMATION_MESSAGE, icon);
        } else {

            String chave = (String) cbCondPagamento.getSelectedItem() + (String) cbFormaPagamento.getSelectedItem();
            if (chave.equals("A VISTADINHEIRO") || (chave.equals("A VISTAPIX"))) {
                salvar_atualizando_caixa();
            } else {
                salvar();
            }

        }
    }//GEN-LAST:event_btnEntrar4ActionPerformed

    private void btnEntrar5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrar5FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar5FocusGained

    private void btnEntrar5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar5MouseEntered

    private void btnEntrar5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar5MouseExited

    private void btnEntrar5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar5MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar5MousePressed

    private void btnEntrar5MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar5MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar5MouseReleased

    private void btnEntrar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrar5ActionPerformed
        double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", ".").replaceAll(";", ""));
        quantidade = quantidade + 1;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String quantidadeFormatada = df.format(quantidade);

        txtQuantidade.setText(quantidadeFormatada);

        txtCodBarras.requestFocus();

    }//GEN-LAST:event_btnEntrar5ActionPerformed

    private void btnEntrar6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrar6FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar6FocusGained

    private void btnEntrar6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar6MouseEntered

    private void btnEntrar6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar6MouseExited

    private void btnEntrar6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar6MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar6MousePressed

    private void btnEntrar6MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar6MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar6MouseReleased

    private void btnEntrar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrar6ActionPerformed
        double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", ".").replaceAll(";", ""));
        quantidade = quantidade - 1;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String quantidadeFormatada = df.format(quantidade);

        txtQuantidade.setText(quantidadeFormatada);
        txtCodBarras.requestFocus();
    }//GEN-LAST:event_btnEntrar6ActionPerformed

    private void btnEntrar7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrar7FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar7FocusGained

    private void btnEntrar7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar7MouseEntered

    private void btnEntrar7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar7MouseExited

    private void btnEntrar7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar7MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar7MousePressed

    private void btnEntrar7MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar7MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar7MouseReleased

    private void btnEntrar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrar7ActionPerformed

    }//GEN-LAST:event_btnEntrar7ActionPerformed

    private void btnAbrirCaixaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirCaixaMouseClicked
        Caixa_PDV caixa = new Caixa_PDV(new javax.swing.JFrame(), true);
        caixa.setVisible(true);
    }//GEN-LAST:event_btnAbrirCaixaMouseClicked

    private void cbBoxInputManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBoxInputManualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBoxInputManualActionPerformed

    private void txtDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusLost
        calculoValorFinal();

//Formata o campo
        double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String descontoFormatado = df.format(desconto);
        txtDesconto.setText(descontoFormatado);
        btnFinalizar.requestFocus();

    }//GEN-LAST:event_txtDescontoFocusLost

    private void txtDescontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDescontoMouseClicked
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoMouseClicked

    private void txtDescontoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusGained
        txtDesconto.selectAll();

        double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String descontoFormatado = df.format(desconto);
        txtDesconto.setText(descontoFormatado);
        txtDesconto.selectAll();
    }//GEN-LAST:event_txtDescontoFocusGained

    private void cbBoxCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBoxCodBarraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBoxCodBarraActionPerformed

    private void cbBoxCodBarraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbBoxCodBarraMouseClicked
        if (cbBoxCodBarra.isSelected()) {
            cbBoxCodInterno.setSelected(false);
            txtCodInterno.setText("");
            txtCodInterno.setEnabled(false);
            txtCodBarras.requestFocus();
            txtCodBarras.setEnabled(true);
            cbBoxCodBarra.setBackground(new Color(239, 237, 237));
            cbBoxCodBarra.setOpaque(true);

        } else {
            txtCodBarras.setText("");
            txtCodBarras.setEnabled(false);

        }
    }//GEN-LAST:event_cbBoxCodBarraMouseClicked

    private void cbBoxCodInternoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbBoxCodInternoMouseClicked
        if (cbBoxCodInterno.isSelected()) {
            cbBoxCodBarra.setSelected(false);
            txtCodBarras.setText("");
            txtCodBarras.setEnabled(false);
            txtCodInterno.requestFocus();
            txtCodInterno.setEnabled(true);
            cbBoxInputManual.setSelected(true);
            cbBoxInputLeitor.setSelected(false);
        } else {
            txtCodInterno.setText("");
            txtCodInterno.setEnabled(false);
            cbBoxInputManual.setSelected(false);
            cbBoxInputLeitor.setSelected(false);

        }
    }//GEN-LAST:event_cbBoxCodInternoMouseClicked

    private void cbBoxInputLeitorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbBoxInputLeitorMouseClicked
        txtCodBarras.setText("");
        if (cbBoxInputLeitor.isSelected()) {
            cbBoxInputManual.setSelected(false);
            txtCodBarras.requestFocus();

        } else {
            txtCodBarras.requestFocus();

        }
    }//GEN-LAST:event_cbBoxInputLeitorMouseClicked

    private void cbBoxInputManualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbBoxInputManualMouseClicked
        txtCodBarras.setText("");
        cbBoxInputManual.setSelected(true);
        if (cbBoxInputManual.isSelected()) {
            cbBoxInputLeitor.setSelected(false);
            txtCodBarras.requestFocus();
        } else {
            txtCodBarras.requestFocus();
        }
    }//GEN-LAST:event_cbBoxInputManualMouseClicked

    private void txtCodBarrasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarrasKeyPressed

        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            if (cbBoxInputLeitor.isSelected()) {
                JOptionPane.showMessageDialog(this, "Selecione a opção Input Manual.");
            } else {

        

            }
        }
    }//GEN-LAST:event_txtCodBarrasKeyPressed

    private void txtDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescontoActionPerformed
        calculoValorFinal();

//Formata o campo
        double desconto = Double.parseDouble(txtDesconto.getText().replace(".", "").replaceAll(",", "."));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String descontoFormatado = df.format(desconto);
        txtDesconto.setText(descontoFormatado);
        btnFinalizar.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescontoActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        BROWSER_VENDAS venda = new BROWSER_VENDAS();
        venda.carregaTabela();
        venda.setVisible(true);
        dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtQuantidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQuantidadeMouseClicked
        txtQuantidade.selectAll();

    }//GEN-LAST:event_txtQuantidadeMouseClicked

    private void txtQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantidadeActionPerformed
        double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", ".").replaceAll(";", ""));

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String quantidadeFormatada = df.format(quantidade);

        txtQuantidade.setText(quantidadeFormatada);
        txtCodBarras.requestFocus();
    }//GEN-LAST:event_txtQuantidadeActionPerformed

    private void txtQuantidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantidadeFocusLost
        double quantidade = Double.parseDouble(txtQuantidade.getText().replace(",", ".").replaceAll(";", ""));

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String quantidadeFormatada = df.format(quantidade);

        txtQuantidade.setText(quantidadeFormatada);
        txtCodBarras.requestFocus();
    }//GEN-LAST:event_txtQuantidadeFocusLost

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        int linha = tabela.getSelectedRow();
        setProdutoSelecioando((String) tabela.getValueAt(linha, 1).toString());

        setLinhaSelecionada(tabela.getSelectedRow());
        setCodAlterar((String) tabela.getValueAt(linha, 2).toString());//Codigo Interno
        setNomeAlterar((String) tabela.getValueAt(linha, 3).toString());//Produto
        setQtdeAlterar((String) tabela.getValueAt(linha, 5).toString());//Quantidade
        setPrecoAlterar((String) tabela.getValueAt(linha, 6).toString());//Preço
        setTotalAlterar((String) tabela.getValueAt(linha, 7).toString());//Total
        setCodBarraAlterar((String) tabela.getValueAt(linha, 12).toString());//Codigo de Barra

        setEstoqueAtual((float) tabela.getValueAt(linha, 13)); //Estoque
        setValorAtual((float) tabela.getValueAt(linha, 14)); //Valor Estoque
        setNovoEstoque((float) tabela.getValueAt(linha, 15)); //Estoque
        setNovoValor((float) tabela.getValueAt(linha, 16)); //Valor Estoque


    }//GEN-LAST:event_tabelaMouseClicked

    private void cbFormaPagamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFormaPagamentoKeyPressed

        descontoCondicional();
        btnFinalizar.requestFocus();

    }//GEN-LAST:event_cbFormaPagamentoKeyPressed

    private void cbFormaPagamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbFormaPagamentoFocusLost
        descontoCondicional();

        if (lblValorFinal.getText().equals("0,00")) {
            JOptionPane.showMessageDialog(this, "Insira algum produto");
        } else {
            if (cbFormaPagamento.getSelectedItem() == "CARTÃO DE CREDITO") {

                this.setValorParaCalculoParcela(txtSubTotal.getText());
                this.setCodVenda(Integer.parseInt(lblVenda.getText()));

                JD_PARCELAMENTO_PDV parcelamento = new JD_PARCELAMENTO_PDV(this, true);
                parcelamento.pegaValor(this);
                //  parcelamento.preenchePArcelas();
                parcelamento.setVisible(true);

                txtTaxasCartao.setText(String.valueOf(parcelamento.getValorTaxa()));
                lblParcelamento.setText(" Parcelamento: " + parcelamento.getParcelamento());
                this.setPlanoCartao(parcelamento.getPlano());
                //Formatar o valor da taxa
                float valorTaxa = Float.parseFloat(txtTaxasCartao.getText());
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String valorDaTaxa = df.format(valorTaxa);
                txtTaxasCartao.setText(valorDaTaxa);

            }
            if (cbFormaPagamento.getSelectedItem() == "CARTÃO DE DEBITO") {
                float valor = Float.parseFloat(txtSubTotal.getText().replace(".", "").replaceAll(",", "."));
                float taxa = (float) (valor * 0.0135);

                DecimalFormat df = new DecimalFormat("#,##0.00");
                String valoTaxa = df.format(taxa);

                txtTaxasCartao.setText(valoTaxa);
                lblParcelamento.setText("Compra a vista no cartão de DEBITO");

            }

            if (cbFormaPagamento.getSelectedItem() == "MULTI FORMAS") {
                cbCondPagamento.setSelectedItem("MULT COND");
                this.setValorParaCalculoParcela(txtSubTotal.getText());
                this.setCodVenda(Integer.parseInt(lblVenda.getText()));

                JD_MULT_FORMAS parcelamento = new JD_MULT_FORMAS(this, true);
                parcelamento.pegaValor(this);
                parcelamento.setVisible(true);
                txtTaxasCartao.setText(String.valueOf(parcelamento.getValorTaxa()));
                lblParcelamento.setText(" Parcelamento: " + parcelamento.getParcelamento());
                this.setPlanoCartao(parcelamento.getPlano());
                this.setValorDinheiro(parcelamento.getValorDinheiro());
                this.setValorPix(parcelamento.getValorPix());
                this.setValorCartaoCredito(parcelamento.getValorCredito());
                this.setValorCartaoDebito(parcelamento.getValorDebito());
                //Formatar o valor da taxa
                float valorTaxa = Float.parseFloat(txtTaxasCartao.getText());
                DecimalFormat df = new DecimalFormat("#,##0.00");
                String valorDaTaxa = df.format(valorTaxa);
                txtTaxasCartao.setText(valorDaTaxa);

            }

        }
    }//GEN-LAST:event_cbFormaPagamentoFocusLost

    private void cbFormaPagamentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbFormaPagamentoKeyReleased
        descontoCondicional();


    }//GEN-LAST:event_cbFormaPagamentoKeyReleased

    private void cbFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFormaPagamentoActionPerformed
        descontoCondicional();
    }//GEN-LAST:event_cbFormaPagamentoActionPerformed

    private void btnFinalizarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnFinalizarFocusLost
        btnFinalizar.setBackground(new java.awt.Color(0, 153, 153));
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFinalizarFocusLost

    private void btnEntrar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrar3ActionPerformed
        JF_MENU menu = new JF_MENU();
        menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnEntrar3ActionPerformed

    private void btnEntrar3MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar3MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar3MouseReleased

    private void btnEntrar3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar3MousePressed

    private void btnEntrar3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar3MouseExited

    private void btnEntrar3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntrar3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar3MouseEntered

    private void btnEntrar3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEntrar3FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEntrar3FocusGained

    private void txtCodBarrasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarrasKeyTyped

    }//GEN-LAST:event_txtCodBarrasKeyTyped

    private void txtCodBarrasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarrasFocusGained
//        somaTabela();

        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodBarrasFocusGained

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed

    }//GEN-LAST:event_tabelaKeyPressed

    private void tabelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyReleased

        int tecla = 65;
        int tecla2 = evt.getKeyCode();

        if (tecla == tecla2) {

            ALTERAR_ITEM_PDV alterar = new ALTERAR_ITEM_PDV(this, true);
            alterar.pegaDados(this);

            alterar.setVisible(true);
            pegaDadosAlteracao(alterar);
            somaTabelaAposAlteracao();
            tabela.clearSelection();
            txtCodBarras.requestFocus();

        } else {

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaKeyReleased

    private void cbVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbVendedorActionPerformed
        buscaComissaoVendedor();
// TODO add your handling code here:
    }//GEN-LAST:event_cbVendedorActionPerformed

    private void cbVendedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbVendedorFocusLost

    }//GEN-LAST:event_cbVendedorFocusLost

    private void txtCodBarrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodBarrasMouseClicked
        tabela.clearSelection();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodBarrasMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        //   buscaProduto();
        buscaProdutoDoArryay();
        totalItem();
        if (estoque < 1 && parametro_estoque == "N") {
            MSGA_PDV01 msg = new MSGA_PDV01(this, true);
            msg.setVisible(true);
        } else {
            addItemTabela();
            somaTabela();
            txtCodBarras.requestFocus();
            txtItens.setText(String.valueOf(tabela.getRowCount()));
            txtCodBarras.setText("");
            preco = "";
            totalProduto = "";
            txtQuantidade.setText("1,00");

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbFormaPagamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbFormaPagamentoMouseClicked

    }//GEN-LAST:event_cbFormaPagamentoMouseClicked

    private void cbFormaPagamentoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbFormaPagamentoMouseReleased

    }//GEN-LAST:event_cbFormaPagamentoMouseReleased

    private void txtAcrescimoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAcrescimoMouseClicked
        txtAcrescimo.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtAcrescimoMouseClicked

    private void txtAcrescimoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAcrescimoFocusLost
        //Formata o campo
        double acrecimo = Double.parseDouble(txtAcrescimo.getText().replace(".", "").replaceAll(",", "."));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String descontoFormatado = df.format(acrecimo);
        txtAcrescimo.setText(descontoFormatado);
        btnFinalizar.requestFocus();
        calculoValorFinal();
    }//GEN-LAST:event_txtAcrescimoFocusLost

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date ds = new Date();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String h = dtf.format(LocalTime.now());

        Timer time = new Timer(10, new hora());
        time.start();
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDV.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PDV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirCaixa;
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEntrar3;
    private javax.swing.JButton btnEntrar4;
    private javax.swing.JButton btnEntrar5;
    private javax.swing.JButton btnEntrar6;
    private javax.swing.JButton btnEntrar7;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JCheckBox cbBoxCodBarra;
    private javax.swing.JCheckBox cbBoxCodInterno;
    private javax.swing.JCheckBox cbBoxInputLeitor;
    private javax.swing.JCheckBox cbBoxInputManual;
    private javax.swing.JComboBox<String> cbCondPagamento;
    private javax.swing.JComboBox<String> cbFormaPagamento;
    private javax.swing.JComboBox cbVendedor;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblCodCat;
    private javax.swing.JLabel lblCodInterno;
    private javax.swing.JLabel lblCodSub;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblEstoqueAtual;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblParcelamento;
    private javax.swing.JLabel lblPrecoVenda;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblStatusCaixa;
    private javax.swing.JLabel lblSubCategoria;
    private javax.swing.JLabel lblUnidade;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblValorFinal;
    private javax.swing.JLabel lblVenda;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtAcrescimo;
    private javax.swing.JTextField txtCodBarras;
    private javax.swing.JTextField txtCodInterno;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtItens;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTaxasCartao;
    // End of variables declaration//GEN-END:variables

    class hora implements ActionListener {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String dia = dtf2.format(LocalDateTime.now());
        String hora = dtf.format(LocalDateTime.now());

        @Override
        public void actionPerformed(ActionEvent e) {

            Calendar now = Calendar.getInstance();
            lblData.setText(String.format(dia + " | " + "%1$tH:%1$tM", now));

            //  lblData.setText(String.format(dia + " | " + "%1$tH:%1$tM:%1$tS", now));
        }
    }

}
