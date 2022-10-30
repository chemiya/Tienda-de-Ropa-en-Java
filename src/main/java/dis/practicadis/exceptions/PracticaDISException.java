/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.exceptions;


public class PracticaDISException extends Exception {
    public PracticaDISException() {
        super();
    }

    public PracticaDISException(String mensaje) {
        super(mensaje);
    }

    public PracticaDISException(String mensaje, Throwable causa) {
        super(mensaje, causa);
  }
}
