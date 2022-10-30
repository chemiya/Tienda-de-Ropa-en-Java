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

import java.util.ArrayList; 
import java.util.function.Consumer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;


public class Empleado extends Usuario {

    private LocalDateTime fechaInicioEnEmpresa;
    private String numeroSeguridadSocial;
    private String iban;
    private ArrayList<Rol> roles;
    private ArrayList<Disponibilidad> dispo;
    private ArrayList<Vinculacion> vincu;
    private Destino destino;
    private Direccion direccion;

    public Empleado(String nom, String ap, String nif, String con, String tel, String mail, LocalDateTime fecha, String num, String iban, Destino des, Direccion dir) {
        super(nom, ap, nif, con, tel, mail);
        this.fechaInicioEnEmpresa = fecha;
        this.numeroSeguridadSocial = num;
        this.iban = iban;
        this.destino = des;
        this.direccion = dir;
        roles = new ArrayList<>();
        dispo = new ArrayList<>();
        vincu = new ArrayList<>();
    }

    public LocalDateTime getFechaInicio(){
        return this.fechaInicioEnEmpresa;
    }
    public String getSeguridadSocial(){
        return this.numeroSeguridadSocial;
    }
    public String getIBAN(){
        return this.iban;
    }
    public Destino getDestino(){
        return this.destino;
    }
    public Direccion getDireccion(){
        return this.direccion;
    }
    
    
    public void setFechaInicio(LocalDateTime dte){
        this.fechaInicioEnEmpresa = dte;
    }
    public void setSeguridadSocial(String str){
        this.numeroSeguridadSocial = str;
    }
    public void setIBAN(String str){
        this.iban = str;
    }
    public void setDestino(Destino des){
        this.destino = des;
    }
    public void setDireccion(Direccion dir){
        this.direccion = dir;
    }
    
    public void addRol(Rol rol) {
        this.roles.add(rol);
    }
    public void addDisponibilidad(Disponibilidad dis) {
        this.dispo.add(dis);
    }
    public void addVinculacion(Vinculacion vin) {
        this.vincu.add(vin);
    }
    
    public ArrayList<Rol> getRoles(){
        return this.roles;
    }
    public ArrayList<Disponibilidad> getDisponibilidad(){
        return this.dispo;
    }
    public ArrayList<Vinculacion> getVinculacion(){
        return this.vincu;
    }
    
    public boolean canWork(){
        return ((this.dispo.get(dispo.size() - 1).getTrabaja())&&(this.vincu.get(vincu.size() - 1).getTrabaja()));
    }
    
    public ArrayList<String> getPrivilegios(){
        return this.roles.get(roles.size() - 1).getPrivilegios();
    }

