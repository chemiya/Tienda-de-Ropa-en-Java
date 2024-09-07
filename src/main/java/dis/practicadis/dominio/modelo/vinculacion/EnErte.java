/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.vinculacion;

import dis.practicadis.dominio.modelo.Vinculacion;
import java.time.LocalDateTime;


public class EnErte extends Vinculacion{
    public EnErte(LocalDateTime fecha){
        super(fecha, false);
    }
    
    public int getOrdinal(){
        return 3;
    }
}
