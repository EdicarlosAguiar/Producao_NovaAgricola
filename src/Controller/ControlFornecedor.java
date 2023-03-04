/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import FormNotificacao.Confirmacao;
import Model.ModelFornecedor;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author Edicarlos
 */
public class ControlFornecedor {

    Conexao conn = new Conexao();

    public void inserirFornecedor(ModelFornecedor mod) throws Exception {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into fornecedor(razaoSocial,nomeFantasia,"
                    + "CNPJ_CPF )"
                    + "VALUES(?,?,?)");

          //  pst.setInt(1, mod.getCodigo());
            pst.setString(1, mod.getRazaoSocial());
            pst.setString(2, mod.getFantasia());
            pst.setString(3, mod.getCNPJ());
           /* pst.setString(5, mod.getCEP());
            pst.setString(6, mod.getEndereco());
            pst.setString(7, mod.getBairro());
            pst.setString(8, mod.getCidade());
            pst.setString(9, mod.getEmail());
            pst.setString(10, mod.getTelefone());
            pst.setString(11, mod.getBloqueado());
            pst.setString(12, mod.getBanco());
            pst.setString(13, mod.getConta());
            pst.setString(14, mod.getAgencia());
            pst.setString(15, mod.getEstado());
            pst.setString(16, mod.getCategoria());*/

            pst.execute();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel finalizar o cadastro "+e, "Mensagem", 2);
        }

    }

    
}
