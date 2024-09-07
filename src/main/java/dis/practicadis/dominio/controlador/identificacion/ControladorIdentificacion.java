/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.controlador.identificacion;

import dis.practicadis.dominio.modelo.Disponibilidad;
import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.dominio.modelo.Rol;
import dis.practicadis.dominio.modelo.Vinculacion;
import dis.practicadis.exceptions.detailed.JSONException;
import dis.practicadis.persistencia.daos.EmpleadoDAO;
import java.util.ArrayList;
import java.util.Collections;


public class ControladorIdentificacion {

    public boolean doesUserExist(String nif) {
        return EmpleadoDAO.doesUserExist(nif);
    }

    public Empleado getEmpleado(String nif, String contra) {
        String json = EmpleadoDAO.getFromUsuarioYContra(nif, contra);
        Empleado emp = null;
        if(json != null){
            try{
                emp = new Empleado(json);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return emp;
    }

}
