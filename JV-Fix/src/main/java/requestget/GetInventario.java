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
        List<campos.Producto> valores = new ArrayList<>();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(http);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            String resultado = EntityUtils.toString(resEntity);
            if (resultado.length() > 2)
            {
                int cont = 0;
                int index = 0;
                do{
                    //Para obtener el codigo del producto
                    cont += 12;
                    String resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    campos.Producto objeto = new campos.Producto();
                    objeto.setCodigo(resul);
                    //Todo lo anterior es codigo que nunca va a variar
                    //Para obtener el nombre del producto
                    cont += 12;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setNombre(resul);
                    //Para obtener el color del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setColor(resul);
                    //Para obtener el modelo del producto
                    cont += 12;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setModelo(resul);
                    //Para obtener la marca del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setMarca(resul);
                    //Para obtener el tipo del producto
                    cont += 10;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setTipo(resul);
                    //Para obtener la descripcion del producto
                    cont += 17;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setDescripcion(resul);
                    //Para obtener la existencia del producto
                    cont += 15;
                    resul = "";
                    while (resultado.charAt(cont) != ',') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setExistencia(Integer.parseInt(resul));
                    //Para obtener el precio del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setPrecio(Float.parseFloat(resul));
                    //Para obtener el id de la categoria del producto
                    cont += 16;
                    resul = "";
                    while (resultado.charAt(cont) != ',') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setCategoriaId(Integer.parseInt(resul));
                    //Para obtener las imagenes del producto
                    cont += 12;
                    resul = "";
                    if(resultado.charAt(cont+1) == '{')//Si existe aunque sea una imagen
                    {
                        do{
                            //Obtener el id de la imagen
                            cont += 7;
                            while (resultado.charAt(cont) != ',') {
                                resul += resultado.charAt(cont);
                                cont++;
                            }
                            //Obtener la imagen
                            cont += 11;
                            String aux = "";
                            while (resultado.charAt(cont) != '"') {
                                aux += resultado.charAt(cont);
                                cont++;
                            }
                            cont += 2;
                            objeto.setCamposImagen(Integer.parseInt(resul), aux);
                            resul = "";
                        }while (resultado.charAt(cont) != ']' && resultado.charAt(cont) != '}');
                        if (resultado.length() > cont+3)
                            cont += 2;
                    }
                    else
                        cont += 3;
                    valores.add(objeto);
                    int contImagenes = 0;
                    while (contImagenes < valores.get(valores.size()-1).getImagenes().size())
                        contImagenes ++;
                }while(resultado.charAt(cont) != ']');
            }
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(GetCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }
    
    public Object getObjeto(String http)
    {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(http);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            String resultado = EntityUtils.toString(resEntity);
            campos.Producto objeto = new campos.Producto();
            if (resultado.length() > 2)
            {
                int cont = 0;
                int index = 0;
                //Para obtener el codigo del producto
                cont += 12;
                String resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setCodigo(resul);
                //Todo lo anterior es codigo que nunca va a variar
                //Para obtener el nombre del producto
                cont += 12;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setNombre(resul);
                //Para obtener el color del producto
                cont += 11;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setColor(resul);
                //Para obtener el modelo del producto
                cont += 12;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setModelo(resul);
                //Para obtener la marca del producto
                cont += 11;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setMarca(resul);
                //Para obtener el tipo del producto
                cont += 10;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setTipo(resul);
                //Para obtener la descripcion del producto
                cont += 17;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setDescripcion(resul);
                //Para obtener la existencia del producto
                cont += 15;
                resul = "";
                while (resultado.charAt(cont) != ',') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setExistencia(Integer.parseInt(resul));
                //Para obtener el precio del producto
                cont += 11;
                resul = "";
                while (resultado.charAt(cont) != '"') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setPrecio(Float.parseFloat(resul));
                //Para obtener el id de la categoria del producto
                cont += 16;
                resul = "";
                while (resultado.charAt(cont) != ',') {
                    resul += resultado.charAt(cont);
                    cont++;
                }
                objeto.setCategoriaId(Integer.parseInt(resul));
                return objeto;
            }
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(GetCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Object filtrar(String http) {
        List<campos.Producto> valores = new ArrayList<>();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(http);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            String resultado = EntityUtils.toString(resEntity);
            System.out.println(resultado);
            if (resultado.length() > 2)
            {
                int cont = 0;
                int index = 0;
                do{
                    //Para obtener el codigo del producto
                    cont += 12;
                    String resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    campos.Producto objeto = new campos.Producto();
                    objeto.setCodigo(resul);
                    //Todo lo anterior es codigo que nunca va a variar
                    //Para obtener el nombre del producto
                    cont += 12;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setNombre(resul);
                    //Para obtener la existencia del producto
                    cont += 15;
                    resul = "";
                    while (resultado.charAt(cont) != ',') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setExistencia(Integer.parseInt(resul));
                    //Para obtener el precio del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setPrecio(Double.parseDouble(resul));
                    
                    valores.add(objeto);
                    cont +=2;
                }while(resultado.charAt(cont) != ']');
            }
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(GetCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
        }
}
