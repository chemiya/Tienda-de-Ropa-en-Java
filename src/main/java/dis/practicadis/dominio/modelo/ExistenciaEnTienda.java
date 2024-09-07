/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.destino.Tienda;
import dis.practicadis.exceptions.detailed.JSONException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;
import javax.json.JsonWriter;

public class ExistenciaEnTienda {

    private int cantidad;
    private Producto producto;
    private Tienda tienda;

    public ExistenciaEnTienda(int cant, Producto prod, Tienda tien) {
        this.cantidad = cant;
        this.producto = prod;
        this.tienda = tien;
    }

    public void setCantidad(int cant) {
        this.cantidad = cant;
    }

    public void setProducto(Producto prod) {
        this.producto = prod;
    }

    public void setTienda(Tienda tien) {
        this.tienda = tien;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public Tienda getTienda() {
        return this.tienda;
    }

    public String toJSON() throws JSONException {
        String existenciaJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("cantidad", this.cantidad)
                .add("producto", this.producto.toJSON())
                .add("tienda", this.tienda.toJSON());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            existenciaJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return existenciaJSON;
    }

    public ExistenciaEnTienda(String json) throws JSONException {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject existenciaJSON = reader.readObject();
            this.cantidad = existenciaJSON.getInt("cantidad");
            this.producto = new Producto(existenciaJSON.getString("producto"));
            this.tienda = new Tienda(existenciaJSON.getString("tienda"));
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }

    public static ArrayList<ExistenciaEnTienda> arrayDeExistencias(String json) throws JSONException {
        ArrayList<ExistenciaEnTienda> existencias = new ArrayList<>();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject existenciaJSON = reader.readObject();
            JsonReader readerArray = factory.createReader(new StringReader(existenciaJSON.getString("existencias")));
            JsonArray existenciasArray = readerArray.readArray();
            for (JsonValue j : existenciasArray) {
                existencias.add(new ExistenciaEnTienda(j.toString()));
            }
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
        return existencias;
    }
}
