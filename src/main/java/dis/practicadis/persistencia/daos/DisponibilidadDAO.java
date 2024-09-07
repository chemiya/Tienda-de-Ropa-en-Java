/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.dominio.modelo.Disponibilidad;
import dis.practicadis.persistencia.accesodb.ConnectionDB;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;


public class DisponibilidadDAO {

    public static final String SELECT_DISPONIBILIDAD_BY_EMPLEADO = "SELECT * FROM DISPONIBILIDADES WHERE EMPLEADO=?";
    public static final String INSERT_DISPONIBILIDAD = "INSERT INTO DISPONIBILIDADES (comienzo, finalprevisto, empleado, disponibilidad) VALUES (?, ?, ?, ?)";
    
    public static String read(String nifEmpleado) {
        StringBuilder disponibilidades = new StringBuilder("[");
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_DISPONIBILIDAD_BY_EMPLEADO);) {
                s.setString(1, nifEmpleado);
                ResultSet result = s.executeQuery();
                int ordinal;
                LocalDateTime inicio;
                LocalDateTime fin;
                while (result.next()) {
                    ordinal = result.getInt("DISPONIBILIDAD");
                    inicio = result.getTimestamp("COMIENZO").toLocalDateTime();
                    if(result.getTimestamp("FINALPREVISTO")==null){
                        fin = null;
                    }
                    else{
                        fin = result.getTimestamp("FINALPREVISTO").toLocalDateTime();
                    }
                    
                    disponibilidades.append(disponibilidadAJSON(ordinal, inicio, fin));
                    disponibilidades.append(",");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DisponibilidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (disponibilidades.charAt(disponibilidades.length() - 1) == ',') {
            disponibilidades.deleteCharAt(disponibilidades.length() - 1);
        }
        disponibilidades.append("]");
        return disponibilidades.toString();
    }

    private static String disponibilidadAJSON(int ord, LocalDateTime fecha, LocalDateTime fechafin) {
        String entryJSON = "";
        JsonObject json;
        if (fechafin != null) {
            json = Json.createObjectBuilder()
                    .add("fechaDispoIni", fecha.toString())
                    .add("fechaDispoFin", fechafin.toString())
                    .add("dispoOrdinal", ord)
                    .build();
        } else {
            json = Json.createObjectBuilder()
                    .add("fechaDispoIni", fecha.toString())
                    .add("fechaDispoFin", JsonObject.NULL)
                    .add("dispoOrdinal", ord)
                    .build();
        }

        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            entryJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(DisponibilidadDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entryJSON;
    }
    
    public static void write(Disponibilidad dis, String nif){
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_DISPONIBILIDAD);) {
                s.setDate(1, java.sql.Date.valueOf(dis.getFechaInicio().toLocalDate()));
                if(dis.getFechaFin() == null){
                    s.setNull(2, java.sql.Types.DATE);
                } else{
                     s.setDate(2, java.sql.Date.valueOf(dis.getFechaFin().toLocalDate()));
                }
                s.setString(3, nif);
                s.setInt(4, dis.getOrdinal());
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DisponibilidadDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
