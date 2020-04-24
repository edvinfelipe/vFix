/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

import java.time.LocalDateTime;


/**
 *
 * @author nasc_
 */
public class DatosFactura {
    private int id;
    private float total;
    private float descuento;
    private boolean venta;
    private boolean garantia;
    private boolean efectivo;
    private boolean tarjeta;
    private String fecha;
    private int clienteId;

    public int getId() {
        return id;
    }

    public float getTotal() {
        return total;
    }

    public float getDescuento() {
        return descuento;
    }

    public boolean isVenta() {
        return venta;
    }

    public boolean isGarantia() {
        return garantia;
    }

    public boolean isEfectivo() {
        return efectivo;
    }

    public boolean isTarjeta() {
        return tarjeta;
    }

    public String getFecha() {
        return fecha;
    }

    public int getClienteId() {
        return clienteId;
    }
    
}
