/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.clientes;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 *
 * @author igeni
 */
public class FachadaClientes {
    //Hacer post para ingresar un cliente
    public void post(List<String> parametros)
    {
        try {
            String url = "http://icris17.pythonanywhere.com/api/clientes/";
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("nombre",parametros.get(0)));
            params.add(new BasicNameValuePair("telefono",parametros.get(1)));
            if (parametros.get(2).length() > 0)
                params.add(new BasicNameValuePair("nit",parametros.get(2)));
            if (parametros.get(3).length() > 0)
                params.add(new BasicNameValuePair("direccion",parametros.get(3)));
            if (parametros.get(4).length() > 0)
                params.add(new BasicNameValuePair("cumpleanios",parametros.get(4)));
            if (parametros.get(5).length() > 0)
                params.add(new BasicNameValuePair("estrellas",parametros.get(5)));
            if (parametros.get(6).length() > 0)
                params.add(new BasicNameValuePair("correo",parametros.get(6)));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            
            HttpResponse response = httpclient.execute(httppost);
        } catch (IOException ex) {
            Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Hacer get (aplicando filtro u obteniendo a todos los empleados)
    public void get(DefaultTableModel modelo, String filtro, boolean interruptor, List<Integer> idClientes, boolean cf)
    {
        while (idClientes.size() > 0)
            idClientes.remove(0);
        while (modelo.getRowCount() > 0)
            modelo.removeRow(0);
        String url = "";
        if (filtro.length() > 0)
            if (interruptor)//Si se está buscando por nombre
                url = "http://icris17.pythonanywhere.com/api/clientes/filtrar/?nombre=" + queryFiltro(filtro);            
            else//Si se está buscando por nit
            {
                if (!cf)//Si no es consumidor final, filtrar por nit
                    url = "http://icris17.pythonanywhere.com/api/clientes/filtrar/?nit=" + queryFiltro(filtro);            
                else//Si es consumidor final, filtrar a estos
                    url = "http://icris17.pythonanywhere.com/api/clientes/filtrar/?nit=c/f";
            }  
        else
        {
            if (!cf)//Si no es consumidor final, filtrar por nit
                url = "http://icris17.pythonanywhere.com/api/clientes/";
            else//Si es consumidor final, filtrar a estos
                url = "http://icris17.pythonanywhere.com/api/clientes/filtrar/?nit=c/f";
        }
        try {
            String result = "";
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);
            
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            
            Gson gson = new Gson();
            Cliente[] clientes = gson.fromJson(result, Cliente[].class);
            int i = 0;
            for (Cliente cliente : clientes){
                if (!cliente.getEliminado())
                {
                    idClientes.add(cliente.getId());
                    Object O[] = null;
                    modelo.addRow(O);
                    modelo.setValueAt(cliente.getNombre(), i, 0);
                    modelo.setValueAt(cliente.getTelefono(), i, 1);
                    modelo.setValueAt(cliente.getNit(), i, 2);
                    if (cliente.getCumpleanios()==null)
                        modelo.setValueAt("", i, 3);
                    else
                        modelo.setValueAt(cliente.getCumpleanios(), i, 3);
                    modelo.setValueAt(cliente.getEstrellas(), i, 4);
                    modelo.setValueAt(cliente.getCorreo(), i, 5);
                    modelo.setValueAt(cliente.getDireccion(), i, 6);
                    i++;
                }       
            }
                
            
        } catch (IOException ex) {
            Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Hacer put para modificar a un cliente
    public void put(List<String> parametros, Integer idCliente)
    {
        try {
            String url = "http://icris17.pythonanywhere.com/api/clientes/" + idCliente + "/";
            //String url = "";
            HttpClient httpclient = HttpClients.createDefault();
            HttpPut httpput = new HttpPut(url);
            String result = "";
            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("nombre",parametros.get(0)));
            params.add(new BasicNameValuePair("telefono",parametros.get(1)));
            params.add(new BasicNameValuePair("nit",parametros.get(2)));
            params.add(new BasicNameValuePair("direccion",parametros.get(3)));
            params.add(new BasicNameValuePair("cumpleanios",parametros.get(4)));
            params.add(new BasicNameValuePair("estrellas",parametros.get(5)));
            params.add(new BasicNameValuePair("correo",parametros.get(6)));
            httpput.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            
            HttpResponse response = httpclient.execute(httpput);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (IOException ex) {
            Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Hacer un delete para eliminar a un cliente
    public void delete(Integer idCliente)
    {
        try {
            String url = "http://icris17.pythonanywhere.com/api/clientes/" + idCliente + "/";
            HttpClient httpclient = HttpClients.createDefault();
            HttpDelete httpdelete = new HttpDelete(url);
            String result = "";          
            HttpResponse response = httpclient.execute(httpdelete);
        } catch (IOException ex) {
            Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Función que sustituye los espacios en blanco por %20
    private String queryFiltro(String busqueda)
    {
        String retornar = "";
        for (int i = 0; i < busqueda.length(); i++)
        {
            if (busqueda.charAt(i) != ' ')
                retornar += busqueda.charAt(i);
            else
                retornar += "%20";
        }
        return retornar;
    }
}
