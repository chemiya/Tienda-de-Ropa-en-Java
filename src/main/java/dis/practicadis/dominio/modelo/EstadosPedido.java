/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;

public enum EstadosPedido {
    PENDIENTE,
    CONFIRMADO,
    PREPARADO,
    ENVIADO,
    ENREPARTO,
    ENTREGADO,
    RECLAMADO;
    
    private final int ordinal;
    
    private EstadosPedido(){
        this.ordinal = this.ordinal()+1;
    }

    public static EstadosPedido getEstado(int est) {
        switch(est){
            case 1:
                return PENDIENTE;
            case 2:
                return CONFIRMADO;
            case 3:
                return PREPARADO;
            case 4:
                return ENVIADO;
            case 5:
                return ENREPARTO;
            case 6:
                return ENTREGADO;
            case 7:
                return RECLAMADO;
            default:
                return null;
        }
    }
    
    public int getOrdinal(){
        return this.ordinal;
    }
}
