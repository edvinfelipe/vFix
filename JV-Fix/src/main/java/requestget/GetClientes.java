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
public class GetClientes implements IRequests{
    @Override
    public Object get(String http)
    {
        List<campos.Cliente> valores = new ArrayList<>();
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
                    //Para obtener el id del cliente
                    cont += 7;
                    String resul = "";
                    while (resultado.charAt(cont) != ',')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    campos.Cliente objeto = new campos.Cliente();
                    objeto.setId(Integer.parseInt(resul));
                    //Todo lo anterior es código que nunca va a variar
                    //Para obtener el código del cliente
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setCodigo(resul);
                    //Para obtener el nombre del cliente
                    cont += 12;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setNombre(resul);
                    //Para obtener el telefono del cliente
                    cont += 14;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setTelefono(resul);
                    //Para obtener el nit del cliente
                    cont += 9;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setNit(resul);
                    //Para obtener la direccion del cliente
                    cont += 15;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setDireccion(resul);
                    //Para obtener el cumpleaños del cliente
                    cont += 17;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setCumpleanos(resul);
                    //Para obtener la importancia del cliente
                    cont += 14;
                    resul = "";
                    while (resultado.charAt(cont) != ',')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setEstrellas(Integer.parseInt(resul));
                    //Para obtener el correo del cliente
                    cont += 11;
                    resul = "";
                    while (resultado.charAt(cont) != '"')
                    {
                        resul += resultado.charAt(cont);
                        cont++;
                    }
                    objeto.setCorreo(resul);
                    cont += 2;
                    valores.add(objeto);
                    System.out.println("Id: " +valores.get(index).getId());
                    System.out.println("Codigo: " +valores.get(index).getCodigo());
                    System.out.println("Nombre: " +valores.get(index).getNombre());
                    System.out.println("Telefono: " +valores.get(index).getTelefono());
                    System.out.println("Nit: " +valores.get(index).getNit());
                    System.out.println("Direccion: " +valores.get(index).getDireccion());
                    System.out.println("Cumpleaños: " +valores.get(index).getCumpleanos());
                    System.out.println("Estrellas: " +valores.get(index).getEstrellas());
                    index++;
                }while (resultado.charAt(cont) == ',');
            }
            EntityUtils.consume(resEntity);
            httpClient.getConnectionManager().shutdown();
            return valores;
        } catch (IOException ex) {
            Logger.getLogger(GetClientes.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }

    @Override
    public Object filtrar(String http) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
