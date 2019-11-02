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
    
    public String post(String http, List<EstructuraPost> estructura){
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
    
    public void put(String http, List<EstructuraPost> estructura)
    {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(http);
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart(estructura.get(0).getVariable(), new StringBody(estructura.get(0).getValor().toString()));
            httpPut.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPut);
            httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Requests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://localhost:8000/api/categorias/");
            //HttpPut httpPut = new HttpPut("http://localhost:8000/api/categorias/");
            //HttpGet httpGet = new HttpGet("http://localhost:8000/api/productos");
            //FileBody data = new FileBody(archivo);
            MultipartEntity reqEntity = new MultipartEntity();
            //LinkedList<FormBodyPart> partList = new LinkedList<>();
            /*reqEntity.addPart("codigo",new StringBody(tfCodigo.getText()));
            reqEntity.addPart("nombre",new StringBody(tfNombre.getText()));
            reqEntity.addPart("color",new StringBody(tfColor.getText()));
            reqEntity.addPart("modelo",new StringBody(tfModelo.getText()));
            reqEntity.addPart("marca",new StringBody(tfMarca.getText()));
            reqEntity.addPart("tipo",new StringBody(tfTipo.getText()));
            reqEntity.addPart("descripcion",new StringBody(tfDescripcion.getText()));
            reqEntity.addPart("existencia",new StringBody(tfExistencia.getText()));
            reqEntity.addPart("precio",new StringBody(tfPrecio.getText()));
            reqEntity.addPart("categoriaId",new StringBody("1"));
            reqEntity.addPart("imagenes",data);*/
            //reqEntity.addPart("nombre", new StringBody("Nueva categoria"));
            //partList.add(new FormBodyPart("nombre", new StringBody("Nueva categoria")));
            
            /*reqEntity.addPart("nombre",new StringBody("Nuevo nombre"));
            reqEntity.addPart("color",new StringBody("Nuevo color"));
            reqEntity.addPart("modelo",new StringBody("Modelo1"));
            reqEntity.addPart("marca",new StringBody("Marca1"));
            reqEntity.addPart("tipo",new StringBody("Tipo1"));
            reqEntity.addPart("descripcion",new StringBody("Descripción1"));
            reqEntity.addPart("existencia",new StringBody("25"));
            reqEntity.addPart("precio",new StringBody("15"));
            reqEntity.addPart("categoriaId",new StringBody("1"));
            reqEntity.addPart("imagenes",data);*/
            
            /*httpPost.setEntity(reqEntity);
            //HttpResponse response = httpClient.execute(httpPost);
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println( response ) ;
            System.out.println( "Codigo: "+statusCode) ;
            
            HttpEntity resEntity = response.getEntity();
            System.out.println( EntityUtils.toString(resEntity) );
            
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();*/
}
