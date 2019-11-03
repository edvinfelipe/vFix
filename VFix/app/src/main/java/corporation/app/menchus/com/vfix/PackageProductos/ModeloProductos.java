package corporation.app.menchus.com.vfix.PackageProductos;

import java.util.List;

import corporation.app.menchus.com.vfix.PackageImagenes.ModeloImagenes;

public class ModeloProductos {

    private String codigo;
    private String nombre;
    private int existencia;
    private List<ModeloImagenes> imagenes;



    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getExistencia() {
        return existencia;
    }

    public List<ModeloImagenes> getImagenes() {
        return imagenes;
    }
}
