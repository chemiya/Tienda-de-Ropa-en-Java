/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.destino;

import dis.practicadis.dominio.modelo.Destino;
import dis.practicadis.dominio.modelo.Direccion;
import dis.practicadis.dominio.modelo.ExistenciaEnTienda;
import dis.practicadis.exceptions.detailed.JSONException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;
import javax.json.JsonWriter;

public class Tienda extends Destino {

    private String nombreIdentificativo;
    private final int ordinal = 3;
    private final String destinacion = "Tienda";

    public Tienda(int iden, String cal, int num, String otr, int cod, String loc, String prov, String tel, String nomIden) {
        super(iden, new Direccion(cal, num, otr, cod, loc, prov), tel);
        this.nombreIdentificativo = nomIden;
    }
    
    public Tienda(int iden, Direccion dir, String tel, String nomIden) {
        super(iden, dir, tel);
        this.nombreIdentificativo = nomIden;
    }

    public void setNombreIdentificativo(String nom) {
        this.nombreIdentificativo = nom;
    }
    


    public String getNombreIdentificativo() {
        return this.nombreIdentificativo;
    }
    


    public String toJSON() throws JSONException {
        String tiendaJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("ordinal", ordinal)
                .add("nombreidentificativo", this.nombreIdentificativo)
                .add("destino", super.toJSON());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            tiendaJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return tiendaJSON;
    }

    public Tienda(String json) throws JSONException {
        super(makeDestino(json));
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject tiendaJSON = reader.readObject();
            this.nombreIdentificativo = tiendaJSON.getString("nombreidentificativo");
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
