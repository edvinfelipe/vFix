/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

/**
 *
 * @author nasc_
 */
public class DatosDetalle {
    private String codigoProducto;
    private int cantidad;
    private String descripcion;
    private float subtotal;
    private int facturaId;

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public int getFacturaId() {
        return facturaId;
    }
    
    
}
