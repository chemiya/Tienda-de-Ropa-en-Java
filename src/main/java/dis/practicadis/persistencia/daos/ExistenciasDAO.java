/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.persistencia.accesodb.ConnectionDB;
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
import javax.json.JsonWriter;

public class ExistenciasDAO {

    public static final String SELECT_EXISTENCIAS = "SELECT * FROM EXISTENCIASENTIENDAS";
    public static final String SELECT_EXISTENCIAS_FROM_TIENDA = "SELECT * FROM EXISTENCIASENTIENDAS WHERE ENTIENDA=?";
    public static final String SELECT_EXISTENCIAS_FROM_PRODUCTO = "SELECT * FROM EXISTENCIASENTIENDAS WHERE PRODUCTO=?";
    public static final String SELECT_EXISTENCIAS_FROM_BOTH = "SELECT * FROM EXISTENCIASENTIENDAS WHERE PRODUCTO=? AND ENTIENDA=?";
    public static final String UPDATE_EXISTENCIAS_CANTIDAD = "UPDATE EXISTENCIASENTIENDAS SET CANTIDAD = ? WHERE PRODUCTO=? AND ENTIENDA=?";
    public static final String INSERT_EXISTENCIAS = "INSERT INTO EXISTENCIASENTIENDAS (cantidad, producto, entienda) VALUES (?, ?, ?)";

    public static boolean isProductInStock(int id) {
        boolean resultado = false;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_EXISTENCIAS_FROM_PRODUCTO);) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                while (result.next()) {
                    if (result.getInt("CANTIDAD") > 0) {
                        resultado = true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static String getExistenciasFromProducto(int id) {
        StringBuilder existencias = new StringBuilder("[");
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_EXISTENCIAS_FROM_PRODUCTO)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                int cantidad;
                String producto;
                String tienda;
                while (result.next()) {
                    if (result.getInt("CANTIDAD") > 0) {
                        cantidad = result.getInt("CANTIDAD");
                        producto = ProductoDAO.read(result.getInt("PRODUCTO"));
                        tienda = TiendaDAO.read(result.getInt("ENTIENDA"));
                        existencias.append(existenciaAJSON(cantidad, producto, tienda));
                        existencias.append(",");
                    }
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (existencias.charAt(existencias.length() - 1) == ',') {
            existencias.deleteCharAt(existencias.length() - 1);
        }
        existencias.append("]");
        String existenciaJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("existencias", existencias.toString())
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            existenciaJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existenciaJSON;
    }

    public static String getAllExistencias() {
        StringBuilder existencias = new StringBuilder("[");
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_EXISTENCIAS_FROM_PRODUCTO)) {
                ResultSet result = s.executeQuery();
                int cantidad;
                String producto;
                String tienda;
                while (result.next()) {
                    cantidad = result.getInt("CANTIDAD");
                    producto = ProductoDAO.read(result.getInt("PRODUCTO"));
                    tienda = TiendaDAO.read(result.getInt("ENTIENDA"));
                    existencias.append(existenciaAJSON(cantidad, producto, tienda));
                    existencias.append(",");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (existencias.charAt(existencias.length() - 1) == ',') {
            existencias.deleteCharAt(existencias.length() - 1);
        }
        existencias.append("]");
        String existenciaJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("existencias", existencias.toString())
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            existenciaJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existenciaJSON;
    }

    public static String read(int id) {
        StringBuilder existencias = new StringBuilder("[");
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_EXISTENCIAS_FROM_TIENDA)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                int cantidad;
                String producto;
                String tienda;
                while (result.next()) {
                    cantidad = result.getInt("CANTIDAD");
                    producto = ProductoDAO.read(result.getInt("PRODUCTO"));
                    tienda = TiendaDAO.read(result.getInt("ENTIENDA"));
                    existencias.append(existenciaAJSON(cantidad, producto, tienda));
                    existencias.append(",");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (existencias.charAt(existencias.length() - 1) == ',') {
            existencias.deleteCharAt(existencias.length() - 1);
        }
        existencias.append("]");
        String existenciaJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("existencias", existencias.toString())
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            existenciaJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existenciaJSON;
    }

    private static String existenciaAJSON(int cantidad, String producto, String tienda) {
        String entryJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("cantidad", cantidad)
                .add("producto", producto)
                .add("tienda", tienda)
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            entryJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entryJSON;
    }

    public static boolean hasExistenciasOnTienda(int tienda, int producto) {
        boolean resultado = false;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_EXISTENCIAS_FROM_BOTH);) {
                s.setInt(1, producto);
                s.setInt(2, tienda);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static int getCantidadFromBoth(int tienda, int producto) {
        int cantidad = -1;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_EXISTENCIAS_FROM_BOTH);) {
                s.setInt(1, producto);
                s.setInt(2, tienda);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    cantidad = result.getInt("CANTIDAD");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidad;
    }

    public static void updateCantidad(int tienda, int producto, int cantidad) {
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(UPDATE_EXISTENCIAS_CANTIDAD);) {
                s.setInt(1, cantidad);
                s.setInt(2, producto);
                s.setInt(3, tienda);
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFromBoth(int tienda, int producto, int cantidad) {
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_EXISTENCIAS);) {
                s.setInt(1, cantidad);
                s.setInt(2, producto);
                s.setInt(3, tienda);
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ExistenciasDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void addExistencias(int tienda, int producto, int cantidad){
        if(hasExistenciasOnTienda(tienda, producto)){
            updateCantidad(tienda, producto, getCantidadFromBoth(tienda, producto)+cantidad);
        } else{
            writeFromBoth(tienda, producto, cantidad);
        }
    }

}
