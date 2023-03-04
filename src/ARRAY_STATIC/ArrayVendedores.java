/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ARRAY_STATIC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import util.Conexao;

/**
 *
 * @author edica
 */
public class ArrayVendedores {
    
    
       private ArrayList<String> vendedores = new ArrayList();

    public ArrayList<String> getVendeoress() {
        return vendedores;
    }

    public void carregaArrayVendedores() {
        Conexao conn = new Conexao();
   

        try {

            PreparedStatement pst;
            ResultSet rs;
            int linha = 1;
            pst = conn.getConexao().prepareStatement("select * from vendedores limit 100");
            rs = pst.executeQuery();

            while (rs.next()) {

                vendedores.add(rs.getString("vendedor")); //Vendedor
                vendedores.add(rs.getString("comissao")); //Comissao
            
                
              //  JOptionPane.showMessageDialog(null, produtos.get(1));
           
            }
            conn.getConexao().close();
        } catch (Exception e) {
        }

    }

    
}
