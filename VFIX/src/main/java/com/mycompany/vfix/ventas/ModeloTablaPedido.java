/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vfix.ventas;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nasc_
 */
public class ModeloTablaPedido extends AbsModeloTabla{
    @Override
    public DefaultTableModel getModelo() {
        DefaultTableModel modelo = null;
        try {
            modelo = (new DefaultTableModel(
                    null, new String[]{
                        "Código",
                        "Cantidad","Unidad",
                        "Subtotal"}) {
                Class[] types = new Class[]{
                    java.lang.String.class,
                    java.lang.Float.class,
                    java.lang.String.class,
                    java.lang.Float.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false, false, false
                };
                
                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return canEdit[colIndex];
                }
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return modelo;
    }
}
