
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Edicarlos
 */
public class Conexao {

    public Statement stm; // Responsavel por preparar e realizar pesquisas no banco de dados;
    public ResultSet rs; // Responsavel por armazenar o resultado de um pesquisa passada para o statement;
    private static String driver;
    private static String url;
    private static String usuario;
    private static String senha;
    private static String dataBase;
    private static String servidor;

    public static String getServidor() {
        return servidor;
    }

    public static void setServidor(String servidor) {
        Conexao.servidor = servidor;
    }

    public static String getDataBase() {
        return dataBase;
    }

    public static void setDataBase(String dataBase) {
        Conexao.dataBase = dataBase;
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        Conexao.driver = driver;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Conexao.url = url;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Conexao.usuario = usuario;
    }

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        Conexao.senha = senha;
    }

    public Connection conexao; // Responsavel por realizar a conexão com o banco de dados;

    public static Connection getConexao() {

        {
            try {
                // Connection conn = DriverManager.getConnection("jdbc:mysql://novaagricola.mysql.dbaas.com.br/novaagricola?serverTimezone=UTC", "novaagricola", "C@rlim05");
                //  Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/novaagricola?characterEncoding=utf8", "root", "");

                Connection conn = DriverManager.getConnection(driver + url + dataBase, usuario, senha);
                return conn;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar se conectar com banco de dados " + ex, "AGUI TECH", 2);

                return null;
            }

        }

    }

    public void closeConection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void closeConection(Connection conn, PreparedStatement smt) {
        if (smt != null) {
            try {
                smt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConection(conn);
    }

    public void closeConection(Connection conn, PreparedStatement smt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConection(conn, smt);

    }

    public void conexaAccess() {

    }
}
