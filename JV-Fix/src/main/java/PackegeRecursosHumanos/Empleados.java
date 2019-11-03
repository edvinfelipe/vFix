/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackegeRecursosHumanos;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 *
 * @author carlo
 */
public class Empleados {
    
    private HttpClient httpClient;
    private MultipartEntity reqEntity;
    private HttpPost httpPost;
    private HttpPut httpPut;
    private HttpGet httpGet;
    private Gson gson;
    private final String url;
    
    public Empleados(String url){
        this.url=url;
    }
    
    public void insertarEmpleado(String codigo, String nombre,boolean rol,String usuario,String contrasena,File fotografia){
        
        try {
            this.httpClient = new DefaultHttpClient();
            this.httpPost = new HttpPost(url);
            
            this.reqEntity = new MultipartEntity();
            
            reqEntity.addPart("codigo",new StringBody(codigo));
            reqEntity.addPart("nombre",new StringBody(nombre));
            reqEntity.addPart("rol",new StringBody(String.valueOf(rol)));
            if(fotografia!=null){
                FileBody data = new FileBody(fotografia);
                reqEntity.addPart("imagen",data);
            }
            reqEntity.addPart("usuario",new StringBody(usuario));
            reqEntity.addPart("contrasenia",new StringBody(contrasena));
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void modificarEmpleado(String nombre,boolean rol, File fotografia){
        
        this.httpClient = new DefaultHttpClient();
        this.httpPut = new HttpPut(url);
        this.reqEntity = new MultipartEntity();
        
        try {
            reqEntity.addPart("nombre",new StringBody(nombre));
            reqEntity.addPart("rol",new StringBody(String.valueOf(rol)));
            if(fotografia!=null){
                FileBody data = new FileBody(fotografia);
                reqEntity.addPart("imagen",data);
            }
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPut);
            HttpEntity resEntity = response.getEntity();
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
            //CloseableHttpResponse response = null;
            //response = (CloseableHttpResponse) httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public List<Empleado> mostrarEmpleados(){
        
        try {
            String result;
            this.httpClient = new DefaultHttpClient();
            this.httpGet = new HttpGet(url);
            this.gson = new Gson();
            CloseableHttpResponse response;
            response = (CloseableHttpResponse) httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            
            
            result = EntityUtils.toString(entity, "UTF-8");
            Type empleados = new TypeToken<List<Empleado>>(){}.getType();
            List<Empleado> listaEmpleados = gson.fromJson(result,empleados);
            httpClient.getConnectionManager().shutdown();
            return listaEmpleados;
        
        } catch (IOException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
