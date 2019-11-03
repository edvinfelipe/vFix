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
                    System.out.println(resul);
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
                    System.out.println(resul);
                    objeto.setNombre(resul);
                    //Para obtener el color del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setColor(resul);
                    //Para obtener el modelo del producto
                    cont += 12;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setModelo(resul);
                    //Para obtener la marca del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setMarca(resul);
                    //Para obtener el tipo del producto
                    cont += 10;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setTipo(resul);
                    //Para obtener la descripcion del producto
                    cont += 17;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setDescripcion(resul);
                    //Para obtener la existencia del producto
                    cont += 15;
                    resul = "";
                    while (resultado.charAt(cont) != ',') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setExistencia(Integer.parseInt(resul));
                    //Para obtener el precio del producto
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
                    objeto.setPrecio(Float.parseFloat(resul));
                    //Para obtener el id de la categoria del producto
                    cont += 16;
                    resul = "";
                    while (resultado.charAt(cont) != ',') {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    System.out.println(resul);
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
                            System.out.println(resul);
                            //Obtener la imagen
                            cont += 11;
                            String aux = "";
                            while (resultado.charAt(cont) != '"') {
                                aux += resultado.charAt(cont);
                                cont++;
                            }
                            System.out.println(aux);
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
                    System.out.println("Codigo: " + valores.get(valores.size()-1).getCodigo());
                    System.out.println("Nombre: " + valores.get(valores.size()-1).getNombre());
                    System.out.println("Color: " + valores.get(valores.size()-1).getColor());
                    System.out.println("Modelo: " + valores.get(valores.size()-1).getModelo());
                    System.out.println("Marca: " + valores.get(valores.size()-1).getMarca());
                    System.out.println("Tipo: " + valores.get(valores.size()-1).getTipo());
                    System.out.println("Descripcion: " + valores.get(valores.size()-1).getDescripcion());
                    System.out.println("Existencia: " + valores.get(valores.size()-1).getExistencia());
                    System.out.println("Precio: " + valores.get(valores.size()-1).getPrecio());
                    System.out.println("Id: " + valores.get(valores.size()-1).getCategoriaId());
                    int contImagenes = 0;
                    while (contImagenes < valores.get(valores.size()-1).getImagenes().size())
                    {
                        System.out.println("Id Imagen: " + valores.get(valores.size()-1).getImagenes().get(contImagenes).getId());
                        System.out.println("Imagen: " + valores.get(valores.size()-1).getImagenes().get(contImagenes).getImagen());
                        contImagenes ++;
                    }
                        
                }while(resultado.charAt(cont) != ']');
            }
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(GetCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }

    @Override
    public List<Object> filtrar(List<Object> lista, String info) {
        return null;
    }
}
