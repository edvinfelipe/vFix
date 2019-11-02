/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestget;

import interfaces.IRequests;
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

/**
 *
 * @author Windows 8.1
 */
public class GetInventario implements IRequests{
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
            System.out.println(resultado);
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(GetCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
}
