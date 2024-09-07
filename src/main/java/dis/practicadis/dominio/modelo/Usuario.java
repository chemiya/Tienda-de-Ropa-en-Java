/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

import dis.practicadis.exceptions.detailed.JSONException;


public class Usuario {

    private String nombre;
    private String apellidos;
    private String nif;
    private String contra;
    private String telefono;
    private String email;

    public Usuario(String nom, String ap, String nif, String con, String tel, String mail) {
        this.nombre = nom;
        this.apellidos = ap;
        this.nif = nif;
        this.contra = con;
        this.telefono = tel;
        this.email = mail;
    }

    public String toJSON() throws JSONException{
        String usuarioJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("nombre", this.nombre)
                .add("apellidos", this.apellidos)
                .add("nif", this.nif)
                .add("contra", this.contra)
                .add("telefono", this.telefono)
                .add("email", this.email);
        JsonObject json = builder.build();
        try (
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            usuarioJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return usuarioJSON;
    }

    public Usuario(String json) throws JSONException{
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject usuarioJSON = reader.readObject();
            this.nombre = usuarioJSON.getString("nombre");
            this.apellidos = usuarioJSON.getString("apellidos");
            this.nif = usuarioJSON.getString("nif");
            this.contra = usuarioJSON.getString("contra");
            this.telefono = usuarioJSON.getString("telefono");
            this.email = usuarioJSON.getString("email");
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }
    
    public String getNombre(){
        return this.nombre;
    }
    public String getApellidos(){
        return this.apellidos;
    }
    public String getNif(){
        return this.nif;
    }
    public String getContra(){
        return this.contra;
    }
    public String getTelefono(){
        return this.telefono;
    }
    public String getEmail(){
        return this.email;
    }
    
    public void setNombre(String str){
        this.nombre = str;
    }
    public void setApellidos(String str){
        this.apellidos = str;
    }
    public void setNif(String str){
        this.nif = str;
    }
    public void setContra(String str){
        this.contra = str;
    }
    public void setTelefono(String str){
        this.telefono = str;
    }
    public void setEmail(String str){
        this.email = str;
    }
    
}
