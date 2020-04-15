/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author carlo
 */
public class InsertarUsuario implements Peticion{

    private String nombre;
    private String usuario;
    private String contrasenia;
    private boolean rol;

    public InsertarUsuario(String nombre, String usuario, String contrasenia, boolean rol) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }
    
    @Override
    public MultipartEntityBuilder getPeticion() {
        
        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            
            multipartEntityBuilder.addPart("nombre", new StringBody(nombre));
            multipartEntityBuilder.addPart("rol", new StringBody(String.valueOf(rol)));
            multipartEntityBuilder.addPart("usuario", new StringBody(usuario));
            multipartEntityBuilder.addPart("contrasenia", new StringBody(contrasenia));
            return multipartEntityBuilder;
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
    
}
