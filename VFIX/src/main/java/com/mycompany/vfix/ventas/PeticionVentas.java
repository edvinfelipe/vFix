/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

import com.mycompany.vfix.usuarios.EjecutarPeticion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author nasc_
 */
public class PeticionVentas {
    
    private final String urlFacturas = "http://icris17.pythonanywhere.com/api/facturas";
    private final String urlDetalles = "http://icris17.pythonanywhere.com/api/detallefacturas";
    
    public void Insertar(HttpEntity httpEntity){
        
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(urlFacturas);
            httpPost.setEntity(httpEntity);
            HttpResponse httpResponse;
            
            httpResponse = httpClient.execute(httpPost);
            System.out.println("Codigo: "+httpResponse.getStatusLine().getStatusCode());
        } catch (IOException ex) {
            Logger.getLogger(EjecutarPeticion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
