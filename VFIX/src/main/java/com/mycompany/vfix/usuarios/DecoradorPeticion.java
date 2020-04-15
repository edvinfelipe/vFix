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
public abstract class DecoradorPeticion implements Peticion{
    
    protected Peticion decoradorPeticion;

    public DecoradorPeticion(Peticion decoradorPeticion) {
        this.decoradorPeticion = decoradorPeticion;
    }
    
    @Override
    public MultipartEntityBuilder getPeticion(){
       return decoradorPeticion.getPeticion();
        
    }
    
}
