/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

import java.util.Date;

/**
 *
 * @author nasc_
 */
public class DatosCliente {
    
    private int id;
    private String nombre;
    private String telefono;
    private String nit;
    private String direccion;
    private Date cumpleanios;
    private int estrellas;
    private String correo;

    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public Date getCumpleanios() {
        return cumpleanios;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public String getCorreo() {
        return correo;
    }
    
    
}
