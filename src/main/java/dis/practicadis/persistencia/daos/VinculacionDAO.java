/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.dominio.modelo.Vinculacion;
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
import javax.json.JsonWriter;


public class VinculacionDAO {
    public static final String SELECT_VINCULACION_BY_EMPLEADO = "SELECT * FROM VINCULACIONESCONLAEMPRESA WHERE EMPLEADO=?";
    public static final String INSERT_VINCULACION = "INSERT INTO VINCULACIONESCONLAEMPRESA (inicio, empleado, vinculo) VALUES (?, ?, ?)";
    
    public static String read(String nifEmpleado) {
        StringBuilder vinculaciones = new StringBuilder("[");
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_VINCULACION_BY_EMPLEADO);) {
                s.setString(1, nifEmpleado);
                ResultSet result = s.executeQuery();
                int ordinal;
                LocalDateTime inicio;
                while (result.next()) {
                    ordinal = result.getInt("VINCULO");
                    inicio = result.getTimestamp("INICIO").toLocalDateTime();
                    vinculaciones.append(vinculoAJSON(ordinal, inicio));
                    vinculaciones.append(",");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (vinculaciones.charAt(vinculaciones.length() - 1) == ',') {
            vinculaciones.deleteCharAt(vinculaciones.length() - 1);
        }
        vinculaciones.append("]");
        return vinculaciones.toString();
    }

    private static String vinculoAJSON(int ord, LocalDateTime fecha) {
        String entryJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("fechaVincu", fecha.toString())
                .add("vincuOrdinal", ord)
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            entryJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entryJSON;
    }
    
    public static void write(Vinculacion vinc, String nif){
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_VINCULACION);) {
                s.setDate(1, java.sql.Date.valueOf(vinc.getFecha().toLocalDate()));
                s.setString(2, nif);
                s.setInt(3, vinc.getOrdinal());
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(VinculacionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
