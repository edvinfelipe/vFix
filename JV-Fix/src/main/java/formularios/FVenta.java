/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import PackegeRecursosHumanos.Empleado;
import PackegeRecursosHumanos.Empleados;
import campos.Cliente;
import campos.Producto;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import requestget.GetClientes;
import requestget.GetInventario;
import requests.EstructuraPostPut;
import requests.Requests;

/**
 *
 * @author User
 */
public class FVenta extends javax.swing.JPanel {

    /**
     * Creates new form FVenta
     */
    
    Empleados empleados = new Empleados("http://icris17.pythonanywhere.com/api/recursoshumanos/");
    List<Empleado> listaEmpleados = new ArrayList<>();
    Empleado objetoEmpleado;
    Producto objetoProducto;
    List<Cliente> listaClientes = new ArrayList<>();
    List<Producto> listaProductos = new ArrayList<>();
    GetClientes getClientes = new GetClientes();
    GetInventario getInventario = new GetInventario();
    DefaultTableModel modeloFiltrado = new DefaultTableModel();
    DefaultTableModel modeloFactura = new DefaultTableModel();
    List<String> putProducto = new ArrayList<>();
    List<EstructuraPostPut> estructuraPut = new ArrayList<>();
    Requests requests = new Requests();
    public FVenta() {
        initComponents();
        listaEmpleados = empleados.mostrarEmpleados();
        btnCerrarSesion.setEnabled(false);
        btnCerrarSesion.setVisible(false);
        modeloFiltrado.addColumn("Código");
        modeloFiltrado.addColumn("Nombre");
        modeloFiltrado.addColumn("Existencia");
        modeloFiltrado.addColumn("Precio");
        modeloFactura.addColumn("Cantidad");
        modeloFactura.addColumn("Código");
        modeloFactura.addColumn("Descripción");
        modeloFactura.addColumn("Total");
        tFactura.setModel(modeloFactura);
        tProductos.setModel(modeloFiltrado);
        filtrarProductoCodigo("");
        inicializarListener();
    }
    
    private void inicializarEstructuraPut()
    {
        putProducto.add("nombre");
        putProducto.add("color");
        putProducto.add("modelo");
        putProducto.add("marca");
        putProducto.add("tipo");
        putProducto.add("descripcion");
        putProducto.add("existencia");
        putProducto.add("precio");
        putProducto.add("categoriaId");
    }

    private void inicializarEstructura()
    {
        for (int i = 0; i < putProducto.size(); i++)
        {
            EstructuraPostPut objeto = new EstructuraPostPut();
            objeto.setVariable(putProducto.get(i));
            estructuraPut.add(objeto);
        }
    }

    

