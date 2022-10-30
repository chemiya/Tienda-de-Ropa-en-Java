/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.exceptions.detailed.JSONException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

public class RegistroEntregas {

    private LocalDateTime fecha;
    private String dniQueFirma;

    public RegistroEntregas(LocalDateTime fec, String dni) {
        this.fecha = fec;
        this.dniQueFirma = dni;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public String getDNI() {
        return this.dniQueFirma;
    }

    public void setFecha(LocalDateTime fec) {
        this.fecha = fec;
    }

    public void setDNI(String dni) {
        this.dniQueFirma = dni;
    }

    public String toJSON() throws JSONException {
        String registroJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("fecha", this.fecha.toString())
                .add("dnifirma", this.dniQueFirma);
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
           registroJSON = stringWriter.toString();
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

        return registroJSON;
    }

    public RegistroEntregas(String json) throws JSONException {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject registroJSON = reader.readObject();
            this.fecha = LocalDateTime.parse(registroJSON.getString("fecha"));
            this.dniQueFirma = registroJSON.getString("dnifirma");
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

    }
}
