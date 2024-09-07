/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.persistencia.accesodb.ConnectionDB;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class PedidoDAO {

    public static final String SELECT_PEDIDO_FROM_ID = "SELECT * FROM PEDIDOSDESUMINISTRODEPRODUCTO WHERE ID=?";
    public static final String UPDATE_PEDIDO_ESTADO = "UPDATE PEDIDOSDESUMINISTRODEPRODUCTO SET ESTADO = ? WHERE ID = ?";
    
    public static boolean doesPedidoExist(int id) {
        boolean resultado = false;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_PEDIDO_FROM_ID);) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static String read(int id) {
        String pedidoJsonString = "";
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            int cantidad = 0;
            LocalDateTime fecha = null;
            int estado = 0;
            int idPreparacion = 0;
            int idSolicitante = 0;
            int idProducto = 0;
            try (
                     PreparedStatement s = connection.getStatement(SELECT_PEDIDO_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    cantidad = result.getInt("CANTIDAD");
                    fecha = result.getTimestamp("FECHA").toLocalDateTime();
                    estado = result.getInt("ESTADO");
                    if(result.getObject("PREPARACION") == null){
                        idPreparacion = -1;
                    }
                    else{
                        idPreparacion = result.getInt("PREPARACION");
                    }
                    idSolicitante = result.getInt("SOLICITADOEN");
                    idProducto = result.getInt("PRODUCTO");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String preparacion = null;
            if(idPreparacion > 0){
                preparacion = PreparacionDAO.read(idPreparacion);
            }    
            String solicitante = TiendaDAO.read(idSolicitante);
            String producto = ProductoDAO.read(idProducto);
            pedidoJsonString = obtainPedidoJsonString(cantidad, fecha, estado, preparacion, solicitante, producto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidoJsonString;
    }

    private static String obtainPedidoJsonString(int cantidad, LocalDateTime fecha, int estado, String preparacion, String solicitante, String producto) {
        String productoJSON = "";
        JsonObjectBuilder builder;
        if (preparacion != null) {
            builder = Json.createObjectBuilder()
                .add("cantidad", cantidad)
                .add("fecha", fecha.toString())
                .add("estado", estado)
                .add("preparacion", preparacion)
                .add("solicitante", solicitante)
                .add("pedido", producto);
        } else {
            builder = Json.createObjectBuilder()
                .add("cantidad", cantidad)
                .add("fecha", fecha.toString())
                .add("estado", estado)
                .add("preparacion", JsonObject.NULL)
                .add("solicitante", solicitante)
                .add("pedido", producto);

        }
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            productoJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoJSON;
    }
    
    
    public static void updateEstado(int id, int est){
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(UPDATE_PEDIDO_ESTADO);) {
                s.setInt(1, est);
                s.setInt(2, id);
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