    public String toJSON(boolean conDatos) throws JSONException {
        String empleadoJSON = "";
        JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("nombre", this.getNombre())
                .add("apellidos", this.getApellidos())
                .add("nif", this.getNif())
                .add("contra", this.getContra())
                .add("telefono", this.getTelefono())
                .add("email", this.getEmail())
                .add("numss", this.numeroSeguridadSocial)
                .add("iban", this.iban)
                .add("fechaInicioEmpresa", this.fechaInicioEnEmpresa.toString())
                .add("destino", this.destino.toJSON())
                .add("direccion", this.direccion.toJSON());

        if (conDatos) {
            JsonArrayBuilder listaRol = Json.createArrayBuilder();
            roles.forEach((Rol entrada) -> {
                listaRol.add(Json.createObjectBuilder()
                        .add("fechaRol", entrada.getFecha().toString())
                        .add("rolOrdinal", entrada.getOrdinal())
                        .build());
            });
            JsonArrayBuilder listaDisponibilidad = Json.createArrayBuilder();
            dispo.forEach(new Consumer<Disponibilidad>() {
                @Override
                public void accept(Disponibilidad entrada) {
                    if(entrada.getFechaFin() != null){
                        listaDisponibilidad.add(Json.createObjectBuilder()
                                .add("fechaDispoIni", entrada.getFechaInicio().toString())
                                .add("fechaDispoFin", entrada.getFechaFin().toString())
                                .add("dispoOrdinal", entrada.getOrdinal())
                                .build());
                    }
                    else{
                        listaDisponibilidad.add(Json.createObjectBuilder()
                                .add("fechaDispoIni", entrada.getFechaInicio().toString())
                                .add("fechaDispoFin", JsonObject.NULL)
                                .add("dispoOrdinal", entrada.getOrdinal())
                                .build());
                    }
                }
            });
            JsonArrayBuilder listaVinculacion = Json.createArrayBuilder();
            vincu.forEach((Vinculacion entrada) -> {
                listaVinculacion.add(Json.createObjectBuilder()
                        .add("fechaVincu", entrada.getFecha().toString())
                        .add("vincuOrdinal", entrada.getOrdinal())
                        .build());
            });
            builder.add("roles", listaRol);
            builder.add("disponibilidades", listaDisponibilidad);
            builder.add("vinculaciones", listaVinculacion);
        }
        JsonObject json = builder.build();
        try (
                 StringWriter stringWriter = new StringWriter();  JsonWriter writer = Json.createWriter(stringWriter);) {
            writer.writeObject(json);
            empleadoJSON = stringWriter.toString();
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

        return empleadoJSON;
    }

    public Empleado(String json) throws JSONException {
        super(json);
        this.roles = new ArrayList<>();
        this.dispo = new ArrayList<>();
        this.vincu = new ArrayList<>();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (
                 JsonReader reader = factory.createReader(new StringReader(json));) {
            JsonObject empleadoJSON = reader.readObject();
            this.fechaInicioEnEmpresa = LocalDateTime.parse(empleadoJSON.getString("fechaInicioEmpresa"));
            this.iban = empleadoJSON.getString("iban");
            this.numeroSeguridadSocial = empleadoJSON.getString("numss");
            this.destino = FactoriaDestino.crearDestinoConJSON(empleadoJSON.getString("destino"));
            this.direccion = new Direccion(empleadoJSON.getString("direccion"));

            JsonArray rolArray = empleadoJSON.getJsonArray("roles");
            JsonArray dispoArray = empleadoJSON.getJsonArray("disponibilidades");
            JsonArray vincuArray = empleadoJSON.getJsonArray("vinculaciones");

            LocalDateTime fecha;
            LocalDateTime fechaFin;
            int ordinal;
            for (JsonValue j : rolArray) {
                fecha = LocalDateTime.parse(j.asJsonObject().getString("fechaRol"));
                ordinal = j.asJsonObject().getInt("rolOrdinal");
                this.roles.add(FactoriaRol.crearRolConOrdinal(ordinal, fecha));
            }
            for (JsonValue j : dispoArray) {
                fecha = LocalDateTime.parse(j.asJsonObject().getString("fechaDispoIni"));
                ordinal = j.asJsonObject().getInt("dispoOrdinal");
                if(!(j.asJsonObject().isNull("fechaDispoFin"))){
                    fechaFin = LocalDateTime.parse(j.asJsonObject().getString("fechaDispoFin"));
                }
                else{
                    fechaFin=null;
                }
                this.dispo.add(FactoriaDisponibilidad.crearDisponibilidadConOrdinal(ordinal, fecha, fechaFin));
            }
            for (JsonValue j : vincuArray) {
                fecha = LocalDateTime.parse(j.asJsonObject().getString("fechaVincu"));
                ordinal = j.asJsonObject().getInt("vincuOrdinal");
                this.vincu.add(FactoriaVinculacion.crearVinculacionConOrdinal(ordinal, fecha));
            }
        } catch (Exception e) {
            throw new JSONException("Could not read data from JSON.", e);
        }

    }

}
