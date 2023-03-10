/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import FormNotificacao.CONFUPDATEPROD;
import FormNotificacao.Confirmacao;
import FormNotificacao.campoObrigatorio;
import FormNotificacao.produtoDuplicado;
import FormulariosConsultas.ConsultaCategoria;
import FormulariosConsultas.ConsultaMarca;
import FormulariosConsultas.ConsultaSubCategoria;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import util.Conexao;
import util.Cores;
import util.utilitario;

/**
 *
 * @author Desktop
 */
public class JD_CAD_PROD_VIA_PDV extends javax.swing.JDialog {

    Conexao conecta = new Conexao();
    private int ultimaCompra;
    private String categoriaVinculada;
    private String pegaCodBarra;
    private String verificaCadastroDuplicado;
    private String pegaNomeDoProdutoJaCadastrado;
    private String codigo;
    private String nome;
    private String unidade;
    private String resp;

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

    private static String codigo_categoria;
    private static String nome_categora;
    private static String codigo_sub_categoria;
    private static String nome_sub_categora;

    public JD_CAD_PROD_VIA_PDV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        utilitario util = new utilitario();

        configIniciais();
        carregaComboUnidade();
        cbUnidade.setBackground(Color.WHITE);

        txtNome.requestFocus();
        txtCodBarra.requestFocus();
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

