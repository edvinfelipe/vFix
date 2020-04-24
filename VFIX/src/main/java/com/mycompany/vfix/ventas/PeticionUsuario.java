/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.vfix.usuarios.DatosEmpleado;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author nasc_
 */
public class PeticionUsuario {
    
    private String urlUsuario = "http://icris17.pythonanywhere.com/api/recursoshumanos/";
    private String urlCliente = "http://icris17.pythonanywhere.com/api/clientes/";
    
    public PeticionUsuario() {
    }
    
    public List<DatosEmpleado> ObtenerDatos(){
        try {
            Gson gson = new Gson();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlUsuario);
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
            JsonReader reader = Json.createReader(new InputStreamReader((response.getEntity().getContent())));
            JsonArray array = reader.readArray();
            
            Type tipoDatosUsuario = new TypeToken<List<DatosEmpleado>>(){}.getType();
            List<DatosEmpleado> datosUsuario = gson.fromJson(array.toString(), tipoDatosUsuario);
            response.close();
            return datosUsuario;    
        } catch (IOException ex) {
            return null;
        } 
    }
    
    public List<DatosCliente> ObtenerDatosCliente(){
        try {
            Gson gson = new Gson();
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(urlCliente);
            CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
            JsonReader reader = Json.createReader(new InputStreamReader((response.getEntity().getContent())));
            JsonArray array = reader.readArray();
            
            Type tipoDatosCliente = new TypeToken<List<DatosCliente>>(){}.getType();
            List<DatosCliente> datosCliente = gson.fromJson(array.toString(), tipoDatosCliente);
            response.close();
            return datosCliente;    
        } catch (IOException ex) {
            return null;
        } 
    }
    
    public boolean buscarUsuario(String user, String password){
        boolean existe = false;
        List<DatosEmpleado> usuarios = ObtenerDatos();
        for(DatosEmpleado usuario : usuarios){
            if((user.equals(usuario.getUsuario()))&&(password.equals(usuario.getContrasenia()))){
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    public int buscarCliente(String nombre, String nit){
        int existe = 0;
        List<DatosCliente> clientes = ObtenerDatosCliente();
        for(DatosCliente cliente : clientes){
            if((nombre.equals(cliente.getNombre()))&&(nit.equals(cliente.getNit()))){
                existe = cliente.getId();
                break;
            }
        }
        return existe;
    }
}
