/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class NovaConexao {

    public Statement stm; // Responsavel por preparar e realizar pesquisas no banco de dados;
    public ResultSet rs; // Responsavel por armazenar o resultado de um pesquisa passada para o statement;
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String caminho = "jdbc:sqlserver://localhost:1433;databaseName=AGUITECH"; // O "control" representa a minha database 
    private String usuario = "edicarlos";
    private String senha = "carlim05";
    public Connection conexao; // Responsavel por realizar a conexão com o banco de dados;

    public static Connection getConexao() {

        {
            try {
                Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;database=AGUITECH;user=sa;password=carlim05");
                return conn;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar se conectar com banco de dados " + ex, "AGUI TECH", 2);
                return null;
            }

        }

    }

}
