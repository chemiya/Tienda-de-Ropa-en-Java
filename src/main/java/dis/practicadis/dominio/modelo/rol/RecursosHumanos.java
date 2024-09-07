/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.rol;

import dis.practicadis.dominio.modelo.Rol;
import java.time.LocalDateTime;


public class RecursosHumanos extends Rol{
    private final String destinaciones = "Oficina";
    
    public RecursosHumanos(LocalDateTime fecha){
        super(fecha);
        this.addPrivilegio("Registrar nuevo empleado");
        this.addPrivilegio("Sacar empleado de ERTE");
    }
    
    @Override
    public int getOrdinal(){
        return 2;
    }
    
    public String getDestinaciones(){
        return destinaciones;
    }
}
