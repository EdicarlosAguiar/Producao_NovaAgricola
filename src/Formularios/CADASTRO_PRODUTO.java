/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import FormNotificacao.CONFUPDATEPROD;
import FormNotificacao.Confirmacao;
import FormNotificacao.campoObrigatorio;
import FormNotificacao.exclusaoNaoAutorizada;
import FormNotificacao.produtoDuplicado;
import FormNotificacao.subCategoriaInvalida;
import FormulariosConsultas.ConsultaCategoria;
import FormulariosConsultas.ConsultaSubCategoria;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author edica
 */
public class CADASTRO_PRODUTO extends javax.swing.JFrame {

    Conexao conecta = new Conexao();
    private int ultimaCompra;
    private static String categoriaVinculada;
    private String pegaCodBarra;
    private String verificaCadastroDuplicado;
    private String pegaNomeDoProdutoJaCadastrado;
    private String codigo;
    private String nome;
    private String unidade;
    private String resp, parametro_estoque, margem;

    public String getMargem() {
        return margem;
    }

    public void setMargem(String margem) {
        this.margem = margem;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getPegaNomeDoProdutoJaCadastrado() {
        return pegaNomeDoProdutoJaCadastrado;
    }

    public void setPegaNomeDoProdutoJaCadastrado(String pegaNomeDoProdutoJaCadastrado) {
        this.pegaNomeDoProdutoJaCadastrado = pegaNomeDoProdutoJaCadastrado;
    }

    public String getVerificaCadastroDuplicado() {
        return verificaCadastroDuplicado;
    }

    public void setVerificaCadastroDuplicado(String verificaCadastroDuplicado) {
        this.verificaCadastroDuplicado = verificaCadastroDuplicado;
    }

    public String getCategoriaVinculada() {
        return categoriaVinculada;
    }

    public void setCategoriaVinculada(String categoriaVinculada) {
        this.categoriaVinculada = categoriaVinculada;
    }
    String categoriaVinculadaNaSub;
    private static String codigo_categoria;
    private static String nome_categora;
    private static String codigo_sub_categoria;
    private static String nome_sub_categora;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataAtual = dataBase.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    public CADASTRO_PRODUTO() {
        initComponents();

        utilitario util = new utilitario();
        util.inserirIcon(this);

        this.setExtendedState(MAXIMIZED_BOTH);

        configIniciais();
        carregaComboUnidade();
        cbUnidade.setBackground(Color.WHITE);

        txtNome.requestFocus();
        txtCodBarra.requestFocus();
        txtData.setEnabled(false);
        txtDataAlteracao.setEnabled(false);
        txtUltimaCompra.setEnabled(false);
        txtUltimaReq.setEnabled(false);
        txtUltimaVenda.setEnabled(false);

    }

    public void geraCodigoProduto() {
        txtCodigo.setText("");
        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select codigo from tb_produtos where codSub = ?");
            pst.setString(1, txtCodSub.getText());

            rs = pst.executeQuery();

            while (rs.next()) {

                int codioatual = rs.getInt(1);
                int codigoGerado = codioatual + 1;

                txtCodigo.setText(String.valueOf(codigoGerado));

            }

            if (txtCodigo.getText().equals("")) {
                txtCodigo.setText(txtCodSub.getText() + "00001");
            } else {

            }

            conecta.getConexao().close();

        } catch (Exception ex) {

        }

    }

    public void tituloIncluir() {
        lblTitulo.setText("Cadastro de Produto - Incluir");
        cbBloqueado.setEnabled(false);
        txtNome.requestFocus();
        cbBloqueado.setVisible(false);
        lblBloquear.setVisible(false);
        txtSaldoAtual.setText("0,00");
        txtCustoAtual.setText("0,00");
        txtValorAtual.setText("0,00");
        txtEstoqueInicial.setText("0,00");
        txtPrecoCompra.setText("0,00");
        txtValorInicial.setText("0,00");
        txtValorInicial.setEnabled(false);
        txtEstoqueInicial.setEnabled(false);
        txtPrecoCompra.setEnabled(false);
        txtSaldoAtual.setEnabled(false);
        txtCustoAtual.setEnabled(false);
        txtValorAtual.setEnabled(false);
        txtReentrada.setText(String.valueOf(0));
        txtDosagem.setText(String.valueOf(0));
        txtCarencia.setText(String.valueOf(0));
     

    }

    public void parametrosAlterar() {
        lblTitulo.setText("Cadastro de Produto - Alterar");

        Cores cor = new Cores();

        //Desabilitando campos que não podem ser alterados!
        cbUnidade.setEnabled(false);
        txtCodCat.setEnabled(false);
        txtCodSub.setEnabled(false);
        txtFabricante.setEnabled(false);
        txtMarca.setEnabled(false);

        //Cor de faundo dos campos desabilitados!
        cbUnidade.setBackground(cor.getCorCampoDesabilitado());
        txtCodCat.setBackground(cor.getCorCampoDesabilitado());
        txtCodSub.setBackground(cor.getCorCampoDesabilitado());
        txtMarca.setBackground(cor.getCorCampoDesabilitado());
        txtFabricante.setBackground(cor.getCorCampoDesabilitado());
        txtCodigo.setBackground(cor.getCorCampoDesabilitado());
        cbUnidade.setBackground(Color.white);

        txtNome.requestFocus();

    }

    public void parametrosVisualizar() {
        Cores cor = new Cores();
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();

        lblTitulo.setText("Cadastro de Produto - Visualizar");

        txtCodigo.setEnabled(false);
        txtNome.setEnabled(false);
        cbUnidade.setEnabled(false);
        txtCodCat.setEnabled(false);
        txtCategoria.setEnabled(false);
        txtCodSub.setEnabled(false);
        txtSubCat.setEnabled(false);
        txtCodMarca.setEnabled(false);
        txtMarca.setEnabled(false);
        txtCodFab.setEnabled(false);
        txtFabricante.setEnabled(false);
        txtPrecoVenda.setEnabled(false);
        txtCodBarra.setEnabled(false);
        txtEstoqueInicial.setEnabled(false);
        txtMargem.setEnabled(false);
        cbBloqueado.setEnabled(false);
        cbNaturezaProduto.setEnabled(false);
        txtPrecoCompra.setEnabled(false);
        txtCustoAtual.setEnabled(false);
        txtSaldoAtual.setEnabled(false);
        txtValorAtual.setEnabled(false);
        txtValorInicial.setEnabled(false);

        txtCodigo.setBackground(cor.getCorCampoDesabilitado());
        txtNome.setBackground(cor.getCorCampoDesabilitado());
        cbUnidade.setBackground(cor.getCorCampoDesabilitado());
        txtCodCat.setBackground(cor.getCorCampoDesabilitado());
        txtCategoria.setBackground(cor.getCorCampoDesabilitado());
        txtCodSub.setBackground(cor.getCorCampoDesabilitado());
        txtSubCat.setBackground(cor.getCorCampoDesabilitado());
        txtCodMarca.setBackground(cor.getCorCampoDesabilitado());
        txtMarca.setBackground(cor.getCorCampoDesabilitado());
        txtCodFab.setBackground(cor.getCorCampoDesabilitado());
        txtFabricante.setBackground(cor.getCorCampoDesabilitado());
        txtCodBarra.setBackground(cor.getCorCampoDesabilitado());
        txtPrecoVenda.setBackground(cor.getCorCampoDesabilitado());
        txtMargem.setBackground(cor.getCorCampoDesabilitado());
        txtEstoqueInicial.setBackground(cor.getCorCampoDesabilitado());
        cbBloqueado.setBackground(cor.getCorCampoDesabilitado());
        cbNaturezaProduto.setBackground(cor.getCorCampoDesabilitado());

    }

