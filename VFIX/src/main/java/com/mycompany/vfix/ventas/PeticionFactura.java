/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

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
import javax.json.JsonReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author nasc_
 */
public class PeticionFactura {
    
    private final String urlFactura = "http://icris17.pythonanywhere.com/api/facturas";
    private final String urlDetalle = "http://icris17.pythonanywhere.com/api/detallefacturas";
    
    public PeticionFactura(){
    
    }
    
    public List<DatosFactura> ObtenerDatos(){
        try {
            Gson gson = new Gson();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlFactura);
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
            JsonReader reader = Json.createReader(new InputStreamReader((response.getEntity().getContent())));
            JsonArray array = reader.readArray();
            
            Type tipoDatosFactura = new TypeToken<List<DatosFactura>>(){}.getType();
            List <DatosFactura> datosFactura = gson.fromJson(array.toString(), tipoDatosFactura);
            response.close();
            return datosFactura;    
        } catch (IOException ex) {
            return null;
        } 
    }
    public int idFactura(){
        int id = 0;
        List<DatosFactura> facturas = ObtenerDatos();
        DatosFactura factura = facturas.get(facturas.size()-1);
        id = factura.getId();
        return id;
    }
    public void InsertarFactura(HttpEntity httpEntity){
        
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlFactura);
            httpPost.setEntity(httpEntity);
            HttpResponse httpResponse;
            
            httpResponse = httpClient.execute(httpPost);
            System.out.println("Codigo: "+httpResponse.getStatusLine().getStatusCode());
        } catch (IOException ex) {
            Logger.getLogger(PeticionFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void InsertarDetalle(HttpEntity httpEntity){
        
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlDetalle);
            httpPost.setEntity(httpEntity);
            HttpResponse httpResponse;
            
            httpResponse = httpClient.execute(httpPost);
            System.out.println("Codigo: "+httpResponse.getStatusLine().getStatusCode());
        } catch (IOException ex) {
            Logger.getLogger(PeticionFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
