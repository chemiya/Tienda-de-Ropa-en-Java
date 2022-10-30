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
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;


public class DestinoDAO {
    public static final String SELECT_DESTINO = "SELECT * FROM DESTINOS WHERE ID=?";
    
    public static String read(int id) {
        String destinoJsonString = "";
        String telefono = "";
        int dir = 0;
        int idDest = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_DESTINO)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    telefono= result.getString("TELEFONO");
                    dir= result.getInt("DIRECCION");
                    idDest= result.getInt("ID");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DestinoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String direccion = DireccionDAO.read(dir);
            destinoJsonString = obtainDestinoJsonString(idDest, telefono, direccion);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return destinoJsonString;
    }
    
    public static String getConOrdinal(int id){
        String result = null;
        if(TiendaDAO.getIds().contains(id)){
            result = TiendaDAO.read(id);
        } else if (AlmacenDAO.getIds().contains(id)){
            result = AlmacenDAO.read(id);
        } else if (OficinaDAO.getIds().contains(id)){
            result = OficinaDAO.read(id);
        }
        return result;
    }

    private static String obtainDestinoJsonString(int id, String telefono, String direccion) {
        String productoJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("id", id)
                .add("telefono", telefono)
                .add("direccion", direccion);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            productoJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(DestinoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return productoJSON;
    }
}
