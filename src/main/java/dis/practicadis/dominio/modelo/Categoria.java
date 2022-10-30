/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.practicadis.dominio.modelo;


public class Categoria {
    private String nombre;
    
    public Categoria(String nom){
        this.nombre = nom;
    }
    
    public void setNombre(String nom){
        this.nombre = nom;
    }
    public String getNombre(){
        return this.nombre;
    }
    
    
}
