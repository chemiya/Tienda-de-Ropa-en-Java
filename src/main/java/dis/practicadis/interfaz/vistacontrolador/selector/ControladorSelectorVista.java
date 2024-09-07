/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz.vistacontrolador.selector;

import dis.practicadis.interfaz.vistacontrolador.comprobardisponibilidad.VistaComprobarDisponibilidad;
import dis.practicadis.interfaz.vistacontrolador.identificacion.VistaIdentificacion;
import dis.practicadis.interfaz.vistacontrolador.registrarempleado.VistaRegistrarEmpleado;


public class ControladorSelectorVista {
    private final SelectorVista vista;
    
    public ControladorSelectorVista(SelectorVista v) {
        this.vista = v;
    }
    
    public void crearCaso1(){
        new VistaIdentificacion().setVisible(true);
        vista.dispose();
    }
    
    public void crearCaso2(){
        new VistaComprobarDisponibilidad().setVisible(true);
        vista.dispose();
    }
    
    public void crearCaso3(){
        new VistaRegistrarEmpleado().setVisible(true);
        vista.dispose();
    }
    
    public void crearCaso4(){
        vista.errorControlador("El caso aun no se encuentra implementado");
    }
}
