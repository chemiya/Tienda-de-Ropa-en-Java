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


public class AlmacenDAO {
    private static final int ordinal = 2;
    
    public static final String SELECT_ALMACEN = "SELECT * FROM ALMACENES";
    public static final String SELECT_ALMACEN_FROM_ID = "SELECT * FROM ALMACENES WHERE ID=?";
    
    public static ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_ALMACEN)) {
                ResultSet result = s.executeQuery();
                
                while (result.next()) {
                    ids.add(result.getInt("ID"));
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlmacenDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
    
    public static String read(int id) {
        String almacenJsonString = "";
        int superficie = 0;
        int dest = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_ALMACEN_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                
                if (result.next()) {
                    superficie = result.getInt("SUPERFICIE");
                    dest = result.getInt("ID");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlmacenDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String destino = DestinoDAO.read(dest);
            almacenJsonString = obtainAlmacenJsonString(superficie, destino);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return almacenJsonString;
    }

    
    private static String obtainAlmacenJsonString(int superficie, String destino) {
        String almacenJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("ordinal", ordinal)
                .add("superficie", superficie)
                .add("destino", destino);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            almacenJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(OficinaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return almacenJSON;
    }
}

