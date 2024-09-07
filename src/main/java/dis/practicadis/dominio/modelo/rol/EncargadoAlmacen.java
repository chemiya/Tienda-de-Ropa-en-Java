/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.rol;

import dis.practicadis.dominio.modelo.Rol;
import java.time.LocalDateTime;


public class EncargadoAlmacen extends Rol{
    private final String destinaciones = "Almacen";
    
    public EncargadoAlmacen(LocalDateTime fecha){
        super(fecha);
    }
    
    @Override
    public int getOrdinal(){
        return 4;
    }
    
    public String getDestinaciones(){
        return destinaciones;
    }
}
