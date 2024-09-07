/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.exceptions.detailed.JSONException;
import dis.practicadis.persistencia.accesodb.ConnectionDB;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

public class ProductoDAO {

    public static final String SELECT_PRODUCTO = "SELECT * FROM PRODUCTOS WHERE ID=?";
    public static final String SELECT_PRODUCTO_FROM_NOMBRE = "SELECT * FROM PRODUCTOS WHERE NOMBRE=?";

    public static boolean doesProductExist(String nombre){
        boolean resultado = false;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_PRODUCTO_FROM_NOMBRE);) {
                s.setString(1, nombre);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public static int getProductId(String nombre){
        int resultado = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_PRODUCTO_FROM_NOMBRE);) {
                s.setString(1, nombre);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    resultado = result.getInt("ID");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public static String read(int id) {
        String productoJsonString = "";
        String[] datos = new String[2];
        LocalDateTime fecha = null;
        int cat = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_PRODUCTO)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                
                if (result.next()) {
                    datos[0] = result.getString("NOMBRE");
                    datos[1] = result.getString("PRECIO");
                    fecha = result.getTimestamp("FECHADECREACION").toLocalDateTime();
                    cat = result.getInt("CATEGORIA");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String categoria = CategoriaDAO.read(cat);
            productoJsonString = obtainProductoJsonString(datos, categoria, fecha);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productoJsonString;
    }

    
    private static String obtainProductoJsonString(String[] datos, String categoria, LocalDateTime fecha) {
        String productoJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("nombre", datos[0])
                .add("precio", datos[1])
                .add("fechaproducto", fecha.toString())
                .add("categoria", categoria);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            productoJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoJSON;
    }
}
