/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz.vistacontrolador.comprobardisponibilidad;

import dis.practicadis.dominio.controlador.comprobardisponibilidad.ControladorComprobarDisponibilidad;
import dis.practicadis.dominio.modelo.Direccion;
import dis.practicadis.dominio.modelo.ExistenciaEnTienda;
import dis.practicadis.dominio.modelo.Producto;
import dis.practicadis.dominio.modelo.destino.Tienda;
import dis.practicadis.interfaz.GestorDeInterfaz;
import java.util.ArrayList;

public class ControladorVistaComprobarDisponibilidad {

    private final VistaComprobarDisponibilidad vista;
    private final ControladorComprobarDisponibilidad control;
    private ArrayList<ExistenciaEnTienda> existencia;
    private ArrayList<ExistenciaEnTienda> noExistencia;
    private Producto producto;
    private ExistenciaEnTienda exist;
    //En un sistema completo esta se extraeria de otra clase
    private int reglaDeNegocio = 4;

    public ControladorVistaComprobarDisponibilidad(VistaComprobarDisponibilidad v) {
        this.vista = v;
        control = new ControladorComprobarDisponibilidad();
    }

    public void procesarEventoProducto() {
        String prod = vista.getBusqueda();
        if (control.doesProductExist(prod)) {
            this.existencia = control.getConExistencias(prod);
            if (!this.existencia.isEmpty()) {                
                this.noExistencia = control.getSinExistencias(prod);
                this.producto = control.getProducto(prod);
                ArrayList<String> conExistencias = new ArrayList<>();
                for (ExistenciaEnTienda ex : this.existencia) {
                    conExistencias.add(ex.getTienda().getNombreIdentificativo());
                }
                ArrayList<String> sinExistencias = new ArrayList<>();
                for (ExistenciaEnTienda ex : this.noExistencia) {
                    sinExistencias.add(ex.getTienda().getNombreIdentificativo());
                }
                vista.productoCorrecto(conExistencias, sinExistencias);
            } else {
                vista.errorControlador("El producto no se encuentra en stock en ninguna tienda.");
            }
        } else {
            vista.errorControlador("El producto es inexistente.");
        }
    }

    public String getNombreProducto() {
        if (this.producto != null) {
            return this.producto.getNombre();
        } else {
            return null;
        }
    }

    public String getPrecioProducto() {
        if (this.producto != null) {
            return Double.toString(this.producto.getPrecio());
        } else {
            return null;
        }
    }

    public String getCategoriaProducto() {
        if (this.producto != null) {
            return this.producto.getCategoria().getNombre();
        } else {
            return null;
        }
    }

    public String getNombreTienda() {
        if (this.exist != null) {
            return this.exist.getTienda().getNombreIdentificativo();
        } else {
            return null;
        }
    }

    public String getDireccionTienda() {
        if (this.exist != null) {
            Direccion temp = this.exist.getTienda().getDireccion();
            String resultado = ("C/" + temp.getCalle() + " " + temp.getNumero() + " " + temp.getOtros());
            return resultado;
        } else {
            return null;
        }
    }

    public String getTelefonoTienda() {
        if (this.exist != null) {
            return this.exist.getTienda().getTelefono();
        } else {
            return null;
        }
    }

    public String getCantidadTienda() {
        if (this.exist != null) {
            return Integer.toString(this.exist.getCantidad());
        } else {
            return null;
        }
    }
    
    public int getPocasUnidades(){
        return this.reglaDeNegocio;
    }

    public void seleccionCon() {
        if (this.existencia != null) {
            if (vista.getListaConValue() != null) {
                for (ExistenciaEnTienda ex : existencia) {
                if (vista.getListaConValue().equals(ex.getTienda().getNombreIdentificativo())) {
                    this.exist = ex;
                }
            }
            vista.mostrarTienda();
            } else{
                vista.ocultarTienda();
            }          
        }
    }

    public void seleccionSin() {
        vista.ocultarTienda();
    }

    public void manejarRegreso() {
        this.existencia = null;
        this.noExistencia = null;
        this.producto = null;
        this.exist = null;
        vista.regresoCorrecto();
    }

    public void cancelarCaso() {
        GestorDeInterfaz.obtenerInstancia().showIdentificacion();
    }
}
