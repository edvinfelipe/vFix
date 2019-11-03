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
public class Categoria {
    public Categoria(){
        
    }
    
    private String categoria;
    private int id;
    
    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }
    
    public String getCategoria()
    {
        return categoria;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
}
