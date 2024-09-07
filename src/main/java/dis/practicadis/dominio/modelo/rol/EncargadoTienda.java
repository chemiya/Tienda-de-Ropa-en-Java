/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.rol;

import dis.practicadis.dominio.modelo.Rol;
import java.time.LocalDateTime;


public class EncargadoTienda extends Rol{
    private final String destinaciones = "Tienda";
    
    public EncargadoTienda(LocalDateTime fecha){
        super(fecha);
        this.addPrivilegio("Recepcion en tienda fisica de pedido de suministro de un producto");
        this.addPrivilegio("Realizar pedido de suministro");
    }
    
    @Override
    public int getOrdinal(){
        return 5;
    }
    
    public String getDestinaciones(){
        return destinaciones;
    }
}
