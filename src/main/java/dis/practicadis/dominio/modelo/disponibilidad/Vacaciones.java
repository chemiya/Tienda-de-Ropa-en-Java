/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo.disponibilidad;

import dis.practicadis.dominio.modelo.Disponibilidad;
import java.time.LocalDateTime;


public class Vacaciones extends Disponibilidad {
    public Vacaciones(LocalDateTime fechaini, LocalDateTime fechafin) {
        super(fechaini, fechafin, false);
    }

    public int getOrdinal() {
        return 1;
    }
}
