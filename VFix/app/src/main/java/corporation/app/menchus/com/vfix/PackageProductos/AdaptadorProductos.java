package corporation.app.menchus.com.vfix.PackageProductos;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorProductos {

    private Call<List<ModeloProductos>> modeloProductos;
    private List<ModeloProductos> productos;

    public AdaptadorProductos(Call<List<ModeloProductos>> modeloProductos){
        this.modeloProductos= modeloProductos;
    }

    public void AdaptarProductos(){

        modeloProductos.enqueue(new Callback<List<ModeloProductos>>() {
            @Override
            public void onResponse(Call<List<ModeloProductos>> call, Response<List<ModeloProductos>> response) {
                productos = response.body();
            }

            @Override
            public void onFailure(Call<List<ModeloProductos>> call, Throwable t) {

            }
        });
    }

    public List<ModeloProductos> getProductos(){
        return this.productos;
    }

}
