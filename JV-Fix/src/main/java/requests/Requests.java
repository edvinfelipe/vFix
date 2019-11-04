/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import campos.Categoria;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Windows 8.1
 */
public class Requests {
    public Requests(){}
    
    public Object post(String http, List<EstructuraPostPut> estructura){
        try {
            String resultado;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(http);
            MultipartEntity reqEntity = new MultipartEntity();
            for (int i = 0; i < estructura.size(); i++)
            {
                if (!estructura.get(i).getVariable().equals("imagenes"))
                    reqEntity.addPart(estructura.get(i).getVariable(), new StringBody(estructura.get(i).getValor().toString()));
                else
                    reqEntity.addPart(estructura.get(i).getVariable(), (FileBody)estructura.get(i).getValor());
            }
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            resultado = EntityUtils.toString(resEntity);
            System.out.println( resultado );
            httpClient.getConnectionManager().shutdown();
            return resultado;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }
    
    public void delete(String http)
    {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete(http);
            HttpResponse response = httpClient.execute(httpDelete);
            int statusCode = response.getStatusLine().getStatusCode();
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void put(String http, List<EstructuraPostPut> estructura)
    {
        try {
            String resultado;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(http);
            MultipartEntity reqEntity = new MultipartEntity();
            for (int i = 0; i < estructura.size(); i++)
            {
                if (!estructura.get(i).getVariable().equals("imagenes"))
                    reqEntity.addPart(estructura.get(i).getVariable(), new StringBody(estructura.get(i).getValor().toString()));
                else
                    reqEntity.addPart(estructura.get(i).getVariable(), (FileBody)estructura.get(i).getValor());
            }
            httpPut.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPut);
            HttpEntity resEntity = response.getEntity();
            resultado = EntityUtils.toString(resEntity);
            System.out.println( resultado );
            httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
