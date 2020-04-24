/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author nasc_
 */
public class CrearEntidad {
    
    public MultipartEntityBuilder EntidadFactura (float total, float descuento, boolean venta, boolean garantia, boolean efectivo, boolean tarjeta, LocalDateTime fecha, int clienteId){
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            
            multipartEntityBuilder.addPart("total", new StringBody(String.valueOf(total)));
            multipartEntityBuilder.addPart("descuento", new StringBody(String.valueOf(descuento)));
            multipartEntityBuilder.addPart("venta", new StringBody(String.valueOf(false)));
            multipartEntityBuilder.addPart("garantia", new StringBody(String.valueOf(garantia)));
            multipartEntityBuilder.addPart("efectivo", new StringBody(String.valueOf(efectivo)));
            multipartEntityBuilder.addPart("tarjeta", new StringBody(String.valueOf(tarjeta)));
            multipartEntityBuilder.addPart("fecha", new StringBody(String.valueOf(fecha)));
            multipartEntityBuilder.addPart("clienteId", new StringBody(String.valueOf(clienteId)));
            return multipartEntityBuilder;
        } catch (UnsupportedEncodingException ex) {
            return null;
        } 
    }
    public MultipartEntityBuilder EntidadDetalle (String codigoProducto, float cantidad, String descripcion, float subtotal, int facturaId){
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            
            multipartEntityBuilder.addPart("codigoProducto", new StringBody(codigoProducto));
            multipartEntityBuilder.addPart("cantidad", new StringBody(String.valueOf(cantidad)));
            multipartEntityBuilder.addPart("descripcion", new StringBody(descripcion));
            multipartEntityBuilder.addPart("subtotal", new StringBody(String.valueOf(subtotal)));
            multipartEntityBuilder.addPart("facturaId", new StringBody(String.valueOf(facturaId)));
            return multipartEntityBuilder;
        } catch (UnsupportedEncodingException ex) {
            return null;
        } 
    }
    public MultipartEntityBuilder Entidadproducto (DatosProducto producto, float existencias){
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            
            multipartEntityBuilder.addPart("codigo", new StringBody(producto.getCodigo()));
            multipartEntityBuilder.addPart("nombre", new StringBody(producto.getNombre()));
            multipartEntityBuilder.addPart("modelo", new StringBody(producto.getModelo()));
            multipartEntityBuilder.addPart("tipo", new StringBody(producto.getTipo()));
            multipartEntityBuilder.addPart("descripcion", new StringBody(producto.getDescripcion()));
            multipartEntityBuilder.addPart("existencia", new StringBody(String.valueOf(existencias)));
            multipartEntityBuilder.addPart("precio", new StringBody(String.valueOf(producto.getPrecio())));
            multipartEntityBuilder.addPart("precioMayorista", new StringBody(String.valueOf(producto.getPrecioMayorista())));
            multipartEntityBuilder.addPart("precioCliente", new StringBody(String.valueOf(producto.getPrecioCliente())));
            multipartEntityBuilder.addPart("colorId", new StringBody(String.valueOf(producto.getColorId())));
            multipartEntityBuilder.addPart("marcaId", new StringBody(String.valueOf(producto.getMarcaId())));
            multipartEntityBuilder.addPart("categoriaId", new StringBody(String.valueOf(producto.getCategoriaId())));
            //multipartEntityBuilder.addPart("descripcion", new StringBody(descripcion));
            return multipartEntityBuilder;
        } catch (UnsupportedEncodingException ex) {
            return null;
        } 
    }
    
}
