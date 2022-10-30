/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz.vistacontrolador.recepcionpedido;

import dis.practicadis.dominio.controlador.recepcionpedido.ControladorRecepcionPedido;
import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.dominio.modelo.EstadosPedido;
import dis.practicadis.dominio.modelo.PedidoDeSuministro;
import dis.practicadis.interfaz.GestorDeInterfaz;
import java.time.LocalDateTime;

public class ControladorVistaRecepcionPedido {

    private final VistaRecepcionPedido vista;
    private final ControladorRecepcionPedido control;
    private PedidoDeSuministro pedido;
    private int pedidoID;
    private Empleado empleado;

    public ControladorVistaRecepcionPedido(VistaRecepcionPedido v) {
        this.vista = v;
        control = new ControladorRecepcionPedido();
        this.empleado = GestorDeInterfaz.obtenerInstancia().getEmpleado();
    }

    public void procesarEventoPedido() {
        this.pedidoID = Integer.parseInt(vista.getPedido());
        this.pedido = control.getPedido(Integer.parseInt(vista.getPedido()));
        if (pedido != null) {
            if (this.pedido.getEstado() == EstadosPedido.ENREPARTO) {
                if (this.pedido.getSolicitante().getID() == this.empleado.getDestino().getID()) {
                    vista.pedidoCorrecto(this.pedido);
                } else {
                    vista.errorControlador("El pedido no esta dirigido a su tienda.");
                    vista.cancelarCaso();
                }
            } else {
                vista.errorControlador("El pedido no esta en reparto.");
                vista.cancelarCaso();
            }
        } else {
            vista.errorControlador("El pedido no existe.");
            vista.cancelarCaso();
        }
    }
    
    public void procesarEventoCancelar(){
        this.pedido = null;
        this.pedidoID = -1;
        vista.cancelarCaso();
    }
    
    public void procesarEventoConfirmar(){
        this.control.writeRegistro(LocalDateTime.now(),"dni");
        this.control.updateEstadoEntregado(this.pedidoID);
        this.control.addExistencias(this.empleado.getDestino(), this.pedido.getProducto(), this.pedido.getCantidad());
        this.pedido = null;
        this.pedidoID = -1;
        vista.mensajeControlador("Se ha aceptado el pedido.");
        vista.cancelarCaso();
    }
    
    public void procesarEventoCancelarCompleto(){
        GestorDeInterfaz.obtenerInstancia().showIdentificacion();
    }
}
