/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;


import java.io.File;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

/**
 *
 * @author carlo
 */
public class AgregarImagen  extends DecoradorPeticion{
    
    private String rutaImagen;
    
    public AgregarImagen(Peticion decoradorPeticion,String rutaimagen) {
        super(decoradorPeticion);
        this.rutaImagen=rutaimagen;
    }
    
    @Override
    public MultipartEntityBuilder getPeticion(){
        
        File fileToUse = new File(rutaImagen);
        FileBody data = new FileBody(fileToUse);
    
        return decoradorPeticion.getPeticion().addPart("imagen", data);
    }
}
