/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.daos;

import dis.practicadis.dominio.modelo.Direccion;
import dis.practicadis.persistencia.accesodb.ConnectionDB;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

public class DireccionDAO {

    public static final String SELECT_DIRECCION = "SELECT * FROM DIRECCIONES";
    public static final String SELECT_DIRECCION_FROM_ID = "SELECT * FROM DIRECCIONES WHERE ID=?";
    public static final String INSERT_DIRECCION = "INSERT INTO DIRECCIONES (id, calle, numero, otros, codigopostal, localidad, provincia) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static ArrayList<Integer> getIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_DIRECCION)) {
                ResultSet result = s.executeQuery();

                while (result.next()) {
                    ids.add(result.getInt("ID"));
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DireccionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static String read(int id) {
        String direccionJsonString = "";
        String[] datosString = new String[4];
        int[] datosInt = new int[2];
        int cat = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_DIRECCION_FROM_ID)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();

                if (result.next()) {
                    datosString[0] = result.getString("CALLE");
                    datosString[1] = result.getString("OTROS");
                    datosString[2] = result.getString("LOCALIDAD");
                    datosString[3] = result.getString("PROVINCIA");
                    datosInt[0] = result.getInt("NUMERO");
                    datosInt[1] = result.getInt("CODIGOPOSTAL");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DireccionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
            direccionJsonString = obtainDireccionJsonString(datosString, datosInt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return direccionJsonString;
    }

    private static String obtainDireccionJsonString(String[] datosString, int[] datosInt) {
        String direccionJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("calle", datosString[0])
                .add("numero", datosInt[0])
                .add("otros", datosString[1])
                .add("codigo", datosInt[1])
                .add("localidad", datosString[2])
                .add("provincia", datosString[3]);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            direccionJSON = stringWriter.toString();
        } catch (Exception ex) {
            Logger.getLogger(DireccionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return direccionJSON;
    }

    public static int write(Direccion dir) {
        int id = 0;
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(INSERT_DIRECCION);) {
                if(getIds().isEmpty()){
                    id = 1;
                } else{
                    id = Collections.max(getIds()) + 1;
                }
                s.setInt(1, id);
                s.setString(2, dir.getCalle());
                s.setInt(3, dir.getNumero());
                s.setString(4, dir.getOtros());
                s.setInt(5, dir.getCodigo());
                s.setString(6, dir.getLocalidad());
                s.setString(7, dir.getProvincia());
                s.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DireccionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
