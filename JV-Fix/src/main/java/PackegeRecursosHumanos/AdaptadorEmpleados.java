/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackegeRecursosHumanos;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author carlo
 */
public class AdaptadorEmpleados {
    
    private DefaultTableModel modelo;
    private final List<Empleado> listaEmpleados;
    private Image imagen;
    private final String URL = "http://icris17.pythonanywhere.com";
    
    public AdaptadorEmpleados(List<Empleado> listaEmpleados){
        this.listaEmpleados=listaEmpleados;
    }
    
    public DefaultTableModel obtenerModelo(){
        
        String[] columnas = {"Codigo","Empleado","Rol","Fotografia"};
        this.modelo = new DefaultTableModel(columnas,0){
            
            @Override
            public boolean isCellEditable(int fila,int columna){
                return false;
            }
        };
        
        Object[] listaEmpleado = new Object[4];
        
        listaEmpleados.forEach((Empleado empleado)-> {
            listaEmpleado[0]= empleado.getCodigo();
            listaEmpleado[1]= empleado.getNombre();
            if(empleado.isRol()==true){
                listaEmpleado[2]="Administrador";
            }else{ 
                listaEmpleado[2]="Empleado"; 
            }
            
            listaEmpleado[3]=(new JLabel(foto(empleado))); 
            modelo.addRow(listaEmpleado);
        });
        return this.modelo;
    }
    
    private ImageIcon foto(Empleado empleado){
        
        try {
            imagen = new ImageIcon(new URL(URL+empleado.getImagen())).getImage();
            Image newimg = imagen.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
            ImageIcon ima = new ImageIcon(newimg);
            return ima;
        } catch (MalformedURLException ex) {
            Logger.getLogger(AdaptadorEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
