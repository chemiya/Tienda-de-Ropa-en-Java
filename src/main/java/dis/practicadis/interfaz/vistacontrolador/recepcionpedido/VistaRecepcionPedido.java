/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.interfaz.vistacontrolador.recepcionpedido;

import dis.practicadis.dominio.modelo.PedidoDeSuministro;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VistaRecepcionPedido extends javax.swing.JFrame {
    private ControladorVistaRecepcionPedido controlador;


    /**
     * Creates new form VistaRecepcionPedido
     */
    public VistaRecepcionPedido() {
        initComponents();
        controlador = new ControladorVistaRecepcionPedido(this);
        setResizable(false);
        jPanel_busqueda.setVisible(true);
        jPanel_confirmacion.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_superior = new javax.swing.JPanel();
        jSeparator = new javax.swing.JSeparator();
        jLabel_manzana = new javax.swing.JLabel();
        jLabel_blue = new javax.swing.JLabel();
        jPanel_inferior = new javax.swing.JPanel();
        jPanel_busqueda = new javax.swing.JPanel();
        jLabel_busquedapedido = new javax.swing.JLabel();
        jTextField_busqueda = new javax.swing.JTextField();
        jLabel_pedido = new javax.swing.JLabel();
        jButton_cancelar = new javax.swing.JButton();
        jButton_aceptar = new javax.swing.JButton();
        jPanel_confirmacion = new javax.swing.JPanel();
        jLabel_confirmacion = new javax.swing.JLabel();
        jButton_cancelarconfirmacion = new javax.swing.JButton();
        jButton_confirmar = new javax.swing.JButton();
        jButton_reclamacion = new javax.swing.JButton();
        jLabel_cantidad = new javax.swing.JLabel();
        jLabel_cantidadvalue = new javax.swing.JLabel();
        jLabel_producto = new javax.swing.JLabel();
        jLabel_productovalue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jSeparator.setBackground(new java.awt.Color(50, 85, 150));

        jLabel_manzana.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel_manzana.setForeground(new java.awt.Color(50, 85, 150));
        jLabel_manzana.setText("Manzana");

        jLabel_blue.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel_blue.setForeground(new java.awt.Color(195, 195, 195));
        jLabel_blue.setText("BLUE");

        javax.swing.GroupLayout jPanel_superiorLayout = new javax.swing.GroupLayout(jPanel_superior);
        jPanel_superior.setLayout(jPanel_superiorLayout);
        jPanel_superiorLayout.setHorizontalGroup(
            jPanel_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel_superiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_blue, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_manzana)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_superiorLayout.setVerticalGroup(
            jPanel_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_superiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_superiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_blue)
                    .addComponent(jLabel_manzana))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_inferior.setLayout(new java.awt.CardLayout());

        jLabel_busquedapedido.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_busquedapedido.setText("Busqueda de pedido");

        jTextField_busqueda.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N

        jLabel_pedido.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel_pedido.setText("Pedido:");

        jButton_cancelar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_cancelar.setText("Cancelar");
        jButton_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelarActionPerformed(evt);
            }
        });

        jButton_aceptar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_aceptar.setText("Aceptar");
        jButton_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_busquedaLayout = new javax.swing.GroupLayout(jPanel_busqueda);
        jPanel_busqueda.setLayout(jPanel_busquedaLayout);
        jPanel_busquedaLayout.setHorizontalGroup(
            jPanel_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_busquedaLayout.createSequentialGroup()
                .addGroup(jPanel_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_busquedaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_busquedapedido))
                    .addGroup(jPanel_busquedaLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_pedido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_busquedaLayout.setVerticalGroup(
            jPanel_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_busquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_busquedapedido)
                .addGap(18, 18, 18)
                .addComponent(jLabel_pedido)
                .addGap(18, 18, 18)
                .addComponent(jTextField_busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jPanel_inferior.add(jPanel_busqueda, "card2");

        jLabel_confirmacion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel_confirmacion.setText("Confirmacion de pedido");

        jButton_cancelarconfirmacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_cancelarconfirmacion.setText("Cancelar");
        jButton_cancelarconfirmacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelarconfirmacionActionPerformed(evt);
            }
        });

        jButton_confirmar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_confirmar.setText("Confirmar");
        jButton_confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmarActionPerformed(evt);
            }
        });

        jButton_reclamacion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_reclamacion.setText("Reclamar");
        jButton_reclamacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_reclamacionActionPerformed(evt);
            }
        });

        jLabel_cantidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel_cantidad.setText("Cantidad:");

        jLabel_cantidadvalue.setText("temp");

        jLabel_producto.setText("Producto:");

        jLabel_productovalue.setText("temp");

        javax.swing.GroupLayout jPanel_confirmacionLayout = new javax.swing.GroupLayout(jPanel_confirmacion);
        jPanel_confirmacion.setLayout(jPanel_confirmacionLayout);
        jPanel_confirmacionLayout.setHorizontalGroup(
            jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_confirmacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_confirmacionLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_reclamacion, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_cancelarconfirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_confirmacionLayout.createSequentialGroup()
                        .addGroup(jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_confirmacion)
                            .addGroup(jPanel_confirmacionLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_confirmacionLayout.createSequentialGroup()
                                        .addComponent(jLabel_cantidad)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel_cantidadvalue))
                                    .addGroup(jPanel_confirmacionLayout.createSequentialGroup()
                                        .addComponent(jLabel_producto)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel_productovalue)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel_confirmacionLayout.setVerticalGroup(
            jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_confirmacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_confirmacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_cantidad)
                    .addComponent(jLabel_cantidadvalue))
                .addGap(38, 38, 38)
                .addGroup(jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_producto)
                    .addComponent(jLabel_productovalue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                .addGroup(jPanel_confirmacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_cancelarconfirmacion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_reclamacion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel_inferior.add(jPanel_confirmacion, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_superior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel_inferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_superior, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_inferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelarActionPerformed
        controlador.procesarEventoCancelarCompleto();
    }//GEN-LAST:event_jButton_cancelarActionPerformed

    private void jButton_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aceptarActionPerformed
        if (jTextField_busqueda.getText().isEmpty()) {
            errorControlador("El campo de busqueda esta vacío");
        } else {
            if (jTextField_busqueda.getText().matches("[0-9]*")){
                controlador.procesarEventoPedido();
            } else{
                errorControlador("El campo de busqueda no es un numero");
            }
        }
    }//GEN-LAST:event_jButton_aceptarActionPerformed

    private void jButton_cancelarconfirmacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelarconfirmacionActionPerformed
        controlador.procesarEventoCancelar();
    }//GEN-LAST:event_jButton_cancelarconfirmacionActionPerformed

    private void jButton_confirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmarActionPerformed
        controlador.procesarEventoConfirmar();
    }//GEN-LAST:event_jButton_confirmarActionPerformed

    private void jButton_reclamacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_reclamacionActionPerformed
        errorControlador("No implementado");
    }//GEN-LAST:event_jButton_reclamacionActionPerformed

    public void pedidoCorrecto(PedidoDeSuministro p) {
        jPanel_busqueda.setVisible(false);
        jPanel_confirmacion.setVisible(true);
        jTextField_busqueda.setText("");
        jLabel_cantidadvalue.setText(Integer.toString(p.getCantidad()));
        jLabel_productovalue.setText(p.getProducto().getNombre());
    }
    
    public void cancelarCaso() {
        jPanel_busqueda.setVisible(true);
        jPanel_confirmacion.setVisible(false);
        jTextField_busqueda.setText("");
        jLabel_cantidadvalue.setText("");
        jLabel_productovalue.setText("");
    }
    
    public void errorControlador(String mensaje) {
        JOptionPane.showMessageDialog(new JFrame(), mensaje, "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
    }
    public void mensajeControlador(String mensaje) {
        JOptionPane.showMessageDialog(new JFrame(), mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public String getPedido() {
        return jTextField_busqueda.getText();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(VistaRecepcionPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaRecepcionPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaRecepcionPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaRecepcionPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaRecepcionPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_aceptar;
    private javax.swing.JButton jButton_cancelar;
    private javax.swing.JButton jButton_cancelarconfirmacion;
    private javax.swing.JButton jButton_confirmar;
    private javax.swing.JButton jButton_reclamacion;
    private javax.swing.JLabel jLabel_blue;
    private javax.swing.JLabel jLabel_busquedapedido;
    private javax.swing.JLabel jLabel_cantidad;
    private javax.swing.JLabel jLabel_cantidadvalue;
    private javax.swing.JLabel jLabel_confirmacion;
    private javax.swing.JLabel jLabel_manzana;
    private javax.swing.JLabel jLabel_pedido;
    private javax.swing.JLabel jLabel_producto;
    private javax.swing.JLabel jLabel_productovalue;
    private javax.swing.JPanel jPanel_busqueda;
    private javax.swing.JPanel jPanel_confirmacion;
    private javax.swing.JPanel jPanel_inferior;
    private javax.swing.JPanel jPanel_superior;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JTextField jTextField_busqueda;
    // End of variables declaration//GEN-END:variables
}