    public void parametrosExcluir() {
        lblTitulo.setText("Cadastro de Produto - Excluir");

        Cores cor = new Cores();

        txtCodigo.setEnabled(false);
        txtNome.setEnabled(false);
        cbUnidade.setEnabled(false);
        txtCodCat.setEnabled(false);
        txtCategoria.setEnabled(false);
        txtCodSub.setEnabled(false);
        txtSubCat.setEnabled(false);
        txtCodMarca.setEnabled(false);
        txtMarca.setEnabled(false);
        txtCodFab.setEnabled(false);
        txtFabricante.setEnabled(false);
        txtPrecoVenda.setEnabled(false);
        txtCodBarra.setEnabled(false);
        txtEstoqueInicial.setEnabled(false);
        txtMargem.setEnabled(false);
        cbBloqueado.setEnabled(false);
        cbNaturezaProduto.setEnabled(false);

        txtCodigo.setBackground(cor.getCorCampoDesabilitado());
        txtNome.setBackground(cor.getCorCampoDesabilitado());
        cbUnidade.setBackground(cor.getCorCampoDesabilitado());
        txtCodCat.setBackground(cor.getCorCampoDesabilitado());
        txtCategoria.setBackground(cor.getCorCampoDesabilitado());
        txtCodSub.setBackground(cor.getCorCampoDesabilitado());
        txtSubCat.setBackground(cor.getCorCampoDesabilitado());
        txtCodMarca.setBackground(cor.getCorCampoDesabilitado());
        txtMarca.setBackground(cor.getCorCampoDesabilitado());
        txtCodFab.setBackground(cor.getCorCampoDesabilitado());
        txtFabricante.setBackground(cor.getCorCampoDesabilitado());
        txtCodBarra.setBackground(cor.getCorCampoDesabilitado());
        txtPrecoVenda.setBackground(cor.getCorCampoDesabilitado());
        txtMargem.setBackground(cor.getCorCampoDesabilitado());
        txtEstoqueInicial.setBackground(cor.getCorCampoDesabilitado());
        cbBloqueado.setBackground(cor.getCorCampoDesabilitado());
        cbNaturezaProduto.setBackground(cor.getCorCampoDesabilitado());

        painelCorpo.setBackground(cor.getCorPainelCorpoForm());

    }

    public void configIniciais() {

        Cores cor = new Cores();

        painelCorpo.setBackground(cor.getCorPainelCorpoForm());
        txtCategoria.setEnabled(false);
        txtSubCat.setEnabled(false);
        txtCategoria.setBackground(cor.getCorCampoDesabilitado());
        txtSubCat.setBackground(cor.getCorCampoDesabilitado());
        txtMarca.setBackground(cor.getCorCampoDesabilitado());
        txtFabricante.setBackground(cor.getCorCampoDesabilitado());
        txtCodigo.setBackground(cor.getCorCampoDesabilitado());
        cbUnidade.setBackground(Color.white);
        txtMarca.setEnabled(false);
        txtFabricante.setEnabled(false);
        txtPrecoVenda.setText("0,00");
        txtData.setText(dataDigiatacao);

        txtCodBarra.requestFocus();

    }

    public void buscaCategoria() {
        ConsultaCategoria cat = new ConsultaCategoria(this, true);

        int totalItens = cat.getArrayCategoria().size();
        int i = 0;
        boolean busca = false;
        while (totalItens - 2 >= i && busca == false) {
            if (txtCodCat.getText().equals(cat.getArrayCategoria().get(i))) {
                txtCategoria.setText(cat.getArrayCategoria().get(i + 1));
                busca = true;
            } else {
                i = i + 2;
            }

        }

    }

    public void buscaSubCategoria() {
        ConsultaSubCategoria sub = new ConsultaSubCategoria(this, true);

        int totalItens = sub.getSubCategoria().size();
        int i = 0;
        boolean busca = false;
        while (totalItens - 4 >= i && busca == false) {
            if (txtCodSub.getText().equals(sub.getSubCategoria().get(i))) {
                txtSubCat.setText(sub.getSubCategoria().get(i + 1));
                busca = true;
                categoriaVinculadaNaSub = txtCodSub.getText().substring(0, 1);

            } else {
                i = i + 4;
            }

        }
    }

