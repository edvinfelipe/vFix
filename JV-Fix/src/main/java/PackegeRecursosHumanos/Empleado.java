/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackegeRecursosHumanos;

/**
 *
 * @author carlo
 */
public class Empleado {

        private String codigo;
        private String nombre;
        private boolean rol;
        private String imagen;
        private String usuario;
        private String contresenia;
    
    public Empleado(){
        
    }
    
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isRol() {
        return rol;
    }

    public String getImagen() {
        return imagen;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContresenia() {
        return contresenia;
    }
}
