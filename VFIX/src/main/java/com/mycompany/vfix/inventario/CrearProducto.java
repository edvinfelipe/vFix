/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.inventario;


import com.google.gson.Gson;
import com.mycompany.vfix.clientes.Cliente;
import com.mycompany.vfix.clientes.FachadaClientes;
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
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Usuario
 */
public class CrearProducto {
    
        public void post(List<String> parametros,FileBody imagen)
    {
        try {
            //HttpClient httpclient = HttpClients.createDefault();
          /*  HttpPost request = new HttpPost("http://localhost:8000/api/productos/");
            HttpClient httpclient = HttpClients.createDefault();
            MultipartEntity reqEntity = new MultipartEntity();
            
            List<NameValuePair> datos = new ArrayList<NameValuePair>(2);
            datos.add(new BasicNameValuePair("codigo",parametros.get(0)));
            datos.add(new BasicNameValuePair("nombre",parametros.get(1)));
            datos.add(new BasicNameValuePair("modelo",parametros.get(2)));
            datos.add(new BasicNameValuePair("tipo",parametros.get(3)));
            datos.add(new BasicNameValuePair("descripcion",parametros.get(4)));
            datos.add(new BasicNameValuePair("existencia",parametros.get(5)));
            datos.add(new BasicNameValuePair("precio",parametros.get(6)));
            datos.add(new BasicNameValuePair("precioMayorista",parametros.get(7)));
            datos.add(new BasicNameValuePair("precioCliente",parametros.get(8)));
            datos.add(new BasicNameValuePair("colorId",parametros.get(9)));
            datos.add(new BasicNameValuePair("marcaId",parametros.get(10)));
            datos.add(new BasicNameValuePair("categoriaId",parametros.get(11)));
            reqEntity.addPart("data", imagen);
            
           // datos.add(new BasicNameValuePair("imagenes",parametros.get(12)));
           // System.out.println(parametros.get(12));

            request.setEntity(new UrlEncodedFormEntity(datos, "UTF-8"));
            HttpResponse response = httpclient.execute(request);*/
              HttpClient httpclient = new DefaultHttpClient();
              HttpPost httppost = new HttpPost("http://localhost:8000/api/productos/");
              MultipartEntity reqEntity = new MultipartEntity();
              reqEntity.addPart("codigo", new StringBody( parametros.get(0))) ;
              reqEntity.addPart("nombre", new StringBody(parametros.get(1)));
              reqEntity.addPart("modelo", new StringBody(parametros.get(2)));
              reqEntity.addPart("tipo", new StringBody(parametros.get(3)));
              reqEntity.addPart("descripcion", new StringBody(parametros.get(4)));
              reqEntity.addPart("existencia", new StringBody(parametros.get(5)));
              reqEntity.addPart("precio", new StringBody(parametros.get(6)));
              reqEntity.addPart("precioMayorista", new StringBody(parametros.get(7)));
              reqEntity.addPart("precioCliente", new StringBody(parametros.get(8)));
              reqEntity.addPart("colorId", new StringBody(parametros.get(9)));
              reqEntity.addPart("marcaId", new StringBody(parametros.get(10)));
              reqEntity.addPart("categoriaId", new StringBody(parametros.get(11)));
              
              //System.out.println(imagen.getFilename());
              //System.out.println(imagen.getCharset());
              
              if(imagen!=null){
                  reqEntity.addPart("imagenes", imagen);
              }
              
              httppost.setEntity(reqEntity);

              HttpResponse response = httpclient.execute(httppost);
              System.out.println( "------- imagen -----" ) ;
              System.out.println( imagen ) ;
              System.out.println( "------- response -----" ) ;
              System.out.println( response ) ;

              HttpEntity resEntity = response.getEntity();
              System.out.println( resEntity ) ;
              System.out.println( EntityUtils.toString(resEntity) );

              EntityUtils.consume(resEntity);
              httpclient.getConnectionManager().shutdown();
          
            //System.out.println("Llego aquí");
            
            //HttpResponse response = httpclient.execute(httppost);
        } catch (IOException ex) {
            Logger.getLogger(CrearProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void get(DefaultTableModel modelo, String codigo, List<Integer> idProducto){
            while (idProducto.size() > 0)
                idProducto.remove(0);
            while (modelo.getRowCount() > 0)
                modelo.removeRow(0);
            String path = "";
            if (!codigo.equals("")){
                path = "http://localhost:8000/api/productos/"+codigo;
            }else{
                path = "http://localhost:8000/api/productos/";
            }
            
            try {
            String result = "";
            HttpClient httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(path);
            
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            
            Gson gson = new Gson();
            Producto[] producto = gson.fromJson(result, Producto[].class);
            int i = 0;
            for (Producto productos : producto){

                    idProducto.add(productos.getId());
                    Object fila[] = null;
                    modelo.addRow(fila);
                    modelo.setValueAt(productos.getCodigo(), i, 0);
                    modelo.setValueAt(productos.getNombre(), i, 1);
                    modelo.setValueAt(productos.getModelo(), i, 2);
                    modelo.setValueAt(productos.getTipo(), i, 3);
                    modelo.setValueAt(productos.getDescripcion(), i, 4);                       
                    modelo.setValueAt(productos.getExistencia(), i, 5);
                    modelo.setValueAt(productos.getPrecio(), i, 6);
                    modelo.setValueAt(productos.getColorId(), i, 7);
                    modelo.setValueAt(productos.getMarcaId(), i, 8);
                    modelo.setValueAt(productos.getCategoriaId(), i, 9);
                    i++;
                   
            }
                
            
        } catch (IOException ex) {
            Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        public void delete(String codigoProducto){
        try {
            String url = "http://localhost:8000/api/productos/" + codigoProducto + "/";
            HttpClient httpclient = HttpClients.createDefault();
            HttpDelete httpdelete = new HttpDelete(url);
            String result = "";          
            HttpResponse response = httpclient.execute(httpdelete);
        } catch (IOException ex) {
            Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


        public void put(List<String> parametros, String codigo)
        {
            try {
                System.out.println("EL CODIGO ES:" + codigo);
                String url = "http://localhost:8000/api/productos/" + codigo+"/";
                //String url = "";
                HttpClient httpclient = HttpClients.createDefault();
                HttpPut httpput = new HttpPut(url);
                String result = "";
                
                
                List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                params.add(new BasicNameValuePair("codigo",parametros.get(0)));
                params.add(new BasicNameValuePair("nombre",parametros.get(1)));
                params.add(new BasicNameValuePair("modelo",parametros.get(2)));
                params.add(new BasicNameValuePair("tipo",parametros.get(3)));
                params.add(new BasicNameValuePair("descripcion",parametros.get(4)));
                params.add(new BasicNameValuePair("existencia",parametros.get(5)));
                params.add(new BasicNameValuePair("precio",parametros.get(6)));
                params.add(new BasicNameValuePair("precioMayorista",parametros.get(7)));
                params.add(new BasicNameValuePair("precioCliente",parametros.get(8)));
                params.add(new BasicNameValuePair("colorId",parametros.get(9)));
                params.add(new BasicNameValuePair("marcaId",parametros.get(10)));
                params.add(new BasicNameValuePair("categoriaId",parametros.get(11)));
                
                
                httpput.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                HttpResponse response = httpclient.execute(httpput);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(response.getEntity());
                System.out.println(result);
            } catch (IOException ex) {
                Logger.getLogger(FachadaClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
}

