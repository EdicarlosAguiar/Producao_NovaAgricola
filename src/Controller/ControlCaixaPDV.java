/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import FormNotificacao.Confirmacao;
import Model.ModelOperacaoCaixaPDV;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author Edicarlos
 */
public class ControlCaixaPDV {

    Conexao conn = new Conexao();
    ModelOperacaoCaixaPDV mod = new ModelOperacaoCaixaPDV();

    public void mudaStatus() {
        try {
            Conexao conn = new Conexao();

            PreparedStatement pst = conn.getConexao().prepareStatement("UPDATE caixa_pdv SET status=? where codigo =?");

            pst.setString(1, mod.getStatus());
            pst.setInt(2, mod.getCodigo());
            pst.executeUpdate();

            pst.close();

        } catch (Exception e) {

        }

    }

    public void abrirCaixa(ModelOperacaoCaixaPDV mod) {

        try {

            PreparedStatement pst = conn.getConexao().prepareStatement("insert into caixa_pdv(codigo,data,operador,saldo_inicial,"
                    + "status,operacao,aprovador)VALUES(?,?,?,?,?,?,?)");

            pst.setInt(1, mod.getCodigo());//Codigo
            pst.setString(2, mod.getData());//Data
            pst.setString(3, mod.getOperador());//Operador
            pst.setDouble(4, mod.getSaldo_inical());//Saldo Inicial
            pst.setString(5, mod.getStatus());//Sttus
            pst.setString(6, mod.getOperacao());//Operação
            pst.setString(7, mod.getAprovador());//Aprovador

            pst.execute();
            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Abrir Caixa" + e);
        }
    }

    public void excluirEntradaVenda() {

        try {

            PreparedStatement pst = conn.getConexao().prepareStatement("delete from caixa_pdv where operacao =? and codigo=?");
            pst.setString(1, "VEDA");
            pst.setInt(2, mod.getCodigo());
            pst.executeUpdate();

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao Abrir Caixa" + e);
        }
    }

    public void fecharCaixa(ModelOperacaoCaixaPDV mod) {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into caixa_pdv (data,operador, status,operacao,"
                    + "saldo_final,codigo,aprovador)VALUES(?,?,?,?,?,?,?)");

            pst.setInt(6, mod.getCodigo());//Codigo
            pst.setString(1, mod.getData());//Data
            pst.setString(2, mod.getOperador());//Operador
            pst.setString(3, mod.getStatus());//Status
            pst.setString(4, mod.getOperacao());//Operacao
            pst.setDouble(5, mod.getSaldo_final());//Saldo Final
            pst.setString(7, mod.getAprovador());//Saldo Final

            pst.execute();

            mudaStatus();

            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);

            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar realizar fechamento!" + "\n"
                    + e, "Erro ao processar fechamento", 1);
        }
    }

    public void sangriaRetirada(ModelOperacaoCaixaPDV mod) {
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into caixa_pdv (data,operador, status,operacao,"
                    + "saidas,codigo,aprovador,conta)VALUES(?,?,?,?,?,?,?,?)");
            pst.setInt(6, mod.getCodigo());//Codigo
            pst.setString(1, mod.getData());//Data
            pst.setString(2, mod.getOperador());//Operador
            pst.setString(3, mod.getStatus());//Status
            pst.setString(4, mod.getOperacao());//Operacao
            pst.setDouble(5, mod.getSaida());//Saida
            pst.setString(7, mod.getAprovador());//Aprovaddor
            pst.setString(8, mod.getConta());//Conta
            pst.execute();
            mudaStatus();
            Confirmacao conf = new Confirmacao(new javax.swing.JFrame(), true);
            conf.setVisible(true);
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar realizar fechamento!" + "\n"
                    + e, "Erro ao processar fechamento", 1);
        }
    }
}
