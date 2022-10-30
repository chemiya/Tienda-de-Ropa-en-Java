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

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;


public abstract class Disponibilidad implements Comparable<Disponibilidad> {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean trabaja;

    public Disponibilidad(LocalDateTime fecha, boolean puedeTrabajar) {
        this.fechaInicio = fecha;
        this.fechaFin = null;
        this.trabaja = puedeTrabajar;
        
    }
    
    public Disponibilidad(LocalDateTime fechaIni, LocalDateTime fechaFin, boolean puedeTrabajar) {
        this.fechaInicio = fechaIni;
        this.fechaFin = fechaFin;
        this.trabaja = puedeTrabajar;
        
    }

    public LocalDateTime getFechaInicio() {
        return this.fechaInicio;
    }
    
    public boolean getTrabaja(){
        return this.trabaja;
    }
    public LocalDateTime getFechaFin() {
        return this.fechaFin;
    }


    public void setFechaInicio(LocalDateTime fecha) {
        this.fechaInicio = fecha;
    }
    public void setTrabaja(boolean puedeTrabajar)
    {
        this.trabaja = puedeTrabajar;
    }  
    public void setFechaFin(LocalDateTime fecha) {
        this.fechaFin = fecha;
    }


    @Override
    public int compareTo(Disponibilidad otro) {
        if (otro == null) {
            throw new IllegalArgumentException("Argument was null.");
        }
        int comp = 0;
        if (this.getFechaInicio().isAfter(otro.getFechaInicio())) {
            comp = 1;
        }
        if (this.getFechaInicio().isBefore(otro.getFechaInicio())) {
            comp = -1;
        }
        return comp;
    }

    public String toJSON() throws JSONException{
        String disponibilidadJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("fecha", this.fechaInicio.toString())
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            disponibilidadJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return disponibilidadJSON;
    }

    public Disponibilidad(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(new StringReader(json));
        JsonObject rolJSON = reader.readObject();
        this.fechaInicio = LocalDateTime.parse(rolJSON.getString("fecha"));
    }
    
    public abstract int getOrdinal();

}

