/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.rol;

import dis.practicadis.dominio.modelo.Rol;
import java.time.LocalDateTime;


public class EncargadoLogistica extends Rol{
    private final String destinaciones = "Oficina";
    
    public EncargadoLogistica(LocalDateTime fecha){
        super(fecha);
        this.addPrivilegio("Consultar disponibilidad de producto en tiendas fisicas");
        this.addPrivilegio("Enviar productos a almacen");
    }
    
    @Override
    public int getOrdinal(){
        return 3;
    }
    
    public String getDestinaciones(){
        return destinaciones;
    }
}
