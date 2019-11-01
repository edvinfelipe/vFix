package corporation.app.menchus.com.vfix;

public class ListaProductos {

    private String categoria;
    private String nombre;
    private int existencia;

    public ListaProductos(String categoria, String nombre, int existencia) {
        this.categoria = categoria;
        this.nombre = nombre;
        this.existencia = existencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getCategoria(){
        return this.categoria;
    }
}
