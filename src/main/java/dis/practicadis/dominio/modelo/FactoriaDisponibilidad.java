/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.disponibilidad.*;
import java.time.LocalDateTime;


public class FactoriaDisponibilidad {
    public static Disponibilidad crearDisponibilidadConOrdinal (int num, LocalDateTime fecha1, LocalDateTime fecha2) throws IllegalArgumentException{
        Disponibilidad resultado = null;
        switch(num){
            case 1:
                resultado = new Vacaciones(fecha1, fecha2);
                break;
            case 2:
                resultado = new BajaTemporal(fecha1, fecha2);
                break;
            case 3:
                resultado = new Trabajando(fecha1);
                break;
            default:
                throw new IllegalArgumentException("Ordinal was out of range. Range is from 1 to 3, ordinal was "+num+".");
        }
        return resultado;
    }
    
}
