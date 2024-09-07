/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

import dis.practicadis.dominio.modelo.vinculacion.*;
import java.time.LocalDateTime;


public class FactoriaVinculacion {
    public static Vinculacion crearVinculacionConOrdinal (int num, LocalDateTime fecha) throws IllegalArgumentException{
        Vinculacion resultado = null;
        switch(num){
            case 1:
                resultado = new Contratado(fecha);
                break;
            case 2:
                resultado = new Despedido(fecha);
                break;
            case 3:
                resultado = new EnErte(fecha);
                break;
            default:
                throw new IllegalArgumentException("Ordinal was out of range. Range is from 1 to 3, ordinal was "+num+".");
        }
        return resultado;
    }
    
}
