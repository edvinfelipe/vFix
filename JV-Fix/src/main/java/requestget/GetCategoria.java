/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import interfaces.IRequests;

/**
 *
 * @author Windows 8.1
 */
public class GetCategoria implements IRequests{
    @Override
    public Object get(String http)
    {
        List<campos.Categoria> valores = new ArrayList<>();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(http);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            String resultado = EntityUtils.toString(resEntity);
            //System.out.println(resultado);
            if (resultado.length() > 2)
            {
                int cont = 0;
                int index = 0;
                do {
                    cont += 7;
                    String resul = "";
                    while (resultado.charAt(cont) != ',') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    campos.Categoria objeto = new campos.Categoria();
                    objeto.setId(Integer.parseInt(resul));
                    //Todo lo anterior es codigo que nunca va a variar
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '}') {
                        if (resultado.charAt(cont) != '\"') {
                            resul += resultado.charAt(cont);
                        }
                        cont++;
                    }
                    objeto.setCategoria(resul);
                    valores.add(objeto);
                    cont++;
                    //Aqui seria un do-while de si el caracter encontrado no es una coma
                    index ++;
                } while (resultado.charAt(cont) == ',');   
            }
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
            
        } catch (IOException ex) {
            Logger.getLogger(GetCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
}
