/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.dominio.modelo.Rol;
import dis.practicadis.persistencia.accesodb.ConnectionDB;
import dis.practicadis.exceptions.detailed.ConnectionDBException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

public class RolDAO {

    public static final String SELECT_ROLES_BY_EMPLEADO = "SELECT * FROM ROLESENEMPRESA WHERE EMPLEADO=?";
    public static final String SELECT_TIPOS_ROLES = "SELECT * FROM TIPOSDEROL";
    public static final String INSERT_ROLES = "INSERT INTO ROLESENEMPRESA (comienzoenrol, empleado, rol) VALUES (?, ?, ?)";

    public static String read(String nifEmpleado) {
        StringBuilder roles = new StringBuilder("[");
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_ROLES_BY_EMPLEADO);) {
                s.setString(1, nifEmpleado);
                ResultSet result = s.executeQuery();
                int ordinal;
                LocalDateTime inicio;
                while (result.next()) {
                    ordinal = result.getInt("ROL");
                    inicio = result.getTimestamp("COMIENZOENROL").toLocalDateTime();
                    roles.append(rolAJSON(ordinal, inicio));
                    roles.append(",");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (roles.charAt(roles.length() - 1) == ',') {
            roles.deleteCharAt(roles.length() - 1);
        }
        roles.append("]");
        return roles.toString();
    }

    private static String rolAJSON(int ord, LocalDateTime fecha) {
        String entryJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("fechaRol", fecha.toString())
                .add("rolOrdinal", ord)
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

    public static HashMap<String, Integer> getRoles() {
        HashMap<String, Integer> resultado = new HashMap<>();
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_TIPOS_ROLES);) {
                ResultSet result = s.executeQuery();
                while (result.next()) {
                    resultado.put(result.getString("NOMBRETIPO"),result.getInt("IDTIPO"));
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public static void write(Rol rol, String nif){
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_ROLES);) {
                s.setDate(1, java.sql.Date.valueOf(rol.getFecha().toLocalDate()));
                s.setString(2, nif);
                s.setInt(3, rol.getOrdinal());
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
