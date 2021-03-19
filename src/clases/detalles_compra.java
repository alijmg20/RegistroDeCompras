package clases;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import registroCompras.MenuPrincipal;

public class detalles_compra {

    public detalles_compra() {
    }

    public DefaultListModel listarproductos() {

        DefaultListModel lista = new DefaultListModel();

        try {

            String SQL = "SELECT nombrep FROM productos ";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                lista.addElement(resultado.getString("nombrep"));
            }

        } catch (Exception e) {

        }

        return lista;
    }

    public DefaultListModel agregarCarrito(JList j1, String nombrep) {

        DefaultListModel lista = (DefaultListModel) j1.getModel();
        Object[] arreglo = lista.toArray();
        boolean agregado = true;
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals(nombrep)) {
                agregado = false;
            }

        }

        if (agregado) {
            lista.addElement(nombrep);
        }
        return lista;
    }

    public DefaultListModel EliminarCarrito(JList j1, String nombrep) {

        DefaultListModel lista = (DefaultListModel) j1.getModel();
        Object[] arreglo = lista.toArray();
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i].equals(nombrep)) {
                lista.removeElement(nombrep);
                return lista;
            }

        }
        return lista;

    }

    public String obtenerCodigop(String nombrep) {

        String SQL = "SELECT idproductos FROM productos WHERE nombrep = '" + nombrep + "'";
        try {
            String idproducto = "";
            Statement consultaCodigo = MenuPrincipal.con.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return idproducto = resultado.getString("idproductos");
        } catch (Exception e) {

        }
        return null;

    }

    public int obtenernrocompra() {

        String SQL = "SELECT nrocompra FROM compras ORDER BY nrocompra DESC LIMIT 1   ";
        try {
            int nrocompra = 0;
            Statement consultaCodigo = MenuPrincipal.con.createStatement();
            ResultSet resultado = consultaCodigo.executeQuery(SQL);
            resultado.next();
            return nrocompra = resultado.getInt("nrocompra");
        } catch (Exception e) {

        }
        return -1;

    }

    public void agregarDetallesCompra(JList j1) {

        DefaultListModel lista = (DefaultListModel) j1.getModel();
        Object[] arreglo = lista.toArray();
        int nrocompra = obtenernrocompra();
        String SQL = "INSERT INTO detalles_compras (nrocompra,idproductos) VALUES (?,?) ";

        for (int i = 0; i < arreglo.length; i++) {
            try {
                String codigop = obtenerCodigop(arreglo[i].toString());

                PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
                consulta.setInt(1, nrocompra);
                consulta.setString(2, codigop);
                consulta.execute();
            } catch (Exception e) {

            }
        }
    }

    public void eliminarDetallesCompra(int nrocompra) {

        String SQL = "DELETE FROM detalles_compras where nrocompra=?";

        try {
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setInt(1, nrocompra);
            consulta.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al eliminar un detalle de compra: " + e.getMessage(), "fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }

    public String mostrarDetallesCompra(int nrocompra) {
        String detalles = "\t DETALLES DE LA COMPRA " + nrocompra + "\n";

        String SQL = "SELECT nombrep,preciop,color FROM productos "
                + "INNER JOIN detalles_compras ON productos.idproductos=detalles_compras.idproductos "
                + "AND detalles_compras.nrocompra= " + nrocompra;

        try {
            Statement consulta = MenuPrincipal.con.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                detalles += "Nombre producto: ";
                detalles += resultado.getString("nombrep");
                detalles += "\n";
                detalles += "precio producto: ";
                detalles += resultado.getString("preciop");
                detalles += " $\n";
                detalles += "color producto: ";
                detalles += resultado.getString("color");
                detalles += "\n";
                detalles += "---------------------------------------\n";
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos " + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }

        return detalles;
    }

}
