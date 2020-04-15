/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;

/**
 *
 * @author carlo
 */
public class DatosEmpleado {
    
    private int id;
    private String nombre;
    private boolean rol;
    private String imagen;
    private String usuario;
    private String contrasenia;

    public int getId() {
        return id;
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

    public String getContrasenia() {
        return contrasenia;
    }
    
}
