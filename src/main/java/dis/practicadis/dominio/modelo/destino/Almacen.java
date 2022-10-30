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


public class Almacen extends Destino{
    private int superficie;
    private final int ordinal = 2;
    private final String destinacion = "Almacen";

    public Almacen(int iden, String cal, int num, String otr, int cod, String loc, String prov, String tel, int sup) {
        super(iden, new Direccion(cal, num, otr, cod, loc, prov), tel);
        this.superficie = sup;
    }
    
    public Almacen(int iden, Direccion dir, String tel, int sup) {
        super(iden, dir, tel);
        this.superficie = sup;
    }

    public void setSuperficie(int sup) {
        this.superficie = sup;
    }

    public int getSuperficie() {
        return this.superficie;
    }


    public String toJSON() throws JSONException {
        String almacenJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("ordinal", ordinal)
                .add("superficie", this.superficie)
                .add("destino", super.toJSON());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            almacenJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return almacenJSON;
    }

    public Almacen(String json) throws JSONException {
        super(makeDestino(json));
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject tiendaJSON = reader.readObject();
            this.superficie = tiendaJSON.getInt("superficie");
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
