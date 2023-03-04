/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import FormNotificacao.AcessoNegado;
import FormNotificacao.Confirmacao;
import Formularios.BASE_COMPRA;


import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author Edicarlos
 */
public class ControlCompra {

    Conexao conn = new Conexao();

    public void salvarCompra(Model.ModelCompra mod) throws Exception {
   
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into compra(codigo,data,codForn,nomeForn,condPagamento,"
                    + "formPagamento,"
                    + "codProduto,nomeProduto,unidade,quantidade,preco,total,desconto,valorFinal,dataDigitacao,usuario,documento,"
                    + "valorFinalDouble,qtdeFloat,precoFloat,totalFloat,descontoFloat,codCat,cat,codSub,sub)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setInt(1, mod.getCodCompra());
            pst.setString(2, mod.getData());
            pst.setInt(3, mod.getCodForn());
            pst.setString(4, mod.getFornecedor());
            pst.setString(5, mod.getCondPagamento());
            pst.setString(6, mod.getFormPagamento());
            pst.setString(7, mod.getCodProduto());
            pst.setString(8, mod.getProduto());
            pst.setString(9, mod.getUnidade());
            pst.setString(10, mod.getQuantidade());
            pst.setString(11, mod.getPreço());
            pst.setString(12, mod.getTotal());
            pst.setString(13, String.valueOf(mod.getDesconto()));
            pst.setString(14, mod.getValorFinal());
            pst.setString(15, mod.getDataDigitacao());
            pst.setString(16, mod.getUsario());
            pst.setString(17, mod.getDocumento());
            pst.setFloat(18, mod.getValorFinalDouble());
            pst.setFloat(19, mod.getQtdeFloat());
            pst.setFloat(20, mod.getPrecoFloat());
            pst.setFloat(21, mod.getTotalFloat());
            pst.setFloat(22, mod.getDescontoFloat());
            pst.setString(23, mod.getCodCat());
            pst.setString(24, mod.getCat());
            pst.setString(25, mod.getCodSub());
            pst.setString(26, mod.getSub());
            pst.execute();
            pst.close();
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar compra " + e);
        }

    }

}
