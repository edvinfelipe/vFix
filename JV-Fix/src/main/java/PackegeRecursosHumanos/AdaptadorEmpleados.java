/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackegeRecursosHumanos;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carlo
 */
public class AdaptadorEmpleados {
    
    private DefaultTableModel modelo;
    private final List<Empleado> listaEmpleados;
    
    public AdaptadorEmpleados(List<Empleado> listaEmpleados){
        this.listaEmpleados=listaEmpleados;
    }
    
    public DefaultTableModel obtenerModelo(){
        
        String[] columnas = {"Codigo","Empleado","rol"};
        this.modelo = new DefaultTableModel(columnas,0);
        
        Object[] listaEmpleado = new Object[3];
        
        listaEmpleados.forEach((Empleado empleado)-> {
            listaEmpleado[0]= empleado.getCodigo();
            listaEmpleado[1]= empleado.getNombre();
            if(empleado.isRol()==true){
                listaEmpleado[2]="Administrador";
            }else{ 
                listaEmpleado[2]="Empleado"; 
            }
            modelo.addRow(listaEmpleado);
        });
        return this.modelo;
    }
    
}
