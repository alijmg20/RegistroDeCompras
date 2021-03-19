package clases;

import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import registroCompras.MenuPrincipal;

public class datosclientes {

    public datosclientes() {
    }

    public void agregarCliente(String nombre, String dni, String direccion, String telefono, Date fechanac) {

        try {

            String SQL = "INSERT INTO clientes (dni,nombrec,direccion,telefono,fechanac) VALUES (?,?,?,?,?)";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setString(1, dni);
            consulta.setString(2, nombre);
            consulta.setString(3, direccion);
            consulta.setString(4, telefono);
            consulta.setDate(5, fechanac);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Cliente registrado!", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro del cliente" + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void moficarCliente(String nombre, String dni, String direccion, String telefono, Date fechanac) {

        try {
            String SQL = "UPDATE clientes SET nombrec=? , direccion=? , telefono=? , fechanac=? WHERE dni=? ";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setString(1, nombre);
            consulta.setString(2, direccion);
            consulta.setString(3, telefono);
            consulta.setDate(4, fechanac);
            consulta.setString(5, dni);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Cliente actualizado!", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro del cliente" + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarCliente(String dni) {

        String SQL = "DELETE FROM clientes where dni=?";

        try {
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setString(1, dni);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "cliente eliminado ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al eliminar cliente: " + e.getMessage(), "fracaso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel mostrarClientes() {
        String[] titulos = {"DNI", "Nombre", "Direccion", "Telefono", "Fecha Nacimiento"};

        String[] registros = new String[5];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM clientes order by dni";

        try {
            Statement consulta = MenuPrincipal.con.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("dni");
                registros[1] = resultado.getString("nombrec");
                registros[2] = resultado.getString("direccion");
                registros[3] = resultado.getString("telefono");
                String FormatoFecha1 = resultado.getString("fechanac");
                String fecha1 = FormatoFecha1.replace("-", "/");
                registros[4] = fecha1;

                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos " + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

}
