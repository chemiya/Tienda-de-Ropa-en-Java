/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.persistencia.accesodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dis.practicadis.exceptions.detailed.ConnectionDBException;
import java.io.FileInputStream;


public class ConnectionDB {

    private static ConnectionDB connectionDB;

    public static ConnectionDB getInstance() throws ConnectionDBException {
        if (connectionDB == null) {
            Properties propiedades = new Properties();
            InputStream read;
            String url, user, pass;
            try {
                read = ConnectionDB.class.getResourceAsStream("config.db");
                propiedades.load(read);
                url = propiedades.getProperty("url");
                user = propiedades.getProperty("user");
                pass = propiedades.getProperty("password");
                read.close();
                connectionDB = new ConnectionDB(url, user, pass);
            } catch (FileNotFoundException e) {
                throw new ConnectionDBException("DB configuration file not found.", e);
            } catch (IOException e) {
                throw new ConnectionDBException("Couln't read DB configuration file.", e);
            }
        }
        return connectionDB;
    }

    private Connection conn;
    private String url;
    private String user;
    private String password;

    private ConnectionDB(String url, String user, String pass) throws ConnectionDBException {
        this.url = url;
        this.user = user;
        this.password = pass;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            throw new ConnectionDBException("Derby driver not found.", e);
        }
    }

    public void openConnection() throws ConnectionDBException {
        try {
            conn = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            throw new ConnectionDBException(e.getMessage(), e);
        }
    }

    public void closeConnection() throws ConnectionDBException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new ConnectionDBException(e.getMessage(), e);
        }
    }

    public PreparedStatement getStatement(String s) throws ConnectionDBException{
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(s);
        } catch (SQLException e) {
            throw new ConnectionDBException(e.getMessage(), e);
        }
        return stmt;
    }
}
