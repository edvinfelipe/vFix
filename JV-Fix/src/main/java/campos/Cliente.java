/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campos;

/**
 *
 * @author Windows 8.1
 */
public class Cliente {
    int id;
    String codigo;
    String nombre;
    String telefono;
    String cumpleanios;
    String nit;
    String direccion;
    int estrellas;
    String correo;
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
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
    
    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }
    
    public String getTelefono()
    {
        return telefono;
    }
    
    public void setCumpleanos(String cumpleanos)
    {
        this.cumpleanios = cumpleanos;
    }
    
    public String getCumpleanos()
    {
        return cumpleanios;
    }
    
    public void setEstrellas(int estrellas)
    {
        this.estrellas = estrellas;
    }
    
    public int getEstrellas()
    {
        return estrellas;
    }
    
    public void setCorreo(String correo)
    {
        this.correo = correo;
    }
    
    public String getCorreo()
    {
        return correo;
    }
    
    public void setNit(String nit)
    {
        this.nit = nit;
    }
    
    public String getNit()
    {
        return nit;
    }
    
    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }
    
    public String getDireccion()
    {
        return direccion;
    }
}
