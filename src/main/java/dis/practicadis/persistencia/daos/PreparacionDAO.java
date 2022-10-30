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


public class PreparacionDAO {
    public static final String SELECT_PREPARACION_FROM_ID = "SELECT * FROM PREPARACIONPEDIDOS WHERE ID=?";


    public static String read(int id) {
        String preparacionJsonString = "";
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            LocalDateTime fecha = null;
            String idPreparadoPor = null;
            int idRegistro = 0;
            int idAlmacen = 0;
            try (
                     PreparedStatement s = connection.getStatement(SELECT_PREPARACION_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                if (result.next()) {
                    fecha = result.getTimestamp("FECHAPREPARACION").toLocalDateTime();
                    idPreparadoPor = result.getString("PREPARADOPOR");
                    if(result.getObject("REGISTRODEENTREGA") == null){
                        idRegistro = -1;
                    }
                    else{
                        idRegistro = result.getInt("REGISTRODEENTREGA");
                    }
                    idAlmacen = result.getInt("ALMACENDONDESEPREPARA");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(PreparacionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            String preparadoPor = EmpleadoDAO.read(idPreparadoPor);
            String registro = null;
            if(idRegistro > 0){
                registro = RegistroDAO.read(idRegistro);
            }    
            String almacen = AlmacenDAO.read(idAlmacen);
            preparacionJsonString = obtainPreparacionJsonString(fecha, preparadoPor, registro, almacen);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparacionJsonString;
    }

    private static String obtainPreparacionJsonString(LocalDateTime fecha, String preparadoPor, String registro, String almacen) {
        String preparacionJSON = "";
        JsonObjectBuilder builder;
        if (registro != null) {
            builder = Json.createObjectBuilder()
                .add("fecha", fecha.toString())
                .add("preparadopor", preparadoPor)
                .add("registro", registro)
                .add("almacen", almacen);
        } else {
            builder = Json.createObjectBuilder()
                .add("fecha", fecha.toString())
                .add("preparadopor", preparadoPor)
                .add("registro", JsonObject.NULL)
                .add("almacen", almacen);
        }
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            preparacionJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(PreparacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return preparacionJSON;
    }
}
