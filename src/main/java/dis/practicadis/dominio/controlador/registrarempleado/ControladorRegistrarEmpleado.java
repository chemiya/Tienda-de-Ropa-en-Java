/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.controlador.registrarempleado;

import dis.practicadis.persistencia.daos.EmpleadoDAO;
import dis.practicadis.persistencia.daos.TiendaDAO;
import dis.practicadis.persistencia.daos.OficinaDAO;
import dis.practicadis.persistencia.daos.AlmacenDAO;
import dis.practicadis.dominio.modelo.Destino;
import dis.practicadis.dominio.modelo.Empleado;
import dis.practicadis.dominio.modelo.FactoriaRol;
import dis.practicadis.dominio.modelo.Rol;
import dis.practicadis.dominio.modelo.destino.Tienda;
import dis.practicadis.dominio.modelo.destino.Oficina;
import dis.practicadis.dominio.modelo.destino.Almacen;
import dis.practicadis.exceptions.detailed.JSONException;
import dis.practicadis.persistencia.daos.RolDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class ControladorRegistrarEmpleado {

    public boolean doesUserExist(String nif) {
        return EmpleadoDAO.doesUserExist(nif);
    }

    public Destino getDestino(int id) throws JSONException {
        Destino resultado = null;

        ArrayList<Integer> tiendas = TiendaDAO.getIds();
        ArrayList<Integer> oficinas = OficinaDAO.getIds();
        ArrayList<Integer> almacenes = AlmacenDAO.getIds();

        if (tiendas.contains(id)) {
            resultado = new Tienda(TiendaDAO.read(id));
        } else if (oficinas.contains(id)) {
            resultado = new Oficina(OficinaDAO.read(id));
        } else if (almacenes.contains(id)) {
            resultado = new Almacen(AlmacenDAO.read(id));
        } else{}
        return resultado;
    }
    
    public boolean doesRolExist(String rol){
        boolean resultado = false;
        HashMap<String, Integer> temp = RolDAO.getRoles();
        if(temp.containsKey(rol)){
            resultado = true;
        }
        return resultado;
    }
    
    public Rol getRolFromString(String rol, LocalDateTime fecha){
        HashMap<String, Integer> temp = RolDAO.getRoles();
        return FactoriaRol.crearRolConOrdinal(temp.get(rol), fecha) ;
    }
    
    public void writeEmpleado(Empleado emp, int dest){
        EmpleadoDAO.write(emp, dest);
    }
}
