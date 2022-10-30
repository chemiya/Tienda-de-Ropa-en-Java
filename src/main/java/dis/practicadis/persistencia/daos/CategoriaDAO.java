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


public class CategoriaDAO {
    public static final String SELECT_CATEGORIA = "SELECT * FROM CATEGORIAS WHERE ID=?";
    
    public static String read(int id) {
        String categoria = "";
        try {
            ConnectionDB connection = ConnectionDB.getInstance();
            connection.openConnection();
            try (
                     PreparedStatement s = connection.getStatement(SELECT_CATEGORIA)) {
                s.setInt(1, id);
                ResultSet result = s.executeQuery();
                
                if (result.next()) {
                    categoria = result.getString("NOMBRE");
                }
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoria;
    }
}