                txtCodigo.setText(String.valueOf("0" + codigoGerado));

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
        txtCodBarra.requestFocus();
        cbBloqueado.setVisible(false);
        lblBloquear.setVisible(false);
    }

    public void parametrosAlterar() {
        lblTitulo.setText("Cadastro de Produto - Alterar");

        Cores cor = new Cores();

        //Desabilitando campos que n??o podem ser alterados!
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

        txtCodBarra.requestFocus();

    }

    public void buscaCategoria() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select * from categoria where codigo=?");
            pst.setString(1, txtCodCat.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                txtCategoria.setText(rs.getString(3));

            }
            conecta.getConexao().close();

        } catch (Exception ex) {

        }
    }

    public void buscaSubCategoria() {

        try {
            PreparedStatement pst = null;
            ResultSet rs;
            pst = conecta.getConexao().prepareStatement("select * from subcategoria where codigo=?");
            pst.setString(1, txtCodSub.getText());
            rs = pst.executeQuery();

            while (rs.next()) {

                txtSubCat.setText(rs.getString(3));

            }
            conecta.getConexao().close();

        } catch (Exception ex) {

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

                cbUnidade.addItem(rs.getString("unidade"));

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
                txtNome.setText(rs.getString(3));
                cbUnidade.addItem(rs.getString(4));
                txtCodCat.setText(rs.getString(5));
                txtCategoria.setText(rs.getString(6));
                txtCodSub.setText(rs.getString(7));
                txtSubCat.setText(rs.getString(8));

                txtCodBarra.setText(rs.getString(16));
                txtPrecoVenda.setText(rs.getString(15));
                cbNaturezaProduto.addItem(rs.getString(17));
                txtEstoqueInicial.setText(rs.getString(18));
                cbBloqueado.setSelectedItem(rs.getString(9));

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
                    + "codBarra,natureza,estoque_inicial)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

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
            pst.setString(17, txtEstoqueInicial.getText());
            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
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
            pst.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, " Erro ao tentar salvar o registro!   " + "\n" + "\n"
                    + " Erro: " + ex, "Notifica????o de erro", JOptionPane.ERROR_MESSAGE);
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
        cbUnidade = new javax.swing.JComboBox<String>();
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
        cbBloqueado = new javax.swing.JComboBox<String>();
        jLabel34 = new javax.swing.JLabel();
        cbNaturezaProduto = new javax.swing.JComboBox<String>();
        txtCodigo = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtCodFab1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtPrecoVenda = new javax.swing.JFormattedTextField();
        jLabel38 = new javax.swing.JLabel();
        txtMargem = new javax.swing.JFormattedTextField();
        jLabel39 = new javax.swing.JLabel();
        txtEstoqueInicial = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        lblBloquear1 = new javax.swing.JLabel();
        cbBloqueado1 = new javax.swing.JComboBox<String>();
        lblBloquear2 = new javax.swing.JLabel();
        cbBloqueado2 = new javax.swing.JComboBox<String>();
        lblBloquear3 = new javax.swing.JLabel();
        cbBloqueado3 = new javax.swing.JComboBox<String>();
        lblBloquear4 = new javax.swing.JLabel();
        cbBloqueado4 = new javax.swing.JComboBox<String>();
        jLabel40 = new javax.swing.JLabel();
        txtEstoqueInicial1 = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

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
        cbUnidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbUnidade.setOpaque(false);
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
        cbBloqueado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N", "S" }));
        cbBloqueado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbBloqueado.setOpaque(false);

        jLabel34.setText("Natureza comercial do Produto:");

        cbNaturezaProduto.setBackground(new java.awt.Color(102, 102, 102));
        cbNaturezaProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PRODUTO PARA REVENDA", "PRODU????O PROPRIA PARA VENDA", "OUTROS" }));
        cbNaturezaProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbNaturezaProduto.setOpaque(false);

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

        jLabel36.setText("Armazem Padr??o:");

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
                    .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(cbUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(CadastraisLayout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(CadastraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtCodFab1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                                .addComponent(cbUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jLabel36)
                .addGap(11, 11, 11)
                .addComponent(txtCodFab1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CADASTRAIS", Cadastrais);

        jPanel2.setBackground(new java.awt.Color(248, 248, 248));

        jLabel31.setText("Preco de Venda:");
        jLabel31.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtPrecoVenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtPrecoVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtPrecoVenda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecoVenda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPrecoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPrecoVendaFocusGained(evt);
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

        txtMargem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtMargem.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtMargem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMargem.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtMargem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMargemFocusGained(evt);
            }
        });
        txtMargem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMargemActionPerformed(evt);
            }
        });

        jLabel39.setText("Estoque Inicial:");
        jLabel39.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtEstoqueInicial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtEstoqueInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtEstoqueInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstoqueInicial.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEstoqueInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEstoqueInicialFocusGained(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrecoVenda)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(txtMargem)
                    .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jLabel39)
                    .addComponent(txtEstoqueInicial))
                .addContainerGap(1056, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addGap(11, 11, 11)
                .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addGap(11, 11, 11)
                .addComponent(txtMargem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addGap(11, 11, 11)
                .addComponent(txtEstoqueInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("COMERCIAL", jPanel2);

        jPanel3.setBackground(new java.awt.Color(248, 248, 248));

        lblBloquear1.setText("Calculo PIS?");

        cbBloqueado1.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N", "S" }));
        cbBloqueado1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbBloqueado1.setOpaque(false);

        lblBloquear2.setText("Calcula COFINS?");

        cbBloqueado2.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N", "S" }));
        cbBloqueado2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbBloqueado2.setOpaque(false);

        lblBloquear3.setText("Calcula IPI?");

        cbBloqueado3.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N", "S" }));
        cbBloqueado3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbBloqueado3.setOpaque(false);

        lblBloquear4.setText("Calcula ICMS?");

        cbBloqueado4.setBackground(new java.awt.Color(102, 102, 102));
        cbBloqueado4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "N", "S" }));
        cbBloqueado4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 236, 236)));
        cbBloqueado4.setOpaque(false);

        jLabel40.setText("NCM:");
        jLabel40.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        txtEstoqueInicial1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txtEstoqueInicial1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtEstoqueInicial1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstoqueInicial1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEstoqueInicial1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEstoqueInicial1FocusGained(evt);
            }
        });
        txtEstoqueInicial1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEstoqueInicial1MouseClicked(evt);
            }
        });
        txtEstoqueInicial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstoqueInicial1ActionPerformed(evt);
            }
        });

        jLabel2.setOpaque(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtEstoqueInicial1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBloqueado4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBloquear4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBloqueado1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBloquear1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblBloquear2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                    .addComponent(cbBloqueado2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbBloqueado3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBloquear3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 824, Short.MAX_VALUE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel40)
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEstoqueInicial1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblBloquear1)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblBloquear4)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblBloquear3)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblBloquear2)
                        .addGap(11, 11, 11)
                        .addComponent(cbBloqueado2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(233, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("IMPOSTOS", jPanel3);

        jPanel5.setBackground(new java.awt.Color(248, 248, 248));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1185, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("OUTROS", jPanel5);

        jPanel1.setBackground(new java.awt.Color(94, 110, 110));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        lblTitulo.setBackground(new java.awt.Color(94, 110, 110));
        lblTitulo.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("Cadastro de Produto - Incluir");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        jPanel4.setPreferredSize(new java.awt.Dimension(0, 3));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Atualiza????o de Produtos");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        btnConfirmar.setBackground(new java.awt.Color(0, 153, 153));
        btnConfirmar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/anydo_104098.png"))); // NOI18N
        btnConfirmar.setText("Finalizar");
        btnConfirmar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnConfirmar.setContentAreaFilled(false);
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

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Metrosecuritydenied_metr_11317.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setAlignmentY(0.8F);
        btnCancelar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        btnCancelar.setContentAreaFilled(false);
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

        javax.swing.GroupLayout painelCorpoLayout = new javax.swing.GroupLayout(painelCorpo);
        painelCorpo.setLayout(painelCorpoLayout);
        painelCorpoLayout.setHorizontalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 1210, Short.MAX_VALUE)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCorpoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        painelCorpoLayout.setVerticalGroup(
            painelCorpoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCorpoLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addGap(10, 10, 10)
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
        setLocationRelativeTo(null);
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
        buscaSubCategoria();
        geraCodigoProduto();
    }//GEN-LAST:event_txtCodSubFocusLost

    private void txtCodSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodSubActionPerformed
        buscaSubCategoria();
        geraCodigoProduto();
        txtCodMarca.requestFocus();
    }//GEN-LAST:event_txtCodSubActionPerformed

    private void txtCodSubKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodSubKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {
            categoriaVinculada = txtCodCat.getText();
            ConsultaSubCategoria sub = new ConsultaSubCategoria(new javax.swing.JFrame(), true);

            sub.setPegaCodCat(this.getCategoriaVinculada());
            sub.setVisible(true);
            String codigo = sub.getCodigo();
            txtCodSub.setText(codigo);
        } else {

        }
    }//GEN-LAST:event_txtCodSubKeyPressed

    private void txtCodCatFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCatFocusLost
        buscaCategoria();
    }//GEN-LAST:event_txtCodCatFocusLost

    private void txtCodCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCatActionPerformed
        buscaCategoria();
        txtCodSub.requestFocus();
    }//GEN-LAST:event_txtCodCatActionPerformed

    private void txtCodCatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodCatKeyPressed
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaCategoria categoria = new ConsultaCategoria(new javax.swing.JFrame(), true);
            categoria.setVisible(true);
            String codigo = categoria.getCodigo().replace("  ", "");
            txtCodCat.setText(codigo);
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
        int tecla = 114;
        int tecla2 = evt.getKeyCode();
        if (tecla == tecla2) {

            ConsultaMarca marca = new ConsultaMarca(new javax.swing.JFrame(), true);
            marca.setVisible(true);
            String codigo = marca.getCodigo();
            txtCodMarca.setText(codigo);
        } else {

        }
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
                produtoDuplicado prod = new produtoDuplicado(new javax.swing.JFrame(), true);

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

    private void txtPrecoVendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPrecoVendaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaFocusGained

    private void txtPrecoVendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPrecoVendaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaMouseClicked

    private void txtPrecoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecoVendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoVendaActionPerformed

    private void txtMargemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMargemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMargemFocusGained

    private void txtMargemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMargemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMargemActionPerformed

    private void txtEstoqueInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstoqueInicialFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicialFocusGained

    private void txtEstoqueInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstoqueInicialMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicialMouseClicked

    private void txtEstoqueInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstoqueInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicialActionPerformed

    private void txtEstoqueInicial1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstoqueInicial1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicial1FocusGained

    private void txtEstoqueInicial1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEstoqueInicial1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicial1MouseClicked

    private void txtEstoqueInicial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstoqueInicial1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstoqueInicial1ActionPerformed

    private void jTabbedPane1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTabbedPane1FocusGained
        txtPrecoVenda.requestFocus();
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

        if (txtCodBarra.getText().equals("") && lblTitulo.getText().equals("Cadastro de Produto - Incluir")) {
            campoObrigatorio erro = new campoObrigatorio(new javax.swing.JFrame(), true);
            erro.setVisible(true);
            txtCodBarra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));
            txtCodBarra.requestFocus();

        } else {
            switch (lblTitulo.getText()) {
                case "Cadastro de Produto - Incluir":

                    pegaCodBarra = "X";
                    verificaCadastroDuplicado();

                    if (pegaCodBarra.equals(txtCodBarra.getText())) {
                        produtoDuplicado prod = new produtoDuplicado(new javax.swing.JFrame(), true);

                        prod.setVisible(true);
                    } else {
                        salvar();
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
      
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(JD_CAD_PROD_VIA_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JD_CAD_PROD_VIA_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JD_CAD_PROD_VIA_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JD_CAD_PROD_VIA_PDV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JD_CAD_PROD_VIA_PDV dialog = new JD_CAD_PROD_VIA_PDV(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel Cadastrais;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JComboBox<String> cbBloqueado;
    private javax.swing.JComboBox<String> cbBloqueado1;
    private javax.swing.JComboBox<String> cbBloqueado2;
    private javax.swing.JComboBox<String> cbBloqueado3;
    private javax.swing.JComboBox<String> cbBloqueado4;
    private javax.swing.JComboBox<String> cbNaturezaProduto;
    private javax.swing.JComboBox<String> cbUnidade;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBloquear;
    private javax.swing.JLabel lblBloquear1;
    private javax.swing.JLabel lblBloquear2;
    private javax.swing.JLabel lblBloquear3;
    private javax.swing.JLabel lblBloquear4;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel painelCorpo;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JTextField txtCodCat;
    private javax.swing.JTextField txtCodFab;
    private javax.swing.JTextField txtCodFab1;
    private javax.swing.JTextField txtCodMarca;
    private javax.swing.JTextField txtCodSub;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JFormattedTextField txtEstoqueInicial;
    private javax.swing.JFormattedTextField txtEstoqueInicial1;
    private javax.swing.JTextField txtFabricante;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JFormattedTextField txtMargem;
    private javax.swing.JTextField txtNome;
    private javax.swing.JFormattedTextField txtPrecoVenda;
    private javax.swing.JTextField txtSubCat;
    // End of variables declaration//GEN-END:variables
}
