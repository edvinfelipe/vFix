/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;

import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 *
 * @author carlo
 */
public interface Peticion {
    
    public MultipartEntityBuilder getPeticion();
    
}
