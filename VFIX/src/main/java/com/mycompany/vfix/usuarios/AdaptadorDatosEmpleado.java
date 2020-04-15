/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.usuarios;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carlo
 */
public class AdaptadorDatosEmpleado {
    
    private DefaultTableModel defaultTableModel;
    private List<DatosEmpleado> datosEmpleado;
    
    public AdaptadorDatosEmpleado(List<DatosEmpleado> datosEmpleado) {
        this.defaultTableModel = new DefaultTableModel();
        this.datosEmpleado = datosEmpleado;
    }
    
    public DefaultTableModel obtenerDatos(){
        
        defaultTableModel.addColumn("Nombre Empleado");
        defaultTableModel.addColumn("Rol");
        defaultTableModel.addColumn("Usuario");
        
        Object[] datos = new Object[3];
        
        datosEmpleado.forEach((DatosEmpleado datosEmpleado1)->{
            datos[0]= datosEmpleado1.getNombre();
            datos[1]= rol(datosEmpleado1.isRol());
            datos[2]= datosEmpleado1.getUsuario();
            defaultTableModel.addRow(datos);
        });
        
        return defaultTableModel;
    }
    
    private String rol(boolean rol){
        if(rol){
            return "Administrador";
        }else{
            return "Empleado";
        }
    }
}
