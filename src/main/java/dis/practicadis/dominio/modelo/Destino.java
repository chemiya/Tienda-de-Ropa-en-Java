/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.exceptions.detailed.JSONException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;


public abstract class Destino {
    private int id;
    private Direccion direccion;
    private String telefono;
    
    public Destino(int iden, Direccion dir, String tel){
        this.direccion = dir;
        this.telefono = tel;
        this.id = iden;
    }
    
    public void setDireccion(Direccion dir){
        this.direccion = dir;
    }
    public void setTelefono(String tel){
        this.telefono = tel;
    }
    public void setID(int iden) {
        this.id = iden;
    }
    
    
    public Direccion getDireccion(){
        return this.direccion;
    }
    public String getTelefono(){
        return this.telefono;
    }
    public int getID() {
        return this.id;
    }
    
    
    public String toJSON() throws JSONException{
        String destinoJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("id", this.id)
                .add("telefono", this.telefono)
                .add("direccion", this.direccion.toJSON());
        JsonObject json = builder.build();
        try (
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            destinoJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return destinoJSON;
    }
    
    public Destino(String json) throws JSONException{
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject destinoJSON = reader.readObject();
            this.id = destinoJSON.getInt("id");
            this.telefono = destinoJSON.getString("telefono");
            this.direccion = new Direccion(destinoJSON.getString("direccion"));
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }
    
    public abstract String getDestinaciones();
}
