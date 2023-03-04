/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import FormNotificacao.Confirmacao;
import FormNotificacao.Exeception;

import Model.ModelFornecedor;
import Model.ModelProduto;
import Model.ModelUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import util.Conexao;
import util.utilitario;

public class ControlProduto {

    Conexao conn = new Conexao();

    public void inserirProduto(Model.ModelProduto mod) throws Exception {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into tb_produtos(codigo,nome,und,codCat,categoria,codSub,"
                    + "subCategoria,bloqueado,data,codMarca, marca,codFab,fabricante,precoVenda,codBarra)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setInt(1, mod.getCodigo());
            pst.setString(2, mod.getNome());
            pst.setString(3, mod.getUnd());
            pst.setString(4, mod.getCodCat());
            pst.setString(5, mod.getCategoria());
            pst.setString(6, mod.getCodSub());
            pst.setString(7, mod.getSubCategoria());
            pst.setString(8, mod.getBloqueado());
            pst.setString(9, mod.getData());
            pst.setInt(10, mod.getCodMarca());
            pst.setString(11, mod.getMarca());
            pst.setInt(12, mod.getCodFab());
            pst.setString(13, mod.getFabricante());
            pst.setString(14, mod.getPrecoVenda());
            pst.setString(15, mod.getCodBarra());
            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);

            pst.close();
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, "Erro ao salvar produto" + e);
        }

    }

    public void inserirCategoria(Model.ModelProduto mod) throws Exception {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into categoria(codigo,categoria)VALUES(?,?)");

            pst.setString(1, mod.getCodCat());
            pst.setString(2, mod.getCategoria());
            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);

            pst.close();
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, "Erro ao salvar produto" + e);
        }

    }

    public void inserirSubCategoria(Model.ModelProduto mod) throws Exception {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into subcategoria(codigo,"
                    + "subCategoria,codCat,categoria)VALUES(?,?,?,?)");

            pst.setString(1, mod.getCodSub());
            pst.setString(2, mod.getSubCategoria());
            pst.setString(3, mod.getCodCat());
            pst.setString(4, mod.getCategoria());
            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);

            pst.close();
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, "Erro ao salvar produto" + e);
        }

    }

    public void alterarProduto(ModelProduto mod) {

        try {

            Conexao conn = new Conexao();

            PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE tb_produtos SET nome=?, "
                    + "codBarra=?,precoVenda=?,estoque_inicial=?,codMarca=?,codFab=?,bloqueado=? where codigo =?");

            pst.setString(1, mod.getNome());//Altera nome do Produto
            pst.setString(2, mod.getCodBarra()); //Altera Codido de Barras
            pst.setString(3, mod.getPrecoVenda()); //Altera Preco de Venda
            pst.setString(4, mod.getEstoqueInicio()); //Altera Estoque Inicial
            pst.setInt(5, 1); //Altera Cod da Marca
            pst.setString(6, ""); //Altera Nome da Marca
            pst.setString(7, mod.getSatus()); //Altera Status
            pst.setInt(8, mod.getCodigo());

            pst.executeUpdate();

            Confirmacao conf = new Confirmacao(null, true);
            conf.textoProdutoAlterado();
            conf.setVisible(true);

            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o produto! " + e, "Alteração de Produto", JOptionPane.ERROR_MESSAGE);
        }

    }
}
