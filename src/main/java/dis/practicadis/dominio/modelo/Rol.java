/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.exceptions.detailed.JSONException;

import java.time.LocalDateTime;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;


public abstract class Rol implements Comparable<Rol> {

    private LocalDateTime fechaInicio;
    private ArrayList<String> privilegios;

    public Rol(LocalDateTime fecha) {
        this.fechaInicio = fecha;
        this.privilegios = new ArrayList<String>();
        this.privilegios.add("Solicitar vacaciones");
    }

    public LocalDateTime getFecha() {
        return this.fechaInicio;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fechaInicio = fecha;
    }
    
    public ArrayList<String> getPrivilegios(){
        return this.privilegios;
    }
    
    public void addPrivilegio(String priv){
        this.privilegios.add(priv);
    }


    @Override
    public int compareTo(Rol otro) {
        if (otro == null) {
            throw new IllegalArgumentException("Argument was null.");
        }
        int comp = 0;
        if (this.getFecha().isAfter(otro.getFecha())) {
            comp = 1;
        }
        if (this.getFecha().isBefore(otro.getFecha())) {
            comp = -1;
        }
        return comp;
    }

    public String toJSON() throws JSONException{
        String rolJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("fecha", this.fechaInicio.toString())
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            rolJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return rolJSON;
    }

    public Rol(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(new StringReader(json));
        JsonObject rolJSON = reader.readObject();
        this.fechaInicio = LocalDateTime.parse(rolJSON.getString("fecha"));
    }
    
    public abstract int getOrdinal();
    public abstract String getDestinaciones();

}
