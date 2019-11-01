package corporation.app.menchus.com.vfix;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import corporation.app.menchus.com.vfix.PackageCategoria.ModeloGetCategorias;
import corporation.app.menchus.com.vfix.PackageProductos.AdaptadorProductos;
import corporation.app.menchus.com.vfix.PackageProductos.AdapterProductos;
import corporation.app.menchus.com.vfix.PackageProductos.ApiProductos;
import corporation.app.menchus.com.vfix.PackageProductos.ModeloProductos;
import retrofit2.Call;
import retrofit2.Retrofit;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Retrofit consultarProductos;
    private ApiProductos apiProductos;
    private Call<List<ModeloProductos>> modeloProductos;
    private List<ModeloGetCategorias> modeloGetCategorias;
    private RecyclerView recyclerView;

    public SpinnerActivity(Retrofit consultarProductos,RecyclerView recyclerView,
                           List<ModeloGetCategorias> modeloGetCategorias){

        this.consultarProductos = consultarProductos;
        this.recyclerView=recyclerView;
        this.modeloGetCategorias=modeloGetCategorias;


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getItemAtPosition(position).toString()!= "Categorias"){

            apiProductos = consultarProductos.create(ApiProductos.class);
            modeloProductos = apiProductos.getProductos(getId(parent.getItemAtPosition(position).toString()));
            AdaptadorProductos adaptadorProductos = new AdaptadorProductos(modeloProductos);
            adaptadorProductos.AdaptarProductos();
            AdapterProductos adapterProductos = new AdapterProductos(adaptadorProductos.getProductos());
            recyclerView.setAdapter(adapterProductos);
        }
        /*Toast.makeText(parent.getContext(),"Seleccionado: "+parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_LONG).show();*/


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getId(String categoria){

        for(ModeloGetCategorias modelo : this.modeloGetCategorias){
            if(modelo.getNombre()==categoria){
                return String.valueOf(modelo.getId());
            }
        }
        return null;
    }

}
