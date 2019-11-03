package corporation.app.menchus.com.vfix.PackageCategoria;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorCategoria {

    private Call<List<ModeloGetCategorias>> categorias ;
    private ArrayList<String> adaptadorCategorias;
    private List<ModeloGetCategorias> categoriasObtenidas;

    public AdaptadorCategoria (Call<List<ModeloGetCategorias>> categorias,ArrayList<String> adaptadorCategorias){
        this.categorias=categorias;
        this.adaptadorCategorias = adaptadorCategorias;
        ObtenerCategorias();
    }

    private void ObtenerCategorias(){


        categorias.enqueue(new Callback<List<ModeloGetCategorias>>() {
            @Override
            public void onResponse(Call<List<ModeloGetCategorias>> call, Response<List<ModeloGetCategorias>> response) {

                    categoriasObtenidas = response.body();

                for (ModeloGetCategorias  modelo : categoriasObtenidas) {
                    adaptadorCategorias.add(modelo.getNombre());
                    System.out.println("Categorias: "+modelo.getNombre());
                }
            }
            @Override
            public void onFailure(Call<List<ModeloGetCategorias>> call, Throwable t) {

            }
        });

    }

    public ArrayList<String> getCategorias(){
        return this.adaptadorCategorias;
    }

    public List<ModeloGetCategorias> getCategoriasObtenidas(){
        return this.categoriasObtenidas;
    }
}
