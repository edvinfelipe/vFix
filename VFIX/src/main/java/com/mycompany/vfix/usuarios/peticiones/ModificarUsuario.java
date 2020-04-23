/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios.peticiones;

import com.mycompany.vfix.usuarios.peticiones.Peticion;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author carlo
 */
public class ModificarUsuario implements Peticion{

    private String nombre;
    private boolean rol;

    public ModificarUsuario(String nombre, boolean rol) {
        this.nombre = nombre;
        this.rol = rol;
    }
    
    @Override
    public MultipartEntityBuilder getPeticion() {
        
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addPart("nombre", new StringBody(nombre));
            multipartEntityBuilder.addPart("rol", new StringBody(String.valueOf(rol)));
            return multipartEntityBuilder;
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
}
