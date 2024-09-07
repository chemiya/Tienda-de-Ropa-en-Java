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
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

public class Producto {

    private String nombre;
    private double precio;
    private LocalDateTime fechaCreacion;
    private Categoria categoria;

    public Producto(String nom, double prec, LocalDateTime fecha, Categoria cat) {
        this.nombre = nom;
        this.precio = prec;
        this.fechaCreacion = fecha;
        this.categoria = cat;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public void setPrecio(double prec) {
        this.precio = prec;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fechaCreacion = fecha;
    }

    public void setCategoria(Categoria cat) {
        this.categoria = cat;
    }

    public String getNombre() {
        return this.nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public LocalDateTime getFecha() {
        return this.fechaCreacion;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public String toJSON() throws JSONException {
        String productoJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("nombre", this.nombre)
                .add("precio", this.precio)
                .add("fechaproducto", this.fechaCreacion.toString())
                .add("categoria", this.categoria.getNombre());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            productoJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return productoJSON;
    }

    public Producto(String json) throws JSONException {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject productoJSON = reader.readObject();
            this.nombre = productoJSON.getString("nombre");
            this.precio = Double.parseDouble(productoJSON.getString("precio"));
            this.fechaCreacion = LocalDateTime.parse(productoJSON.getString("fechaproducto"));
            this.categoria = new Categoria(productoJSON.getString("categoria"));
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }

}