    public void buscaUltimaCompra() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select * from compra where codProduto=?");
            pst.setString(1, txtCodigo.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                ultimaCompra = (rs.getInt(2));

            }
            conecta.getConexao().close();

        } catch (Exception ex) {

        }
    }

    public void carregaComboUnidade() {

        try {

            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select * from unidade");

            rs = pst.executeQuery();

            while (rs.next()) {

                cbUnidade.addItem(rs.getString("und"));

            }

            conecta.getConexao().close();

        } catch (Exception ex) {

        }
    }

    public void buscaFabricante() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select fabricante from fabricante where codigo = ?");
            pst.setString(1, txtCodFab.getText());

            rs = pst.executeQuery();

            while (rs.next()) {
                txtFabricante.setText(rs.getString(3));

            }
            conecta.getConexao().close();

        } catch (Exception ex) {

        }
    }

    public void buscaMarca() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select * from marca where codigo = ?");
            pst.setString(1, txtCodMarca.getText());

            rs = pst.executeQuery();

            while (rs.next()) {

                txtMarca.setText(rs.getString(3));
            }
            conecta.getConexao().close();

        } catch (Exception ex) {

        }
    }

    public void pegaProduto(BASE_PROD prod) {
        txtCodigo.setText(prod.getProdutoSelecionado().replace("  ", ""));
        buscaProduto();
    }

    public void buscaProduto() {
        cbNaturezaProduto.setSelectedItem("");
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos where codigo = ?");
            pst.setString(1, txtCodigo.getText().replace("  ", ""));
            rs = pst.executeQuery();

            while (rs.next()) {

                //Saldos Iniciais
                float estoqueInicial = Float.parseFloat(rs.getString(18).replace(".", "").replaceAll(",", ".")); //Saldo Atual Quantidade
                float valorIcinial = rs.getFloat(19); //Saldo Atual valor
                float custoInicial = valorIcinial / estoqueInicial; //Custo Medio Atual

                //Saldos Atuais
                float estoqueAtual = rs.getFloat(20); //Saldo Atual Quantidade
                float valorAtual = rs.getFloat(21); //Saldo Atual valor
                float custoAtual = valorAtual / estoqueAtual; //Custo Medio Atual

                txtNome.setText(rs.getString(6));
                cbUnidade.addItem(rs.getString(7));
                txtCodCat.setText(rs.getString(4));
                txtCategoria.setText(rs.getString(8));
                txtCodSub.setText(rs.getString(5));
                txtSubCat.setText(rs.getString(9));

                txtCodBarra.setText(rs.getString(16));
                txtPrecoVenda.setText(rs.getString(15));
                cbNaturezaProduto.addItem(rs.getString(17));

                //Iniciais
                txtEstoqueInicial.setText(rs.getString(18));
                txtValorInicial.setText(String.valueOf(valorIcinial));
                txtPrecoCompra.setText(String.valueOf(custoInicial));

                //Auais
                txtSaldoAtual.setText(String.valueOf(estoqueAtual));
                txtValorAtual.setText(String.valueOf(valorAtual));
                txtCustoAtual.setText(String.valueOf(custoAtual));

                cbBloqueado.setSelectedItem(rs.getString(10));
                txtData.setText(rs.getString(3));
                txtDataAlteracao.setText(rs.getString(23));
                txtUltimaCompra.setText(rs.getString(24));
                txtUltimaVenda.setText(rs.getString(25));
                txtUltimaReq.setText(rs.getString(26));
                cb_PETQ_001.addItem(rs.getString(27));
                txtMargem.setText(rs.getString(28));

            }
            conn.getConexao().close();
            pst.close();
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void verificaCadastroDuplicado() {
        cbNaturezaProduto.setSelectedItem("");
        try {
            Conexao conn = new Conexao();
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conn.getConexao().prepareStatement("select codBarra, codigo,nome from tb_produtos where codBarra = ?");
            pst.setString(1, txtCodBarra.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                pegaCodBarra = (rs.getString(1));
                this.setVerificaCadastroDuplicado(rs.getString(2));
                this.setPegaNomeDoProdutoJaCadastrado(rs.getString(3));
            }
            conn.getConexao().close();
            pst.close();
            rs.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao Conectar com banco!" + ex);
        }
    }

    public void salvar() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy" + " hh:mm:ss");
        String dataCadastro = dtf.format(LocalDateTime.now());

        try {
            PreparedStatement pst = conecta.getConexao().prepareStatement("insert into tb_produtos(codigo,nome,und,codCat,categoria,codSub,"
                    + "subCategoria,bloqueado,data,codMarca,marca,codFab,fabricante,precoVenda,"
                    + "codBarra,natureza,estoque_inicial,valorInicial, atual,valor,PETQ_001,garantias_princAtivo,justificativa,"
                    + "carencia, dosagem_ha,reent_epi,class_tox,classe)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setString(1, txtCodigo.getText());
            pst.setString(2, txtNome.getText());
            pst.setString(3, (String) cbUnidade.getSelectedItem());
            pst.setString(4, txtCodCat.getText());
            pst.setString(5, txtCategoria.getText());
            pst.setString(6, txtCodSub.getText());
            pst.setString(7, txtSubCat.getText());

            pst.setString(8, (String) cbBloqueado.getSelectedItem());
            pst.setString(9, dataCadastro);
            pst.setString(10, txtCodMarca.getText());
            pst.setString(11, txtMarca.getText());
            pst.setString(12, txtCodFab.getText());
            pst.setString(13, txtFabricante.getText());

            pst.setString(14, txtPrecoVenda.getText());
            pst.setString(15, txtCodBarra.getText());
            pst.setString(16, (String) cbNaturezaProduto.getSelectedItem());
            pst.setFloat(17, Float.parseFloat(txtEstoqueInicial.getText().replace(".", "").replaceAll(",", ".")));
            pst.setFloat(18, Float.parseFloat(txtValorInicial.getText().replace(".", "").replaceAll(",", ".")));

            pst.setFloat(19, Float.parseFloat(txtEstoqueInicial.getText().replace(".", "").replaceAll(",", ".")));
            pst.setFloat(20, Float.parseFloat(txtValorInicial.getText().replace(".", "").replaceAll(",", ".")));
            pst.setString(21, (String) cb_PETQ_001.getSelectedItem());
            pst.setString(22, txtGarantias.getText());
            pst.setString(23, txtJustificativa.getText());
            pst.setInt(24, Integer.parseInt(txtCarencia.getText()));
            pst.setFloat(25, Float.parseFloat(txtDosagem.getText().replace(".", "").replaceAll(",", ".")));
            pst.setString(26, txtReentrada.getText());
            pst.setString(27, (String) cbClassTox.getSelectedItem());
            pst.setString(28, txtClasse.getText());

            pst.execute();

            Confirmacao conf = new Confirmacao(this, true);
            conf.setVisible(true);

            txtNome.setText("");
            txtCodigo.setText("");
            txtCodCat.setText("");
            txtCategoria.setText("");
            txtCodSub.setText("");
            txtSubCat.setText("");
            txtCodMarca.setText("");
            txtMarca.setText("");
            txtCodFab.setText("");
            txtFabricante.setText("");
            txtPrecoVenda.setText("");
            txtCodBarra.setText("");
            txtNome.requestFocus();
            Cadastrais.requestFocus();
          
            txtNome.requestFocus();

            pst.close();

            BASE_PROD produto = new BASE_PROD();
            //   produto.carregaArray();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, " Erro ao tentar salvar o registro!   " + "\n" + "\n"
                    + " Erro: " + ex, "Notificação de erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void excluirProdudto() {

        try {

            int resp1 = JOptionPane.showConfirmDialog(null, "Confirma a exclusão do produto " + txtCodigo.getText() + "?", "Exclusão de Produto", JOptionPane.YES_NO_OPTION);
            if (resp1 == 0) {

                Conexao conn = new Conexao();
                PreparedStatement pst;

                ResultSet rs;
                int linha = 1;
                pst = conn.getConexao().prepareStatement("delete from tb_produtos where codigo=?");
                pst.setInt(1, Integer.parseInt(txtCodigo.getText()));
                pst.executeUpdate();

                Confirmacao conf = new Confirmacao(this, true);
                conf.textoProdutoExcluir();
                conf.setVisible(true);
                pst.close();

                BASE_PROD base = new BASE_PROD();
                base.setVisible(true);
                this.dispose();

            } else {

                BASE_PROD base = new BASE_PROD();
                base.setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
        }

    }

    public void alterar() {

        try {

            Conexao conn = new Conexao();

            PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE tb_produtos SET nome=?, "
                    + "codBarra=?,precoVenda=?,estoque_inicial=?,codMarca=?,marca=?,bloqueado=?, PETQ_001=?"
                    + " where codigo =?");

            pst.setString(1, txtNome.getText());//Altera nome do Produto
            pst.setString(2, txtCodBarra.getText()); //Altera Codido de Barras
            pst.setString(3, txtPrecoVenda.getText()); //Altera Preco de Venda
            pst.setFloat(4, Float.parseFloat(txtEstoqueInicial.getText().replace(".", "").replaceAll(",", "."))); //Altera Estoque Inicial
            pst.setInt(5, 0); //Altera Cod da Marca
            pst.setString(6, "SEM MARCA"); //Altera Nome da Marca
            pst.setString(7, (String) cbBloqueado.getSelectedItem()); //Altera Status
            pst.setString(8, (String) cb_PETQ_001.getSelectedItem());
            pst.setString(9, txtCodigo.getText());

            pst.executeUpdate();

            Confirmacao conf = new Confirmacao(this, true);
            conf.textoProdutoAlterado();
            conf.setVisible(true);
            BASE_PROD base = new BASE_PROD();

            base.carregaTabela();
            base.setVisible(true);
            dispose();

            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao tentar alterar o produto! " + e, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
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

        painelCorpo = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Cadastrais = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbUnidade = new javax.swing.JComboBox<>();
        txtCodSub = new javax.swing.JTextField();
        txtCodCat = new javax.swing.JTextField();
        txtSubCat = new javax.swing.JTextField();
        txtCategoria = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtCodMarca = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtCodFab = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtFabricante = new javax.swing.JTextField();
        txtCodBarra = new javax.swing.JTextField();
        lblBloquear = new javax.swing.JLabel();
        cbBloqueado = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        cbNaturezaProduto = new javax.swing.JComboBox<>();
        txtCodigo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtCodFab1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtData = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtDataAlteracao = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        txtDesEspecifica = new javax.swing.JTextField();
        Estoque = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        txtEstoqueInicial = new javax.swing.JFormattedTextField();
        jLabel42 = new javax.swing.JLabel();
        txtValorInicial = new javax.swing.JFormattedTextField();
        jLabel43 = new javax.swing.JLabel();
        txtPrecoCompra = new javax.swing.JFormattedTextField();
        jLabel44 = new javax.swing.JLabel();
        txtSaldoAtual = new javax.swing.JFormattedTextField();
        jLabel45 = new javax.swing.JLabel();
        txtCustoAtual = new javax.swing.JFormattedTextField();
        jLabel46 = new javax.swing.JLabel();
        txtValorAtual = new javax.swing.JFormattedTextField();
        jLabel47 = new javax.swing.JLabel();
        txtUltimaCompra = new javax.swing.JFormattedTextField();
        jLabel48 = new javax.swing.JLabel();
        txtUltimaVenda = new javax.swing.JFormattedTextField();
        jLabel49 = new javax.swing.JLabel();
        txtUltimaReq = new javax.swing.JFormattedTextField();
        jLabel26 = new javax.swing.JLabel();
        cb_PETQ_001 = new javax.swing.JComboBox<>();
        comercial = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtPrecoVenda = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        lblCalcPreco = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtMargem = new javax.swing.JFormattedTextField();
        Impostos = new javax.swing.JPanel();
        lblBloquear1 = new javax.swing.JLabel();
        cbBloqueado1 = new javax.swing.JComboBox<>();
        lblBloquear2 = new javax.swing.JLabel();
        cbBloqueado2 = new javax.swing.JComboBox<>();
        lblBloquear3 = new javax.swing.JLabel();
        cbBloqueado3 = new javax.swing.JComboBox<>();
        lblBloquear4 = new javax.swing.JLabel();
        cbBloqueado4 = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        txtNCM = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        Impostos1 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        txtDosagem = new javax.swing.JFormattedTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        cbClassTox = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        txtGarantias = new javax.swing.JTextField();
        txtJustificativa = new javax.swing.JTextField();
        txtCarencia = new javax.swing.JTextField();
        txtReentrada = new javax.swing.JTextField();
        txtClasse = new javax.swing.JTextField();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        painelCorpo.setBackground(new java.awt.Color(255, 255, 255));
        painelCorpo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        jTabbedPane1.setBackground(new java.awt.Color(191, 229, 243));
        jTabbedPane1.setForeground(new java.awt.Color(0, 102, 255));
        jTabbedPane1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTabbedPane1FocusGained(evt);
            }
        });

        Cadastrais.setBackground(new java.awt.Color(248, 248, 248));

        jLabel17.setText("Codigo:");

        jLabel18.setText("Nome do Produto:");

        jLabel21.setText("U. de Medida:");

        jLabel22.setText("Categoria:");

        jLabel25.setText("Sub Categoria:");

        jLabel20.setText("Codigo de Barras");

        cbUnidade.setBackground(new java.awt.Color(102, 102, 102));
        cbUnidade.setAutoscrolls(true);
        cbUnidade.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 236, 236), 1, true));
        cbUnidade.setOpaque(true);
        cbUnidade.setPreferredSize(new java.awt.Dimension(28, 25));
        cbUnidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbUnidadeFocusLost(evt);
            }
        });
        cbUnidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUnidadeActionPerformed(evt);
            }
        });
        cbUnidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbUnidadeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbUnidadeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cbUnidadeKeyTyped(evt);
            }
        });

        txtCodSub.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodSub.setPreferredSize(new java.awt.Dimension(6, 25));
        txtCodSub.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodSubFocusLost(evt);
            }
        });
        txtCodSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodSubActionPerformed(evt);
            }
        });
        txtCodSub.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodSubKeyPressed(evt);
            }
        });

        txtCodCat.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodCat.setPreferredSize(new java.awt.Dimension(6, 25));
        txtCodCat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCatFocusLost(evt);
            }
        });
        txtCodCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCatActionPerformed(evt);
            }
        });
        txtCodCat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodCatKeyPressed(evt);
            }
        });

        txtSubCat.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSubCat.setPreferredSize(new java.awt.Dimension(6, 25));

        txtCategoria.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCategoria.setPreferredSize(new java.awt.Dimension(6, 25));

        jLabel23.setText("Cod Cat:");

        jLabel24.setText("Cod Sub:");

        txtNome.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNome.setPreferredSize(new java.awt.Dimension(6, 25));
        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        jLabel27.setText("Cod Marca:");

        txtCodMarca.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodMarca.setPreferredSize(new java.awt.Dimension(6, 25));
        txtCodMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMarcaFocusLost(evt);
            }
        });
        txtCodMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMarcaActionPerformed(evt);
            }
        });
        txtCodMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodMarcaKeyPressed(evt);
            }
        });

        jLabel28.setText("Marca:");

        txtMarca.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMarca.setPreferredSize(new java.awt.Dimension(6, 25));

        jLabel29.setText("Cod Fab:");

        txtCodFab.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodFab.setPreferredSize(new java.awt.Dimension(6, 25));
        txtCodFab.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFabFocusLost(evt);
            }
        });
        txtCodFab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFabActionPerformed(evt);
            }
        });
        txtCodFab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodFabKeyPressed(evt);
            }
        });

        jLabel35.setText("Fabricante:");

        txtFabricante.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtFabricante.setPreferredSize(new java.awt.Dimension(6, 25));
        txtFabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFabricanteActionPerformed(evt);
            }
        });

        txtCodBarra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodBarra.setEnabled(false);
        txtCodBarra.setPreferredSize(new java.awt.Dimension(6, 25));
        txtCodBarra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBarraFocusLost(evt);
            }
        });
        txtCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBarraActionPerformed(evt);
            }
        });

        lblBloquear.setText("Bloqueado:");

        cbBloqueado.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));
        cbBloqueado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        jLabel34.setText("Natureza comercial do Produto:");

        cbNaturezaProduto.setBackground(new java.awt.Color(102, 102, 102));
        cbNaturezaProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PRODUTO PARA REVENDA", "PRODUÇÃO PROPRIA PARA VENDA", "OUTROS" }));
        cbNaturezaProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 0, 51));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("*");
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 0, 51));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("*");
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 0, 51));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("*");
        jLabel32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("*");
        jLabel33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel36.setText("Armazem Padrão:");

        txtCodFab1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCodFab1.setPreferredSize(new java.awt.Dimension(6, 25));
        txtCodFab1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodFab1FocusLost(evt);
            }
        });
        txtCodFab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodFab1ActionPerformed(evt);
            }
        });
        txtCodFab1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodFab1KeyPressed(evt);
            }
        });

        jLabel37.setText("Data Criação:");

        txtData.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtData.setPreferredSize(new java.awt.Dimension(6, 25));
        txtData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataFocusLost(evt);
            }
        });
        txtData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataActionPerformed(evt);
            }
        });
        txtData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataKeyPressed(evt);
            }
        });

        jLabel39.setText("Data Alteração");

        txtDataAlteracao.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDataAlteracao.setPreferredSize(new java.awt.Dimension(6, 25));
        txtDataAlteracao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataAlteracaoFocusLost(evt);
            }
        });
        txtDataAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataAlteracaoActionPerformed(evt);
            }
        });
        txtDataAlteracao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataAlteracaoKeyPressed(evt);
            }
        });

        jLabel51.setText("Descrição especifica:");

        txtDesEspecifica.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDesEspecifica.setPreferredSize(new java.awt.Dimension(6, 25));
        txtDesEspecifica.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesEspecificaFocusLost(evt);
            }
        });
        txtDesEspecifica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesEspecificaActionPerformed(evt);
            }
        });
        txtDesEspecifica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDesEspecificaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout CadastraisLayout = new javax.swing.GroupLayout(Cadastrais);
        Cadastrais.setLayout(CadastraisLayout);
        CadastraisLayout.setHorizontalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel35))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(txtCodFab, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(txtFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbBloqueado, 0, 73, Short.MAX_VALUE)
                            .addComponent(lblBloquear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(cbNaturezaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CadastraisLayout.createSequentialGroup()
                                    .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtCodCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, CadastraisLayout.createSequentialGroup()
                                            .addComponent(jLabel23)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(CadastraisLayout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jLabel22))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(10, 10, 10)
                                    .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCodSub, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(CadastraisLayout.createSequentialGroup()
                                            .addComponent(jLabel24)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27))
                                .addGap(10, 10, 10)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel51)
                                    .addComponent(txtDesEspecifica, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCodFab1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(txtDataAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CadastraisLayout.setVerticalGroup(
            CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastraisLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel20)
                .addGap(11, 11, 11)
                .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(lblBloquear)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19))
                                .addGap(11, 11, 11)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel30))
                                .addGap(11, 11, 11)
                                .addComponent(cbUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel51)
                                .addGap(11, 11, 11)
                                .addComponent(txtDesEspecifica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)
                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel28)
                                            .addComponent(jLabel27))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtCodMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(txtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel32)
                                            .addComponent(jLabel22))
                                        .addGap(11, 11, 11)
                                        .addComponent(txtCodCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(CadastraisLayout.createSequentialGroup()
                                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel33))
                                        .addGap(10, 10, 10)
                                        .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtCodSub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSubCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(10, 10, 10)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel35)
                                    .addComponent(jLabel29))
                                .addGap(11, 11, 11)
                                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCodFab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel34)
                                .addGap(11, 11, 11)
                                .addComponent(cbNaturezaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addGap(11, 11, 11)
                        .addComponent(txtCodFab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addGap(11, 11, 11)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CadastraisLayout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addGap(11, 11, 11)
                        .addComponent(txtDataAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CADASTRAIS", Cadastrais);

        Estoque.setBackground(new java.awt.Color(248, 248, 248));

        jLabel41.setText("Estoque Inicial:");
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtEstoqueInicial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtEstoqueInicial.setForeground(new java.awt.Color(51, 51, 51));
        txtEstoqueInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtEstoqueInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstoqueInicial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEstoqueInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEstoqueInicialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEstoqueInicialFocusLost(evt);
            }
        });
        txtEstoqueInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEstoqueInicialMouseClicked(evt);
            }
        });
        txtEstoqueInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstoqueInicialActionPerformed(evt);
            }
        });

        jLabel42.setText("Valor Inicial");
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtValorInicial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtValorInicial.setForeground(new java.awt.Color(51, 51, 51));
        txtValorInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValorInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorInicial.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtValorInicial.setEnabled(false);
        txtValorInicial.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtValorInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorInicialFocusGained(evt);
            }
        });
        txtValorInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtValorInicialMouseClicked(evt);
            }
        });
        txtValorInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorInicialActionPerformed(evt);
            }
        });

        jLabel43.setText("Custo Inicial");
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtPrecoCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPrecoCompra.setForeground(new java.awt.Color(51, 51, 51));
        txtPrecoCompra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtPrecoCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecoCompra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecoCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecoCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecoCompraFocusLost(evt);
            }
        });
        txtPrecoCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecoCompraMouseClicked(evt);
            }
        });
        txtPrecoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoCompraActionPerformed(evt);
            }
        });

        jLabel44.setText("Estoque Atual");
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtSaldoAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtSaldoAtual.setForeground(new java.awt.Color(51, 51, 51));
        txtSaldoAtual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSaldoAtual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSaldoAtual.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtSaldoAtual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSaldoAtualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSaldoAtualFocusLost(evt);
            }
        });
        txtSaldoAtual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSaldoAtualMouseClicked(evt);
            }
        });
        txtSaldoAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoAtualActionPerformed(evt);
            }
        });

        jLabel45.setText("Custo Atual");
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtCustoAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtCustoAtual.setForeground(new java.awt.Color(51, 51, 51));
        txtCustoAtual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtCustoAtual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCustoAtual.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCustoAtual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCustoAtualFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCustoAtualFocusLost(evt);
            }
        });
        txtCustoAtual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCustoAtualMouseClicked(evt);
            }
        });
        txtCustoAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustoAtualActionPerformed(evt);
            }
        });

        jLabel46.setText("Valor Atual");
        jLabel46.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtValorAtual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtValorAtual.setForeground(new java.awt.Color(51, 51, 51));
        txtValorAtual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtValorAtual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtValorAtual.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtValorAtual.setEnabled(false);
        txtValorAtual.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtValorAtual.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtValorAtualFocusGained(evt);
            }
        });
        txtValorAtual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtValorAtualMouseClicked(evt);
            }
        });
        txtValorAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorAtualActionPerformed(evt);
            }
        });

        jLabel47.setText("Ultima Compra");
        jLabel47.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtUltimaCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtUltimaCompra.setForeground(new java.awt.Color(51, 51, 51));
        txtUltimaCompra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtUltimaCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimaCompra.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUltimaCompra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUltimaCompraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUltimaCompraFocusLost(evt);
            }
        });
        txtUltimaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUltimaCompraMouseClicked(evt);
            }
        });
        txtUltimaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltimaCompraActionPerformed(evt);
            }
        });

        jLabel48.setText("Ultima Venda");
        jLabel48.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtUltimaVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtUltimaVenda.setForeground(new java.awt.Color(51, 51, 51));
        txtUltimaVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtUltimaVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimaVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtUltimaVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUltimaVendaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUltimaVendaFocusLost(evt);
            }
        });
        txtUltimaVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUltimaVendaMouseClicked(evt);
            }
        });
        txtUltimaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltimaVendaActionPerformed(evt);
            }
        });

        jLabel49.setText("Ultima Requisição");
        jLabel49.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtUltimaReq.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtUltimaReq.setForeground(new java.awt.Color(51, 51, 51));
        txtUltimaReq.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtUltimaReq.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimaReq.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txtUltimaReq.setEnabled(false);
        txtUltimaReq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUltimaReqFocusGained(evt);
            }
        });
        txtUltimaReq.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUltimaReqMouseClicked(evt);
            }
        });
        txtUltimaReq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUltimaReqActionPerformed(evt);
            }
        });

        jLabel26.setText("Perminite baixa sem saldo?");

        cb_PETQ_001.setBackground(new java.awt.Color(102, 102, 102));
        cb_PETQ_001.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "N" }));
        cb_PETQ_001.setAutoscrolls(true);
        cb_PETQ_001.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(239, 236, 236), 1, true));
        cb_PETQ_001.setOpaque(true);
        cb_PETQ_001.setPreferredSize(new java.awt.Dimension(28, 25));
        cb_PETQ_001.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cb_PETQ_001FocusLost(evt);
            }
        });
        cb_PETQ_001.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_PETQ_001ActionPerformed(evt);
            }
        });
        cb_PETQ_001.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cb_PETQ_001KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cb_PETQ_001KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cb_PETQ_001KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout EstoqueLayout = new javax.swing.GroupLayout(Estoque);
        Estoque.setLayout(EstoqueLayout);
        EstoqueLayout.setHorizontalGroup(
            EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EstoqueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EstoqueLayout.createSequentialGroup()
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(txtEstoqueInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(txtSaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(txtCustoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(EstoqueLayout.createSequentialGroup()
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(txtUltimaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel48)
                            .addComponent(txtUltimaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUltimaReq, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_PETQ_001, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(410, Short.MAX_VALUE))
        );
        EstoqueLayout.setVerticalGroup(
            EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EstoqueLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(EstoqueLayout.createSequentialGroup()
                            .addComponent(jLabel45)
                            .addGap(11, 11, 11)
                            .addComponent(txtCustoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EstoqueLayout.createSequentialGroup()
                                .addComponent(jLabel46)
                                .addGap(11, 11, 11)
                                .addComponent(txtValorAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EstoqueLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addGap(11, 11, 11)
                                .addComponent(txtSaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(EstoqueLayout.createSequentialGroup()
                            .addComponent(jLabel43)
                            .addGap(11, 11, 11)
                            .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EstoqueLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(11, 11, 11)
                                .addComponent(txtValorInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EstoqueLayout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addGap(11, 11, 11)
                                .addComponent(txtEstoqueInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28)
                .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(EstoqueLayout.createSequentialGroup()
                            .addComponent(jLabel48)
                            .addGap(11, 11, 11)
                            .addComponent(txtUltimaVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(EstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EstoqueLayout.createSequentialGroup()
                                .addComponent(jLabel49)
                                .addGap(11, 11, 11)
                                .addComponent(txtUltimaReq, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EstoqueLayout.createSequentialGroup()
                                .addComponent(jLabel47)
                                .addGap(11, 11, 11)
                                .addComponent(txtUltimaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(EstoqueLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(11, 11, 11)
                        .addComponent(cb_PETQ_001, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(264, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ESTOQUE", Estoque);

        comercial.setBackground(new java.awt.Color(248, 248, 248));

        jLabel31.setText("Preco de Venda:");
        jLabel31.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtPrecoVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPrecoVenda.setForeground(new java.awt.Color(51, 51, 51));
        txtPrecoVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtPrecoVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecoVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecoVenda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPrecoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecoVendaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPrecoVendaFocusLost(evt);
            }
        });
        txtPrecoVenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPrecoVendaMouseClicked(evt);
            }
        });
        txtPrecoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecoVendaActionPerformed(evt);
            }
        });

        jLabel38.setText("Margem Bruta %:");
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblCalcPreco.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCalcPreco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCalcPrecoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCalcPrecoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCalcPrecoMouseExited(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel8.setBackground(new java.awt.Color(208, 227, 208));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("%");

        txtMargem.setBorder(null);
        txtMargem.setForeground(new java.awt.Color(51, 51, 51));
        txtMargem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtMargem.setCaretColor(new java.awt.Color(51, 51, 51));
        txtMargem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtMargem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMargemFocusGained(evt);
            }
        });
        txtMargem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMargemMouseClicked(evt);
            }
        });
        txtMargem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMargemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMargem, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
            .addComponent(txtMargem)
        );

        javax.swing.GroupLayout comercialLayout = new javax.swing.GroupLayout(comercial);
        comercial.setLayout(comercialLayout);
        comercialLayout.setHorizontalGroup(
            comercialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comercialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(comercialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(comercialLayout.createSequentialGroup()
                        .addGroup(comercialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecoVenda)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCalcPreco)))
                .addContainerGap(1023, Short.MAX_VALUE))
        );
        comercialLayout.setVerticalGroup(
            comercialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comercialLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel38)
                .addGap(11, 11, 11)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel31)
                .addGap(11, 11, 11)
                .addGroup(comercialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCalcPreco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPrecoVenda))
                .addContainerGap(269, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("COMERCIAL", comercial);

        Impostos.setBackground(new java.awt.Color(248, 248, 248));

        lblBloquear1.setText("Calculo PIS?");

        cbBloqueado1.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));
        cbBloqueado1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        lblBloquear2.setText("Calcula COFINS?");

        cbBloqueado2.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));
        cbBloqueado2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        lblBloquear3.setText("Calcula IPI?");

        cbBloqueado3.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));
        cbBloqueado3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        lblBloquear4.setText("Calcula ICMS?");

        cbBloqueado4.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N", "S" }));
        cbBloqueado4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));

        jLabel40.setText("NCM:");
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtNCM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtNCM.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtNCM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNCM.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNCM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNCMFocusGained(evt);
            }
        });
        txtNCM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNCMMouseClicked(evt);
            }
        });
        txtNCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNCMActionPerformed(evt);
            }
        });

        jLabel2.setOpaque(true);

        javax.swing.GroupLayout ImpostosLayout = new javax.swing.GroupLayout(Impostos);
        Impostos.setLayout(ImpostosLayout);
        ImpostosLayout.setHorizontalGroup(
            ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImpostosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addGroup(ImpostosLayout.createSequentialGroup()
                        .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNCM, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ImpostosLayout.createSequentialGroup()
                                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBloqueado4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBloquear4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBloqueado1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBloquear1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ImpostosLayout.createSequentialGroup()
                                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblBloquear2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                    .addComponent(cbBloqueado2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBloqueado3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBloquear3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 829, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        ImpostosLayout.setVerticalGroup(
            ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImpostosLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel40)
                .addGap(11, 11, 11)
                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNCM, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(ImpostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ImpostosLayout.createSequentialGroup()
                        .addComponent(lblBloquear1)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ImpostosLayout.createSequentialGroup()
                        .addComponent(lblBloquear4)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ImpostosLayout.createSequentialGroup()
                        .addComponent(lblBloquear3)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ImpostosLayout.createSequentialGroup()
                        .addComponent(lblBloquear2)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(269, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("IMPOSTOS", Impostos);

        Impostos1.setBackground(new java.awt.Color(248, 248, 248));

        jLabel50.setText("Garantias/Principio Ativo:");
        jLabel50.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel52.setText("Justificativa:");
        jLabel52.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel53.setText("Carencia (Dias):");
        jLabel53.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtDosagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtDosagem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtDosagem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDosagem.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDosagem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDosagemFocusGained(evt);
            }
        });
        txtDosagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDosagemMouseClicked(evt);
            }
        });
        txtDosagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDosagemActionPerformed(evt);
            }
        });

        jLabel54.setText("Dosagem Ha:");
        jLabel54.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel55.setText("Reent. s/ EPI.");
        jLabel55.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        jLabel56.setText("Classificação toxicologica:");
        jLabel56.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        cbClassTox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0 - Não se aplica", "1 - Extremamente tóxico (vermelha)", "2 - Altamente tóxico (amarela)", "3 - Moderadamente  tóxico (verde)", "4 - Pouco tóxico (azul)" }));

        jLabel57.setText("Classe:");
        jLabel57.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout Impostos1Layout = new javax.swing.GroupLayout(Impostos1);
        Impostos1.setLayout(Impostos1Layout);
        Impostos1Layout.setHorizontalGroup(
            Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Impostos1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbClassTox, 0, 263, Short.MAX_VALUE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel56)
                    .addComponent(txtGarantias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57)
                    .addGroup(Impostos1Layout.createSequentialGroup()
                        .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtClasse, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(Impostos1Layout.createSequentialGroup()
                                    .addComponent(jLabel52)
                                    .addGap(205, 205, 205)
                                    .addComponent(jLabel53))
                                .addGroup(Impostos1Layout.createSequentialGroup()
                                    .addComponent(txtJustificativa, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtCarencia, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel54)
                            .addComponent(txtDosagem, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(txtReentrada, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(244, Short.MAX_VALUE))
        );
        Impostos1Layout.setVerticalGroup(
            Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Impostos1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Impostos1Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(11, 11, 11)
                        .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDosagem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtReentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCarencia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJustificativa, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGarantias, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Impostos1Layout.createSequentialGroup()
                        .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel53)
                            .addComponent(jLabel52)
                            .addComponent(jLabel50))
                        .addGap(35, 35, 35)))
                .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Impostos1Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(34, 34, 34))
                    .addGroup(Impostos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbClassTox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtClasse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Impostos1Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addGap(35, 35, 35)))
                .addContainerGap(285, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("AGRO", Impostos1);

        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        btnConfirmar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnConfirmar.setText("Finalizar");
        btnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnConfirmar.setContentAreaFilled(false);
        btnConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfirmar.setMargin(new java.awt.Insets(2, 4, 2, 4));
        btnConfirmar.setOpaque(true);
        btnConfirmar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnConfirmarFocusGained(evt);
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

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
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

        lblTitulo.setBackground(new java.awt.Color(238, 234, 234));
        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 51, 102));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Cadastro de Produto - Incluir");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblTitulo.setOpaque(true);

        javax.swing.GroupLayout painelCorpoLayout = new javax.swing.GroupLayout(painelCorpo);
        painelCorpo.setLayout(painelCorpoLayout);
        painelCorpoLayout.setHorizontalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelCorpoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1))
                .addContainerGap())
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        painelCorpoLayout.setVerticalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addGap(5, 5, 5)
                .addGroup(painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar))
                .addGap(10, 10, 10))
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
    }// </editor-fold>//GEN-END:initComponents

    private void cbUnidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbUnidadeFocusLost

    }//GEN-LAST:event_cbUnidadeFocusLost

    private void cbUnidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUnidadeActionPerformed
        txtCodCat.requestFocus();
    }//GEN-LAST:event_cbUnidadeActionPerformed

    private void cbUnidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbUnidadeKeyPressed
        txtCodCat.requestFocus();
    }//GEN-LAST:event_cbUnidadeKeyPressed

    private void cbUnidadeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbUnidadeKeyReleased

    }//GEN-LAST:event_cbUnidadeKeyReleased

    private void cbUnidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbUnidadeKeyTyped

    }//GEN-LAST:event_cbUnidadeKeyTyped

    private void txtCodSubFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodSubFocusLost
        if (txtCodSub.getText().equals("")) {

        } else {
            txtSubCat.setText("");
            buscaSubCategoria();
            geraCodigoProduto();

        }


    }//GEN-LAST:event_txtCodSubFocusLost

    private void txtCodSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSubActionPerformed
        if (txtCodSub.getText().equals("")) {

        } else {
            txtSubCat.setText("");
            buscaSubCategoria();
            geraCodigoProduto();
        }
    }//GEN-LAST:event_txtCodSubActionPerformed

    private void txtCodSubKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodSubKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            categoriaVinculada = txtCodCat.getText();
            ConsultaSubCategoria sub = new ConsultaSubCategoria(this, true);
            sub.carregaArray();
            sub.setPegaCodCat(this.getCategoriaVinculada());
            sub.parametroConsultaSub(this);
            sub.carregaTabela();
            sub.setVisible(true);
            String codigo = sub.getCodigo();
            String subCat = sub.getSubCat();

            txtCodSub.setText(codigo);
            txtSubCat.setText(subCat);
        } else {

        }
    }//GEN-LAST:event_txtCodSubKeyPressed

    private void txtCodCatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCatFocusLost
        if (txtCodCat.getText().equals("")) {

        } else {
            buscaCategoria();
        }
    }//GEN-LAST:event_txtCodCatFocusLost

    private void txtCodCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCatActionPerformed
        buscaCategoria();
        txtCodSub.requestFocus();
    }//GEN-LAST:event_txtCodCatActionPerformed

    private void txtCodCatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodCatKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaCategoria categoria = new ConsultaCategoria(this, true);
            categoria.carregaTabela();
            categoria.setVisible(true);
            String codigo = categoria.getCodigo().replace("  ", "");
            String cat = categoria.getCategoria();
            txtCodCat.setText(codigo);
            txtCategoria.setText(cat);
            txtCodSub.requestFocus();
        } else {

        }
    }//GEN-LAST:event_txtCodCatKeyPressed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        cbUnidade.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtCodMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMarcaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodMarcaFocusLost

    private void txtCodMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMarcaActionPerformed
        buscaMarca();
        txtCodFab.requestFocus();
    }//GEN-LAST:event_txtCodMarcaActionPerformed

    private void txtCodMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodMarcaKeyPressed
        /*  int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaMarca marca = new ConsultaMarca(new javax.swing.JFrame(), true);
            marca.setVisible(true);
            String codigo = marca.getCodigo();
            txtCodMarca.setText(codigo);
        } else {

        }*/
    }//GEN-LAST:event_txtCodMarcaKeyPressed

    private void txtCodFabFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFabFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFabFocusLost

    private void txtCodFabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFabActionPerformed
        txtCodBarra.requestFocus();
    }//GEN-LAST:event_txtCodFabActionPerformed

    private void txtCodFabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodFabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFabKeyPressed

    private void txtFabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFabricanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFabricanteActionPerformed

    private void txtCodBarraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarraFocusLost
        String acao = lblTitulo.getText();
        if (txtCodBarra.getText().equals("") || lblTitulo.getText().equals("Cadastro de Produto - Alterar")
                || lblTitulo.getText().equals("Cadastro de Produto - Excluir")) {

        } else {
            pegaCodBarra = "X'";
            verificaCadastroDuplicado();
            if (pegaCodBarra.equals(txtCodBarra.getText())) {
                produtoDuplicado prod = new produtoDuplicado(this, true);
                prod.pegaProduto(this);
                prod.setVisible(true);
            } else {

            }
        }
    }//GEN-LAST:event_txtCodBarraFocusLost

    private void txtCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBarraActionPerformed
        txtNome.requestFocus();
    }//GEN-LAST:event_txtCodBarraActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodFab1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodFab1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFab1FocusLost

    private void txtCodFab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodFab1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFab1ActionPerformed

    private void txtCodFab1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodFab1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodFab1KeyPressed

    private void txtDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataFocusLost

    private void txtDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataActionPerformed

    private void txtDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataKeyPressed

    private void txtDataAlteracaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataAlteracaoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataAlteracaoFocusLost

    private void txtDataAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataAlteracaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataAlteracaoActionPerformed

    private void txtDataAlteracaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataAlteracaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataAlteracaoKeyPressed

    private void txtEstoqueInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstoqueInicialFocusGained

    }//GEN-LAST:event_txtEstoqueInicialFocusGained

    private void txtEstoqueInicialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstoqueInicialFocusLost

    }//GEN-LAST:event_txtEstoqueInicialFocusLost

    private void txtEstoqueInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstoqueInicialMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicialMouseClicked

    private void txtEstoqueInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstoqueInicialActionPerformed
        txtPrecoCompra.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicialActionPerformed

    private void txtValorInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorInicialFocusGained

    private void txtValorInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtValorInicialMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorInicialMouseClicked

    private void txtValorInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorInicialActionPerformed

    private void txtPrecoCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoCompraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoCompraFocusGained

    private void txtPrecoCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoCompraFocusLost

        float quantidade = Float.parseFloat(txtEstoqueInicial.getText().replace(".", "").replaceAll(",", "."));
        float preco = Float.parseFloat(txtPrecoCompra.getText().replace(".", "").replaceAll(",", "."));
        float total = quantidade * preco;

        DecimalFormat df = new DecimalFormat("#,##0.00");
        String valorFormatado = df.format(total);
        txtValorInicial.setText(valorFormatado);
    }//GEN-LAST:event_txtPrecoCompraFocusLost

    private void txtPrecoCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecoCompraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoCompraMouseClicked

    private void txtPrecoCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoCompraActionPerformed
        btnConfirmar.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoCompraActionPerformed

    private void txtSaldoAtualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaldoAtualFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualFocusGained

    private void txtSaldoAtualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSaldoAtualFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualFocusLost

    private void txtSaldoAtualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSaldoAtualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualMouseClicked

    private void txtSaldoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSaldoAtualActionPerformed

    private void txtCustoAtualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustoAtualFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustoAtualFocusGained

    private void txtCustoAtualFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustoAtualFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustoAtualFocusLost

    private void txtCustoAtualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCustoAtualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustoAtualMouseClicked

    private void txtCustoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustoAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustoAtualActionPerformed

    private void txtValorAtualFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtValorAtualFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorAtualFocusGained

    private void txtValorAtualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtValorAtualMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorAtualMouseClicked

    private void txtValorAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorAtualActionPerformed

    private void txtUltimaCompraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUltimaCompraFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaCompraFocusGained

    private void txtUltimaCompraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUltimaCompraFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaCompraFocusLost

    private void txtUltimaCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUltimaCompraMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaCompraMouseClicked

    private void txtUltimaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltimaCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaCompraActionPerformed

    private void txtUltimaVendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUltimaVendaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaVendaFocusGained

    private void txtUltimaVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUltimaVendaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaVendaFocusLost

    private void txtUltimaVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUltimaVendaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaVendaMouseClicked

    private void txtUltimaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltimaVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaVendaActionPerformed

    private void txtUltimaReqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUltimaReqFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaReqFocusGained

    private void txtUltimaReqMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUltimaReqMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaReqMouseClicked

    private void txtUltimaReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUltimaReqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUltimaReqActionPerformed

    private void cb_PETQ_001FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cb_PETQ_001FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_PETQ_001FocusLost

    private void cb_PETQ_001ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_PETQ_001ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_PETQ_001ActionPerformed

    private void cb_PETQ_001KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cb_PETQ_001KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_PETQ_001KeyPressed

    private void cb_PETQ_001KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cb_PETQ_001KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_PETQ_001KeyReleased

    private void cb_PETQ_001KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cb_PETQ_001KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_PETQ_001KeyTyped

    private void txtPrecoVendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoVendaFocusGained
        txtPrecoVenda.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaFocusGained

    private void txtPrecoVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoVendaFocusLost
        if (txtPrecoVenda.getText().equals("")) {

        } else {
            DecimalFormat df = new DecimalFormat("#,##0.00");

            Double qtde = Double.parseDouble(txtPrecoVenda.getText().replace(".", "").replaceAll(",", "."));

            String quantidade = df.format(qtde);
            txtPrecoVenda.setText(quantidade);

        }
    }//GEN-LAST:event_txtPrecoVendaFocusLost

    private void txtPrecoVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecoVendaMouseClicked
        txtPrecoVenda.selectAll();         // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaMouseClicked

    private void txtPrecoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaActionPerformed

    private void lblCalcPrecoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCalcPrecoMouseClicked

        this.setCodigo(txtCodigo.getText());
        this.setNome(txtNome.getText());
        this.setMargem(txtMargem.getText());
        JD_CALCULO_PRECO calc = new JD_CALCULO_PRECO(this, true);
        calc.pegapProduto(this);
        calc.buscaUltimaCompra(this.getCodigo());

        calc.setVisible(true);
        String respPreco = calc.getRespostaPrecificacao();

        if (respPreco == "S") {
            String preco = calc.getPreco();
            txtPrecoVenda.setText(preco);
        } else {

        }

    }//GEN-LAST:event_lblCalcPrecoMouseClicked

    private void lblCalcPrecoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCalcPrecoMouseEntered
        lblCalcPreco.setBackground(new java.awt.Color(94, 100, 100));
    }//GEN-LAST:event_lblCalcPrecoMouseEntered

    private void lblCalcPrecoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCalcPrecoMouseExited
        lblCalcPreco.setBackground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_lblCalcPrecoMouseExited

    private void txtMargemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMargemFocusGained
        txtMargem.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtMargemFocusGained

    private void txtMargemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMargemMouseClicked
        txtMargem.selectAll();          // TODO add your handling code here:
    }//GEN-LAST:event_txtMargemMouseClicked

    private void txtMargemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMargemActionPerformed
        txtPrecoVenda.requestFocus();
    }//GEN-LAST:event_txtMargemActionPerformed

    private void txtNCMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNCMFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNCMFocusGained

    private void txtNCMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNCMMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNCMMouseClicked

    private void txtNCMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNCMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNCMActionPerformed

    private void txtDosagemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDosagemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDosagemFocusGained

    private void txtDosagemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDosagemMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDosagemMouseClicked

    private void txtDosagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDosagemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDosagemActionPerformed

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained
        txtMargem.requestFocus();
    }//GEN-LAST:event_jTabbedPane1FocusGained

    private void btnConfirmarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnConfirmarFocusGained
        btnConfirmar.setBackground(new java.awt.Color(0, 204, 204));
    }//GEN-LAST:event_btnConfirmarFocusGained

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
    }//GEN-LAST:event_btnConfirmarMouseReleased

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed

        if (txtCodigo.getText().equals("") && lblTitulo.getText().equals("Cadastro de Produto - Incluir")) {
            campoObrigatorio erro = new campoObrigatorio(this, true);
            erro.setVisible(true);
            txtCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));
            txtNome.requestFocus();

        } else {
            switch (lblTitulo.getText()) {
                case "Cadastro de Produto - Incluir":

                    salvar();

                    break;
                case "Cadastro de Produto - Alterar":

                    this.setCodigo(txtCodigo.getText());
                    this.setNome(txtNome.getText());
                    this.setUnidade((String) cbUnidade.getSelectedItem());

                    CONFUPDATEPROD CONF = new CONFUPDATEPROD(this, true);
                    CONF.pegaDados(this);

                    CONF.setVisible(true);

                    resp = CONF.getResposta();
                    if (resp == "SIM") {

                        alterar();
                    } else {

                    }

                    break;
                case "Cadastro de Produto - Excluir":
                    buscaUltimaCompra();

                    if (ultimaCompra > 0) {
                        exclusaoNaoAutorizada erro = new exclusaoNaoAutorizada(this, true);
                        erro.setVisible(true);
                    } else {
                        excluirProdudto();
                    }

                    break;
            }
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new java.awt.Color(239, 237, 237));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        BASE_PROD produto = new BASE_PROD();
        produto.carregaTabela();
        produto.setVisible(true);
        dispose();

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDesEspecificaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesEspecificaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesEspecificaFocusLost

    private void txtDesEspecificaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesEspecificaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesEspecificaActionPerformed

    private void txtDesEspecificaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDesEspecificaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDesEspecificaKeyPressed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CADASTRO_PRODUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CADASTRO_PRODUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CADASTRO_PRODUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CADASTRO_PRODUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CADASTRO_PRODUTO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cadastrais;
    private javax.swing.JPanel Estoque;
    private javax.swing.JPanel Impostos;
    private javax.swing.JPanel Impostos1;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cbBloqueado;
    private javax.swing.JComboBox<String> cbBloqueado1;
    private javax.swing.JComboBox<String> cbBloqueado2;
    private javax.swing.JComboBox<String> cbBloqueado3;
    private javax.swing.JComboBox<String> cbBloqueado4;
    private javax.swing.JComboBox<String> cbClassTox;
    private javax.swing.JComboBox<String> cbNaturezaProduto;
    private javax.swing.JComboBox<String> cbUnidade;
    private javax.swing.JComboBox<String> cb_PETQ_001;
    private javax.swing.JPanel comercial;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBloquear;
    private javax.swing.JLabel lblBloquear1;
    private javax.swing.JLabel lblBloquear2;
    private javax.swing.JLabel lblBloquear3;
    private javax.swing.JLabel lblBloquear4;
    private javax.swing.JLabel lblCalcPreco;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JTextField txtCarencia;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtClasse;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JTextField txtCodCat;
    private javax.swing.JTextField txtCodFab;
    private javax.swing.JTextField txtCodFab1;
    private javax.swing.JTextField txtCodMarca;
    private javax.swing.JTextField txtCodSub;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtCustoAtual;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDataAlteracao;
    private javax.swing.JTextField txtDesEspecifica;
    private javax.swing.JFormattedTextField txtDosagem;
    private javax.swing.JFormattedTextField txtEstoqueInicial;
    private javax.swing.JTextField txtFabricante;
    private javax.swing.JTextField txtGarantias;
    private javax.swing.JTextField txtJustificativa;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JFormattedTextField txtMargem;
    private javax.swing.JFormattedTextField txtNCM;
    private javax.swing.JTextField txtNome;
    private javax.swing.JFormattedTextField txtPrecoCompra;
    private javax.swing.JFormattedTextField txtPrecoVenda;
    private javax.swing.JTextField txtReentrada;
    private javax.swing.JFormattedTextField txtSaldoAtual;
    private javax.swing.JTextField txtSubCat;
    private javax.swing.JFormattedTextField txtUltimaCompra;
    private javax.swing.JFormattedTextField txtUltimaReq;
    private javax.swing.JFormattedTextField txtUltimaVenda;
    private javax.swing.JFormattedTextField txtValorAtual;
    private javax.swing.JFormattedTextField txtValorInicial;
    // End of variables declaration//GEN-END:variables
}
