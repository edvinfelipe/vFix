/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formularios;

import campos.Categoria;
import campos.Producto;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import requestget.GetCategoria;
import requestget.GetInventario;
import requests.EstructuraPostPut;
import requests.Requests;

/**
 *
 * @author Windows 8.1
 */
public class FModInventario extends javax.swing.JPanel {

    File archivo;
    GetInventario requestGet = new GetInventario();
    DefaultTableModel productos = new DefaultTableModel();
    GetCategoria getCategoria = new GetCategoria();
    String codigoSeleccionado = "";
    int filaSeleccionada = -1;
    List<Categoria> datosCategoria = new ArrayList<>();
    GetCategoria categoria = new GetCategoria();
    Producto objetoActual = null;
    int contImagenes = 0;
    int imagenActual = 0;
    List<Producto> listaProductos = new ArrayList<>();
    List<String> nuevasImagenes = new ArrayList<>();
    List<EstructuraPostPut> estructuraPut = new ArrayList<>();
    List<String> variablesInventario = new ArrayList<>();
    List<EstructuraPostPut> imagenes = new ArrayList<>();
    Requests requests = new Requests();
    
    public FModInventario() {
        initComponents();
        productos.addColumn("Código");
        productos.addColumn("Categoría");
        productos.addColumn("Nombre");
        productos.addColumn("Modelo");
        productos.addColumn("Color");
        productos.addColumn("Marca");
        productos.addColumn("Tipo");
        productos.addColumn("Descripción");
        productos.addColumn("Existencia");
        productos.addColumn("Precio");
        datosCategoria = (List<Categoria>)categoria.get("http://localhost:8000/api/categorias/");
        tProductos.setModel(productos);
        inicializarcbCategoria();
        inicializarvarInventario();
        inicializarEstructuraPut();
        cargarInformacion();
        lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
        
    }
    
    private void inicializarvarInventario()
    {
        variablesInventario.add("nombre");
        variablesInventario.add("color");
        variablesInventario.add("modelo");
        variablesInventario.add("marca");
        variablesInventario.add("tipo");
        variablesInventario.add("descripcion");
        variablesInventario.add("existencia");
        variablesInventario.add("precio");
        variablesInventario.add("categoriaId");
    }
    
    private void inicializarEstructuraPut()
    {
        for (int i = 0; i < 9; i++)
        {
            EstructuraPostPut objeto = new EstructuraPostPut();
            objeto.setVariable(variablesInventario.get(i));
            estructuraPut.add(objeto);
        }
    }
    
    private void inicializarcbCategoria()
    {
        cbCategoria.addItem("Seleccionar...");
        for (int i = 0; i < datosCategoria.size(); i++)
            cbCategoria.addItem(datosCategoria.get(i).getCategoria());
    }

