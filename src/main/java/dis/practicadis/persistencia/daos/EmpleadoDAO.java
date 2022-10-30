/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.dominio.modelo.Disponibilidad;
import dis.practicadis.persistencia.accesodb.ConnectionDB;
import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.dominio.modelo.Rol;
import dis.practicadis.dominio.modelo.Vinculacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.StringReader;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

public class EmpleadoDAO {

    public static final String SELECT_USUARIO = "SELECT * FROM USUARIOS WHERE NIF=?";
    public static final String SELECT_EMPLEADO = "SELECT * FROM EMPLEADOS WHERE NIF=?";
    public static final String INSERT_USUARIO = "INSERT INTO USUARIOS (nif, nombre, apellidos, password, email, telefono) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_EMPLEADO = "INSERT INTO EMPLEADOS (nif, numeroseguridadsocial, iban, fechainicioenempresa, direccionpostal, destinadoen) VALUES (?, ?, ?, ?, ?, ?)";

    public static boolean doesUserExist(String nif) {
        boolean resultado = false;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_USUARIO);) {
                s.setString(1, nif);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }
    
    public static String getFromUsuarioYContra(String nif, String contra) {
        boolean passCorrect = false;
        String resul = null;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_USUARIO)) {
                s.setString(1, nif);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    if(result.getString("PASSWORD").equals(contra)){
                        passCorrect = true;
                    }
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(passCorrect){
            resul = read(nif);
        }
        return resul;
    }

    public static String read(String nif) {
        String empleadoJsonString = "";
        LocalDateTime inicioEmpresa = LocalDateTime.now();
        String[] datos = new String[8];
        int destino = 0;
        int direccion = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s1 = connection.getStatement(SELECT_USUARIO);  PreparedStatement s2 = connection.getStatement(SELECT_EMPLEADO)) {
                s1.setString(1, nif);
                ResultSet result1 = s1.executeQuery();
                s2.setString(1, nif);
                ResultSet result2 = s2.executeQuery();
                if (result1.next()) {
                    datos[0] = result1.getString("NOMBRE");
                    datos[1] = result1.getString("APELLIDOS");
                    datos[2] = result1.getString("NIF");
                    datos[3] = result1.getString("PASSWORD");
                    datos[4] = result1.getString("TELEFONO");
                    datos[5] = result1.getString("EMAIL");
                }
                result1.close();
                if (result2.next()) {
                    inicioEmpresa = result2.getTimestamp("FECHAINICIOENEMPRESA").toLocalDateTime();
                    datos[6] = result2.getString("NUMEROSEGURIDADSOCIAL");
                    datos[7] = result2.getString("IBAN");
                    destino = result2.getInt("DESTINADOEN");
                    direccion = result2.getInt("DIRECCIONPOSTAL");
                }
                result2.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String roles = RolDAO.read(nif);
            String dispo = DisponibilidadDAO.read(nif);
            String vincu = VinculacionDAO.read(nif);

            empleadoJsonString = obtainEmpleadoJsonString(roles, dispo, vincu, datos, destino, direccion, inicioEmpresa);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleadoJsonString;
    }

    private static String obtainEmpleadoJsonString(String rol, String disponibilidad, String vinculacion, String[] datos, int destino, int direccion, LocalDateTime inicioEmpresa) {
        String registryJsonString = "";
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader readerRol = factory.createReader(new StringReader(rol));  JsonReader readerDispo = factory.createReader(new StringReader(disponibilidad));  JsonReader readerVincu = factory.createReader(new StringReader(vinculacion));  StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            JsonArray rolJsonArray = readerRol.readArray();
            JsonArray dispoJsonArray = readerDispo.readArray();
            JsonArray vincuJsonArray = readerVincu.readArray();
            String jsonDestino = DestinoDAO.getConOrdinal(destino);
            String jsonDireccion = DireccionDAO.read(direccion);

            JsonObject registryJson = Json.createObjectBuilder()
                    .add("nombre", datos[0])
                    .add("apellidos", datos[1])
                    .add("nif", datos[2])
                    .add("contra", datos[3])
                    .add("telefono", datos[4])
                    .add("email", datos[5])
                    .add("numss", datos[6])
                    .add("iban", datos[7])
                    .add("destino", jsonDestino)
                    .add("direccion", jsonDireccion)
                    .add("fechaInicioEmpresa", inicioEmpresa.toString())
                    .add("roles", rolJsonArray)
                    .add("disponibilidades", dispoJsonArray)
                    .add("vinculaciones", vincuJsonArray)
                    .build();
            writer.writeObject(registryJson);
            registryJsonString = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registryJsonString;
    }

    public static void write(Empleado emp, int dest) {
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_USUARIO);) {
                s.setString(1, emp.getNif());
                s.setString(2, emp.getNombre());
                s.setString(3, emp.getApellidos());
                s.setString(4, emp.getContra());
                s.setString(5, emp.getEmail());
                s.setString(6, emp.getTelefono());
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (
                     PreparedStatement s = connection.getStatement(INSERT_EMPLEADO);) {
                s.setString(1, emp.getNif());
                s.setString(2, emp.getSeguridadSocial());
                s.setString(3, emp.getIBAN());
                s.setDate(4, java.sql.Date.valueOf(emp.getFechaInicio().toLocalDate()));
                s.setInt(5, DireccionDAO.write(emp.getDireccion()));
                s.setInt(6, dest);
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(Rol rol : emp.getRoles()){
            RolDAO.write(rol, emp.getNif());
        }
        for(Disponibilidad dis : emp.getDisponibilidad()){
            DisponibilidadDAO.write(dis, emp.getNif());
        }
        for(Vinculacion vinc : emp.getVinculacion()){
            VinculacionDAO.write(vinc, emp.getNif());
        }
    }
}
