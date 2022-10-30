/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.controlador.comprobardisponibilidad;

import dis.practicadis.dominio.modelo.ExistenciaEnTienda;
import dis.practicadis.dominio.modelo.Producto;
import dis.practicadis.dominio.modelo.destino.Tienda;
import dis.practicadis.persistencia.daos.ExistenciasDAO;
import dis.practicadis.persistencia.daos.ProductoDAO;
import dis.practicadis.persistencia.daos.TiendaDAO;
import java.util.ArrayList;

public class ControladorComprobarDisponibilidad {

    public boolean doesProductExist(String nombre) {
        return ProductoDAO.doesProductExist(nombre);
    }

    public boolean isProductInStock(String nombre) {
        return ExistenciasDAO.isProductInStock(ProductoDAO.getProductId(nombre));
    }

    public Producto getProducto(String nombre) {
        Producto prod = null;
        try {
            prod = new Producto(ProductoDAO.read(ProductoDAO.getProductId(nombre)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prod;
    }

    public ArrayList<ExistenciaEnTienda> getConExistencias(String nombre) {
        ArrayList<ExistenciaEnTienda> resultado = null;
        try {
            resultado = ExistenciaEnTienda.arrayDeExistencias(ExistenciasDAO.getExistenciasFromProducto(ProductoDAO.getProductId(nombre)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ArrayList<Tienda> getTiendaConExistencias(String nombre) {
        ArrayList<Tienda> resultado = new ArrayList<>();
        ArrayList<ExistenciaEnTienda> existencias = getConExistencias(nombre);
        for (ExistenciaEnTienda ex : existencias) {
            resultado.add(ex.getTienda());
        }
        return resultado;
    }

    public ArrayList<Tienda> getTiendaSinExistencias(String nombre) {
        ArrayList<Tienda> todas = new ArrayList<>();
        try {
            for (int id : TiendaDAO.getIds()) {
                todas.add(new Tienda(TiendaDAO.read(id)));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<Tienda> conExistencias = this.getTiendaConExistencias(nombre);
        ArrayList<Tienda> sinExistencias = new ArrayList();
        for(Tienda tien1: todas){
            boolean flag = true;
            for(Tienda tien2: conExistencias){
                if(tien1.getNombreIdentificativo().equals(tien2.getNombreIdentificativo())){
                    flag = false;
                }
            }
            if(flag){
                sinExistencias.add(tien1);
            }
        }
        return sinExistencias;
    }
    
    public ArrayList<ExistenciaEnTienda> getSinExistencias(String nombre) {
        ArrayList<Tienda> tiendas = this.getTiendaSinExistencias(nombre);
        ArrayList<ExistenciaEnTienda> resultado = new ArrayList<>();
        for(Tienda tien: tiendas){
            resultado.add(new ExistenciaEnTienda(0, this.getProducto(nombre), tien));
        }
        return resultado;
    }

}
