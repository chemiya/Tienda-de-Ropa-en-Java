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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;


public class TiendaDAO {
    private static final int ordinal = 3;

    public static final String SELECT_TIENDA = "SELECT * FROM TIENDAS";
    public static final String SELECT_TIENDA_FROM_ID = "SELECT * FROM TIENDAS WHERE ID=?";
    
    public static ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_TIENDA)) {
                ResultSet result = s.executeQuery();
                
                while (result.next()) {
                    ids.add(result.getInt("ID"));
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(TiendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
    
    public static String read(int id) {
        String tiendaJsonString = "";
        String nombreIdentificativo = "";
        int dest = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_TIENDA_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                
                if (result.next()) {
                    nombreIdentificativo = result.getString("NOMBREIDENTIFICATIVO");
                    dest = result.getInt("ID");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(TiendaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String destino = DestinoDAO.read(dest);
            tiendaJsonString = obtainTiendaJsonString(nombreIdentificativo, destino);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiendaJsonString;
    }

    
    private static String obtainTiendaJsonString(String nombreIdentificativo, String destino) {
        String tiendaJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("ordinal", ordinal)
                .add("nombreidentificativo", nombreIdentificativo)
                .add("destino", destino);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            tiendaJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(TiendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tiendaJSON;
    }
}
