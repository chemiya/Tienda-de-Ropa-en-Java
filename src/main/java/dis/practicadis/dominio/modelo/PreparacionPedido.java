/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.destino.Almacen;
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


public class PreparacionPedido {
    private LocalDateTime fechaPreparacion;
    private Empleado preparadoPor;
    private RegistroEntregas registroDeEntregas;
    private Almacen almacenPreparacion;
    
    public PreparacionPedido(LocalDateTime fec, Empleado emp, RegistroEntregas reg, Almacen al){
        this.fechaPreparacion = fec;
        this.preparadoPor = emp;
        this.registroDeEntregas = reg;
        this.almacenPreparacion = al;
    }
    
    public LocalDateTime getFechaPreparacion(){
        return this.fechaPreparacion;
    }
    
    public Empleado getPreparadoPor(){
        return this.preparadoPor;
    }
    
    public RegistroEntregas getRegistroEntregas(){
        return this.registroDeEntregas;
    }
    
    public Almacen getAlmacen(){
        return this.almacenPreparacion;
    }
    
    
    public void setFechaPreparacion(LocalDateTime fec){
        this.fechaPreparacion = fec;
    }
    
    public void setPreparadoPor(Empleado emp){
        this.preparadoPor = emp;
    }
    
    public void setRegistroEntregas(RegistroEntregas reg){
        this.registroDeEntregas = reg;
    }
    
    public void setAlmacen(Almacen al){
        this.almacenPreparacion = al;
    }
    
    public String toJSON() throws JSONException {
        String preparacionJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("fecha", this.fechaPreparacion.toString())
                .add("preparadopor", this.preparadoPor.toJSON())
                .add("registro", this.registroDeEntregas.toJSON())
                .add("almacen", this.almacenPreparacion.toJSON());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
           preparacionJSON = stringWriter.toString();
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

        return preparacionJSON;
    }

    public PreparacionPedido(String json) throws JSONException {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject preparacionJSON = reader.readObject();
            this.fechaPreparacion = LocalDateTime.parse(preparacionJSON.getString("fecha"));
            this.preparadoPor = new Empleado(preparacionJSON.getString("preparadopor"));
            if(preparacionJSON.isNull("registro")){
                this.registroDeEntregas = null;
            } else{
                this.registroDeEntregas = new RegistroEntregas(preparacionJSON.getString("registro"));
            }
            
            this.almacenPreparacion = new Almacen(preparacionJSON.getString("almacen"));
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

    }
}
