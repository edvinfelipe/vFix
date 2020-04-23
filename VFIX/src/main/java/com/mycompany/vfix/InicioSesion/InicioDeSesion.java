/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.InicioSesion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
    private final String URLBASE="http://127.0.0.1:8000/api/login/";
    
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
