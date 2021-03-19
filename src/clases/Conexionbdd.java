package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexionbdd {

    private Connection conectado = null;
    private String jdbc = "jdbc:mysql://localhost/";

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectado = DriverManager.getConnection(jdbc + "pagoscalzados", "root", "");
            System.out.println("conexion realizada exitosamente");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "error de conexion a la base de datos" + e.getMessage());
        }
        return conectado;
    }
    
}
