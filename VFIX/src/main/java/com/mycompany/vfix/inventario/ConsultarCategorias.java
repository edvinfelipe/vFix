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
 * 
 */
public class ConsultarCategorias {
    
            /**
             * 
             * @param modelo ComboBox en el cual se cargarán los datos solicitados por el parametro opcion
             * @param idProducto 
             * @param opcion opcion para la funcion get donde 1 es categoria, 2 marca y 3 color
             */
            public void get(DefaultComboBoxModel modelo,  List<Integer> idProducto, int opcion){
            modelo.removeAllElements();
            try {
            String result = "";
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet("");
            if(opcion == 1){
                httpget = new HttpGet("http://icris17.pythonanywhere.com/api/categorias/");
            }else if(opcion == 2){
                httpget = new HttpGet("http://icris17.pythonanywhere.com/api/marcas/");
            }else{
                httpget = new HttpGet("http://icris17.pythonanywhere.com/api/colores/");
            }
            
            
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            
            Gson gson = new Gson();
            Categoria[] categoria = gson.fromJson(result, Categoria[].class);
            
            modelo.addElement("");
            for (Categoria categorias : categoria){
                idProducto.add(categorias.getId());
                modelo.addElement(categorias.getNombre());
            }
                
            
        } catch (IOException ex) {
            Logger.getLogger(ConsultarCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}
