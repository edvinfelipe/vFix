/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.clientes;

/**
 *
 * @author igeni
 */
public class Cliente {
    //Atributos del cliente
    private int id;
    private String nombre;
    private String telefono;
    private String cumpleanios;
    private int estrellas;
    private String correo;
    private String nit;
    private boolean eliminado;
    private String direccion;
    //Obtener id del cliente
    public int getId()
    {
        return id;
    }
    //Asignar id al cliente
    public void setId(int id)
    {
        this.id = id;
    }
    //Obtener nombre del cliente
    public String getNombre()
    {
        return nombre;
    }
    //Asignar nombre al cliente
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    //Obtener teléfono del cliente
    public String getTelefono()
    {
        return telefono;
    }
    //Asignar teléfono al cliente
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }
    //Obtener fecha de nacimiento del cliente
    public String getCumpleanios()
    {
        return cumpleanios;
    }
    //Asignar fecha de nacimiento al cliente
    public void setCumpleanios(String cumpleanios)
    {
        this.cumpleanios = cumpleanios;
    }
    //Obtener estrellas del cliente
    public int getEstrellas()
    {
        return estrellas;
    }
    //Asignar estrellas al cliente
    public void setEstrellas(int estrellas)
    {
        this.estrellas = estrellas;
    }
    //Obtener correo del cliente
    public String getCorreo()
    {
        return correo;
    }
    //Asignar correo al cliente
    public void setCorreo(String correo)
    {
        this.correo = correo;
    }
    //Obtener nit del cliente
    public void setNit(String nit)
    {
        this.nit = nit;
    }
    //Asignar nit al cliente
    public String getNit()
    {
        return nit;
    }
    //Asignar booleano eliminado del cliente
    public void setEliminado(boolean eliminado)
    {
        this.eliminado = eliminado;
    }
    //Obtener booleano eliminado del cliente
    public boolean getEliminado()
    {
        return eliminado;
    }
    //Obtener dirección del cliente
    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }
    //Asignar dirección del cliente
    public String getDireccion()
    {
        return direccion;
    }
}
