/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz.vistacontrolador.identificacion;

import dis.practicadis.dominio.controlador.identificacion.ControladorIdentificacion;
import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.interfaz.GestorDeInterfaz;
import java.util.ArrayList;

public class ControladorVistaIdentificacion {

    private final VistaIdentificacion vista;
    private final ControladorIdentificacion control;
    private Empleado emp;

    public ControladorVistaIdentificacion(VistaIdentificacion v) {
        this.vista = v;
        control = new ControladorIdentificacion();
    }

    public void procesarEventoIdentificacion() {
        String user = vista.getUsername();
        String pass = vista.getPassword();
        if (control.doesUserExist(user)) {
            this.emp = control.getEmpleado(user, pass);
            if (emp != null) {
                if (emp.canWork()) {
                    vista.identificacionCorrecta(emp.getPrivilegios());
                } else {
                    vista.errorControlador("El usuario no está en activo.");
                }
            } else {
                vista.errorControlador("La combinación usuario/contraseña es errónea.");
            }
        } else {
            vista.errorControlador("El usuario no esta registrado en el sistema.");
        }
    }

    public void procesarEventoCancelar() {
        this.emp = null;
        vista.cancelarCaso();
    }

    public void selectCasoUso() {
        GestorDeInterfaz temp = GestorDeInterfaz.obtenerInstancia();
        switch(vista.getCasoUso()){
            case "Registrar nuevo empleado":
                temp.setEmpleado(this.emp);
                temp.showRegistrarEmpleado();
                break;
            case "Recepcion en tienda fisica de pedido de suministro de un producto":
                temp.setEmpleado(this.emp);
                temp.showRecepcionPedido();
                break;
            case "Consultar disponibilidad de producto en tiendas fisicas":
                temp.setEmpleado(this.emp);
                temp.showDisponibilidad();
                break;
            default:
                vista.errorControlador("El caso de uso no esta implementado.");
                break;
        }
    }
}
