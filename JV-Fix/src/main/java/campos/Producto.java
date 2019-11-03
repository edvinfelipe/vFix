/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Windows 8.1
 */
public class Producto {
    String codigo;
    String nombre;
    String color;
    String modelo;
    String marca;
    String tipo;
    String descripcion;
    int existencia;
    double precio;
    int categoriaId;
    List<campos.ImagenesProducto> imagenes = new ArrayList<>();
    
    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }
    
    public String getCodigo()
    {
        return codigo;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setColor(String color)
    {
        this.color = color;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
    
    public String getModelo()
    {
        return modelo;
    }
    
    public void setMarca(String marca)
    {
        this.marca = marca;
    }
    
    public String getMarca()
    {
        return marca;
    }
    
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    
    public String getTipo()
    {
        return tipo;
    }
    
    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion()
    {
        return descripcion;
    }
    
    public void setExistencia(int existencia)
    {
        this.existencia = existencia;
    }
    
    public int getExistencia()
    {
        return existencia;
    }
    
    public void setPrecio(double precio)
    {
        this.precio = precio;
    }
    
    public double getPrecio()
    {
        return precio;
    }
    
    public void setCategoriaId(int categoriaId)
    {
        this.categoriaId = categoriaId;
    }
    
    public int getCategoriaId()
    {
        return categoriaId;
    }
    
    public void setCamposImagen(int id, String imagen)
    {
        campos.ImagenesProducto objeto = new campos.ImagenesProducto();
        objeto.id = id;
        objeto.imagen = imagen;
        imagenes.add(objeto);
    }
    
    public List<campos.ImagenesProducto> getImagenes()
    {
        return imagenes;
    }
}
