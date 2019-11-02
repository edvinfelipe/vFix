/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.util.List;

/**
 *
 * @author Windows 8.1
 */
public class EstructuraPost {
    public EstructuraPost(){
        
    }
    private String variable;
    private Object valor;
    
    public void setVariable(String variable)
    {
        this.variable = variable;
    }
    
    public String getVariable()
    {
        return variable;
    }
    
    public void setValor(Object valor)
    {
        this.valor = valor;
    }
    
    public Object getValor()
    {
        return valor;
    }
}
