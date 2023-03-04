/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ARRAY_STATIC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author edica
 */
public class ArrayProdutos {

   private ArrayList<String> produtos = new ArrayList();

    public ArrayList<String> getProdutos() {
        return produtos;
    }

  

 

    public void carregaArray() {
        Conexao conn = new Conexao();
   

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from tb_produtos limit 100");
            rs = pst.executeQuery();

            while (rs.next()) {

                produtos.add(rs.getString("codigo")); //Codigo
                produtos.add(rs.getString("nome")); //Nome do Produto
                produtos.add(rs.getString("und")); //Unidade de Medida
                produtos.add(rs.getString("categoria")); //
                produtos.add(rs.getString("subCategoria"));
                produtos.add(rs.getString("precoVenda"));
                produtos.add(rs.getString("codBarra"));
                produtos.add(rs.getString("bloqueado"));
                
              //  JOptionPane.showMessageDialog(null, produtos.get(1));
           
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

    }

}
