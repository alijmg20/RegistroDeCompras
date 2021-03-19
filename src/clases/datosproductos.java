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

public class datosproductos {

    public datosproductos() {
    }
    
    public void agregarProductos(String codigo, String nombre, String descripcion, float preciop,String talla,int stock,String color) {

        try {

            String SQL = "INSERT INTO productos (idproductos,nombrep,descripcion,preciop,talla,stock,color) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setString(1, codigo);
            consulta.setString(2, nombre);
            consulta.setString(3, descripcion);
            consulta.setFloat(4, preciop);
            consulta.setString(5, talla);
            consulta.setInt(6, stock);
            consulta.setString(7, color);
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Producto registrado!", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro del Producto" + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void modificarProductos(String codigo, String nombre, String descripcion, float preciop,String talla,int stock,String color) {

        try {
            String SQL = "UPDATE productos SET nombrep=? , descripcion=? , preciop=? , talla=? , stock=? , color=? WHERE idproductos=? ";
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setString(1, nombre);
            consulta.setString(2, descripcion);
            consulta.setFloat(3, preciop);
            consulta.setString(4, talla);
            consulta.setInt(5, stock);
            consulta.setString(6, color);
            consulta.setString(7, codigo);
            
            consulta.execute();
            JOptionPane.showMessageDialog(null, "Producto actualizado!", "Exito", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Fallo del Registro del Producto" + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarProductos(String idproducto) {

        String SQL = "DELETE FROM productos where idproductos=?";

        try {
            PreparedStatement consulta = MenuPrincipal.con.prepareStatement(SQL);
            consulta.setString(1, idproducto);
            consulta.executeUpdate();
            JOptionPane.showMessageDialog(null, "producto eliminado ", "Accion realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Hubo un error al eliminar cliente: " + e.getMessage(), "fracaso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public DefaultTableModel mostrarProductos() {
        String[] titulos = {"codigo Producto", "Nombre producto", "Descripcion Producto", "Precio "," Talla ", " Stock ", " Color "};

        String[] registros = new String[7];
        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        String SQL = "SELECT * FROM productos order by nombrep";

        try {
            Statement consulta = MenuPrincipal.con.createStatement();
            ResultSet resultado = consulta.executeQuery(SQL);

            while (resultado.next()) {
                registros[0] = resultado.getString("idproductos");
                registros[1] = resultado.getString("nombrep");
                registros[2] = resultado.getString("descripcion");
                registros[3] = resultado.getString("preciop");
                registros[4] = resultado.getString("talla");
                registros[5] = resultado.getString("stock");
                registros[6] = resultado.getString("color");
                tabla.addRow(registros);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos " + e.getMessage(), "Fracaso", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }
    
    
}