    private void decidirCategoriaSeleccionada()
    {
        for (int i = 1; i < cbCategoria.getItemCount(); i++)
        {
            if (String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 1))
                    .equals(String.valueOf(cbCategoria.getItemAt(i))))
                cbCategoria.setSelectedIndex(i);
        }
    }
    
    private void seleccionarProducto()
    {
        try {
            while (imagenes.size() > 0)
                imagenes.remove(0);
            codigoSeleccionado = String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 0));
            filaSeleccionada = tProductos.getSelectedRow();
            decidirCategoriaSeleccionada();
            tfNombre.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 2)));
            tfModelo.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 3)));
            tfColor.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 4)));
            tfMarca.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 5)));
            tfTipo.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 6)));
            tfDescripcion.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 7)));
            tfExistencia.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 8)));
            tfPrecio.setText(String.valueOf(tProductos.getValueAt(tProductos.getSelectedRow(), 9)));
            objetoActual = listaProductos.get(filaSeleccionada);
            if (objetoActual.getImagenes().size() > 0)
            {
                imagenActual = 1;
                contImagenes = objetoActual.getImagenes().size();
                URL myURL = new URL("http://localhost:8000" + objetoActual.getImagenes().get(imagenActual - 1).getImagen());
                Image img = ImageIO.read(myURL.openStream());
                ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                lblSelImg.setIcon(img2);
            }
            else
            {
                imagenActual = 0;
                contImagenes = 0;
                Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes" +
                File.separator + "Fondo imagen.png").getImage();
                ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                lblSelImg.setIcon(img2);
            }
            lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
        } catch (MalformedURLException ex) {
            Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
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
        lblCategoria = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        lblImagenesCont = new javax.swing.JLabel();
        sepColor = new javax.swing.JSeparator();
        tfNombre = new javax.swing.JTextField();
        sepNombre = new javax.swing.JSeparator();
        tfModelo = new javax.swing.JTextField();
        sepModelo = new javax.swing.JSeparator();
        tfColor = new javax.swing.JTextField();
        lblImagen = new javax.swing.JLabel();
        tfMarca = new javax.swing.JTextField();
        sepMarca = new javax.swing.JSeparator();
        lblTipo = new javax.swing.JLabel();
        tfTipo = new javax.swing.JTextField();
        sepTipo = new javax.swing.JSeparator();
        lblDescripcion = new javax.swing.JLabel();
        tfDescripcion = new javax.swing.JTextField();
        sepDescripcion = new javax.swing.JSeparator();
        lblExistencia = new javax.swing.JLabel();
        tfExistencia = new javax.swing.JTextField();
        sepExistencia = new javax.swing.JSeparator();
        lblPrecio = new javax.swing.JLabel();
        tfPrecio = new javax.swing.JTextField();
        sepPrecio = new javax.swing.JSeparator();
        btnIngresar = new javax.swing.JButton();
        btnElegirImg = new javax.swing.JButton();
        lblSelImg = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        btnAdelante = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnEliminarImagen = new javax.swing.JButton();
        lblColor1 = new javax.swing.JLabel();
        btnAgregarImagen = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tProductos = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();

        pnlGeneral.setBackground(new java.awt.Color(34, 51, 59));
        pnlGeneral.setMinimumSize(new java.awt.Dimension(1280, 680));
        pnlGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCategoria.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblCategoria.setForeground(new java.awt.Color(255, 255, 255));
        lblCategoria.setText("Categoría *");
        pnlGeneral.add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        lblNombre.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setText("Nombre *");
        pnlGeneral.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, -1, -1));

        lblModelo.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("Modelo *");
        pnlGeneral.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        lblImagenesCont.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblImagenesCont.setForeground(new java.awt.Color(255, 255, 255));
        lblImagenesCont.setText("Contador");
        pnlGeneral.add(lblImagenesCont, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 320, 210, -1));
        pnlGeneral.add(sepColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 200, -1));

        tfNombre.setBackground(new java.awt.Color(34, 51, 59));
        tfNombre.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfNombre.setForeground(new java.awt.Color(255, 255, 255));
        tfNombre.setBorder(null);
        tfNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfNombreKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNombreKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 200, -1));
        pnlGeneral.add(sepNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 170, 200, 10));

        tfModelo.setBackground(new java.awt.Color(34, 51, 59));
        tfModelo.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfModelo.setForeground(new java.awt.Color(255, 255, 255));
        tfModelo.setBorder(null);
        tfModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfModeloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfModeloKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 200, -1));
        pnlGeneral.add(sepModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 200, -1));

        tfColor.setBackground(new java.awt.Color(34, 51, 59));
        tfColor.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfColor.setForeground(new java.awt.Color(255, 255, 255));
        tfColor.setBorder(null);
        tfColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfColorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfColorKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 200, -1));

        lblImagen.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblImagen.setForeground(new java.awt.Color(255, 255, 255));
        lblImagen.setText("Imagen");
        pnlGeneral.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, -1, -1));

        tfMarca.setBackground(new java.awt.Color(34, 51, 59));
        tfMarca.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfMarca.setForeground(new java.awt.Color(255, 255, 255));
        tfMarca.setBorder(null);
        tfMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfMarcaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfMarcaKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 200, -1));
        pnlGeneral.add(sepMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 200, -1));

        lblTipo.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(255, 255, 255));
        lblTipo.setText("Tipo *");
        pnlGeneral.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 100, -1, -1));

        tfTipo.setBackground(new java.awt.Color(34, 51, 59));
        tfTipo.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfTipo.setForeground(new java.awt.Color(255, 255, 255));
        tfTipo.setBorder(null);
        tfTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTipoActionPerformed(evt);
            }
        });
        tfTipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfTipoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfTipoKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, 200, -1));
        pnlGeneral.add(sepTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 120, 200, -1));

        lblDescripcion.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setText("Descripción *");
        pnlGeneral.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 150, -1, -1));

        tfDescripcion.setBackground(new java.awt.Color(34, 51, 59));
        tfDescripcion.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        tfDescripcion.setBorder(null);
        tfDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfDescripcionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfDescripcionKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 150, 200, -1));
        pnlGeneral.add(sepDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 170, 200, -1));

        lblExistencia.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblExistencia.setForeground(new java.awt.Color(255, 255, 255));
        lblExistencia.setText("Existencia *");
        pnlGeneral.add(lblExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 200, -1, -1));

        tfExistencia.setBackground(new java.awt.Color(34, 51, 59));
        tfExistencia.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfExistencia.setForeground(new java.awt.Color(255, 255, 255));
        tfExistencia.setBorder(null);
        tfExistencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfExistenciaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfExistenciaKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 200, 200, -1));
        pnlGeneral.add(sepExistencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 220, 200, -1));

        lblPrecio.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(255, 255, 255));
        lblPrecio.setText("Precio *");
        pnlGeneral.add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 250, -1, -1));

        tfPrecio.setBackground(new java.awt.Color(34, 51, 59));
        tfPrecio.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        tfPrecio.setForeground(new java.awt.Color(255, 255, 255));
        tfPrecio.setBorder(null);
        tfPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPrecioKeyTyped(evt);
            }
        });
        pnlGeneral.add(tfPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 250, 200, -1));
        pnlGeneral.add(sepPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 270, 200, -1));

        btnIngresar.setBackground(new java.awt.Color(226, 98, 75));
        btnIngresar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("Modificar");
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 360, 150, 50));

        btnElegirImg.setBackground(new java.awt.Color(226, 98, 75));
        btnElegirImg.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        btnElegirImg.setForeground(new java.awt.Color(255, 255, 255));
        btnElegirImg.setText("Elegir imagen");
        btnElegirImg.setFocusPainted(false);
        btnElegirImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElegirImgActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnElegirImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 140, 30));

        lblSelImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Fondo imagen.png"))); // NOI18N
        pnlGeneral.add(lblSelImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, 210, 160));

        lblMarca.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblMarca.setForeground(new java.awt.Color(255, 255, 255));
        lblMarca.setText("Marca *");
        pnlGeneral.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, -1, -1));

        cbCategoria.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCategoriaItemStateChanged(evt);
            }
        });
        cbCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cbCategoriaMouseReleased(evt);
            }
        });
        cbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriaActionPerformed(evt);
            }
        });
        cbCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbCategoriaKeyReleased(evt);
            }
        });
        pnlGeneral.add(cbCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 200, 30));

        btnAdelante.setBackground(new java.awt.Color(226, 98, 75));
        btnAdelante.setForeground(new java.awt.Color(255, 255, 255));
        btnAdelante.setText(">");
        btnAdelante.setFocusPainted(false);
        btnAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdelanteActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnAdelante, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 170, -1, -1));

        btnAtras.setBackground(new java.awt.Color(226, 98, 75));
        btnAtras.setForeground(new java.awt.Color(255, 255, 255));
        btnAtras.setText("<");
        btnAtras.setBorderPainted(false);
        btnAtras.setFocusPainted(false);
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 170, -1, -1));

        btnEliminarImagen.setBackground(new java.awt.Color(226, 98, 75));
        btnEliminarImagen.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        btnEliminarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarImagen.setText("-");
        btnEliminarImagen.setFocusPainted(false);
        btnEliminarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarImagenActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnEliminarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, 40, 30));

        lblColor1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        lblColor1.setForeground(new java.awt.Color(255, 255, 255));
        lblColor1.setText("Color *");
        pnlGeneral.add(lblColor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 57, -1));

        btnAgregarImagen.setBackground(new java.awt.Color(226, 98, 75));
        btnAgregarImagen.setFont(new java.awt.Font("Calibri", 0, 11)); // NOI18N
        btnAgregarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarImagen.setText("+");
        btnAgregarImagen.setFocusPainted(false);
        btnAgregarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarImagenActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnAgregarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 280, 40, 30));

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

        pnlGeneral.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 940, 170));

        btnEliminar.setBackground(new java.awt.Color(226, 98, 75));
        btnEliminar.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setText("Eliminar");
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        pnlGeneral.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 520, 120, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cargarInformacion()
    {
        Object O[] = null;
        
        listaProductos = (List<Producto>)requestGet.get("http://localhost:8000/api/productos/");
        List<Categoria> objeto = new ArrayList<>();
        for (int i = 0; i < listaProductos.size(); i++)
        {
            objeto.add((Categoria)
                    getCategoria.getUnaCategoria("http://localhost:8000/api/categorias/" + listaProductos.get(i).getCategoriaId() + "/"));
            productos.addRow(O);
            productos.setValueAt(listaProductos.get(i).getCodigo(), i, 0);
            productos.setValueAt(objeto.get(0).getCategoria(), i, 1);
            productos.setValueAt(listaProductos.get(i).getNombre(), i, 2);
            productos.setValueAt(listaProductos.get(i).getModelo(), i, 3);
            productos.setValueAt(listaProductos.get(i).getColor(), i, 4);
            productos.setValueAt(listaProductos.get(i).getMarca(), i, 5);
            productos.setValueAt(listaProductos.get(i).getTipo(), i, 6);
            productos.setValueAt(listaProductos.get(i).getDescripcion(), i, 7);
            productos.setValueAt(listaProductos.get(i).getExistencia(), i, 8);
            productos.setValueAt(listaProductos.get(i).getPrecio(), i, 9);
            objeto.remove(0);
        }
    }
    
    private void tfNombreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNombreKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfModelo.requestFocus();
        //else if (evt.getKeyCode() == KeyEvent.VK_UP)
        //tfCodigo.requestFocus();
    }//GEN-LAST:event_tfNombreKeyPressed

    private void tfNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNombreKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A')|c > 'Z')
        {
            if ((c != ' ') && (c != 'á') && (c != 'é') && (c != 'í') && (c != 'ó') && (c != 'ú'))
            evt.consume();
        }

        if (tfNombre.getText().length()>55)
        evt.consume();
    }//GEN-LAST:event_tfNombreKeyTyped

    private void tfModeloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfModeloKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfColor.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_UP)
        tfNombre.requestFocus();
    }//GEN-LAST:event_tfModeloKeyPressed

    private void tfModeloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfModeloKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A')|c > 'Z')
        {
            if ((c != ' ') && (c != 'á') && (c != 'é') && (c != 'í') && (c != 'ó') && (c != 'ú'))
            {
                if ((c < '0') || (c > '9'))
                evt.consume();
            }
        }

        if (tfModelo.getText().length()>45)
        evt.consume();
    }//GEN-LAST:event_tfModeloKeyTyped

    private void tfColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfColorKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfMarca.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_UP)
        tfModelo.requestFocus();
    }//GEN-LAST:event_tfColorKeyPressed

    private void tfColorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfColorKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A')|c > 'Z')
        {
            if ((c != ' ') && (c != 'á') && (c != 'é') && (c != 'í') && (c != 'ó') && (c != 'ú'))
            evt.consume();
        }

        if (tfColor.getText().length()>35)
        evt.consume();
    }//GEN-LAST:event_tfColorKeyTyped

    private void tfMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfMarcaKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfTipo.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_UP)
        tfColor.requestFocus();
    }//GEN-LAST:event_tfMarcaKeyPressed

    private void tfMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfMarcaKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A')|c > 'Z')
        {
            if ((c != ' ') && (c != 'á') && (c != 'é') && (c != 'í') && (c != 'ó') && (c != 'ú'))
            {
                if ((c < '0') || (c > '9'))
                evt.consume();
            }
        }

        if (tfMarca.getText().length()>45)
        evt.consume();
    }//GEN-LAST:event_tfMarcaKeyTyped

    private void tfTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTipoActionPerformed

    private void tfTipoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTipoKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfDescripcion.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_UP)
        tfMarca.requestFocus();
    }//GEN-LAST:event_tfTipoKeyPressed

    private void tfTipoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfTipoKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A')|c > 'Z')
        {
            if ((c != ' ') && (c != 'á') && (c != 'é') && (c != 'í') && (c != 'ó') && (c != 'ú'))
            {
                if ((c < '0') || (c > '9'))
                evt.consume();
            }
        }

        if (tfTipo.getText().length()>45)
        evt.consume();
    }//GEN-LAST:event_tfTipoKeyTyped

    private void tfDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfDescripcionKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfExistencia.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_UP)
        tfTipo.requestFocus();
    }//GEN-LAST:event_tfDescripcionKeyPressed

    private void tfDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfDescripcionKeyTyped
        char c = evt.getKeyChar();

        if ((c < 'a' || c > 'z') && (c < 'A')|c > 'Z')
        {
            if ((c != ' ') && (c != 'á') && (c != 'é') && (c != 'í') && (c != 'ó') && (c != 'ú'))
            {
                if ((c < '0') || (c > '9'))
                evt.consume();
            }
        }

        if (tfDescripcion.getText().length()>200)
        evt.consume();
    }//GEN-LAST:event_tfDescripcionKeyTyped

    private void tfExistenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfExistenciaKeyPressed
        if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        tfPrecio.requestFocus();
        else if (evt.getKeyCode() == KeyEvent.VK_UP)
        tfDescripcion.requestFocus();
    }//GEN-LAST:event_tfExistenciaKeyPressed

    private void tfExistenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfExistenciaKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
        evt.consume();

        if (tfExistencia.getText().length()>10)
        evt.consume();
    }//GEN-LAST:event_tfExistenciaKeyTyped

    private void tfPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPrecioKeyPressed
        //if ((evt.getKeyCode() == KeyEvent.VK_DOWN) || (evt.getKeyCode() == KeyEvent.VK_ENTER))
        //tfCodigo.requestFocus();
        // else if (evt.getKeyCode() == KeyEvent.VK_UP)
        // tfExistencia.requestFocus();
    }//GEN-LAST:event_tfPrecioKeyPressed

    private void tfPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPrecioKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
        {
            if (c != '.')
            evt.consume();
        }

        if (tfPrecio.getText().length()>10)
        evt.consume();
    }//GEN-LAST:event_tfPrecioKeyTyped

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        if (filaSeleccionada != -1)
        {
            if (cbCategoria.getSelectedIndex() > 0 && tfNombre.getText().length() > 0 && tfModelo.getText().length() > 0
                    && tfColor.getText().length() > 0 && tfMarca.getText().length() > 0 && tfTipo.getText().length() > 0
                    && tfDescripcion.getText().length() > 0 && tfExistencia.getText().length() > 0
                    && tfPrecio.getText().length() > 0) {
                if (imagenesInsertadas()) {
                    estructuraPut.get(0).setValor(tfNombre.getText());
                    estructuraPut.get(1).setValor(tfColor.getText());
                    estructuraPut.get(2).setValor(tfModelo.getText());
                    estructuraPut.get(3).setValor(tfMarca.getText());
                    estructuraPut.get(4).setValor(tfTipo.getText());
                    estructuraPut.get(5).setValor(tfDescripcion.getText());
                    estructuraPut.get(6).setValor(tfExistencia.getText());
                    estructuraPut.get(7).setValor(tfPrecio.getText());
                    estructuraPut.get(8).setValor(String.valueOf(
                            datosCategoria.get(cbCategoria.getSelectedIndex() - 1).getId()));
                    for (int i = 0; i < imagenes.size(); i++) {
                        estructuraPut.add(imagenes.get(i));
                    }
                    requests.put("http://localhost:8000/api/productos/" + objetoActual.getCodigo() + "/", estructuraPut);
                    tfNombre.setText("");
                    tfColor.setText("");
                    tfModelo.setText("");
                    tfMarca.setText("");
                    tfTipo.setText("");
                    tfDescripcion.setText("");
                    tfExistencia.setText("");
                    tfPrecio.setText("");
                    cbCategoria.setSelectedIndex(0);
                    Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                            + File.separator + "Fondo imagen.png").getImage();
                    ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                    lblSelImg.setIcon(img2);
                    JOptionPane.showMessageDialog(null, "Producto modificado satisfactoriamente");
                    reiniciarValores();
                }
                else
                    JOptionPane.showMessageDialog(null, imagenesSinInsertar());
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Por favor seleccione un producto");
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void reiniciarValores()
    {
        while (imagenes.size() > 0) {
            imagenes.remove(0);
        }
        while (estructuraPut.size() > 9) {
            estructuraPut.remove(9);
        }
        imagenActual = contImagenes = 0;
        lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
        while (productos.getRowCount() > 0) {
            productos.removeRow(0);
        }
        cargarInformacion();
        filaSeleccionada = -1;
    }
    private String imagenesSinInsertar()
    {
        List<Integer> posiciones = new ArrayList<>();
        String resultado = "";
        boolean encontrado = false;
        for (int i = 0; i < imagenes.size(); i++)
        {
            if (imagenes.get(i).getValor().toString()
                    .equals("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes" +
                File.separator + "Fondo imagen.png")
                    )
            posiciones.add(objetoActual.getImagenes().size() + i+1);
        }
            
        
        if (posiciones.size() == 1)
                resultado += "Hace falta insertar la imagen " + posiciones.get(0);
        else
        {
            resultado += "Hace falta insertar las imágenes ";
            while (posiciones.size() > 0)
            {
                if (posiciones.size() > 1)
                    resultado += posiciones.get(0) + ", ";
                else
                    resultado += posiciones.get(0);
                posiciones.remove(0);
            }
        }
        return resultado;
    }
    
    private boolean imagenesInsertadas()
    {
        for (int i = 0; i < imagenes.size(); i++)
        {
            if (imagenes.get(i).getValor().toString().equals("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes" +
                File.separator + "Fondo imagen.png"))
                return false;
        }
        return true;
    }
    
    private void btnElegirImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElegirImgActionPerformed
        if (contImagenes > 0) {
            if (imagenActual > objetoActual.getImagenes().size())
            {
                JFileChooser filechooser = new JFileChooser();
                FileFilter filtradorArchivo = new FileNameExtensionFilter("Imágenes", new String[]{"jpg", "jpeg", "png"});
                filechooser.setDialogTitle("Buscar imagen");
                filechooser.setFileFilter(filtradorArchivo);
                if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File archivo = new File(filechooser.getSelectedFile().toString());
                    if (FilenameUtils.getExtension(archivo.getAbsolutePath()).equals("jpg")
                            || FilenameUtils.getExtension(archivo.getAbsolutePath()).equals("jpeg")
                            || FilenameUtils.getExtension(archivo.getAbsolutePath()).equals("png")) {
                        this.archivo = archivo;
                        System.out.println(archivo.toString());
                        Image img = new ImageIcon(archivo.toString()).getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                        FileBody imagen = new FileBody(archivo);
                        if (imagen != null) {
                            imagenes.get(imagenActual - (objetoActual.getImagenes().size()+1)).setValor(imagen);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese una imagen");
                    }
                }
            } 
            else
                JOptionPane.showMessageDialog(null, "Las imágenes cargadas del servidor no se pueden modificar");
        } else
            JOptionPane.showMessageDialog(null, "Todavía no se ha agregado ninguna imagen");
            
    }//GEN-LAST:event_btnElegirImgActionPerformed

    private void cbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCategoriaItemStateChanged

    }//GEN-LAST:event_cbCategoriaItemStateChanged

    private void cbCategoriaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbCategoriaMouseReleased

    }//GEN-LAST:event_cbCategoriaMouseReleased

    private void cbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriaActionPerformed

    }//GEN-LAST:event_cbCategoriaActionPerformed

    private void cbCategoriaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbCategoriaKeyReleased

    }//GEN-LAST:event_cbCategoriaKeyReleased

    private void btnAdelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdelanteActionPerformed
        if (imagenActual < contImagenes) {
            try {
                imagenActual++;
                lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
                if (imagenActual <= objetoActual.getImagenes().size())
                {
                    URL myURL = new URL("http://localhost:8000" + objetoActual.getImagenes().get(imagenActual - 1).getImagen());
                    Image img = ImageIO.read(myURL.openStream());
                    ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                    lblSelImg.setIcon(img2);
                }
                else
                {
                    if (!imagenes.get(imagenActual - (objetoActual.getImagenes().size()+1)).getValor().toString()
                            .equals("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                                    + File.separator + "Fondo imagen.png")) {
                        FileBody imagenObtenida = (FileBody) imagenes.get(imagenActual - (objetoActual.getImagenes().size()+1)).getValor();
                        Image img = new ImageIcon(imagenObtenida.getFile().toString()).getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    } else {
                        Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                                + File.separator + "Fondo imagen.png").getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    }
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAdelanteActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        if (imagenActual > 1) {
            try {
                imagenActual--;
                lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
                if (imagenActual <= objetoActual.getImagenes().size()) {
                    URL myURL = new URL("http://localhost:8000" + objetoActual.getImagenes().get(imagenActual - 1).getImagen());
                    Image img = ImageIO.read(myURL.openStream());
                    ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                    lblSelImg.setIcon(img2);
                } else {
                    if (!imagenes.get(imagenActual - (objetoActual.getImagenes().size() + 1)).getValor().toString()
                            .equals("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                                    + File.separator + "Fondo imagen.png")) {
                        FileBody imagenObtenida = (FileBody) imagenes.get(imagenActual - (objetoActual.getImagenes().size()+1)).getValor();
                        Image img = new ImageIcon(imagenObtenida.getFile().toString()).getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    } else {
                        Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                                + File.separator + "Fondo imagen.png").getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    }
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnEliminarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarImagenActionPerformed
        if (filaSeleccionada != -1) {
            if (imagenActual > objetoActual.getImagenes().size()) {
                imagenes.remove(imagenActual - (objetoActual.getImagenes().size() + 1));
            } else {
                EstructuraPostPut objeto = new EstructuraPostPut();
                objeto.setVariable("imagenes_borrar");
                objeto.setValor(objetoActual.getImagenes().get(imagenActual - 1).getId());
                estructuraPut.add(objeto);
                objetoActual.getImagenes().remove(imagenActual - 1);
            }
            if (imagenActual > 1) {
                imagenActual--;
            }
            if (contImagenes > 0) {
                contImagenes--;
            }
            if (contImagenes == 0 && imagenActual == 1) {
                imagenActual--;
            }
            if (contImagenes == 0) {
                Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                        + File.separator + "Fondo imagen.png").getImage();
                ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                lblSelImg.setIcon(img2);
            }
            lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
            if (imagenActual > 0) {
                if (imagenActual <= objetoActual.getImagenes().size()) {
                    try {
                        URL myURL = new URL("http://localhost:8000" + objetoActual.getImagenes().get(imagenActual - 1).getImagen());
                        Image img = ImageIO.read(myURL.openStream());
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FModInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    if (!imagenes.get(imagenActual - (objetoActual.getImagenes().size() + 1)).getValor().toString()
                            .equals("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                                    + File.separator + "Fondo imagen.png")) {
                        FileBody imagenObtenida = (FileBody) imagenes.get(imagenActual - (objetoActual.getImagenes()
                                .size() + 1)).getValor();
                        Image img = new ImageIcon(imagenObtenida.getFile().toString()).getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    } else {
                        Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                                + File.separator + "Fondo imagen.png").getImage();
                        ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
                        lblSelImg.setIcon(img2);
                    }
                }
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Por favor seleccione un producto");
    }//GEN-LAST:event_btnEliminarImagenActionPerformed

    private void btnAgregarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarImagenActionPerformed
        if (filaSeleccionada != -1)
        {
            Image img = new ImageIcon("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                    + File.separator + "Fondo imagen.png").getImage();
            ImageIcon img2 = new ImageIcon(img.getScaledInstance(lblSelImg.getWidth(), lblSelImg.getHeight(), Image.SCALE_SMOOTH));
            lblSelImg.setIcon(img2);
            EstructuraPostPut estructuraPost = new EstructuraPostPut();
            estructuraPost.setVariable("imagenes");
            estructuraPost.setValor("src" + File.separator + "main" + File.separator + "resources" + File.separator + "imagenes"
                    + File.separator + "Fondo imagen.png");
            imagenes.add(estructuraPost);
            contImagenes++;
            imagenActual = contImagenes;
            lblImagenesCont.setText(imagenActual + " de " + contImagenes + " imágenes agregadas");
        }
        else
            JOptionPane.showMessageDialog(null, "Por favor seleccione un producto");
        
    }//GEN-LAST:event_btnAgregarImagenActionPerformed

    private void tProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseClicked

    }//GEN-LAST:event_tProductosMouseClicked

    private void tProductosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tProductosMouseReleased
        if (evt.getButton() == 1)
            seleccionarProducto();
    }//GEN-LAST:event_tProductosMouseReleased

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (filaSeleccionada != -1)
        {
            int input = JOptionPane.showConfirmDialog(null, "¿Eliminar producto?","Eliminar",JOptionPane.OK_CANCEL_OPTION);
            if (input == 0)
            {
                requests.delete("http://localhost:8000/api/productos/" + objetoActual.getCodigo() + "/");
                JOptionPane.showMessageDialog(null, "Producto eliminado satisfactoriamente");
                reiniciarValores();
            }
        }
        else
            JOptionPane.showMessageDialog(null, "Por favor seleccione un producto");
    }//GEN-LAST:event_btnEliminarActionPerformed

    
    
    private void limpiarTProductos()
    {
        while (productos.getRowCount() > 0)
            productos.removeRow(0);
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdelante;
    private javax.swing.JButton btnAgregarImagen;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnElegirImg;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarImagen;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblColor1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblExistencia;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblImagenesCont;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblSelImg;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel pnlGeneral;
    private javax.swing.JSeparator sepColor;
    private javax.swing.JSeparator sepDescripcion;
    private javax.swing.JSeparator sepExistencia;
    private javax.swing.JSeparator sepMarca;
    private javax.swing.JSeparator sepModelo;
    private javax.swing.JSeparator sepNombre;
    private javax.swing.JSeparator sepPrecio;
    private javax.swing.JSeparator sepTipo;
    private javax.swing.JTable tProductos;
    private javax.swing.JTextField tfColor;
    private javax.swing.JTextField tfDescripcion;
    private javax.swing.JTextField tfExistencia;
    private javax.swing.JTextField tfMarca;
    private javax.swing.JTextField tfModelo;
    private javax.swing.JTextField tfNombre;
    private javax.swing.JTextField tfPrecio;
    private javax.swing.JTextField tfTipo;
    // End of variables declaration//GEN-END:variables
}
