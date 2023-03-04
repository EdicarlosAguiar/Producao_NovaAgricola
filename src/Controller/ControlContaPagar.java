/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Formularios.BASE_COMPRA;
import Model.ModelContaPagar;
import Model.ModelUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import util.Conexao;

/**
 *
 * @author Edicarlos
 */
public class ControlContaPagar {

    Conexao conn = new Conexao();
    ModelUsuario user = new ModelUsuario();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy " + "| " + "HH:mm");
    DateTimeFormatter dataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dataReferencia1 = DateTimeFormatter.ofPattern("yyyyMMdd");

    String dataDigiatacao = dtf.format(LocalDateTime.now());
    String dataReferencia = dataReferencia1.format(LocalDateTime.now());

    public void salvarContaPagar(ModelContaPagar mod) throws Exception {

        try {
            PreparedStatement pst = conn.getConexao().prepareStatement("insert into contas_pagar(TITULO,COD_COMPRA,"
                    + "COD_FORN,NOME_FORN,VENCIMENTO,"
                    + "PARCELA,VALOR,STATUS,DATA_BASE,VENCIMENTO_BASE,USUARIO,NATUREZA_FINAN,numero_titulo,tipo_titulo)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            pst.setInt(1, mod.getTitulo());//TITULO
            pst.setInt(2, mod.getCodCompra());//CODIGO DA COMPRA
            pst.setInt(3, mod.getCodForn());//CODIGO DO FORNECEOR
            pst.setString(4, mod.getFornecedor());//NOME DO FORNECEDOR
            pst.setString(5, mod.getVencimento());//VENCIMENTO
            pst.setString(6, mod.getParcela());//PARCELA
            pst.setFloat(7, mod.getValorTitulo());//VALOR ORIGINAL
            pst.setString(8, mod.getStatus());//STATUS
            pst.setString(9, dataDigiatacao);
            pst.setString(10, mod.getVencimento_base());
            pst.setString(11, user.getUsuarioLogado());
            pst.setString(12, mod.getNatureza());
            pst.setString(13, mod.getTitulo_origem());
            pst.setString(14, mod.getTipo_titulo());
            pst.execute();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de processamento" + e);
        }

    }

}
