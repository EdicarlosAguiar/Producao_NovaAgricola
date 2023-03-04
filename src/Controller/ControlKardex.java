/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import FormNotificacao.Confirmacao;
import Formularios.BASE_COMPRA;
import Model.ModelKardex;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author Edicarlos
 */
public class ControlKardex {

    Conexao conn = new Conexao();

    public void salvarCompra(ModelKardex mod) throws Exception {


        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into kardex(data,documento,"
                    + "codProduto,produto,unidade,entrada,entradaValor,saida,saidaValor)"
                    + "VALUES(?,?,?,?,?,?,?,?,?)");

            pst.setString(1, mod.getData());
            pst.setString(2, mod.getDocumento());
            pst.setString(3, mod.getCodProduto());
            pst.setString(4, mod.getProduto());
            pst.setString(5, mod.getUnidade());
            pst.setDouble(6, Double.parseDouble(mod.getEntradaQuantidade()));
            pst.setDouble(7, Double.parseDouble(mod.getEntradaValor()));
            pst.setDouble(8, 0);
            pst.setDouble(9, 0);

            pst.execute();

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar produto" + e);
        }

    }
}
 
