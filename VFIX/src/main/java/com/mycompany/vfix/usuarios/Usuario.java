/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 *
 * @author carlo
 */
public class Usuario {
    
    private final String url = "";

    public Usuario() {
    }
    /**
     * 
     * 
     * @param nombre
     * @param usuario
     * @param contrasenia
     * @param rutaImagen
     * @param rol 
     */
    public void insertarEmpleado(String nombre, String usuario, String contrasenia,String rutaImagen, boolean rol){
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder mulpipartBuilder = MultipartEntityBuilder.create();
        File fileToUse = new File(rutaImagen);
        FileBody data = new FileBody(fileToUse);
        
        try {
            mulpipartBuilder.addPart("nombre", new StringBody(nombre));
            mulpipartBuilder.addPart("rol", new StringBody(String.valueOf(rol)));
            mulpipartBuilder.addPart("imagen", data);
            mulpipartBuilder.addPart("usuario",new StringBody(usuario));
            mulpipartBuilder.addPart("contrasenia",new StringBody(contrasenia));
            httpPost.setEntity(mulpipartBuilder.build());
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println("Codigo: "+httpResponse.getStatusLine().getStatusCode());
        
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void modificarEmpleado(String nombre, String imagen, boolean rol){
        
         CloseableHttpClient httpClient = HttpClients.createDefault();
         HttpPut httpPut = new HttpPut(url);
         MultipartEntityBuilder mulpipartBuilder = MultipartEntityBuilder.create();
         
        try {
            mulpipartBuilder.addPart("nombre", new StringBody(nombre));
            mulpipartBuilder.addPart("rol", new StringBody(nombre));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        
        
    }
    
    
}
