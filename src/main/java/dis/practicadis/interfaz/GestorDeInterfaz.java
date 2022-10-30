/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz;

import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.interfaz.vistacontrolador.comprobardisponibilidad.VistaComprobarDisponibilidad;
import dis.practicadis.interfaz.vistacontrolador.identificacion.VistaIdentificacion;
import dis.practicadis.interfaz.vistacontrolador.recepcionpedido.VistaRecepcionPedido;
import dis.practicadis.interfaz.vistacontrolador.registrarempleado.VistaRegistrarEmpleado;
import javax.swing.JFrame;

public class GestorDeInterfaz {

    private JFrame frameActual;
    private Empleado empleado;
    private static GestorDeInterfaz instancia = null;

    public GestorDeInterfaz() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestorDeInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorDeInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorDeInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestorDeInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
    }

    public static GestorDeInterfaz obtenerInstancia() {
        if (instancia == null) {
            instancia = new GestorDeInterfaz();
        }
        return instancia;
    }

    public void setEmpleado(Empleado emp) {
        this.empleado = emp;
    }

    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void showIdentificacion() {
        this.empleado = null;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (frameActual != null) {
                    frameActual.setVisible(false);
                    frameActual.dispose();
                }
                frameActual = new VistaIdentificacion();
                frameActual.setVisible(true);
            }
        });
    }

    public void showDisponibilidad() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (frameActual != null) {
                    frameActual.setVisible(false);
                    frameActual.dispose();
                }
                frameActual = new VistaComprobarDisponibilidad();
                frameActual.setVisible(true);
            }
        });
    }

    public void showRegistrarEmpleado() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (frameActual != null) {
                    frameActual.setVisible(false);
                    frameActual.dispose();
                }
                frameActual = new VistaRegistrarEmpleado();
                frameActual.setVisible(true);
            }
        });
    }

    public void showRecepcionPedido() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (frameActual != null) {
                    frameActual.setVisible(false);
                    frameActual.dispose();
                }
                frameActual = new VistaRecepcionPedido();
                frameActual.setVisible(true);
            }
        });
    }

}
