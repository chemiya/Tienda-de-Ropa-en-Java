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
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;


public class Direccion {
    private String calle;
    private int numero;
    private String otros;
    private int codigoPostal;
    private String localidad;
    private String provincia;
    
    
    public Direccion(String cal, int num, String otr, int cod, String loc, String prov){
        this.calle = cal;
        this.numero = num;
        this.otros = otr;
        this.codigoPostal = cod;
        this.localidad = loc;
        this.provincia = prov;
    }
    
    public void setCalle(String cal){
        this.calle = cal;
    }
    public void setNumero(int num){
        this.numero = num;
    }
    public void setOtros(String otr){
        this.otros = otr;
    }
    public void setCodigo(int cod){
        this.codigoPostal = cod;
    }
    public void setLocalida(String loc){
        this.localidad = loc;
    }
    public void setProvincia(String prov){
        this.provincia = prov;
    }
    
    
    public String getCalle(){
        return this.calle;
    }
    public int getNumero(){
        return this.numero;
    }
    public String getOtros(){
        return this.otros;
    }
    public int getCodigo(){
        return this.codigoPostal;
    }
    public String getLocalidad(){
        return this.localidad;
    }
    public String getProvincia(){
        return this.provincia;
    }
    
    public String toJSON() throws JSONException{
        String direccionJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("calle", this.calle)
                .add("numero", this.numero)
                .add("otros", this.otros)
                .add("codigo", this.codigoPostal)
                .add("localidad", this.localidad)
                .add("provincia", this.provincia);
        JsonObject json = builder.build();
        try (
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            direccionJSON = stringWriter.toString();
        } catch (IOException e) {
            throw new JSONException("Could not write JSON as String.", e);
        }
        return direccionJSON;
    }
    
    public Direccion(String json) throws JSONException{
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject direccionJSON = reader.readObject();
            this.calle = direccionJSON.getString("calle");
            this.numero = direccionJSON.getInt("numero");
            this.otros = direccionJSON.getString("otros");
            this.codigoPostal = direccionJSON.getInt("codigo");
            this.localidad = direccionJSON.getString("localidad");
            this.provincia = direccionJSON.getString("provincia");
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }
    
}
