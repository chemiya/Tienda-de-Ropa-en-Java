/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.controlador.recepcionpedido;

import dis.practicadis.dominio.modelo.Destino;
import dis.practicadis.dominio.modelo.PedidoDeSuministro;
import dis.practicadis.dominio.modelo.Producto;
import dis.practicadis.dominio.modelo.RegistroEntregas;
import dis.practicadis.dominio.modelo.destino.Tienda;
import dis.practicadis.exceptions.detailed.JSONException;
import dis.practicadis.persistencia.daos.ExistenciasDAO;
import dis.practicadis.persistencia.daos.PedidoDAO;
import dis.practicadis.persistencia.daos.ProductoDAO;
import dis.practicadis.persistencia.daos.RegistroDAO;
import java.time.LocalDateTime;


public class ControladorRecepcionPedido {
    
    public PedidoDeSuministro getPedido(int id){
        PedidoDeSuministro result = null;
        if(PedidoDAO.doesPedidoExist(id)){
            try{
                result = new PedidoDeSuministro (PedidoDAO.read(id));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return result;
    }
    
    public void writeRegistro(LocalDateTime tiempo, String dni){
        RegistroEntregas registro = new RegistroEntregas(tiempo, dni);
        RegistroDAO.write(registro);
    }
    
    public void updateEstadoEntregado(int id){
        PedidoDAO.updateEstado(id, 6);
    }
    
    public void addExistencias(Destino t, Producto p, int cantidad){
        ExistenciasDAO.addExistencias(t.getID(), ProductoDAO.getProductId(p.getNombre()), cantidad);
    }
}
