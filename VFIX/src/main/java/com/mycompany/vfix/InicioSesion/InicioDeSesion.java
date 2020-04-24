/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.InicioSesion;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author carlo
 */
public class InicioDeSesion {

    private final String usuario;
    private final String contrasenia;
    private final String URLBASE="http://icris17.pythonanywhere.com/api/login/";
    
    public InicioDeSesion(String usuario, String contrasenia) {
        
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }
    
    public Login validarUsuario(){
        
        try {
            Gson gson = new Gson();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(URLBASE);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            
            multipartEntityBuilder.addPart("usuario", new StringBody(usuario));
            multipartEntityBuilder.addPart("contrasenia", new StringBody(contrasenia));
            httpPost.setEntity(multipartEntityBuilder.build());
            CloseableHttpResponse response =  httpClient.execute(httpPost);
            Login token = gson.fromJson(EntityUtils.toString(response.getEntity()),Login.class);
            return token;
        } catch (IOException ex) {
            Logger.getLogger(InicioDeSesion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
}
