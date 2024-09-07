/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.rol.*;
import java.time.LocalDateTime;


public class FactoriaRol {
    public static Rol crearRolConOrdinal (int num, LocalDateTime fecha) throws IllegalArgumentException{
        Rol resultado = null;
        switch(num){
            case 1:
                resultado = new GestorMarca(fecha);
                break;
            case 2:
                resultado = new RecursosHumanos(fecha);
                break;
            case 3:
                resultado = new EncargadoLogistica(fecha);
                break;
            case 4:
                resultado = new EncargadoAlmacen(fecha);
                break;
            case 5:
                resultado = new EncargadoTienda(fecha);
                break;
            case 6:
                resultado = new EmpleadoAlmacen(fecha);
                break;
            case 7:
                resultado = new EmpleadoTienda(fecha);
                break;
            default:
                throw new IllegalArgumentException("Ordinal was out of range. Range is from 1 to 7, ordinal was "+num+".");
        }
        return resultado;
    }
    
}
