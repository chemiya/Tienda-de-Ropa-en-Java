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
import java.time.LocalDateTime;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;


public abstract class Vinculacion implements Comparable<Vinculacion> {

    private LocalDateTime fechaInicio;
    private boolean trabaja;

    public Vinculacion(LocalDateTime fecha, boolean puedeTrabajar) {
        this.fechaInicio = fecha;
        this.trabaja = puedeTrabajar;
    }

    public LocalDateTime getFecha() {
        return this.fechaInicio;
    }
    public boolean getTrabaja(){
        return this.trabaja;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fechaInicio = fecha;
    }
    public void setTrabaja(boolean puedeTrabajar)
    {
        this.trabaja = puedeTrabajar;
    } 
    


    @Override
    public int compareTo(Vinculacion otro) {
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
        String vinculacionJSON = "";
        JsonObject json = Json.createObjectBuilder()
                .add("fecha", this.fechaInicio.toString())
                .build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            vinculacionJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return vinculacionJSON;
    }

    public Vinculacion(String json) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader reader = factory.createReader(new StringReader(json));
        JsonObject rolJSON = reader.readObject();
        this.fechaInicio = LocalDateTime.parse(rolJSON.getString("fecha"));
    }
    
    public abstract int getOrdinal();

}
