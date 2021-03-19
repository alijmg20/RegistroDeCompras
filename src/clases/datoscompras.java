package clases;

import java.awt.HeadlessException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import registroCompras.MenuPrincipal;

public class datoscompras {

    public datoscompras() {
    }

    public void Clientescb(JComboBox cb) {
        try {
            String SQL = "SELECT * FROM clientes";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            cb.addItem("Selecciona");
            while (resultado.next()) {
                cb.addItem(resultado.getString("dni"));
            }

        } catch (Exception ex) {

        }
    }

    public void agregarcompras(Date fecha, char tipoMoneda, String descripcionc, String dnicliente , char tipopago) {

        try {

            String SQL = "INSERT INTO compras (nrocompra,fechaemision,tipomoneda,tipopago,descripcionc,dniclientes) VALUES (NULL,?,?,?,?,?)";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setDate(1, fecha);
            consulta.setString(2, String.valueOf(tipoMoneda));
            consulta.setString(3, String.valueOf(tipopago));
            consulta.setString(4, descripcionc);
            consulta.setString(5, dnicliente);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "compra agregada!", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro de la compra: " + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void modificarcompras(int compra, Date fecha, char tipoMoneda, String descripcionc, String dnicliente, char tipopago) {

        try {

            String SQL = "UPDATE compras SET fechaemision=?,tipomoneda=?,tipopago=?,descripcionc=?,dniclientes=? WHERE nrocompra=?";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setDate(1, fecha);
            consulta.setString(2, String.valueOf(tipoMoneda));
            consulta.setString(3, String.valueOf(tipopago));
            consulta.setString(4, descripcionc);
            consulta.setString(5, dnicliente);
            consulta.setInt(6, compra);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "compra modificada!", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro de la factura: " + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void eliminarCompras(int compra) {

        String SQL = "DELETE FROM compras where nrocompra=?";

        try {
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setInt(1, compra);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "compra eliminada ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al eliminar factura: " + e.getMessage(), "fracaso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel mostrarcompras() {
        String[] titulos = {"nro compra", "fecha emision", "tipo moneda", "descripcion", "dni cliente", " tipo pago "};

        String[] registros = new String[6];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM compras order by nrocompra  ";

        try {
            Statement consulta = MenuPrincipal.con.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("nrocompra");
                String FormatoFecha1 = resultado.getString("fechaemision");
                String fecha1 = FormatoFecha1.replace("-", "/");
                registros[1] = fecha1;

                registros[2] = resultado.getString("tipomoneda");
                registros[3] = resultado.getString("descripcionc");
                registros[4] = resultado.getString("dniclientes");
                registros[5] = resultado.getString("tipopago");
                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos " + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

}
