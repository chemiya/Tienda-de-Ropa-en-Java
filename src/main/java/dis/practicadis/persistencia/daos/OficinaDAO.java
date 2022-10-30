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


public class OficinaDAO {
    private static final int ordinal = 1;
    
    public static final String SELECT_OFICINA = "SELECT * FROM OFICINAS";
    public static final String SELECT_OFICINA_FROM_ID = "SELECT * FROM OFICINAS WHERE ID=?";
    
    public static ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_OFICINA)) {
                ResultSet result = s.executeQuery();
                
                while (result.next()) {
                    ids.add(result.getInt("ID"));
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(OficinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
    
    public static String read(int id) {
        String oficinaJsonString = "";
        String otrasIndicaciones = "";
        int dest = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_OFICINA_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                
                if (result.next()) {
                    otrasIndicaciones = result.getString("OTRASINDICACIONESDEZONA");
                    dest = result.getInt("ID");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(OficinaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String destino = DestinoDAO.read(dest);
            oficinaJsonString = obtainOficinaJsonString(otrasIndicaciones, destino);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return oficinaJsonString;
    }

    
    private static String obtainOficinaJsonString(String otrasIndicaciones, String destino) {
        String oficinaJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("ordinal", ordinal)
                .add("otrasindicaciones", otrasIndicaciones)
                .add("destino", destino);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            oficinaJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(OficinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oficinaJSON;
    }
}

