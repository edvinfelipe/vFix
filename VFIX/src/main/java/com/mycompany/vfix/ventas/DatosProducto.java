/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

import java.util.ArrayList;

/**
 *
 * @author nasc_
 */
public class DatosProducto {
    
    private String codigo;
    private String nombre;
    private String modelo;
    private String tipo;
    private String descripcion;
    private int existencia;
    private float precio;
    private float precioMayorista;
    private float precioCliente;
    private String colorId;
    private String marcaId;
    private String categoriaId;
    private ArrayList imagenes;

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getExistencia() {
        return existencia;
    }

    public float getPrecio() {
        return precio;
    }

    public float getPrecioMayorista() {
        return precioMayorista;
    }

    public float getPrecioCliente() {
        return precioCliente;
    }

    public String getColorId() {
        return colorId;
    }

    public String getMarcaId() {
        return marcaId;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

   

    public ArrayList getImagenes() {
        return imagenes;
    }

    
}
