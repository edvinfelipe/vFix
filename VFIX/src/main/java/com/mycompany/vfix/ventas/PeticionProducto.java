/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.vfix.usuarios.EjecutarPeticion;
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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
/**
 *
 * @author nasc_
 */
public class PeticionProducto {
    
    private String urlProductos = "http://icris17.pythonanywhere.com/api/productos/codigo";
    
    public PeticionProducto() {
    }
    
    public List<DatosProducto> ObtenerDatos(){
        try {
            Gson gson = new Gson();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlProductos);
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
            JsonReader reader = Json.createReader(new InputStreamReader((response.getEntity().getContent())));
            JsonArray array = reader.readArray();
            
            Type tipoDatosProducto = new TypeToken<List<DatosProducto>>(){}.getType();
            List<DatosProducto> datosProductos = gson.fromJson(array.toString(), tipoDatosProducto);
            response.close();
            return datosProductos;    
        } catch (IOException ex) {
            return null;
        } 
    }
    public List<DatosProducto> ObtenerProducto(String codigo){
        try {
            Gson gson = new Gson();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlProductos + codigo + "/");
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
            JsonReader reader = Json.createReader(new InputStreamReader((response.getEntity().getContent())));
            JsonArray array = reader.readArray();
            
            Type tipoDatosProducto = new TypeToken<List<DatosProducto>>(){}.getType();
            List <DatosProducto> datosProductos = gson.fromJson(array.toString(), tipoDatosProducto);
            response.close();
            return datosProductos;    
        } catch (IOException ex) {
            return null;
        } 
    }
    public void Actualizar(HttpEntity httpEntity, String codigo){ 
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(urlProductos + codigo + "/");
            httpPut.setEntity(httpEntity);
            HttpResponse httpResponse;
            
            httpResponse = httpClient.execute(httpPut);
             System.out.println("Codigo: "+httpResponse.getStatusLine().getStatusCode());
        } catch (IOException ex) {
            Logger.getLogger(EjecutarPeticion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
