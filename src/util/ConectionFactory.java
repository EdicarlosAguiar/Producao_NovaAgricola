/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Desktop
 */
public class ConectionFactory {

    public Connection getConnection() {

        try {
            return DriverManager.getConnection(
                    "jdbc:sqlserver://jdbc:sqlserver://localhost:1433;databaseName=AGUITECH;user=sa;password=carlim05"
      
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return null;

    }
}
