/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.exceptions.detailed;

import dis.practicadis.exceptions.PracticaDISException;


public class ConnectionDBException extends PracticaDISException {

    public ConnectionDBException() {
        super();
    }

    public ConnectionDBException(String mensaje) {
        super(mensaje);
    }

    public ConnectionDBException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
