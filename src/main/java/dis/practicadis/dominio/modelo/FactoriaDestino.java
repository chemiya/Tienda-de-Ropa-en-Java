/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.destino.Almacen;
import dis.practicadis.dominio.modelo.destino.Oficina;
import dis.practicadis.dominio.modelo.destino.Tienda;
import dis.practicadis.exceptions.detailed.JSONException;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;


public class FactoriaDestino {
    public static Destino crearDestinoConOrdinal (int id, Direccion dir, String tel, String extra, int num) throws IllegalArgumentException{
        Destino resultado = null;
        switch(num){
            case 1:
                resultado = new Oficina(id, dir, tel, extra);
                break;
            case 3:
                resultado = new Tienda(id, dir, tel, extra);
                break;
            default:
                throw new IllegalArgumentException("Ordinal was out of range. Range is from 1 to 3, ordinal was "+num+".");
        }
        return resultado;
    }
    
    public static Destino crearDestinoConOrdinal (int id, Direccion dir, String tel, int extra, int num) throws IllegalArgumentException{
        Destino resultado = null;
        switch(num){
            case 2:
                resultado = new Almacen(id, dir, tel, extra);
                break;
            default:
                throw new IllegalArgumentException("Ordinal was out of range. Range is from 1 to 3, ordinal was "+num+".");
        }
        return resultado;
    }
    
    public static Destino crearDestinoConJSON (String json) throws IllegalArgumentException, JSONException{
        int ordinal = 0;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject tiendaJSON = reader.readObject();
            ordinal = tiendaJSON.getInt("ordinal");
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
        Destino resultado = null;
        switch(ordinal){
            case 1:
                resultado = new Oficina(json);
                break;
            case 2:
                resultado = new Almacen(json);
                break;
            case 3:
                resultado = new Tienda(json);
                break;
            default:
                throw new IllegalArgumentException("Ordinal was out of range. Range is from 1 to 3, ordinal was "+ordinal+".");
        }
        return resultado;
    }
}
