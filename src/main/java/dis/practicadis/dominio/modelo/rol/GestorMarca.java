/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.rol;

import dis.practicadis.dominio.modelo.Rol;
import java.time.LocalDateTime;


public class GestorMarca extends Rol{
    private final String destinaciones = "Oficina";
    
    public GestorMarca(LocalDateTime fecha){
        super(fecha);
    }
    
    @Override
    public int getOrdinal(){
        return 1;
    }
    
    public String getDestinaciones(){
        return destinaciones;
    }
}