/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.inventario;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Usuario
 */
public class ConsultarCategorias {
    
    
            public void get(DefaultComboBoxModel modelo,  List<Integer> idProducto){
            modelo.removeAllElements();
            try {
            String result = "";
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("http://localhost:8000/api/categorias/");
            
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            
            Gson gson = new Gson();
            Categoria[] categoria = gson.fromJson(result, Categoria[].class);
            int i = 0;
            for (Categoria categorias : categoria){
                idProducto.add(categorias.getId());
                modelo.addElement(categorias.getNombre());
                i++;  
            }
                
            
        } catch (IOException ex) {
            Logger.getLogger(ConsultarCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}
