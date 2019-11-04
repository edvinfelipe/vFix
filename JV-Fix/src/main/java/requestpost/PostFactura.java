/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestpost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import requests.EstructuraPostPut;
import requests.Requests;

/**
 *
 * @author Windows 8.1
 */
public class PostFactura {
    public Object post(String http, List<EstructuraPostPut> estructura){
        try {
            String resultado;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(http);
            MultipartEntity reqEntity = new MultipartEntity();
            for (int i = 0; i < estructura.size(); i++)
            {
                System.out.println(estructura.get(i).getValor().toString());
                System.out.println(estructura.get(i).getVariable().toString());
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
            //Para obtener el id del cliente
            int cont = 0;
            cont += 6;
            String resul = "";
            while (resultado.charAt(cont) != ',') {
                resul += resultado.charAt(cont);
                cont++;
            }
            httpClient.getConnectionManager().shutdown();
            return resul;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }
}
