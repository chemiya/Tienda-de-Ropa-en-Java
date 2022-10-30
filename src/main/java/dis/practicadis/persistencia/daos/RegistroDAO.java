/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.dominio.modelo.RegistroEntregas;
import dis.practicadis.persistencia.accesodb.ConnectionDB;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class RegistroDAO {
    public static final String SELECT_REGISTRO = "SELECT * FROM REGISTRODEENTREGAS";
    public static final String SELECT_REGISTRO_FROM_ID = "SELECT * FROM REGISTRODEENTREGAS WHERE ID=?";
    public static final String INSERT_REGISTRO = "INSERT INTO REGISTRODEENTREGAS (id, fecha, hora, dniquienfirma) VALUES (?, ?, ?, ?)";
    
    
    public static ArrayList<Integer> getIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_REGISTRO)) {
                ResultSet result = s.executeQuery();

                while (result.next()) {
                    ids.add(result.getInt("ID"));
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static String read(int id) {
        String registroJsonString = null;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            LocalDateTime fecha = null;
            String dniFirmante = null;
            try (
                     PreparedStatement s = connection.getStatement(SELECT_REGISTRO_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    fecha = result.getTimestamp("FECHA").toLocalDateTime();
                    dniFirmante = result.getString("DNIQUIENFIRMA");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            registroJsonString = obtainRegistroJsonString(fecha, dniFirmante);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registroJsonString;
    }

    private static String obtainRegistroJsonString(LocalDateTime fecha, String dniFirmante) {
        String preparacionJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("fecha", fecha.toString())
                .add("dnifirma", dniFirmante);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            preparacionJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(RegistroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preparacionJSON;
    }

    public static void write(RegistroEntregas reg) {
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_REGISTRO);) {
                int id;
                if(getIds().isEmpty()){
                    id = 1;
                } else{
                    id = Collections.max(getIds()) + 1;
                }
                s.setInt(1, id);
                s.setDate(2, java.sql.Date.valueOf(reg.getFecha().toLocalDate()));
                s.setTime(3, java.sql.Time.valueOf(reg.getFecha().toLocalTime()));
                s.setString(4, reg.getDNI());
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
