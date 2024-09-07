/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz.vistacontrolador.registrarempleado;

import dis.practicadis.dominio.controlador.registrarempleado.ControladorRegistrarEmpleado;
import dis.practicadis.dominio.modelo.Destino;
import dis.practicadis.dominio.modelo.Direccion;
import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.dominio.modelo.Rol;
import dis.practicadis.dominio.modelo.disponibilidad.Trabajando;
import dis.practicadis.dominio.modelo.vinculacion.Contratado;
import dis.practicadis.exceptions.detailed.JSONException;
import dis.practicadis.interfaz.GestorDeInterfaz;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ControladorVistaRegistrarEmpleado {

    private final VistaRegistrarEmpleado vista;
    private final ControladorRegistrarEmpleado control;
    private Empleado empleado;

    public ControladorVistaRegistrarEmpleado(VistaRegistrarEmpleado v) {
        this.vista = v;
        control = new ControladorRegistrarEmpleado();
    }

    public void procesarEventoNif() {
        if (!control.doesUserExist(vista.getNifValue())) {
            vista.nifCorrecto();
        } else {
            vista.errorControlador("El nif introducido ya se encuentra registrado en el sistema.");
            vista.cancelarCaso();
        }
    }

    public void procesarEventoDatos() {
        this.empleado = new Empleado(
                vista.getNombreValue(),
                vista.getApellidosValue(),
                vista.getNifValue(),
                this.generarContra(15),
                vista.getTelefonoValue(),
                vista.getEmailValue(),
                LocalDateTime.now().truncatedTo(ChronoUnit.DAYS),
                vista.getSeguridadSocialValue(),
                vista.getIBANValue(),
                null,
                new Direccion(
                        vista.getCalleValue(),
                        Integer.parseInt(vista.getNumeroValue()),
                        vista.getOtrosValue(),
                        Integer.parseInt(vista.getCodigoPostalValue()),
                        vista.getLocalidadValue(),
                        vista.getProvinciaValue()
                )
        );
        vista.datosCorrectos(this.empleado);
    }

    public void procesarEventoRolYDestino() {
        Destino tempDes = null;
        Rol tempRol = null;
        if (control.doesRolExist(vista.getRolValue())) {
            try {
                tempDes = control.getDestino(Integer.parseInt(vista.getDestinoValue()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tempRol = control.getRolFromString(vista.getRolValue(), this.empleado.getFechaInicio());
            if (tempDes != null) {
                if (tempRol.getDestinaciones().equals(tempDes.getDestinaciones())) {
                    this.empleado.setDestino(tempDes);
                    this.empleado.addRol(tempRol);
                    this.empleado.addDisponibilidad(new Trabajando(this.empleado.getFechaInicio()));
                    this.empleado.addVinculacion(new Contratado(this.empleado.getFechaInicio()));
                    control.writeEmpleado(this.empleado, Integer.parseInt(vista.getDestinoValue()));
                    vista.mensajeControlador("Usuario registrado, su contraseña es " + this.empleado.getContra() + ". Le recomendamos cambiarla inmediatamente.");
                    this.empleado = null;
                    vista.cancelarCaso();
                } else {
                    vista.errorControlador("El rol introducido no puede trabajar en el destino introducido.");
                    vista.cancelarRolYDestino();
                }
            } else {
                vista.errorControlador("El destino es inexistente.");
                vista.cancelarRolYDestino();
            }

        } else {
            vista.errorControlador("El rol es inexistente.");
            vista.cancelarRolYDestino();
        }
    }

    public void cancelarDatos() {
        this.empleado = null;
        vista.cancelarDatos();
    }

    public void cancelarNif() {
        vista.cancelarCaso();
    }

    private String generarContra(int tam) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tam; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }

    public void cancelarCompleto() {
        GestorDeInterfaz.obtenerInstancia().showIdentificacion();
    }

}
