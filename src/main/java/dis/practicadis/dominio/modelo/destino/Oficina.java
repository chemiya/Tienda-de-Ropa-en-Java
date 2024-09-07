/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.destino;

import dis.practicadis.dominio.modelo.Destino;
import dis.practicadis.dominio.modelo.Direccion;

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


public class Oficina extends Destino{
    private String otrasIndicaciones;
    private final int ordinal = 1;
    private final String destinacion = "Oficina";

    public Oficina(int iden, String cal, int num, String otr, int cod, String loc, String prov, String tel, String otro) {
        super(iden, new Direccion(cal, num, otr, cod, loc, prov), tel);
        this.otrasIndicaciones = otro;
    }
    
    public Oficina(int iden, Direccion dir, String tel, String otro) {
        super(iden, dir, tel);
        this.otrasIndicaciones = otro;
    }

    public void setOtrasIdentificaciones(String otro) {
        this.otrasIndicaciones = otro;
    }

    public String getNombreIdentificativo() {
        return this.otrasIndicaciones;
    }


    public String toJSON() throws JSONException {
        String oficinaJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("ordinal", ordinal)
                .add("otrasindicaciones", this.otrasIndicaciones)
                .add("destino", super.toJSON());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            oficinaJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return oficinaJSON;
    }

    public Oficina(String json) throws JSONException {
        super(makeDestino(json));
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject tiendaJSON = reader.readObject();
            this.otrasIndicaciones = tiendaJSON.getString("otrasindicaciones");
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }

    private static String makeDestino(String json) throws JSONException {
        String destino;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject destinoJSON = reader.readObject();
            destino = destinoJSON.getString("destino");
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
        return destino;
    }
    
    public String getDestinaciones(){
        return destinacion;
    }
}