    private void  inicializarListener(){
        tProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evet){
                JTable tabla = (JTable) Mouse_evet.getSource();
                Point point = Mouse_evet.getPoint();
                if(Mouse_evet.getClickCount()==2){
                    String respuesta = "";
                    respuesta = JOptionPane.showInputDialog(null, "Ingrese las unidades a comprar:");
                    if (respuesta != null) {
                        Producto objeto = new Producto();
                        try{
                            int descuento = Integer.parseInt(respuesta);
                            objeto = (Producto)getInventario.getObjeto("http://localhost:8000/api/productos/" + 
                                    modeloFiltrado.getValueAt(tProductos.getSelectedRow(), 0));
                            if (objeto.getExistencia() >= descuento)
                            {
                                Object O[] = null;
                                modeloFactura.addRow(O);
                                //cantidad
                                modeloFactura.setValueAt(respuesta, modeloFactura.getRowCount() - 1, 0);
                                //código
                                modeloFactura.setValueAt(modeloFiltrado.getValueAt(tProductos.getSelectedRow(), 0), modeloFactura.getRowCount() - 1, 1);
                                //descripción
                                modeloFactura.setValueAt("Compra " + modeloFiltrado.getValueAt(tProductos.getSelectedRow(), 1),
                                        modeloFactura.getRowCount() - 1, 2);
                                //total
                                double calculo = Double.parseDouble(modeloFiltrado.getValueAt(tProductos.getSelectedRow(), 3).toString()) * descuento;
                                modeloFactura.setValueAt(calculo, modeloFactura.getRowCount() - 1, 3);
                                requests.put("http://localhost:8000/api/productos/", estructuraPut);
                            }
                            else
                                JOptionPane.showMessageDialog(null, "No hay suficientes existencias");
                            
                        }
                        catch (Exception e)
                        {
                            System.out.println("Ingrese sólo números enteros");
                        }
                        
                    }
                }
            }
        });
    }
    
    private void  inicializarListenerBorrar(){
        tFactura.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evet){
                JTable tabla = (JTable) Mouse_evet.getSource();
                Point point = Mouse_evet.getPoint();
                if(Mouse_evet.getClickCount()==2){
                    
                }
            }
        });
    }
    private void filtrarProductoCodigo(String info)
    {
        Object O[] = null;
        if (info.equals(""))
            listaProductos = (List<Producto>)getInventario.get("http://localhost:8000/api/productos/");
        else
            listaProductos = (List<Producto>)getInventario.filtrar("http://localhost:8000/api/productos/filtrar/?codigo=" + info);
        for (int i = 0; i < listaProductos.size(); i++)
        {
            if (listaProductos.get(i).getExistencia() > 0)
            {
                modeloFiltrado.addRow(O);
                modeloFiltrado.setValueAt(listaProductos.get(i).getCodigo(), i, 0);
                modeloFiltrado.setValueAt(listaProductos.get(i).getNombre(), i, 1);
                modeloFiltrado.setValueAt(listaProductos.get(i).getExistencia(), i, 2);
                modeloFiltrado.setValueAt(listaProductos.get(i).getPrecio(), i, 3);
            }
        }
    }
    
    private void filtrarProductoNombre(String info)
    {
        Object O[] = null;
        if (info.equals(""))
            listaProductos = (List<Producto>)getInventario.get("http://localhost:8000/api/productos/");
        else
            listaProductos = (List<Producto>)getInventario.filtrar("http://localhost:8000/api/productos/filtrar/?nombre=" + info);
        for (int i = 0; i < listaProductos.size(); i++)
        {
            if (listaProductos.get(i).getExistencia() > 0)
            {
                modeloFiltrado.addRow(O);
                modeloFiltrado.setValueAt(listaProductos.get(i).getCodigo(), i, 0);
                modeloFiltrado.setValueAt(listaProductos.get(i).getNombre(), i, 1);
                modeloFiltrado.setValueAt(listaProductos.get(i).getExistencia(), i, 2);
                modeloFiltrado.setValueAt(listaProductos.get(i).getPrecio(), i, 3);
            }
            
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlGeneral = new javax.swing.JPanel();
        lblQTotal = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblVendedor = new javax.swing.JLabel();
        lblContrasena = new javax.swing.JLabel();
        sepNit = new javax.swing.JSeparator();
        sepContrasena = new javax.swing.JSeparator();
        tfDireccion = new javax.swing.JTextField();
        sepDireccion = new javax.swing.JSeparator();
        tfNit = new javax.swing.JTextField();
        sepVendedor = new javax.swing.JSeparator();
        tfCliente = new javax.swing.JTextField();
        lblCliente = new javax.swing.JLabel();
        sepFacturacion = new javax.swing.JSeparator();
        sepCuenta = new javax.swing.JSeparator();
        sepCliente = new javax.swing.JSeparator();
        tfVendedor = new javax.swing.JTextField();
        lblNit = new javax.swing.JLabel();
        tfCodProducto = new javax.swing.JTextField();
        lblFacturacion = new javax.swing.JLabel();
        tfNombre = new javax.swing.JTextField();
        spFacturacion = new javax.swing.JScrollPane();
        tFactura = new javax.swing.JTable();
        sepFiltro = new javax.swing.JSeparator();
        lblNombre = new javax.swing.JLabel();
        btnDescuento = new javax.swing.JButton();
        btnFacturar = new javax.swing.JButton();
        lblCProducto = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        pfContrasena = new javax.swing.JPasswordField();
        btnCerrarSesion = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();

        pnlGeneral.setBackground(new java.awt.Color(34, 51, 59));
        pnlGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblQTotal.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblQTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblQTotal.setText("Q. 999999.99");
        pnlGeneral.add(lblQTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 620, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion.setText("Dirección");
        pnlGeneral.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, -1));

        lblVendedor.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(255, 255, 255));
        lblVendedor.setText("Vendedor");
        pnlGeneral.add(lblVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 30, -1, -1));

        lblContrasena.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblContrasena.setForeground(new java.awt.Color(255, 255, 255));
        lblContrasena.setText("Contraseña");
        pnlGeneral.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, 100, -1));
        pnlGeneral.add(sepNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 260, 0));
        pnlGeneral.add(sepContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 90, 200, -1));

        tfDireccion.setBackground(new java.awt.Color(34, 51, 59));
        tfDireccion.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfDireccion.setForeground(new java.awt.Color(255, 255, 255));
        tfDireccion.setBorder(null);
        pnlGeneral.add(tfDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 270, 20));
        pnlGeneral.add(sepDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 270, -1));

        tfNit.setBackground(new java.awt.Color(34, 51, 59));
        tfNit.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfNit.setForeground(new java.awt.Color(255, 255, 255));
        tfNit.setBorder(null);
        tfNit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNitActionPerformed(evt);
            }
        });
        tfNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNitKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 260, -1));
        pnlGeneral.add(sepVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 50, 200, -1));

        tfCliente.setBackground(new java.awt.Color(34, 51, 59));
        tfCliente.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfCliente.setForeground(new java.awt.Color(255, 255, 255));
        tfCliente.setBorder(null);
        pnlGeneral.add(tfCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 260, -1));

        lblCliente.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblCliente.setText("Cliente");
        pnlGeneral.add(lblCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));
        pnlGeneral.add(sepFacturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 360, 1350, 10));

        sepCuenta.setOrientation(javax.swing.SwingConstants.VERTICAL);
        pnlGeneral.add(sepCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 10, 110));
        pnlGeneral.add(sepCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 260, -1));

        tfVendedor.setBackground(new java.awt.Color(34, 51, 59));
        tfVendedor.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfVendedor.setForeground(new java.awt.Color(255, 255, 255));
        tfVendedor.setBorder(null);
        tfVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfVendedorKeyPressed(evt);
            }
        });
        pnlGeneral.add(tfVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 30, 200, -1));

        lblNit.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblNit.setForeground(new java.awt.Color(255, 255, 255));
        lblNit.setText("Nit");
        pnlGeneral.add(lblNit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        tfCodProducto.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfCodProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCodProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfCodProductoKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfCodProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 200, -1));

        lblFacturacion.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblFacturacion.setForeground(new java.awt.Color(255, 255, 255));
        lblFacturacion.setText("Facturación");
        pnlGeneral.add(lblFacturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 370, -1, -1));

        tfNombre.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfNombreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNombreKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 130, -1));

        tFactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spFacturacion.setViewportView(tFactura);

        pnlGeneral.add(spFacturacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 1230, 180));
        pnlGeneral.add(sepFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, 110, 1350, -1));

        lblNombre.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("Nombre");
        pnlGeneral.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        btnDescuento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Descuento.png"))); // NOI18N
        btnDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescuentoActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 620, 120, 30));

        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Facturar.png"))); // NOI18N
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnFacturar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 620, 120, 30));

        lblCProducto.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblCProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblCProducto.setText("Código Producto");
        pnlGeneral.add(lblCProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

        lblTotal.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("Total");
        pnlGeneral.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 620, -1, -1));

        pfContrasena.setBackground(new java.awt.Color(34, 51, 59));
        pfContrasena.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pfContrasena.setForeground(new java.awt.Color(255, 255, 255));
        pfContrasena.setBorder(null);
        pfContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pfContrasenaKeyPressed(evt);
            }
        });
        pnlGeneral.add(pfContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 70, 200, 20));

        btnCerrarSesion.setBackground(new java.awt.Color(226, 98, 75));
        btnCerrarSesion.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setText("x");
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnCerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 0, -1, -1));

        tProductos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Dirección", "Usuario"
            }
        ));
        tProductos.setFocusable(false);
        tProductos.getTableHeader().setReorderingAllowed(false);
        tProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tProductosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tProductosMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tProductos);

        pnlGeneral.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 1230, 140));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tfNitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNitActionPerformed

    private void btnDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescuentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDescuentoActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFacturarActionPerformed

    private void tfVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfVendedorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            pfContrasena.requestFocus();
        }    
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (pfContrasena.getPassword().length == 0)
                pfContrasena.requestFocus();
            else
                Loguear();
        }
    }//GEN-LAST:event_tfVendedorKeyPressed

    private void pfContrasenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pfContrasenaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            tfVendedor.requestFocus();
        }    
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (tfVendedor.getText().length() == 0)
                tfVendedor.requestFocus();
            else
                Loguear();
        }
    }//GEN-LAST:event_pfContrasenaKeyPressed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        JOptionPane.showMessageDialog(null, "Adiós");
        btnCerrarSesion.setEnabled(false);
        btnCerrarSesion.setVisible(false);
        tfVendedor.setEnabled(true);
        pfContrasena.setEnabled(true);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void tfNitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNitKeyTyped
        char c = evt.getKeyChar();

        if ((c < '0') || (c > '9')) {
            if (c != '-') {
                evt.consume();
            }
        }

        if (tfNit.getText().length() > 10)
            evt.consume();      
        
        listaClientes = (List<Cliente>)getClientes.get("http://localhost:8000/api/clientes/filtrar/?nit=" + tfNit.getText());
        for (int i = 0; i < listaClientes.size(); i++)
        {
            System.out.println(listaClientes.get(i).getNombre());
            System.out.println(listaClientes.get(i).getDireccion());
        }
            
    }//GEN-LAST:event_tfNitKeyTyped

    private void tfNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNombreKeyTyped
        
    }//GEN-LAST:event_tfNombreKeyTyped

    private void tfCodProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCodProductoKeyTyped
        
    }//GEN-LAST:event_tfCodProductoKeyTyped

    private void tfCodProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCodProductoKeyReleased
        while (modeloFiltrado.getRowCount() > 0)
            modeloFiltrado.removeRow(0);
        filtrarProductoCodigo(tfCodProducto.getText());
    }//GEN-LAST:event_tfCodProductoKeyReleased

    private void tfNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNombreKeyReleased
        while (modeloFiltrado.getRowCount() > 0)
            modeloFiltrado.removeRow(0);
        filtrarProductoNombre(tfNombre.getText());
    }//GEN-LAST:event_tfNombreKeyReleased

    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked

    }//GEN-LAST:event_tProductosMouseClicked

    private void tProductosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseReleased

    }//GEN-LAST:event_tProductosMouseReleased

    private void Loguear()
    {
        boolean encontrado = false;
        for (int i = 0; i < listaEmpleados.size(); i++)
        {
            if (tfVendedor.getText().equals(listaEmpleados.get(i).getUsuario()) &&
                    String.valueOf(pfContrasena.getPassword()).equals(listaEmpleados.get(i).getContresenia()))
            {
                tfVendedor.setEnabled(false);
                pfContrasena.setEnabled(false);
                tfVendedor.setText("");
                pfContrasena.setText("");
                JOptionPane.showMessageDialog(null, "Bienvenido");
                objetoEmpleado = listaEmpleados.get(i);
                encontrado = true;
                btnCerrarSesion.setEnabled(true);
                btnCerrarSesion.setVisible(true);
            }
        }
        if (!encontrado)
            JOptionPane.showMessageDialog(null, "Error en autenticación");
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnDescuento;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCProducto;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFacturacion;
    private javax.swing.JLabel lblNit;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblQTotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JPasswordField pfContrasena;
    private javax.swing.JPanel pnlGeneral;
    private javax.swing.JSeparator sepCliente;
    private javax.swing.JSeparator sepContrasena;
    private javax.swing.JSeparator sepCuenta;
    private javax.swing.JSeparator sepDireccion;
    private javax.swing.JSeparator sepFacturacion;
    private javax.swing.JSeparator sepFiltro;
    private javax.swing.JSeparator sepNit;
    private javax.swing.JSeparator sepVendedor;
    private javax.swing.JScrollPane spFacturacion;
    private javax.swing.JTable tFactura;
    private javax.swing.JTable tProductos;
    private javax.swing.JTextField tfCliente;
    private javax.swing.JTextField tfCodProducto;
    private javax.swing.JTextField tfDireccion;
    private javax.swing.JTextField tfNit;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfVendedor;
    // End of variables declaration//GEN-END:variables
}
