/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios.peticiones;

import com.mycompany.vfix.usuarios.AgregarImagen;
import com.mycompany.vfix.usuarios.peticiones.Peticion;

/**
 *
 * @author carlo
 */
public class FabricaPeticion {

    public FabricaPeticion() {
    }
    
    public Peticion getPeticion(Peticion peticion,String rutaImagen){
        
        if(!rutaImagen.isEmpty()){
            return new AgregarImagen(peticion, rutaImagen);
        }else{
            return peticion;
        }        
    }
    
}
