/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.destino.Tienda;
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


public class PedidoDeSuministro {
    private int cantidad;
    private LocalDateTime fecha;
    private EstadosPedido estado;
    private PreparacionPedido preparacion;
    private Tienda solicitadoEn;
    private Producto pedido;
    
    public PedidoDeSuministro(int can, LocalDateTime fec, EstadosPedido est, PreparacionPedido prep, Tienda tien, Producto prod){
        this.cantidad = can;
        this.fecha = fec;
        this.estado = est;
        this.preparacion = prep;
        this.solicitadoEn = tien;
        this.pedido = prod;
    }
    
    public int getCantidad(){
        return this.cantidad;
    }
    
    public LocalDateTime getFecha(){
        return this.fecha;
    }
    
    public EstadosPedido getEstado(){
        return this.estado;
    }
    
    public PreparacionPedido getPreparacion(){
        return this.preparacion;
    }
    
    public Tienda getSolicitante(){
        return this.solicitadoEn;
    }
    public Producto getProducto(){
        return this.pedido;
    }
    
    public void setCantidad(int can){
        this.cantidad = can;
    }
    
    public void setFecha(LocalDateTime fec){
        this.fecha = fec;
    }
    
    public void setEstado(EstadosPedido est){
        this.estado = est;
    }
    
    public void setPreparacion(PreparacionPedido prep){
        this.preparacion = prep;
    }
    
    public void setSolicitante(Tienda tien){
        this.solicitadoEn = tien;
    }
    
    public void setProducto(Producto prod){
        this.pedido = prod;
    }
    
    public String toJSON() throws JSONException {
        String pedidoJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("cantidad", this.cantidad)
                .add("fecha", this.fecha.toString())
                .add("estado", this.estado.getOrdinal())
                .add("preparacion", this.preparacion.toJSON())
                .add("solicitante", this.solicitadoEn.toJSON())
                .add("pedido", this.pedido.toJSON());
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
           pedidoJSON = stringWriter.toString();
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

        return pedidoJSON;
    }

    public PedidoDeSuministro(String json) throws JSONException {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject pedidoJSON = reader.readObject();
            this.cantidad = pedidoJSON.getInt("cantidad");
            this.fecha = LocalDateTime.parse(pedidoJSON.getString("fecha"));
            this.estado = EstadosPedido.getEstado(pedidoJSON.getInt("estado"));
            if(pedidoJSON.isNull("preparacion")){
                this.preparacion = null;
            } else{
                this.preparacion = new PreparacionPedido(pedidoJSON.getString("preparacion"));
            }
            this.solicitadoEn = new Tienda(pedidoJSON.getString("solicitante"));
            this.pedido = new Producto(pedidoJSON.getString("pedido"));
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }
    }
}
